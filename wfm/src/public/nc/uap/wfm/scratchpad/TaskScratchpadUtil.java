package nc.uap.wfm.scratchpad;
public class TaskScratchpadUtil {
	public static String getScratchpad(String userName, String startDate, String scratchpad) {
		StringBuffer sb = new StringBuffer();
		sb.append("�����ˣ�" + userName + "\r\n");
		sb.append("����ʱ�䣺" + startDate + "\r\n");
		sb.append("���ݣ�" + scratchpad + "\r\n\n");
		return sb.toString();
	}
}
