function setTabItemURL(tabId, itemIndex, url,name) {
	var widget=pageUI.getWidget("main");
	var tab_view=widget.getTab(tabId)
	var iframe=document.createElement("iframe");
	iframe.src=url;
	iframe.id=name;
	iframe.width="100%";
	iframe.height="100%";
	iframe.style.overflowY="auto";
	var itms=tab_view.getTabItems();
	var old= itms[itemIndex].getObjHtml().childNodes[0].childNodes[0];
  	if(old!=null){
       itms[itemIndex].getObjHtml().childNodes[0].removeChild(old);
  	}
	itms[itemIndex].getObjHtml().childNodes[0].appendChild(iframe);
}
function addAttachFileDs(pk,name,filesize,filetype,uploadtime,pk_user,username){	
	 var widget=pageUI.getWidget('main');
	 var dsUploader=widget.getDataset('ds_attachfile');
	 var row=dsUploader.getEmptyRow();
	 row.setCellValue(dsUploader.nameToIndex("pk_file"),pk);
	 row.setCellValue(dsUploader.nameToIndex("displayname"),name);
	 row.setCellValue(dsUploader.nameToIndex("filesize"),filesize);
	 row.setCellValue(dsUploader.nameToIndex("uploadtime"),uploadtime);
	 row.setCellValue(dsUploader.nameToIndex("creator"),pk_user);
	 row.setCellValue(dsUploader.nameToIndex("creator_username"),username);
	 dsUploader.addRow(row);
	 var formdataWidget= pageUI.getWidget('main').getComponent('iframe_formdata').frame.contentWindow.pageUI.getWidget('main');
	 var attachComp=formdataWidget.getComponent("attachment");
	 if(typeof(attachComp)=='object'){
	 	var rows=dsUploader.getAllRows();
	 	 var value="";
	 	  if(rows!=null){
	 	  	for(var i=0;i<dsUploader.getRowCount();i++){
	 	  		if(""==value){
	               value= rows[i].getCellValue(dsUploader.nameToIndex("filename"));
	 	         }else{
	 	         	value=value+","+rows[i].getCellValue(dsUploader.nameToIndex("filename"));
	 	         }
	 	  	}
	 	  }
	 	   attachComp.setValue(value);
	 }
}
function beforeCallServer(proxy, listenerId, eventName, eleId){
	    var iframe = document.getElementById('iframe_word');
		if(iframe != null&&listenerId=='pwfmlistener'){
	        iframe.contentWindow.saveFileToURL();
	    }
}


function EleNameRender() {
};
EleNameRender.render = function(rowIndex, colIndex, value, header, cell) {
    if(value==null||value==''){
       return ;
    }
	cell.style.overflow = "hidden";
	cell.style.textOverflow = "ellipsis";
	cell.style.cursor = "default";
	if (header.textAlign != null && header.textAlign != "")
		cell.style.textAlign = header.textAlign;
	else	
		cell.style.textAlign = "right";
	cell.innerHTML = "<img src='" + window.globalPath + "/pt/file/down?id=" + value + "'/>";
};
