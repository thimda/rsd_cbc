package nc.uap.wfm.context;
public abstract class StartedProInsInfoCtx extends WfmFlowInfoCtx {
	private static final long serialVersionUID = -2071132709758206474L;
	protected String taskPk;
	public String getTaskPk() {
		return taskPk;
	}
	public void setTaskPk(String taskPk) {
		this.taskPk = taskPk;
	}
}
