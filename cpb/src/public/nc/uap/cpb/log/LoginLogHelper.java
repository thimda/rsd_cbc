package nc.uap.cpb.log;

import nc.uap.cpb.log.loginlog.LoginLogVO;
import nc.uap.lfw.core.exception.LfwBusinessException;
import nc.vo.pub.lang.UFDateTime;
/**
 * 登录日志接口
 * @author maokun
 *
 */
public class LoginLogHelper {
	/**
	 * 获取所有登陆日志
	 * @return 返回登录日志数组
	 * @throws LfwBusinessException
	 */
	public static LoginLogVO[] getAllLogs() throws LfwBusinessException {
		return (LoginLogVO[]) LogHelper.getAllLogs(LoginLogVO.class);
	}
	/**
	 * 按照主键查询
	 * @param pk  主键
	 * @return	返回登录日志VO  如查找失败返回NULL
	 * @throws LfwBusinessException
	 */
	public static LoginLogVO getLogByPK(String pk) throws LfwBusinessException {
		return (LoginLogVO) LogHelper.getLogByPK(LoginLogVO.class, pk);
	}
	/**
	 * 按照where条件查询  select * from table where ……
	 * @param strWhere    like "detail like '%111%'"
	 * @return 		返回查找结果VO数组
	 * @throws LfwBusinessException
	 */
	public static LoginLogVO[] getLogsByCondition(String strWhere)
			throws LfwBusinessException {
		return (LoginLogVO[]) LogHelper.getLogsByCondition(LoginLogVO.class,
				strWhere);
	}
	/**
	 * 按照where条件和字段查询   select (field1,field2) from table where detail like '%111%'
	 * @param strWhere	where语句  like "detail like '%111%'"
	 * @param fields	查询字段数组  like (field1,field2)
	 * @return		返回查找结果VO数组
	 * @throws LfwBusinessException
	 */
	public static LoginLogVO[] getLogsByCondition(String strWhere,
			String[] fields) throws LfwBusinessException {
		return (LoginLogVO[]) LogHelper.getLogsByCondition(LoginLogVO.class,
				strWhere, fields);
	}
	/**
	 *  插入  主键非空则插入原有主键
	 * @param vo   要插入的日志VO
	 * @return		插入成功则返回主键
	 * @throws LfwBusinessException
	 */
	public static String insertLogVOWithPK(LoginLogVO vo)
			throws LfwBusinessException {
		return LogHelper.insertLogVOWithPK(vo);
	}
	/**
	 *  插入多个  主键非空则插入原有主键
	 * @param vos  要插入的日志VO数组
	 * @return		插入成功则返回主键数组
	 * @throws LfwBusinessException
	 */
	public static String[] insertLogVOWithPKs(LoginLogVO[] vos)
			throws LfwBusinessException {
		return LogHelper.insertLogVOWithPKs(vos);
	}
	/**
	 * 插入   无主键
	 * @param vo 	要插入的日志VO
	 * @return		插入成功则返回主键
	 * @throws LfwBusinessException
	 */
	public static String insertLogVO(LoginLogVO vo) throws LfwBusinessException {
		return LogHelper.insertLogVO(vo);
	}
	/**
	 * 插入多个  无主键
	 * @param vos	要插入的日志VO数组
	 * @return		插入成功则返回主键数组
	 * @throws LfwBusinessException
	 */
	public static String[] insertLogVOs(LoginLogVO[] vos)
			throws LfwBusinessException {
		return LogHelper.insertLogVOs(vos);
	}
	/**
	 * 插入登陆信息   (登录时调用  无主键)
	 * @param vo    要插入的登陆日志VO
	 * @return		成功则返回主键
	 * @throws LfwBusinessException
	 */
	public static String login(LoginLogVO vo) throws LfwBusinessException {
		return LogHelper.insertLogVO(vo);
	}
	/**
	 * 更新登出信息  (登出时调用)
	 * @param sessionid		当前sessionid
	 * @param logouttime	登出时间
	 * @return		如操作成功则返回正数  否则返回-1
	 * @throws LfwBusinessException
	 */
	public static int logout(String sessionid, UFDateTime logouttime)
			throws LfwBusinessException {
		LoginLogVO[] vos = getLogsByCondition("sessionid = '" + sessionid+"'");
		if (vos.length > 0) {
			vos[0].setLogouttime(logouttime);
			return LogHelper.updataLogVO(vos[0]);
		}
		return -1;
	}
}
