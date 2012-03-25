
function ekeyDeleteRender() {
	}
ekeyDeleteRender.render = function(rowIndex, colIndex, value, header, cell){
		cell.style.overflow = "hidden";
		cell.style.textOverflow = "ellipsis";
		cell.style.cursor = "default";
		cell.style.textAlign = "center";
		
		var grid = header.owner;
		var ds = grid.model.dataset;

		var pk_usersign = ds.getRow(rowIndex).getCellValue(ds.nameToIndex('pk_ekey'));
		//var rowid = ds.getRow(rowIndex).rowId;
		cell.innerHTML = "<a style='cursor:pointer;text-decoration:underline'  onclick='Ekeyedit(event,\""+ pk_usersign +"\",\"delete\")'>删除</a>";		
	}
function ekeyEditRender() {
	}
ekeyEditRender.render = function(rowIndex, colIndex, value, header, cell){
		cell.style.overflow = "hidden";
		cell.style.textOverflow = "ellipsis";
		cell.style.cursor = "default";
		cell.style.textAlign = "center";
		
		var grid = header.owner;
		var ds = grid.model.dataset;

		var pk_usersign = ds.getRow(rowIndex).getCellValue(ds.nameToIndex('pk_ekey'));
		//var rowid = ds.getRow(rowIndex).rowId;
		cell.innerHTML = "<a style='cursor:pointer;text-decoration:underline'  onclick='Ekeyedit(event,\""+ pk_usersign +"\",\"edit\")'>编辑</a>";		
}
/**
 * 
 * @param {} e
 * @param {} pk
 * @param {} type delete,edit,new
 * @return {Boolean}
 */
function Ekeyedit(e,pk,type){
	
	
	stopEvent(e);
	var signuserds = pageUI.getWidget('main').getDataset("ekeyds");
	var rows = signuserds.getAllRows();
	if(rows){
		for(var i=0;i< rows.length;i++){
			var curpk = rows[i].getCellValue(signuserds.nameToIndex('pk_ekey'));
			if(curpk == pk){
				var index = signuserds.getRowIndex(rows[i]);
				signuserds.setRowSelected(index);
				break;
			}
		}
	}
	
	var proxy = new ServerProxy(null,null,false); //代理类
	proxy.addParam('clc', 'nc.uap.portal.ctrl.office.controller.EKeyControl' );//控制类
	proxy.addParam('m_n', 'EkeyEditEvent');//方法名
	proxy.addParam('pk',pk);//参数
	proxy.addParam('type',type);//参数
	
	// 设置提交父数据集的提交规则
	var rule = new SubmitRule();
	var wdr_parentWidget = new WidgetRule('main');	
	var dsr_signDataset = new DatasetRule('ekeyds', 'ds_all_line');
		
	wdr_parentWidget.addDsRule('ekeyds', dsr_signDataset);
	
	rule.addWidgetRule('main', wdr_parentWidget);
	
	proxy.setSubmitRule(rule);
	
	proxy.execute();
	
	return false;
}


/**印章控件*****************************************************************************************************/
/**
 * 印章控件
 */
function SignControlComp() {	
// 自身创建方法
	this.createSelf();
	// 校验失败提示内容
	this.failedHTML = "<div style=\"color:red;font-size:18px;\">*</div>";
		//印章控件
	this.signctr = null;
	//印章来源 local,remote,ekey,none
	this.signsrctype = "none";
	//印章状态 none,open, create, edit
	this.signstatus = "none";
	//打开地址
	this.url = "";
	//pk
	this.signpk = "";
	//delete,edit,new,ekeynew,ekeyedit,view
	this.editorType = "";
	require("inputdialog", function(){});
};
/**
 * 创建自身
 */
SignControlComp.prototype.createSelf = function() {
	// 主DIV
	this.mainDiv = $ce("DIV");
	this.mainDiv.style.width = "0";
	this.mainDiv.style.height = "0";
	this.mainDiv.style.overflow = "auto";
	this.mainDiv.style.display = "none";
	this.signDiv = $ce("DIV");//容器
	this.signDiv.ID = "signdiv";	
	this.mainDiv.appendChild(this.signDiv);
	
}
/**
 * 返回主DIV
 */
SignControlComp.prototype.getMainDiv = function() {
	return this.mainDiv;
};
SignControlComp.prototype.getSelfCtx = function() {
	// 创建Context对象
	var ctx = new Object;
	ctx.javaClass = "";
	ctx.focusItemId = this.focusItemId;
	return ctx;
};
SignControlComp.prototype.setSelfCtx = function(ctx) {
	if(ctx){
		
	}
};

//初始化控件
SignControlComp.prototype.InitControl=function(){	
	this.signctr = new SignControl({
		TargetElement:"signdiv",
		TargetElementDiv:this.signDiv,
		userName:window.usercode,
		rootpath:window.globalPath,
		width:"280px"
	});	
}
SignControlComp.prototype.getEkeySN = function(){
	return this.signctr.EkeySN();
}
SignControlComp.prototype.StatusCode = function(){
	return this.signctr.StatusCode();
}
SignControlComp.prototype.ChangeEkeyPassword = function(oldpas,newpas,isadmin){
	this.signctr.ChangeEkeyPassword(oldpas,newpas,isadmin);
}
SignControlComp.prototype.ResetEkeyUserPassword = function(adminPass,newUserPass){
	this.signctr.ResetEkeyUserPassword(adminPass,newUserPass);
}
SignControlComp.prototype.DeleteFromEkey = function(){
	this.signctr.DeleteFromEkey();
	if(this.StatusCode() == 0){
		signErrorShowMessage("删除成功");
	}
	else{
		signErrorShowMessage("删除失败");
	}
}
SignControlComp.prototype.ResetEkeySigns = function(){
	this.signctr.ResetEkeySigns();
	if(this.StatusCode() == 0){
		signErrorShowMessage("清除成功");
	}
	else{
		signErrorShowMessage("清除失败");
	}
}
SignControlComp.prototype.ShowChangePsw = function(isadmin,isreset){
	if(isreset){
		this.PSWDialog = new InputDialogComp("userdialog", "重置用户口令", 200, 200, 500, 200, 150, this.ChangePSWOk, this.ChangePSWCancel, this, null, 10001, null)
	}
	else{
		if(isadmin){
			this.PSWDialog = new InputDialogComp("userdialog", "修改管理员口令", 200, 200, 500, 230, 150, this.ChangePSWOk, this.ChangePSWCancel, this, null, 10001, null)
		}
		else{
			this.PSWDialog = new InputDialogComp("userdialog", "修改用户口令", 200, 200, 500, 200, 150, this.ChangePSWOk, this.ChangePSWCancel, this, null, 10001, null)
		}
	}
	this.isResetpsw =  isreset;
	this.ChangeIsAdminpsw = isadmin; 
	this.PSWDialog.create();
	if(isreset){
		this.PSWDialog.addItem("管理员口令", "oldpswtxt", InputDialogComp.PSWTEXT_TYPE, true, null, "");
		this.PSWDialog.addItem("新用户口令[4-16位]", "newpswtxt", InputDialogComp.PSWTEXT_TYPE, true, null, "");
		this.PSWDialog.addItem("确认新用户口令", "confirmnewpswtxt", InputDialogComp.PSWTEXT_TYPE, true, null, "");	
	}
	else{
		if(isadmin)
			this.PSWDialog.addItem("注意:请务必牢记管理员访问口令!如果丢失只能报废EKEY!", "warnlabel", InputDialogComp.LABEL_TYPE, false, null, null,"signwarn");
		this.PSWDialog.addItem("旧口令", "oldpswtxt", InputDialogComp.PSWTEXT_TYPE, true, null, "");
		this.PSWDialog.addItem("新口令[4-16位]", "newpswtxt", InputDialogComp.PSWTEXT_TYPE, true, null, "");
		this.PSWDialog.addItem("确认新口令", "confirmnewpswtxt", InputDialogComp.PSWTEXT_TYPE, true, null, "");
	}
	this.PSWDialog.show();
}

SignControlComp.prototype.ChangePSWOk=function(){
	try{
		var oldpsw = this.PSWDialog.getItem("oldpswtxt").getValue();
		var newpsw = this.PSWDialog.getItem("newpswtxt").getValue();
		var confirmpsw = this.PSWDialog.getItem("confirmnewpswtxt").getValue();
		if(this.isResetpsw){
			if(!oldpsw || oldpsw == ""){
				signErrorShowMessage("管理员密码不能为空");
				return false;
			}
		}
		if(!newpsw){
			signErrorShowMessage("新密码不能为空");
			return false;
		}
		if(newpsw.length < 4 || newpsw.length > 16){
			signErrorShowMessage("新密码必须在4-16位之间");
			return false;
		}
		if(newpsw != confirmpsw){
			signErrorShowMessage("两次输入密码不一致");
			return false;
		}
		if(this.isResetpsw){
			this.ResetEkeyUserPassword(oldpsw,newpsw);
		}
		else
			this.ChangeEkeyPassword(oldpsw,newpsw,this.ChangeIsAdminpsw);
		if(this.StatusCode() == 0){
			signErrorShowMessage("密码修改成功");
			this.PSWDialog.hide();	
		}
		else{
			signErrorShowMessage("密码修改失败");
			return false;
		}
		
	}
	catch(err){
		signErrorShowMessage(err);
		return false;
	}
	return true;
}
SignControlComp.prototype.ChangePSWCancel=function(){
}

function setEkeySntotext(){
	var editwd = pageUI.getWidget('edit');
	var signcmp = editwd.getComponent("selfsign");
	var sn = signcmp.contentObj.getEkeySN();
	//var sntext =
	//var formcmp = editwd.getComponent("ekeyform");
	//var snelem = formcmp.getElement("sn");
	var ekeyds = editwd.getDataset("ekeyds");
	var row = ekeyds.getSelectedRow();
	if(row){
		var cursn = ekeyds.getValue();
		var btnok = editwd.getComponent("btn_ok");
		
		if(!sn){
			btnok.setActive(false);
			ekeyds.setValue("sn","");
			signErrorShowMessage("请插入EKey");
			return;
		}
		
		if( cursn && cursn != sn){
			btnok.setActive(false);
			ekeyds.setValue("sn","");
			signErrorShowMessage("当前数据的EKEY没有被插入，请确认");
			return;
		}
		else{
			ekeyds.setValue("sn",sn);
		}
	}
}

function signErrorShowMessage(msg){
	//alert(msg);
	//require('errordialog', function(){ErrorDialogComp.showDialog(msg)});
	showErrorDialog(msg);
}
function signShowMessage(msg){
	showMessageDialog(msg,200);
}


