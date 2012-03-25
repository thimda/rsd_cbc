package nc.uap.wfm.itf;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.vo.WfmTaskTokenVO;
public interface IWfmTaskTokenQry {
	WfmTaskTokenVO getTaskTokenVoByTokenId(String tokenkId) throws WfmServiceException;
}
