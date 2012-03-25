package nc.uap.wfm.context;
import nc.uap.lfw.core.exception.LfwRuntimeException;
public class ExeDeliverInfoCtx extends StartedProInsInfoCtx {
	private static final long serialVersionUID = 2364190195479307397L;
	public void check() {
		if (this.getTaskPk() == null || this.getTaskPk().length() == 0) {
			throw new LfwRuntimeException("执行传阅任务必须提供任务Pk");
		}
	}
}
