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
public class RestartAddSignTaskCmd extends AbstractCommand implements ICommand<Void> {
	public RestartAddSignTaskCmd() {
		super();
	}
	public Void execute() throws WfmServiceException {
		Task task = PwfmContext.getCurrentBpmnSession().getTask();
		Set<Task> children = task.getSubTasks();
		if (children == null || children.size() == 0) {
			throw new LfwRuntimeException("没有加签任务,不需要加签重启");
		}
		if (Task.State_BeforeAddSignCmplt.equalsIgnoreCase(task.getState())) {
			Iterator<Task> iter = children.iterator();
			int count = 0;
			Task tmpTask = null;
			while (iter.hasNext()) {
				tmpTask = iter.next();
				if (Task.State_End.equalsIgnoreCase(tmpTask.getState())) {
					count++;
				}
			}
			if (count == children.size()) {
				throw new LfwRuntimeException("加签已经完成，不需要加签重起");
			}
		} else {
			throw new LfwRuntimeException("已经处于加签状态，不需加签要重起");
		}
		this.restartAddSignTask(task);
		this.update(task, BeforeAddSignUtil.getMaxAddSignTimes(task));
		return null;
	}
	private void restartAddSignTask(Task task) {
		task.setState(task.getExt9());
		task.setExt9(null);
		task.asyn();
	}
	private void update(Task task, String maxAddSignTimes) {
		Set<Task> children = task.getSubTasks();
		if (children == null || children.size() == 0) {
			return;
		}
		Task tmpTask = null;
		try {
			Iterator<Task> iter = children.iterator();
			while (iter.hasNext()) {
				tmpTask = iter.next();
				tmpTask = ModelBuilder.builder(tmpTask);
				if (!tmpTask.getIsnotexe().booleanValue()) {
					if (maxAddSignTimes != null) {
						if (tmpTask.getAddSignTimes().equalsIgnoreCase(maxAddSignTimes)) {
							this.restartAddSignTask(tmpTask);
						}
					} else {
						this.restartAddSignTask(tmpTask);
					}
					if (tmpTask.getSubTasks() != null && tmpTask.getSubTasks().size() != 0) {
						this.update(tmpTask, null);
					}
				}
			}
		} catch (WfmServiceException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
	}
}
