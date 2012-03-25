/**
 *  office file render 
 * */
 /**
  * 
  * request string 
  */
 FileRequest = {
	QueryString : function(item){
		var svalue = location.search.match(new RegExp("[\?\&]" + item + "=([^\&]*)(\&?)","i"));
		return svalue ? svalue[1] : svalue;
	}
}

 	/**
 	 * 
 	 */
	function fileEditRender() {
	}
	
	
	fileEditRender.render = function(rowIndex, colIndex, value, header, cell){
		cell.style.overflow = "hidden";
		cell.style.textOverflow = "ellipsis";
		cell.style.cursor = "default";
		cell.style.textAlign = "center";
		
		var grid = header.owner;
		var ds = grid.model.dataset;
		//var pk = value;
		var filetype = ds.getRow(rowIndex).getCellValue(ds.nameToIndex('filetype'));
		var pk = ds.getRow(rowIndex).getCellValue(ds.nameToIndex('fileurl'));
		cell.innerHTML = "<a style='cursor:pointer;text-decoration:underline'  onclick='editfile(event,\""+filetype +"\",\""+ pk +"\")'>编辑</a>";		
	}
	
	function editfile(e,doctype,pk){
		if(doctype != "sign"){
			var url = window.globalPath + "/core/word.jsp?pageId=officeedit&url="+pk;
			showDialog(url, "编辑文档", 980 ,600, "编辑文档", true ,false) ;
		}
		else{
			alert("暂不支持电子印章");
		}
		stopEvent(e);
		return false;
	}
	/**
	 * 上传文件回调方法
	 */
    function tbcall(pk_lfwfile,fileName,size,fileType,createtime,userpk,username,billitem){
        var proxy = new ServerProxy(null, null, true);   
        proxy.addParam("listener_class", "nc.uap.portal.ctrl.office.data.FileMgrUploadCompleteListener");		
		proxy.addParam("pk", pk_lfwfile);
		proxy.addParam("fileName", fileName);
		proxy.addParam("fileext", fileType);
		proxy.addParam("createtime", createtime);
		proxy.addParam("filetype", FileRequest.QueryString("f"));
		proxy.addParam("billitem", billitem);
		
		var sbr = new SubmitRule();
 		proxy.setSubmitRule(sbr);
        proxy.execute();
    }
	
    function uploadFile(){
    	var url =window.globalPath +  "/core/file.jsp?pageId=file&method=tbcall&iscover=false&billtype=portal&billitem=neednew&closeDialog=true"
    		+ "&fileExt=*.doc;*.docx;*.xls;*.xlsx;*.wps;*.et&fileDesc=office file(*.doc;*.docx;*.xls;*.xlsx;*.wps;*.et)&queueSizeLimit=1&isquick=true";
    	showDialog(url, "Office 控件", 500 ,400, "office file select", true ,false, "fileList") ;
    }
	
	