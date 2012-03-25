package nc.uap.wfm.vo;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDateTime;
public class WfmTaskVO extends SuperVO {
	private static final long serialVersionUID = 1L;
	/**
	 * 任务标识
	 */
	private String pk_task;
	/**
	 * 父任务标识
	 */
	private String pk_parent;
	/**
	 * 流程实例标识
	 */
	private String pk_proins;
	/**
	 * 根流程实例标识
	 */
	private String pk_rootproins;
	/**
	 * 流程标识
	 */
	private String pk_prodef;
	/**
	 * 流程编码
	 */
	private String prodef_id;
	/**
	 * 流程名称
	 */
	private String prodefname;
	/**
	 * 活动标识
	 */
	private String pk_humactins;
	/**
	 *节点ID
	 */
	private String port_id;
	/**
	 * 活动名称
	 */
	private String humactname;
	/**
	 * 任务的创建者
	 */
	private String pk_creater;
	/**
	 * 任务的拥有者
	 */
	private String pk_owner;
	/**
	 * 任务的拥有部门
	 */
	private String pk_ownerdept;
	/**
	 * 任务的执行者
	 */
	private String pk_executer;
	/**
	 * 任务的代办者
	 */
	private String pk_agenter;
	/**
	 * 任务的改派者
	 */
	private String pk_assigner;
	/**
	 * 电子签章标识
	 */
	private String pk_myvisa;
	/**
	 * 标识任务是否执行
	 */
	private UFBoolean isnotexe;
	/**
	 * 标识任务是否通过
	 */
	private UFBoolean isnotpas;
	/**
	 * 标识任务状态
	 */
	private String state;
	/**
	 * 办件情况
	 */
	private String handlepiece;
	/**
	 * 表单实例
	 */
	private String pk_frmins;
	/**
	 * 表单定义
	 */
	private String pk_frmdef;
	/**
	 * 任务完成方式
	 */
	private String finishtype;
	/**
	 * 任务的创建类型
	 */
	private String createtype;
	/**
	 * 流程开始日期
	 */
	private UFDate proinsstartdate;
	/**
	 * 开始日期
	 */
	private UFDate startdate;
	/**
	 * 签收时间
	 */
	private UFDate signdate;
	/**
	 * 结束日期
	 */
	private UFDate enddate;
	/**
	 * 任务持续时间
	 */
	private String standingtime;
	/**
	 * 意见
	 */
	private String opinion;
	/**
	 * 便签
	 */
	private String scratchpad;
	/**
	 * 加签次数
	 */
	private String addsigntimes;
	/**
	 * 优先级
	 */
	private String priority;
	/**
	 * 活动类型
	 */
	private String actiontype;
	/**
	 * 流程类型
	 */
	private String pk_flowtype;
	/**
	 * 是否是结束流程实例的任务
	 */
	private UFBoolean isnotended;
	private String flowtypename;
	/**
	 * 扩张属性
	 */
	private String ext0;
	private String ext1;
	private String ext2;
	private String ext3;
	private String ext4;
	private String ext5;
	private String ext6;
	private String ext7;
	private String ext8;
	private String ext9;
	private UFDateTime ts;
	private java.lang.Integer dr;
	public String getPk_task() {
		return pk_task;
	}
	public void setPk_task(String pk_task) {
		this.pk_task = pk_task;
	}
	public String getPk_parent() {
		return pk_parent;
	}
	public void setPk_parent(String pk_parent) {
		this.pk_parent = pk_parent;
	}
	public String getPk_humactins() {
		return pk_humactins;
	}
	public void setPk_humactins(String pk_humactins) {
		this.pk_humactins = pk_humactins;
	}
	public String getPk_owner() {
		return pk_owner;
	}
	public void setPk_owner(String pk_owner) {
		this.pk_owner = pk_owner;
	}
	public String getPk_creater() {
		return pk_creater;
	}
	public void setPk_creater(String pk_creater) {
		this.pk_creater = pk_creater;
	}
	public String getPk_executer() {
		return pk_executer;
	}
	public void setPk_executer(String pk_executer) {
		this.pk_executer = pk_executer;
	}
	public String getPk_agenter() {
		return pk_agenter;
	}
	public void setPk_agenter(String pk_agenter) {
		this.pk_agenter = pk_agenter;
	}
	public UFDate getStartdate() {
		return startdate;
	}
	public void setStartdate(UFDate startdate) {
		this.startdate = startdate;
	}
	public UFDate getEnddate() {
		return enddate;
	}
	public void setEnddate(UFDate enddate) {
		this.enddate = enddate;
	}
	public UFBoolean getIsnotexe() {
		return isnotexe;
	}
	public void setIsnotexe(UFBoolean isnotexe) {
		this.isnotexe = isnotexe;
	}
	public UFBoolean getIsnotpas() {
		return isnotpas;
	}
	public void setIsnotpas(UFBoolean isnotpas) {
		this.isnotpas = isnotpas;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
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
		return "pk_task";
	}
	public String getTableName() {
		return "wfm_task";
	}
	public String getOpinion() {
		return opinion;
	}
	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}
	public String getPk_proins() {
		return pk_proins;
	}
	public void setPk_proins(String pk_proins) {
		this.pk_proins = pk_proins;
	}
	public String getPort_id() {
		return port_id;
	}
	public void setPort_id(String port_id) {
		this.port_id = port_id;
	}
	public String getHumactname() {
		return humactname;
	}
	public void setHumactname(String humactname) {
		this.humactname = humactname;
	}
	public String getProdefname() {
		return prodefname;
	}
	public void setProdefname(String prodefname) {
		this.prodefname = prodefname;
	}
	public String getProdef_id() {
		return prodef_id;
	}
	public void setProdef_id(String prodef_id) {
		this.prodef_id = prodef_id;
	}
	public String getPk_frmins() {
		return pk_frmins;
	}
	public void setPk_frmins(String pk_frmins) {
		this.pk_frmins = pk_frmins;
	}
	public String getPk_prodef() {
		return pk_prodef;
	}
	public void setPk_prodef(String pk_prodef) {
		this.pk_prodef = pk_prodef;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public String getPk_frmdef() {
		return pk_frmdef;
	}
	public void setPk_frmdef(String pk_frmdef) {
		this.pk_frmdef = pk_frmdef;
	}
	public String getPk_rootproins() {
		return pk_rootproins;
	}
	public void setPk_rootproins(String pk_rootproins) {
		this.pk_rootproins = pk_rootproins;
	}
	public String getPk_myvisa() {
		return pk_myvisa;
	}
	public void setPk_myvisa(String pk_myvisa) {
		this.pk_myvisa = pk_myvisa;
	}
	public String getPk_assigner() {
		return pk_assigner;
	}
	public void setPk_assigner(String pk_assigner) {
		this.pk_assigner = pk_assigner;
	}
	public String getFinishtype() {
		return finishtype;
	}
	public String getStandingtime() {
		return standingtime;
	}
	public void setStandingtime(String standingtime) {
		this.standingtime = standingtime;
	}
	public void setFinishtype(String finishtype) {
		this.finishtype = finishtype;
	}
	public String getExt0() {
		return ext0;
	}
	public void setExt0(String ext0) {
		this.ext0 = ext0;
	}
	public String getExt1() {
		return ext1;
	}
	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}
	public String getExt2() {
		return ext2;
	}
	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}
	public String getExt3() {
		return ext3;
	}
	public void setExt3(String ext3) {
		this.ext3 = ext3;
	}
	public String getExt4() {
		return ext4;
	}
	public void setExt4(String ext4) {
		this.ext4 = ext4;
	}
	public String getExt5() {
		return ext5;
	}
	public void setExt5(String ext5) {
		this.ext5 = ext5;
	}
	public String getExt6() {
		return ext6;
	}
	public void setExt6(String ext6) {
		this.ext6 = ext6;
	}
	public String getExt7() {
		return ext7;
	}
	public void setExt7(String ext7) {
		this.ext7 = ext7;
	}
	public String getExt8() {
		return ext8;
	}
	public void setExt8(String ext8) {
		this.ext8 = ext8;
	}
	public String getExt9() {
		return ext9;
	}
	public void setExt9(String ext9) {
		this.ext9 = ext9;
	}
	public String getCreatetype() {
		return createtype;
	}
	public void setCreatetype(String createtype) {
		this.createtype = createtype;
	}
	public String getScratchpad() {
		return scratchpad;
	}
	public void setScratchpad(String scratchpad) {
		this.scratchpad = scratchpad;
	}
	public String getAddsigntimes() {
		return addsigntimes;
	}
	public void setAddsigntimes(String addsigntimes) {
		this.addsigntimes = addsigntimes;
	}
	public String getPk_ownerdept() {
		return pk_ownerdept;
	}
	public void setPk_ownerdept(String pk_ownerdept) {
		this.pk_ownerdept = pk_ownerdept;
	}
	public UFDate getSigndate() {
		return signdate;
	}
	public void setSigndate(UFDate signdate) {
		this.signdate = signdate;
	}
	public String getHandlepiece() {
		return handlepiece;
	}
	public void setHandlepiece(String handlepiece) {
		this.handlepiece = handlepiece;
	}
	public UFDate getProinsstartdate() {
		return proinsstartdate;
	}
	public void setProinsstartdate(UFDate proinsstartdate) {
		this.proinsstartdate = proinsstartdate;
	}
	public UFBoolean getIsnotended() {
		return isnotended;
	}
	public void setIsnotended(UFBoolean isnotended) {
		this.isnotended = isnotended;
	}
	public String getActiontype() {
		return actiontype;
	}
	public void setActiontype(String actiontype) {
		this.actiontype = actiontype;
	}
	public String getPk_flowtype() {
		return pk_flowtype;
	}
	public void setPk_flowtype(String pk_flowtype) {
		this.pk_flowtype = pk_flowtype;
	}
	public String getFlowtypename() {
		return flowtypename;
	}
	public void setFlowtypename(String flowtypename) {
		this.flowtypename = flowtypename;
	}
}
