package nc.uap.cpb.org.itf;
import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.vos.CpUserVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFDate;
/**
 * 用户管理操作接口
 * 
 * @author zhangxya
 * 
 */
public interface ICpUserBill {
	/**
	 * 插入用户信息
	 * 
	 * @param uservo
	 * @throws PortalServiceException
	 */
	public String addCpUserVO(CpUserVO uservo) throws CpbBusinessException;
	 
	public String addCpUserVOWithPk(CpUserVO uservo) throws CpbBusinessException;
	
	public void updateCpUserVO(CpUserVO userVo) throws CpbBusinessException;
	/**
	 * 插入用户关联部门vo
	 * 
	 * @param deCpUserVO
	 * @return
	 * @throws PortalServiceException
	 */
	// public String addCpUserDeptVO(PtDeptUserVO deCpUserVO) throws
	// CpbBusinessException;
	/**
	 * 插入多个部门关联用户信息
	 * 
	 * @param deCpUserVO
	 * @return
	 * @throws PortalServiceException
	 */
	// public void addCpUserDeptVOS(PtDeptUserVO[] deCpUserVO) throws
	// CpbBusinessException;
	/**
	 * 插入多条用户关联公司的信息
	 * 
	 * @param corpUserVOs
	 * @throws PortalServiceException
	 */
	// public void addCpUserCorpVOS(PtCorpUserVO[] corpUserVOs) throws
	// CpbBusinessException;
	/**
	 * 更新用户所用语言
	 * 
	 * @param uservo
	 * @param languageId
	 * @throws PortalServiceException
	 */
	public void changeUserLanguage(String pk_user, String languageId) throws CpbBusinessException;
	/**
	 * 更新用户主题
	 * 
	 * @param pk_user
	 * @param themeId
	 * @throws PortalServiceException
	 */
	public void changeUserTheme(String pk_user, String themeId) throws CpbBusinessException;
	/**
	 * 删除用户信息
	 * 
	 * @param pk_user
	 * @throws PortalServiceException
	 */
	public void deleteCpUserVO(String pk_user) throws CpbBusinessException;
	/**
	 * 删除用户信息
	 * 
	 * @param uservo
	 * @throws PortalServiceException
	 */
	public void deleteCpUserVO(CpUserVO uservo) throws CpbBusinessException;
	/**
	 * 删除用户信息
	 * 
	 * @param uservos
	 * @throws PortalServiceException
	 */
	public void deleteCpUserVOs(CpUserVO[] uservos) throws CpbBusinessException;
	/**
	 * 修改portal用户口令 * @param userId
	 * 
	 * @param newPwd
	 * @throws PortalServiceException
	 */
	public void updateUserPwd(String pk_user, String newPwd, UFDate passwordmodifydate) throws CpbBusinessException;
	/**
	 * 校验密码安全策略
	 * @param cpUserVO
	 * @return
	 * @throws BusinessException
	 */
	public boolean checkPwdLevel(CpUserVO cpUserVO)throws BusinessException;
	/**
	 * 用户进入portal验证，验证后组成portal系统中的UserVO返回
	 * 
	 * @param userName
	 *            用户名
	 * @param password
	 *            口令
	 * @return portal系统的UserVO实体
	 * @throws UserNotFoundException
	 *             用户不存在
	 * @throws UserAccessException
	 *             口令不正确或者不允许登录
	 * @throws BusinessException
	 *             其它例外信息
	 */
	// CpUserVO authenticateUser(String userName, String password, Object
	// userObject)
	// throws UserNotFoundException, UserAccessException,
	// BusinessException;
	/**
	 * 获得符合条件的指定位置的用户记录（为portal管理使用，用于搜索和分页显示） 约定:当pageSize<=0时，返回符合条件的所有用户记录信息。
	 * 
	 * @param pageSize
	 * @param pageNumber
	 * @param userId
	 *            对应于Portal用户的userid字段的模糊值，第三方Provider根据自己定义的映射关系构造自己的Sql语句进行查询。
	 * @return
	 * @throws BusinessException
	 */
	// List<CpUserVO> getUsers(int pageSize,int pageNumber,String userId) throws
	// BusinessException;
	/**
	 * 通过此方法获取符合条件的用户的总人数
	 * 
	 * @param userId
	 *            对应于Portal用户的userID的模糊值。
	 * @return
	 * @throws BusinessException
	 */
	// int getCountofUsers(String userId) throws BusinessException;
	/**
	 * 更新用户信息，由用户的提供者选择这些用户信息如何映射成其内部的用户信息并进行更新。
	 * 关于映射规则是在该类的authenticateUser方法中由第三方提供者自己定义的。用户可以
	 * 选择不更新数据库信息，这样在页面维护页面中可以直接将通过<code>ISecurityQueryService</code>
	 * 的getUserById1(userId)方法获得的UserVO作为验证方法的返回值。
	 * 
	 * 对于第三方的用户提供者暂时不提供对用户的添加功能。
	 * 
	 * @param user
	 * @throws BusinessException
	 */
	CpUserVO getUser(String userId) throws BusinessException;
	/**
	 * 对用户密码信息的修改，有用户提供者进行实现。注意，此处的UserVO中存在
	 * 用户的密码信息，该vo或者来自于authenticateUser()方法返回的信息（由个人修改个人密码的情况），
	 * 或者来自于getUsers(int pageSize,int pageNumber,String userId)（由管理员统一修改用户密码的情况）
	 * 由提供者确保密码比对及密码修改时是否加密性。 需要将修改完成后的UserVO返回。
	 * 
	 * @param user
	 *            被修改用户信息
	 * @param inputOldPwd
	 *            用户输入的原密码信息（非密文）
	 * @param inputNewPwd
	 *            用户输入的新密码信息（非密文）
	 * @return
	 */
	CpUserVO changeUserPwd(CpUserVO user, String inputOldPwd, String inputNewPwd) throws BusinessException;
}
