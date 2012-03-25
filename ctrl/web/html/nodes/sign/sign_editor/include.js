
/**
 * 创建自定义控件
 * 该对象必须实现：
 * 1.getMainDiv() 方法，返回最外层DIV
 * 2.getSelfCtx() 方法，获取其自定义的Context对象，Context对象必须包括javaClass属性
 * 	例如：	var otherCtx = new Object;
			otherCtx.javaClass = "nc.vo.personvo";
 * 3.setSelfCtx(otherCtx) 方法，设置其自定义的Context对象
 * 
 */
 /**文件选择控件*****************************************************************************************************/
function FileControlComp() {	
	// 自身创建方法
	this.createSelf();
	// 校验失败提示内容
	this.failedHTML = "<div style=\"color:red;font-size:18px;\">*</div>";
};
/**
 * 创建自身
 */
FileControlComp.prototype.createSelf = function() {
	// 主DIV
	this.mainDiv = $ce("DIV");
	this.mainDiv.style.width = "100%";	
	this.titleDiv = $ce("DIV");
	this.titleDiv.setAttribute("style","width:49px;height:23px;float:left;position:relative");
	
	this.titleDiv.title = "  源文件";	
	this.titleDiv.setAttribute("class","label_div");
	this.titleDiv.appendChild(document.createTextNode("源文件"))
	this.mainDiv.appendChild(this.titleDiv);
	
	
	this.textdiv = $ce("DIV");
	this.textdiv.setAttribute("style","width:180px;height:20px;float:right;position:relative");
	this.textdiv.setAttribute("class","text_div");
	this.FileCtr = $ce("input");
	this.FileCtr.style.width = "100%";
	this.FileCtr.style.height = "100%";
	//this.FileCtr.type = "file";
	this.FileCtr.setAttribute("type","file");
	this.FileCtr.setAttribute("accept","image/jpg,image/bmp,image/jpeg");
	this.textdiv.appendChild(this.FileCtr);
	
	this.mainDiv.appendChild(this.textdiv);
}

/**
 * 返回主DIV
 */
FileControlComp.prototype.getMainDiv = function() {
	return this.mainDiv;
};
FileControlComp.prototype.getSelfCtx = function() {
	// 创建Context对象
	var ctx = new Object;
	// 自定义对象的对应Java类，必须包含该属性！
	ctx.javaClass = "";
	ctx.focusItemId = this.focusItemId;
	var filename = "";
	if(this.FileCtr)
		filename = this.FileCtr.value ;
	ctx.fileName = filename;
	return ctx;
};
FileControlComp.prototype.setSelfCtx = function(ctx) {
	if(ctx){
		var filename = ctx.filename;
		if(filename == undefined)
			filename = "";
		//this.FileCtr.value = filename;
		this.FileCtr = $ce("input");
		this.FileCtr.style.width = "100%";
		this.FileCtr.style.height = "100%";
		//this.FileCtr.type = "file";
		this.FileCtr.setAttribute("type","file");
		this.FileCtr.setAttribute("accept","image/jpg,image/bmp,image/jpeg");
		this.FileCtr.setAttribute("value",filename);
		this.textdiv.innerHTML = "";
		this.textdiv.appendChild(this.FileCtr);
	}
};
FileControlComp.prototype.getFileName = function() {
	var filename = "";
	if(this.FileCtr)
		filename = this.FileCtr.value ;
	return filename;
}

 /**文件form控件*****************************************************************************************************/
function SignFormComp() {	
	// 自身创建方法
	this.createSelf();
	// 校验失败提示内容
	this.failedHTML = "<div style=\"color:red;font-size:18px;\">*</div>";
};
/**
 * 创建自身
 */
SignFormComp.prototype.createSelf = function() {
	// 主DIV
	this.mainDiv = $ce("DIV");
	this.mainDiv.style.width = "0";	
	this.mainDiv.style.display = "none";
	var innerhtml = '';
	innerhtml += '<form id="signform" method="post" enctype="multipart/form-data" action="'
			+ window.globalPath +'/pt/file/upload?;jsessionid='
			+ window.JSessionID + '">'				 
       		+ '<input type="hidden" id="billtype" name="billtype" value="OfficeSign">'
       		+ '<input type="hidden" id ="billitem" name="billitem" value="">'
       		+ '<input type="hidden" id ="category" name="category" value="">'
       		+ '<input type="hidden" id ="formusercode" name="formusercode" value="">'
       		+ '<input type="hidden" id ="formsignname" name="formsignname" value="">'
       		+ '<input type="hidden" id ="formsignno" name="formsignno" value="">'
       		+ '<input type="hidden" id ="iscover" name="iscover" value="">'
       		+ '<input type="hidden" id ="filepk" name="filepk" value="">'
       		+ '<input type="hidden" id ="extendclass" name="extendclass" value="nc.uap.portal.ctrl.office.action.SignUploadExtender">'
			+ '</form>'
	this.mainDiv.innerHTML = innerhtml;
}

/**
 * 返回主DIV
 */
SignFormComp.prototype.getMainDiv = function() {
	return this.mainDiv;
};
SignFormComp.prototype.getSelfCtx = function() {
	// 创建Context对象
	var ctx = new Object;
	// 自定义对象的对应Java类，必须包含该属性！
	ctx.javaClass = "";
	ctx.focusItemId = this.focusItemId;
	return ctx;
};
SignFormComp.prototype.setSelfCtx = function(ctx) {
};


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
	this.filepk = "";
	//delete,edit,new,ekeynew,ekeyedit,view
	this.editorType = "";
};
/**
 * 创建自身
 */
SignControlComp.prototype.createSelf = function() {
	// 主DIV
	this.mainDiv = $ce("DIV");
	this.mainDiv.style.width = "100%";
	this.mainDiv.style.height = "100%";
	this.mainDiv.style.overflow = "auto";
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
	this.ChangeStatus("none","none");
}
//重置界面
SignControlComp.prototype.reset=function(){
	// 主DIV
	$(this.mainDiv).html("");
	this.signDiv = $ce("DIV");//容器
	this.signDiv.ID = "signdiv";	
	this.mainDiv.appendChild(this.signDiv);
	
	this.InitControl();
	var mainWidget = pageUI.getWidget("main");
	setcmpValue(mainWidget,"textuser",window.usercode);
	setcmpValue(mainWidget,"textname","");
	setcmpValue(mainWidget,"textpsw","");
	setcmpValue(mainWidget,"textsn","");
	
	var context = new Object;
	context.otherCtx = new Object;
	context.otherCtx.filename = "";
	var filecmp = 	mainWidget.getComponent("selffile");
	filecmp.setContext(context);
}
//修改当前控件状态
SignControlComp.prototype.ChangeStatus=function(srctype,status){
	this.signsrctype = srctype;
	this.signstatus = status;
	if(this.signstatus == "none"||this.signstatus == "open"){
		setmenuActive("remoteMenu",false,"saveRemoteMenu");
		//setmenuActive("localMenu",false,"saveLocalMenu");
		setmenuActive("ekeyMenu",false,"saveEkeyMenu");
	} 
	else if(this.signstatus == "create"){
		setmenuActive("remoteMenu",true,"saveRemoteMenu");
		//setmenuActive("localMenu",true,"saveLocalMenu");
		setmenuActive("ekeyMenu",true,"saveEkeyMenu");
	} 
	else if(this.signstatus == "edit" ){
		if(this.signsrctype == "local"){
			setmenuActive("remoteMenu",false,"saveRemoteMenu");
			//setmenuActive("localMenu",true,"saveLocalMenu");
			setmenuActive("ekeyMenu",false,"saveEkeyMenu");
		} 
		else if(this.signsrctype  == "remote"){
			setmenuActive("remoteMenu",true,"saveRemoteMenu");
			//setmenuActive("localMenu",false,"saveLocalMenu");
			setmenuActive("ekeyMenu",false,"saveEkeyMenu");
		}
		else if(this.signsrctype  == "ekey"){
			setmenuActive("remoteMenu",false,"saveRemoteMenu");
			//setmenuActive("localMenu",false,"saveLocalMenu");
			setmenuActive("ekeyMenu",true,"saveEkeyMenu");
		}
		else if(this.signsrctype =="none"){
			setmenuActive("remoteMenu",false,"saveRemoteMenu");
			//setmenuActive("localMenu",false,"saveLocalMenu");
			setmenuActive("ekeyMenu",false,"saveEkeyMenu")
		}
	}
}
/**
*
*	生成印章
*/
SignControlComp.prototype.GenSign=function(){
	
	var mainWidget = pageUI.getWidget("main");
	try{
		var signname = getcmpValue(mainWidget,"textname","印章名称不可为空");
		var usercode = getcmpValue(mainWidget,"textuser","用户编码不可为空");
		var signpsw = getcmpValue(mainWidget,"textpsw","印章密码不可为空");
		//var confirmpsw = getcmpValue(mainWidget,"testsn","确认密码不可为空");
		if(signpsw.length < 6){signErrorShowMessage("印章密码需大于六位");return;}
		//if(signpsw != confirmpsw){signErrorShowMessage("两次密码输入不一致");return;}
		
		var filecmp = 	mainWidget.getComponent("selffile");		
		var srcfile = filecmp.contentObj.getFileName();
		if(srcfile == null || srcfile == undefined || srcfile == ""){
			signErrorShowMessage("源文件不能为空"); return;
		}
		if(this.signctr){
			this.signctr.CreateNew(signname,usercode,signpsw,srcfile);
	        if (0 != this.signctr.StatusCode()) {
                throw("创建印章错误.");		             
            }
            if(this.signsrctype == "none")
            	this.ChangeStatus(this.signsrctype,"create");
            else
            	this.ChangeStatus(this.signsrctype,"edit");
            
            setcmpValue(mainWidget,"textsn",this.signctr.SignSN());
            setButtonEnable("btnok",true);	
		}
	}
	catch(ex){
		signErrorShowMessage(ex);
	}
}
SignControlComp.prototype.SaveSign=function(){
	//服务器印章
	if(this.editorType == "edit" || this.editorType == "new"){
		this.saveRemoteSign();
	}
	else if(this.editorType == "ekeyedit" || this.editorType == "ekeynew"){
		var mainWidget = pageUI.getWidget("main");
		var signcmp = 	mainWidget.getComponent("selfsign");
		var ret = signcmp.contentObj.SaveToEkey(-1,true);
		if(ret == -1){
			signErrorShowMessage("保存失败");
		}
		else{
			signErrorShowMessage("保存成功");
			parent.hideDialog();
		}
	}
}
SignControlComp.prototype.SaveToEkey = function(Index, IsPromptOverwrite){
		return this.signctr.SaveToEkey(Index, IsPromptOverwrite);
}
SignControlComp.prototype.OpenFromEkey = function(){
		this.signctr.OpenFromEkey();
		if(this.signctr.StatusCode() == 0){
			this.ChangeStatus("ekey","open");
			this.synSigntoForm();
			setButtonEnable("btngen",true);
		}
}
//保存签章到服务器
SignControlComp.prototype.saveRemoteSign = function(){
	try{
		if(this.signctr){
			var signname = this.signctr.SignName();
			var uploadform = $("#signform");			
			var billitem;
			if(this.signpk)
				billitem = this.signpk;
			else
				billitem = this.getnewPK();
			$("#formusercode").val(this.signctr.SignUser());
			$("#formsignname").val(this.signctr.SignName());
			$("#formsignno").val(this.signctr.SignSN());
			$("#billitem").val(billitem);
			
			var ret = this.signctr.SaveToUrl(uploadform.attr("action"),signname + ".sign","",signname+ ".sign","signform");
			if (0 != this.signctr.StatusCode()) {
				throw "保存印章出错";
			}
			if(this.signctr.StatusCode() == 0){
				this.ChangeStatus("remote","open");
				var proxy = new ServerProxy(null,null,false); //代理类
				proxy.addParam('clc', 'nc.uap.portal.ctrl.office.controller.SignEditControl' );//控制类
				proxy.addParam('m_n', 'SaveSignEvent');//方法名
				proxy.addParam('pk',billitem);//参数		
				proxy.execute();
				//signShowMessage("保存成功");
			}
			else{signErrorShowMessage("保存失败");}
		}
		else{
			signErrorShowMessage("无法检测到签章控件，请检查浏览器环境");					
		}
	}
	catch(ex){
		signErrorShowMessage(ex);
	}
}
/**
 * 生成新的pk
 * @return {}
 */
SignControlComp.prototype.getnewPK=function(){
	var newpk = "";
	$.ajax({
	    type:"GET",
	    url:window.globalPath +'/pt/office/getnewpk',
	    data:'',
	    async:false,
	    success:function(req){
	    	newpk = req;
	    },                        
	    error:function(req){
	    	throw "获取单据pk失败,"+ req
	    },
	    cache:false
	    });
    return newpk;
}
SignControlComp.prototype.openSignFormurl=function(filepk,sign_pk){
	if(this.signctr){
		showloading(true);
		var url = window.globalPath + "/pt/doc/file/down?id=" + filepk;
		this.signctr.OpenFromUrl(url);				
		if(this.signctr.StatusCode() == 0){
			this.ChangeStatus("remote","open");
			this.synSigntoForm();
			setButtonEnable("btngen",true);
			this.signpk = sign_pk;
			this.filepk = filepk;
		}
		
		showloading(false);
	}
}
//同步印章数据到form
SignControlComp.prototype.synSigntoForm = function(){
	if(this.signstatus != "none"){
		var mainWidget = pageUI.getWidget("main");
		setcmpValue(mainWidget,"textname",this.signctr.SignName());
		setcmpValue(mainWidget,"textuser",this.signctr.SignUser());
		setcmpValue(mainWidget,"textpsw",this.signctr.Password());
		setcmpValue(mainWidget,"textsn",this.signctr.SignSN());
	}
}
/**
 * delete,edit,new,ekeynew,ekeyedit,view
 * @param {} editor
 */
SignControlComp.prototype.seteditType = function(editor){
	this.editorType = editor;
	if(this.editorType == "edit" || this.editorType == "ekeyedit"){//服务器印章编辑
		setButtonEnable("btnok",false);
		setButtonEnable("btngen",false);
	}
	else if(this.editorType == "new" || this.editorType == "ekeynew"){
		setButtonEnable("btnok",false);
		setButtonEnable("btngen",true);
	} 
	else {
		setButtonEnable("btnok",false);
		setButtonEnable("btngen",false);
	}
}
/***公共函数*****************************************************************************************************/

function openRemoteSign(filepk,signpk){
	var mainWidget = pageUI.getWidget("main");
	var signcmp = 	mainWidget.getComponent("selfsign");
	signcmp.contentObj.openSignFormurl(filepk,signpk);
}
function openFromEkey(){
	var mainWidget = pageUI.getWidget("main");
	var signcmp = 	mainWidget.getComponent("selfsign");
	signcmp.contentObj.OpenFromEkey();
}
function seteditType(){
	var mainWidget = pageUI.getWidget("main");
	var signcmp = 	mainWidget.getComponent("selfsign");
	var edittype = getSessionAttribute("edittype");
	signcmp.contentObj.seteditType(edittype);
	
}

//设置菜单状态
function setmenuActive(itemname,isactive,childItem){
		return;
		var mainWidget = pageUI.getWidget("main");
		var menubar =  mainWidget.getMenu("signmenu");
		var menu = menubar.getMenu(itemname);
		if(childItem != null && childItem != "")
			menu = menu.getMenu(childItem);
		menu.setActive(isactive);
}
function setButtonEnable(btnname,isenable){
	var mainWidget = pageUI.getWidget("main");
	var btn =  mainWidget.getComponent(btnname);
	if(btn){
		btn.setActive(isenable);
	}
}

//设置lfw控件属性
function setcmpValue(widget,cmpname,value){
	var cmp =  widget.getComponent(cmpname);
	cmp.setValue(value);
}

//显示message
function signErrorShowMessage(msg){
	//alert(msg);
	//require('errordialog', function(){ErrorDialogComp.showDialog(msg)});
	showErrorDialog(msg);
}
function signShowMessage(msg){
	showMessageDialog(msg,200);
}

function showloading(ishow){
	if(ishow)
		showDefaultLoadingBar();
	else
		hideDefaultLoadingBar();
}
function confirmmsg(msg){
	var ret =  confirm(msg);
	return ret;
}
//获取lfw控件属性
function getcmpValue(widget,cmpname,msg){
	var cmp =  widget.getComponent(cmpname);
	var value = cmp.getValue();
	if(value == null || value == undefined || value == ""){
		throw msg;	
	}
	return value
}