/**
	 * 职责类型render
	 */
	function resptypeRender() {
	}

	

	resptypeRender.render = function(rowIndex, colIndex, value, header, cell) {
		cell.style.overflow = "hidden";
		cell.style.textOverflow = "ellipsis";
		cell.style.cursor = "default";
		cell.style.textAlign = "center";
		if (value == 0)
			cell.innerHTML = "管理类型";
		else if (value == 1)
		  cell.innerHTML = "业务类型";		
	}
	
	
	
	