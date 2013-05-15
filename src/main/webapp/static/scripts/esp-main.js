if (!window.esp) {
	window.esp = new Object();
}

esp.user = new Object();

var esp = {
	adjustMainContainerHeight : function() {

	},
	pageInit : function(pageName) {
		if (!pageName) {
			return;
		}

		switch (pageName) {
		case 'main':
			esp.mainPageComponentInit();
			break;
		case 'reg':
			esp.regPageComponentInit();
			break;
		}
	}
};

esp.mainPageComponentInit = function() {
	esp.page.main.init.tooltip();
	esp.page.main.init.slider();
	esp.page.main.init.joinDialog();
};

esp.page = {
	main : new Object()
};

esp.page.main.init = {
	tooltip : function() {
		var imgQuery = $("#study-room img.preview");
		if (imgQuery.length === 0) {
			imgQuery = $("#no-study img.preview");
		}
		var imgSrc = imgQuery.attr("src");
		var imgPreviewContent = '<img src="' + imgSrc + '"/>';

		imgQuery.qtip({
			content : imgPreviewContent,
			show : 'mouseover',
			hide : 'mouseout',
			style : {
				classes : 'esp-qtip-custom'
			}
		});
		$("#study-room .da-link").qtip({
			content : '클릭시 스터디 메인 페이지 이동',
			show : 'mouseover',
			hide : 'mouseout',
			position : {
				at : 'bottom center'
			}
		});
	},
	slider : function() {
		$('#da-slider').cslider({
			current : 0,
			autoplay : false
		});
	},
	joinDialog : function() {
		$(".join-study").click(function(event) {
			var e = window.event || event;
			e.preventDefault();
			if (!esp.user) {
				alert("로그인 후 스터디에 참여 할 수 있습니다.");
				return;
			}
			$("#join-confirm-dialog").dialog({
				resizable : false,
				modal : true,
				height : 180,
				width : 306,
				buttons : {
					"수락" : function() {
						$(this).dialog("close");
						var url = "/esp/study/join";
						var studyId = $(
								"#study-room input[type='hidden']")
								.val();
						var sendData = {
							uid : esp.user.uid,
							studyId : studyId
						};
						$.ajax({
									url : url,
									type : "POST",
									dataType : "json",
									data : sendData,
									error : function(
											jqXHR,
											textStatus,
											errorThrown) {
										alert("스터디 참여 실패. 관리자에게 문의를...");
									},
									success : function(
											data,
											textStatus,
											jqXHR) {
										if (data == "success") {
											alert("스터디 참여에 성공 하였습니다.");
										} else if (data == "already") {
											alert("이미 참여 중입니다.");
										} else {
											alert("스터디 참여 실패. 관리자에게 문의를...");
										}
									}
								});
					},
					"취소" : function() {
						$(this).dialog("close");
					}
				}
			});
		});
	}
};

esp.regPageComponentInit = function() {
	esp.page.reg.init.datePicker();
	esp.page.reg.init.mapSearchDialog();
	esp.page.reg.init.imageUploader();
	esp.page.reg.init.submitBtnEvent();
};