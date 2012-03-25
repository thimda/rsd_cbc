package nc.uap.wfm.itf;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.model.HumActIns;
public interface IWfmHumActInsBill {
	/**
	 * 同步活动实例信息
	 * @param actIns
	 * @return
	 * @throws WfmServiceException
	 */
	HumActIns asynHumActIns(HumActIns actIns) throws WfmServiceException;
	/**
	 * 假删活动实例信息
	 * @param humActInsPk
	 * @return
	 * @throws WfmServiceException
	 */
	boolean bogusDeleteHumActInsByPk(String humActInsPk) throws WfmServiceException;
	/**
	 * 真删活动实例信息
	 * @param humActInsPk
	 * @return
	 * @throws WfmServiceException
	 */
	boolean realDeleteHumActInsByPk(String humActInsPk) throws WfmServiceException;
	/**
	 * 根据流程实例PK删除人工活动信息
	 * @param proInsPk
	 * @return
	 * @throws WfmServiceException
	 */
	boolean deleteByProInsPk(String proInsPk) throws WfmServiceException;
}
