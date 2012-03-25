package nc.uap.wfm.execution;
import nc.bs.framework.common.NCLocator;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.wfm.context.PwfmContext;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.itf.IWfmTaskBill;
import nc.uap.wfm.model.HumActIns;
import nc.uap.wfm.model.Task;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
public class TaskExecution {
	private static TaskExecution instance = null;
	private TaskExecution() {}
	synchronized public static TaskExecution getInstance() {
		if (instance != null)
			return instance;
		else {
			instance = new TaskExecution();
			return instance;
		}
	}
	public Task initTask(Task task) {
		task.setPk_task(null);
		task.setFinishType(null);
		task.setPk_myvisa(null);
		task.setPk_creater(PwfmContext.getCurrentBpmnSession().getCurrentUserPk());
		task.setPk_executer(null);
		task.setPk_agenter(null);
		task.setPk_assigner(null);
		task.setStartDate(new UFDate());
		task.setSignDate(null);
		task.setEndDate(null);
		task.setIsnotexe(new UFBoolean(false));
		task.setIsnotpas(new UFBoolean(false));
		task.setCreateType(Task.CreateType_Normal);
		task.setHandlepiece(Task.HandlerPiece_UnRead);
		task.setState(Task.State_Run);
		task.setExt8(null);
		task.setExt9(null);
		return task;
	}
	/**
	 * 创建任务
	 * 
	 * @param humActIns
	 * @return
	 * @throws WfmServiceException
	 */
	public Task createTask(HumActIns humActIns) throws WfmServiceException {
		Task task = new Task();
		task.setHumActIns(humActIns);
		task.setStartDate(new UFDate());
		task.setIsnotexe(UFBoolean.FALSE);
		task.setIsnotpas(UFBoolean.FALSE);
		task.setPk_creater(PwfmContext.getCurrentBpmnSession().getCurrentUserPk());
		task.setState(Task.State_Run);
		task.setProIns(humActIns.getProIns());
		task.setRootProIns(humActIns.getRootProIns());
		task.setProDef(humActIns.getProIns().getProDef());
		task.setFlowType(humActIns.getProIns().getProDef().getFlwtype());
		task.setCreateType(Task.CreateType_Normal);
		return task;
	}
	/**
	 * 完成任务
	 * 
	 * @param task
	 * @return
	 * @throws WfmServiceException
	 */
	public Task completeTask(Task task) {
		if (Task.State_End.equalsIgnoreCase(task.getState())) {
			throw new LfwRuntimeException("任务已经被执行");
		}
		task.setIsnotexe(UFBoolean.TRUE);
		task.setEndDate(new UFDate());
		task.setPk_executer(PwfmContext.getCurrentBpmnSession().getCurrentUserPk());
		task.setState(Task.State_End);
		task.setOpinion(PwfmContext.getCurrentBpmnSession().getOpinion());
		if (Task.CreateType_Deliver.equalsIgnoreCase(task.getCreateType())) {
			task.setFinishType(Task.FinishType_Deliver);
		} else {
			task.setFinishType(Task.FinishType_Normal);
		}
		return task;
	}
	/**
	 * 驳回任务
	 * 
	 * @param task
	 * @throws WfmServiceException
	 */
	public void rejectTask(Task task) {
		if (task.getState().equalsIgnoreCase(Task.State_End)) {
			throw new LfwRuntimeException("任务已经被执行");
		}
		/**
		 * 设置本步任务的状态
		 */
		task.setIsnotexe(UFBoolean.TRUE);
		task.setIsnotpas(UFBoolean.FALSE);
		task.setEndDate(new UFDate());
		task.setState(Task.State_End);
		task.setFinishType(Task.FinishType_Reject);
		task.setPk_executer(PwfmContext.getCurrentBpmnSession().getCurrentUserPk());
	}
	/**
	 * 取回任务
	 * 
	 * @param takk
	 * @throws WfmServiceException
	 */
	public void backTask(Task task) throws WfmServiceException {
		task.setEndDate(null);
		task.setOpinion(null);
		task.setPk_executer(null);
		task.setIsnotexe(UFBoolean.FALSE);
		task.setIsnotpas(UFBoolean.FALSE);
		task.setState(Task.State_Run);
		task.setSignDate(null);
		task.setHandlepiece(Task.HandlerPiece_UnRead);
	}
	/**
	 * 假删除任务
	 */
	public boolean bogusDeleteTask(Task task) throws WfmServiceException {
		task.setEndDate(new UFDate());
		task.asyn();
		IWfmTaskBill taskBill = NCLocator.getInstance().lookup(IWfmTaskBill.class);
		taskBill.bogusDeleteTask(task.getPk_task());
		return true;
	}
	/**
	 * 真删除任务
	 * 
	 * @param task
	 * @return
	 * @throws WfmServiceException
	 */
	public boolean realDeleteTask(Task task) throws WfmServiceException {
		IWfmTaskBill taskBill = NCLocator.getInstance().lookup(IWfmTaskBill.class);
		taskBill.realDeleteTask(task.getPk_task());
		return true;
	}
}
