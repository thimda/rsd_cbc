package nc.uap.wfm.message;

import java.util.Map;

/**
 * ��������ӿ�
 * @author zhangxya
 *
 */
public interface ITaskCreatedMessageSender {
	/**
	 * �½�������չ��
	 */
	public static final String TaskNewCreated = "tasknewcreated";
	
	//�½�������
	public static final String TaskNewCreatedEmail = "taskcreatedemail";
	public static final String TaskNewCreatedInnerMess = "taskcreatedinner";
	public static final String TaskNewCreatedOutLook = "taskcreatedoutlook";
	
		
	public void sendTaskNewCreatedMessage(Map<String, Object> messageMap);

}
