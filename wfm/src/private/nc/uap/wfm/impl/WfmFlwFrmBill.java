package nc.uap.wfm.impl;

import nc.bs.dao.DAOException;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.itf.IWfmFlwFrmBill;
import nc.uap.wfm.vo.WfmFlwFrmVO;

public class WfmFlwFrmBill implements IWfmFlwFrmBill {
	public boolean deleteFrmItmPrt(String proDefPk, String proDefId, String portId) throws WfmServiceException {
		if (proDefId == null || proDefId.length() == 0 || portId == null || portId.length() == 0) {
			return false;
		}
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.executeUpdate("delete from wfm_flwfrm  where pk_prodef='" + proDefPk + "' and  prodef_id='" + proDefId + "' and port_id='" + portId + "'");
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new WfmServiceException(e.getMessage());
		}
		return true;
	}
	@Override
	public void saveFlwFrm(WfmFlwFrmVO flwFrmVo) throws WfmServiceException {
		if (flwFrmVo == null)
			return;
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.insertVO(flwFrmVo);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new WfmServiceException(e.getMessage());
		}
	}
	@Override
	public void saveFlwFrm(WfmFlwFrmVO[] flwFrmVos) throws WfmServiceException {
		if (flwFrmVos == null)
			return;
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.insertVOs(flwFrmVos);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new WfmServiceException(e.getMessage());
		}
	}
}
