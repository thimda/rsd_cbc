package nc.uap.wfm.vo.base;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDateTime;
public class ProDefBaseVO extends SuperVO {
	private static final long serialVersionUID = 1L;
	/**
	 * 编码
	 */
	private String id;
	/**
	 * 主键
	 */
	private String pk_prodef;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 描述
	 */
	private String memo;
	/**
	 * 版本
	 */
	private String version;
	/**
	 * 流程XML
	 */
	private byte[] processxml;
	/**
	 * 流程分类
	 */
	private String flwtype;
	/**
	 * 流程启动表单
	 */
	private String pk_startfrm;
	/**
	 * 流程监控角色组标识
	 */
	private String watchrolepks;
	/**
	 * 流程监控角色组名称
	 */
	private String watchrolenames;
	/**
	 * 扩张属性控制类
	 */
	private String serverclass;
	private UFBoolean isnotstartup;
	public UFBoolean getIsnotstartup() {
		return isnotstartup;
	}
	public void setIsnotstartup(UFBoolean isnotstartup) {
		this.isnotstartup = isnotstartup;
	}
	private UFDateTime ts;
	private UFBoolean dr;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public byte[] getProcessxml() {
		return processxml;
	}
	
	public void setProcessxml(byte[] processxml) {
		this.processxml = processxml;
	}
	
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getPk_prodef() {
		return pk_prodef;
	}
	public void setPk_prodef(String pk_prodef) {
		this.pk_prodef = pk_prodef;
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
		return "pk_prodef";
	}
	public String getTableName() {
		return "wfm_prodef";
	}
	public String getWatchrolepks() {
		return watchrolepks;
	}
	public void setWatchrolepks(String watchrolepks) {
		this.watchrolepks = watchrolepks;
	}
	public String getWatchrolenames() {
		return watchrolenames;
	}
	public void setWatchrolenames(String watchrolenames) {
		this.watchrolenames = watchrolenames;
	}
	public String getFlwtype() {
		return flwtype;
	}
	public void setFlwtype(String flwtype) {
		this.flwtype = flwtype;
	}
	public String getPk_startfrm() {
		return pk_startfrm;
	}
	public void setPk_startfrm(String pk_startfrm) {
		this.pk_startfrm = pk_startfrm;
	}
	public String getServerclass() {
		return serverclass;
	}
	public void setServerclass(String serverclass) {
		this.serverclass = serverclass;
	}
}
