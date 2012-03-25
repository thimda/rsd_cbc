package nc.uap.wfm.context;
import nc.uap.lfw.core.exception.LfwRuntimeException;
public class OverTimeTaskInfoCtx extends StartedProInsInfoCtx {
	private static final long serialVersionUID = -3007650760597352036L;
	public void check() {
		if (this.getTaskPk() == null || this.getTaskPk().length() == 0) {
			throw new LfwRuntimeException("计算任务超时必须提供任务Pk");
		}
	}
}
