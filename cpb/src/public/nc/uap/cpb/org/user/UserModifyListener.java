package nc.uap.cpb.org.user;
import nc.bs.businessevent.IBusinessEvent;
import nc.bs.businessevent.IBusinessListener;
import nc.bs.businessevent.bd.BDCommonEvent;
import nc.bs.businessevent.bd.BDCommonEvent.BDCommonUserObj;
import nc.login.vo.INCUserTypeConstant;
import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.util.CpbServiceFacility;
import nc.uap.cpb.org.util.CpbUtil;
import nc.uap.cpb.org.vos.CpUserVO;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.vo.pub.BusinessException;
import nc.vo.sm.UserVO;
public class UserModifyListener implements IBusinessListener {
	@Override public void doAction(IBusinessEvent event) throws BusinessException {
		BDCommonEvent bevent = (BDCommonEvent) event;
		BDCommonUserObj ncUserVo = (BDCommonUserObj) bevent.getUserObject();
		ncUserVo.getNewObjects().getClass().getName();
		UserVO[] userVos = (UserVO[]) ncUserVo.getNewObjects();
		if (userVos == null || userVos.length == 0) {
			return;
		}
		for (int i = 0; i < userVos.length; i++) {
			CpUserVO userVo = CpbUtil.convert(userVos[i]);
			if (INCUserTypeConstant.USER_TYPE_USER== userVo.getUser_type()) {
				continue;
			}
			try {
				CpbServiceFacility.getCpUserBill().updateCpUserVO(userVo);
			} catch (CpbBusinessException e) {
				LfwLogger.error(e.getMessage(), e);
				throw new LfwRuntimeException(e.getMessage());
			}
		}
	}
}
