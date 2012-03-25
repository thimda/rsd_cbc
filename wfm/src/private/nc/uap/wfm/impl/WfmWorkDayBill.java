package nc.uap.wfm.impl;

import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.itf.IWfmWorkDayBill;
import nc.uap.wfm.vo.WfmWeekendVO;

/**
 * 工作日设置服务实现
 * @author licza
 *
 */
public class WfmWorkDayBill implements IWfmWorkDayBill {

	public WfmWeekendVO add(WfmWeekendVO vo) throws WfmServiceException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.insertVO(vo);
			return vo;
		} catch (Exception e) {
			LfwLogger.error(e.getMessage(),e);
			throw new WfmServiceException(e.getMessage(),e);
		}
	}

	public void update(WfmWeekendVO vo) throws WfmServiceException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.updateVO(vo);
		} catch (Exception e) {
			LfwLogger.error(e.getMessage(),e);
			throw new WfmServiceException(e.getMessage(),e);
		}
	}

}
