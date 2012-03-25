package nc.uap.wfm.itf;

import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.vo.WfmFlowAgentVO;
import nc.uap.wfm.vo.WfmUseragentVO;

/**
 * �û������˷���
 *
 */
public interface IWfmUserAgentService {
	/**
	 * ����һ������������
	 * @param agent
	 * @throws WfmServiceException
	 */
	public void update (WfmUseragentVO agent) throws WfmServiceException;
	
	/**
	 * ����һ������������
	 * @param agent
	 * @throws WfmServiceException
	 */
	public void update (WfmUseragentVO[] agent) throws WfmServiceException;
	
	/**
	 * ����һ������������
	 * @param agent
	 * @throws WfmServiceException
	 */
	public void update (WfmFlowAgentVO[] agent) throws WfmServiceException;
}
