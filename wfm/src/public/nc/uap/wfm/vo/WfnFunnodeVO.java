package nc.uap.wfm.vo;

import nc.vo.pub.SuperVO;

public class WfnFunnodeVO extends SuperVO{
	private static final long serialVersionUID = 1L;
	//node����
	private String id;
	//����
	private String funname;
	//url
	private String funurl;
	//����
	private String funtype;
	//���ڵ�
	private String parentid;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFunname() {
		return funname;
	}
	public void setFunname(String funname) {
		this.funname = funname;
	}
	public String getFunurl() {
		return funurl;
	}
	public void setFunurl(String funurl) {
		this.funurl = funurl;
	}
	public String getFuntype() {
		return funtype;
	}
	public void setFuntype(String funtype) {
		this.funtype = funtype;
	}
	public String getParentid() {
		return parentid;
	}
	public void setParentid(String parentid) {
		this.parentid = parentid;
	}
	
}
