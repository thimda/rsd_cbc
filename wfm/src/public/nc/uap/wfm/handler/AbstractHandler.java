package nc.uap.wfm.handler;
import nc.uap.wfm.execution.HumActInsExecution;
import nc.uap.wfm.execution.ProInsExecution;
import nc.uap.wfm.execution.TaskExecution;
public abstract class AbstractHandler {
	protected ProInsExecution proInsExe = ProInsExecution.getInstance();
	protected HumActInsExecution humActInsExe = HumActInsExecution.getInstance();
	protected TaskExecution taskExe = TaskExecution.getInstance();
}
