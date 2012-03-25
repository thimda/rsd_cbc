package nc.uap.wfm.environment;
import nc.uap.wfm.model.Task;
public final class WfmRuntimeEnvironment {
	protected static ThreadLocal<EnviromentObject> threadLocal = new ThreadLocal<EnviromentObject>();
}
class EnviromentObject {
	protected String userPk;
	protected Task task;
	protected void reset() {
		userPk = null;
		task = null;
	}
}
