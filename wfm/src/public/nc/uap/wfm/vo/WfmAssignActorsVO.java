package nc.uap.wfm.vo;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFDateTime;
public class WfmAssignActorsVO extends SuperVO {
	private static final long serialVersionUID = 1L;
	private String pk_assignactors;
	private String pk_rootproins;
	private String prodef_id;
	private String humact_id;
	private String pk_user;
	private UFDateTime ts;
	private java.lang.Integer dr;
	public String getPk_assignactors() {
		return pk_assignactors;
	}
	public void setPk_assignactors(String pk_assignactors) {
		this.pk_assignactors = pk_assignactors;
	}
	public String getPk_rootproins() {
		return pk_rootproins;
	}
	public void setPk_rootproins(String pk_rootproins) {
		this.pk_rootproins = pk_rootproins;
	}
	public String getProdef_id() {
		return prodef_id;
	}
	public void setProdef_id(String prodef_id) {
		this.prodef_id = prodef_id;
	}
	public String getHumact_id() {
		return humact_id;
	}
	public void setHumact_id(String humact_id) {
		this.humact_id = humact_id;
	}
	public String getPk_user() {
		return pk_user;
	}
	public void setPk_user(String pk_user) {
		this.pk_user = pk_user;
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
		return "pk_assignactors";
	}
	public String getTableName() {
		return "wfm_assignactors";
	}
}
