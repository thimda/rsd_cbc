package nc.uap.cpb.org.itf;

import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.vos.CpUserGroupRoleVO;

public interface ICpUserGroupRoleQry {

	/**
	 * 根据用户pk查询所有角色组
	 * 
	 * @return
	 * @throws PortalServiceException
	 */
	public CpUserGroupRoleVO[] getUgRoleByUserPk(String pk_user) throws CpbBusinessException;

	/**
	 * 根据角色pk查询角色所关联的用户组
	 * 
	 * @param pk_role
	 * @return
	 * @throws PortalServiceException
	 */
	public CpUserGroupRoleVO[] getUgRoleByRolePk(String pk_role) throws CpbBusinessException;

}
