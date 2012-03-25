package nc.uap.wfm.context;
public abstract class UnStartProInsInfoCtx extends WfmFlowInfoCtx {
	private static final long serialVersionUID = 4074435946667599069L;
	protected String flowTypePk;
	public String getFlowTypePk() {
		return flowTypePk;
	}
	public void setFlowTypePk(String flowTypePk) {
		this.flowTypePk = flowTypePk;
	}
}
