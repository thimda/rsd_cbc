package nc.uap.wfm.context;
public class InterimProInsInfoCtx extends WfmFlowInfoCtx {
	private static final long serialVersionUID = 5469341720758487179L;
	private String frmDefPk;
	private String pProInsPk;
	private String startTaskPk;
	private String taskPk;
	public String getTaskPk() {
		return taskPk;
	}
	public void setTaskPk(String taskPk) {
		this.taskPk = taskPk;
	}
	public String getPProInsPk() {
		return pProInsPk;
	}
	public void setPProInsPk(String proInsPk) {
		pProInsPk = proInsPk;
	}
	public String getFrmDefPk() {
		return frmDefPk;
	}
	public void setFrmDefPk(String frmDefPk) {
		this.frmDefPk = frmDefPk;
	}
	public String getStartTaskPk() {
		return startTaskPk;
	}
	public void setStartTaskPk(String startTaskPk) {
		this.startTaskPk = startTaskPk;
	}
	public void check() {}
}
