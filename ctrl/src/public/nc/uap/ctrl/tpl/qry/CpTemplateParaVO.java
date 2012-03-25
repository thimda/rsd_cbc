package nc.uap.ctrl.tpl.qry;


public class CpTemplateParaVO {
	private static final long serialVersionUID = -3908877502149203994L;

	/**
	 * 组织PK
	 */
	private String pk_org = null;

	/**
	 * 模板类别
	 */
	private int tempstyle = 0;

	/**
	 * 功能节点编码
	 */
	private String appid = null;

	/**
	 * 操作员PK
	 */
	private String operator = null;
	
	/**
	 * 节点标识
	 */
	private String nodeKey = null;

	public String getPk_org() {
		return pk_org;
	}

	public void setPk_org(String pk_org) {
		this.pk_org = pk_org;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getNodeKey() {
		return nodeKey;
	}

	public void setNodeKey(String nodeKey) {
		this.nodeKey = nodeKey;
	}

	public int getTempstyle() {
		return tempstyle;
	}

	public void setTempstyle(int tempstyle) {
		this.tempstyle = tempstyle;
	}

	
}