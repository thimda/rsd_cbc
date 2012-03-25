package nc.uap.wfm.completesgy;
import java.util.Set;
import nc.uap.wfm.model.HumActIns;
import nc.uap.wfm.model.Task;
public interface ICompleteSgyService {
	boolean isNotComplete(HumActIns humActIns, Integer count);
	Set<Task> fiterTask(Set<Task> tasks);
}
