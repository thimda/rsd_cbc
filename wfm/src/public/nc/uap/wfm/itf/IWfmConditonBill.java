package nc.uap.wfm.itf;

import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.vo.WfmQueryconditionVO;

/**
 * ������ѯ�ӿ�
 * @author zhangxya
 *
 */
public interface IWfmConditonBill {
	
	public void addContidions(WfmQueryconditionVO[] conditions) throws WfmServiceException;
	
	public void updateConditons(WfmQueryconditionVO[] conditions) throws WfmServiceException;

}
