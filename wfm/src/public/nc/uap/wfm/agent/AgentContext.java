package nc.uap.wfm.agent;

/**
 * ������������
 * @author licza
 *
 */
public class AgentContext {
	/**�û�����**/
	private String user;
	/** �������� **/
	private String frmtype;
	/**��������**/
	private String dept;
	/**������**/
	private String agent;
 	public String getFrmtype() {
		return frmtype;
	}
	public void setFrmtype(String frmtype) {
		this.frmtype = frmtype;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getAgent() {
		return agent;
	}
	public void setAgent(String agent) {
		this.agent = agent;
	}
	
	/**
	 * Ĭ�Ϲ��캯��
	 */
	public AgentContext() {
		super();
	}
	/**
	 * ������������
	 * @param user �û�PK
	 * @param frmtype ��������PK
	 * @param dept ����PK
	 */
	public AgentContext(String user, String frmtype, String dept) {
		super();
		this.user = user;
		this.frmtype = frmtype;
		this.dept = dept;
	}
	
}
