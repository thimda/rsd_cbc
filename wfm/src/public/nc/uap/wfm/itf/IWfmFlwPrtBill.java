package nc.uap.wfm.itf;

import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.vo.WfmFlwPrtVO;

public interface IWfmFlwPrtBill {
	void saveOrUpdate(WfmFlwPrtVO flwPrtVo) throws WfmServiceException;
}
