package nc.uap.wfm.cmd;
import java.util.Map;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.wfm.agent.AgentContext;
import nc.uap.wfm.agent.UserAgentHelper;
import nc.uap.wfm.completesgy.ICompleteSgy;
import nc.uap.wfm.constant.WfmConstants;
import nc.uap.wfm.context.PwfmContext;
import nc.uap.wfm.engine.IHumActHandler;
import nc.uap.wfm.engine.ITaskExtHandler;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.ext.ProDefExt;
import nc.uap.wfm.message.TaskMessageGatherUtil;
import nc.uap.wfm.message.TaskMessageSenderMgr;
import nc.uap.wfm.model.HumAct;
import nc.uap.wfm.model.HumActIns;
import nc.uap.wfm.model.ProIns;
import nc.uap.wfm.model.Task;
import nc.uap.wfm.scratchpad.TaskScratchpadUtil;
import nc.uap.wfm.trans.WfmTransUtil;
import nc.uap.wfm.utils.WfmClassUtil;
import nc.vo.pub.lang.UFBoolean;
import org.apache.commons.lang.StringUtils;
public class CreateTaskCmd extends AbstractCommand implements ICommand<Task> {
	private Task task = null;
	private Task pTask = null;
	private HumActIns humActIns = null;
	private ProIns cntProIns = null;
	private ProIns rootProIns = null;
	private HumAct humAct = null;
	private String ownerPk = null;
	private String cntActionType = null;
	public CreateTaskCmd(Task task, Task pTask, HumActIns humActIns, ProIns cntProIns, ProIns rootProIns, HumAct humAct, String ownerPk, String cntActionType) {
		super();
		this.task = task;
		this.pTask = pTask;
		this.humActIns = humActIns;
		this.cntProIns = cntProIns;
		this.rootProIns = rootProIns;
		this.humAct = humAct;
		this.ownerPk = ownerPk;
		this.cntActionType = cntActionType;
	}
	public Task execute() throws WfmServiceException {
		try {
			task = taskExe.createTask(humActIns);
		} catch (WfmServiceException e) {
			throw new LfwRuntimeException(e);
		}
		task.setPk_owner(ownerPk);
		task.setParent(pTask);
		task.setPk_frmins(PwfmContext.getCurrentBpmnSession().getFrmInsPk());
		task.setRootProIns(rootProIns);
		task.setPk_frmdef(humAct.getPk_formdefinition());
		task.setCreateType(Task.CreateType_Normal);
		task.setProInsStartDate(cntProIns.getStartDate());
		task.setHandlepiece(Task.HandlerPiece_UnRead);
		task.setFlowType(humActIns.getProIns().getProDef().getFlwtype());
		this.handlerTaskType();
		this.handlerScratchPad();
		this.handlerAgenter();
		this.handlerDeliver();
		this.handlerExtAttr();
		this.handlerAfterTask();
		this.handlerMsgSender();
		this.handlerPriority();
		task.asyn();
		return task;
	}
	/**
	 * 任务类型处理
	 */
	public void handlerTaskType() {
		if (HumAct.ActionType_Normal.equalsIgnoreCase(cntActionType)) {
			task.setActionType(Task.ActionType_Normal);
		} else if (HumAct.ActionType_Deliver.equalsIgnoreCase(cntActionType)) {
			task.setActionType(Task.ActionType_Deliver);
		} else {
			if (HumAct.ActionType_Major.equalsIgnoreCase(cntActionType)) {
				task.setActionType(Task.ActionType_Major);
			}
			if (HumAct.ActionType_Assist.equalsIgnoreCase(cntActionType)) {
				task.setActionType(Task.ActionType_Assist);
			}
		}
	}
	/**
	 * 便签处理
	 * 
	 * @param task
	 */
	public void handlerScratchPad() {
		String scratchPad = PwfmContext.getCurrentBpmnSession().getScratchpad();
		if (StringUtils.isNotBlank(scratchPad)) {
			scratchPad = TaskScratchpadUtil.getScratchpad(WfmTransUtil.getUserNameByUserPk(task.getPk_creater()), task.getStartDate().toString(), scratchPad);
			task.setScratchpad(scratchPad);
		}
	}
	/**
	 * 任务代办处理
	 * 
	 * @param task
	 * @param humAct
	 * @param ownerPk
	 */
	public void handlerAgenter() {
		UserAgentHelper agentHelper = new UserAgentHelper();
		AgentContext agentCtx = new AgentContext();
		agentCtx.setFrmtype(humAct.getPk_formdefinition());
		agentCtx.setUser(ownerPk);
		agentHelper.doFind(agentCtx);
		String userAgentPk = agentCtx.getAgent();
		task.setPk_agenter(userAgentPk);
	}
	/**
	 * 处理消息发送
	 * 
	 * @param task
	 */
	public void handlerMsgSender() {
		Map<String, Object> messageMap = TaskMessageGatherUtil.getMessageMap(task, task.getPk_owner());
		messageMap.put(TaskMessageSenderMgr.FrontControlType, PwfmContext.getCurrentBpmnSession().getMsgType());
		TaskMessageSenderMgr.sendTaskCreatedMessage(messageMap);
	}
	/**
	 * 任务的属性迁移
	 * 
	 * @param task
	 */
	public void handlerExtAttr() {
		ProDefExt.handlerExtAttr(task, PwfmContext.getCurrentBpmnSession().getFormVo());
	}
	/**
	 * 任务创建后的扩展
	 * 
	 * @param task
	 * @param humAct
	 */
	public void handlerAfterTask() {
		IHumActHandler humActHandler = null;
		try {
			humActHandler = (IHumActHandler) Class.forName(humAct.getDelegatorClass()).newInstance();
			String taskExtClazz = humActHandler.getTaskExtClazz();
			ITaskExtHandler taskExthandler = (ITaskExtHandler) WfmClassUtil.loadClass(taskExtClazz);
			taskExthandler.afterCreateTask(task);
		} catch (Exception e) {
			throw new LfwRuntimeException(e);
		}
	}
	/**
	 * 传阅活动的状态设置
	 */
	public void handlerDeliver() {
		if (WfmConstants.ActionType_Cirulated.equalsIgnoreCase(humAct.getActionType())) {
			task.setState(Task.State_UnRead);
			humActIns.setIsNotExe(new UFBoolean(true));
			humActIns.setIsNotPas(new UFBoolean(true));
			humActIns.setState(HumActIns.End);
			humActIns.asyn();
		}
	}
	/**
	 * 任务优先级的处理
	 */
	public void handlerPriority() {
		if (ICompleteSgy.CompleteSgy_Countersign == humAct.getCompleteStrategy().getStrategyType() && Boolean.valueOf(humAct.getCompleteStrategy().getIsNotBunch())) {
			task.setPriority(String.valueOf(PwfmContext.getCurrentBpmnSession().getPriority()));
			PwfmContext.getCurrentBpmnSession().setPriority(PwfmContext.getCurrentBpmnSession().getPriority() + 1);
		} else {
			task.setPriority(String.valueOf(0));
		}
	}
}
