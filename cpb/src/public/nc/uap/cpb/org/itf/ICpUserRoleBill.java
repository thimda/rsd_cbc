package nc.uap.cpb.org.itf;

import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.vos.CpUserRoleVO;

/**
 * 用户角色关联操作接口
 * @author zhangxya
 *
 */
public interface ICpUserRoleBill {
	/**
	 * 插入用户角色关联vo
	 * 
	 * @param roleUservo
	 * @throws PortalServiceException
	 */
	public String addPtRoleUserVO(CpUserRoleVO roleUservo) throws CpbBusinessException;

	/**
	 * 插入批量用户角色关联vo
	 * @param roleUservos
	 * @return
	 * @throws PortalServiceException
	 */
	public void addPtRoleUserVOS(CpUserRoleVO[] roleUservos) throws CpbBusinessException;
	
	/**
	 * 更新用户角色关联vo
	 * @param roleUservo
	 * @throws PortalServiceException
	 */
	public void updatePtRoleUserVO(CpUserRoleVO roleUservo) throws CpbBusinessException;
	
	/**
	 * 删除用户角色关联vo
	 * @param roleUservo
	 * @throws PortalServiceException
	 */
	public void deletePtRoleUserVO(CpUserRoleVO roleUservo) throws CpbBusinessException;

	/**
	 * 删除用户角色关联vo
	 * @param pk_role
	 * @throws PortalServiceException
	 */
	public void deletePtRoleUserByRolepk(String pk_role) throws CpbBusinessException;
	
	/**
	 * 删除用户角色关联vo
	 * @param pk_user
	 * @throws PortalServiceException
	 */
	public void deletePtRoleUserByUserpks(String[] pk_users) throws CpbBusinessException;

}