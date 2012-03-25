function beforeCallServer(proxy, listenerId, eventName, eleId){
	if(listenerId=='pwfmlistener'&&eleId=='btn_ok'){
	     var mainWidget=window.pageUI.getWidget('main');
	     var btnOk = mainWidget.getComponent(eleId);
	     // btnOk.setActive(false);
	}
}
function EleNameRender() {};
EleNameRender.render = function(rowIndex, colIndex, value, header, cell) {
	cell.style.overflow = "hidden";
	cell.style.textOverflow = "ellipsis";
	cell.style.cursor = "default";
	if (header.textAlign != null && header.textAlign != "")
		cell.style.textAlign = header.textAlign;
	else	
		cell.style.textAlign = "right";
    var mainWidget=window.pageUI.getWidget('main');
    var ds=mainWidget.getDataset("ds_nexthumact");
	var rowId = ds.getRow(rowIndex).rowId;
	if(value=='true'){
	   cell.innerHTML = "<input type='button' value='选人' style='width:80px' onclick=assign(\'"+rowId+"\')>";
	}else{
	   cell.innerHTML = "<input type='button' value='选人' style='width:80px'  disabled='disabled'>";
	}	
};
function assign(rowId){
	 var proxy = new ServerProxy(null,null,true);
	 proxy.addParam('clc', 'nc.uap.wfm.next.WfmNextScriptCtrl');
     proxy.addParam('m_n', 'handlerEvent');
 	 var sbr = new SubmitRule();  
 	 var wdr_main = new WidgetRule('main');
 	 var widget = pageUI.getWidget('main');
 	 var datasets=widget.getDatasets();
 	 if(datasets!=null){
 	   for(var i=0;i<datasets.length;i++){
 	      var tmpDataset=datasets[i];
 	      var datasetId=tmpDataset.id;
 	      var ds_rule = new DatasetRule(datasetId, 'ds_all_line');
 	      wdr_main.addDsRule(datasetId, ds_rule);
 	   }
 	 }
 	 sbr.addWidgetRule('main', wdr_main);
	 proxy.setSubmitRule(sbr); 
	 proxy.execute();

}
