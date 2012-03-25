package nc.uap.cpb.org.usergroup;

import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.user.UserCacheUtil;
import nc.uap.cpb.org.util.CpbServiceFacility;
import nc.uap.cpb.org.vos.CpUserVO;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;

/**
 * 用户组缓存工具类 2011-6-7 下午01:23:23 limingf
 */
public class UserGroupCacheUtil {
	/**
	 * 根据用户组清除用户组下用户角色缓存
	 * 
	 * @param pk_users
	 */
	public static void clearDeptRolesCache(String pk_usergroup) {
		CpUserVO[] users = null;
		try {
			users = CpbServiceFacility.getCpUserQry().getUserByUserGroup(pk_usergroup);
		} catch (CpbBusinessException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
		UserCacheUtil.clearUserRolesCache(users);
	}
}
