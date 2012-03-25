package nc.uap.wfm.vo;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDateTime;
public class WfmProInsStateVO extends SuperVO {
	private static final long serialVersionUID = 8133218325233746402L;
	private String pk_proinsstate;
	private String pk_proins;
	private String pk_user;
	private String reason;
	private String state;
	private UFDate actiondate;
	private UFDateTime ts;
	private UFBoolean dr;
	public String getPk_proinsstate() {
		return pk_proinsstate;
	}
	public void setPk_proinsstate(String pk_proinsstate) {
		this.pk_proinsstate = pk_proinsstate;
	}
	public String getPk_proins() {
		return pk_proins;
	}
	public void setPk_proins(String pk_proins) {
		this.pk_proins = pk_proins;
	}
	public String getPk_user() {
		return pk_user;
	}
	public void setPk_user(String pk_user) {
		this.pk_user = pk_user;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}

	public UFDate getActiondate() {
		return actiondate;
	}
	public void setActiondate(UFDate actiondate) {
		this.actiondate = actiondate;
	}
	public UFDateTime getTs() {
		return ts;
	}
	public void setTs(UFDateTime ts) {
		this.ts = ts;
	}
	public UFBoolean getDr() {
		return dr;
	}
	public void setDr(UFBoolean dr) {
		this.dr = dr;
	}
	public String getPKFieldName() {
		return "pk_proinsstate";
	}
	public String getTableName() {
		return "wfm_proinsstate";
	}
}
