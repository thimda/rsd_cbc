package nc.uap.cpb.org.dept;

import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.user.UserCacheUtil;
import nc.uap.cpb.org.util.CpbServiceFacility;
import nc.uap.cpb.org.vos.CpUserVO;
import nc.uap.lfw.core.log.LfwLogger;

/**
 * ���Ż��湤���� 2011-6-7 ����01:23:23 limingf
 */
public class DeptCacheUtil {
	/**
	 * ���ݲ�������������û���ɫ����
	 * 
	 * @param pk_users
	 */
	public static void clearDeptRolesCache(String pk_dept) {
		CpUserVO[] users = null;
		try {
			users = CpbServiceFacility.getCpUserQry().getAllUserByPkDept(pk_dept);
		} catch (CpbBusinessException e) {
			LfwLogger.error(e.getMessage(), e);
		}
		UserCacheUtil.clearUserRolesCache(users);
		//LfwCacheMonitor.get
		//LfwCacheManager.getFileCache(name, dsName)
	}
}
