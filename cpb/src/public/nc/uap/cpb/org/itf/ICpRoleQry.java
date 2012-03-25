package nc.uap.cpb.org.itf;
import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.vos.CpRoleVO;
/**
 * ��ɫ��ѯ�ӿ�
 * 
 * @author zhangxya
 * 
 */
public interface ICpRoleQry {
	/**
	 * ��ѯ���н�ɫ
	 * 
	 * @return
	 * @throws PortalServiceException
	 */
	public CpRoleVO[] getAllRoles() throws CpbBusinessException;
	/**
	 * ����where������ѯ��ɫ
	 * 
	 * @return
	 * @throws PortalServiceException
	 */
	public CpRoleVO[] getRoleVos(String where) throws CpbBusinessException;
	
	
	/**
	 * ��ȥָ��pk������н�ɫ
	 * 
	 * @param pk_role
	 * @return
	 * @throws PortalServiceException
	 */
	public CpRoleVO[] getAllRolesExcp(String pk_role) throws CpbBusinessException;
	/**
	 * ����pk��ѯ��ɫ
	 * 
	 * @param pk_role
	 * @return
	 * @throws PortalServiceException
	 */
	public CpRoleVO getRoleByPk(String pk_role) throws CpbBusinessException;
	/**
	 * ���ݲ���pk��ѯ��ɫ
	 * 
	 * @param pk_dept
	 * @return
	 * @throws PortalServiceException
	 */
	public CpRoleVO[] getRoleByDeptPk(String pk_dept) throws CpbBusinessException;
	/**
	 * ���ݽ�ɫ�����ѯ��ɫ
	 * 
	 * @param rolecode
	 * @return
	 * @throws PortalServiceException
	 */
	public CpRoleVO getRoleByRolecode(String rolecode) throws CpbBusinessException;
	/**
	 * ���ݽ�ɫ���Ʋ�ѯ��ɫ
	 * 
	 * @param rolename
	 * @return
	 * @throws PortalServiceException
	 */
	public CpRoleVO getRoleByRolename(String rolename) throws CpbBusinessException;
	/**
	 * ���ݽ�ɫ���룬��ɫ���ƣ���ɫ���ѯ��ɫ
	 * 
	 * @param pk_rolegroup
	 * @param rolecode
	 * @param rolename
	 * @return
	 * @throws PortalServiceException
	 */
	public CpRoleVO getRoleByRole(String pk_rolegroup, String rolecode, String rolename) throws CpbBusinessException;
	/**
	 * ���ݽ�ɫ���ͣ���ɫ���ƣ���ɫ���ѯ��ɫ
	 * 
	 * @param pk_rolegroup
	 * @param rolecode
	 * @param rolename
	 * @return
	 * @throws PortalServiceException
	 */
	public CpRoleVO getRoleByRole(int type, String rolecode, String rolename) throws CpbBusinessException;
	/**
	 * ���ݶ����ɫ����������ȡ��ɫ
	 * 
	 * @param pk_roles
	 * @return
	 * @throws PortalServiceException
	 */
	public CpRoleVO[] getRolesByPk(String[] pk_roles) throws CpbBusinessException;
	/**
	 * ���ݽ�ɫ��pk��ѯ��ɫ
	 * 
	 * @param pk_rolegroup
	 * @return
	 * @throws PortalServiceException
	 */
	public CpRoleVO[] getRoleByRoleGroup(String pk_rolegroup) throws CpbBusinessException;
	/**
	 * ��ȡ�û���Ӧ�Ľ�ɫ��
	 * 
	 * @param pk_user
	 * @param withGroup
	 *            �����Ƿ�Ҳȡ�û��������Ӧ�Ľ�ɫ
	 * @return
	 * @throws PortalServiceException
	 */
	public CpRoleVO[] getUserRoles(String pk_user, boolean withGroup) throws CpbBusinessException;
	/**
	 * ����pk_user��ѯ����roles
	 * 
	 * @param pk_user
	 * @return
	 * @throws PortalServiceException
	 */
	public CpRoleVO[] getUserRoles(String pk_user) throws CpbBusinessException;
	/**
	 * ��������ѯ��ɫ
	 * 
	 * @param voClass
	 * @param condition
	 * @return
	 * @throws PortalServiceException
	 */
	public CpRoleVO[] getAllRoleByCondition(Class<?> voClass, String condition) throws CpbBusinessException;
	/**
	 * ��ȡ�û���ɫ�����û�PK
	 * 
	 * @param userPk
	 * @return
	 * @throws CpbBusinessException
	 */
	public String[] getRolePksByUserPk(String userPk) throws CpbBusinessException;
}
