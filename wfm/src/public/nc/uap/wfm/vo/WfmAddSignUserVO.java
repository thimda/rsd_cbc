package nc.uap.wfm.vo;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFDateTime;
public class WfmAddSignUserVO extends SuperVO {
	private static final long serialVersionUID = -7461313712108622875L;
	private String pk_addsignuser;
	private String pk_user;
	private String pk_dept;
	private String pk_addsign;
	private UFDateTime ts;
	private java.lang.Integer dr;
	public String getPk_addsignuser() {
		return pk_addsignuser;
	}
	public void setPk_addsignuser(String pk_addsignuser) {
		this.pk_addsignuser = pk_addsignuser;
	}
	public String getPk_user() {
		return pk_user;
	}
	public void setPk_user(String pk_user) {
		this.pk_user = pk_user;
	}
	public String getPk_addsign() {
		return pk_addsign;
	}
	public void setPk_addsign(String pk_addsign) {
		this.pk_addsign = pk_addsign;
	}
	public UFDateTime getTs() {
		return ts;
	}
	public void setTs(UFDateTime ts) {
		this.ts = ts;
	}
	public java.lang.Integer getDr() {
		return dr;
	}
	public void setDr(java.lang.Integer dr) {
		this.dr = dr;
	}
	public String getPk_dept() {
		return pk_dept;
	}
	public void setPk_dept(String pk_dept) {
		this.pk_dept = pk_dept;
	}
	public String getPKFieldName() {
		return "pk_addsignuser";
	}
	public String getTableName() {
		return "wfm_addsignuser";
	}
}
