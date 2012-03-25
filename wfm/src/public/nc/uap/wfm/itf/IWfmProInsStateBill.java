package nc.uap.wfm.itf;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.vo.WfmProInsStateVO;
public interface IWfmProInsStateBill {
	WfmProInsStateVO saveProInsState(WfmProInsStateVO vo) throws WfmServiceException;
	void deleteProInsStateByProInsPk(String proInsPk) throws WfmServiceException;
}
