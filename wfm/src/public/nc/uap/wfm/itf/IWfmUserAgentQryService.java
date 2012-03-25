package nc.uap.wfm.itf;

import java.util.Map;

import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.vo.WfmFlowAgentVO;
import nc.uap.wfm.vo.WfmUseragentVO;

/**
 * 用户代理人查询服务
 */
public interface IWfmUserAgentQryService {
	/**
	 * 根据部门获得用户设置的代理人PK
	 * @param pk_user 用户PK
	 * @param pk_dept 部门PK
	 * @return 代理人PK 如果没有设置,返回用户PK
	 * @throws PortalServiceException
	 */
 	public String getDeptAgent(String pk_user,String pk_dept) throws WfmServiceException;
 	
 	/**
 	 * 根据部门获得用户设置的代理人PKs
 	 * @param pk_user 用户PK数组
 	 * @param pk_dept 部门PK
 	 * @return  用户PK对应的代理人PK ( key : 用户PK ,value :代理人PK ) 如果没有设置,返回用户PK
 	 * @throws WfmServiceException
 	 */
 	public Map<String,String> getDeptAgent(String[] pk_user,String pk_dept) throws WfmServiceException;
 	
 	/**
 	 * 根据PK获得代理人设置VO
 	 * @param pk
 	 * @return
 	 * @throws WfmServiceException
 	 */
 	public WfmUseragentVO getAgentByPK(String pk) throws WfmServiceException;
 	
 	/**
 	 * 获得带激活的部门型代理人设置VO
 	 * @return
 	 * @throws WfmServiceException
 	 */
 	public WfmUseragentVO[] getBeActiveDeptAgents() throws WfmServiceException;
 	
 	/**
 	 * 获得带激活的单据型代理人设置VO
 	 * @return
 	 * @throws WfmServiceException
 	 */
 	public WfmFlowAgentVO[] getBeActiveFrmAgents() throws WfmServiceException;
 	
 	/**
 	 * 根据单据类型获得用户设置的代理人PK
 	 * @param pk_user
 	 * @param frmtype
 	 * @return
 	 * @throws WfmServiceException
 	 */
 	public String getFrmAgent(String pk_user,String frmtype) throws WfmServiceException;
 	
}
