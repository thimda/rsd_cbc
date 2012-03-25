package nc.uap.cpb.org.itf;
import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.vos.CpUserVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFDate;
/**
 * �û���������ӿ�
 * 
 * @author zhangxya
 * 
 */
public interface ICpUserBill {
	/**
	 * �����û���Ϣ
	 * 
	 * @param uservo
	 * @throws PortalServiceException
	 */
	public String addCpUserVO(CpUserVO uservo) throws CpbBusinessException;
	 
	public String addCpUserVOWithPk(CpUserVO uservo) throws CpbBusinessException;
	
	public void updateCpUserVO(CpUserVO userVo) throws CpbBusinessException;
	/**
	 * �����û���������vo
	 * 
	 * @param deCpUserVO
	 * @return
	 * @throws PortalServiceException
	 */
	// public String addCpUserDeptVO(PtDeptUserVO deCpUserVO) throws
	// CpbBusinessException;
	/**
	 * ���������Ź����û���Ϣ
	 * 
	 * @param deCpUserVO
	 * @return
	 * @throws PortalServiceException
	 */
	// public void addCpUserDeptVOS(PtDeptUserVO[] deCpUserVO) throws
	// CpbBusinessException;
	/**
	 * ��������û�������˾����Ϣ
	 * 
	 * @param corpUserVOs
	 * @throws PortalServiceException
	 */
	// public void addCpUserCorpVOS(PtCorpUserVO[] corpUserVOs) throws
	// CpbBusinessException;
	/**
	 * �����û���������
	 * 
	 * @param uservo
	 * @param languageId
	 * @throws PortalServiceException
	 */
	public void changeUserLanguage(String pk_user, String languageId) throws CpbBusinessException;
	/**
	 * �����û�����
	 * 
	 * @param pk_user
	 * @param themeId
	 * @throws PortalServiceException
	 */
	public void changeUserTheme(String pk_user, String themeId) throws CpbBusinessException;
	/**
	 * ɾ���û���Ϣ
	 * 
	 * @param pk_user
	 * @throws PortalServiceException
	 */
	public void deleteCpUserVO(String pk_user) throws CpbBusinessException;
	/**
	 * ɾ���û���Ϣ
	 * 
	 * @param uservo
	 * @throws PortalServiceException
	 */
	public void deleteCpUserVO(CpUserVO uservo) throws CpbBusinessException;
	/**
	 * ɾ���û���Ϣ
	 * 
	 * @param uservos
	 * @throws PortalServiceException
	 */
	public void deleteCpUserVOs(CpUserVO[] uservos) throws CpbBusinessException;
	/**
	 * �޸�portal�û����� * @param userId
	 * 
	 * @param newPwd
	 * @throws PortalServiceException
	 */
	public void updateUserPwd(String pk_user, String newPwd, UFDate passwordmodifydate) throws CpbBusinessException;
	/**
	 * У�����밲ȫ����
	 * @param cpUserVO
	 * @return
	 * @throws BusinessException
	 */
	public boolean checkPwdLevel(CpUserVO cpUserVO)throws BusinessException;
	/**
	 * �û�����portal��֤����֤�����portalϵͳ�е�UserVO����
	 * 
	 * @param userName
	 *            �û���
	 * @param password
	 *            ����
	 * @return portalϵͳ��UserVOʵ��
	 * @throws UserNotFoundException
	 *             �û�������
	 * @throws UserAccessException
	 *             �����ȷ���߲������¼
	 * @throws BusinessException
	 *             ����������Ϣ
	 */
	// CpUserVO authenticateUser(String userName, String password, Object
	// userObject)
	// throws UserNotFoundException, UserAccessException,
	// BusinessException;
	/**
	 * ��÷���������ָ��λ�õ��û���¼��Ϊportal����ʹ�ã����������ͷ�ҳ��ʾ�� Լ��:��pageSize<=0ʱ�����ط��������������û���¼��Ϣ��
	 * 
	 * @param pageSize
	 * @param pageNumber
	 * @param userId
	 *            ��Ӧ��Portal�û���userid�ֶε�ģ��ֵ��������Provider�����Լ������ӳ���ϵ�����Լ���Sql�����в�ѯ��
	 * @return
	 * @throws BusinessException
	 */
	// List<CpUserVO> getUsers(int pageSize,int pageNumber,String userId) throws
	// BusinessException;
	/**
	 * ͨ���˷�����ȡ�����������û���������
	 * 
	 * @param userId
	 *            ��Ӧ��Portal�û���userID��ģ��ֵ��
	 * @return
	 * @throws BusinessException
	 */
	// int getCountofUsers(String userId) throws BusinessException;
	/**
	 * �����û���Ϣ�����û����ṩ��ѡ����Щ�û���Ϣ���ӳ������ڲ����û���Ϣ�����и��¡�
	 * ����ӳ��������ڸ����authenticateUser�������ɵ������ṩ���Լ�����ġ��û�����
	 * ѡ�񲻸������ݿ���Ϣ��������ҳ��ά��ҳ���п���ֱ�ӽ�ͨ��<code>ISecurityQueryService</code>
	 * ��getUserById1(userId)������õ�UserVO��Ϊ��֤�����ķ���ֵ��
	 * 
	 * ���ڵ��������û��ṩ����ʱ���ṩ���û�����ӹ��ܡ�
	 * 
	 * @param user
	 * @throws BusinessException
	 */
	CpUserVO getUser(String userId) throws BusinessException;
	/**
	 * ���û�������Ϣ���޸ģ����û��ṩ�߽���ʵ�֡�ע�⣬�˴���UserVO�д���
	 * �û���������Ϣ����vo����������authenticateUser()�������ص���Ϣ���ɸ����޸ĸ���������������
	 * ����������getUsers(int pageSize,int pageNumber,String userId)���ɹ���Աͳһ�޸��û�����������
	 * ���ṩ��ȷ������ȶԼ������޸�ʱ�Ƿ�����ԡ� ��Ҫ���޸���ɺ��UserVO���ء�
	 * 
	 * @param user
	 *            ���޸��û���Ϣ
	 * @param inputOldPwd
	 *            �û������ԭ������Ϣ�������ģ�
	 * @param inputNewPwd
	 *            �û��������������Ϣ�������ģ�
	 * @return
	 */
	CpUserVO changeUserPwd(CpUserVO user, String inputOldPwd, String inputNewPwd) throws BusinessException;
}
