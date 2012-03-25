package nc.uap.cpb.org.itf;
import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.vos.CpRoleVO;
/**
 * 角色查询接口
 * 
 * @author zhangxya
 * 
 */
public interface ICpRoleQry {
	/**
	 * 查询所有角色
	 * 
	 * @return
	 * @throws PortalServiceException
	 */
	public CpRoleVO[] getAllRoles() throws CpbBusinessException;
	/**
	 * 根据where条件查询角色
	 * 
	 * @return
	 * @throws PortalServiceException
	 */
	public CpRoleVO[] getRoleVos(String where) throws CpbBusinessException;
	
	
	/**
	 * 除去指定pk外的所有角色
	 * 
	 * @param pk_role
	 * @return
	 * @throws PortalServiceException
	 */
	public CpRoleVO[] getAllRolesExcp(String pk_role) throws CpbBusinessException;
	/**
	 * 根据pk查询角色
	 * 
	 * @param pk_role
	 * @return
	 * @throws PortalServiceException
	 */
	public CpRoleVO getRoleByPk(String pk_role) throws CpbBusinessException;
	/**
	 * 根据部门pk查询角色
	 * 
	 * @param pk_dept
	 * @return
	 * @throws PortalServiceException
	 */
	public CpRoleVO[] getRoleByDeptPk(String pk_dept) throws CpbBusinessException;
	/**
	 * 根据角色编码查询角色
	 * 
	 * @param rolecode
	 * @return
	 * @throws PortalServiceException
	 */
	public CpRoleVO getRoleByRolecode(String rolecode) throws CpbBusinessException;
	/**
	 * 根据角色名称查询角色
	 * 
	 * @param rolename
	 * @return
	 * @throws PortalServiceException
	 */
	public CpRoleVO getRoleByRolename(String rolename) throws CpbBusinessException;
	/**
	 * 根据角色编码，角色名称，角色组查询角色
	 * 
	 * @param pk_rolegroup
	 * @param rolecode
	 * @param rolename
	 * @return
	 * @throws PortalServiceException
	 */
	public CpRoleVO getRoleByRole(String pk_rolegroup, String rolecode, String rolename) throws CpbBusinessException;
	/**
	 * 根据角色类型，角色名称，角色组查询角色
	 * 
	 * @param pk_rolegroup
	 * @param rolecode
	 * @param rolename
	 * @return
	 * @throws PortalServiceException
	 */
	public CpRoleVO getRoleByRole(int type, String rolecode, String rolename) throws CpbBusinessException;
	/**
	 * 根据多个角色编码批量获取角色
	 * 
	 * @param pk_roles
	 * @return
	 * @throws PortalServiceException
	 */
	public CpRoleVO[] getRolesByPk(String[] pk_roles) throws CpbBusinessException;
	/**
	 * 根据角色组pk查询角色
	 * 
	 * @param pk_rolegroup
	 * @return
	 * @throws PortalServiceException
	 */
	public CpRoleVO[] getRoleByRoleGroup(String pk_rolegroup) throws CpbBusinessException;
	/**
	 * 获取用户对应的角色。
	 * 
	 * @param pk_user
	 * @param withGroup
	 *            表明是否也取用户所属组对应的角色
	 * @return
	 * @throws PortalServiceException
	 */
	public CpRoleVO[] getUserRoles(String pk_user, boolean withGroup) throws CpbBusinessException;
	/**
	 * 根据pk_user查询关联roles
	 * 
	 * @param pk_user
	 * @return
	 * @throws PortalServiceException
	 */
	public CpRoleVO[] getUserRoles(String pk_user) throws CpbBusinessException;
	/**
	 * 按条件查询角色
	 * 
	 * @param voClass
	 * @param condition
	 * @return
	 * @throws PortalServiceException
	 */
	public CpRoleVO[] getAllRoleByCondition(Class<?> voClass, String condition) throws CpbBusinessException;
	/**
	 * 获取用户角色根据用户PK
	 * 
	 * @param userPk
	 * @return
	 * @throws CpbBusinessException
	 */
	public String[] getRolePksByUserPk(String userPk) throws CpbBusinessException;
}
