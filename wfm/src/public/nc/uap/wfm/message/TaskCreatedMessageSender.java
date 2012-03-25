package nc.uap.wfm.message;

import java.util.Map;

/**
 * 任务完成消息发送接口
 * 
 * @author zhangxya
 * 
 */
public interface TaskCreatedMessageSender {

	// 完成任务扩展点
	public static final String TaskCreated = "tasknewcreated";
	// 完成任务插件
	public static final String TaskCreatedEmail = "taskcreatedemail";
	public static final String TaskCreatedPhone = "taskcreatedphone";
	public static final String TaskCreatedInnerMess = "taskcreatedinner";
	public static final String TaskCreatedOutLook = "taskcreatedoutlook";

	/**
	 * 发送任务完成信息接口
	 * 
	 * @param messageMap
	 */

	public void sendTaskCteatedMessage(Map<String, Object> messageMap);

}
