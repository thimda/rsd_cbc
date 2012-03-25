package nc.uap.wfm.rejectsgy;
import nc.uap.wfm.model.HumAct;
import nc.uap.wfm.model.HumActIns;
import nc.uap.wfm.model.Task;
public class PreviousHumAct extends RejectSgyService {
	@Override
	HumAct[] getRejectRange(Task task) {
		HumAct[] humActs = new HumAct[1];
		Task pTask = task.getParent();
		if (pTask == null) {
			return null;
		}
		HumActIns humActIns = pTask.getHumActIns();
		HumAct humAct = humActIns.getHumAct();
		humActs[0] = humAct;
		return humActs;
	}
}
