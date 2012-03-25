package nc.uap.wfm.context;
import nc.uap.lfw.core.exception.LfwRuntimeException;
public class CommitInfoCtx extends UnStartProInsInfoCtx {
	private static final long serialVersionUID = -340998830405303952L;
	private String pProInsPk;
	private String startTaskPk;
	private HumActInfoEngCtx[] nextInfo;
	public HumActInfoEngCtx[] getNextInfo() {
		return nextInfo;
	}
	public void setNextInfo(HumActInfoEngCtx[] nextInfo) {
		this.nextInfo = nextInfo;
	}
	public String getPProInsPk() {
		return pProInsPk;
	}
	public void setPProInsPk(String proInsPk) {
		pProInsPk = proInsPk;
	}
	public String getStartTaskPk() {
		return startTaskPk;
	}
	public void setStartTaskPk(String startTaskPk) {
		this.startTaskPk = startTaskPk;
	}
	public void check() {
		if (this.getFlowTypePk() == null || this.getFlowTypePk().length() == 0) {
			throw new LfwRuntimeException("流程提交必须提供流程类型Pk");
		}
	}
}
