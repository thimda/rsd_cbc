package nc.uap.wfm.rejectsgy;
import org.apache.commons.lang.StringUtils;
import nc.uap.dbl.constant.DblConstants;
import nc.uap.wfm.model.HumAct;
import nc.uap.wfm.model.RejectStrategy;
import nc.uap.wfm.model.Task;
public abstract class RejectSgyService {
	public boolean isNotPermit(HumAct humAct) {
		RejectStrategy sgy = humAct.getRejectStrategy();
		if(sgy==null){
			return false;
		}
		String str = sgy.getIsNotReject();
		if (StringUtils.isNotBlank(str)) {
			if (DblConstants.StrTrue.equalsIgnoreCase(str))
				return true;
			else
				return false;
		}
		return true;
	}
	abstract HumAct[] getRejectRange(Task task);
}
