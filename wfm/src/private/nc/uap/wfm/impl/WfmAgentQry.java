package nc.uap.wfm.impl;
import nc.bs.dao.DAOException;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.itf.IWfmAgentQry;
import nc.uap.wfm.vo.WfmFuncAgentVO;
public class WfmAgentQry implements IWfmAgentQry {
	@Override
	public WfmFuncAgentVO[] getAgentsByUserPk(String userPk) throws WfmServiceException {
		if (userPk == null || userPk.length() == 0) {
			return null;
		}
		WfmFuncAgentVO[] vos = null;
		PtBaseDAO dao = new PtBaseDAO();
		try {
			vos = (WfmFuncAgentVO[]) dao.queryByCondition(WfmFuncAgentVO.class, "pk_user='" + userPk + "'");
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new WfmServiceException(e.getMessage());
		}
		return vos;
	}

	@Override
	public WfmFuncAgentVO getAgentsByPk(String pk) throws WfmServiceException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			return (WfmFuncAgentVO)dao.retrieveByPK(WfmFuncAgentVO.class, pk);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new WfmServiceException(e.getMessage());
		}
	}
}
