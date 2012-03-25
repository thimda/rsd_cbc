package nc.uap.portal.ctrl.office.data.sign.signlog;

import nc.bs.framework.common.NCLocator;
import nc.uap.lfw.core.exception.LfwBusinessException;
import nc.uap.portal.ctrl.office.data.sign.IsignLogQuery;
import nc.uap.portal.ctrl.office.data.sign.SignlogVO;

public class SignLogHelper {
	/**
	 * 获取所有的日志
	 * @return
	 * @throws LfwBusinessException
	 */
	public static SignlogVO[] getAllSignLogs() throws LfwBusinessException {
		return getIsignLogQuery().getAllSignLogs();
	}
	/**
	 * 根据主键搜索
	 * @param pk
	 * @return
	 * @throws LfwBusinessException
	 */
	public static SignlogVO getSignLogByPK(String pk)
			throws LfwBusinessException {
		return getIsignLogQuery().getSignLogByPK(pk);
	}
	/**
	 * 根据where条件搜索
	 * @param strWhere
	 * @return
	 * @throws LfwBusinessException
	 */
	public static SignlogVO[] getSignLogsByCondition(String strWhere)
			throws LfwBusinessException {
		return getIsignLogQuery().getSignLogsByCondition(strWhere);
	}
	/**
	 * 按条件和字段查询
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
	 * 插入  主键非空则插入原有主键
	 * @param vo
	 * @return
	 * @throws LfwBusinessException
	 */
	public static String insertSignLogVOWithPK(SignlogVO vo)
			throws LfwBusinessException {
		return getIsignLogQuery().insertSignLogVOWithPK(vo);
	}
	/**
	 *  插入多个  主键非空则插入原有主键
	 * @param vos
	 * @return
	 * @throws LfwBusinessException
	 */
	public static String[] insertSignLogVOWithPKs(SignlogVO[] vos)
			throws LfwBusinessException {
		return getIsignLogQuery().insertSignLogVOWithPKs(vos);
	}
	/**
	 * 插入
	 * @param vo
	 * @return
	 * @throws LfwBusinessException
	 */
	public static String insertSignLogVO(SignlogVO vo)
			throws LfwBusinessException {
		return getIsignLogQuery().insertSignLogVO(vo);
	}
	/**
	 * 插入多个
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
