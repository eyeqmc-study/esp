if (!window.esp) {
	window.esp = new Object();
}

esp.page = {
		reg : new Object()
};

var isTest = 3;
//var apiKey = "80c0bbc7e9db2e1b68b301e0c90264ecdbfdeb1d";
var protocol=(location.protocol=="https:"?"https:":"http:");
var addrToCoordApiAddr = protocol+"//apis.daum.net/local/geo/addr2coord?apikey=";
var coordToAddrApiAddr = protocol+"//apis.daum.net/local/geo/coord2addr?apikey=";
var addrSearchCallback = "pongSearch";
var searchResultBody = "search-result";
var searchResultHeader = "search-result-count";
var roadViewAddrHeader = "rvAddr";

var searchViewId = "map-search-view";

//var _LOCAL_API_KEY = "69b6253e667bfca8f626d43189e7d040bf1c6ebe";
var _LOCAL_API_KEY = "b9b7de587425cf153be35b1c4a224a56f89f557c";

var _DNADEV_API_KEY = "5b90a73d7eeba1b9a11135f8304d0971eddaa196";
var _DNA_API_KEY = "4f4aa74e5487db73d0fac67496c7ee3fb648d989";

var marker;
var mark;
var map;
var po;
var infoWindow;

esp.page.reg.init = {
		submitBtnEvent : function() {
			$('.reg-form input[type="submit"]').click(function(event){
				var imageQuery = $('#upload-container');
				var placeQuery = $('#place-container');
				var placeName = $('.place-name', placeQuery).text();
				var imageName = $('.fileupload-progress .bar', imageQuery).width();
				
				if (!imageName || imageName === 0) {
					alert("이미지를 등록 해주세요");
					return false;
				}

				if (!placeName || placeName === '-' || placeName === '') {
					alert("장소를 등록 해주세요");
					return false;
				}
			});
		},
		datePicker : function() {
			$.getJSON("/esp/static/json/datepicker_options_ko.json", function(data){
			}).success(function(data, textStatus, jqXHR){
				$("#s_fdate").datepicker(data);
				$("#s_tdate").datepicker(data);
			}).error(function(data, textStatus, jqXHR){
				var reason = "=== Datepicker options data load error ===\n";
				reason += " - reason : " + textStatus + "\n";
				reason += "==========================================\n";
				reason += " - Date picker load with default options.\n";
				reason += "==========================================";
				if (console.log) {
					console.log(reason);
				} else {
					alert(reason);
				}
				$("#s_fdate").datepicker();
				$("#s_tdate").datepicker();
			});
		},
		mapSearchDialog : function() {
			$("#place-selector").dialog({
				width : 700,
				modal : true,
				title : "장소 등록",
				autoOpen: false,
				open : function(event, ui) {
					esp.page.reg.init.studyPlaceMap();
				},
				buttons : [{
					text : "등록",
					click : function() {
						var placeName=prompt("장소 이름을 입력해주세요","");
						$(".place-infobar div.place-name").text(placeName);
						esp.page.reg.addStudyPlace();
						$(this).dialog("close");
					}
				},{
					text : "취소",
					click : function() {
						$(".place-infobar div.place-name").text("");
						$(".place-infobar div.place-addr").text("");
						$(this).dialog("close");
					}
				}]
			});
			$("#place-container span.place-add-btn").click(function(event){
				$("#place-selector").dialog("open");
			});
		},
		imageUploader : function() {
			$("#fileupload").fileupload({
				url : '/esp/image/upload',
				dataType : 'json',
				type: 'POST',
				maxFileSize : 5000000,
				maxNumberOfFiles : 1,
				acceptFileTypes : /(\.|\/)(gif|jpe?g|png)$/i,
				done: function (e, data) {
					alert("업로드 완료");
		        },
				stop: function (e) {
		            var that = $(this).data('blueimp-fileupload') ||
		                    $(this).data('fileupload'),
		                deferred = that._addFinishedDeferreds();
		            $.when.apply($, that._getFinishedDeferreds())
		                .done(function () {
		                    that._trigger('stopped', e);
		                });
		            that._transition($(this).find('.fileupload-progress')).done(
		                function () {
		                    deferred.resolve();
		                }
		            );
		        },
		        destroy: function (e, data) {
		            var that = $(this).data('blueimp-fileupload') ||
		                    $(this).data('fileupload');
		            if (data.url) {
		                $.ajax(data);
		            }
		            that._adjustMaxNumberOfFiles(1);
		            that._transition($(this).find('.fileupload-progress')).done(
		                function () {
		                	$(this).find('.progress')
		                    .attr('aria-valuenow', '0')
		                    .find('.bar').css('width', '0%');
		                	$(this).find('.progress-extended').html('&nbsp;');
		                }
		            );
		            that._transition(data.context).done(
		                function () {
		                    $(this).remove();
		                    that._trigger('destroyed', e, data);
		                }
		            );
		        }
			});
		},
		studyPlaceMap : function(id, position) {
			var viewQuery = $("#" + searchViewId);
			$(".search-btn", viewQuery).click(function(event){
				var e = window.event || event;
				e.preventDefault();
				searchPosition(searchViewId);
			});
			$(".search-word", viewQuery).keydown(function(event){
				var e = window.event || event;
				if (e.keyCode === 13) {
					searchPosition(searchViewId);
				}
			});
			
			if (!id) {
				id = "study-place-map";
			}
			if (!position) {
				position = [37.537123, 127.005523];
			}
			var pos = new daum.maps.LatLng(position[0], position[1]); 
			map = new daum.maps.Map(document.getElementById(id), { 
				center: pos, 
				level: 4, 
				mapTypeId: daum.maps.MapTypeId.ROADMAP 
			});
			var zoomControl = new daum.maps.ZoomControl();
			map.addControl(zoomControl, daum.maps.ControlPosition.RIGHT);
			var mapTypeControl = new daum.maps.MapTypeControl();
			map.addControl(mapTypeControl, daum.maps.ControlPosition.TOPRIGHT);

			daum.maps.event.addListener(map, "click", function(MouseEvent){
				var latLng = MouseEvent.latLng;
				var latitude = latLng.getLat();
				var longitude = latLng.getLng();
				if(mark != null)
					mark.setVisible(false);
				mark = new daum.maps.Marker({
					position : new daum.maps.LatLng(latitude, longitude) 
				});

				if(marker != null)
					marker.setVisible(false);
				
				mark.setMap(map);
				
				if(confirm("클릭하신 곳을 중심으로 지정하시겠습니까?")){
					document.getElementById("latitude").value = latitude;
					document.getElementById("longitude").value = longitude;
				}else{
					mark.setVisible(false);

					var lat = document.getElementById("latitude").value;
					var lng = document.getElementById("longitude").value;

					var po;
					if(lat == "" || lng == ""){
						po = new daum.maps.LatLng(37.537123, 127.005523);
					}
					else{
						po = new daum.maps.LatLng(lat, lng);
						mark = new daum.maps.Marker({
							position : po 
						});
						mark.setMap(map); 
					}
					map.setCenter(po);
					return;
				}
			});
		}
};

esp.page.reg.addStudyPlace = function() {
	var url = "/esp/study/place/add";
	var placeLatitude = $("#latitude").val();
	var placeLongitude = $("#longitude").val();
	var containerQuery = $("#place-container");
	var placeName = $("div.place-name", containerQuery).text();
	var placeAddr = $("div.place-addr", containerQuery).text();
	var data = {
			placeName : placeName,
			placeAddr : placeAddr,
			placeLongitude : placeLongitude,
			placeLatitude : placeLatitude
	};
	$.ajax({
		url : url,
		type : "POST",
		data : data,
		dataType : "text",
		error : function(jqXHR, textStatus, errorThrown) {
			alert("장소 등록 실패. 관리자에게 문의해주세요");
		},
		success : function(data, textStatus, jqXHR) {
			if (data === "error") {
				alert("장소 등록 실패. 관리자에게 문의해주세요");
			}
		}
	});
};

function getApiKey(){
	if(isTest == 1){
		return _LOCAL_API_KEY;
	}else if(isTest == 2){
		return _DNADEV_API_KEY;
	}else {
		return _DNA_API_KEY;
	}
}
/*
 * 주소 검색을 위한 function
 * */
function searchPosition(id){
	var query = $("#" + id + " .search-word").val();
	
	if(!query){
		alert("검색어를 입력해 주십시오");
		return;
	}
	
	pingSearch(query);
}

/*
 * 주소 검색을 위한 api 요청
 * */
function pingSearch(query){
	var oScript = document.createElement("script");
	oScript.type = "text/javascript";
	oScript.charset = "utf-8";		  
	oScript.src = addrToCoordApiAddr + _LOCAL_API_KEY  + "&output=json&callback=" + addrSearchCallback + "&q=" + encodeURI(query);	
    document.getElementsByTagName("head")[0].appendChild(oScript);
}
/*
 * 주소 검색 callback method
 * */
function pongSearch(data){
	var resultForm = $("#map-search-view").find("." + searchResultBody);
	resultForm.html("");

	if(!data.channel || data.channel.item.length <= 0){
		resultForm.html("검색 결과가 없습니다.");
		return ;
	}else{
		var i;
		var htmlResult = "";
		console.log(data.channel);
		for (i = 0; i < data.channel.item.length; i++){
			htmlResult += "<div class='addrResult'><a href='javascript:searchMark("
									+ data.channel.item[i].point_y
									+ "," 
									+ data.channel.item[i].point_x
									+ ")'>" 
									+ data.channel.item[i].title.replace(/&lt;/g,"\<").replace(/&gt;/g,"\>")
									+ "</div></div>";
		}
		resultForm.html(htmlResult);
		$(".addrResult").click(function(event){
			var selectedAddr = $("a",this).text();
			$(".place-infobar div.place-addr").text(selectedAddr);
		});
		
		var resultFormHeader = $("#map-search-view").find("." + searchResultHeader);
		resultFormHeader.html("<span class='addrResultH'>" 
				+ "주소검색 -" + "<span class='redNum'> " + i + "</span>건"  + "</span>");
	}
	
}
/*
 * 검색된 주소 클릭시 오른쪽 맵에 마커 표시
 * */
function searchMark(lat,lng){
	if(mark != null)
		mark.setVisible(false);
	var po = new daum.maps.LatLng(lat, lng);
	map.setCenter(po);

	if(marker != null)
		marker.setVisible(false);
	marker = new daum.maps.Marker({
		position: po
	});
	marker.setMap(map);

	$("#latitude").val(lat);
	$("#longitude").val(lng);
}

/*
 * 좌표를 통해서 주소 가져오는 function
 * */
function addAddress(lat, lng){
	var oScript = document.createElement('script');
	oScript.type ='text/javascript';
	oScript.charset ='utf-8';		  
	oScript.src = coordToAddrApiAddr + _LOCAL_API_KEY + 
				  '&latitude=' + lat + '&longitude=' + lng +
				  '&output=json&callback=addrSearch';
    document.getElementsByTagName('head')[0].appendChild(oScript);
}

/*
 * 좌표 - > 주소검색 callback
 * */
function addrSearch(data){
	$("#" + roadViewAddrHeader).text(data.fullName);
}
