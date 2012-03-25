package nc.uap.cpb.log;

import nc.bs.framework.common.NCLocator;
import nc.uap.lfw.core.exception.LfwBusinessException;
import nc.vo.pub.SuperVO;

public class LogHelper {
	/**
	 * 获取所有的日志
	 * @param voClass
	 * @return
	 * @throws LfwBusinessException
	 */
	public static SuperVO[] getAllLogs(Class voClass)
			throws LfwBusinessException {
		return getICpLogQuery().getAllLogs(voClass);
	}
	/**
	 * 根据主键搜索
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
	 * 根据where条件搜索
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
	 * 按条件和字段查询
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
	 *  插入  主键非空则插入原有主键
	 * @param vo
	 * @return
	 * @throws LfwBusinessException
	 */
	public static String insertLogVOWithPK(SuperVO vo)
			throws LfwBusinessException {
		return getICpLogQuery().insertLogVOWithPK(vo);
	}
	/**
	 * 插入多个  主键非空则插入原有主键
	 * @param vos
	 * @return
	 * @throws LfwBusinessException
	 */
	public static String[] insertLogVOWithPKs(SuperVO[] vos)
			throws LfwBusinessException {
		return getICpLogQuery().insertLogVOWithPKs(vos);
	}
	/**
	 * 无主键插入
	 * @param vo
	 * @return
	 * @throws LfwBusinessException
	 */
	public static String insertLogVO(SuperVO vo) throws LfwBusinessException {
		return getICpLogQuery().insertLogVO(vo);
	}
	/**
	 * 无主键插入多个
	 * @param vos
	 * @return
	 * @throws LfwBusinessException
	 */
	public static String[] insertLogVOs(SuperVO[] vos) throws LfwBusinessException {
		return getICpLogQuery().insertLogVOs(vos);
	}
	/**
	 * 根据vo对象更新数据库
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
