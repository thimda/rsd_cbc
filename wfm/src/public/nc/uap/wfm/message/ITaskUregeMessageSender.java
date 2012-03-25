package nc.uap.wfm.message;
import java.util.Map;
/**
 * 任务完成消息发送接口
 * @author zhangxya
 *
 */
public interface ITaskUregeMessageSender {
	//完成任务扩展点
	public static final String TaskUrege = "taskurege";
	//完成任务插件
	public static final String TaskUregeEmail = "taskuregeemail";
	public static final String TaskUregeInnerMess = "taskuregeinner";
	public static final String TaskUregeOutLook = "taskuregeoutlook";
	public static final String MsgType = "msgtype";
	/**
	 * 发送任务完成信息接口
	 * @param messageMap
	 */
	public void sendTaskUregeMessage(Map<String, Object> messageMap);
}
