package nc.uap.wfm.itf;

import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.vo.WfmFuncAgentVO;

public interface IWfmAgentQry {
	WfmFuncAgentVO[] getAgentsByUserPk(String userPk) throws WfmServiceException;
	WfmFuncAgentVO getAgentsByPk(String pk) throws WfmServiceException;
}
