package nc.uap.wfm.message;
import java.util.Map;
/**
 * ���������Ϣ���ͽӿ�
 * @author zhangxya
 *
 */
public interface ITaskUregeMessageSender {
	//���������չ��
	public static final String TaskUrege = "taskurege";
	//���������
	public static final String TaskUregeEmail = "taskuregeemail";
	public static final String TaskUregeInnerMess = "taskuregeinner";
	public static final String TaskUregeOutLook = "taskuregeoutlook";
	public static final String MsgType = "msgtype";
	/**
	 * �������������Ϣ�ӿ�
	 * @param messageMap
	 */
	public void sendTaskUregeMessage(Map<String, Object> messageMap);
}
