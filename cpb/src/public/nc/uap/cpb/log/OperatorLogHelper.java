package nc.uap.cpb.log;

import nc.uap.cpb.log.operatorlog.OperatorLogVO;
import nc.uap.lfw.core.exception.LfwBusinessException;
/**
 * ������־�ӿ�
 * @author maokun
 *
 */
public class OperatorLogHelper {
	/**
	 * ��ȡ���в�����־
	 * @return  ���ز�����־����
	 * @throws LfwBusinessException
	 */
	public static OperatorLogVO[] getAllLogs() throws LfwBusinessException {
		return (OperatorLogVO[]) LogHelper.getAllLogs(OperatorLogVO.class);
	}
	/**
	 * ������������
	 * @param pk	����
	 * @return		���ز�����־VO  �����ʧ�ܷ���NULL
	 * @throws LfwBusinessException
	 */
	public static OperatorLogVO getLogByPK(String pk) throws LfwBusinessException {
		return (OperatorLogVO) LogHelper.getLogByPK(OperatorLogVO.class, pk);
	}
	/**
	 * ����where ��������	select * from table where ����
	 * @param strWhere	like "detail like '%111%'"
	 * @return		���ز��ҽ��VO����
	 * @throws LfwBusinessException
	 */
	public static OperatorLogVO[] getLogsByCondition(String strWhere)
			throws LfwBusinessException {
		return (OperatorLogVO[]) LogHelper.getLogsByCondition(OperatorLogVO.class,
				strWhere);
	}
	/**
	 * ����where�������ֶβ���	select (field1,field2) from table where detail like '%111%'
	 * @param strWhere 		where���	like "detail like '%111%'"
	 * @param fields	��ѯ�ֶ�����		like(field1,field2)
	 * @return		���ز��ҽ��VO����
	 * @throws LfwBusinessException
	 */
	public static OperatorLogVO[] getLogsByCondition(String strWhere,
			String[] fields) throws LfwBusinessException {
		return (OperatorLogVO[]) LogHelper.getLogsByCondition(OperatorLogVO.class,
				strWhere, fields);
	}
	/**
	 * ����  �����ǿ������ԭ������
	 * @param vo	Ҫ�������־VO
	 * @return		�ɹ��򷵻�����
	 * @throws LfwBusinessException
	 */
	public static String insertLogVOWithPK(OperatorLogVO vo)
			throws LfwBusinessException {
		return LogHelper.insertLogVOWithPK(vo);
	}
	/**
	 * ������  �����ǿ������ԭ������
	 * @param vos	Ҫ�������־VO����
	 * @return		�ɹ��򷵻���������
	 * @throws LfwBusinessException
	 */
	public static String[] insertLogVOWithPKs(OperatorLogVO[] vos)
			throws LfwBusinessException {
		return LogHelper.insertLogVOWithPKs(vos);
	}
	/**
	 * ����	������
	 * @param vo	���������־VO
	 * @return		�ɹ��򷵻�����
	 * @throws LfwBusinessException
	 */
	public static String insertLogVO(OperatorLogVO vo) throws LfwBusinessException {
		return LogHelper.insertLogVO(vo);
	}
	/**
	 * ������	������
	 * @param vos	���������־VO����
	 * @return		�ɹ��򷵻���������
	 * @throws LfwBusinessException
	 */
	public static String[] insertLogVOs(OperatorLogVO[] vos)
			throws LfwBusinessException {
		return LogHelper.insertLogVOs(vos);
	}
	/**
	 * ���������־  (��¼������־ʱ���� ������)
	 * @param vo	���������־VO
	 * @return		�ɹ��򷵻�����
	 * @throws LfwBusinessException
	 */
	public static String doOperator(OperatorLogVO vo)throws LfwBusinessException {
		return LogHelper.insertLogVO(vo);
	}
}
