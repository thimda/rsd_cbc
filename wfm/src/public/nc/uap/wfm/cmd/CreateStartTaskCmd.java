package nc.uap.wfm.cmd;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.wfm.context.PwfmContext;
import nc.uap.wfm.engine.IHumActHandler;
import nc.uap.wfm.engine.ITaskExtHandler;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.ext.ProDefExt;
import nc.uap.wfm.handler.PortAndEdgeHandler;
import nc.uap.wfm.model.HumAct;
import nc.uap.wfm.model.HumActIns;
import nc.uap.wfm.model.ProDef;
import nc.uap.wfm.model.ProIns;
import nc.uap.wfm.model.StartEvent;
import nc.uap.wfm.model.Task;
import nc.uap.wfm.utils.WfmClassUtil;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
/**
 * 完成任务命令
 * 
 * @author tianchw
 * 
 */
public class CreateStartTaskCmd extends AbstractCommand implements ICommand<Task> {
	public CreateStartTaskCmd() {
		super();
	}
	public Task execute() throws WfmServiceException {
		ProIns proIns = PwfmContext.getCurrentBpmnSession().getProIns();
		ProDef proDef = proIns.getProDef();
		StartEvent startEvent = proDef.getStart();
		HumAct cntHumAct = null;
		HumActIns startIns = null;
		if (startEvent.isNotCoverMakePort()) {
			cntHumAct = startEvent;
		} else {
			startIns = this.createStartHumActIns(startEvent, proIns);
			cntHumAct = (HumAct) PortAndEdgeHandler.getNextHumActs(startEvent)[0];
		}
		HumActIns humActIns = this.createBillFrmHumActIns(startEvent, cntHumAct, proIns, startIns);
		Task task = this.createStartTask(proIns, humActIns);
		this.afterHandler(cntHumAct);
		return task;
	}
	private HumActIns createStartHumActIns(StartEvent startEvent, ProIns proIns) {
		HumActIns startIns = null;
		try {
			startIns = humActInsExe.createHumActIns(startEvent);
		} catch (WfmServiceException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
		startIns.setProIns(proIns);
		startIns.setRootProIns(proIns);
		startIns.setParent(null);
		startIns.setIsNotExe(new UFBoolean(true));
		startIns.setIsNotPas(new UFBoolean(true));
		startIns.setState(HumActIns.End);
		startIns.asyn();
		return startIns;
	}
	private HumActIns createBillFrmHumActIns(StartEvent startEvent, HumAct currentHumAct, ProIns proIns, HumActIns startHumActIns) {
		HumActIns humActIns = null;
		try {
			humActIns = humActInsExe.createHumActIns(currentHumAct);
		} catch (WfmServiceException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
		humActIns.setProIns(proIns);
		humActIns.setRootProIns(proIns);
		if (startEvent.isNotCoverMakePort()) {
			humActIns.setParent(null);
		} else {
			humActIns.setParent(startHumActIns);
		}
		humActIns.setIsNotExe(new UFBoolean(true));
		humActIns.setIsNotPas(new UFBoolean(true));
		humActIns.setState(HumActIns.End);
		humActIns.asyn();
		return humActIns;
	}
	private Task createStartTask(ProIns proIns, HumActIns humActIns) {
		String userPk = proIns.getPk_starter();
		ProDef proDef = proIns.getProDef();
		Task newTask = null;
		try {
			newTask = taskExe.createTask(humActIns);
		} catch (WfmServiceException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
		newTask.setPk_owner(userPk);
		newTask.setPk_executer(userPk);
		newTask.setHumActIns(humActIns);
		newTask.setParent(null);
		newTask.setCreateType(Task.CreateType_Normal);
		newTask.setPriority("0");
		newTask.setPk_frmins(proIns.getPk_startfrmins());
		newTask.setPk_frmdef(proDef.getPk_startfrm());
		UFDate startDate = new UFDate(System.currentTimeMillis() - 1000);
		newTask.setStartDate(startDate);
		newTask.setEndDate(startDate);
		newTask.setProInsStartDate(proIns.getStartDate());
		newTask.setState(Task.State_End);
		newTask.setIsnotexe(UFBoolean.TRUE);
		newTask.setIsnotpas(UFBoolean.TRUE);
		newTask.setFlowType(proDef.getFlwtype());
		newTask.setActionType(Task.ActionType_Normal);
		newTask.setFinishType(Task.FinishType_Normal);
		newTask.setHandlepiece(Task.HandlerPiece_Readed);
		newTask.setOpinion(PwfmContext.getCurrentBpmnSession().getOpinion());
		ProDefExt.handlerExtAttr(newTask, PwfmContext.getCurrentBpmnSession().getFormVo());
		newTask.asyn();
		PwfmContext.getCurrentBpmnSession().setTask(newTask);
		return newTask;
	}
	private void afterHandler(HumAct cntHumAct) {
		String serviceClass = cntHumAct.getDelegatorClass();
		IHumActHandler handler = (IHumActHandler) WfmClassUtil.loadClass(serviceClass);
		ITaskExtHandler taskExthandler = (ITaskExtHandler) WfmClassUtil.loadClass(handler.getTaskExtClazz());
		taskExthandler.afterCmpltTask(PwfmContext.getCurrentBpmnSession().getTask());
		handler.afterHumAct();
	}
}
