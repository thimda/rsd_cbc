package nc.uap.wfm.impl;
import nc.bs.dao.DAOException;
import nc.bs.logging.Logger;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.itf.IWfmAssignActorsBill;
import nc.uap.wfm.vo.WfmAssignActorsVO;
public class WfmAssignActorsBill implements IWfmAssignActorsBill {
	public void saveAssignActors(WfmAssignActorsVO[] actors) throws WfmServiceException {
		if (actors == null || actors.length == 0) {
			return;
		}
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.insertVOs(actors);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new WfmServiceException(e.getMessage());
		}
	}
	public boolean deleteByRootProInsPk(String rootInsPk) throws WfmServiceException {
		if (rootInsPk == null || rootInsPk.length() == 0) {
			return false;
		}
		PtBaseDAO dao = new PtBaseDAO();
		String sql = "delete from wfm_assignactors where pk_rootproins='" + rootInsPk + "'";
		try {
			dao.executeUpdate(sql);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new WfmServiceException(e.getMessage());
		}
		return true;
	}
	@Override
	public void deleteAssignActors(String rootInsPk, String proDefId, String portId) {
		if (rootInsPk == null || rootInsPk.length() == 0 || portId == null || portId.length() == 0) {
			return;
		}
		String str = "delete from wfm_assignactors where  pk_rootproins='" + rootInsPk + "' and humact_id='" + portId + "' and prodef_id='" + proDefId + "'";
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.executeUpdate(str);
		} catch (DAOException e) {
			Logger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
	}
}
