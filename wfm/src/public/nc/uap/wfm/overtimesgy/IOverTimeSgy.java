package nc.uap.wfm.overtimesgy;
/**
 * 
 * @author tianchw
 *
 */
public interface IOverTimeSgy {
	public static final int OverTimeRemiand_Email = 1;//邮件提醒
	public static final int OverTimeRemiand_Message = 2;//消息提醒
	public static final int OverTimeSgy_Wait = 1;//继续等待
	public static final int OverTimeSgy_Rollback = 2;//活动后退
	public static final int OverTimeSgy_Suspend = 3;//流程挂起
	public static final int OverTimeSgy_Cancel = 4;//流程取消
	public static final int OverTimeSgy_Transrer = 5;//工作移交
}
