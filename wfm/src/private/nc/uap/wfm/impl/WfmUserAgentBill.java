package nc.uap.wfm.impl;

import nc.bs.dao.DAOException;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.itf.IWfmUserAgentService;
import nc.uap.wfm.vo.WfmFlowAgentVO;
import nc.uap.wfm.vo.WfmUseragentVO;

/**
 * 用户代理人服务实现
 *
 */
public class WfmUserAgentBill implements IWfmUserAgentService{

	@Override
	public void update(WfmUseragentVO[] agent) throws WfmServiceException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.updateVOArray(agent);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(),e);
		}
	}

	@Override
	public void update(WfmUseragentVO agent) throws WfmServiceException {
		update(new WfmUseragentVO[]{agent});
	}

	@Override
	public void update(WfmFlowAgentVO[] agent) throws WfmServiceException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.updateVOArray(agent);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(),e);
		}
	}
	
}
