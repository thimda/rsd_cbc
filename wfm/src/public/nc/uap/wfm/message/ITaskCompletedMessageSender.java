package nc.uap.wfm.message;

import java.util.Map;

/**
 * ���������Ϣ���ͽӿ�
 * @author zhangxya
 *
 */
public interface ITaskCompletedMessageSender {

	//���������չ��
	public static final String TaskCompleted = "taskcompleted";
	//���������
	public static final String TaskCompletedEmail = "taskcompletedemail";
	public static final String TaskCompletedInnerMess = "taskcompletedinner";
	public static final String TaskCompletedOutLook = "taskcompletedoutlook";
	/**
	 * �������������Ϣ�ӿ�
	 * @param messageMap
	 */
	
	public void sendTaskCompletedMessage(Map<String, Object> messageMap);

}
