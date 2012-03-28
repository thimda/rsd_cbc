package nc.uap.wfm.cmd;
import java.util.Iterator;
import java.util.Set;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.wfm.context.PwfmContext;
import nc.uap.wfm.convert.ModelBuilder;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.model.Task;
import nc.uap.wfm.utils.BeforeAddSignUtil;
public class StopAddSignTaskCmd extends AbstractCommand implements ICommand<Void> {
	public StopAddSignTaskCmd() {
		super();
	}
	public Void execute() throws WfmServiceException {
		Task task = PwfmContext.getCurrentBpmnSession().getTask();
		Set<Task> children = task.getSubTasks();
		/**
		 * û�л�ǩ����,�������ǩֹͣ
		 */
		if (children == null || children.size() == 0) {
			throw new LfwRuntimeException("û�м�ǩ����,����Ҫ��ǩֹͣ");
		}
		/**
		 *������״̬Ϊ��ǩ��ɣ����ܻ�ǩֹͣ
		 */
		if (Task.State_BeforeAddSignCmplt.equalsIgnoreCase(task.getState())) {
			throw new LfwRuntimeException("������״̬Ϊ��ǩ��ɣ�����Ҫ��ǩֹͣ");
		}
		/**
		 * �����Լ���״̬
		 */
		task.setExt9(task.getState());
		task.setState(Task.State_BeforeAddSignCmplt);
		task.asyn();
		/**
		 * �����������״̬
		 */
		this.update(task, BeforeAddSignUtil.getMaxAddSignTimes(task));
		return null;
	}
	private void update(Task task, String maxAddStateTimes) {
		Set<Task> children = task.getSubTasks();
		if (children != null) {
			Iterator<Task> iter = children.iterator();
			Task tmpTask = null;
			Set<Task> tmpChildren = null;
			try {
				while (iter.hasNext()) {
					tmpTask = iter.next();
					tmpTask = ModelBuilder.builder(tmpTask);
					tmpChildren = tmpTask.getSubTasks();
					if (tmpChildren == null || tmpChildren.size() == 0) {
						/**
						 * û��ִ�еļ�ǩ����ֱ������Ϊ��ǩ��ֹ
						 */
						if (Task.CreateType_BeforeAddSign.equalsIgnoreCase(tmpTask.getCreateType())) {
							if (!tmpTask.getIsnotexe().booleanValue()) {
								if (maxAddStateTimes != null) {
									if (tmpTask.getAddSignTimes().equalsIgnoreCase(maxAddStateTimes)) {
										this.stopAddSignTask(tmpTask);
									}
								} else {
									this.stopAddSignTask(tmpTask);
								}
							}
						}
					} else {
						/**
						 * �����ǩ�ټ�ǩ����������û�м�ǩ��ɣ������Լ����������״̬Ϊ��ǩ��ֹ
						 */
						if (Task.CreateType_BeforeAddSign.equalsIgnoreCase(tmpTask.getCreateType())) {
							if (!tmpTask.getIsnotexe().booleanValue()) {
								this.stopAddSignTask(tmpTask);
								this.update(tmpTask, null);
							}
						}
					}
				}
			} catch (WfmServiceException e) {
				LfwLogger.error(e.getMessage(), e);
				throw new LfwRuntimeException(e.getMessage());
			}
		}
	}
	private void stopAddSignTask(Task task) {
		task.setExt9(task.getState());
		task.setState(Task.State_BeforeAddSignStop);
		task.asyn();
	}
}
