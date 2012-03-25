package nc.uap.wfm.context;
public class HumActInfoEngCtx {
	private String portId;
	private String[] userPks;
	private String[] msgTypes;
	public String getPortId() {
		return portId;
	}
	public void setPortId(String portId) {
		this.portId = portId;
	}
	public String[] getUserPks() {
		return userPks;
	}
	public void setUserPks(String[] userPks) {
		this.userPks = userPks;
	}
	public String[] getMsgTypes() {
		return msgTypes;
	}
	public void setMsgTypes(String[] msgTypes) {
		this.msgTypes = msgTypes;
	}
}
