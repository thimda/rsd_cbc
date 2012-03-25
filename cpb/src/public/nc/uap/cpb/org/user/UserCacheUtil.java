package nc.uap.cpb.org.user;

import nc.uap.cpb.org.util.SecurityUtil;
import nc.uap.cpb.org.vos.CpUserVO;
import nc.uap.lfw.core.cache.ILfwCache;
import nc.uap.lfw.core.cache.LfwCacheManager;
import nc.uap.lfw.core.common.WebConstant;

/**
 * �û����湤����
 * 2011-6-7 ����01:23:23  limingf
 */

public class UserCacheUtil { 
	
	/**
	 * ����û���ɫ����
	 * @param pk_users
	 */
	public static void clearUserRolesCache(String[] pk_users){
		if(pk_users==null||pk_users.length<1)return;
		ILfwCache cache = LfwCacheManager.getStrongCache(WebConstant.LFW_CORE_CACHE, null);
		for(String pk_user:pk_users){
			String cacheKey = pk_user+"_"+SecurityUtil.USER_ROLES_CACHE;
			cache.remove(cacheKey);
		}
	}
	/**
	 * ����û���ɫ����
	 * @param users
	 */
	public static void clearUserRolesCache(CpUserVO[] users){
		if(users==null||users.length<1)return;
		String[] pk_users = new String[users.length];
		for (int i = 0; i < users.length; i++) {
			pk_users[i] = users[i].getCuserid();
		}
		UserCacheUtil.clearUserRolesCache(pk_users);
	}
	
	/**
	 * �����û���ɫ����
	 * @param pk_users
	 */
//	public static void updateUserRolesCache(String[] pk_users){
//		ILfwCache cache = LfwCacheManager.getStrongCache(WebConstant.LFW_CORE_CACHE, null);
//		for(String pk_user:pk_users){
//			String cacheKey = pk_user+"_"+SecurityUtil.USER_ROLES_CACHE;
//			List<String> rolePks =  (List<String>) cache.get(cacheKey);	
//			//�������Ϊ�գ�������
//			if(rolePks == null)return;			
//			PtRoleVO[] roles = null;
//			try {
//				roles = NCLocator.getInstance().lookup(IPortalServerService.class).getUserRoles(pk_user, true);
//				if(roles != null && roles.length > 0){
//					for(int i = 0; i < roles.length; i++)
//						rolePks.add(roles[i].getPk_role());
//				}
//			} 
//			catch (PortalServiceException e) {
//				LfwLogger.error(e.getMessage(),e);
//			}
//		}
//	}

}
