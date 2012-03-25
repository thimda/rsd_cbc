package nc.uap.wfm.impl;
import nc.bs.dao.DAOException;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.wfm.itf.IWfmFrmNumElemBill;
import nc.uap.wfm.vo.WfmFrmNumElemVO;
public class WfmFrmNumElemBill implements IWfmFrmNumElemBill {
	@Override public void addFrmNumElem(WfmFrmNumElemVO frmNumElemVo) {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.insertVO(frmNumElemVo);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
	}
	@Override public void deleteFrmNumElemByPk(String frmNumElemPk) {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.deleteByPK(WfmFrmNumElemVO.class, frmNumElemPk);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
	}
	@Override public void updateFrmNumElem(WfmFrmNumElemVO frmNumElemVo) {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.updateVO(frmNumElemVo);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
	}
	public void saveOrUpdateFrmNumElem(WfmFrmNumElemVO frmNumElemVo) {
		if (frmNumElemVo == null) {
			return;
		}
		if (frmNumElemVo.getPk_frmnumelem() == null || frmNumElemVo.getPk_frmnumelem().length() == 0) {
			this.addFrmNumElem(frmNumElemVo);
		} else {
			this.updateFrmNumElem(frmNumElemVo);
		}
	}
}
