package nc.uap.wfm.itf;
import nc.uap.wfm.vo.WfmFlowAgentVO;
public interface IWfmFlowAgentBill {
	void saveOrUpdateFlowAgent(WfmFlowAgentVO vo);
	void deleteFlowAgent(WfmFlowAgentVO vo);
	void deleteFlowAgent(String flowAgentPk);
}
