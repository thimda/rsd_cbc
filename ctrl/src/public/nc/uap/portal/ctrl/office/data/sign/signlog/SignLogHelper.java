package nc.uap.portal.ctrl.office.data.sign.signlog;

import nc.bs.framework.common.NCLocator;
import nc.uap.lfw.core.exception.LfwBusinessException;
import nc.uap.portal.ctrl.office.data.sign.IsignLogQuery;
import nc.uap.portal.ctrl.office.data.sign.SignlogVO;

public class SignLogHelper {
	/**
	 * ��ȡ���е���־
	 * @return
	 * @throws LfwBusinessException
	 */
	public static SignlogVO[] getAllSignLogs() throws LfwBusinessException {
		return getIsignLogQuery().getAllSignLogs();
	}
	/**
	 * ������������
	 * @param pk
	 * @return
	 * @throws LfwBusinessException
	 */
	public static SignlogVO getSignLogByPK(String pk)
			throws LfwBusinessException {
		return getIsignLogQuery().getSignLogByPK(pk);
	}
	/**
	 * ����where��������
	 * @param strWhere
	 * @return
	 * @throws LfwBusinessException
	 */
	public static SignlogVO[] getSignLogsByCondition(String strWhere)
			throws LfwBusinessException {
		return getIsignLogQuery().getSignLogsByCondition(strWhere);
	}
	/**
	 * ���������ֶβ�ѯ
	 * @param strWhere
	 * @param fields
	 * @return
	 * @throws LfwBusinessException
	 */
	public static SignlogVO[] getSignLogsByCondition(String strWhere,
			String[] fields) throws LfwBusinessException {
		return getIsignLogQuery().getSignLogsByCondition(strWhere, fields);
	}
	/**
	 * ����  �����ǿ������ԭ������
	 * @param vo
	 * @return
	 * @throws LfwBusinessException
	 */
	public static String insertSignLogVOWithPK(SignlogVO vo)
			throws LfwBusinessException {
		return getIsignLogQuery().insertSignLogVOWithPK(vo);
	}
	/**
	 *  ������  �����ǿ������ԭ������
	 * @param vos
	 * @return
	 * @throws LfwBusinessException
	 */
	public static String[] insertSignLogVOWithPKs(SignlogVO[] vos)
			throws LfwBusinessException {
		return getIsignLogQuery().insertSignLogVOWithPKs(vos);
	}
	/**
	 * ����
	 * @param vo
	 * @return
	 * @throws LfwBusinessException
	 */
	public static String insertSignLogVO(SignlogVO vo)
			throws LfwBusinessException {
		return getIsignLogQuery().insertSignLogVO(vo);
	}
	/**
	 * ������
	 * @param vos
	 * @return
	 * @throws LfwBusinessException
	 */
	public static String[] insertSignLogVOs(SignlogVO[] vos)
			throws LfwBusinessException {
		return getIsignLogQuery().insertSignLogVOs(vos);
	}
	
	private static IsignLogQuery getIsignLogQuery() {
		return NCLocator.getInstance().lookup(IsignLogQuery.class);
	}
}
