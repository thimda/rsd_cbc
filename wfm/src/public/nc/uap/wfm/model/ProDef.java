package nc.uap.wfm.model;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import nc.bs.framework.common.NCLocator;
import nc.uap.dbl.constant.DblConstants;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.itf.IWfmProDefQry;
import nc.uap.wfm.parse.ProcessParser;
import nc.uap.wfm.vo.WfmFlwTypeVO;
import nc.uap.wfm.vo.WfmProdefVO;
import nc.vo.pub.lang.UFBoolean;
public class ProDef extends SingleInOutPort implements Serializable {
	private static final long serialVersionUID = 5052113023631684777L;
	/**
	 * 流程主键
	 */
	private String pk_prodef;
	/**
	 * 父流程编码
	 */
	private String parent_id;
	/**
	 * 根流程编码
	 */
	private String root_id;
	/**
	 * 流程编码
	 */
	private String id;
	/**
	 * 流程名称
	 */
	private String name;
	/**
	 * 流程描述
	 */
	private String memo;
	/**
	 * 流程版本
	 */
	private String version;
	/**
	 * 开始事件
	 */
	private StartEvent start;
	/**
	 * 结束事件
	 */
	private EndEvent end;
	/**
	 * 人工活动
	 */
	private Map<String, HumAct> humActs = new HashMap<String, HumAct>();
	/**
	 * 手工活动
	 */
	private Map<String, ManAct> manAcs = new HashMap<String, ManAct>();
	/**
	 * 脚本活动
	 */
	private Map<String, ScrAct> scrActs = new HashMap<String, ScrAct>();
	/**
	 * 唯一网关
	 */
	private Map<String, ExcGat> excGats = new HashMap<String, ExcGat>();
	/**
	 * 包含网关
	 */
	private Map<String, IncGat> incGats = new HashMap<String, IncGat>();
	/**
	 * 子流程活动
	 */
	private Map<String, ProDef> subPros = new HashMap<String, ProDef>();
	/**
	 * 连线
	 */
	private Map<String, SequenceFlow> squFlws = new HashMap<String, SequenceFlow>();
	/**
	 * 所有的点
	 */
	private Map<String, IPort> ports = new HashMap<String, IPort>();
	/**
	 * 流程分类
	 */
	private WfmFlwTypeVO flwtype;
	/**
	 * 启动表单
	 */
	private String pk_startfrm;
	/**
	 * 是否允许反审核
	 */
	private String allowReverseAudit;
	/**
	 * 扩张属性控制类
	 */
	private String serverClass;
	/**
	 * 流程图元信息
	 */
	private ProcessDiagram processDiagram;
	private String extProDefAttr0;
	private String extProDefAttr1;
	private String extProDefAttr2;
	private UFBoolean isLazy;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public StartEvent getStart() {
		return start;
	}
	public void setStart(StartEvent start) {
		this.start = start;
	}
	public EndEvent getEnd() {
		return end;
	}
	public void setEnd(EndEvent end) {
		this.end = end;
	}
	public Map<String, HumAct> getHumActs() {
		return humActs;
	}
	public void setHumActs(Map<String, HumAct> humActs) {
		this.humActs = humActs;
	}
	public Map<String, ManAct> getManAcs() {
		return manAcs;
	}
	public void setManAcs(Map<String, ManAct> manAcs) {
		this.manAcs = manAcs;
	}
	public Map<String, ScrAct> getScrActs() {
		return scrActs;
	}
	public void setScrActs(Map<String, ScrAct> scrActs) {
		this.scrActs = scrActs;
	}
	public Map<String, ExcGat> getExcGats() {
		return excGats;
	}
	public void setExcGats(Map<String, ExcGat> excGats) {
		this.excGats = excGats;
	}
	public Map<String, IncGat> getIncGats() {
		return incGats;
	}
	public void setIncGats(Map<String, IncGat> incGats) {
		this.incGats = incGats;
	}
	public Map<String, ProDef> getSubPros() {
		return subPros;
	}
	public void setSubPros(Map<String, ProDef> subPros) {
		this.subPros = subPros;
	}
	public String getPk_prodef() {
		return pk_prodef;
	}
	public void setPk_prodef(String pk_prodef) {
		this.pk_prodef = pk_prodef;
	}
	public Map<String, IPort> getPorts() {
		return ports;
	}
	public void setPorts(Map<String, IPort> ports) {
		this.ports = ports;
	}
	public void addPorts(IPort port) {
		ports.put(port.getId(), port);
	}
	public String getParent_id() {
		return parent_id;
	}
	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}
	public Map<String, SequenceFlow> getSquFlws() {
		return squFlws;
	}
	public void setSquFlws(Map<String, SequenceFlow> squFlws) {
		this.squFlws = squFlws;
	}
	public WfmFlwTypeVO getFlwtype() {
		return flwtype;
	}
	public void setFlwtype(WfmFlwTypeVO flwtype) {
		this.flwtype = flwtype;
	}
	public String getPk_startfrm() {
		return pk_startfrm;
	}
	public void setPk_startfrm(String pk_startfrm) {
		this.pk_startfrm = pk_startfrm;
	}
	/**
	 * 增加一个连线
	 * 
	 * @param sf
	 */
	public void addSequenceFlows(SequenceFlow sf) {
		squFlws.put(sf.getId(), sf);
	}
	/**
	 * 增加一个用户活动
	 * 
	 * @param sf
	 */
	public void addHumanActivitys(HumAct ha) {
		humActs.put(ha.getId(), ha);
	}
	/**
	 * 增加一个唯一网关
	 */
	public void addExclusiveGateways(ExcGat eg) {
		excGats.put(eg.getId(), eg);
	}
	/**
	 * 增加一个包含网关
	 */
	public void addInclusiveGateways(IncGat ig) {
		incGats.put(ig.getId(), ig);
	}
	/**
	 * 增加一个子流程
	 * 
	 * @return
	 */
	public void addSubProcess(ProDef proDef) {
		subPros.put(proDef.getId(), proDef);
		proDef.setParent_id(this.getId());
		this.addPorts(proDef);
	}
	public String getRoot_id() {
		return root_id;
	}
	public void setRoot_id(String root_id) {
		this.root_id = root_id;
	}
	public String getServerClass() {
		return serverClass;
	}
	public void setServerClass(String serverClass) {
		this.serverClass = serverClass;
	}
	public String getAllowReverseAudit() {
		return allowReverseAudit;
	}
	public void setAllowReverseAudit(String allowReverseAudit) {
		this.allowReverseAudit = allowReverseAudit;
	}
	public boolean isNotBack() {
		String flag = this.getAllowReverseAudit();
		if (DblConstants.StrTrue.equalsIgnoreCase(flag)) {
			return true;
		} else {
			return false;
		}
	}
	public ProcessDiagram getProcessDiagram() {
		if (this.processDiagram == null) {
			WfmProdefVO proDefVo = null;
			try {
				proDefVo = NCLocator.getInstance().lookup(IWfmProDefQry.class).getProDefVOByProDefPk(this.getPk_prodef());
				ProcessDiagram processDiagram = ProcessParser.getInstance().parseDiagram(proDefVo.getProcessstr());
				this.processDiagram = processDiagram;
			} catch (WfmServiceException e) {
				LfwLogger.error(e.getMessage(), e);
				throw new LfwRuntimeException(e.getMessage());
			}
		}
		return processDiagram;
	}
	public void setProcessDiagram(ProcessDiagram processDiagram) {
		this.processDiagram = processDiagram;
	}
	public String getExtProDefAttr0() {
		return extProDefAttr0;
	}
	public void setExtProDefAttr0(String extProDefAttr0) {
		this.extProDefAttr0 = extProDefAttr0;
	}
	public String getExtProDefAttr1() {
		return extProDefAttr1;
	}
	public void setExtProDefAttr1(String extProDefAttr1) {
		this.extProDefAttr1 = extProDefAttr1;
	}
	public String getExtProDefAttr2() {
		return extProDefAttr2;
	}
	public void setExtProDefAttr2(String extProDefAttr2) {
		this.extProDefAttr2 = extProDefAttr2;
	}
	public UFBoolean getIsLazy() {
		return isLazy;
	}
	public void setIsLazy(UFBoolean isLazy) {
		this.isLazy = isLazy;
	}
}
