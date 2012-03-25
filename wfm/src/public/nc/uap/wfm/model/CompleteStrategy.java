package nc.uap.wfm.model;
import nc.uap.wfm.completesgy.ICompleteSgy;
import nc.uap.wfm.constant.WfmConstants;
public class CompleteStrategy extends AbstractStrategy implements ICompleteSgy {
	private String count;
	private String percent;
	private String isNotBunch;//ÊÇ·ñ´®ÐÐ
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getPercent() {
		return percent;
	}
	public void setPercent(String percent) {
		this.percent = percent;
	}
	public String getIsNotBunch() {
		return isNotBunch;
	}
	public void setIsNotBunch(String isNotBunch) {
		this.isNotBunch = isNotBunch;
	}
	public int getStrategyType() {
		String value = this.getValue();
		String[] str = value.split(":");
		if (WfmConstants.CompleteSgy_Occupy.equalsIgnoreCase(str[0])) {
			return CompleteSgy_Occupy;
		}
		if (WfmConstants.CompleteSgy_Countersign.equalsIgnoreCase(str[0])) {
			return CompleteSgy_Countersign;
		}
		if (WfmConstants.CompleteSgy_ByCount.equalsIgnoreCase(str[0])) {
			this.setCount(str[1]);
			return CompleteSgy_ByCount;
		}
		if (WfmConstants.CompleteSgy_ByPercent.equalsIgnoreCase(str[0])) {
			this.setPercent(str[1]);
			return CompleteSgy_ByPercent;
		}
		return 0;
	}
}
