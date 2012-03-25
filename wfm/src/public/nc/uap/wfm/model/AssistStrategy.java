package nc.uap.wfm.model;
import nc.uap.wfm.actorsgy.IActorSgy;
public class AssistStrategy extends AbstractStrategy implements IActorSgy {
	private String users;
	private String roles;
	private String depts;
	private String virtualRole;
	private String someAsOthers;
	private String selfDefClass;
	
	public String getUsers() {
		return users;
	}
	public void setUsers(String users) {
		this.users = users;
	}
	public String getRoles() {
		return roles;
	}
	public void setRoles(String roles) {
		this.roles = roles;
	}
	public String getDepts() {
		return depts;
	}
	public void setDepts(String depts) {
		this.depts = depts;
	}
	public String getSomeAsOthers() {
		return someAsOthers;
	}
	public void setSomeAsOthers(String someAsOthers) {
		this.someAsOthers = someAsOthers;
	}
	public String getSelfDefClass() {
		return selfDefClass;
	}
	public void setSelfDefClass(String selfDefClass) {
		this.selfDefClass = selfDefClass;
	}
	
	public String getVirtualRole() {
		return virtualRole;
	}
	public void setVirtualRole(String virtualRole) {
		this.virtualRole = virtualRole;
	}
	@Override
	public int getStrategyType() {
		return 0;
	}
}
