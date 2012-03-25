package nc.uap.wfm.message;

import java.util.Map;

/**
 * ���������չ�㡣
 * @author ybo
 * @version 6.0 2011-5-17
 * @since 1.6
 */
public interface ProcCompletedMessageSender {
	// ���������չ��
	public static final String ProcCompleted = "proccompleted";
	// ���������
	public static final String ProcCompletedEmail = "proccompletedemail";
	public static final String ProcCompletedInnerMess = "proccompletedinner";
	public static final String ProcCompletedOutLook = "proccompletedoutlook";

	/**
	 * �������������Ϣ�ӿ�
	 * 
	 * @param messageMap
	 */

	public void sendProcCompletedMessage(Map<String, Object> messageMap);

}
