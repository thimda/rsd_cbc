package nc.uap.wfm.itf;

import java.util.Set;

import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.model.HumActIns;
import nc.uap.wfm.vo.WfmHumActInsVO;

public interface IWfmHumActInsQry {
	/**
	 * ��ȡ�˹��ʵ����Ϣ
	 * 
	 * @param humActInsPk
	 * @return
	 * @throws WfmServiceException
	 */
	HumActIns getHumActInsByPk(String humActInsPk) throws WfmServiceException;

	/**
	 * ��ȡ��������Ļʵ��
	 * 
	 * @param proInsPk
	 * @return
	 * @throws WfmServiceException
	 */
	Set<HumActIns> getHumActInsesByProInsPk(String proInsPk) throws WfmServiceException;

	/**
	 * ��������ʵ�����˹��ID��ȡ�˹��ʵ����Ϣ
	 * 
	 * @param proInsPk
	 * @param prtId
	 * @return
	 * @throws WfmServiceException
	 */
	HumActIns getHumActInsByProInsPkAndPrtId(String proInsPk, String prtId) throws WfmServiceException;

	WfmHumActInsVO[] getHumActInsesByRootProInsPk(String rootInsPk) throws WfmServiceException;

}
