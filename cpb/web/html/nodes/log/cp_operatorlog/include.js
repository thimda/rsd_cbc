

﻿//信息标题渲染
function HyperLinkRender() {}
 
HyperLinkRender.render = function(rowIndex, colIndex, value, header, cell, dsRowIndex) {
	cell.style.overflow = "hidden";
	cell.style.textOverflow = "ellipsis";
	cell.tip = value;
	
	var mainWidget=window.pageUI.getWidget('main');
    	var ds=mainWidget.getDataset("operatorlog");
	var dsrow = ds.getRow(rowIndex);
	var a = $ce("a");
	a.href = "javascript:void(0)";
	a.innerHTML = "详细信息";
	
	var pk_funcoperlog =dsrow.getCellValue(ds.nameToIndex("pk_funcoperlog"));
	
	a.onclick = function(e){
		if(e==null)
			stopAll(event);
		else
			stopAll(e);
		showInfCard(pk_funcoperlog);
	}
	cell.appendChild(a);
};

function showInfCard(pk_funcoperlog){
	var proxy = new ServerProxy(null,null,false);
	proxy.addParam('clc', 'nc.uap.cpb.log.ShowLogController' );
	proxy.addParam('m_n', 'onOperatorLogLink');
	proxy.addParam("pk_funcoperlog",pk_funcoperlog);
	proxy.execute();
}