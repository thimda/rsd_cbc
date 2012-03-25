package nc.uap.wfm.impl;

import nc.bs.dao.DAOException;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.itf.IWfmFlwPrtBill;
import nc.uap.wfm.vo.WfmFlwPrtVO;

public class WfmFlwPrtBill implements IWfmFlwPrtBill {
	@Override
	public void saveOrUpdate(WfmFlwPrtVO flwPrtVo) throws WfmServiceException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			if (flwPrtVo.getPk_flwprt() == null) {
				dao.insertVO(flwPrtVo);
			} else {
				dao.updateVO(flwPrtVo);
			}
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
	}
}
