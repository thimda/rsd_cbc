package nc.uap.wfm.model;
import nc.uap.wfm.constant.WfmConstants;
import nc.uap.wfm.rejectsgy.IRejectSgy;
public class RejectStrategy extends AbstractStrategy implements IRejectSgy {
	private String isNotReject;
	private String[] humActs;
	public String[] getHumActs() {
		return humActs;
	}
	public void setHumActs(String[] humActs) {
		this.humActs = humActs;
	}
	public String getIsNotReject() {
		return isNotReject;
	}
	public void setIsNotReject(String isNotReject) {
		this.isNotReject = isNotReject;
	}
	@Override
	public int getStrategyType() {
		String value = this.getValue();
		if (value == null || value.length() == 0) {
			return 0;
		}
		String str[] = value.split(":");
		if (str.length == 0) {
			return 0;
		}
		if (WfmConstants.RejectSgy_PreviousHumAct.equalsIgnoreCase(str[0])) {
			return RejectSgy_PreviousHumAct;
		} else if (WfmConstants.RejectSgy_AllHumAct.equalsIgnoreCase(str[0])) {
			return RejectSgy_AllHumAct;
		} else if (WfmConstants.RejectSgy_StartHumAct.equalsIgnoreCase(str[0])) {
			return RejectSgy_StartHumAct;
		} else if (WfmConstants.RejectSgy_AppointHumAct.equalsIgnoreCase(str[0])) {
			if (str.length == 2) {
				this.setHumActs(str[1].split(","));
			}
			return RejectSgy_AppointHumAct;
		} else {
			return 0;
		}
	}
}
