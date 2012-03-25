package nc.uap.cpb.log;

import nc.uap.cpb.log.loginlog.LoginLogVO;
import nc.uap.lfw.core.exception.LfwBusinessException;
import nc.vo.pub.lang.UFDateTime;
/**
 * ��¼��־�ӿ�
 * @author maokun
 *
 */
public class LoginLogHelper {
	/**
	 * ��ȡ���е�½��־
	 * @return ���ص�¼��־����
	 * @throws LfwBusinessException
	 */
	public static LoginLogVO[] getAllLogs() throws LfwBusinessException {
		return (LoginLogVO[]) LogHelper.getAllLogs(LoginLogVO.class);
	}
	/**
	 * ����������ѯ
	 * @param pk  ����
	 * @return	���ص�¼��־VO  �����ʧ�ܷ���NULL
	 * @throws LfwBusinessException
	 */
	public static LoginLogVO getLogByPK(String pk) throws LfwBusinessException {
		return (LoginLogVO) LogHelper.getLogByPK(LoginLogVO.class, pk);
	}
	/**
	 * ����where������ѯ  select * from table where ����
	 * @param strWhere    like "detail like '%111%'"
	 * @return 		���ز��ҽ��VO����
	 * @throws LfwBusinessException
	 */
	public static LoginLogVO[] getLogsByCondition(String strWhere)
			throws LfwBusinessException {
		return (LoginLogVO[]) LogHelper.getLogsByCondition(LoginLogVO.class,
				strWhere);
	}
	/**
	 * ����where�������ֶβ�ѯ   select (field1,field2) from table where detail like '%111%'
	 * @param strWhere	where���  like "detail like '%111%'"
	 * @param fields	��ѯ�ֶ�����  like (field1,field2)
	 * @return		���ز��ҽ��VO����
	 * @throws LfwBusinessException
	 */
	public static LoginLogVO[] getLogsByCondition(String strWhere,
			String[] fields) throws LfwBusinessException {
		return (LoginLogVO[]) LogHelper.getLogsByCondition(LoginLogVO.class,
				strWhere, fields);
	}
	/**
	 *  ����  �����ǿ������ԭ������
	 * @param vo   Ҫ�������־VO
	 * @return		����ɹ��򷵻�����
	 * @throws LfwBusinessException
	 */
	public static String insertLogVOWithPK(LoginLogVO vo)
			throws LfwBusinessException {
		return LogHelper.insertLogVOWithPK(vo);
	}
	/**
	 *  ������  �����ǿ������ԭ������
	 * @param vos  Ҫ�������־VO����
	 * @return		����ɹ��򷵻���������
	 * @throws LfwBusinessException
	 */
	public static String[] insertLogVOWithPKs(LoginLogVO[] vos)
			throws LfwBusinessException {
		return LogHelper.insertLogVOWithPKs(vos);
	}
	/**
	 * ����   ������
	 * @param vo 	Ҫ�������־VO
	 * @return		����ɹ��򷵻�����
	 * @throws LfwBusinessException
	 */
	public static String insertLogVO(LoginLogVO vo) throws LfwBusinessException {
		return LogHelper.insertLogVO(vo);
	}
	/**
	 * ������  ������
	 * @param vos	Ҫ�������־VO����
	 * @return		����ɹ��򷵻���������
	 * @throws LfwBusinessException
	 */
	public static String[] insertLogVOs(LoginLogVO[] vos)
			throws LfwBusinessException {
		return LogHelper.insertLogVOs(vos);
	}
	/**
	 * �����½��Ϣ   (��¼ʱ����  ������)
	 * @param vo    Ҫ����ĵ�½��־VO
	 * @return		�ɹ��򷵻�����
	 * @throws LfwBusinessException
	 */
	public static String login(LoginLogVO vo) throws LfwBusinessException {
		return LogHelper.insertLogVO(vo);
	}
	/**
	 * ���µǳ���Ϣ  (�ǳ�ʱ����)
	 * @param sessionid		��ǰsessionid
	 * @param logouttime	�ǳ�ʱ��
	 * @return		������ɹ��򷵻�����  ���򷵻�-1
	 * @throws LfwBusinessException
	 */
	public static int logout(String sessionid, UFDateTime logouttime)
			throws LfwBusinessException {
		LoginLogVO[] vos = getLogsByCondition("sessionid = '" + sessionid+"'");
		if (vos.length > 0) {
			vos[0].setLogouttime(logouttime);
			return LogHelper.updataLogVO(vos[0]);
		}
		return -1;
	}
}
