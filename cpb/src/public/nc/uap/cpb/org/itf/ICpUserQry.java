package nc.uap.cpb.org.itf;
import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.vos.CpUserVO;
import nc.uap.lfw.core.data.PaginationInfo;
import nc.vo.sm.UserVO;
/**
 * 用户查询接口
 * 
 * @author zhangxya
 * 
 */
public interface ICpUserQry {
	/**
	 * 查询所有用户
	 * 
	 * @return
	 * @throws PortalServiceException
	 */
	public CpUserVO[] getAllUsers() throws CpbBusinessException;
	/**
	 * 根据where条件查询用户
	 * 
	 * @param where
	 * @return
	 * @throws CpbBusinessException
	 */
	public CpUserVO[] getUserByWhere(String where) throws CpbBusinessException;
	/**
	 * 根据用户类型查询此类型的所有用户
	 * 
	 * @param usertype
	 * @return
	 * @throws PortalServiceException
	 */
	public CpUserVO[] getAllUsersByPkCorp(String pk_corp, PaginationInfo pfinfo) throws CpbBusinessException;
	/**
	 * 查询公司下的用户
	 * 
	 * @param pk_corp
	 * @return
	 * @throws PortalServiceException
	 */
	public CpUserVO[] getAllUsersByPkCorp(String pk_corp) throws CpbBusinessException;
	/**
	 * 根据pk查询用户
	 * 
	 * @param pk_user
	 * @return
	 * @throws PortalServiceException
	 */
	public CpUserVO getUserByPk(String pk_user) throws CpbBusinessException;
	/**
	 * 根据多个用户pk查询批量用户
	 * 
	 * @param pk_users
	 * @return
	 * @throws PortalServiceException
	 */
	public CpUserVO[] getUserByPkS(String[] pk_users) throws CpbBusinessException;
	/**
	 * 根据用户组pk查询用户
	 * 
	 * @param userId
	 * @return
	 * @throws PortalServiceException
	 */
	public CpUserVO[] getUserByUserGroup(String pk_usergroup) throws CpbBusinessException;
	/**
	 * 根据用户Id查询用户(只查找portal用户)
	 * 
	 * @param userId
	 * @return
	 * @throws PortalServiceException
	 */
	public CpUserVO getUserByCode(String userCode) throws CpbBusinessException;
	
	/**
	 * 根据用户Id查询用户(包括portal全部用户,nc集团管理员用户),
	 * 
	 * @param userId
	 * @return
	 * @throws PortalServiceException
	 */
	public CpUserVO getUserByCodeWithGroupAdmin(String userId) throws CpbBusinessException ;
	/**
	 * 根据用户Id查询用户(包括portal，nc全部用户),
	 * 
	 * @param userId
	 * @return
	 * @throws PortalServiceException
	 */
	public CpUserVO getGlobalUserByCode(String userId) throws CpbBusinessException ;
	/**
	 * 根据用户序列号查询用户
	 * 
	 * @param userId
	 * @return
	 * @throws PortalServiceException
	 */
	public CpUserVO getUserBySerialNo(String serialno) throws CpbBusinessException;
	/**
	 * 根据用户名称查询用户
	 * 
	 * @param username
	 * @return
	 * @throws PortalServiceException
	 */
	public CpUserVO getUserByUsername(String username) throws CpbBusinessException;
	/**
	 * 通过某字段查询vo
	 * 
	 * @param condition
	 * @return
	 * @throws PortalServiceException
	 */
	public CpUserVO[] getUserByCondition(String value) throws CpbBusinessException;
	/**
	 * 根据original_pk获取用户信息
	 * 
	 * @param original_pk
	 * @return
	 * @throws PortalServiceException
	 */
	public CpUserVO getUserByOrigiPk(String original_pk) throws CpbBusinessException;
	/**
	 * 查询除掉指定pk外的所有用户
	 * 
	 * @param pk_user
	 * @return
	 * @throws PortalServiceException
	 */
	public CpUserVO[] getAllUsersExcep(String pk_user, String pk_group) throws CpbBusinessException;
	/**
	 * 查询指定公司下的所有公司用户
	 * 
	 * @param pk_corp
	 * @param pk_user
	 * @return
	 * @throws PortalServiceException
	 */
	public CpUserVO[] getCorpUsersExcep(String pk_corp, String pk_user) throws CpbBusinessException;
	/**
	 * 根据密码安全策略查询用户
	 * 
	 * @param pwdlevelcode
	 * @return
	 * @throws PortalServiceException
	 */
	public CpUserVO[] getUserByPwdlevelCode(String pwdlevelcode) throws CpbBusinessException;
	/**
	 * 根据pk_user查询bbsUser
	 * 
	 * @param ptUserPk
	 * @return
	 * @throws PortalServiceException
	 */
	public CpUserVO[] getAllUserByCondition(Class<?> voClass, String condition) throws CpbBusinessException;
	/**
	 * 查询密码安全策略
	 * 
	 * @return
	 * @throws PortalServiceException
	 */
	// public PtPasswordSecurityVO[] getPaswordSecurity() throws
	// CpbBusinessException;
	/**
	 * 获取参数角色对应的普通用户pk
	 * 
	 * @param pk_roles
	 * @param pk_group
	 * @return
	 * @throws PortalServiceException
	 */
	public String[] getUserPksByRoles(String[] pk_roles) throws CpbBusinessException;
	/**
	 * 获取当前集团下所有普通用户pk
	 * 
	 * @param pk_group
	 * @return
	 * @throws PortalServiceException
	 */
	public String[] getUserPks(String pk_group) throws CpbBusinessException;
	/**
	 * 查询集团下的用户
	 * 
	 * @param pk_group
	 * @return
	 * @throws PortalServiceException
	 */
	public CpUserVO[] getUserByPkGroup(String pk_group) throws CpbBusinessException;
	/**
	 * 查询组织下的用户
	 * 
	 * @param pk_org
	 * @return
	 * @throws PortalServiceException
	 */
	public CpUserVO[] getUserByPkorg(String pk_org) throws CpbBusinessException;
	/**
	 * 查询部门下的所有用户
	 * 
	 * @param pk_corp
	 * @return
	 * @throws PortalServiceException
	 */
	public CpUserVO[] getAllUserByPkDept(String pk_dept, PaginationInfo pfinfo) throws CpbBusinessException;
	/**
	 * 查询部门下的用户
	 * 
	 * @param pk_dept
	 * @return
	 * @throws PortalServiceException
	 */
	public CpUserVO[] getAllUserByPkDept(String pk_dept) throws CpbBusinessException;
	/**
	 * 根据集团pk，系统编码查询用户
	 * 
	 * @param pk_group
	 * @param systemCode
	 * @return
	 * @throws PortalServiceException
	 */
	public CpUserVO[] getAllUsersByPkCorp(String pk_group, String systemCode) throws CpbBusinessException;
	/**
	 * 查询角色下的所有的用户
	 * 
	 * @param pk_role
	 * @return
	 * @throws PortalServiceException
	 */
	public CpUserVO[] getAllUserByPkRole(String pk_role) throws CpbBusinessException;
	/**
	 * 根据用户Pk查询NC用户。
	 * 
	 * @param userPk
	 * @return
	 * @throws CpbBusinessException
	 */
	public UserVO getNCUserByUserPk(String userPk) throws CpbBusinessException;
}