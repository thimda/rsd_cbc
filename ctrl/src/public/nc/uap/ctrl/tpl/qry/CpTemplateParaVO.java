package nc.uap.ctrl.tpl.qry;


public class CpTemplateParaVO {
	private static final long serialVersionUID = -3908877502149203994L;

	/**
	 * ��֯PK
	 */
	private String pk_org = null;

	/**
	 * ģ�����
	 */
	private int tempstyle = 0;

	/**
	 * ���ܽڵ����
	 */
	private String appid = null;

	/**
	 * ����ԱPK
	 */
	private String operator = null;
	
	/**
	 * �ڵ��ʶ
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