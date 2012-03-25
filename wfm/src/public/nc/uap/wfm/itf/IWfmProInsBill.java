package nc.uap.wfm.itf;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.model.ProIns;
public interface IWfmProInsBill {
	/**
	 * 同步流程实例数据
	 * @param processInstance
	 * @return
	 * @throws WfmServiceException
	 */
	ProIns asynProIns(ProIns processInstance) throws WfmServiceException;
	/**
	 * 删除流程实例
	 * @param proInsPk
	 * @throws WfmServiceException
	 */
	void deleteProInsByProInsPk(String proInsPk) throws WfmServiceException;
}
