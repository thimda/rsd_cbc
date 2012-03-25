package nc.uap.wfm.impl;
import nc.bs.dao.DAOException;
import nc.bs.logging.Logger;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.wfm.itf.IWfmAssignActorsQry;
import nc.uap.wfm.vo.WfmAssignActorsVO;
public class WfmAssignActorsQry implements IWfmAssignActorsQry {
	@Override
	public WfmAssignActorsVO[] getAssignActors(String rootProInsPk, String proDefId, String portId) {
		if (rootProInsPk == null || rootProInsPk.length() == 0 || portId == null || portId.length() == 0) {
			return null;
		}
		WfmAssignActorsVO[] vos = null;
		String str = "pk_rootproins='" + rootProInsPk + "' and humact_id='" + portId + "' and prodef_id='" + proDefId + "'";
		PtBaseDAO dao = new PtBaseDAO();
		try {
			vos = (WfmAssignActorsVO[]) dao.queryByCondition(WfmAssignActorsVO.class, str);
		} catch (DAOException e) {
			Logger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
		return vos;
	}
}
