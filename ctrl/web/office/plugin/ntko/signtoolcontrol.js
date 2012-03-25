var SignControl;
if (SignControl == undefined) {
	SignControl = function (settings) {
		this.initOfficeControl(settings);
		this.load();
	};
}

SignControl.prototype.initOfficeControl = function (userSettings) {
	try {
		this.customSettings = {};
		this.settings = {};
		//初始化参数
		this.initSettings(userSettings);
	} catch (ex) {		
		throw ex;
	}
};

//初始化数据
SignControl.prototype.initSettings = function (userSettings) {
	this.ensureDefault = function (settingName, defaultValue) {
		var setting = userSettings[settingName];
		if (setting != undefined) {
			if (typeof(setting) === "object" && (setting instanceof Array)) {
				var clone = {};
				for (var key in setting) {
					if (setting.hasOwnProperty(key)) {
						clone[key] = setting[key];
					}
				}
				this.settings[settingName] = clone;
			} else {
				this.settings[settingName] = setting;
			}
		} else {
			this.settings[settingName] = defaultValue;
		}
	};
	
	//脚本参数
	this.ensureDefault("TargetElement", "");//用户名	
	this.ensureDefault("userName", "");//用户名	
	this.ensureDefault("rootpath","portal");//lfw跟路径
	this.ensureDefault("Caption","双击最大化编辑");//标题
	this.ensureDefault("MakerCaption","用友软件股份有限公司");//公司名称
	this.ensureDefault("width","70%");//宽度
	this.ensureDefault("TargetElementDiv","");//宽度
	
	delete this.ensureDefault;
};


//加载控件
SignControl.prototype.load = function(){
		//创建对象
		this.loadObject();
		//设置初始化参数
		this.curobj = document.getElementById("ntkosignctl");		
}
//加载控件
SignControl.prototype.loadObject = function () {
	var targetElement, tempParent;
	targetElement = document.getElementById(this.settings.TargetElement) ;
	if (targetElement == undefined) {
		targetElement = this.settings.TargetElementDiv; 
		if(!targetElement)
			throw "未能找到主控件: " + this.settings.TargetElement;
	}
	
	var wrapperType = (targetElement.currentStyle && targetElement.currentStyle["display"] || window.getComputedStyle && document.defaultView.getComputedStyle(targetElement, null).getPropertyValue("display")) !== "block" ? "span" : "div";
	
	tempParent = document.createElement(wrapperType);
	tempParent.innerHTML = this.getObjectHTML();	// Using innerHTML is non-standard but the only sensible way to dynamically add Flash in IE (and maybe other browsers)
	targetElement.parentNode.replaceChild(tempParent.firstChild, targetElement);
};
//获取office 控件的html对象
SignControl.prototype.getObjectHTML = function () {
	var htmlstr = '<object id="ntkosignctl" classid="clsid:97D0031E-4C58-4bc7-A9BA-872D5D572896"'
		+ 'codebase="'+ this.settings.rootpath 
		+'/office/plugin/ntko/ntkosigntool.cab#version=4,0,0,0" width="' 
		+ this.settings.width +'" height="240px"> '
		+'<param name="IsUseUTF8Data" value="-1"/>'
		+'<param name="IsUseUTF8URL" value="-1"/>'
		+'<span>不能装载印章管理控件。请在检查浏览器的选项中检查浏览器的安全设置。</span>'
		+'</object>';
	return htmlstr;
};
SignControl.prototype.CreateNew = function (signname,usercode,signpswm,srcfile) {
	this.curobj.CreateNew(signname,usercode,signpswm,srcfile);
}
SignControl.prototype.StatusCode = function(){
	if(this.curobj){
		return this.curobj.StatusCode;
	}
	retrun -1;
}
SignControl.prototype.SaveToUrl = function(url,filefieldname,para,filename,forname){
		return this.curobj.SaveToURL(url,filefieldname,para,filename,forname);
}
SignControl.prototype.OpenFromUrl = function(url,psw){
		this.curobj.OpenFromURL(url,psw);
}
SignControl.prototype.OpenFromLocal = function(url,PromptSelect,psw){
		this.curobj.OpenFromLocal(url,PromptSelect,psw);
}
SignControl.prototype.SaveToLocal = function(url,PromptSelect){
		this.curobj.SaveToLocal(url,PromptSelect);
}
SignControl.prototype.SaveToEkey = function(Index, IsPromptOverwrite){
		return this.curobj.SaveToEkey(Index, IsPromptOverwrite);
}
SignControl.prototype.OpenFromEkey = function(){
		return this.curobj.OpenFromEkey();
}
SignControl.prototype.SignName = function(){
		return this.curobj.SignName;
}
SignControl.prototype.SignUser = function(){
		return this.curobj.SignUser;
}
SignControl.prototype.Password = function(){
		return this.curobj.Password;
}
SignControl.prototype.LocalFileName = function(){
		return this.curobj.LocalFileName;
}
SignControl.prototype.SignSN = function(){
		return this.curobj.SignSN;
}
SignControl.prototype.EkeySN = function(){
		return this.curobj.EkeySN;
}
SignControl.prototype.ChangeEkeyPassword = function(oldpas,newpas,isadmin){
	this.curobj.ChangeEkeyPassword(oldpas,newpas,isadmin);
}
SignControl.prototype.ResetEkeyUserPassword = function(adminPass,newUserPass){
	this.curobj.ResetEkeyUserPassword(adminPass,newUserPass);
}
SignControl.prototype.DeleteFromEkey = function(){
		return this.curobj.DeleteFromEkey();
}
SignControl.prototype.ResetEkeySigns = function(){
		return this.curobj.ResetEkeySigns();
}
