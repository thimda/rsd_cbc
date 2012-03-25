package nc.uap.wfm.impl;

import nc.bs.dao.DAOException;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.itf.IWfmTaskTabColQry;
import nc.uap.wfm.vo.WfmTaskTabColVO;

public class WfmTaskTabColQry implements IWfmTaskTabColQry {
	@Override
	public WfmTaskTabColVO[] getTaskTabColVos(String proDefPk, String proDefId, String tabCtrlValue) throws WfmServiceException{
		return this.getTaskTabColVosByWhere("pk_prodef='" + proDefPk + "' and prodef_id='" + proDefId + "' and tabctrlvalue='" + tabCtrlValue + "'");
	}
	public WfmTaskTabColVO[] getTaskTabColVosByWhere(String where) throws WfmServiceException {
		WfmTaskTabColVO[] vos = null;
		PtBaseDAO dao = new PtBaseDAO();
		try {
			vos = (WfmTaskTabColVO[]) dao.queryByCondition(WfmTaskTabColVO.class, where);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new WfmServiceException(e.getMessage(),e);
		}
		return vos;
	}
}