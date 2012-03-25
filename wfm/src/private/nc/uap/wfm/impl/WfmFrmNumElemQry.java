package nc.uap.wfm.impl;
import nc.bs.dao.DAOException;
import nc.bs.framework.common.NCLocator;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.wfm.itf.IWfmFrmNumElemQry;
import nc.uap.wfm.vo.WfmFrmNumElemVO;
public class WfmFrmNumElemQry implements IWfmFrmNumElemQry {
	@Override public WfmFrmNumElemVO getFrmNumElemVoByPk(String frmNumElemPk) {
		if (frmNumElemPk == null || frmNumElemPk.length() == 0) {
			return null;
		}
		WfmFrmNumElemVO[] frmNumElemVos = null;
		PtBaseDAO dao = new PtBaseDAO();
		try {
			frmNumElemVos = (WfmFrmNumElemVO[]) dao.queryByCondition(WfmFrmNumElemVO.class, "pk_frmnumelem='" + frmNumElemPk + "'");
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
		if (frmNumElemVos == null) {
			return null;
		}
		if (frmNumElemVos.length == 1) {
			return frmNumElemVos[0];
		}
		return null;
	}
	@Override public WfmFrmNumElemVO getFrmNumElemVOByFrmNumRulePkAndCode(String frmNumRulePk, String code) {
		if (frmNumRulePk == null || frmNumRulePk.length() == 0) {
			return null;
		}
		if (code == null || code.length() == 0) {
			return null;
		}
		WfmFrmNumElemVO[] frmNumElemVos = null;
		PtBaseDAO dao = new PtBaseDAO();
		try {
			frmNumElemVos = (WfmFrmNumElemVO[]) dao.queryByCondition(WfmFrmNumElemVO.class, "pk_frmnumrule='" + frmNumRulePk + "' and code='" + code + "'");
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
		if (frmNumElemVos == null) {
			return null;
		}
		if (frmNumElemVos.length == 1) {
			return frmNumElemVos[0];
		}
		return null;
	}
	@Override public WfmFrmNumElemVO[] getFrmNumElemVOByFrmNumRulePk(String frmNumRulePk) {
		if (frmNumRulePk == null || frmNumRulePk.length() == 0) {
			return null;
		}
		WfmFrmNumElemVO[] frmNumElemVos = null;
		PtBaseDAO dao = new PtBaseDAO();
		try {
			frmNumElemVos = (WfmFrmNumElemVO[]) dao.queryByCondition(WfmFrmNumElemVO.class, "pk_frmnumrule='" + frmNumRulePk + "' order by orderstr");
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
		return frmNumElemVos;
	}
	public String getOrderStr(String frmNumRulePk) {
		WfmFrmNumElemVO[] frmNumElemVos = NCLocator.getInstance().lookup(IWfmFrmNumElemQry.class).getFrmNumElemVOByFrmNumRulePk(frmNumRulePk);
		int count = 0;
		if (frmNumElemVos == null || frmNumElemVos.length == 0) {
			count = count + 1;
		} else {
			count = count + frmNumElemVos.length + 1;
		}
		return String.valueOf(count);
	}
}
