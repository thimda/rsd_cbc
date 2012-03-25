function setXmltoWorkflow(xmlString){
		//parent.setXmltoWorkflow(xmlString);
		var pwin = window.opener;
		if(pwin == null)
			pwin = window.dialogArguments;
		var success = pwin.setXmltoWorkflow(xmlString);
		return success;
}

function getUrl(){
	//alert(1);
//alert(window.location.href);
   var url = window.location.href;
   return url;
   }

function getServerWebRoot(){
	var url = window.location;
	var protocol = url.protocol;
	var host = url.host;
	var fullUrl = protocol + "//" + host;
	fullUrl += webContextPath;
	//fullUrl = protocol + "//" + host + "/portal";
	return fullUrl;
} 

function savePortletXml(xmlString){
		parent.saveXml(xmlString);
}

function openWindow(url){
	 window.open(url,"_blank","height = 768,width = 1024");
}