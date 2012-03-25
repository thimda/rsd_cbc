package nc.uap.cpb.org.itf;

import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.vos.CpResourceVO;

/**
 * Portal资源查询服务
 * 
 * @author licza
 * 
 */
public interface ICpResourceQry {

	/**
	 * 根据条件查询Portal资源
	 * 
	 * @param wherePart
	 *            条件
	 * @return
	 * @throws PortalServiceException
	 */
	public CpResourceVO[] queryResources(String wherePart) throws CpbBusinessException;

	/**
	 * 根据资源类型获得模块下已经存在的资源
	 * 
	 * @param module
	 *            模块名
	 * @param tp
	 *            资源类型
	 * @return
	 */
	public CpResourceVO[] getModuleResByType(String module, Integer tp) throws CpbBusinessException;

	/**
	 * 获得用户资源
	 * 
	 * @param pk_user
	 * @return
	 * @throws PortalServiceException
	 */
	public CpResourceVO[] getResoureces(String pk_user) throws  CpbBusinessException;

	/**
	 * 根据角色PK获得资源
	 * 
	 * @param pkroles
	 * @return
	 * @throws PortalServiceException
	 */
	public CpResourceVO[] getResourcesByRoles(String[] pkroles) throws CpbBusinessException;
}
