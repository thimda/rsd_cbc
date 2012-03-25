package nc.uap.portal.ctrl.office.core;

/**
 * 电子印章 vo
 * @author lisw
 *
 */
public class OfficeSignVO {
	/**
	 * 使用者名称
	 */	
	private String  username;
	/**
	 * 使用者编码
	 */
	private String  usercode;
	/**
	 * 印章名称
	 */
	private String  signname;
	/**
	 * 印章路径
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
