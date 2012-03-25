package nc.uap.cpb.org.user;
import nc.bs.businessevent.BusinessEvent;
import nc.bs.businessevent.IBusinessEvent;
import nc.bs.businessevent.IBusinessListener;
import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.util.CpbServiceFacility;
import nc.uap.cpb.org.vos.CpUserVO;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.vo.pub.BusinessException;
public class UserUpdateListener implements IBusinessListener {
	@Override public void doAction(IBusinessEvent event) throws BusinessException {
		BusinessEvent bevent = (BusinessEvent) event;
		CpUserVO userVo = (CpUserVO) bevent.getUserObject();
		try {
			CpbServiceFacility.getCpUserBill().updateCpUserVO(userVo);
		} catch (CpbBusinessException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
	}
}
