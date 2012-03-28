package nc.uap.wfm.impl;

import nc.bs.dao.DAOException;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.itf.IWfmAddSignBill;
import nc.uap.wfm.vo.WfmAddSignUserVO;
import nc.uap.wfm.vo.WfmBeforeAddSignVO;

public class WfmAddSignBill implements IWfmAddSignBill {
	public void deleteAddSignVoByTaskPk(String taskPk) throws WfmServiceException {
		PtBaseDAO dao = new PtBaseDAO();
		String sql1 = "delete from wfm_addsignuser where pk_addsign in( select pk_addsign from wfm_addsign where pk_task='" + taskPk + "') ";
		String sql2 = "delete wfm_addsign where pk_task='" + taskPk + "'";
		try {
			dao.executeUpdate(sql1);
			dao.executeUpdate(sql2);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
	}
	public void saveAddSignVo(WfmBeforeAddSignVO addSignVo) throws WfmServiceException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.insertVO(addSignVo);
			WfmAddSignUserVO[] addSignUserVos = addSignVo.getAddSignUserVos();
			for (int i = 0; i < addSignUserVos.length; i++) {
				addSignUserVos[i].setPk_beforeaddsign(addSignVo.getPk_beforeaddsign());
			}
			dao.insertVOs(addSignUserVos);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
	}
}
