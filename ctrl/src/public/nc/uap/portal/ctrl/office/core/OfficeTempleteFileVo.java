package nc.uap.portal.ctrl.office.core;
/**
 * office ģ���ļ���Ϣ
 * @author lisw
 * 
 */
public class OfficeTempleteFileVo {
	/**
	 * �ļ�����
	 * 		word
	 * 		excel
	 * 		wps
	 * 		et
	 */
	private String fileType;
	/**
	 * �ļ�����,չʾʹ��
	 */
	private String fileName;
	/**
	 * �ļ���ַ������ʹ��
	 */	
	private String fileUrl;
	/**
	 * ģ������
	 * 		redhead
	 * 		redend
	 * 		templete
	 */
	private String templeteType; 
	/**
	 * ��ʾ����
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
