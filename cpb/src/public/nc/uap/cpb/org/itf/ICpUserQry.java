package nc.uap.cpb.org.itf;
import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.vos.CpUserVO;
import nc.uap.lfw.core.data.PaginationInfo;
import nc.vo.sm.UserVO;
/**
 * �û���ѯ�ӿ�
 * 
 * @author zhangxya
 * 
 */
public interface ICpUserQry {
	/**
	 * ��ѯ�����û�
	 * 
	 * @return
	 * @throws PortalServiceException
	 */
	public CpUserVO[] getAllUsers() throws CpbBusinessException;
	/**
	 * ����where������ѯ�û�
	 * 
	 * @param where
	 * @return
	 * @throws CpbBusinessException
	 */
	public CpUserVO[] getUserByWhere(String where) throws CpbBusinessException;
	/**
	 * �����û����Ͳ�ѯ�����͵������û�
	 * 
	 * @param usertype
	 * @return
	 * @throws PortalServiceException
	 */
	public CpUserVO[] getAllUsersByPkCorp(String pk_corp, PaginationInfo pfinfo) throws CpbBusinessException;
	/**
	 * ��ѯ��˾�µ��û�
	 * 
	 * @param pk_corp
	 * @return
	 * @throws PortalServiceException
	 */
	public CpUserVO[] getAllUsersByPkCorp(String pk_corp) throws CpbBusinessException;
	/**
	 * ����pk��ѯ�û�
	 * 
	 * @param pk_user
	 * @return
	 * @throws PortalServiceException
	 */
	public CpUserVO getUserByPk(String pk_user) throws CpbBusinessException;
	/**
	 * ���ݶ���û�pk��ѯ�����û�
	 * 
	 * @param pk_users
	 * @return
	 * @throws PortalServiceException
	 */
	public CpUserVO[] getUserByPkS(String[] pk_users) throws CpbBusinessException;
	/**
	 * �����û���pk��ѯ�û�
	 * 
	 * @param userId
	 * @return
	 * @throws PortalServiceException
	 */
	public CpUserVO[] getUserByUserGroup(String pk_usergroup) throws CpbBusinessException;
	/**
	 * �����û�Id��ѯ�û�(ֻ����portal�û�)
	 * 
	 * @param userId
	 * @return
	 * @throws PortalServiceException
	 */
	public CpUserVO getUserByCode(String userCode) throws CpbBusinessException;
	
	/**
	 * �����û�Id��ѯ�û�(����portalȫ���û�,nc���Ź���Ա�û�),
	 * 
	 * @param userId
	 * @return
	 * @throws PortalServiceException
	 */
	public CpUserVO getUserByCodeWithGroupAdmin(String userId) throws CpbBusinessException ;
	/**
	 * �����û�Id��ѯ�û�(����portal��ncȫ���û�),
	 * 
	 * @param userId
	 * @return
	 * @throws PortalServiceException
	 */
	public CpUserVO getGlobalUserByCode(String userId) throws CpbBusinessException ;
	/**
	 * �����û����кŲ�ѯ�û�
	 * 
	 * @param userId
	 * @return
	 * @throws PortalServiceException
	 */
	public CpUserVO getUserBySerialNo(String serialno) throws CpbBusinessException;
	/**
	 * �����û����Ʋ�ѯ�û�
	 * 
	 * @param username
	 * @return
	 * @throws PortalServiceException
	 */
	public CpUserVO getUserByUsername(String username) throws CpbBusinessException;
	/**
	 * ͨ��ĳ�ֶβ�ѯvo
	 * 
	 * @param condition
	 * @return
	 * @throws PortalServiceException
	 */
	public CpUserVO[] getUserByCondition(String value) throws CpbBusinessException;
	/**
	 * ����original_pk��ȡ�û���Ϣ
	 * 
	 * @param original_pk
	 * @return
	 * @throws PortalServiceException
	 */
	public CpUserVO getUserByOrigiPk(String original_pk) throws CpbBusinessException;
	/**
	 * ��ѯ����ָ��pk��������û�
	 * 
	 * @param pk_user
	 * @return
	 * @throws PortalServiceException
	 */
	public CpUserVO[] getAllUsersExcep(String pk_user, String pk_group) throws CpbBusinessException;
	/**
	 * ��ѯָ����˾�µ����й�˾�û�
	 * 
	 * @param pk_corp
	 * @param pk_user
	 * @return
	 * @throws PortalServiceException
	 */
	public CpUserVO[] getCorpUsersExcep(String pk_corp, String pk_user) throws CpbBusinessException;
	/**
	 * �������밲ȫ���Բ�ѯ�û�
	 * 
	 * @param pwdlevelcode
	 * @return
	 * @throws PortalServiceException
	 */
	public CpUserVO[] getUserByPwdlevelCode(String pwdlevelcode) throws CpbBusinessException;
	/**
	 * ����pk_user��ѯbbsUser
	 * 
	 * @param ptUserPk
	 * @return
	 * @throws PortalServiceException
	 */
	public CpUserVO[] getAllUserByCondition(Class<?> voClass, String condition) throws CpbBusinessException;
	/**
	 * ��ѯ���밲ȫ����
	 * 
	 * @return
	 * @throws PortalServiceException
	 */
	// public PtPasswordSecurityVO[] getPaswordSecurity() throws
	// CpbBusinessException;
	/**
	 * ��ȡ������ɫ��Ӧ����ͨ�û�pk
	 * 
	 * @param pk_roles
	 * @param pk_group
	 * @return
	 * @throws PortalServiceException
	 */
	public String[] getUserPksByRoles(String[] pk_roles) throws CpbBusinessException;
	/**
	 * ��ȡ��ǰ������������ͨ�û�pk
	 * 
	 * @param pk_group
	 * @return
	 * @throws PortalServiceException
	 */
	public String[] getUserPks(String pk_group) throws CpbBusinessException;
	/**
	 * ��ѯ�����µ��û�
	 * 
	 * @param pk_group
	 * @return
	 * @throws PortalServiceException
	 */
	public CpUserVO[] getUserByPkGroup(String pk_group) throws CpbBusinessException;
	/**
	 * ��ѯ��֯�µ��û�
	 * 
	 * @param pk_org
	 * @return
	 * @throws PortalServiceException
	 */
	public CpUserVO[] getUserByPkorg(String pk_org) throws CpbBusinessException;
	/**
	 * ��ѯ�����µ������û�
	 * 
	 * @param pk_corp
	 * @return
	 * @throws PortalServiceException
	 */
	public CpUserVO[] getAllUserByPkDept(String pk_dept, PaginationInfo pfinfo) throws CpbBusinessException;
	/**
	 * ��ѯ�����µ��û�
	 * 
	 * @param pk_dept
	 * @return
	 * @throws PortalServiceException
	 */
	public CpUserVO[] getAllUserByPkDept(String pk_dept) throws CpbBusinessException;
	/**
	 * ���ݼ���pk��ϵͳ�����ѯ�û�
	 * 
	 * @param pk_group
	 * @param systemCode
	 * @return
	 * @throws PortalServiceException
	 */
	public CpUserVO[] getAllUsersByPkCorp(String pk_group, String systemCode) throws CpbBusinessException;
	/**
	 * ��ѯ��ɫ�µ����е��û�
	 * 
	 * @param pk_role
	 * @return
	 * @throws PortalServiceException
	 */
	public CpUserVO[] getAllUserByPkRole(String pk_role) throws CpbBusinessException;
	/**
	 * �����û�Pk��ѯNC�û���
	 * 
	 * @param userPk
	 * @return
	 * @throws CpbBusinessException
	 */
	public UserVO getNCUserByUserPk(String userPk) throws CpbBusinessException;
}