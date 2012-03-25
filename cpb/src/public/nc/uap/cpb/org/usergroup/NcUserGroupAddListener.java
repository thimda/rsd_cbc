package nc.uap.cpb.org.usergroup;
import nc.bs.businessevent.IBusinessEvent;
import nc.bs.businessevent.IBusinessListener;
import nc.bs.businessevent.bd.BDCommonEvent;
import nc.bs.businessevent.bd.BDCommonEvent.BDCommonUserObj;
import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.user.ICpUserConst;
import nc.uap.cpb.org.util.CpbServiceFacility;
import nc.uap.cpb.org.vos.CpUserGroupVO;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.vo.pub.BusinessException;
import nc.vo.uap.rbac.UserGroupVO;
public class NcUserGroupAddListener implements IBusinessListener {
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
				//如果已经存在相同编码的数据，则保留原pk，其他数据覆盖
//				CpUserGroupVO usergroupvo = CpbServiceFacility.getCpUserGroupQry().getUserGroupByGroupCode(ncobjectvo.getGroupcode());
//				if(usergroupvo!=null){
//					cpobjectvo.setPk_usergroup(usergroupvo.getPk_usergroup());
//					CpbServiceFacility.getCpUserGroupBill().updatePtUserGroupVO(cpobjectvo);
//				}
//				else 
				CpbServiceFacility.getCpUserGroupBill().addPtUserGroupVOWithPk(new CpUserGroupVO[]{cpobjectvo});
			} catch (CpbBusinessException e) {
				LfwLogger.error(e.getMessage(), e);
				throw new LfwRuntimeException(e.getMessage());
			}
		}
	}
}
