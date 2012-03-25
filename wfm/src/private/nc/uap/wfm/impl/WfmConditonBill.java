package nc.uap.wfm.impl;

import nc.bs.dao.DAOException;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.itf.IWfmConditonBill;
import nc.uap.wfm.vo.WfmQueryconditionVO;

/**
 * 条件实现类
 * @author zhangxya
 *
 */
public class WfmConditonBill implements IWfmConditonBill {

	@Override
	public void addContidions(WfmQueryconditionVO[] conditions)
			throws WfmServiceException {
		// TODO Auto-generated method stub
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.insertVOs(conditions);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			LfwLogger.error(e.getMessage(), e);
			throw new WfmServiceException(e);
		}
	}

	@Override
	public void updateConditons(WfmQueryconditionVO[] conditions)
			throws WfmServiceException {
		// TODO Auto-generated method stub
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.updateVOArray(conditions);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			LfwLogger.error(e.getMessage(), e);
			throw new WfmServiceException(e);
		}
	}

}
