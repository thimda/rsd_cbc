package nc.uap.wfm.model;
public class GateWay extends MultiPort {
	private static final long serialVersionUID = 7527851423504487247L;
	private String logic;
	private String count;
	public String getLogic() {
		return logic;
	}
	public void setLogic(String logic) {
		this.logic = logic;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
}
