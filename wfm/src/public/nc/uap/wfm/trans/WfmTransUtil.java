package nc.uap.wfm.trans;
import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.util.CpbServiceFacility;
import nc.uap.cpb.org.vos.CpUserVO;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
public class WfmTransUtil {
	public static String getUserNameByUserPk(String userPk) {
		try {
			if (userPk == null || userPk.length() == 0) {
				return "";
			}
			CpUserVO userVo = CpbServiceFacility.getCpUserQry().getUserByPk(userPk);
			if (userVo == null) {
				return userPk;
			}
			return userVo.getUser_name();
		} catch (CpbBusinessException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
	}
	public static String getDeptNameByDeptPk(String deptPk) {
		return "";
	}
}
