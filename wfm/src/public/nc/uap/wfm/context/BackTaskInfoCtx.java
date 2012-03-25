package nc.uap.wfm.context;
import nc.uap.lfw.core.exception.LfwRuntimeException;
public class BackTaskInfoCtx extends StartedProInsInfoCtx {
	private static final long serialVersionUID = 3996080133212688173L;
	public void check() {
		if (this.getTaskPk() == null || this.getTaskPk().length() == 0) {
			throw new LfwRuntimeException("任务回退必须提供任务Pk");
		}
	}
}
