package nc.uap.wfm.itf;

import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.vo.WfmVirtualRoleVO;

/**
 * 虚拟角色查询
 * 2011-4-26 上午09:06:24 
 * @author limingf
 */
public interface IWfmVirtualRoleQry {
	/**
	 * 查询所有角色
	 * 
	 * @return
	 * @throws PortalServiceException
	 */
	public WfmVirtualRoleVO[] getAllRoles() throws WfmServiceException;

	
	/**
	 * 根据pk查询角色
	 * 
	 * @param pk_role
	 * @return
	 * @throws PortalServiceException
	 */
	public WfmVirtualRoleVO[] getRoleByPk(String[] pk_roles) throws WfmServiceException;
	
		
	/**
	 * 根据角色编码查询角色
	 * 
	 * @param rolecode
	 * @return
	 * @throws PortalServiceException
	 */
	public WfmVirtualRoleVO getRoleByCode(String code) throws WfmServiceException;
	/**
	 * 根据角色名称查询角色
	 * @param rolename
	 * @return
	 * @throws PortalServiceException
	 */
	public WfmVirtualRoleVO getRoleByName(String name) throws WfmServiceException;
	
	/**
	 * 根据角色名称查询角色
	 * @param rolename
	 * @return
	 * @throws PortalServiceException
	 */
	public WfmVirtualRoleVO getRoleByName(String pk_group,String name) throws WfmServiceException;
	
	/**
	 * 根据集团pk查询角色
	 * @param pk_group
	 * @return
	 * @throws PortalServiceException
	 */
	public WfmVirtualRoleVO[] getRoleByGroup(String pk_group) throws WfmServiceException;
	
}
