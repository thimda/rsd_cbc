package nc.uap.wfm.itf;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.vo.WfmPageAttachVO;
public interface IWfmPageAttachQry {
	/**
	 * 根据sql查询纸质附件信息
	 * @param sql
	 * @return
	 * @throws WfmServiceException
	 */
	WfmPageAttachVO[] getAttachFile(String sql) throws WfmServiceException;
}
