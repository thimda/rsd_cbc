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
		 * 没有会签任务,不允许会签停止
		 */
		if (children == null || children.size() == 0) {
			throw new LfwRuntimeException("没有加签任务,不需要加签停止");
		}
		/**
		 *该任务状态为会签完成，不能会签停止
		 */
		if (Task.State_BeforeAddSignCmplt.equalsIgnoreCase(task.getState())) {
			throw new LfwRuntimeException("该任务状态为加签完成，不需要加签停止");
		}
		/**
		 * 更新自己的状态
		 */
		task.setExt9(task.getState());
		task.setState(Task.State_BeforeAddSignCmplt);
		task.asyn();
		/**
		 * 更新子任务的状态
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
						 * 没有执行的加签任务直接设置为加签终止
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
						 * 如果加签再加签出来的任务没有加签完成，更新自己和子任务的状态为加签终止
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
