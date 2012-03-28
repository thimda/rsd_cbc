<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.ufida.com/lfw" prefix="lfw"%>
<%@ taglib	uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%
	// 用户名
	String userName = nc.uap.lfw.core.LfwRuntimeEnvironment.getLfwSessionBean().getUser_name();
	String userPK = nc.uap.lfw.core.LfwRuntimeEnvironment.getLfwSessionBean().getPk_user();
	//是否使用留痕
	String trackRevisions =  StringUtils.defaultIfEmpty(request.getParameter("TrackRevisions") , "false");
	//自动保存
	String autoSave = StringUtils.defaultIfEmpty(request.getParameter("autoSave") , "false");
	//保存间隔
	//String saveTTL = StringUtils.defaultIfEmpty(request.getParameter("saveTTL") , "180000");
	String saveTTL = StringUtils.defaultIfEmpty(request.getParameter("saveTTL") , "10000");
	//只读
	String readonly = StringUtils.defaultIfEmpty(request.getParameter("readonly") , "false");
	//是否可打开
	String canopen = StringUtils.defaultIfEmpty(request.getParameter("canopen") , "false");
	//隐藏痕迹
	String showRevisions = StringUtils.defaultIfEmpty(request.getParameter("showRevisions") , "true");
	
    String occupymsg = StringUtils.defaultIfEmpty(request.getParameter("occupymsg") , "");
    String filenew = StringUtils.defaultIfEmpty(request.getParameter("filenew") , "false");
    //core path
	String rootpath = nc.uap.lfw.core.LfwRuntimeEnvironment.getRootPath();
	//lfwpath
	String lfwpath = nc.uap.lfw.core.LfwRuntimeEnvironment.getLfwCtx();
	String openurl = StringUtils.defaultIfEmpty(request.getParameter("openurl") , "");
	String noieopen = StringUtils.defaultIfEmpty(request.getParameter("noieopen") , "false");
	// 要打开的Word文件PK
	String pk = StringUtils.defaultIfEmpty(request.getParameter("url") , "");
	String loadbookmarkclass = StringUtils.defaultIfEmpty(request.getParameter("loadbookmarkclass") , "");
	String demo = StringUtils.defaultIfEmpty(request.getParameter("demo") , "false");
	String signmethod = StringUtils.defaultIfEmpty(request.getParameter("signmethod") , "");
	//String url = rootpath+"/pt/doc/file/down?id="+pk;
	request.setAttribute("userName",userName);
	request.setAttribute("rootpath",rootpath);
	request.setAttribute("lfwpath",lfwpath);
	request.setAttribute("pk",pk);
	request.setAttribute("openurl",openurl);
	//request.setAttribute("url",url);
	request.setAttribute("trackRevisions",trackRevisions);
	request.setAttribute("autoSave",autoSave);
	request.setAttribute("saveTTL",saveTTL);
	request.setAttribute("readonly",readonly);
	request.setAttribute("canopen",canopen);
	request.setAttribute("occupymsg",occupymsg);
	request.setAttribute("showRevisions",showRevisions);
	request.setAttribute("filenew",filenew);
	request.setAttribute("noieopen",noieopen);
	
%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<lfw:base />
		<lfw:head />
		<lfw:pageModel>
	</lfw:pageModel>
		<lfw:import />
	<script type="text/javascript" src="${rootpath}/office/js/jquery-1.4.2.min.js"></script>
	<script type="text/javascript" src="${rootpath}/office/js/OfficeUtil.js"></script>
	<script type="text/javascript" src="${rootpath}/office/plugin/ntko/officecontrol.js"></script>
	<!-- 
	<script type="text/javascript" src="${lfwpath}/frame/device_pc/script/ui/basic/HashMap.js"></script>
 -->
	    <script type="text/javascript"  >	    
		function afterLoad()
			{
				
			}
		var curfiletype;		
		function selectFile(filetype,doctype){	
			if("sign" == filetype){
				var url = "${rootpath}/app/mockapp/sign_select?user=<%=userPK%>";
				var dialog = showDialog(url, "印章选择", 700 ,500, "sign select", true ,false, "fileList") ;
				return false;				
			}
			else{
				curfiletype = filetype;		
				var url = "${rootpath}/core/uimeta.um?pageId=officefileselect&model=nc.uap.portal.ctrl.office.action.FileSelectModel&f=" + filetype + "&d=" +doctype;
				//var util = new OfficeUtil();
				//var ret = util.ShowModalDialog(url,"Office 控件","350px","500px",true,false);
				var dialog = showDialog(url, "Office 控件", 700 ,500, "office file select", true ,false, "fileList") ;				
				var divgen = $(dialog.Div_gen);
				if(divgen.find("> iframe").length < 1){
					var frmstr  = '<iframe src="" style="position:absolute; visibility:inherit; top:0px; left:0px; width:100%; height:100%; z-index:-1; "></<iframe>'
					//filter='progid:DXImageTransform.Microsoft.Alpha(style=0,opacity=0)';					 
					divgen.append(frmstr);
					var frm = divgen.find("> iframe");
					frm.css('filter','progid:DXImageTransform.Microsoft.Alpha(style=0,opacity=0)');
				}
				//return ret;
				return false;
			}
		}
		
		//插入模板文件回调
		function fileCallback(tempfile,filetype){
			var templetetype = curfiletype;
			if(filetype){
				templetetype = filetype;
			}
			//dialog.close();
			window.hideDialog();
			insertTemplete(tempfile,templetetype);
		}
		//插入模板
		function insertTemplete(file,filetype){
			if(officeControl == null)
				return;
			if(filetype){
				if(filetype == "redhead"){
					officeControl.insertRedHeadFromUrl(file);
				} 
				else if(filetype == "redend"){
					officeControl.insertRedEndFromUrl(file);
				}
				else if(filetype == "templete"){
					officeControl.insertTempleteFromUrl(file);
				}
				else if(filetype == "sign"){
					officeControl.addRemoteSign(file);
				}
			}
		}
		//从自定义接口加载标签 
		function loadBookMarks(){
			var bookmarks={};
			$.ajax({
            type:"GET",
            url:'${rootpath}/pt/office/loadbookmark?loadbookmark=${loadbookmarkclass}',	            
            async:false,
            success:function(xmlrequest){
            	bookmarks = GetResult(xmlrequest);
            },
            error:Onerror,
            cache:false
            });
            
            if(bookmarks.err){
				if(bookmarks.err != "")
				{
					alert(bookmarks.err);
					return false;
				}
			}
            var hash = new  HashMap();
            if(bookmarks.list){
            	if(bookmarks.list.length > 0){
            		$(bookmarks.list).each(function(){
            			hash.put(this.name,this.value);
            		})
            	} 
            }
            return hash;
		}
		//接受所有修订
		function  acceptAllRevision(){
			officeControl.acceptAllRevision();
		}
		//拒绝所有修订
		function rejectAllRevisions(){
			officeControl.acceptAllRevision();
		}
		//替换标签
		function replaceMarks(bookmarks){			
			officeControl.replaseBookMark(bookmarks);
		}
		//创建标签
		function addBookMark(name,text){
			officeControl.addBookMark(name,text);
		}		
		//标签替换文件
		function replaseBookMarkToFile(markname,url){
			officeControl.replaseBookMarkToFile(markname,url);
		}
		//ukey签章
		function addSignFromEkey(){
			officeControl.addSignFromEkey();
		}
		//获取结果
	    function GetResult(x) {
		    var result = { "value": null, "error": null };
		    var responseText = x;//x.responseText;
		    try {
			    result = eval("(" + responseText + ")");
			    if(result.err)
			    {
			       result.err = decodeURI(result.err);
			    }
		    } catch (e) {
			    if (responseText.length == 0) {
				    result.err = "NORESPONSE";
			    } else {
				    result.err = "BADRESPONSE";
				    result.responseText = responseText;
			    }
		    }
		    return result;
	    }
	    var officeControl= null;
		function CtrateControl()
		{
			var openurl = "${openurl}";
			var filepk = "${pk}";
			if(openurl && openurl != ""){
				filepk = "";
			}
			else
				openurl = "${rootpath}/pt/doc/file/down?id=";
				
		 	officeControl = new OfficeControl({
				TargetElement:"officeobject",
				rootpath:"${rootpath}",		
				userName:"${userName}",			
				pk:filepk,
				url:openurl,
				saveurl:"${rootpath}/pt/file/doc",
				//trackRevisions:${trackRevisions},
				trackRevisions:true,
				autoSave:${autoSave},
				readonly:${readonly},
				FileOpen:${canopen}&&!${readonly},
				occupymsg:"${occupymsg}",
				showRevisions:${showRevisions},
				saveTTL:${saveTTL},
				FileNew:${filenew},
				demo:<%=demo%>,
				height:"100%",
				SelectFile:selectFile,
				LoadBookMarks:loadBookMarks,
				OnBeforeDoSecSign:myBeforeDoSecSign,
				OnBeforeDoSecSignFromEkey:myBeforeDoSecSignFromEkey,
				OnSecSignSetInfo:mySecSignSetInfo,
				OnSecSignDeleted:mySecSignDeleted,
				OnSecSignFinished:mySecSignFinished			
			});
		}
		function Onerror(result)
	    {
	        alert(result);
	    }
	    function SetPk(newpk)
	    {
	    	if(newpk){	    		
	    		if(officeControl != null){
		    		officeControl.ChangeSetting("pk",newpk);
		    	}
	    	}
	    }
	    
	    //保存到服务器
	    function saveFileToURL(pk){
	    	if(officeControl != null){
	    		officeControl.saveFileToURL(pk);
	    	}	    	
	    }
	    //重新打开文件
	    function Load(pk){
	    	if(officeControl != null){
	    		officeControl.AfterLoad(pk);
	    	}
	    }
	    //获取正文内容
	    function getContent(start,end){
	    	var ret;
	    	if(officeControl != null){
	    		ret = officeControl.getContent(start,end);
	    	}
	    	return ret;
	    }
	    var PSWDialog;
	    function doSign(){
	    	PSWDialog = new InputDialogComp("userdialog", "请选择用印方式", 200, 200, 500, 200, 150, signTypeSelectOK, null, this, null, 10001, null)
			PSWDialog.create();
			
			var comboData = new ComboData();
			var attr = new Object;
			attr.selectOnly = true;
			var combData = new ComboData();
			combData.addItem(new ComboItem("加密狗印章", "ekey"));
			combData.addItem(new ComboItem("服务器印章", "server"));
			attr.comboData = combData;	
			
			PSWDialog.addItem("用印方式", "combsigntype", InputDialogComp.COMBO_TYPE, true, attr, "");
			PSWDialog.getItem("combsigntype").setSelectedItem(0);
	    	PSWDialog.show();
	    }
	    function signTypeSelectOK(){
	    	if(PSWDialog){
	    		var signtype  = PSWDialog.getItem("combsigntype").getSelectedValue();
	    		if(signtype == "ekey"){
	    			officeControl.addSignFromEkey();
	    		}
	    		else
	    			selectFile("sign","");
	    			
	    		PSWDialog.hide();
	    	}
	    }
	    //印章事件
	    function myBeforeDoSecSign(UserName,SignName,SignUser,SignSN, IsCancel){
	    	
		}
		function myBeforeDoSecSignFromEkey(ofctl,UserName,SignName,SignUser, SignSN, EkeySN, IsCancel){
				
		}
		function mySecSignSetInfo(Username, SignType,SecSignObject){
			
		}		
		function mySecSignDeleted(ofctl,UserName,SignName,SignUser, SignSN, EkeySN, UserData){
			alert("删除印章成功");
		}
		function mySecSignFinished(isOk,SecSignObject){
			try{
				
				
	    	<% if(!signmethod.equals("")){ %>
				//获得真正的parent
				var trueParent;
				var pWin = window.dialogArguments;
				if( parent.<%=signmethod%>){
					trueParent=parent;
				}else if(window.opener && window.opener.<%=signmethod%>){
					trueParent=window.opener;
				}else if(parent.getTrueParent() && parent.getTrueParent().<%=signmethod%>){
					trueParent=parent.getTrueParent();
				}else if(pWin.<%=signmethod%>){
					trueParent=pWin;
				}
				//印章类型、印章序号、EKEYSN,isok
				var signtype = SecSignObject.SignType;
				var signsn = SecSignObject.SignSN;
				var ekeysn =  SecSignObject.EkeySN;
				
				<%
					out.print("trueParent." + signmethod + "(signtype,signsn,ekeysn,isok);");
				}%>
			}
			catch(error){
			
			}
				return true;
		}
	</script>
	</head>
	<body  style="margin:0;padding:0;">
	<script type="text/javascript"  >
		$(document).ready(function(){
			if(IS_IE){				
			require("inputdialog", function(){});
				CtrateControl();
			}
			else{
				if(${noieopen}){
					var openurl = "${openurl}";
					var filepk = "${pk}";
					if(openurl && openurl != ""){
						filepk = "";
					}
					else
						openurl = "${rootpath}/pt/doc/file/down?id=" + filepk;
					window.location = openurl;
				}
				else{
					try{
						CtrateControl();
					}
					catch(err){
					}
				}
			}
		})		
	</script>
	<% if(demo.equals("true")){ %>
		<button id="btnok" onclick="doSign();" >签章</button>
	<% } %>
	<div id="officeobject"/>		
		
	<div id="fileList" style="display:none"/>
		
	</div>	
	</body>
	
</html>


