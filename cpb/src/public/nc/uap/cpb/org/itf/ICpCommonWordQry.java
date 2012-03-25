package nc.uap.cpb.org.itf;
import nc.uap.cpb.org.vos.CpCommonWordVO;
/**
 * 常用语查询接口。
 * 
 * @author ybo
 * @version 2011-3-17
 * @since NCPortal6.0
 */
public interface ICpCommonWordQry {
	/**
	 * 取得用户可用的常用语
	 * 
	 * @param userPk
	 *            用户主键
	 * @return 常用语VO数组
	 * @throws PwfmBusinessException
	 */
	CpCommonWordVO[] getUserCommonWord(String userPk);
	/**
	 * 取得指定范围内指定对象的常用语
	 * 
	 * @param pkObj
	 *            常用语所属范围对象主键
	 * @param scope
	 *            范围
	 * @return 常用语VO
	 * @throws PwfmBusinessException
	 */
	CpCommonWordVO[] getCommonWordByScopeObj(String pkObj, int scope);
}
