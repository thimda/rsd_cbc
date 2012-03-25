package nc.uap.wfm.itf;
import nc.uap.wfm.vo.WfmFrmNumRuleVO;
public interface IWfmFrmNumRuleBill {
	void addFrmNumRule(WfmFrmNumRuleVO frmNumRuleVo);
	void updateFrmNumRule(WfmFrmNumRuleVO frmNumRuleVo);
	void deleteFrmNumRuleByPk(String frmNumRulePk);
	void saveOrUpdateFrmNumRule(WfmFrmNumRuleVO frmNumRule);
}
