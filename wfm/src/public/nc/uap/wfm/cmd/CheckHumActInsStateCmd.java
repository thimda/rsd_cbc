package nc.uap.wfm.cmd;
import java.util.Iterator;
import java.util.Set;
import nc.bs.framework.common.NCLocator;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.wfm.completesgy.CompleteSgyManager;
import nc.uap.wfm.context.PwfmContext;
import nc.uap.wfm.convert.ModelBuilder;
import nc.uap.wfm.engine.IHumActHandler;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.itf.IWfmAddSignBill;
import nc.uap.wfm.model.HumActIns;
import nc.uap.wfm.model.Task;
import nc.vo.pub.lang.UFBoolean;
public class CheckHumActInsStateCmd extends AbstractCommand implements ICommand<UFBoolean> {
	public CheckHumActInsStateCmd() {
		super();
	}
	public UFBoolean execute() throws WfmServiceException {
		Task task = PwfmContext.getCurrentBpmnSession().getTask();
		if (Task.ActionType_Assist.equalsIgnoreCase(task.getActionType())) {
			return new UFBoolean(false);
		}
		if (Task.CreateType_BeforeAddSign.equalsIgnoreCase(task.getCreateType())) {
			return new UFBoolean(false);
		}
		boolean flag = true;
		HumActIns humActIns = task.getHumActIns();
		flag = CompleteSgyManager.getInstance().isNotComplete(humActIns, null);
		if (flag) {
			String afterClass = humActIns.getHumAct().getDelegatorClass();
			try {
				IHumActHandler after = (IHumActHandler) Class.forName(afterClass).newInstance();
				after.afterHumAct();
			} catch (Exception e) {
				LfwLogger.error(e.getMessage(), e);
				throw new LfwRuntimeException(e.getMessage());
			}
			humActIns = ModelBuilder.builder(humActIns);
			Set<Task> tasks = humActIns.getTasks();
			if (tasks == null || tasks.size() == 0) {
				return new UFBoolean(flag);
			}
			Iterator<Task> iter = tasks.iterator();
			while (iter.hasNext()) {
				Task tmpTask = iter.next();
				if (Task.CreateType_Deliver.equalsIgnoreCase(tmpTask.getCreateType())) {
					continue;
				}
				if (Task.CreateType_BeforeAddSign.equalsIgnoreCase(tmpTask.getCreateType())) {
					continue;
				}
				if (!tmpTask.getIsnotexe().booleanValue()) {
					taskExe.realDeleteTask(tmpTask);
				}
			}
			NCLocator.getInstance().lookup(IWfmAddSignBill.class).deleteAddSignVoByTaskPk(task.getPk_task());
		}
		return new UFBoolean(flag);
	}
}
