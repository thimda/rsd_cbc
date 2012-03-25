package nc.uap.wfm.itf;
import nc.uap.wfm.vo.WfmFlowAgentVO;
public interface IWfmFlowAgentQry {
	WfmFlowAgentVO[] getFlowAgentVos();
	WfmFlowAgentVO[] getFlowAgentVosByWhere(String whereSql);
	WfmFlowAgentVO getFlowAgentVoByPk(String flowAgentPk);
	WfmFlowAgentVO getFlowAgentVoByFlowTypePk(String flowTypePk);
}
