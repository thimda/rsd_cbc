package nc.uap.wfm.impl;

import nc.bs.dao.DAOException;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.itf.IWfmFlwPrtQry;
import nc.uap.wfm.vo.WfmFlwPrtVO;

public class WfmFlwPrtQry implements IWfmFlwPrtQry {
	@Override
	public WfmFlwPrtVO getFlwPrtVoByProDefPkAndIdAndPrtId(String proDefPk, String proDefId, String portId) throws WfmServiceException {
		if (proDefId == null || proDefId.length() == 0 || portId == null || portId.length() == 0) {
			return null;
		}
		WfmFlwPrtVO[] vos = null;
		PtBaseDAO dao = new PtBaseDAO();
		try {
			vos = (WfmFlwPrtVO[]) dao.queryByCondition(WfmFlwPrtVO.class, "pk_prodef='" + proDefPk + "' and prodef_id='" + proDefId + "' and port_id='" + portId + "'");
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new WfmServiceException(e.getMessage());
		}
		if (vos == null || vos.length == 0) {
			return null;
		}
		return vos[0];
	}
}
