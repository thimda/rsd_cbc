package nc.uap.wfm.context;
import nc.uap.lfw.core.exception.LfwRuntimeException;
public class RetractTaskInfoCtx extends StartedProInsInfoCtx {
	private static final long serialVersionUID = -4929200234418725469L;
	public void check() {
		if (this.getTaskPk() == null || this.getTaskPk().length() == 0) {
			throw new LfwRuntimeException("收回任务必须提供任务Pk");
		}
	}
}
