package nc.uap.wfm.itf;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.vo.WfmPageAttachVO;
public interface IWfmPageAttachQry {
	/**
	 * ����sql��ѯֽ�ʸ�����Ϣ
	 * @param sql
	 * @return
	 * @throws WfmServiceException
	 */
	WfmPageAttachVO[] getAttachFile(String sql) throws WfmServiceException;
}
