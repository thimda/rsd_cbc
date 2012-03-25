package nc.uap.wfm.itf;

import java.util.Set;

import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.model.HumActIns;
import nc.uap.wfm.vo.WfmHumActInsVO;

public interface IWfmHumActInsQry {
	/**
	 * 获取人工活动实例信息
	 * 
	 * @param humActInsPk
	 * @return
	 * @throws WfmServiceException
	 */
	HumActIns getHumActInsByPk(String humActInsPk) throws WfmServiceException;

	/**
	 * 获取流程下面的活动实例
	 * 
	 * @param proInsPk
	 * @return
	 * @throws WfmServiceException
	 */
	Set<HumActIns> getHumActInsesByProInsPk(String proInsPk) throws WfmServiceException;

	/**
	 * 根据流程实例和人工活动ID获取人工活动实例信息
	 * 
	 * @param proInsPk
	 * @param prtId
	 * @return
	 * @throws WfmServiceException
	 */
	HumActIns getHumActInsByProInsPkAndPrtId(String proInsPk, String prtId) throws WfmServiceException;

	WfmHumActInsVO[] getHumActInsesByRootProInsPk(String rootInsPk) throws WfmServiceException;

}
