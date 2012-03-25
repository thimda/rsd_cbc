package nc.uap.cpb.log;
import nc.uap.cpb.log.datalog.DataLogVO;
import nc.uap.lfw.core.exception.LfwBusinessException;

/**
 * 业务日志接口
 * @author maokun
 *
 */

public class DataLogHelper {
	/**
	 * 获取所有业务日志
	 * @return 返回业务日志数组
	 * @throws LfwBusinessException
	 */
	public static DataLogVO[] getAllLogs() throws LfwBusinessException {
		return (DataLogVO[]) LogHelper.getAllLogs(DataLogVO.class);
	}
	/**
	 * 按照主键查询
	 * @param pk   主键
	 * @return  返回业务日志VO  如查找失败返回NULL
	 * @throws LfwBusinessException
	 */
	public static DataLogVO getLogByPK(String pk) throws LfwBusinessException {
		return (DataLogVO) LogHelper.getLogByPK(DataLogVO.class, pk);
	}
	/**
	 * 按照where条件查询   select * from table where ……
	 * @param strWhere   like "detail like '%111%'"
	 * @return   返回查找结果VO数组
	 * @throws LfwBusinessException
	 */
	public static DataLogVO[] getLogsByCondition(String strWhere)
			throws LfwBusinessException {
		return (DataLogVO[]) LogHelper.getLogsByCondition(DataLogVO.class,
				strWhere);
	}
	/**
	 * 按照where条件和字段查询 select (field1,field2) from table where detail like '%111%'
	 * @param strWhere  where 语句  like "detail like '%111%'"
	 * @param fields	查询字段数组		like(field1,field2)
	 * @return  返回查找结果VO数组
	 * @throws LfwBusinessException
	 */
	public static DataLogVO[] getLogsByCondition(String strWhere,
			String[] fields) throws LfwBusinessException {
		return (DataLogVO[]) LogHelper.getLogsByCondition(DataLogVO.class,
				strWhere, fields);
	}
	/**
	 * 插入  主键非空则插入原有主键
	 * @param vo    要插入的日志VO
	 * @return     插入成功则返回主键
	 * @throws LfwBusinessException
	 */
	public static String insertLogVOWithPK(DataLogVO vo)
			throws LfwBusinessException {
		return LogHelper.insertLogVOWithPK(vo);
	}
	/**
	 * 插入多个 主键非空
	 * @param vos    要插入的日志VO数组
	 * @return		插入成功则返回主键数组
	 * @throws LfwBusinessException
	 */
	public static String[] insertLogVOWithPKs(DataLogVO[] vos)
			throws LfwBusinessException {
		return LogHelper.insertLogVOWithPKs(vos);
	}
	/**
	 * 插入 无主键
	 * @param vo   要插入的日志VO
	 * @return		返回主键
	 * @throws LfwBusinessException
	 */
	public static String insertLogVO(DataLogVO vo) throws LfwBusinessException {
		return LogHelper.insertLogVO(vo);
	}
	/**
	 * 插入多个  无主键
	 * @param vos    要插入的日志VO数组
	 * @return		返回主键数组
	 * @throws LfwBusinessException
	 */
	public static String[] insertLogVOs(DataLogVO[] vos)
			throws LfwBusinessException {
		return LogHelper.insertLogVOs(vos);
	}
	/**
	 * 插入业务日志  (记录业务日志时调用  无主键)
	 * @param vo	要插入的日志VO
	 * @return		插入成功则返回主键
	 * @throws LfwBusinessException
	 */
	public static String doTask(DataLogVO vo) throws LfwBusinessException {
		return LogHelper.insertLogVO(vo);
	}
}
