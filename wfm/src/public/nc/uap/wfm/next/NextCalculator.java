package nc.uap.wfm.next;

import java.util.Iterator;
import java.util.Set;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.wfm.builder.ModelBuilder;
import nc.uap.wfm.completesgy.CompleteSgyManager;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.model.HumActIns;
import nc.uap.wfm.model.Task;

public class NextCalculator {
	
	public static boolean isNotFinishAddSignTaskByTask(Task task) {
		try {
			task = ModelBuilder.builder(task);
			Set<Task> childern = task.getSubTasks();
			if (childern == null || childern.size() == 0) {
				return true;
			}
			Iterator<Task> iter = childern.iterator();
			Task tmpTask = null;
			while (iter.hasNext()) {
				tmpTask = iter.next();
				if (Task.CreateType_BeforeAddSign.equalsIgnoreCase(tmpTask.getCreateType())
						&& !(Task.State_BeforeAddSignCmplt.equals(tmpTask.getState()) || Task.State_BeforeAddSignStop.equals(tmpTask.getState()) || Task.State_End.equals(tmpTask.getState()))) {
					return false;
				}
			}
			return true;
		} catch (WfmServiceException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
	}
	public static boolean isNeedNextHumAct(Task task) throws WfmServiceException {
		if (Task.CreateType_BeforeAddSign.equalsIgnoreCase(task.getCreateType())) {
			return false;
		}
		/**
		 * 又这个任务发出的加签任务没有完成，直接返回
		 */
		if (!NextCalculator.isNotFinishAddSignTaskByTask(task)) {
			return false;
		}
		HumActIns humActIns = ModelBuilder.builder(task.getHumActIns());
		Set<Task> tasks = humActIns.getTasks();
		Iterator<Task> iter = tasks.iterator();
		Task tmpTask = null;
		int count = 0;
		while (iter.hasNext()) {
			tmpTask = iter.next();
			if (tmpTask.getIsnotexe().booleanValue() || Task.State_BeforeAddSignStop.equals(tmpTask.getState()) || Task.State_UnRead.equals(tmpTask.getState())) {
				count++;
			}
		}
		CompleteSgyManager cmpltSgyMgr = CompleteSgyManager.getInstance();
		if (cmpltSgyMgr.isNotComplete(humActIns, count + 1)) {
			return true;
		} else {
			return false;
		}
	}
}
