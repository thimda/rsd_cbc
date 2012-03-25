
﻿//信息标题渲染
function HyperLinkRender() {}
 
HyperLinkRender.render = function(rowIndex, colIndex, value, header, cell, dsRowIndex) {
	cell.style.overflow = "hidden";
	cell.style.textOverflow = "ellipsis";
	cell.tip = value;
	
	var mainWidget=window.pageUI.getWidget('main');
    var ds=mainWidget.getDataset("signlog");
	var dsrow = ds.getRow(rowIndex);
	//var infId = dsrow.getCellValue(ds.nameToIndex("pk_signlog"));
	//var inftype = dsrow.getCellValue(ds.nameToIndex("infotype"));
	//alert(infId);
	//var detail = dsrow.getCellValue(ds.nameToIndex("detail"));
	var a = $ce("a");
	a.href = "javascript:void(0)";
	a.innerHTML = "详细信息";
	
	var pk_signlog =dsrow.getCellValue(ds.nameToIndex("pk_signlog"));
	
	a.onclick = function(e){
		if(e==null)
			stopAll(event);
		else
			stopAll(e);
		showInfCard(pk_signlog);
	}
	cell.appendChild(a);
};

﻿//栏目渲染
//function ColumnRender() {}
//
//ColumnRender.render = function(rowIndex, colIndex, value, header, cell) {
//	var mainWidget=window.pageUI.getWidget('main');
//    var ds=mainWidget.getDataset("signlog");
//	var dsrow = ds.getRow(rowIndex);
//	var grpName = dsrow.getCellValue(ds.nameToIndex("pk_group_name"));
//	var orgName = dsrow.getCellValue(ds.nameToIndex("pk_org_name"));
//	grpName = (grpName==null)?"":grpName;
//	orgName = (orgName==null)?grpName:orgName;
//	var text = (orgName=="")? value:value+"[" + orgName + "]";
//	cell.style.overflow = "hidden";
//	cell.style.textOverflow = "ellipsis";
//	cell.tip = text;
//	cell.appendChild(document.createTextNode(text));
//};

//弹出信息卡片
/*function showInfCard(infid,inftype){
	var proxy = new ServerProxy(null,null,false);
	proxy.addParam('clc', 'nc.uap.portal.ctrl.office.data.sign.InfPubListController' );
	proxy.addParam('m_n', 'onInfTitleLink');
	proxy.addParam("infid",infid);
	proxy.addParam("inftype",inftype);
	proxy.execute();	
}*/
function showInfCard(pk_signlog){
	var proxy = new ServerProxy(null,null,false);
	proxy.addParam('clc', 'nc.uap.portal.ctrl.office.data.sign.InfPubListController' );
	proxy.addParam('m_n', 'onInfTitleLink');
	proxy.addParam("pk_signlog",pk_signlog);
	proxy.execute();
}