package nc.uap.portal.ctrl.office.data.sign;

import nc.uap.lfw.core.exception.LfwBusinessException;

public interface IsignLogQuery {
	/**
	 * 获取所有的日志
	 * @return
	 * @throws LfwBusinessException
	 */
	public SignlogVO[] getAllSignLogs() throws LfwBusinessException;
	/**
	 * 根据主键搜索
	 * @return
	 * @throws LfwBusinessException
	 */
	public SignlogVO getSignLogByPK(String pk) throws LfwBusinessException;
	/**
	 * 根据where条件搜索
	 * @param strWhere
	 * @return
	 * @throws LfwBusinessException
	 */
	public SignlogVO[] getSignLogsByCondition(String strWhere)
			throws LfwBusinessException;
	/**
	 * 按条件和字段查询
	 * @param strWhere
	 * @param fields
	 * @return
	 * @throws LfwBusinessException
	 */
	public SignlogVO[] getSignLogsByCondition(String strWhere, String[] fields)
			throws LfwBusinessException;
	/**
	 * 插入  主键非空则插入原有主键
	 * @param vo
	 * @return
	 * @throws LfwBusinessException
	 */
	public String insertSignLogVOWithPK(SignlogVO vo) throws LfwBusinessException;
	/**
	 * 插入多个  主键非空则插入原有主键
	 * @param vos
	 * @return
	 * @throws LfwBusinessException
	 */
	public String[] insertSignLogVOWithPKs(SignlogVO[] vos)
			throws LfwBusinessException;
	/**
	 * 插入
	 * @param vo
	 * @return
	 * @throws LfwBusinessException
	 */
	public String insertSignLogVO(SignlogVO vo) throws LfwBusinessException;
	/**
	 * 插入多个
	 * @param vos
	 * @return
	 * @throws LfwBusinessException
	 */
	public String[] insertSignLogVOs(SignlogVO[] vos) throws LfwBusinessException;
}
