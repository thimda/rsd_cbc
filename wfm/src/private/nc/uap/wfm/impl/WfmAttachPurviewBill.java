package nc.uap.wfm.impl;

import nc.bs.dao.DAOException;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.wfm.itf.IWfmAttachPurviewBill;
import nc.uap.wfm.vo.WfmAttachPurviewVO;

public class WfmAttachPurviewBill implements IWfmAttachPurviewBill {
	@Override
	public void saveAttachPurview(WfmAttachPurviewVO[] vos) {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.insertVOs(vos);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
	}
	@Override
	public void deleteAttachPurview(String proDefPk, String proDefId, String portId) {
		if (proDefId == null || proDefId.length() == 0 || portId == null || portId.length() == 0) {
			return;
		}
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.executeUpdate("delete from wfm_attachpurview  where pk_prodef='" + proDefPk + "' and  prodef_id='" + proDefId + "' and port_id='" + portId + "'");
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
	}
}
