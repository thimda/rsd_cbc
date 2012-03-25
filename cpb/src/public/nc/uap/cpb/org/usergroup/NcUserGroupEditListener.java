package nc.uap.cpb.org.usergroup;
import nc.bs.businessevent.IBusinessEvent;
import nc.bs.businessevent.IBusinessListener;
import nc.bs.businessevent.bd.BDCommonEvent;
import nc.bs.businessevent.bd.BDCommonEvent.BDCommonUserObj;
import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.util.CpbServiceFacility;
import nc.uap.cpb.org.vos.CpUserGroupVO;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.vo.pub.BusinessException;
import nc.vo.uap.rbac.UserGroupVO;
public class NcUserGroupEditListener implements IBusinessListener {
	@Override public void doAction(IBusinessEvent event) throws BusinessException {
		BDCommonEvent bevent = (BDCommonEvent) event;
		BDCommonUserObj ncUserVo = (BDCommonUserObj) bevent.getUserObject();
		ncUserVo.getNewObjects().getClass().getName();
		Object[] ncobjectvos = (Object[]) ncUserVo.getNewObjects();
		if (ncobjectvos == null || ncobjectvos.length == 0) {
			return;
		}
		for (int i = 0; i < ncobjectvos.length; i++) {
			UserGroupVO ncobjectvo = (UserGroupVO)ncobjectvos[i];
					
			CpUserGroupVO cpobjectvo = new CpUserGroupVO();
			try {
				UsergroupUtils.convert(cpobjectvo, ncobjectvo);
			} catch (Exception e) {
				LfwLogger.error(e.getMessage(), e);
				throw new LfwRuntimeException(e.getMessage());
			}
			try { 
				CpbServiceFacility.getCpUserGroupBill().updatePtUserGroupVO(cpobjectvo);
			} catch (CpbBusinessException e) {
				LfwLogger.error(e.getMessage(), e);
				throw new LfwRuntimeException(e.getMessage());
			}
		}
	}
}
