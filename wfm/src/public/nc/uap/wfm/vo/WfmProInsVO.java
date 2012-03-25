package nc.uap.wfm.vo;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDateTime;
public class WfmProInsVO extends SuperVO {
	private static final long serialVersionUID = 3532658597233071827L;
	/**
	 * 流程实例标识
	 */
	private String pk_proins;
	/**
	 * 根流程实例标识
	 */
	private String pk_rootproins;
	/**
	 * 流程定义标识
	 */
	private String pk_prodef;
	/**
	 *流程定义编码
	 */
	private String prodef_id;
	/**
	 * 父流程实例
	 */
	private String pk_parent;
	/**
	 * 流程启动者
	 */
	private String pk_starter;
	/**
	 * 启动单据标识
	 */
	private String pk_startfrmins;
	/**
	 * 流程状态
	 */
	private String state;
	/**
	 * 流程启动时间
	 */
	private UFDate startdate;
	/**
	 * 流程结束时间
	 */
	private UFDate enddate;
	/**
	 * 用于发起子单据的父流程实例pk
	 */
	private String pk_pproins;
	/**
	 * 发起子流程的任务Pk
	 */
	private String pk_starttask;
	private UFDateTime ts;
	private java.lang.Integer dr;
	public String getPk_proins() {
		return pk_proins;
	}
	public void setPk_proins(String pk_proins) {
		this.pk_proins = pk_proins;
	}
	public String getProdef_id() {
		return prodef_id;
	}
	public void setProdef_id(String prodef_id) {
		this.prodef_id = prodef_id;
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
		return "pk_proins";
	}
	public String getTableName() {
		return "wfm_proins";
	}
	public UFDate getStartdate() {
		return startdate;
	}
	public void setStartdate(UFDate startdate) {
		this.startdate = startdate;
	}
	public String getPk_starter() {
		return pk_starter;
	}
	public void setPk_starter(String pk_starter) {
		this.pk_starter = pk_starter;
	}
	public UFDate getEnddate() {
		return enddate;
	}
	public void setEnddate(UFDate enddate) {
		this.enddate = enddate;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getPk_parent() {
		return pk_parent;
	}
	public void setPk_parent(String pk_parent) {
		this.pk_parent = pk_parent;
	}
	public String getPk_startfrmins() {
		return pk_startfrmins;
	}
	public void setPk_startfrmins(String pk_startfrmins) {
		this.pk_startfrmins = pk_startfrmins;
	}
	public String getPk_prodef() {
		return pk_prodef;
	}
	public void setPk_prodef(String pk_prodef) {
		this.pk_prodef = pk_prodef;
	}
	public String getPk_rootproins() {
		return pk_rootproins;
	}
	public void setPk_rootproins(String pk_rootproins) {
		this.pk_rootproins = pk_rootproins;
	}
	public String getPk_pproins() {
		return pk_pproins;
	}
	public void setPk_pproins(String pk_pproins) {
		this.pk_pproins = pk_pproins;
	}
	public String getPk_starttask() {
		return pk_starttask;
	}
	public void setPk_starttask(String pk_starttask) {
		this.pk_starttask = pk_starttask;
	}
}
