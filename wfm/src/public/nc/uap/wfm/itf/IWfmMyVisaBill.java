package nc.uap.wfm.itf;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.vo.WfmMyVisaVO;
public interface IWfmMyVisaBill {
	public void saveMyVisa(WfmMyVisaVO myVisaVo);
	
	public void deleteMyVisByFilePk(String pk_file) throws WfmServiceException;
}
