package nc.uap.wfm.itf;

import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.vo.WfmFlwPrtVO;

public interface IWfmFlwPrtQry {
	WfmFlwPrtVO getFlwPrtVoByProDefPkAndIdAndPrtId(String proDefPk, String proDefId, String prtId) throws WfmServiceException;
}
