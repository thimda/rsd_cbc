package nc.uap.wfm.cmd;
import nc.bs.framework.common.NCLocator;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.wfm.context.AddSignTaskInfoCtx;
import nc.uap.wfm.context.AddSignUserInfoCtx;
import nc.uap.wfm.context.PwfmContext;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.itf.IWfmAddSignBill;
import nc.uap.wfm.model.Task;
import nc.uap.wfm.utils.BeforeAddSignUtil;
import nc.uap.wfm.vo.WfmAddSignUserVO;
import nc.uap.wfm.vo.WfmAddSignVO;
import nc.vo.pub.lang.UFDate;
public class CreateBeforeAddSignTaskCmd extends CreateAddSignTaskCmd {
	protected void createAddSignTasks() {
		Task task = PwfmContext.getCurrentBpmnSession().getTask();
		AddSignTaskInfoCtx flwInfoCtx = (AddSignTaskInfoCtx) PwfmContext.getCurrentBpmnSession().getFlwInfoCtx();
		AddSignUserInfoCtx[] addSignUserInfo = PwfmContext.getCurrentBpmnSession().getAddSignUserInfo();
		if (addSignUserInfo == null) {
			return;
		}
		int length = addSignUserInfo.length;
		WfmAddSignVO addSignVo = new WfmAddSignVO();
		addSignVo.setPk_task(task.getPk_task());
		addSignVo.setScratchpad(flwInfoCtx.getScratchpad());
		int maxTime = Integer.parseInt(BeforeAddSignUtil.getMaxAddSignTimes(task));
		addSignVo.setAddsigntime(String.valueOf(maxTime + 1));
		WfmAddSignUserVO[] addSignUserVos = new WfmAddSignUserVO[length];
		addSignVo.setAddSignUserVos(addSignUserVos);
		try {
			Task newTask = null;
			AddSignUserInfoCtx tmpUserInfo = null;
			for (int i = 0; i < length; i++) {
				tmpUserInfo = addSignUserInfo[i];
				newTask = task.clone();
				newTask = taskExe.initTask(newTask);
				newTask.setPk_creater(PwfmContext.getCurrentBpmnSession().getCurrentUserPk());
				newTask.setStartDate(new UFDate());
				newTask.setPk_owner(tmpUserInfo.getUserPk());
				newTask.setPk_ownerdept(tmpUserInfo.getDeptPk());
				newTask.setParent(task);
				newTask.setAddSignTimes(String.valueOf(maxTime + 1));
				newTask.setScratchpad(this.genScratchPad(newTask, flwInfoCtx));
				newTask.asyn();
				WfmAddSignUserVO userVo = new WfmAddSignUserVO();
				userVo.setPk_user(tmpUserInfo.getUserPk());
				userVo.setPk_dept(tmpUserInfo.getDeptPk());
				addSignUserVos[i] = userVo;
			}
			NCLocator.getInstance().lookup(IWfmAddSignBill.class).saveAddSignVo(addSignVo);
		} catch (WfmServiceException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
	}
	protected void updateTask() {
		Task task = PwfmContext.getCurrentBpmnSession().getTask();
		task.setState(Task.State_BeforeAddSignSend);
		task.asyn();
	}
}
