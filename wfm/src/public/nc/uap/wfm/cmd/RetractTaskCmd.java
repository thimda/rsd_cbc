package nc.uap.wfm.cmd;
import nc.bs.framework.common.NCLocator;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.wfm.context.PwfmContext;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.itf.IWfmAssignActorsBill;
import nc.uap.wfm.model.Task;
public class RetractTaskCmd extends AbstractCommand implements ICommand<Void> {
	protected Task task = null;
	public RetractTaskCmd() {
		super();
	}
	public Void execute() throws WfmServiceException {
		task = PwfmContext.getCurrentBpmnSession().getTask();
		deleteAssignInfo(task);
		if (task.getIsnotexe().booleanValue()) {
			throw new LfwRuntimeException("�������Ѿ���ִ�У��������ջ�");
		}
		taskExe.realDeleteTask(task);
		return null;
	}
	/**
	 * ɾ��ָ����Ϣ
	 * @param task
	 * @throws WfmServiceException
	 */
	public void deleteAssignInfo(Task task) throws WfmServiceException {
		NCLocator.getInstance().lookup(IWfmAssignActorsBill.class).deleteAssignActors(task.getRootProIns().getPk_proins(), task.getProDef().getId(), task.getHumActIns().getHumAct().getId());
	}
}
