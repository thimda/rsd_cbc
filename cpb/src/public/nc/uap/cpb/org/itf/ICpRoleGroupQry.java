package nc.uap.cpb.org.itf;

import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.vos.CpRoleGroupVO;

/**
 * 角色组查询接口
 * 
 * @author zhangxya
 * 
 */
public interface ICpRoleGroupQry {
	/**
	 * 查询所有角色组
	 * 
	 * @return
	 * @throws PortalServiceException
	 */
	public CpRoleGroupVO[] getAllRoleGroups() throws CpbBusinessException;

	/**
	 * 根据pk查询角色组
	 * 
	 * @return
	 * @throws PortalServiceException
	 */
	public CpRoleGroupVO getRoleGroupByPk(String pk_rolegroup) throws CpbBusinessException;

	/**
	 * 根据角色组编码查询角色组
	 * 
	 * @param groupcode
	 * @return
	 * @throws PortalServiceException
	 */
	public CpRoleGroupVO getRoleGroupByGroupCode(String groupcode) throws CpbBusinessException;

	/**
	 * 根据角色组名查询角色组
	 * 
	 * @param groupname
	 * @return
	 * @throws PortalServiceException
	 */
	public CpRoleGroupVO getRoleGroupByGroupName(String groupname) throws CpbBusinessException;

	/**
	 * 查询某一公司下的角色组
	 * 
	 * @param pk_corp
	 * @return
	 * @throws PortalServiceException
	 */
	public CpRoleGroupVO[] getRoleGroupByPkcorp(String pk_corp) throws CpbBusinessException;
	
	public CpRoleGroupVO[] getRoleGroupByParent(String pk_parent) throws CpbBusinessException;
	
	/**
	 * 根据where条件查询角色组
	 * @param where
	 * @return
	 * @throws CpbBusinessException
	 */
	public CpRoleGroupVO[] getRoleGroupVos(String where) throws CpbBusinessException;

	/**
	 * 查询集团角色组
	 * 
	 * @param pk_group
	 * @param type
	 * @return
	 * @throws PortalServiceException
	 */
	public CpRoleGroupVO[] getRoleGroupByPkgroup(String pk_group, String type) throws CpbBusinessException;

	/**
	 * 查询集团角色组
	 * 
	 * @param pk_group
	 * @param type
	 * @return
	 * @throws PortalServiceException
	 */
	public CpRoleGroupVO[] getRoleGroupByPkgroup(String pk_group) throws CpbBusinessException;

	/**
	 * 根据多个pk查询批量角色组
	 * 
	 * @param pk_rolegroups
	 * @return
	 * @throws PortalServiceException
	 */
	public CpRoleGroupVO[] getRoleGroupByPkS(String[] pk_rolegroups) throws CpbBusinessException;

	/**
	 * 查询除去pk外的所有角色组
	 * 
	 * @param pk_rolegroup
	 * @return
	 * @throws PortalServiceException
	 */
	public CpRoleGroupVO[] getRoleGroupExc(String pk_rolegroup) throws CpbBusinessException;

	/**
	 * 查询组织角色组
	 * 
	 * @param pk_orgs
	 * @return
	 * @throws PortalServiceException
	 */
	public CpRoleGroupVO[] getRoleGroupByPkorgs(String[] pk_orgs) throws CpbBusinessException;
}
