package nc.uap.wfm.vo;

import nc.vo.pub.SuperVO;

public class WfmMailMsgVO extends SuperVO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String smtpHost;
	private String fromAddr;
	private String senderName;
	private String userName;
	private String password;
	private String receiverEmails;
	private String subject;
	private StringBuffer sb;
	public String getSmtpHost() {
		return smtpHost;
	}
	public void setSmtpHost(String smtpHost) {
		this.smtpHost = smtpHost;
	}
	public String getFromAddr() {
		return fromAddr;
	}
	public void setFromAddr(String fromAddr) {
		this.fromAddr = fromAddr;
	}
	public String getSenderName() {
		return senderName;
	}
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getReceiverEmails() {
		return receiverEmails;
	}
	public void setReceiverEmails(String receiverEmails) {
		this.receiverEmails = receiverEmails;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public StringBuffer getSb() {
		return sb;
	}
	public void setSb(StringBuffer sb) {
		this.sb = sb;
	}


}
