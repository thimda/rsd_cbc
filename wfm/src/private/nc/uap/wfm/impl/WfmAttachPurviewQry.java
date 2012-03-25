package nc.uap.wfm.impl;

import nc.bs.dao.DAOException;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.wfm.itf.IWfmAttachPurviewQry;
import nc.uap.wfm.vo.WfmAttachPurviewVO;

public class WfmAttachPurviewQry implements IWfmAttachPurviewQry {
	public WfmAttachPurviewVO[] getAttachPurviews(String proDefPk, String proDefId, String portId) {
		if (proDefId == null || proDefId.length() == 0 || portId == null || portId.length() == 0) {
			return null;
		}
		WfmAttachPurviewVO[] vos = null;
		PtBaseDAO dao = new PtBaseDAO();
		try {
			vos = (WfmAttachPurviewVO[]) dao.queryByCondition(WfmAttachPurviewVO.class, "pk_prodef='" + proDefPk + "' and prodef_id='" + proDefId + "' and port_id='" + portId + "'");
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
		return vos;
	}
	@Override
	public WfmAttachPurviewVO getAttachPurview(String proDefPk, String proDefId, String portId, String targetId) {
		if (proDefId == null || proDefId.length() == 0 || portId == null || portId.length() == 0 || targetId == null || targetId.length() == 0) {
			return null;
		}
		WfmAttachPurviewVO[] vos = null;
		PtBaseDAO dao = new PtBaseDAO();
		try {
			vos = (WfmAttachPurviewVO[]) dao.queryByCondition(WfmAttachPurviewVO.class, "pk_prodef='" + proDefPk + "' and prodef_id='" + proDefId + "' and port_id='" + portId + "' and taget_id='"
			+ targetId + "'");
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
		if (vos == null||vos.length==0) {
			return null;
		} else {
			return vos[0];
		}
	}
}
