function setTabText(tabname,itmname,title){
  var widget=pageUI.getWidget("main");
  var tab=widget.getTab(tabname);
  var item = tab.getItemByName(itmname);
  item.changeTitle(title);
}

function doPortletAction(arg0){
//	if(document._pt_container_id){
//		document.getContainer().doAction(arg0);
//	}else{
//		setTimeout("doPortletAction('"+arg0+"')",2000);
//	}
}