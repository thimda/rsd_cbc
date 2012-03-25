package nc.uap.wfm.vo;
import nc.vo.pub.SuperVO;
/**
 * ´«ÔÄvo
 * 
 * @author zhangxya
 * 
 */
public class WfmDeliverVO extends SuperVO {
	private static final long serialVersionUID = 1L;
	private String pk_deliver;
	private String stateusers;
	private String pk_task;
	private String pk_dept;
	private String scratchpad;
	public String getPk_deliver() {
		return pk_deliver;
	}
	public void setPk_deliver(String pk_deliver) {
		this.pk_deliver = pk_deliver;
	}
	public String getStateusers() {
		return stateusers;
	}
	public void setStateusers(String stateusers) {
		this.stateusers = stateusers;
	}
	public String getPk_task() {
		return pk_task;
	}
	public void setPk_task(String pk_task) {
		this.pk_task = pk_task;
	}
	public String getPk_dept() {
		return pk_dept;
	}
	public void setPk_dept(String pk_dept) {
		this.pk_dept = pk_dept;
	}
	public String getScratchpad() {
		return scratchpad;
	}
	public void setScratchpad(String scratchpad) {
		this.scratchpad = scratchpad;
	}
	@Override public String getPKFieldName() {
		return "pk_deliver";
	}
	public String getTableName() {
		return "wfm_deliver";
	}
}
