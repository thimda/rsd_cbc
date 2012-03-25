package nc.uap.wfm.context;
import nc.uap.lfw.core.exception.LfwRuntimeException;
public class ReStartAddSignTaskInfoCtx extends StartedProInsInfoCtx {
	private static final long serialVersionUID = -3173602800116027665L;
	public void check() {
		if (this.getTaskPk() == null || this.getTaskPk().length() == 0) {
			throw new LfwRuntimeException("重启加签任务必须提供任务Pk");
		}
	}
}
