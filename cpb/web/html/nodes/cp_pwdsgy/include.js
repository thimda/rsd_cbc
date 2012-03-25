/**
	 * 密码过期策略render
	 */
	function passworddirtyRender() {
	}

	passworddirtyRender.render = function(rowIndex, colIndex, value, header, cell) {
		cell.style.overflow = "hidden";
		cell.style.textOverflow = "ellipsis";
		cell.style.cursor = "default";
		cell.style.textAlign = "center";
		if (value < 0)
			cell.innerHTML = "锁定";
			else if (value == 1)
			cell.innerHTML = "10分钟后重试";
			else if (value == 2)
			cell.innerHTML = "30分钟后重试";
			else if (value == 3)
			cell.innerHTML = "1小时后重试";
	    
	}