package nc.uap.wfm.vo;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFDateTime;
public class WfmTaskTokenVO extends SuperVO {
	private static final long serialVersionUID = -910862604764783009L;
	private String pk_tasktoken;
	private String pk_task;
	private String token_id;
	private String pk_user;
	private UFDateTime ts;
	private java.lang.Integer dr;
	public String getPk_tasktoken() {
		return pk_tasktoken;
	}
	public void setPk_tasktoken(String pk_tasktoken) {
		this.pk_tasktoken = pk_tasktoken;
	}
	public String getPk_task() {
		return pk_task;
	}
	public void setPk_task(String pk_task) {
		this.pk_task = pk_task;
	}
	public String getToken_id() {
		return token_id;
	}
	public void setToken_id(String token_id) {
		this.token_id = token_id;
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
	public String getPKFieldName() {
		return "pk_tasktoken";
	}
	public String getTableName() {
		return "wfm_tasktoken";
	}
	public String getPk_user() {
		return pk_user;
	}
	public void setPk_user(String pk_user) {
		this.pk_user = pk_user;
	}
}
