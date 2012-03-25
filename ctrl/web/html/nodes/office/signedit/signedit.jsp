<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.ufida.com/lfw" prefix="lfw"%>
<%@ taglib	uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ page import="java.util.UUID"%>
<%@ page import="nc.uap.cpb.persist.dao.PtBaseDAO"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

	// 用户名
	String userName = nc.uap.lfw.core.LfwRuntimeEnvironment.getLfwSessionBean().getUser_name();
	String userCode = nc.uap.lfw.core.LfwRuntimeEnvironment.getLfwSessionBean().getUser_code();
    //core path
	String rootpath = nc.uap.lfw.core.LfwRuntimeEnvironment.getRootPath();
	//lfwpath
	String lfwpath = nc.uap.lfw.core.LfwRuntimeEnvironment.getLfwCtx();
	String iscover = "true";
	String datasource  = nc.uap.lfw.core.LfwRuntimeEnvironment.getDatasource();

	request.setAttribute("userName",userName);
	request.setAttribute("userCode",userCode);
	request.setAttribute("lfwpath",lfwpath);
	request.setAttribute("rootpath",rootpath);
	request.setAttribute("iscover",iscover);

	
%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<lfw:base />
		<lfw:head />		
		<lfw:import />
		
		<script type="text/javascript" src="${rootpath}/office/js/jquery-1.4.2.min.js"></script>
		<script type="text/javascript" src="${rootpath}/office/js/OfficeUtil.js"></script>
		<script type="text/javascript" src="${rootpath}/office/plugin/ntko/signtoolcontrol.js"></script>
		<script type="text/javascript"  >
		//印章控件
		var signctr;
		//印章来源 local,remote,ekey,none
		var signsrctype;
		//印章状态 none,open, create, edit
		var signstatus;
		//打开地址
		var url;
		var billitem;
		
		//初始化控件
		function InitControl(){
			var mainWidget = pageUI.getWidget("main");
			var usercodecmp =  mainWidget.getComponent("usercode");
			usercodecmp.setValue("${userCode}");
			signctr = new SignControl({
				TargetElement:"signdiv",
				userName:"${userName}",
				rootpath:"${rootpath}",
				width:"300px"
			});
			ChangeStatus("none","none");
		}
		/**
		*
		*	生成印章
		*/
		function GenSign(){
			
			var mainWidget = pageUI.getWidget("main");
			try{
				var signname = getcmpValue(mainWidget,"signname","印章名称不可为空");
				var usercode = getcmpValue(mainWidget,"usercode","用户编码不可为空");
				var signpsw = getcmpValue(mainWidget,"signpsw","印章密码不可为空");
				var confirmpsw = getcmpValue(mainWidget,"confirmpsw","确认密码不可为空");
				if(signpsw.length < 6){showmsg("印章密码需大于六位");return;}
				if(signpsw != confirmpsw){showmsg("两次密码输入不一致");return;}
				
				var filecmp = 	mainWidget.getComponent("selffile");
				var context = filecmp.getContext();
				var srcfile = context.otherCtx.fileName;
				if(srcfile == null || srcfile == undefined || srcfile == ""){
					showmsg("源文件不能为空"); return;
				}
				if(signctr){
					signctr.CreateNew(signname,usercode,signpsw,srcfile);
			        if (0 != signctr.StatusCode()) {
		                thorw("创建印章错误.");		             
		            }
		            if(signsrctype == "none")
		            	ChangeStatus(signsrctype,"create");
		            else
		            	ChangeStatus(signsrctype,"edit");
				}
			}
			catch(ex){
				showmsg(ex);
			}
		}
		//打开本地印章
		function openLocalSign(){
			reset();
			if(signctr){
				signctr.OpenFromLocal('',true);
				if(signctr.StatusCode() == 0){
					ChangeStatus("local","open");
					synSigntoForm();
					url = signctr.LocalFileName();
				}
			}
		}
		//保存本地印章
		function saveLocalSign(){
			signctr.SaveToLocal(url,true);
		}
		//同步印章数据到form
		function synSigntoForm(){
			if(signstatus != "none"){
				var mainWidget = pageUI.getWidget("main");
				setcmpValue(mainWidget,"signname",signctr.SignName());
				setcmpValue(mainWidget,"usercode",signctr.SignUser());
				setcmpValue(mainWidget,"signpsw",signctr.Password());
				setcmpValue(mainWidget,"confirmpsw",signctr.Password());
			}
		}
		function openRemoteSign(){
			reset();
			var url = "${rootpath}/core/uimeta.um?pageId=officefileselect&model=nc.uap.portal.ctrl.office.action.FileSelectModel&f=sign"
			var dialog = showDialog(url, "Office 控件", 500 ,400, "office file select", true ,false, "fileList") ;				
			var divgen = $(dialog.Div_gen);
			if(divgen.find("> iframe").length < 1){
				var frmstr  = "<iframe src='' style='position:absolute; visibility:inherit; top:0px; left:0px; width:100%; height:100%; z-index:-1; filter='progid:DXImageTransform.Microsoft.Alpha(style=0,opacity=0)';'></<iframe>" 
				divgen.append(frmstr);
			}
			return false;
		}
		function openSignFormurl(tempfile){
			if(signctr){
				showloading(true);
				signctr.OpenFromUrl(tempfile.url);				
				if(signctr.StatusCode() == 0){
					ChangeStatus("remote","open");
					synSigntoForm();
					url = tempfile.url
					billitem = tempfile.pk;
				}
				showloading(false);
			}
		}
		//保存签章到服务器
		function saveRemoteSign(){
			try{
				if(signctr){
					var signname = signctr.SignName();
					var uploadform = $("#signform");
					if(billitem == "" || billitem == undefined){
						billitem = getnewPK();
					}
					$("#formusercode").val(signctr.SignUser());
					$("#formsignname").val(signctr.SignName());
					$("#billitem").val(billitem);					
					var ret = signctr.SaveToUrl(uploadform.attr("action"),signname + ".sign","",signname+ ".sign","signform");
					if (0 != signctr.StatusCode()) {
						throw "保存印章出错";
					}
					if(signctr.StatusCode() == 0){
						ChangeStatus("remote","open");
						showmsg("保存成功");
					}
					else{showmsg("保存失败");}
				}
				else{
					showmsg("无法检测到签章控件，请检查浏览器环境");					
				}
			}
			catch(ex){
				showmsg(ex);
			}
		}
		function deleteRemoteSign(){
			if(signsrctype  != "remote" || !(billitem != "" && billitem != undefined)){
				showmsg("当前印章不是从服务器打开，无法删除");
				return;
			}
			if(!confirmmsg("确实要删除当前印章吗?"))
				return;
				
			$.ajax({
            type:"GET",
            url:'${rootpath}/pt/office/deletesign?pk=' + billitem,
            data:'',
            async:false,
            success:function(req){
            	if(req == "success"){
            		reset();
            		showmsg("保存成功");
            	}
            	else {
            		showmsg("删除失败 ," + req);
            	}            	
            },
            error:function(req){
            	throw "获取单据pk失败,"+ req
            },
            cache:false
            });
		}
		//ekey
		//从Ekey打开
		function openEkeySign(){
			reset();
			if(signctr){
				ntkosignctl.OpenFromEkey();
    			if(0 != ntkosignctl.StatusCode){
	    			showmsg("从EKEY打开印章错误.");
	    			return;
    			}
			}
		}
		//保存到ekey
		function savetoEkey(){
			signctr.SaveToEkey();
		}
		
		//重置界面
		function reset(){
			$("#signContainer").html('<div id="signdiv"></div>');
			InitControl();
			url = "";
			billitem = "";
			var mainWidget = pageUI.getWidget("main");
			setcmpValue(mainWidget,"signname","");
			setcmpValue(mainWidget,"signpsw","");
			setcmpValue(mainWidget,"confirmpsw","");
			
			var context = new Object;
			context.otherCtx = new Object;
			context.otherCtx.filename = "";
			var filecmp = 	mainWidget.getComponent("selffile");
			filecmp.setContext(context);
		}
		</script>
		
		<!-- 工具类函数 -->
		<script type="text/javascript" >
		//设置菜单状态
		function setmenuActive(itemname,isactive,childItem){
				var mainWidget = pageUI.getWidget("main");
				var menubar =  mainWidget.getMenu("signmenu");
				var menu = menubar.getMenu(itemname);
				if(childItem != null && childItem != "")
					menu = menu.getMenu(childItem);
				menu.setActive(isactive);
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
		
		//设置lfw控件属性
		function setcmpValue(widget,cmpname,value){
			var cmp =  widget.getComponent(cmpname);
			cmp.setValue(value);
		}
		//显示message
		function showmsg(msg){
			alert(msg);
		}
		function confirmmsg(msg){
			var ret =  confirm(msg);
			return ret;
		}
		
		//修改当前控件状态
		function ChangeStatus(srctype,status){
			signsrctype = srctype;
			signstatus = status;
			if(signstatus == "none"||signstatus == "open"){
				setmenuActive("remoteMenu",false,"saveRemoteMenu");
				setmenuActive("localMenu",false,"saveLocalMenu");
				setmenuActive("ekeyMenu",false,"saveEkeyMenu");
			} 
			else if(signstatus == "create"){
				setmenuActive("remoteMenu",true,"saveRemoteMenu");
				setmenuActive("localMenu",true,"saveLocalMenu");
				setmenuActive("ekeyMenu",true,"saveEkeyMenu");
			} 
			else if(signstatus == "edit" ){
				if(signsrctype == "local"){
					setmenuActive("remoteMenu",false,"saveRemoteMenu");
					setmenuActive("localMenu",true,"saveLocalMenu");
					setmenuActive("ekeyMenu",false,"saveEkeyMenu");
				} 
				else if(signsrctype  == "remote"){
					setmenuActive("remoteMenu",true,"saveRemoteMenu");
					setmenuActive("localMenu",false,"saveLocalMenu");
					setmenuActive("ekeyMenu",false,"saveEkeyMenu");
				}
				else if(signsrctype  == "ekey"){
					setmenuActive("remoteMenu",false,"saveRemoteMenu");
					setmenuActive("localMenu",false,"saveLocalMenu");
					setmenuActive("ekeyMenu",true,"saveEkeyMenu");
				}
				else if(signsrctype =="none"){
					setmenuActive("remoteMenu",false,"saveRemoteMenu");
					setmenuActive("localMenu",false,"saveLocalMenu");
					setmenuActive("ekeyMenu",false,"saveEkeyMenu")
				}
			}
		}
		
		function fileCallback(tempfile){
			//dialog.close();
			window.hideDialog();
			if(signctr){
				if(tempfile){
					openSignFormurl(tempfile);
				}
			}				
		} 
		function getnewPK(){
			var newpk = "";
			 $.ajax({
            type:"GET",
            url:'${rootpath}/pt/office/getnewpk',
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
		function showloading(ishow){
			if(ishow)
				showDefaultLoadingBar();
			else
				hideDefaultLoadingBar();
		}		
		</script>
		
	</head>
	<body>
	<script type="text/javascript"  >	
		window.onload = function(){
			pageBodyScript();
			InitControl();
		}
	</script>
		<lfw:pageModel>			
			<lfw:widget id="main">
			<lfw:flowvLayout id="flowv3">
				<lfw:flowvPanel height="23">
					<lfw:menubar id='signmenu' widget='main'/>
				</lfw:flowvPanel>
				<lfw:flowvPanel height="23">
					<lfw:textcomp id="signname" widget='main'/>
				</lfw:flowvPanel>
				<lfw:flowvPanel height="23">
					<lfw:textcomp id="usercode" widget='main'/>
				</lfw:flowvPanel>
				<lfw:flowvPanel height="23">
					<lfw:textcomp id="signpsw" widget='main'/>
				</lfw:flowvPanel>
				<lfw:flowvPanel height="23">
					<lfw:textcomp id="confirmpsw" widget='main'/>
				</lfw:flowvPanel>
				<lfw:flowvPanel height="23">
					<lfw:selfDefComp id="selffile" widget='main'/>
				</lfw:flowvPanel>
				<div id = "signContainer">
					<div id="signdiv">
					</div>
				</div>
			</lfw:flowvLayout>
			</lfw:widget>
		</lfw:pageModel>
		<div  style="display:none">
			<form id="signform" method="post" enctype="multipart/form-data" action="<%=basePath%>pt/file/upload?;jsessionid=<%= session.getId() %>">				 
       			<input type="hidden" id="billtype" name="billtype" value="OfficeSign">
       			<input type="hidden" id ="billitem" name="billitem" value="">
       			<input type="hidden" id ="category" name="category" value="">
       			<input type="hidden" id ="formusercode" name="formusercode" value="">
       			<input type="hidden" id ="formsignname" name="formsignname" value="">       			
       			<input type="hidden" id ="iscover" name="iscover" value="<%=iscover%>">
       			<input type="hidden" id ="extendclass" name="extendclass" value="nc.uap.portal.ctrl.office.action.SignUploadExtender">
       			
			</form>
		</div>
	</body>
</html>
	