package nc.uap.cpb.org.extention;

import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.util.CpbServiceFacility;
import nc.uap.lfw.core.log.LfwLogger;


/**
 * 职责管理扩展实现
 * 2012-3-26 下午02:40:10
 * @author limingf
 *
 */
public class RespExtentionServiceImpl implements ICpbExtentionService {

	public String acceptFunType() {
		return ICpbExtentionService.RESPMANAGE;
	}

	@Override
	public void afterAction(String functionType, String actionType, Object obj) {		
		if (actionType.equals(ICpbExtentionService.DELETE)) {
			if (obj instanceof String) {
				String pk_responsibility = (String) obj;
				try {
					CpbServiceFacility.getCpRoleRespBill().deleteCpRoleRespByResppks(new String[]{pk_responsibility});
				} catch (CpbBusinessException e) {
					LfwLogger.error(e.getMessage(),e);
				}
			}
		}
		
	}

	@Override
	public void beforeAction(String functionType, String actionType,Object object) {
		
	}

}
