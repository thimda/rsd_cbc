package nc.uap.wfm.agent;

/**
 * 代理人上下文
 * @author licza
 *
 */
public class AgentContext {
	/**用户主键**/
	private String user;
	/** 单据类型 **/
	private String frmtype;
	/**部门主键**/
	private String dept;
	/**代理人**/
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
	 * 默认构造函数
	 */
	public AgentContext() {
		super();
	}
	/**
	 * 代理人上下文
	 * @param user 用户PK
	 * @param frmtype 单据类型PK
	 * @param dept 部门PK
	 */
	public AgentContext(String user, String frmtype, String dept) {
		super();
		this.user = user;
		this.frmtype = frmtype;
		this.dept = dept;
	}
	
}
