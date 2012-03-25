package nc.uap.wfm.message;

import java.util.Map;

/**
 * 创建任务接口
 * @author zhangxya
 *
 */
public interface ITaskCreatedMessageSender {
	/**
	 * 新建任务扩展点
	 */
	public static final String TaskNewCreated = "tasknewcreated";
	
	//新建任务插件
	public static final String TaskNewCreatedEmail = "taskcreatedemail";
	public static final String TaskNewCreatedInnerMess = "taskcreatedinner";
	public static final String TaskNewCreatedOutLook = "taskcreatedoutlook";
	
		
	public void sendTaskNewCreatedMessage(Map<String, Object> messageMap);

}
