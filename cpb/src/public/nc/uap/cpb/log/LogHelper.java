package nc.uap.cpb.log;

import nc.bs.framework.common.NCLocator;
import nc.uap.lfw.core.exception.LfwBusinessException;
import nc.vo.pub.SuperVO;

public class LogHelper {
	/**
	 * ��ȡ���е���־
	 * @param voClass
	 * @return
	 * @throws LfwBusinessException
	 */
	public static SuperVO[] getAllLogs(Class voClass)
			throws LfwBusinessException {
		return getICpLogQuery().getAllLogs(voClass);
	}
	/**
	 * ������������
	 * @param voClass
	 * @param pk
	 * @return
	 * @throws LfwBusinessException
	 */
	public static SuperVO getLogByPK(Class voClass, String pk)
			throws LfwBusinessException {
		return getICpLogQuery().getLogByPK(voClass, pk);
	}
	/**
	 * ����where��������
	 * @param voClass
	 * @param strWhere
	 * @return
	 * @throws LfwBusinessException
	 */
	public static SuperVO[] getLogsByCondition(Class voClass, String strWhere)
			throws LfwBusinessException {
		return getICpLogQuery().getLogsByCondition(voClass, strWhere);
	}
	/**
	 * ���������ֶβ�ѯ
	 * @param voClass
	 * @param strWhere
	 * @param fields
	 * @return
	 * @throws LfwBusinessException
	 */
	public static SuperVO[] getLogsByCondition(Class voClass, String strWhere,
			String[] fields) throws LfwBusinessException {
		return getICpLogQuery().getLogsByCondition(voClass, strWhere, fields);
	}
	/**
	 *  ����  �����ǿ������ԭ������
	 * @param vo
	 * @return
	 * @throws LfwBusinessException
	 */
	public static String insertLogVOWithPK(SuperVO vo)
			throws LfwBusinessException {
		return getICpLogQuery().insertLogVOWithPK(vo);
	}
	/**
	 * ������  �����ǿ������ԭ������
	 * @param vos
	 * @return
	 * @throws LfwBusinessException
	 */
	public static String[] insertLogVOWithPKs(SuperVO[] vos)
			throws LfwBusinessException {
		return getICpLogQuery().insertLogVOWithPKs(vos);
	}
	/**
	 * ����������
	 * @param vo
	 * @return
	 * @throws LfwBusinessException
	 */
	public static String insertLogVO(SuperVO vo) throws LfwBusinessException {
		return getICpLogQuery().insertLogVO(vo);
	}
	/**
	 * ������������
	 * @param vos
	 * @return
	 * @throws LfwBusinessException
	 */
	public static String[] insertLogVOs(SuperVO[] vos) throws LfwBusinessException {
		return getICpLogQuery().insertLogVOs(vos);
	}
	/**
	 * ����vo����������ݿ�
	 * @param vo
	 * @return
	 * @throws LfwBusinessException
	 */
	public static int updataLogVO(SuperVO vo) throws LfwBusinessException {
		return getICpLogQuery().updataVO(vo);
	}

	private static ICpLogQuery getICpLogQuery() {
		return NCLocator.getInstance().lookup(ICpLogQuery.class);
	}
}