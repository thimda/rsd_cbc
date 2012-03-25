package nc.uap.wfm.itf;

import java.util.Map;

import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.vo.WfmFlowAgentVO;
import nc.uap.wfm.vo.WfmUseragentVO;

/**
 * �û������˲�ѯ����
 */
public interface IWfmUserAgentQryService {
	/**
	 * ���ݲ��Ż���û����õĴ�����PK
	 * @param pk_user �û�PK
	 * @param pk_dept ����PK
	 * @return ������PK ���û������,�����û�PK
	 * @throws PortalServiceException
	 */
 	public String getDeptAgent(String pk_user,String pk_dept) throws WfmServiceException;
 	
 	/**
 	 * ���ݲ��Ż���û����õĴ�����PKs
 	 * @param pk_user �û�PK����
 	 * @param pk_dept ����PK
 	 * @return  �û�PK��Ӧ�Ĵ�����PK ( key : �û�PK ,value :������PK ) ���û������,�����û�PK
 	 * @throws WfmServiceException
 	 */
 	public Map<String,String> getDeptAgent(String[] pk_user,String pk_dept) throws WfmServiceException;
 	
 	/**
 	 * ����PK��ô���������VO
 	 * @param pk
 	 * @return
 	 * @throws WfmServiceException
 	 */
 	public WfmUseragentVO getAgentByPK(String pk) throws WfmServiceException;
 	
 	/**
 	 * ��ô�����Ĳ����ʹ���������VO
 	 * @return
 	 * @throws WfmServiceException
 	 */
 	public WfmUseragentVO[] getBeActiveDeptAgents() throws WfmServiceException;
 	
 	/**
 	 * ��ô�����ĵ����ʹ���������VO
 	 * @return
 	 * @throws WfmServiceException
 	 */
 	public WfmFlowAgentVO[] getBeActiveFrmAgents() throws WfmServiceException;
 	
 	/**
 	 * ���ݵ������ͻ���û����õĴ�����PK
 	 * @param pk_user
 	 * @param frmtype
 	 * @return
 	 * @throws WfmServiceException
 	 */
 	public String getFrmAgent(String pk_user,String frmtype) throws WfmServiceException;
 	
}
