package nc.uap.wfm.context;
public class HumActInfoPageCtx {
	private String portId;
	private String userPks;
	private String userNames;
	private boolean assign;
	private String portName;
	public String getPortId() {
		return portId;
	}
	public void setPortId(String portId) {
		this.portId = portId;
	}
	public String getUserPks() {
		return userPks;
	}
	public void setUserPks(String userPks) {
		this.userPks = userPks;
	}
	public String getUserNames() {
		return userNames;
	}
	public void setUserNames(String userNames) {
		this.userNames = userNames;
	}
	public boolean isAssign() {
		return assign;
	}
	public void setAssign(boolean isAssign) {
		this.assign = isAssign;
	}
	public String getPortName() {
		return portName;
	}
	public void setPortName(String portName) {
		this.portName = portName;
	}
}
