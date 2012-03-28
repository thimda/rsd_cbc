package nc.uap.wfm.cmd;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import nc.bs.framework.common.NCLocator;
import nc.uap.lfw.core.InteractionUtil;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.wfm.completesgy.ICompleteSgy;
import nc.uap.wfm.context.PwfmContext;
import nc.uap.wfm.convert.ModelBuilder;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.handler.PortAndEdgeHandler;
import nc.uap.wfm.itf.IWfmAssignActorsBill;
import nc.uap.wfm.model.CompleteStrategy;
import nc.uap.wfm.model.EndEvent;
import nc.uap.wfm.model.HumAct;
import nc.uap.wfm.model.HumActIns;
import nc.uap.wfm.model.IPort;
import nc.uap.wfm.model.ProDef;
import nc.uap.wfm.model.ProIns;
import nc.uap.wfm.model.Task;
import nc.uap.wfm.utils.WfmTaskUtil;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
public class BackTaskCmd extends AbstractCommand implements ICommand<Void> {
	protected Task task = null;
	public BackTaskCmd() {
		super();
	}
	public Void execute() throws WfmServiceException {
		task = PwfmContext.getCurrentBpmnSession().getTask();
		if (task == null) {
			throw new LfwRuntimeException("空任务不允许取回");
		}
		if (!task.getIsnotexe().booleanValue()) {
			throw new LfwRuntimeException("未执行的任务不允许取回");
		}
		if (this.isPermit()) {
			HumActIns humActIns = task.getHumActIns();
			HumAct humAct = humActIns.getHumAct();
			if (ProIns.End.equalsIgnoreCase(task.getProIns().getState())) {
				if (task.getProDef().isNotBack()) {
					IPort[] ports = PortAndEdgeHandler.getNextHumActs(humAct);
					if (ports == null || ports.length == 0) {
						return null;
					}
					IPort tmpPort = null;
					for (int i = 0; i < ports.length; i++) {
						tmpPort = ports[i];
						if (tmpPort instanceof EndEvent) {
							try {
								this.backProIns();
								return null;
							} catch (WfmServiceException e) {
								LfwLogger.error(e.getMessage(), e);
								throw new LfwRuntimeException(e.getMessage());
							}
						}
					}
				} else {
					throw new LfwRuntimeException("该流程已经结束，不能够取回");
				}
			}
		}
		this.backNormalTask();
		return null;
	}
	private boolean isPermit() {
		if (Task.CreateType_Deliver.equalsIgnoreCase(task.getCreateType())) {
			throw new LfwRuntimeException("传阅任务不允许取回");
		}
		return true;
	}
	private void backProIns() throws WfmServiceException {
		HumActIns humActIns = task.getHumActIns();
		Set<HumActIns> humActInses = ModelBuilder.builder(humActIns).getSubHumActIns();
		if (humActInses == null || humActInses.size() == 0) {
			throw new LfwRuntimeException("无流程结束信息，反审核出错");
		}
		Iterator<HumActIns> iter = humActInses.iterator();
		HumActIns tmpHumActIns = null;
		IPort tmpPort = null;
		InteractionUtil.showConfirmDialog("是否反审核", "该流程已办结,是否收回重新处理");
		boolean f = InteractionUtil.getConfirmDialogResult();
		if (f) {
			while (iter.hasNext()) {
				tmpHumActIns = iter.next();
				tmpPort = tmpHumActIns.getPort();
				if (tmpPort instanceof EndEvent) {
					humActInsExe.realDeleteHumActIns(tmpHumActIns);
					continue;
				}
			}
			Task newTask = task.clone();
			newTask.setPk_task(null);
			newTask.setSignDate(null);
			taskExe.backTask(newTask);
			String userPk = PwfmContext.getCurrentBpmnSession().getCurrentUserPk();
			newTask.setStartDate(new UFDate());
			newTask.setPk_creater(userPk);
			newTask.asyn();
			humActIns.setIsNotPas(new UFBoolean(false));
			humActIns.setIsNotExe(new UFBoolean(false));
			humActIns.setState(HumActIns.Run);
			humActIns.asyn();
		}
	}
	private void backNormalTask() throws WfmServiceException {
		HumActIns humActIns = task.getHumActIns();
		HumAct humAct = humActIns.getHumAct();
		ProDef proDef = task.getProDef();
		IPort port = WfmTaskUtil.getStratPort(proDef);
		if (humAct.getId().equalsIgnoreCase(port.getId())) {
			throw new LfwRuntimeException("该任务在制单节点，不允许取回");
		}
		humActIns = ModelBuilder.builder(humActIns);
		this.handlerSubTasks(humActIns);
		this.handlerSubHumActIns(humActIns);
		taskExe.backTask(task);
		task.asyn();
		humActIns.setIsNotPas(new UFBoolean(false));
		humActIns.setIsNotExe(new UFBoolean(false));
		humActIns.setState(HumActIns.Run);
		humActIns.asyn();
	}
	private void handlerSubHumActIns(HumActIns humActIns) throws WfmServiceException {
		Set<HumActIns> subHumActIns = humActIns.getSubHumActIns();
		if (subHumActIns == null || subHumActIns.size() == 0) {
			return;
		}
		HumActIns tmpHumActIns = null;
		ProIns tmpProIns = null;
		IWfmAssignActorsBill assignBill = NCLocator.getInstance().lookup(IWfmAssignActorsBill.class);
		for (Iterator<HumActIns> iter = subHumActIns.iterator(); iter.hasNext();) {
			tmpHumActIns = iter.next();
			humActInsExe.realDeleteHumActIns(tmpHumActIns);
			tmpProIns = tmpHumActIns.getProIns();
			assignBill.deleteAssignActors(tmpProIns.getPk_proins(), tmpProIns.getProDef().getId(), tmpHumActIns.getHumAct().getId());
		}
	}
	private void handlerSubTasks(HumActIns humActIns) throws WfmServiceException {
		HumAct humAct = humActIns.getHumAct();
		CompleteStrategy sgy = humAct.getCompleteStrategy();
		if (sgy != null) {
			if (ICompleteSgy.CompleteSgy_Occupy != sgy.getStrategyType()) {
				Set<Task> taskSet = humActIns.getTasks();
				if (taskSet != null && taskSet.size() > 1) {
					throw new LfwRuntimeException("该节点是会签节点，并且有多人参与会签，不允许取回");
				}
			}
		}
		Set<Task> subTasks = task.getSubTasks();
		if (subTasks == null || subTasks.size() == 0) {
			return;
		}
		Map<String, HumActIns> map = new HashMap<String, HumActIns>();
		Task tmpTask = null;
		for (Iterator<Task> iter = subTasks.iterator(); iter.hasNext();) {
			tmpTask = iter.next();
			if (tmpTask.getSignDate() != null) {
				throw new LfwRuntimeException("该任务产生的子任务已经被签收，不允许取回");
			}
			map.put(tmpTask.getHumActIns().getPk_humactins(), tmpTask.getHumActIns());
			taskExe.realDeleteTask(tmpTask);
		}
		HumActIns tmpHumActIns = null;
		ProIns tmpProIns = null;
		IWfmAssignActorsBill assignBill = NCLocator.getInstance().lookup(IWfmAssignActorsBill.class);
		for (Iterator<String> iter = map.keySet().iterator(); iter.hasNext();) {
			tmpHumActIns = map.get(iter.next());
			humActInsExe.realDeleteHumActIns(tmpHumActIns);
			tmpProIns = tmpHumActIns.getProIns();
			assignBill.deleteAssignActors(tmpProIns.getPk_proins(), tmpProIns.getProDef().getId(), tmpHumActIns.getHumAct().getId());
		}
	}
}
