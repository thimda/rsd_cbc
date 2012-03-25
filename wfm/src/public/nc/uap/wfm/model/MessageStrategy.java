package nc.uap.wfm.model;
import nc.uap.dbl.constant.DblConstants;
import nc.uap.wfm.overtimesgy.IOverTimeSgy;
public class MessageStrategy extends AbstractStrategy implements IOverTimeSgy {
	/**
	 * 任务创建发出的消息
	 */
	private String createdMsg;
	/**
	 * 任务完成的消息提醒
	 */
	private String completedMsg;
	/**
	 * 任务超时的时间提醒
	 */
	private String overtimeMsg;
	/**
	 * 超时的动作
	 */
	private String overtimeAct;
	/**
	 * 提前时间
	 */
	private String aheadTime;
	/**
	 * 工作时间
	 */
	private String workTime;
	/**
	 * 时间单位
	 */
	private String unit;
	/**
	 * 
	 *是否控制
	 */
	private String allowControl;
	public String getCreatedMsg() {
		return createdMsg;
	}
	public void setCreatedMsg(String createdMsg) {
		this.createdMsg = createdMsg;
	}
	public String getCompletedMsg() {
		return completedMsg;
	}
	public void setCompletedMsg(String completedMsg) {
		this.completedMsg = completedMsg;
	}
	public String getOvertimeMsg() {
		return overtimeMsg;
	}
	public void setOvertimeMsg(String overtimeMsg) {
		this.overtimeMsg = overtimeMsg;
	}
	@Override
	public int getStrategyType() {
		return 0;
	}
	public String getOvertimeAct() {
		return overtimeAct;
	}
	public void setOvertimeAct(String overtimeAct) {
		this.overtimeAct = overtimeAct;
	}
	public String getAheadTime() {
		return aheadTime;
	}
	public void setAheadTime(String aheadTime) {
		this.aheadTime = aheadTime;
	}
	public String getWorkTime() {
		return workTime;
	}
	public void setWorkTime(String workTime) {
		this.workTime = workTime;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getAllowControl() {
		return allowControl;
	}
	public void setAllowControl(String allowControl) {
		this.allowControl = allowControl;
	}
	public boolean isNotControlTime() {
		String flag = this.getAllowControl();
		if (DblConstants.StrTrue.equalsIgnoreCase(flag)) {
			return true;
		} else {
			return false;
		}
	}
}
