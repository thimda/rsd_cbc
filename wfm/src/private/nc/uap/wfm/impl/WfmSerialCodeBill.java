package nc.uap.wfm.impl;
import nc.bs.dao.DAOException;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.wfm.itf.IWfmSerialCodeBill;
import nc.uap.wfm.vo.WfmSerialCodeVO;
public class WfmSerialCodeBill implements IWfmSerialCodeBill {
	@Override
	public void addSerialCode(WfmSerialCodeVO serialCodeVo) {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.insertVO(serialCodeVo);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
	}
	@Override
	public void updateSerialCode(WfmSerialCodeVO serialCodeVo) {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.updateVO(serialCodeVo);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
	}
}
