package nc.uap.wfm.impl;
import nc.bs.dao.BaseDAO;
import nc.bs.dao.DAOException;
import nc.bs.framework.common.NCLocator;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.wfm.builder.BeanConvert;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.itf.IWfmAssignActorsBill;
import nc.uap.wfm.itf.IWfmHumActInsBill;
import nc.uap.wfm.itf.IWfmProInsBill;
import nc.uap.wfm.itf.IWfmProInsStateBill;
import nc.uap.wfm.itf.IWfmTaskBill;
import nc.uap.wfm.model.ProIns;
import nc.uap.wfm.vo.WfmProInsVO;
public class WfmProInsBill implements IWfmProInsBill {
	public ProIns asynProIns(ProIns proIns) throws WfmServiceException {
		if (proIns == null) {
			return null;
		}
		BaseDAO dao = new BaseDAO();
		WfmProInsVO proInsVO = BeanConvert.toProInsVO(proIns);
		try {
			if (proInsVO.getDr() == null) {
				proInsVO.setDr(new Integer(0));
			}
			if (proInsVO.getPk_proins() == null || proInsVO.getPk_proins().length() == 0) {
				dao.insertVO(proInsVO);
			} else {
				dao.updateVO(proInsVO);
			}
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new WfmServiceException(e.getMessage());
		}
		proIns.setPk_proins(proInsVO.getPk_proins());
		return proIns;
	}
	@Override public void deleteProInsByProInsPk(String proInsPk) throws WfmServiceException {
		if (proInsPk == null || proInsPk.length() == 0) {
			return;
		}
		IWfmTaskBill taskBill = NCLocator.getInstance().lookup(IWfmTaskBill.class);
		taskBill.deleteTaskByProInsPk(proInsPk);
		IWfmHumActInsBill humActInsBill = NCLocator.getInstance().lookup(IWfmHumActInsBill.class);
		humActInsBill.deleteByProInsPk(proInsPk);
		IWfmProInsStateBill proInsState = NCLocator.getInstance().lookup(IWfmProInsStateBill.class);
		proInsState.deleteProInsStateByProInsPk(proInsPk);
		IWfmAssignActorsBill assignBill = NCLocator.getInstance().lookup(IWfmAssignActorsBill.class);
		assignBill.deleteByRootProInsPk(proInsPk);
		PtBaseDAO dao = new PtBaseDAO();
		String sql = "delete from wfm_proins where pk_proins='" + proInsPk + "'";
		try {
			dao.executeUpdate(sql);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new WfmServiceException(e.getMessage());
		}
	}
}
