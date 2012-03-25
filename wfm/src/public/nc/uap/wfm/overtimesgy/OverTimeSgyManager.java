package nc.uap.wfm.overtimesgy;
import nc.uap.wfm.engine.IHumActHandler;
import nc.uap.wfm.engine.ITaskExtHandler;
import nc.uap.wfm.model.HumAct;
import nc.uap.wfm.model.HumActIns;
import nc.uap.wfm.model.MessageStrategy;
import nc.uap.wfm.model.Task;
import nc.uap.wfm.utils.WfmClassUtil;
/**
 * 完成策略管理器
 * 
 * @author tianchw
 * 
 */
public class OverTimeSgyManager implements IOverTimeSgy {
	public static boolean isNotOverTime(Task task) {
		if (task.getHumActIns().getHumAct() == null) {
			return false;
		}
		String serverClass = task.getHumActIns().getHumAct().getDelegatorClass();
		IHumActHandler humActHandler = (IHumActHandler) WfmClassUtil.loadClass(serverClass);
		String taskExtClazz = humActHandler.getTaskExtClazz();
		ITaskExtHandler taskExthandler = (ITaskExtHandler) WfmClassUtil.loadClass(taskExtClazz);
		return taskExthandler.isOverTime(task);
	}
	public static boolean isNotRemind(Task task) {
		HumActIns humActIns = task.getHumActIns();
		HumAct humAct = humActIns.getHumAct();
		MessageStrategy sgy = humAct.getMessageStrategy();
		if (sgy.isNotControlTime()) {
			return true;
		} else {
			return false;
		}
	}
	public static int getRemindType(Task task) {
		// HumActIns humActIns = task.getHumActIns();
		// HumAct humAct = humActIns.getHumAct();
		// MessageStrategy sgy = humAct.getOverTimeStrategy();
		// if (sgy != null) {
		// return sgy.getRemindSgy();
		// }
		return 0;
	}
	public static int getOverTimeAction(Task task) {
		// HumActIns humActIns = task.getHumActIns();
		// HumAct humAct = humActIns.getHumAct();
		// MessageStrategy sgy = humAct.getOverTimeStrategy();
		// if (sgy != null) {
		// return sgy.getActionSgy();
		// }
		return 0;
	}
}
