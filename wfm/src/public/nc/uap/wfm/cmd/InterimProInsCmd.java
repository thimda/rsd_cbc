package nc.uap.wfm.cmd;
import nc.uap.wfm.context.PwfmContext;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.ext.ProDefExt;
import nc.uap.wfm.model.HumActIns;
import nc.uap.wfm.model.IPort;
import nc.uap.wfm.model.ProDef;
import nc.uap.wfm.model.ProIns;
import nc.uap.wfm.model.StartEvent;
import nc.uap.wfm.model.Task;
import nc.uap.wfm.utils.WfmTaskUtil;
import nc.uap.wfm.vo.WfmFormInfoCtx;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
/**
 *ÔÝ´æÁ÷³Ì
 * 
 * @author tianchw
 * 
 */
public class InterimProInsCmd extends AbstractCommand implements ICommand<Task> {
	public InterimProInsCmd() {}
	public Task execute() throws WfmServiceException {
		ProDef proDef = PwfmContext.getCurrentBpmnSession().getProDef();
		ProIns pproIns = PwfmContext.getCurrentBpmnSession().getPProIns();
		ProIns proIns = proInsExe.createProIns(proDef);
		proIns.setPk_starttask(PwfmContext.getCurrentBpmnSession().getStartTaskPk());
		proInsExe.startProIns(proIns);
		WfmFormInfoCtx formVo = PwfmContext.getCurrentBpmnSession().getFormVo();
		proIns.setPk_startfrmins(formVo.getFrmInsPk());
		proIns.setPproIns(pproIns);
		proIns.setState(ProIns.NottStart);
		proIns.asyn();
		HumActIns humActIns = null;
		StartEvent start = proDef.getStart();
		HumActIns startHumActIns = humActInsExe.createHumActIns(start);
		startHumActIns.setProIns(proIns);
		startHumActIns.setRootProIns(proIns);
		startHumActIns.setParent(null);
		startHumActIns.setIsNotExe(new UFBoolean(true));
		startHumActIns.setState(HumActIns.End);
		startHumActIns.asyn();
		humActIns = startHumActIns;
		IPort nextPort = WfmTaskUtil.getStratPort(proDef);
		if (nextPort != start) {
			HumActIns nextHumActIns = humActInsExe.createHumActIns(nextPort);
			nextHumActIns.setProIns(proIns);
			nextHumActIns.setRootProIns(proIns);
			nextHumActIns.setParent(startHumActIns);
			nextHumActIns.setIsNotExe(new UFBoolean(true));
			nextHumActIns.setState(HumActIns.End);
			nextHumActIns.asyn();
			humActIns = nextHumActIns;
		}
		String userPk = proIns.getPk_starter();
		Task task = taskExe.createTask(humActIns);
		task.setPk_owner(userPk);
		task.setPk_executer(userPk);
		task.setHumActIns(humActIns);
		task.setParent(null);
		task.setCreateType(Task.CreateType_Normal);
		task.setPriority("0");
		task.setPk_frmins(proIns.getPk_startfrmins());
		task.setStartDate(new UFDate());
		task.setFlowType(proDef.getFlwtype());
		task.setActionType(Task.ActionType_Normal);
		ProDefExt.handlerExtAttr(task, formVo);
		task.asyn();
		return task;
	}
}
