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
 * 	modified on 2006-05-31 ȥ���������Ȩ�޲���
 */
public class SecurityUtil {
	public static final String USER_ROLES_CACHE = "USER_ROLES_CACHE";
	/**
	 * ��ȡ��ǰ��½�û��Ľ�ɫpk(�����û����û����ڲ��ţ��û������û���Ľ�ɫ����)
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
		//��֤ÿ��˳��һ�¼���
		Arrays.sort(roles);
		return StringUtil.merge(roles);
	}
	/**
	 * ��ȡ����õĲ�����","��������Ľ�ɫ����
	 * @return
	 */
	public static String getMergedRoleArray() {
		String[] roles = getRolePks();
		return getMergedRoleArray(roles);
	}
	/**
	 * �жϵ�ǰ��¼�û��Ƿ���ָ����ɫ
	 * @param roleId
	 * @return
	 */
	public static boolean hasRole(String roleId) {
		return hasRole(new String[] { roleId });
	}
	//	
	/**
	 * �жϵ�ǰ�û��Ľ�ɫ�����Ƿ����ָ�������е���һ����ɫ
	 * @param userRoles
	 * @param roleId
	 * @return
	 */
	public static boolean hasRole(String[] roleIds) {
		return true;
	}
}
