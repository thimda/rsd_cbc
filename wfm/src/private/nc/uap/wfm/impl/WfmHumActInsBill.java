package nc.uap.wfm.impl;
import nc.bs.dao.BaseDAO;
import nc.bs.dao.DAOException;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.wfm.builder.BeanConvert;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.itf.IWfmHumActInsBill;
import nc.uap.wfm.model.HumActIns;
import nc.uap.wfm.vo.WfmHumActInsVO;
public class WfmHumActInsBill implements IWfmHumActInsBill {
	public HumActIns asynHumActIns(HumActIns humActIns) throws WfmServiceException {
		if (humActIns == null) {
			return null;
		}
		BaseDAO dao = new BaseDAO();
		WfmHumActInsVO humActInsVO = BeanConvert.toHumActInsVO(humActIns);
		try {
			if (humActInsVO.getDr() == null) {
				humActInsVO.setDr(new Integer(0));
			}
			if (humActIns.getPk_humactins() == null || humActIns.getPk_humactins().length() == 0) {
				dao.insertVO(humActInsVO);
			} else {
				dao.updateVO(humActInsVO);
			}
			humActIns.setPk_humactins(humActInsVO.getPk_humactins());
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage());
			throw new WfmServiceException(e.getMessage());
		}
		return humActIns;
	}
	@Override
	public boolean bogusDeleteHumActInsByPk(String humActInsPk) throws WfmServiceException {
		if (humActInsPk == null || humActInsPk.length() == 0) {
			return false;
		}
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.executeUpdate("update wfm_humactins set dr=1 where pk_humactins='" + humActInsPk + "'");
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new WfmServiceException(e.getMessage());
		}
		return true;
	}
	@Override
	public boolean realDeleteHumActInsByPk(String humActInsPk) throws WfmServiceException {
		if (humActInsPk == null || humActInsPk.length() == 0) {
			return false;
		}
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.deleteByPK(WfmHumActInsVO.class, humActInsPk);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new WfmServiceException(e.getMessage(), e);
		}
		return true;
	}
	@Override
	public boolean deleteByProInsPk(String proInsPk) throws WfmServiceException {
		if (proInsPk == null || proInsPk.length() == 0) {
			return false;
		}
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.executeUpdate("delete from wfm_humactins where pk_proins='" + proInsPk + "'");
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new WfmServiceException(e.getMessage());
		}
		return true;
	}
}
