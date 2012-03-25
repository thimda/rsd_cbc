package nc.uap.wfm.context;
public class BeforeAddSignTaskInfoCtx extends AddSignTaskInfoCtx {
	private static final long serialVersionUID = -632188841813278133L;
	private String logic;
	public String getLogic() {
		return logic;
	}
	public void setLogic(String logic) {
		this.logic = logic;
	}
	public enum Logic {
		Bunch, Combine
		// Bunch´®ÐÐ Combine ²¢ÐÐ
	}
}
