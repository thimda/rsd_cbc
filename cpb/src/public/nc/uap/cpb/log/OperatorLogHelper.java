package nc.uap.cpb.log;

import nc.uap.cpb.log.operatorlog.OperatorLogVO;
import nc.uap.lfw.core.exception.LfwBusinessException;
/**
 * 操作日志接口
 * @author maokun
 *
 */
public class OperatorLogHelper {
	/**
	 * 获取所有操作日志
	 * @return  返回操作日志数组
	 * @throws LfwBusinessException
	 */
	public static OperatorLogVO[] getAllLogs() throws LfwBusinessException {
		return (OperatorLogVO[]) LogHelper.getAllLogs(OperatorLogVO.class);
	}
	/**
	 * 按照主键查找
	 * @param pk	主键
	 * @return		返回操作日志VO  如查找失败返回NULL
	 * @throws LfwBusinessException
	 */
	public static OperatorLogVO getLogByPK(String pk) throws LfwBusinessException {
		return (OperatorLogVO) LogHelper.getLogByPK(OperatorLogVO.class, pk);
	}
	/**
	 * 按照where 条件查找	select * from table where ……
	 * @param strWhere	like "detail like '%111%'"
	 * @return		返回查找结果VO数组
	 * @throws LfwBusinessException
	 */
	public static OperatorLogVO[] getLogsByCondition(String strWhere)
			throws LfwBusinessException {
		return (OperatorLogVO[]) LogHelper.getLogsByCondition(OperatorLogVO.class,
				strWhere);
	}
	/**
	 * 按照where条件和字段查找	select (field1,field2) from table where detail like '%111%'
	 * @param strWhere 		where语句	like "detail like '%111%'"
	 * @param fields	查询字段数组		like(field1,field2)
	 * @return		返回查找结果VO数组
	 * @throws LfwBusinessException
	 */
	public static OperatorLogVO[] getLogsByCondition(String strWhere,
			String[] fields) throws LfwBusinessException {
		return (OperatorLogVO[]) LogHelper.getLogsByCondition(OperatorLogVO.class,
				strWhere, fields);
	}
	/**
	 * 插入  主键非空则插入原有主键
	 * @param vo	要插入的日志VO
	 * @return		成功则返回主键
	 * @throws LfwBusinessException
	 */
	public static String insertLogVOWithPK(OperatorLogVO vo)
			throws LfwBusinessException {
		return LogHelper.insertLogVOWithPK(vo);
	}
	/**
	 * 插入多个  主键非空则插入原有主键
	 * @param vos	要插入的日志VO数组
	 * @return		成功则返回主键数组
	 * @throws LfwBusinessException
	 */
	public static String[] insertLogVOWithPKs(OperatorLogVO[] vos)
			throws LfwBusinessException {
		return LogHelper.insertLogVOWithPKs(vos);
	}
	/**
	 * 插入	无主键
	 * @param vo	待插入的日志VO
	 * @return		成功则返回主键
	 * @throws LfwBusinessException
	 */
	public static String insertLogVO(OperatorLogVO vo) throws LfwBusinessException {
		return LogHelper.insertLogVO(vo);
	}
	/**
	 * 插入多个	无主键
	 * @param vos	待插入的日志VO数组
	 * @return		成功则返回主键数组
	 * @throws LfwBusinessException
	 */
	public static String[] insertLogVOs(OperatorLogVO[] vos)
			throws LfwBusinessException {
		return LogHelper.insertLogVOs(vos);
	}
	/**
	 * 插入操作日志  (记录操作日志时调用 无主键)
	 * @param vo	待插入的日志VO
	 * @return		成功则返回主键
	 * @throws LfwBusinessException
	 */
	public static String doOperator(OperatorLogVO vo)throws LfwBusinessException {
		return LogHelper.insertLogVO(vo);
	}
}
