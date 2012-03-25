package nc.uap.wfm.impl;
import nc.bs.dao.DAOException;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.wfm.itf.IWfmFlowAgentBill;
import nc.uap.wfm.vo.WfmFlowAgentVO;
public class WfmFlowAgentBill implements IWfmFlowAgentBill {
	public void deleteFlowAgent(WfmFlowAgentVO vo) {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.deleteVO(vo);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage(), e);
		}
	}
	public void deleteFlowAgent(String flowAgentPk) {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.deleteByPK(WfmFlowAgentVO.class, flowAgentPk);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage(), e);
		}
	}
	public void saveOrUpdateFlowAgent(WfmFlowAgentVO vo) {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			if (vo.getPk_flowagent() == null || vo.getPk_flowagent().length() == 0) {
				dao.insertVO(vo);
			} else {
				dao.updateVO(vo);
			}
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage(), e);
		}
	}
}
