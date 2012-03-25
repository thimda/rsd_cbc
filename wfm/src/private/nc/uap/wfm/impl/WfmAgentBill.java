package nc.uap.wfm.impl;
import nc.bs.dao.DAOException;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.itf.IWfmAgentBill;
import nc.uap.wfm.vo.WfmFuncAgentVO;
public class WfmAgentBill implements IWfmAgentBill {
	@Override
	public void saveAgents(WfmFuncAgentVO[] vos) throws WfmServiceException {
		if (vos == null || vos.length == 0) {
			return;
		}
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.insertVOs(vos);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new WfmServiceException(e.getMessage());
		}
	}
	@Override
	public boolean deleteAgentByUserPk(String userPk) throws WfmServiceException {
		if (userPk == null || userPk.length() == 0)
			return false;
		PtBaseDAO dao = new PtBaseDAO();
		String sql = "delete from wfm_agentsetting where pk_user='" + userPk + "'";
		try {
			dao.executeUpdate(sql);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new WfmServiceException(e.getMessage());
		}
		return true;
	}
	@Override
	public void update(WfmFuncAgentVO vo) throws WfmServiceException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.updateVO(vo);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new WfmServiceException(e.getMessage());
		}
	}
	
	
}
