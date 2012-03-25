package nc.uap.cpb.org.itf;
import nc.uap.cpb.org.exception.CpbBusinessException;
/**
 *角色授权资源 2010-10-28 下午01:43:19 limingf
 */
public interface ICpRoleResourceBill {
	/**
	 * 根据资源pk删除角色授权资源
	 * 
	 * @param pk_resource
	 * @throws PortalServiceException
	 */
	public void deleteRoleResourceByPk_resource(String pk_resource) throws CpbBusinessException;
	/**
	 * 根据角色pk删除角色授权资源
	 * 
	 * @param pk_resource
	 * @throws PortalServiceException
	 */
	public void deleteRoleResourceByRolePk(String pk_role) throws CpbBusinessException;
	public void deleteRoleResourceByRoleResoPk(String roleResoPk) throws CpbBusinessException;
}
