package nc.uap.wfm.impl;
import nc.bs.dao.DAOException;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.wfm.itf.IWfmFrmNumRuleBill;
import nc.uap.wfm.vo.WfmFrmNumRuleVO;
public class WfmFrmNumRuleBill implements IWfmFrmNumRuleBill {
	@Override public void addFrmNumRule(WfmFrmNumRuleVO frmNumRuleVo) {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.insertVO(frmNumRuleVo);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
	}
	@Override public void updateFrmNumRule(WfmFrmNumRuleVO frmNumRuleVo) {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.updateVO(frmNumRuleVo);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
	}
	@Override public void deleteFrmNumRuleByPk(String frmNumRulePk) {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.deleteByPK(WfmFrmNumRuleVO.class, frmNumRulePk);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
	}
	public void saveOrUpdateFrmNumRule(WfmFrmNumRuleVO frmNumRule) {
		if (frmNumRule == null) {
			return;
		}
		if(frmNumRule.getPk_frmnumrule()==null||frmNumRule.getPk_frmnumrule().length()==0){
			this.addFrmNumRule(frmNumRule);
		}else{
			this.updateFrmNumRule(frmNumRule);
		}
	}
}
