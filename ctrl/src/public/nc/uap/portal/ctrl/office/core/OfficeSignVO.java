package nc.uap.portal.ctrl.office.core;

/**
 * ����ӡ�� vo
 * @author lisw
 *
 */
public class OfficeSignVO {
	/**
	 * ʹ��������
	 */	
	private String  username;
	/**
	 * ʹ���߱���
	 */
	private String  usercode;
	/**
	 * ӡ������
	 */
	private String  signname;
	/**
	 * ӡ��·��
	 */
	private String  url;
	
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUsername() {
		return username;
	}
	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}
	public String getUsercode() {
		return usercode;
	}
	public void setSignname(String signname) {
		this.signname = signname;
	}
	public String getSignname() {
		return signname;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUrl() {
		return url;
	}	
}
