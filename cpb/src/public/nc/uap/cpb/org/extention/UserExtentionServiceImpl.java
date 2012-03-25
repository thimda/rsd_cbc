package nc.uap.cpb.org.extention;

import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.user.UserCacheUtil;
import nc.uap.cpb.org.util.CpbServiceFacility;
import nc.uap.cpb.org.vos.CpRoleVO;
import nc.uap.cpb.org.vos.CpUserRoleVO;
import nc.uap.lfw.core.log.LfwLogger;
import nc.vo.pub.lang.UFDate;


/**
 * 用户管理扩展实现
 * 2011-6-8 上午11:11:09 
 * @author limingf
 */
public class UserExtentionServiceImpl implements ICpbExtentionService {

	public String acceptFunType() {
		return ICpbExtentionService.USERMANAGE;
	}

	@Override
	public void afterAction(String functionType, String actionType, Object obj) {		
		if(actionType.equals(ICpbExtentionService.ADD)){
			String pk_user = (String) obj;
			try {
				CpRoleVO role = CpbServiceFacility.getCpRoleQry().getRoleByRolecode(CpRoleVO.DEFAULT_ROLECODE);
				if(role==null)
					return;
				CpUserRoleVO userrole = new CpUserRoleVO();
				userrole.setPk_user(pk_user);
				userrole.setEnabledate(new UFDate());
				userrole.setDisabledate(new UFDate(System.currentTimeMillis()+365*60*60*60*1000L));
				userrole.setPk_role(role.getPk_role());
				CpbServiceFacility.getCpUserRoleBill().addPtRoleUserVO(userrole);
			} catch (CpbBusinessException e) {
				LfwLogger.error(e.getMessage(),e);
			}
		}
		if(actionType.equals(ICpbExtentionService.USER_RELATE_ROLE)||actionType.equals(ICpbExtentionService.USER_DELETE_ROLE)){
			String pk_user = (String) obj;
			UserCacheUtil.clearUserRolesCache(new String[] { pk_user});
		}
		
	}

	@Override
	public void beforeAction(String functionType, String actionType,Object object) {
		
	}

}
