package nc.uap.wfm.itf;

import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.vo.WfmFlowAgentVO;
import nc.uap.wfm.vo.WfmUseragentVO;

/**
 * 用户代理人服务
 *
 */
public interface IWfmUserAgentService {
	/**
	 * 更新一个代理人设置
	 * @param agent
	 * @throws WfmServiceException
	 */
	public void update (WfmUseragentVO agent) throws WfmServiceException;
	
	/**
	 * 更新一批代理人设置
	 * @param agent
	 * @throws WfmServiceException
	 */
	public void update (WfmUseragentVO[] agent) throws WfmServiceException;
	
	/**
	 * 更新一批代理人设置
	 * @param agent
	 * @throws WfmServiceException
	 */
	public void update (WfmFlowAgentVO[] agent) throws WfmServiceException;
}
