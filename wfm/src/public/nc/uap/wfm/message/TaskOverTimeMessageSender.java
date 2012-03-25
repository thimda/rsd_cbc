package nc.uap.wfm.message;

import java.util.Map;

public interface TaskOverTimeMessageSender {
	/**
	 * �½�������չ��
	 */
	public static final String TaskOvertime = "taskovertime";

	// �½�������
	public static final String TaskOertimeEmail = "taskovertimeemail";
	public static final String TaskOvertimeInnerMess = "taskovertimeinner";
	public static final String TaskOvertimeOutLook = "taskovertimeoutlook";

	/**
	 * ��������ʱ��Ϣ
	 * 
	 * @param messageMap
	 */
	public void sendTaskOverTimeMessage(Map<String, Object> messageMap);

}
