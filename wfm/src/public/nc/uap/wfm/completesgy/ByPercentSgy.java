package nc.uap.wfm.completesgy;
import java.util.Iterator;
import java.util.Set;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.wfm.convert.ModelBuilder;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.model.HumAct;
import nc.uap.wfm.model.HumActIns;
import nc.uap.wfm.model.Task;
import nc.uap.wfm.next.NextCalculator;
public class ByPercentSgy extends AbstractSgyService {
	public boolean isNotComplete(HumActIns humActIns, Integer count) {
		try {
			humActIns = ModelBuilder.builder(humActIns);
		} catch (WfmServiceException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
		HumAct humAct = humActIns.getHumAct();
		Set<Task> tasks = this.fiterTask(humActIns.getTasks());
		if (count == null) {
			count = new Integer(0);
			if (tasks == null || tasks.size() == 0) {
				return false;
			}
			Iterator<Task> iter = tasks.iterator();
			Task task = null;
			while (iter.hasNext()) {
				task = iter.next();
				if (NextCalculator.isNotFinishAddSignTaskByTask(task) && task.getIsnotexe().booleanValue()) {
					count++;
				}
			}
		}
		String percent = humAct.getCompleteStrategy().getPercent();
		if (percent == null || percent.length() == 0) {
			return false;
		}
		if (count / tasks.size() >= Integer.parseInt(percent) / 100) {
			return true;
		} else {
			return false;
		}
	}
}
