package nc.uap.wfm.itf;

import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.vo.WfmQueryconditionVO;

/**
 * 条件查询接口
 * @author zhangxya
 *
 */
public interface IWfmConditonBill {
	
	public void addContidions(WfmQueryconditionVO[] conditions) throws WfmServiceException;
	
	public void updateConditons(WfmQueryconditionVO[] conditions) throws WfmServiceException;

}
