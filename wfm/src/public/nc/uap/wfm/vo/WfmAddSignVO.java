package nc.uap.wfm.vo;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFDateTime;
public class WfmAddSignVO extends SuperVO {
	private static final long serialVersionUID = 2568349657856128416L;
	public static final String TYPE_AND = "0";// //´®ÐÐ
	public static final String TYPE_OR = "1";// //²¢ÐÐ
	public static final String ACTION_NEXTSTATE = "0";
	public static final String ACTION_CIRCLE = "1";
	private String pk_addsign;
	private String pk_task;
	private String addsigntime;
	private String addsigntype;
	private String addsignaction;
	private String scratchpad;
	private UFDateTime ts;
	private java.lang.Integer dr;
	private WfmAddSignUserVO[] addSignUserVos;
	public String getPk_addsign() {
		return pk_addsign;
	}
	public void setPk_addsign(String pk_addsign) {
		this.pk_addsign = pk_addsign;
	}
	public String getAddsigntype() {
		return addsigntype;
	}
	public void setAddsigntype(String addsigntype) {
		this.addsigntype = addsigntype;
	}
	public String getAddsignaction() {
		return addsignaction;
	}
	public void setAddsignaction(String addsignaction) {
		this.addsignaction = addsignaction;
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
	public String getScratchpad() {
		return scratchpad;
	}
	public void setScratchpad(String scratchpad) {
		this.scratchpad = scratchpad;
	}
	public String getPk_task() {
		return pk_task;
	}
	public void setPk_task(String pk_task) {
		this.pk_task = pk_task;
	}
	public String getPKFieldName() {
		return "pk_addsign";
	}
	public String getTableName() {
		return "wfm_addsign";
	}
	public WfmAddSignUserVO[] getAddSignUserVos() {
		return addSignUserVos;
	}
	public void setAddSignUserVos(WfmAddSignUserVO[] addSignUserVos) {
		this.addSignUserVos = addSignUserVos;
	}
	public String getAddsigntime() {
		return addsigntime;
	}
	public void setAddsigntime(String addsigntime) {
		this.addsigntime = addsigntime;
	}
}
