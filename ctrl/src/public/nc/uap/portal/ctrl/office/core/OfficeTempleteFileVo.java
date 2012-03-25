package nc.uap.portal.ctrl.office.core;
/**
 * office 模板文件信息
 * @author lisw
 * 
 */
public class OfficeTempleteFileVo {
	/**
	 * 文件类型
	 * 		word
	 * 		excel
	 * 		wps
	 * 		et
	 */
	private String fileType;
	/**
	 * 文件名称,展示使用
	 */
	private String fileName;
	/**
	 * 文件地址，下载使用
	 */	
	private String fileUrl;
	/**
	 * 模板类型
	 * 		redhead
	 * 		redend
	 * 		templete
	 */
	private String templeteType; 
	/**
	 * 显示名称
	 */
	private String displayName;
	
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	
	
	public String getFileUrl() {
		return fileUrl;
	}

	public void setTempleteType(String templeteType) {
		this.templeteType = templeteType;
	}

	public String getTempleteType() {
		return templeteType;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}
}
