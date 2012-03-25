package nc.uap.wfm.itf;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.vo.WfmCommonWordVO;


/**
 * 常用语查询接口。
 * @author ybo
 * @version 2011-3-17
 * @since NCPortal6.0
 */
public interface IWfmCommonWordQry {
	
	/**
	 * 取得用户可用的常用语
	 * @param userPk 用户主键
	 * @return 常用语VO数组
	 * @throws WfmServiceException
	 */
	WfmCommonWordVO[] getUserCommonWord(String userPk) throws WfmServiceException;
	
	/**
	 * 取得指定范围内指定对象的常用语
	 * @param pkObj 常用语所属范围对象主键
	 * @param scope 范围
	 * @return 常用语VO
	 * @throws WfmServiceException
	 */
	WfmCommonWordVO[] getCommonWordByScopeObj(String pkObj,int scope) throws WfmServiceException;
}
