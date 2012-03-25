package nc.uap.cpb.org.itf;
import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.vos.CpRoleResVO;
/**
 * 角色授权资源查询 2010-10-28 下午01:43:19 limingf
 */
public interface ICpRoleResourceQry {
	/**
	 * 查询角色授权的资源
	 * 
	 * @param pk_roles
	 * @param resourcetype
	 * @return
	 * @throws PortalServiceException
	 */
	public CpRoleResVO[] getPtRoleResourceByPkRoles(String[] pk_roles, int resourcetype) throws CpbBusinessException;
	/**
	 * 查询角色资源
	 * 
	 * @param pk_resource
	 * @return
	 * @throws PortalServiceException
	 */
	public CpRoleResVO[] getRoleResourceByPk_resource(String pk_resource) throws CpbBusinessException;
}
