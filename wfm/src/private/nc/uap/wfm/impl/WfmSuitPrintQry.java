package nc.uap.wfm.impl;
import nc.bs.dao.DAOException;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.wfm.itf.IWfmSuitPrintQry;
import nc.uap.wfm.vo.WfmSuitPrintVO;
public class WfmSuitPrintQry implements IWfmSuitPrintQry {
	public WfmSuitPrintVO[] getSuitPrintVo(String pk_formdefintion) {
		String where = "pk_formdefinition='" + pk_formdefintion + "'";
		WfmSuitPrintVO[] vos = this.getSuitPrintVosByWhere(where);
		if (vos == null || vos.length == 0) {
			return null;
		}
		return vos;
	}
	public WfmSuitPrintVO[] getSuitPrintVosByWhere(String where) {
		WfmSuitPrintVO[] vos = null;
		PtBaseDAO dao = new PtBaseDAO();
		try {
			vos = (WfmSuitPrintVO[]) dao.queryByCondition(WfmSuitPrintVO.class, where);
			return vos;
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
	}
	public WfmSuitPrintVO getSuitPrintVoByPk(String suitPrintPk) {
		String where = "pk_suitprint='" + suitPrintPk + "'";
		WfmSuitPrintVO[] vos = this.getSuitPrintVosByWhere(where);
		if (vos == null || vos.length == 0) {
			return null;
		}
		return vos[0];
	}
	
	

}
