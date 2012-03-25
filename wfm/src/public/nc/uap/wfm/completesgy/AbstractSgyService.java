package nc.uap.wfm.completesgy;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.wfm.model.Task;
import org.apache.commons.collections.CollectionUtils;
public abstract class AbstractSgyService implements ICompleteSgyService {
	public Set<Task> fiterTask(Set<Task> tasks) {
		if (tasks == null || tasks.size() == 0) {
			return null;
		}
		Set<Task> copyTasks = new HashSet<Task>();
		CollectionUtils.addAll(copyTasks, tasks.toArray(new Task[0]));
		Iterator<Task> iter = copyTasks.iterator();
		while (iter.hasNext()) {
			Task task = iter.next();
			if (Task.ActionType_Deliver.equalsIgnoreCase(task.getActionType())) {
				tasks.remove(task);
			}
			if (Task.ActionType_Assist.equalsIgnoreCase(task.getActionType())) {
				if (!task.getIsnotexe().booleanValue()) {
					throw new LfwRuntimeException("协办任务还没有完成，流程不允许推进");
				}
				tasks.remove(task);
			}
		}
		return tasks;
	}
}
