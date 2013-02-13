if (window.esp) {
	window.esp = new Object();
}

var esp = {
	adjustMainContainerHeight : function() {
		
	},
	pageInit : function(pageName) {
		if (!pageName) {
			return;
		}
		
		switch(pageName) {
		case "reg" : 
			esp.regPageComponentInit();
			break;
		}
	}
};

esp.regPageComponentInit = function() {
	esp.page.reg.init.datePicker();
	esp.page.reg.init.mapSearchDialog();
	esp.page.reg.init.imageUploader();
};