package nc.uap.cpb.log;

import nc.uap.lfw.core.exception.LfwBusinessException;
import nc.vo.pub.SuperVO;

public interface ICpLogQuery {
	/**
	 * 获取所有的日志
	 * @return
	 * @throws LfwBusinessException
	 */
	public SuperVO[] getAllLogs(Class voClass) throws LfwBusinessException;
	/**
	 * 根据主键搜索
	 * @return
	 * @throws LfwBusinessException
	 */
	public SuperVO getLogByPK(Class voClass,String pk) throws LfwBusinessException;
	/**
	 * 根据where条件搜索
	 * @param strWhere
	 * @return
	 * @throws LfwBusinessException
	 */
	public SuperVO[] getLogsByCondition(Class voClass,String strWhere)
			throws LfwBusinessException;
	/**
	 * 按条件和字段查询
	 * @param strWhere
	 * @param fields
	 * @return
	 * @throws LfwBusinessException
	 */
	public SuperVO[] getLogsByCondition(Class voClass,String strWhere, String[] fields)
			throws LfwBusinessException;
	/**
	 * 插入  主键非空则插入原有主键
	 * @param vo
	 * @return
	 * @throws LfwBusinessException
	 */
	public String insertLogVOWithPK(SuperVO vo) throws LfwBusinessException;
	/**
	 * 插入多个  主键非空则插入原有主键
	 * @param vos
	 * @return
	 * @throws LfwBusinessException
	 */
	public String[] insertLogVOWithPKs(SuperVO[] vos)
			throws LfwBusinessException;
	/**
	 * 插入
	 * @param vo
	 * @return
	 * @throws LfwBusinessException
	 */
	public String insertLogVO(SuperVO vo) throws LfwBusinessException;
	/**
	 * 插入多个
	 * @param vos
	 * @return
	 * @throws LfwBusinessException
	 */
	public String[] insertLogVOs(SuperVO[] vos) throws LfwBusinessException;
	/**
	 * 根据vo对象更新数据库
	 * @param vo
	 * @return
	 * @throws LfwBusinessException
	 */
	public int updataVO(SuperVO vo) throws LfwBusinessException;
}
