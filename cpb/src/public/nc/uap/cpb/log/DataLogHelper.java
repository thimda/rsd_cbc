package nc.uap.cpb.log;
import nc.uap.cpb.log.datalog.DataLogVO;
import nc.uap.lfw.core.exception.LfwBusinessException;

/**
 * ҵ����־�ӿ�
 * @author maokun
 *
 */

public class DataLogHelper {
	/**
	 * ��ȡ����ҵ����־
	 * @return ����ҵ����־����
	 * @throws LfwBusinessException
	 */
	public static DataLogVO[] getAllLogs() throws LfwBusinessException {
		return (DataLogVO[]) LogHelper.getAllLogs(DataLogVO.class);
	}
	/**
	 * ����������ѯ
	 * @param pk   ����
	 * @return  ����ҵ����־VO  �����ʧ�ܷ���NULL
	 * @throws LfwBusinessException
	 */
	public static DataLogVO getLogByPK(String pk) throws LfwBusinessException {
		return (DataLogVO) LogHelper.getLogByPK(DataLogVO.class, pk);
	}
	/**
	 * ����where������ѯ   select * from table where ����
	 * @param strWhere   like "detail like '%111%'"
	 * @return   ���ز��ҽ��VO����
	 * @throws LfwBusinessException
	 */
	public static DataLogVO[] getLogsByCondition(String strWhere)
			throws LfwBusinessException {
		return (DataLogVO[]) LogHelper.getLogsByCondition(DataLogVO.class,
				strWhere);
	}
	/**
	 * ����where�������ֶβ�ѯ select (field1,field2) from table where detail like '%111%'
	 * @param strWhere  where ���  like "detail like '%111%'"
	 * @param fields	��ѯ�ֶ�����		like(field1,field2)
	 * @return  ���ز��ҽ��VO����
	 * @throws LfwBusinessException
	 */
	public static DataLogVO[] getLogsByCondition(String strWhere,
			String[] fields) throws LfwBusinessException {
		return (DataLogVO[]) LogHelper.getLogsByCondition(DataLogVO.class,
				strWhere, fields);
	}
	/**
	 * ����  �����ǿ������ԭ������
	 * @param vo    Ҫ�������־VO
	 * @return     ����ɹ��򷵻�����
	 * @throws LfwBusinessException
	 */
	public static String insertLogVOWithPK(DataLogVO vo)
			throws LfwBusinessException {
		return LogHelper.insertLogVOWithPK(vo);
	}
	/**
	 * ������ �����ǿ�
	 * @param vos    Ҫ�������־VO����
	 * @return		����ɹ��򷵻���������
	 * @throws LfwBusinessException
	 */
	public static String[] insertLogVOWithPKs(DataLogVO[] vos)
			throws LfwBusinessException {
		return LogHelper.insertLogVOWithPKs(vos);
	}
	/**
	 * ���� ������
	 * @param vo   Ҫ�������־VO
	 * @return		��������
	 * @throws LfwBusinessException
	 */
	public static String insertLogVO(DataLogVO vo) throws LfwBusinessException {
		return LogHelper.insertLogVO(vo);
	}
	/**
	 * ������  ������
	 * @param vos    Ҫ�������־VO����
	 * @return		������������
	 * @throws LfwBusinessException
	 */
	public static String[] insertLogVOs(DataLogVO[] vos)
			throws LfwBusinessException {
		return LogHelper.insertLogVOs(vos);
	}
	/**
	 * ����ҵ����־  (��¼ҵ����־ʱ����  ������)
	 * @param vo	Ҫ�������־VO
	 * @return		����ɹ��򷵻�����
	 * @throws LfwBusinessException
	 */
	public static String doTask(DataLogVO vo) throws LfwBusinessException {
		return LogHelper.insertLogVO(vo);
	}
}
