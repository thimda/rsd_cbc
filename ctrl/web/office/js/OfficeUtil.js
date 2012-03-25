var OfficeUtil;
if (OfficeUtil == undefined) {
	OfficeUtil = function () {
	};
}
OfficeUtil.prototype.ShowModalDialog = function (url,title,height,width,hideStatusBar,isModelessDialog)
{  
    var time = new Date();
    url = url + '&time=' + time
    var _features="dialogHeight:"+height+"px; dialogWidth: "+width+"px; edge: Raised; center: Yes; help: No; resizable:No; status: No;scrollBars:No;";
    
    window.hideStatusBar = hideStatusBar;
    
    if(title)
    {
        window.document.title=title;
    }
    
    var retv;
    if(isModelessDialog){
        _features="status:off;dialogWidth:"+width+"px;dialogHeight:"+height+"px";
        retv = window.showModelessDialog(url,null,_features);	     
    }else{
        retv = window.showModalDialog(url,window,_features);
    }
    return retv;    
} 