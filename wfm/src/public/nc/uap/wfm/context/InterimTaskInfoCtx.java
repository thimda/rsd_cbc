package nc.uap.wfm.context;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.wfm.vo.WfmAssignActorsVO;
public class InterimTaskInfoCtx extends StartedProInsInfoCtx {
	private static final long serialVersionUID = -3940770111542499526L;
	private WfmAssignActorsVO[] assignActors;
	public WfmAssignActorsVO[] getAssignActors() {
		return assignActors;
	}
	public void setAssignActors(WfmAssignActorsVO[] assignActors) {
		this.assignActors = assignActors;
	}
	public void check() {
		if (this.getTaskPk() == null || this.getTaskPk().length() == 0) {
			throw new LfwRuntimeException("暂存任务必须提供任务Pk");
		}
	}
}
