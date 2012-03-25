package nc.uap.wfm.context;
import nc.uap.lfw.core.exception.LfwRuntimeException;
public class RejectTaskInfoCtx extends StartedProInsInfoCtx {
	private static final long serialVersionUID = -8285584917979263144L;
	private HumActInfoEngCtx rejectInfo;
	public HumActInfoEngCtx getRejectInfo() {
		return rejectInfo;
	}
	public void setRejectInfo(HumActInfoEngCtx rejectInfo) {
		this.rejectInfo = rejectInfo;
	}
	public void check() {
		if (this.getTaskPk() == null || this.getTaskPk().length() == 0) {
			throw new LfwRuntimeException("任务驳回必须提供任务Pk");
		}
	}
}
