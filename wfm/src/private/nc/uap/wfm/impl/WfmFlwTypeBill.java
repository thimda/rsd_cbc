package nc.uap.wfm.impl;
import nc.bs.dao.DAOException;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.itf.IWfmFlwTypeBill;
import nc.uap.wfm.vo.WfmFlwTypeVO;
public class WfmFlwTypeBill implements IWfmFlwTypeBill {
	public void saveFlwType(WfmFlwTypeVO flwTypeVo) throws WfmServiceException {
		this.saveOrUpdateFlwType(flwTypeVo);
	}
	public void saveOrUpdateFlwType(WfmFlwTypeVO flwTypeVo) throws WfmServiceException {
		if (flwTypeVo == null) {
			return;
		}
		PtBaseDAO dao = new PtBaseDAO();
		if (flwTypeVo.getPk_flwtype() == null) {
			try {
				dao.insertVO(flwTypeVo);
			} catch (DAOException e) {
				LfwLogger.error(e.getMessage(), e);
				throw new WfmServiceException(e.getMessage());
			}
		} else {
			try {
				dao.updateVO(flwTypeVo);
			} catch (DAOException e) {
				LfwLogger.error(e.getMessage(), e);
				throw new WfmServiceException(e.getMessage());
			}
		}
	}
	public void updateFlwType(WfmFlwTypeVO flwTypeVo) throws WfmServiceException {
		this.saveOrUpdateFlwType(flwTypeVo);
	}
	public void deleteFlwTypeByPk(String flwTypePk) throws WfmServiceException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.deleteByPK(WfmFlwTypeVO.class, flwTypePk);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new WfmServiceException(e.getMessage());
		}
	}
}
