/**
 * ״̬����ʾ������Ϣ
 */
function setDescription(gridRowEvent){
	var comp = gridRowEvent.obj;
	var ds = comp.model.dataset;
	var widget = pageUI.getWidget("formulaeditor");
	var row = ds.getSelectedRow();
	var description = "";
	if(row!=null){
		description = row.getCellValue(1);
		var description_area = widget.getComponent("status_textarea");
	}	
	description_area.setValue(description);
}
/**
 * ˫���ֶμ���༭��ʽ�ı���
 */
function onFormulaDbClick(gridRowEvent){
	var comp = gridRowEvent.obj;
	var ds = comp.model.dataset;
	var widget = pageUI.getWidget("formulaeditor");
	var row = ds.getSelectedRow();
	var formula = "";
	if(row!=null){
		formula = row.getCellValue(0);
		var edit_area = widget.getComponent("edit_textarea");
	}	
	var oldvalue = edit_area.getValue();
	//edit_area.setValue(oldvalue+formula);
		
	var textarea = edit_area.textArea;
	textarea.focus();
	if(document.selection){
		var range = document.selection.createRange();
		alert(range);
		range.text = formula;
		
	}
	else{
		var index = textarea.selectionStart;
		edit_area.setValue(oldvalue.substring(0,index)+formula+oldvalue.substring(index,oldvalue.length));
	}
}

/**
 * ֵ��ť��������༭��ʽ�ı���
 */
function onValueBtnClick(mouseEvent){
	var btn = mouseEvent.obj;
	var value = btn.text;
	
	var widget = pageUI.getWidget("formulaeditor");
	var formula = "";
	if(value!=null){
		var edit_area = widget.getComponent("edit_textarea");
		var oldvalue = edit_area.getValue();
	}	
	//edit_area.setValue(oldvalue+value);
	var textarea = edit_area.textArea;
	textarea.focus();
	if(document.selection){
		var range = document.selection.createRange().text = value;
	}
	else{
		var index = textarea.selectionStart;
		edit_area.setValue(oldvalue.substring(0,index)+value+oldvalue.substring(index,oldvalue.length));
	}
}
/**
 * ��ȡ�༭��ʽ�ı���
 */
function getEditArea(){
	var widget = pageUI.getWidget("formulaeditor");
	var edit_area = widget.getComponent("edit_textarea");
	return edit_area;
}

/**
 * �༭��ʽ�ı���ȫѡ
 */
function allSelectEditArea(){
	var widget = pageUI.getWidget("formulaeditor");
	var edit_area = widget.getComponent("edit_textarea");
	var textarea = edit_area.textArea;
	textarea.select();
}