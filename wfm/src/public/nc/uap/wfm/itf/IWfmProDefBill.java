package nc.uap.wfm.itf;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.vo.WfmProdefVO;
/**
 * 2010-12-17 обнГ04:20:41 limingf
 */
public interface IWfmProDefBill {
	public String insertProdef(WfmProdefVO prodefvo) throws WfmServiceException;
	public void updateProdef(WfmProdefVO prodefvo) throws WfmServiceException;
	public void deleteProDefByPk(String proDefPk) throws WfmServiceException;
}
