package nc.uap.wfm.overtimesgy;
import nc.uap.wfm.constant.WfmConstants;
import nc.uap.wfm.model.HumAct;
import nc.uap.wfm.model.HumActIns;
import nc.uap.wfm.model.MessageStrategy;
import nc.uap.wfm.model.Task;
import org.apache.commons.lang.StringUtils;
public class OverTimeCalculator {
	public static boolean isOverTime(Task task) {
		HumActIns humActIns = task.getHumActIns();
		HumAct humAct = humActIns.getHumAct();
		if (humAct == null) {
			return false;
		}
		MessageStrategy sgy = humAct.getMessageStrategy();
		if (sgy == null) {
			return false;
		}
		String unit = sgy.getUnit();
		long sub = System.currentTimeMillis() - task.getStartDate().getMillis();
		long total = 0;
		if (WfmConstants.Unit_Day.equalsIgnoreCase(unit)) {
			if (StringUtils.isNotBlank(sgy.getWorkTime())) {
				total = total + Integer.parseInt(sgy.getWorkTime()) * 24 * 60 * 60 * 1000;
			}
			if (StringUtils.isNotBlank(sgy.getAheadTime())) {
				total = total - Integer.parseInt(sgy.getAheadTime()) * 24 * 60 * 60 * 1000;
			}
		} else if (WfmConstants.Unit_Hour.equalsIgnoreCase(unit)) {
			if (StringUtils.isNotBlank(sgy.getWorkTime())) {
				total = total + Integer.parseInt(sgy.getWorkTime()) * 60 * 60 * 1000;
			}
			if (StringUtils.isNotBlank(sgy.getAheadTime())) {
				total = total - Integer.parseInt(sgy.getAheadTime()) * 60 * 60 * 1000;
			}
		} else if (WfmConstants.Unit_Min.equalsIgnoreCase(unit)) {
			if (StringUtils.isNotBlank(sgy.getWorkTime())) {
				total = total + Integer.parseInt(sgy.getWorkTime()) * 60 * 1000;
			}
			if (StringUtils.isNotBlank(sgy.getAheadTime())) {
				total = total - Integer.parseInt(sgy.getAheadTime()) * 60 * 1000;
			}
		} else if (WfmConstants.Unit_Sec.equalsIgnoreCase(unit)) {
			if (StringUtils.isNotBlank(sgy.getWorkTime())) {
				total = total + Integer.parseInt(sgy.getWorkTime()) * 1000;
			}
			if (StringUtils.isNotBlank(sgy.getAheadTime())) {
				total = total - Integer.parseInt(sgy.getAheadTime()) * 1000;
			}
		}
		if (sub <= total) {
			return true;
		} else {
			return false;
		}
	}
}
