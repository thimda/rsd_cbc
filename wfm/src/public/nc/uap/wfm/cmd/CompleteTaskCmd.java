package nc.uap.wfm.cmd;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import nc.bs.framework.common.NCLocator;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.wfm.constant.WfmConstants;
import nc.uap.wfm.context.NextTaskInfoCtx;
import nc.uap.wfm.context.PwfmContext;
import nc.uap.wfm.context.WfmFlowInfoCtx;
import nc.uap.wfm.convert.ModelBuilder;
import nc.uap.wfm.engine.IHumActHandler;
import nc.uap.wfm.engine.ITaskExtHandler;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.ext.ProDefExt;
import nc.uap.wfm.itf.IWfmAssignActorsBill;
import nc.uap.wfm.itf.IWfmTaskTokenBill;
import nc.uap.wfm.message.TaskMessageGatherUtil;
import nc.uap.wfm.message.TaskMessageSenderMgr;
import nc.uap.wfm.model.HumActIns;
import nc.uap.wfm.model.ProIns;
import nc.uap.wfm.model.Task;
import nc.uap.wfm.scratchpad.TaskScratchpadUtil;
import nc.uap.wfm.trans.WfmTransUtil;
import nc.uap.wfm.utils.WfmClassUtil;
import nc.uap.wfm.vo.WfmFormInfoCtx;
import nc.uap.wfm.vo.WfmMyVisaVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDateTime;
/**
 * 完成任务命令
 * 
 * @author tianchw
 * 
 */
public class CompleteTaskCmd extends AbstractCommand implements ICommand<Task> {
	public CompleteTaskCmd() {
		super();
	}
	public Task execute() throws WfmServiceException {
		Task task = PwfmContext.getCurrentBpmnSession().getTask();
		if (Task.State_BeforeAddSignPlmnt.equalsIgnoreCase(task.getState())) {
			throw new LfwRuntimeException("加签中的任务不允许执行");
		}
		ProIns proIns = task.getProIns();
		proIns.setState(ProIns.Run);
		proIns.asyn();
		HumActIns humActIns = task.getHumActIns();
		String serverClass = humActIns.getHumAct().getDelegatorClass();
		IHumActHandler humActHandler = (IHumActHandler) WfmClassUtil.loadClass(serverClass);
		String taskExtClazz = humActHandler.getTaskExtClazz();
		ITaskExtHandler taskExthandler = (ITaskExtHandler) WfmClassUtil.loadClass(taskExtClazz);
		taskExthandler.beforeCmpltTask(task);
		String opinion = PwfmContext.getCurrentBpmnSession().getOpinion();
		task.setOpinion(opinion);
		if (ProIns.NottStart.equalsIgnoreCase(task.getProIns().getState())) {
			task.setProInsStartDate(task.getProIns().getStartDate());
		}
		WfmFormInfoCtx formVo = PwfmContext.getCurrentBpmnSession().getFormVo();
		ProDefExt.handlerExtAttr(task, formVo);
		String scratchpad = PwfmContext.getCurrentBpmnSession().getScratchpad();
		if (Task.CreateType_BeforeAddSign.equalsIgnoreCase(task.getCreateType())) {
			if (scratchpad != null && scratchpad.length() != 0) {
				Task parentTask = task.getParent();
				if (parentTask != null) {
					parentTask = ModelBuilder.builder(parentTask);
					String scratchPad = parentTask.getScratchpad();
					if (scratchPad == null) {
						scratchPad = "";
					}
					scratchPad = scratchPad + "\n\n" + TaskScratchpadUtil.getScratchpad(WfmTransUtil.getUserNameByUserPk(task.getPk_owner()), task.getStartDate().toString(), scratchpad);
					parentTask.setScratchpad(scratchPad);
					parentTask.asyn();
				}
			}
		}
		task.setIsnotpas(UFBoolean.TRUE);
		WfmMyVisaVO myVisaVo = PwfmContext.getCurrentBpmnSession().getMyVisa();
		if (myVisaVo != null) {
			task.setPk_myvisa(myVisaVo.getPk_lfwfile());
		}
		task.setExt8("N");
		taskExe.completeTask(task);
		WfmFlowInfoCtx flwInfoCtx = PwfmContext.getCurrentBpmnSession().getFlwInfoCtx();
		if (flwInfoCtx instanceof NextTaskInfoCtx) {
			NextTaskInfoCtx nextFlwInfoCtx = (NextTaskInfoCtx) flwInfoCtx;
			String finishType = nextFlwInfoCtx.getFinishType();
			if (finishType != null && finishType.length() != 0) {
				if (WfmConstants.StrCountersign.equalsIgnoreCase(finishType)) {
					task.setFinishType(Task.FinishType_Countersign);
				} else if (WfmConstants.StrSignstart.equalsIgnoreCase(finishType)) {
					task.setFinishType(Task.FinishType_SignStart);
				}
			}
		}
		Map<String, Object> messageMap = getMessageMap(task);
		TaskMessageSenderMgr.sendTaskCompletedMessage(messageMap);
		task.asyn();
		taskExthandler.afterCmpltTask(task);
		humActIns.setIsNotPas(UFBoolean.FALSE);
		humActIns.setIsNotExe(UFBoolean.TRUE);
		humActIns.asyn();
		this.updatePriority(task);
		this.deleteAssignInfo(task);
		this.setEndParalTaskState(task);
		this.setSendAddSignTaskState(task);
		if (formVo != null) {
			formVo.setAttributeValue(formVo.getFrmAuditUserField(), PwfmContext.getCurrentBpmnSession().getCurrentUserPk());
			formVo.setAttributeValue(formVo.getFrmAuditDateField(), new UFDateTime());
			formVo.setAttributeValue(formVo.getFrmStateField(), ProIns.Run);
		}
		return task;
	}
	/**
	 *更新发送加签任务的状态
	 * 
	 * @param task
	 */
	public void setSendAddSignTaskState(Task task) {
		if (Task.CreateType_BeforeAddSign.equalsIgnoreCase(task.getCreateType())) {
			Task parentTask = task.getParent();
			if (parentTask == null) {
				return;
			}
			try {
				parentTask = ModelBuilder.builder(parentTask);
			} catch (WfmServiceException e1) {
				LfwLogger.error(e1.getMessage(), e1);
				throw new LfwRuntimeException(e1.getMessage());
			}
			Set<Task> children = null;
			try {
				children = ModelBuilder.builder(task.getParent()).getSubTasks();
			} catch (WfmServiceException e) {
				LfwLogger.error(e.getMessage(), e);
				throw new LfwRuntimeException(e.getMessage());
			}
			Iterator<Task> iter = children.iterator();
			int addsignCount = 0;
			int finishAddSigncount = 0;
			Task tmpTask = null;
			while (iter.hasNext()) {
				tmpTask = iter.next();
				if (Task.CreateType_BeforeAddSign.equalsIgnoreCase(tmpTask.getCreateType())) {
					addsignCount++;
					if (tmpTask.getIsnotexe().booleanValue() || Task.State_BeforeAddSignStop.equalsIgnoreCase(tmpTask.getState())) {
						finishAddSigncount++;
					}
				}
			}
			if (addsignCount == finishAddSigncount) {
				parentTask.setState(Task.State_BeforeAddSignCmplt);
			} else {
				parentTask.setState(Task.State_BeforeAddSignPlmnt);
			}
			parentTask.asyn();
		}
	}
	/**
	 * 收集发送信息
	 * 
	 * @param task
	 * @return
	 */
	public Map<String, Object> getMessageMap(Task task) {
		return TaskMessageGatherUtil.getMessageMap(task, task.getProIns().getPk_starter());
	}
	/**
	 * 任务完成后处理平行任务状态
	 * 
	 * @param task
	 * @throws WfmServiceException
	 */
	public void setEndParalTaskState(Task task) throws WfmServiceException {}
	/**
	 * 更新任务的优先级别
	 * 
	 * @param task
	 * @throws WfmServiceException
	 */
	public void updatePriority(Task task) throws WfmServiceException {
		HumActIns humActIns = task.getHumActIns();
		humActIns = ModelBuilder.builder(humActIns);
		Set<Task> tasks = humActIns.getTasks();
		Iterator<Task> iter = tasks.iterator();
		Task tmpTask = null;
		int tmpPriority = 0;
		while (iter.hasNext()) {
			tmpTask = iter.next();
			if (tmpTask.getIsnotexe().booleanValue()) {
				continue;
			}
			tmpPriority = Integer.parseInt(tmpTask.getPriority());
			if (tmpPriority == 0) {
				continue;
			}
			tmpTask.setPriority(String.valueOf(tmpPriority - 1));
			tmpTask.asyn();
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
	 * 删除口令信息
	 * 
	 * @param task
	 * @throws WfmServiceException
	 */
	public void deteleTaskToken(String tokenId, Task task) throws WfmServiceException {
		IWfmTaskTokenBill taskTokenBill = NCLocator.getInstance().lookup(IWfmTaskTokenBill.class);
		if (tokenId == null || tokenId.length() == 0) {
			taskTokenBill.deleteTaskTokenByTaskPk(task.getPk_task());
		} else {
			taskTokenBill.deleteTaskTokenByTokenId(tokenId);
		}
	}
}
