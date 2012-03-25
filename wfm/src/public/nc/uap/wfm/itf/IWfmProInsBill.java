package nc.uap.wfm.itf;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.model.ProIns;
public interface IWfmProInsBill {
	/**
	 * ͬ������ʵ������
	 * @param processInstance
	 * @return
	 * @throws WfmServiceException
	 */
	ProIns asynProIns(ProIns processInstance) throws WfmServiceException;
	/**
	 * ɾ������ʵ��
	 * @param proInsPk
	 * @throws WfmServiceException
	 */
	void deleteProInsByProInsPk(String proInsPk) throws WfmServiceException;
}
