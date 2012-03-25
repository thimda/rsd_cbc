package nc.uap.wfm.gui;
/**
 * 2011-5-26 上午09:30:33 limingf
 */
public class Element {
	private static final long serialVersionUID = 2448015201519911936L;
	private String id = "";
	private String name = "";
	// 节点执行状态,0,未处理（默认值），1,待办，2，在办,3,已办，4，停办，5，退回，
	// _state+8,当前状态，_state+16,加签，_state+24，改派
	private int state = 0;
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
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
}
