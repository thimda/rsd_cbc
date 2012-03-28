function sysinitCellEditor(parent, row, colIndex){
	var mainWidget = window.pageUI.getWidget('main');
    var ds = mainWidget.getDataset("cp_sysinit_ds");
    var valuelists = ds.getSelectedRow().getCellValue(ds.nameToIndex("valuelist"));
    var value = ds.getSelectedRow().getCellValue(ds.nameToIndex("value"));
	var comp = null;
	// 除了“/”,","为下拉，其它都为文本框
	if(!(valuelists.split("/").length>1||valuelists.split(",").length>1)){
		comp = new StringTextComp(parent, "text", 0, 0, "100%","absolute", "", "");
	}
	return comp;
}
