package nc.uap.cpb.org.itf;

import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.vos.CpUserGroupRoleVO;

/**
 * 用户组关联角色操作接口
 * 
 * @author zhangxya
 * 
 */
public interface ICpUserGroupRoleBill {

	/**
	 *插入用户组角色vo
	 * 
	 * @param usergroupRolevo
	 * @throws PortalServiceException
	 */
	public String addPtUserGroupRoleVO(CpUserGroupRoleVO usergroupRolevo) throws CpbBusinessException;

	/**
	 *批量插入用户组角色vo
	 * 
	 * @param usergroupRolevo
	 * @throws PortalServiceException
	 */
	public String[] addPtUserGroupRoleVOs(CpUserGroupRoleVO[] usergroupRolevos) throws CpbBusinessException;

	/**
	 * 更新用户组角色vo
	 * 
	 * @param usergroupRolevo
	 * @throws PortalServiceException
	 */
	public void updatePtUserGroupRoleVO(CpUserGroupRoleVO usergroupRolevo) throws CpbBusinessException;

	/**
	 * 删除用户组角色vo
	 * 
	 * @param usergroupRolevo
	 * @throws PortalServiceException
	 */
	public void deletePtUserGroupRoleVO(CpUserGroupRoleVO usergroupRolevo) throws CpbBusinessException;

}