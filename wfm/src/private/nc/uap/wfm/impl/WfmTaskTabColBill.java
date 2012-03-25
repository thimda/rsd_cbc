package nc.uap.wfm.impl;

import nc.bs.dao.DAOException;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.itf.IWfmTaskTabColBill;
import nc.uap.wfm.vo.WfmTaskTabColVO;

public class WfmTaskTabColBill implements IWfmTaskTabColBill {
	@Override
	public void saveTaskTabCol(WfmTaskTabColVO[] taskTabColVos) throws WfmServiceException{
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.insertVOs(taskTabColVos);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new WfmServiceException(e.getMessage(),e);
		}
	}
	@Override
	public void saveTaskTabCol(WfmTaskTabColVO taskTabColVo) throws WfmServiceException{
		this.saveTaskTabCol(new WfmTaskTabColVO[] { taskTabColVo });
	}
	@Override
	public void deleteTaskTabCol(String proDefPk, String proDefId, String tabCtrlValue) throws WfmServiceException {
		PtBaseDAO dao = new PtBaseDAO();
		String sql = "delete from wfm_tasktabcol where pk_prodef='" + proDefPk + "' and prodef_id='" + proDefId + "' and tabctrlvalue='" + tabCtrlValue + "'";
		try {
			dao.executeUpdate(sql);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new WfmServiceException(e.getMessage(),e);
		}
	}
}