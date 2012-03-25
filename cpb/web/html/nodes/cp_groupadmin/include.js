/**
	 * 用户类型render
	 */
	function usertypeRender() {
	}
	/**
	 * 语言render
	 */
		function languageRender() {
	}
	/**
	 * 身份认证方式render
	 */
	function authmodeRender(){
	}
	/**
	 * 密码安全级别render
	 */
	function pwdlevelRender(){}

	usertypeRender.render = function(rowIndex, colIndex, value, header, cell) {
		cell.style.overflow = "hidden";
		cell.style.textOverflow = "ellipsis";
		cell.style.cursor = "default";
		cell.style.textAlign = "center";
		if (value == 0)
			cell.innerHTML = "集团用户";
		else if (value == 1)
		  cell.innerHTML = "公司用户";
		else if (value == 2)
		  cell.innerHTML = "系统管理员";
		else if (value == 3)
		  cell.innerHTML = "集团管理员";
		else if (value == 4)
		  cell.innerHTML = "普通管理员";
	}
	
	languageRender.render = function(rowIndex, colIndex, value, header, cell){
		cell.style.overflow = "hidden";
		cell.style.textOverflow = "ellipsis";
		cell.style.cursor = "default";
		cell.style.textAlign = "center";
		if (value == 0)
			cell.innerHTML = "简体中文";
		else if (value == 1)
		  cell.innerHTML = "繁体中文";
		  else if(value==2)
		   cell.inerHTML = "英文";
	}
	
	authmodeRender.render = function(rowIndex, colIndex, value, header, cell){
		cell.style.overflow = "hidden";
		cell.style.textOverflow = "ellipsis";
		cell.style.cursor = "default";
		cell.style.textAlign = "center";
		if (value == 0)
			cell.innerHTML = "静态密码";
		else if (value == 1)
		  cell.innerHTML = "动态密码";
		 	
	}
	
	pwdlevelRender.render = function(rowIndex, colIndex, value, header, cell){
		cell.style.overflow = "hidden";
		cell.style.textOverflow = "ellipsis";
		cell.style.cursor = "default";
		cell.style.textAlign = "center";
		if (value == '0000Z31000000000099E')
			cell.innerHTML = "普通级";
		else if (value == '0001Z3100000000013CP')
		  cell.innerHTML = "管理级";
	}