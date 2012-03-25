package nc.uap.wfm.context;
import nc.uap.lfw.core.exception.LfwRuntimeException;
public class StopAddSignTaskInfoCtx extends StartedProInsInfoCtx {
	private static final long serialVersionUID = 2499639525946805835L;
	public void check() {
		if (this.getTaskPk() == null || this.getTaskPk().length() == 0) {
			throw new LfwRuntimeException("停止加签任务必须提供任务Pk");
		}
	}
}
