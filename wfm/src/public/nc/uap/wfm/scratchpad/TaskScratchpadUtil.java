package nc.uap.wfm.scratchpad;
public class TaskScratchpadUtil {
	public static String getScratchpad(String userName, String startDate, String scratchpad) {
		StringBuffer sb = new StringBuffer();
		sb.append("发送人：" + userName + "\r\n");
		sb.append("发送时间：" + startDate + "\r\n");
		sb.append("内容：" + scratchpad + "\r\n\n");
		return sb.toString();
	}
}
