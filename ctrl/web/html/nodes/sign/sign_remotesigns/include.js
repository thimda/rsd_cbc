function userDeleteRender() {
	}
userDeleteRender.render = function(rowIndex, colIndex, value, header, cell){
		cell.style.overflow = "hidden";
		cell.style.textOverflow = "ellipsis";
		cell.style.cursor = "default";
		cell.style.textAlign = "center";
		
		var grid = header.owner;
		var ds = grid.model.dataset;

		var pk_usersign = ds.getRow(rowIndex).getCellValue(ds.nameToIndex('pk_usersign'));
		//var rowid = ds.getRow(rowIndex).rowId;
		cell.innerHTML = "<a style='cursor:pointer;text-decoration:underline'  onclick='UserSignDelete(event,\""+ pk_usersign +"\")'>删除</a>";		
	}
function userDeleteRender() {
	}

	
function UserSignDelete(e,pk){
	stopEvent(e);
	var signuserds = pageUI.getWidget('main').getDataset("signuserlist");
	var rows = signuserds.getAllRows();
	if(rows){
		for(var i=0;i< rows.length;i++){
			var curpk = rows[i].getCellValue(signuserds.nameToIndex('pk_usersign'));
			if(curpk == pk){
				var index = signuserds.getRowIndex(rows[i]);
				signuserds.setRowSelected(index);
				break;
			}
		}
	}
	
	var proxy = new ServerProxy(null,null,false); //代理类
	proxy.addParam('clc', 'nc.uap.portal.ctrl.office.controller.SignListControl' );//控制类
	proxy.addParam('m_n', 'DeleteUserSign');//方法名
	proxy.addParam('pk',pk);//参数		
	proxy.execute();
	
	return false;
}
function signDeleteRender() {
	}
signDeleteRender.render = function(rowIndex, colIndex, value, header, cell){
		cell.style.overflow = "hidden";
		cell.style.textOverflow = "ellipsis";
		cell.style.cursor = "default";
		cell.style.textAlign = "center";
		
		var grid = header.owner;
		var ds = grid.model.dataset;

		var pk_usersign = ds.getRow(rowIndex).getCellValue(ds.nameToIndex('PK_sign'));
		//var rowid = ds.getRow(rowIndex).rowId;
		cell.innerHTML = "<a style='cursor:pointer;text-decoration:underline'  onclick='Signedit(event,\""+ pk_usersign +"\",\"delete\")'>删除</a>";		
	}
function signEditRender() {
	}
signEditRender.render = function(rowIndex, colIndex, value, header, cell){
		cell.style.overflow = "hidden";
		cell.style.textOverflow = "ellipsis";
		cell.style.cursor = "default";
		cell.style.textAlign = "center";
		
		var grid = header.owner;
		var ds = grid.model.dataset;

		var pk_usersign = ds.getRow(rowIndex).getCellValue(ds.nameToIndex('PK_sign'));
		//var rowid = ds.getRow(rowIndex).rowId;
		cell.innerHTML = "<a style='cursor:pointer;text-decoration:underline'  onclick='Signedit(event,\""+ pk_usersign +"\",\"edit\")'>编辑</a>";		
	}
/**
 * 
 * @param {} e
 * @param {} pk
 * @param {} type delete,edit,new
 * @return {Boolean}
 */
function Signedit(e,pk,type){
	stopEvent(e);
	var signuserds = pageUI.getWidget('main').getDataset("signlist");
	var rows = signuserds.getAllRows();
	if(rows){
		for(var i=0;i< rows.length;i++){
			var curpk = rows[i].getCellValue(signuserds.nameToIndex('PK_sign'));
			if(curpk == pk){
				var index = signuserds.getRowIndex(rows[i]);
				signuserds.setRowSelected(index);
				break;
			}
		}
	}
	
	var proxy = new ServerProxy(null,null,false); //代理类
	proxy.addParam('clc', 'nc.uap.portal.ctrl.office.controller.SignListControl' );//控制类
	proxy.addParam('m_n', 'EditSignEvent');//方法名
	proxy.addParam('pk',pk);//参数
	proxy.addParam('type',type);//参数
	
	// 设置提交父数据集的提交规则
	var rule = new SubmitRule();
	var wdr_parentWidget = new WidgetRule('main');	
	var dsr_signDataset = new DatasetRule('signlist', 'ds_all_line');
	var dsr_signuserDataset = new DatasetRule('signuserlist', 'ds_all_line');	
	wdr_parentWidget.addDsRule('signlist', dsr_signDataset);
	wdr_parentWidget.addDsRule('signuserlist', dsr_signuserDataset);
	rule.addWidgetRule('main', wdr_parentWidget);
	
	proxy.setSubmitRule(rule);
	
	proxy.execute();
	
	return false;
}