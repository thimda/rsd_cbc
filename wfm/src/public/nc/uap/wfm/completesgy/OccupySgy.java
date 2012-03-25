package nc.uap.wfm.completesgy;
import java.util.Iterator;
import java.util.Set;
import nc.bs.logging.Logger;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.wfm.builder.ModelBuilder;
import nc.uap.wfm.model.HumActIns;
import nc.uap.wfm.model.Task;
import nc.uap.wfm.next.NextCalculator;
public class OccupySgy extends AbstractSgyService {
	public boolean isNotComplete(HumActIns humActIns, Integer count) {
		try {
			if (count != null && 1 == count) {
				return true;
			}
			humActIns = ModelBuilder.builder(humActIns);
			Set<Task> tasks = this.fiterTask(humActIns.getTasks());
			if (tasks == null || tasks.size() == 0) {
				return false;
			}
			if (count != null && count == tasks.size()) {
				return true;
			}
			Iterator<Task> iter = tasks.iterator();
			while (iter.hasNext()) {
				Task task = iter.next();
				if (NextCalculator.isNotFinishAddSignTaskByTask(task) && task.getIsnotexe().booleanValue()) {
					return true;
				}
			}
		} catch (Exception e) {
			Logger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage(), e);
		}
		return false;
	}
}
