package nc.uap.wfm.cmd;
import java.util.Iterator;
import java.util.Set;
import nc.bs.framework.common.NCLocator;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.wfm.builder.ModelBuilder;
import nc.uap.wfm.completesgy.ICompleteSgy;
import nc.uap.wfm.context.HumActInfoEngCtx;
import nc.uap.wfm.context.PwfmContext;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.itf.IWfmAssignActorsBill;
import nc.uap.wfm.model.HumAct;
import nc.uap.wfm.model.HumActIns;
import nc.uap.wfm.model.IPort;
import nc.uap.wfm.model.ProDef;
import nc.uap.wfm.model.ProIns;
import nc.uap.wfm.model.StartEvent;
import nc.uap.wfm.model.Task;
import nc.uap.wfm.rejectsgy.RejectSgyManager;
import nc.vo.pub.lang.UFBoolean;
public class RejectTaskCmd extends AbstractCommand implements ICommand<Void> {
	public RejectTaskCmd() {
		super();
	}
	public Void execute() throws WfmServiceException {
		Task task = PwfmContext.getCurrentBpmnSession().getTask();
		if (isPerimitReject(task)) {
			/**
			 * 设置驳回意见
			 */
			task.setOpinion(PwfmContext.getCurrentBpmnSession().getOpinion());
			/**
			 * 调用执行器，设置驳回的相关状态信息
			 */
			taskExe.rejectTask(task);
			task.asyn();
			HumActInfoEngCtx rejectInfo = PwfmContext.getCurrentBpmnSession().getRejectInfo();
			if (rejectInfo == null) {
				throw new LfwRuntimeException("请选择回退步");
			}
			/**
			 * 对驳回节点进行处理和驳回到的节点信息拷贝
			 */
			String[] rejectUsers = rejectInfo.getUserPks();
			ProDef proDef = PwfmContext.getCurrentBpmnSession().getProDef();
			IPort rejectPort = proDef.getPorts().get(rejectInfo.getPortId());
			if (rejectUsers == null || rejectUsers.length == 0) {
				throw new LfwRuntimeException("请选择退回人");
			}
			if (rejectPort instanceof StartEvent) {
				this.handlerRejectStart(task);
			} else {
				if (rejectPort instanceof HumAct) {
					String scratchPad = PwfmContext.getCurrentBpmnSession().getScratchpad();
					humActInsExe.rejectHumActIns(task, task.getHumActIns(), (HumAct) rejectPort, rejectUsers, scratchPad);
				}
			}
			/**
			 * 删除平行任务
			 */
			deleteParaTask(task);
			/**
			 * 删除指派信息
			 */
			deleteAssignInfo(task);
		}
		return null;
	}
	/**
	 * 开始节点特殊处理
	 * 
	 * @param task
	 */
	public void handlerRejectStart(Task task) {
		HumActIns humActIns = task.getHumActIns();
		humActIns.setState(HumActIns.End);
		humActIns.setIsNotExe(UFBoolean.TRUE);
		humActIns.setIsNotPas(UFBoolean.FALSE);
		humActIns.setTasks(null);
		humActIns.asyn();
		ProIns proIns = task.getRootProIns();
		ProDef proDef = proIns.getProDef();
		StartEvent start = proDef.getStart();
		HumActIns newHumActIns = null;
		try {
			ProIns rootProIns = task.getRootProIns();
			newHumActIns = humActInsExe.createHumActIns(start);
			newHumActIns.setProIns(rootProIns);
			newHumActIns.setRootProIns(rootProIns);
			newHumActIns.setParent(humActIns);
			newHumActIns.setIsNotReject(UFBoolean.TRUE);
			newHumActIns.asyn();
			String userPk = proIns.getPk_starter();
			Task newTask = taskExe.createTask(newHumActIns);
			newTask.setPk_owner(userPk);
			newTask.setHumActIns(newHumActIns);
			newTask.setParent(task);
			newTask.setHandlepiece(Task.HandlerPiece_Rejected);
			newTask.setPriority("0");
			newTask.setPk_frmins(rootProIns.getPk_startfrmins());
			newTask.asyn();
		} catch (WfmServiceException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
	}
	/**
	 * 删除平行任务
	 */
	public void deleteParaTask(Task task) throws WfmServiceException {
		Task parentTask = task.getParent();
		parentTask = ModelBuilder.builder(parentTask);
		Set<Task> paralTasks = parentTask.getSubTasks();
		if (paralTasks == null || paralTasks.size() == 0) {
			return;
		}
		Iterator<Task> iter = paralTasks.iterator();
		Task tmpTask = null;
		HumActIns tmpHumActIns = null;
		while (iter.hasNext()) {
			tmpTask = iter.next();
			if (tmpTask.getPk_task().equalsIgnoreCase(task.getPk_task())) {
				continue;
			}
			if (tmpTask.getIsnotexe().booleanValue()) {
				continue;
			}
			taskExe.realDeleteTask(tmpTask);
			tmpHumActIns = tmpTask.getHumActIns();
			if (tmpHumActIns.getIsNotExe().booleanValue()) {
				continue;
			}
			humActInsExe.realDeleteHumActIns(tmpHumActIns);
		}
	}
	/**
	 * 删除指派信息
	 * 
	 * @param task
	 * @throws WfmServiceException
	 */
	public void deleteAssignInfo(Task task) throws WfmServiceException {
		IWfmAssignActorsBill assignBill = NCLocator.getInstance().lookup(IWfmAssignActorsBill.class);
		assignBill.deleteAssignActors(task.getRootProIns().getPk_proins(), task.getProDef().getId(), task.getHumActIns().getHumAct().getId());
	}
	/**
	 * 验证规则
	 * 
	 * @param humActIns
	 * @throws WfmServiceException
	 */
	private boolean isPerimitReject(Task task) throws WfmServiceException {
		/**
		 * 前加签任务不允许驳回
		 */
		if (Task.CreateType_BeforeAddSign.equalsIgnoreCase(task.getCreateType())) {
			throw new WfmServiceException("前加签任务不允许退回");
		}
		/**
		 * 传阅任务不允许驳回
		 */
		if (Task.CreateType_Deliver.equalsIgnoreCase(task.getCreateType())) {
			throw new WfmServiceException("传阅任务不允许退回");
		}
		/**
		 * 首先判断该任务所属的活动节点是否允许驳回
		 */
		if (!RejectSgyManager.getInstance().isNotPermit(task)) {
			throw new WfmServiceException("该任务节点不允许退回");
		}
		/**
		 * 该节点是会签节点，并且有多条任务，不允许退回 任务该节点有多条并行任务，完成策略不是抢占，待讨论
		 */
		HumActIns humActIns = ModelBuilder.builder(task.getHumActIns());
		if (ICompleteSgy.CompleteSgy_Occupy != humActIns.getHumAct().getCompleteStrategy().getStrategyType()) {
			Set<Task> tasks = humActIns.getTasks();
			if (tasks != null && tasks.size() > 1) {
				throw new LfwRuntimeException("该节点有多条并行任务，不允许退回");
			}
		}
		return true;
	}
}
