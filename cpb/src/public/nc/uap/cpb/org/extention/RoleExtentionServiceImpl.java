package nc.uap.cpb.org.extention;
import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.user.UserCacheUtil;
import nc.uap.cpb.org.util.CpbServiceFacility;
import nc.uap.cpb.org.vos.CpRoleVO;
import nc.uap.cpb.org.vos.CpUserRoleVO;
import nc.uap.lfw.core.log.LfwLogger;

/**
 *  角色管理扩展实现
 * 2012-3-26 下午02:43:26
 * @author limingf
 *
 */
public class RoleExtentionServiceImpl implements ICpbExtentionService {
	public String acceptFunType() {
		return ICpbExtentionService.ROLEMANAGE;
	}
	public void afterAction(String functionType, String actionType, Object object) {
		if (actionType.equals(ICpbExtentionService.ROLE_RELATE_USER)) {
			if (object instanceof CpUserRoleVO) {
				CpUserRoleVO roleuser = null;
				roleuser = (CpUserRoleVO) object;
				UserCacheUtil.clearUserRolesCache(new String[] { roleuser.getPk_user() });
			}
		} else if (actionType.equals(ICpbExtentionService.ROLE_DELETE_USER)) {
			if (object instanceof CpUserRoleVO) {
				CpUserRoleVO roleuser = null;
				roleuser = (CpUserRoleVO) object;
				UserCacheUtil.clearUserRolesCache(new String[] { roleuser.getPk_user() });
			}
		}
		else if (actionType.equals(ICpbExtentionService.DELETE)) {
			if (object instanceof CpRoleVO) {
				CpRoleVO role = null;
				role = (CpRoleVO) object;
				try {
					CpbServiceFacility.getCpUserRoleBill().deletePtRoleUserByRolepk(role.getPk_role());
				} catch (CpbBusinessException e) {
					LfwLogger.error(e.getMessage(), e);
				}
			}
		}
	}
	public void beforeAction(String functionType, String actionType, Object object) {
		if (actionType.equals(ICpbExtentionService.ADD)) {}
	}
}
