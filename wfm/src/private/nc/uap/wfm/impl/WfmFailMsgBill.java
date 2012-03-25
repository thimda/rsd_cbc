package nc.uap.wfm.impl;
import nc.bs.dao.DAOException;
import nc.bs.logging.Logger;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.wfm.itf.IWfmFailMsgBill;
import nc.uap.wfm.vo.WfmFailMsgVO;
public class WfmFailMsgBill implements IWfmFailMsgBill {
	@Override
	public void deleteFailMsgByPk(String failMsgPk) {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.deleteByPK(WfmFailMsgVO.class, failMsgPk);
		} catch (DAOException e) {
			Logger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage(), e);
		}
	}
	@Override
	public void saveFailMsg(WfmFailMsgVO failMsgVO) {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.insertVO(failMsgVO);
		} catch (DAOException e) {
			Logger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage(), e);
		}
	}
}
