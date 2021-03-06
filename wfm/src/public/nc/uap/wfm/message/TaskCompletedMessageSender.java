package nc.uap.wfm.message;

import java.util.Map;

public interface TaskCompletedMessageSender {
	// 完成任务扩展点
	public static final String TaskCompleted = "taskcompleted";
	// 完成任务插件
	public static final String TaskCompletedEmail = "taskcompletedemail";
	public static final String TaskCompletedInnerMess = "taskcompletedinner";
	public static final String TaskCompletedOutLook = "taskcompletedoutlook";

	/**
	 * 发送任务完成信息接口
	 * 
	 * @param messageMap
	 */

	public void sendTaskCompletedMessage(Map<String, Object> messageMap);

}
