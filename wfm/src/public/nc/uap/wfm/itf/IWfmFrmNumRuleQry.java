package nc.uap.wfm.itf;
import nc.uap.wfm.vo.WfmFrmNumRuleVO;
public interface IWfmFrmNumRuleQry {
	WfmFrmNumRuleVO getFrmNumRuleVoByPk(String frmNumRulePk);
	WfmFrmNumRuleVO getFrmNumRuleVoByProDefPkAndId(String proDefPk, String proDefId);
	WfmFrmNumRuleVO getFrmNumRulrVoByFlowTypePk(String flowTypePk);
}
