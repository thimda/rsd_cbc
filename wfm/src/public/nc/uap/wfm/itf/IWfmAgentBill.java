package nc.uap.wfm.itf;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.vo.WfmFuncAgentVO;
public interface IWfmAgentBill {
	void update(WfmFuncAgentVO vo) throws WfmServiceException;
	void saveAgents(WfmFuncAgentVO[] vos) throws WfmServiceException;
	boolean deleteAgentByUserPk(String userPk) throws WfmServiceException;
}
