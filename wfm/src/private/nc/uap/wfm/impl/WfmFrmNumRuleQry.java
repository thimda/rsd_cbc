package nc.uap.wfm.impl;
import nc.bs.dao.DAOException;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.wfm.itf.IWfmFrmNumRuleQry;
import nc.uap.wfm.vo.WfmFrmNumRuleVO;
import nc.vo.pub.SuperVO;
public class WfmFrmNumRuleQry implements IWfmFrmNumRuleQry {
	@Override public WfmFrmNumRuleVO getFrmNumRuleVoByPk(String frmNumRulePk) {
		if (frmNumRulePk == null || frmNumRulePk.length() == 0) {
			return null;
		}
		WfmFrmNumRuleVO[] frmNumRuleVos = null;
		PtBaseDAO dao = new PtBaseDAO();
		try {
			frmNumRuleVos = (WfmFrmNumRuleVO[]) dao.queryByCondition(WfmFrmNumRuleVO.class, "pk_frmnumrule='" + frmNumRulePk + "'");
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
		if (frmNumRuleVos == null) {
			return null;
		}
		if (frmNumRuleVos.length == 1) {
			return frmNumRuleVos[0];
		}
		return null;
	}
	@Override public WfmFrmNumRuleVO getFrmNumRuleVoByProDefPkAndId(String proDefPk, String proDefId) {
		if (proDefPk == null || proDefPk.length() == 0) {
			return null;
		}
		if (proDefId == null || proDefId.length() == 0) {
			return null;
		}
		WfmFrmNumRuleVO[] frmNumRuleVos = null;
		PtBaseDAO dao = new PtBaseDAO();
		try {
			frmNumRuleVos = (WfmFrmNumRuleVO[]) dao.queryByCondition(WfmFrmNumRuleVO.class, "pk_prodef='" + proDefPk + "' and prodef_id='" + proDefId + "'");
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
		if (frmNumRuleVos == null) {
			return null;
		}
		if (frmNumRuleVos.length == 1) {
			return frmNumRuleVos[0];
		}
		return null;
	}
	public WfmFrmNumRuleVO getFrmNumRulrVoByFlowTypePk(String flowTypePk) {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			SuperVO[] vos = dao.queryByCondition(WfmFrmNumRuleVO.class, "pk_flowtype='" + flowTypePk + "'");
			if (vos != null && vos.length == 1) {
				return (WfmFrmNumRuleVO) vos[0];
			}
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
		return null;
	}
}
