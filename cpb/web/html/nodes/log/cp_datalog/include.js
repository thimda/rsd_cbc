
﻿//信息标题渲染
function HyperLinkRender() {}
 
HyperLinkRender.render = function(rowIndex, colIndex, value, header, cell, dsRowIndex) {
	cell.style.overflow = "hidden";
	cell.style.textOverflow = "ellipsis";
	cell.tip = value;
	
	var mainWidget=window.pageUI.getWidget('main');
    	var ds=mainWidget.getDataset("datalog");
	var dsrow = ds.getRow(rowIndex);
	var a = $ce("a");
	a.href = "javascript:void(0)";
	a.innerHTML = "详细信息";
	
	var pk_businesslog =dsrow.getCellValue(ds.nameToIndex("pk_businesslog"));
	
	a.onclick = function(e){
		if(e==null)
			stopAll(event);
		else
			stopAll(e);
		showInfCard(pk_businesslog);
	}
	cell.appendChild(a);
};

function showInfCard(pk_businesslog){
	var proxy = new ServerProxy(null,null,false);
	proxy.addParam('clc', 'nc.uap.cpb.log.ShowLogController' );
	proxy.addParam('m_n', 'onDataLogLink');
	proxy.addParam("pk_businesslog",pk_businesslog);
	proxy.execute();
}