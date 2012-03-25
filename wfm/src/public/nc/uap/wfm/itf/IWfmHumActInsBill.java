package nc.uap.wfm.itf;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.model.HumActIns;
public interface IWfmHumActInsBill {
	/**
	 * ͬ���ʵ����Ϣ
	 * @param actIns
	 * @return
	 * @throws WfmServiceException
	 */
	HumActIns asynHumActIns(HumActIns actIns) throws WfmServiceException;
	/**
	 * ��ɾ�ʵ����Ϣ
	 * @param humActInsPk
	 * @return
	 * @throws WfmServiceException
	 */
	boolean bogusDeleteHumActInsByPk(String humActInsPk) throws WfmServiceException;
	/**
	 * ��ɾ�ʵ����Ϣ
	 * @param humActInsPk
	 * @return
	 * @throws WfmServiceException
	 */
	boolean realDeleteHumActInsByPk(String humActInsPk) throws WfmServiceException;
	/**
	 * ��������ʵ��PKɾ���˹����Ϣ
	 * @param proInsPk
	 * @return
	 * @throws WfmServiceException
	 */
	boolean deleteByProInsPk(String proInsPk) throws WfmServiceException;
}
