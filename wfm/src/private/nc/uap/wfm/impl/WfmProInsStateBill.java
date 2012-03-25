package nc.uap.wfm.impl;
import nc.bs.dao.DAOException;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.itf.IWfmProInsStateBill;
import nc.uap.wfm.vo.WfmProInsStateVO;
public class WfmProInsStateBill implements IWfmProInsStateBill {
	@Override
	public WfmProInsStateVO saveProInsState(WfmProInsStateVO vo) throws WfmServiceException {
		if (vo == null) {
			return null;
		}
		PtBaseDAO dao = new PtBaseDAO();
		try {
			if (vo.getPk_proinsstate() == null || vo.getPk_proinsstate().length() == 0) {
				dao.insertVO(vo);
			} else {
				dao.updateVO(vo);
			}
			return vo;
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new WfmServiceException(e.getMessage());
		}
	}
	@Override
	public void deleteProInsStateByProInsPk(String proInsPk) throws WfmServiceException {
		if (proInsPk == null || proInsPk.length() == 0) {
			return;
		}
		PtBaseDAO dao = new PtBaseDAO();
		String sql = "delete from wfm_proinsstate where pk_proins='" + proInsPk + "'";
		try {
			dao.executeUpdate(sql);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new WfmServiceException(e.getMessage());
		}
	}
}
