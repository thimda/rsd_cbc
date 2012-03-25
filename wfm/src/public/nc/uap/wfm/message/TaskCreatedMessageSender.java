package nc.uap.wfm.message;

import java.util.Map;

/**
 * ���������Ϣ���ͽӿ�
 * 
 * @author zhangxya
 * 
 */
public interface TaskCreatedMessageSender {

	// ���������չ��
	public static final String TaskCreated = "tasknewcreated";
	// ���������
	public static final String TaskCreatedEmail = "taskcreatedemail";
	public static final String TaskCreatedPhone = "taskcreatedphone";
	public static final String TaskCreatedInnerMess = "taskcreatedinner";
	public static final String TaskCreatedOutLook = "taskcreatedoutlook";

	/**
	 * �������������Ϣ�ӿ�
	 * 
	 * @param messageMap
	 */

	public void sendTaskCteatedMessage(Map<String, Object> messageMap);

}
