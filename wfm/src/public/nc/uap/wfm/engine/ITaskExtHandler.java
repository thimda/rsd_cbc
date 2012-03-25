package nc.uap.wfm.engine;
import nc.uap.wfm.model.Task;
/**
 * 任务扩展接口
 * 
 * @author tianchw
 * 
 */
public interface ITaskExtHandler {
	/**
	 * 任务创建后扩展
	 * 
	 * @param task
	 */
	void afterCreateTask(Task task);
	/**
	 * 任务完成前扩展操作
	 */
	void beforeCmpltTask(Task task);
	/**
	 * 任务完成后的扩展操作
	 */
	void afterCmpltTask(Task task);
	/**
	 * 是否超时扩展操作
	 * 
	 * @param task
	 * @return
	 */
	boolean isOverTime(Task task);
	/**
	 * 任务回收后的扩展操作
	 * 
	 * @param task
	 */
	void afterBack(Task task);
	/**
	 * 流程台帐模型渲染类
	 * 
	 * @param task
	 * @return
	 */
	String getFlowDataDispModel(Task task);
	/**
	 * 流程明细模型渲染类
	 * 
	 * @param task
	 * @return
	 */
	String getFlwDataDetailModel(Task task);
	/**
	 * 任务消息提醒扩张类
	 * 
	 * @param task
	 * @return
	 */
	String getPhoneMsg(Task task);
}
