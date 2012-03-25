package nc.uap.wfm.message;
import java.util.Map;
/*
 * 催办接口
 */
public interface IUrgencyManage {
	/**
	 * 催办任务扩展点
	 */
	public static final String UrgencyManage = "urgencymanage";
	// 新建任务插件
	public static final String UrgencyManageEmail = "urgencymanageemail";
	public static final String UrgencyManageInnerMess = "urgencymanageinner";
	public static final String UrgencyManageOutLook = "urgencymanageoutlook";
	public static final String UrgencyManagePhone = "urgencymanagephone";
	public void sendUrgencyMessage(Map<String, Object> messageMap);
}
