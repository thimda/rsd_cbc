package nc.uap.wfm.impl;
import nc.bs.dao.DAOException;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.itf.IWfmProDefBill;
import nc.uap.wfm.vo.WfmProdefVO;
/**
 * 2010-12-17 ÏÂÎç04:24:05 limingf
 */
public class WfmProDefBill implements IWfmProDefBill {
	@Override public void updateProdef(WfmProdefVO prodefvo) throws WfmServiceException {
		try {
			PtBaseDAO dao = new PtBaseDAO();
			dao.updateVO(prodefvo);
		} catch (DAOException e) {
			throw new WfmServiceException(e);
		}
	}
	@Override public String insertProdef(WfmProdefVO prodefvo) throws WfmServiceException {
		try {
			PtBaseDAO dao = new PtBaseDAO();
			return dao.insertVO(prodefvo);
		} catch (DAOException e) {
			throw new WfmServiceException(e);
		}
	}
	@Override public void deleteProDefByPk(String proDefPk) throws WfmServiceException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.deleteByPK(WfmProdefVO.class, proDefPk);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new WfmServiceException(e);
		}
	}
}
