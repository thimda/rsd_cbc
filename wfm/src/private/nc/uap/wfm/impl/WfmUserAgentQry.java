package nc.uap.wfm.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import nc.bs.dao.DAOException;
import nc.jdbc.framework.processor.ArrayListProcessor;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.itf.IWfmUserAgentQryService;
import nc.uap.wfm.vo.WfmFlowAgentVO;
import nc.uap.wfm.vo.WfmUseragentVO;
import nc.vo.pub.lang.UFDateTime;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

/**
 * 用户代理查询服务实现
 *
 */
public class WfmUserAgentQry implements IWfmUserAgentQryService {

	@Override
	public String getDeptAgent(String pk_user, String pk_dept) throws WfmServiceException {
		return getDeptAgent(new String[]{pk_user}, pk_dept).get(pk_user);
	}

	@SuppressWarnings("unchecked") @Override
	public Map<String, String> getDeptAgent(String[] pk_user, String pk_dept) throws WfmServiceException {
		String sql = "SELECT pk_user , pk_agenter FROM wfm_useragent WHERE useflag = 'Y' AND pk_user IN ('" + StringUtils.join(pk_user, "','") + "')  AND pk_depts LIKE '%" + pk_dept + "%'";
		PtBaseDAO dao = new PtBaseDAO();
		Map<String ,String> agent = new LinkedHashMap<String ,String>();
		List<String> pk_clone = new ArrayList<String>();
		CollectionUtils.addAll(pk_clone, pk_user);
		try {
			List list = (List)dao.executeQuery(sql, new ArrayListProcessor());
			if(!(list == null || list.isEmpty())){
				for(Object o : list){
					Object[] arr = (Object[]) o;
					String key = (String)arr[0] ;
					agent.put(key, (String)arr[1]);
					pk_clone.remove(key);
				}
			}
			if(!(pk_clone == null || pk_clone.isEmpty())){
				for(String pk : pk_clone){
					agent.put(pk, pk);
				}
			}
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(),e);
			throw new WfmServiceException("查询用户设置的部门代理人出现错误:" + e.getMessage());
		}
		return agent;
	}

	@Override
	public WfmUseragentVO getAgentByPK(String pk) throws WfmServiceException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			return (WfmUseragentVO)dao.retrieveByPK(WfmUseragentVO.class, pk);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(),e);
			throw new WfmServiceException(e.getMessage());
		}
	}

	@SuppressWarnings("unchecked") @Override
	public WfmUseragentVO[] getBeActiveDeptAgents() throws WfmServiceException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			String now = new UFDateTime().toString();
			String condition = 	" (ISNULL(useflag, 'N') = 'N') AND startdate <= '"+ now +"' AND ((ISNULL(stopdate, '~') = '~') OR stopdate > '"+ now +"')";
			List list = (List) dao.retrieveByClause(WfmUseragentVO.class,  condition);
			if(!(list == null || list.isEmpty())){
				return (WfmUseragentVO[]) list.toArray(new WfmUseragentVO[0]);
			}
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(),e);
			throw new WfmServiceException(e.getMessage());
		}
		return new WfmUseragentVO[0];
	}

	
	
	
	@SuppressWarnings("unchecked") @Override
	public String getFrmAgent(String pk_user, String frmtype) throws WfmServiceException {
		String agent = pk_user;
		PtBaseDAO dao = new PtBaseDAO();
		try {
			List list = (List)dao.retrieveByClause(WfmFlowAgentVO.class, "useflag = 'Y' AND pk_user IN ('" + (pk_user) + "')  AND ( pk_frmdef LIKE '%" + frmtype + "%'  or isnull(pk_frmdef,'~') = '~' )");
			if(!(list == null || list.isEmpty())){
				agent = ((WfmFlowAgentVO)list.get(0)).getPk_agent();
			}
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(),e);
			throw new WfmServiceException(e.getMessage());
		}
		return agent;
	}

	@SuppressWarnings("unchecked") @Override
	public WfmFlowAgentVO[] getBeActiveFrmAgents() throws WfmServiceException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			String now = new UFDateTime().toString();
			String condition = 	"  (ISNULL(useflag, 'N') = 'N') AND startdate <=  '"+ now +"' AND ((ISNULL(stopdate, '~') = '~') OR stopdate > '"+ now +"')";
			List list = (List) dao.retrieveByClause(WfmFlowAgentVO.class,  condition);
			if(!(list == null || list.isEmpty())){
				return (WfmFlowAgentVO[]) list.toArray(new WfmFlowAgentVO[0]);
			}
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(),e);
			throw new WfmServiceException(e.getMessage());
		}
		return new WfmFlowAgentVO[0];
	}
}
