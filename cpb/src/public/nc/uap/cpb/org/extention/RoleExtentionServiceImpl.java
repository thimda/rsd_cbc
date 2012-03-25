package nc.uap.cpb.org.extention;
import nc.uap.cpb.org.user.UserCacheUtil;
import nc.uap.cpb.org.vos.CpUserRoleVO;
/**
 * 角色管理扩展实现 2011-6-8 上午11:11:09
 * 
 * @author limingf
 */
public class RoleExtentionServiceImpl implements ICpbExtentionService {
	public String acceptFunType() {
		return ICpbExtentionService.ROLEMANAGE;
	}
	public void afterAction(String functionType, String actionType, Object object) {
		CpUserRoleVO roleuser = null;
		if (actionType.equals(ICpbExtentionService.ROLE_RELATE_USER)) {
			if (object instanceof CpUserRoleVO) {
				roleuser = (CpUserRoleVO) object;
				UserCacheUtil.clearUserRolesCache(new String[] { roleuser.getPk_user() });
			}
		} else if (actionType.equals(ICpbExtentionService.ROLE_DELETE_USER)) {
			if (object instanceof CpUserRoleVO) {
				roleuser = (CpUserRoleVO) object;
				UserCacheUtil.clearUserRolesCache(new String[] { roleuser.getPk_user() });
			}
		}
	}
	public void beforeAction(String functionType, String actionType, Object object) {
		if (actionType.equals(ICpbExtentionService.ADD)) {}
	}
}
