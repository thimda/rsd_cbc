package nc.uap.wfm.message;
import java.util.Map;
/*
 * �߰�ӿ�
 */
public interface IUrgencyManage {
	/**
	 * �߰�������չ��
	 */
	public static final String UrgencyManage = "urgencymanage";
	// �½�������
	public static final String UrgencyManageEmail = "urgencymanageemail";
	public static final String UrgencyManageInnerMess = "urgencymanageinner";
	public static final String UrgencyManageOutLook = "urgencymanageoutlook";
	public static final String UrgencyManagePhone = "urgencymanagephone";
	public void sendUrgencyMessage(Map<String, Object> messageMap);
}
