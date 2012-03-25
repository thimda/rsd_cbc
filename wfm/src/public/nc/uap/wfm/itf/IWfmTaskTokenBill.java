package nc.uap.wfm.itf;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.vo.WfmTaskTokenVO;
public interface IWfmTaskTokenBill {
	void saveTaskToken(WfmTaskTokenVO taskTokenVo) throws WfmServiceException;
	void deleteTaskToken(String taskTokenVoPk) throws WfmServiceException;
	void deleteTaskTokenByTaskPk(String taskPk) throws WfmServiceException;
	void deleteTaskTokenByTokenId(String tokenId) throws WfmServiceException;
}
