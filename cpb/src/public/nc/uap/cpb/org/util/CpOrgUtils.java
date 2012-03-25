package nc.uap.cpb.org.util;

import nc.uap.lfw.core.LfwRuntimeEnvironment;

/**
 * nc组织参照帮助类
 * 2012-3-20 下午08:23:46
 * @author limingf
 *
 */
public class CpOrgUtils {
	public static String getOrgReferenceWhere(){
		String pk_group = LfwRuntimeEnvironment.getLfwSessionBean().getPk_unit();
		String where = " pk_group = '" + pk_group + "'  and (isbusinessunit ='Y' or orgtype1 = 'Y') and (enablestate = 2)" ;
		return where;
	}
}
