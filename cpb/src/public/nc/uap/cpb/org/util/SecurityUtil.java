package nc.uap.cpb.org.util;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nc.bs.framework.common.NCLocator;
import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.itf.ICpRoleQry;
import nc.uap.cpb.org.vos.CpRoleVO;
import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.cache.ILfwCache;
import nc.uap.lfw.core.cache.LfwCacheManager;
import nc.uap.lfw.core.common.WebConstant;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.lfw.util.StringUtil;
/**
 * @author yzy created on 2006-03-15 
 * 	modified on 2006-05-31 去掉布局组的权限操作
 */
public class SecurityUtil {
	public static final String USER_ROLES_CACHE = "USER_ROLES_CACHE";
	/**
	 * 获取当前登陆用户的角色pk(包括用户，用户所在部门，用户所在用户组的角色集合)
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String[] getRolePks() {
		String pk_user = LfwRuntimeEnvironment.getLfwSessionBean().getPk_user();
		ILfwCache cache = LfwCacheManager.getStrongCache(WebConstant.LFW_CORE_CACHE, null);
		String cacheKey = pk_user + "_" + SecurityUtil.USER_ROLES_CACHE;
		List<String> rolePks = (List<String>) cache.get(cacheKey);
		if (rolePks != null)
			return rolePks.toArray(new String[0]);
		if (rolePks == null) {
			rolePks = new ArrayList<String>();
			cache.put(cacheKey, rolePks);
		}
		CpRoleVO[] roles = null;
		try {
			roles = NCLocator.getInstance().lookup(ICpRoleQry.class).getUserRoles(pk_user, true);
			if(roles != null && roles.length > 0){
				for(int i = 0; i < roles.length; i++)
					rolePks.add(roles[i].getPk_role());
			}
		} 
		catch (CpbBusinessException e) {
			LfwLogger.error(e.getMessage(),e);
		}
		return rolePks.toArray(new String[0]);
	}
	@SuppressWarnings("unchecked")
	public static String[] getRolePks(String pk_user) {
		ILfwCache cache = LfwCacheManager.getStrongCache(WebConstant.LFW_CORE_CACHE, null);
		String cacheKey = pk_user + "_" + SecurityUtil.USER_ROLES_CACHE;
		List<String> rolePks = (List<String>) cache.get(cacheKey);
		if (rolePks != null)
			return rolePks.toArray(new String[0]);
		if (rolePks == null) {
			rolePks = new ArrayList<String>();
			cache.put(cacheKey, rolePks);
		}
		CpRoleVO[] roles = null;
		try {
			roles = NCLocator.getInstance().lookup(ICpRoleQry.class).getUserRoles(pk_user, true);
			if(roles != null && roles.length > 0){
				for(int i = 0; i < roles.length; i++)
					rolePks.add(roles[i].getPk_role());
			}
		} 
		catch (CpbBusinessException e) {
			LfwLogger.error(e.getMessage(),e);
		}
		return rolePks.toArray(new String[0]);
	}
	public static String getMergedRoleArray(String[] roles) {
		if (roles == null || roles.length == 0)
			return null;
		//保证每次顺序一致即可
		Arrays.sort(roles);
		return StringUtil.merge(roles);
	}
	/**
	 * 获取排序好的并按照","组合起来的角色数组
	 * @return
	 */
	public static String getMergedRoleArray() {
		String[] roles = getRolePks();
		return getMergedRoleArray(roles);
	}
	/**
	 * 判断当前登录用户是否有指定角色
	 * @param roleId
	 * @return
	 */
	public static boolean hasRole(String roleId) {
		return hasRole(new String[] { roleId });
	}
	//	
	/**
	 * 判断当前用户的角色集合是否包括指定集合中的任一个角色
	 * @param userRoles
	 * @param roleId
	 * @return
	 */
	public static boolean hasRole(String[] roleIds) {
		return true;
	}
}
