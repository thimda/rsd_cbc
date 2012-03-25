package nc.uap.wfm.impl;
import nc.bs.dao.DAOException;
import nc.jdbc.framework.processor.ColumnProcessor;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.itf.IWfmAddSignQry;
import nc.uap.wfm.vo.WfmAddSignUserVO;
import nc.uap.wfm.vo.WfmAddSignVO;
public class WfmAddSignQry implements IWfmAddSignQry {
	@Override
	public WfmAddSignVO[] getAddSignVoByTaskPk(String taskPk) throws WfmServiceException {
		String where = "pk_task='" + taskPk + "'";
		WfmAddSignVO[] vos = this.getAddSignVosByWhere(where);
		if (vos == null || vos.length == 0) {
			return null;
		}
		int length = vos.length;
		for (int i = 0; i < length; i++) {
			where = "pk_addsign" + vos[i].getPk_addsign() + "'";
			WfmAddSignUserVO[] addSignUserVos = this.getAddSignUserVosByWhere(where);
			vos[i].setAddSignUserVos(addSignUserVos);
		}
		return vos;
	}
	public WfmAddSignVO[] getAddSignVosByWhere(String where) throws WfmServiceException {
		WfmAddSignVO[] vos = null;
		PtBaseDAO dao = new PtBaseDAO();
		try {
			vos = (WfmAddSignVO[]) dao.queryByCondition(WfmAddSignVO.class, where);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new WfmServiceException(e.getMessage());
		}
		return vos;
	}
	public WfmAddSignUserVO[] getAddSignUserVosByWhere(String where) throws WfmServiceException {
		WfmAddSignUserVO[] vos = null;
		PtBaseDAO dao = new PtBaseDAO();
		try {
			vos = (WfmAddSignUserVO[]) dao.queryByCondition(WfmAddSignUserVO.class, where);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new WfmServiceException(e.getMessage());
		}
		return vos;
	}
	@Override
	public String getMaxStateTimeByTaskPk(String taskPk) throws WfmServiceException {
		PtBaseDAO dao = new PtBaseDAO();
		String sql = "SELECT max(addsigntime) FROM wfm_addsign WHERE pk_task = '" + taskPk + "'";
		try {
			String maxValue = (String) dao.executeQuery(sql, new ColumnProcessor());
			if (maxValue == null) {
				return String.valueOf(0);
			}
			return String.valueOf(maxValue);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new WfmServiceException(e.getMessage());
		}
	}
	@Override
	public WfmAddSignVO getAddSignVoByTaskPkAndTime(String taskPk, String addSignTimes) throws WfmServiceException {
		String where = "pk_task='" + taskPk + "' and addsigntime ='" + addSignTimes + "'";
		WfmAddSignVO[] vos = this.getAddSignVosByWhere(where);
		if (vos == null || vos.length == 0) {
			return null;
		}
		return vos[0];
	}
	@Override
	public WfmAddSignVO getAddSingVoByAddSignPk(String addSignPk) throws WfmServiceException {
		String where = "pk_addsign='" + addSignPk + "'";
		WfmAddSignVO[] vos = this.getAddSignVosByWhere(where);
		if (vos == null || vos.length == 0) {
			return null;
		}
		WfmAddSignUserVO[] addSignUserVos = this.getAddSignUserVosByWhere(where);
		vos[0].setAddSignUserVos(addSignUserVos);
		return vos[0];
	}
}
