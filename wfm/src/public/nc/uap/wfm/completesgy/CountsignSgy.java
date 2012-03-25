package nc.uap.wfm.completesgy;
import java.util.Iterator;
import java.util.Set;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.wfm.builder.ModelBuilder;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.model.HumActIns;
import nc.uap.wfm.model.Task;
public class CountsignSgy extends AbstractSgyService {
	public boolean isNotComplete(HumActIns humActIns, Integer count) {
		try {
			humActIns = ModelBuilder.flwBuilder(humActIns);
		} catch (WfmServiceException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
		Set<Task> tasks = this.fiterTask(humActIns.getTasks());
		if (count == null) {
			count = new Integer(0);
			if (tasks == null || tasks.size() == 0) {
				return false;
			}
			Iterator<Task> iter = tasks.iterator();
			while (iter.hasNext()) {
				Task task = iter.next();
				if (task.getIsnotexe().booleanValue() || Task.State_BeforeAddSignStop.equals(task.getState())) {
					count++;
				}
			}
		}
		if (count == tasks.size()) {
			return true;
		} else {
			return false;
		}
	}
}
