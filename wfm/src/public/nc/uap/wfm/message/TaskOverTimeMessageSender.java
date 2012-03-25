package nc.uap.wfm.message;

import java.util.Map;

public interface TaskOverTimeMessageSender {
	/**
	 * 新建任务扩展点
	 */
	public static final String TaskOvertime = "taskovertime";

	// 新建任务插件
	public static final String TaskOertimeEmail = "taskovertimeemail";
	public static final String TaskOvertimeInnerMess = "taskovertimeinner";
	public static final String TaskOvertimeOutLook = "taskovertimeoutlook";

	/**
	 * 发送任务超时消息
	 * 
	 * @param messageMap
	 */
	public void sendTaskOverTimeMessage(Map<String, Object> messageMap);

}
