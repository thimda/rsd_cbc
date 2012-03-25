package nc.uap.wfm.itf;
import nc.uap.wfm.exception.WfmServiceException;
public interface IWfmProInsStateQry {
	String getProInsState(String proInsPk) throws WfmServiceException;
}
