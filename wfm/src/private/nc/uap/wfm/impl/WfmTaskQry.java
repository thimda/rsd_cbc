package nc.uap.wfm.impl;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import nc.bs.dao.DAOException;
import nc.jdbc.framework.processor.ArrayListProcessor;
import nc.jdbc.framework.processor.BeanListProcessor;
import nc.jdbc.framework.processor.ColumnProcessor;
import nc.jdbc.framework.processor.ProcessorUtils;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.lfw.core.crud.CRUDHelper;
import nc.uap.lfw.core.data.PaginationInfo;
import nc.uap.lfw.core.exception.LfwBusinessException;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.wfm.builder.BeanConvert;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.itf.IWfmTaskQry;
import nc.uap.wfm.model.Task;
import nc.uap.wfm.vo.WfmTaskVO;
import org.apache.commons.lang.StringUtils;
public class WfmTaskQry implements IWfmTaskQry {
	public Task getTaskByPk(String taskPk) throws WfmServiceException {
		WfmTaskVO taskVO = this.getTaskVOByPk(taskPk);
		if (taskVO == null) {
			return null;
		}
		Task task = BeanConvert.toTask(taskVO);
		if (StringUtils.isNotBlank(taskVO.getPk_parent())) {
			WfmTaskVO parentVO = this.getTaskVOByPk(taskVO.getPk_parent());
			if (parentVO != null) {
				Task parent = BeanConvert.toTask(parentVO);
				task.setParent(parent);
			}
		}
		Set<Task> subTask = this.getSubTaskByParentPk(taskPk);
		task.setSubTasks(subTask);
		return task;
	}
	public WfmTaskVO getTaskVOByPk(String taskPk) throws WfmServiceException {
		if (taskPk == null || taskPk.length() == 0) {
			return null;
		}
		WfmTaskVO taskVO = null;
		WfmTaskVO[] vos = this.getTaskVOsByWhere("pk_task='" + taskPk + "'");
		if (vos != null && vos.length == 1) {
			taskVO = vos[0];
		}
		return taskVO;
	}
	public Set<Task> getSubTaskByParentPk(String parentPk) throws WfmServiceException {
		if (parentPk == null || parentPk.length() == 0) {
			return null;
		}
		WfmTaskVO[] vos = this.getTaskVOsByWhere("pk_parent='" + parentPk + "'");
		Set<Task> subTask = null;
		if (vos != null && vos.length != 0) {
			subTask = new HashSet<Task>();
			for (int i = 0; i < vos.length; i++) {
				Task temp = BeanConvert.toTask(vos[i]);
				subTask.add(temp);
			}
		}
		return subTask;
	}
	public Set<Task> getTasksByHumActInsPk(String humActInsPk) throws WfmServiceException {
		if (humActInsPk == null || humActInsPk.length() == 0) {
			return null;
		}
		Set<Task> tasks = null;
		WfmTaskVO[] vos = this.getTaskVOsByWhere("pk_humactins='" + humActInsPk + "'");
		if (vos != null && vos.length != 0) {
			tasks = new HashSet<Task>();
			for (int i = 0; i < vos.length; i++) {
				Task tmpTask = this.getTaskByPk(vos[i].getPk_task());
				tasks.add(tmpTask);
			}
		}
		return tasks;
	}
	@SuppressWarnings("unchecked") @Override public WfmTaskVO getLasterUndneTaskByProInsPk(String proInsPk) throws WfmServiceException {
		if (proInsPk == null || proInsPk.length() == 0) {
			return null;
		}
		String sql = "SELECT * FROM wfm_task WHERE startdate =(SELECT max(startdate) FROM wfm_task WHERE pk_proins='" + proInsPk + "') AND pk_proins='" + proInsPk + "'";
		PtBaseDAO dao = new PtBaseDAO();
		try {
			List<WfmTaskVO> vos = (List<WfmTaskVO>) dao.executeQuery(sql, new BeanListProcessor(WfmTaskVO.class));
			if (vos == null || vos.size() == 0) {
				return null;
			}
			return vos.get(0);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new WfmServiceException(e.getMessage());
		}
	}
	@SuppressWarnings("unchecked") @Override public WfmTaskVO getLasterCmpltTaskByProInsPk(String proInsPk) throws WfmServiceException {
		if (proInsPk == null || proInsPk.length() == 0) {
			return null;
		}
		String sql = "SELECT * FROM wfm_task WHERE enddate =(SELECT max(enddate) FROM wfm_task WHERE pk_proins='" + proInsPk + "') AND pk_proins='" + proInsPk + "'";
		PtBaseDAO dao = new PtBaseDAO();
		try {
			List<WfmTaskVO> vos = (List<WfmTaskVO>) dao.executeQuery(sql, new BeanListProcessor(WfmTaskVO.class));
			if (vos == null || vos.size() == 0) {
				return null;
			}
			return vos.get(0);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new WfmServiceException(e.getMessage());
		}
	}
	@SuppressWarnings( { "unchecked", "serial" }) @Override public List<Map<String, String>> getMyScratchpadByTaskPk(String taskPk) throws WfmServiceException {
		PtBaseDAO dao = new PtBaseDAO();
		String sql = "select a.startdate, a.scratchpad, b.username from (select a.pk_creater, a.startdate, a.scratchpad "
				+ " from wfm_task a join (select a.pk_prodef,a.prodef_id,a.port_id,a.pk_proins, a.pk_owner  from wfm_task a where pk_task =" + " '" + taskPk
				+ "') b on a.pk_prodef = b.pk_prodef and a.prodef_id = b.prodef_id and a.port_id = b.port_id and a.pk_proins = b.pk_proins and a.pk_owner = b.pk_owner"
				+ " and isnull(a.scratchpad, '~') <> '~') a left join pt_user b on a.pk_creater = b.pk_user order by a.startdate";
		try {
			List list = (List) dao.executeQuery(sql, new ArrayListProcessor() {
				public Object processResultSet(ResultSet rs) throws SQLException {
					List result = new ArrayList();
					while (rs.next()) {
						result.add(toMap(rs));
					}
					return result;
				}
				public Map toMap(ResultSet rs) throws SQLException {
					return ProcessorUtils.toMap(rs);
				}
			});
			return list;
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new WfmServiceException(e.getMessage());
		}
	}
	public WfmTaskVO[] getAddSignTaskByTaskPk(String taskPk) throws WfmServiceException {
		String where = "pk_parent='" + taskPk + "' and createtype='" + Task.CreateType_BeforeAddSign + "'";
		return this.getTaskVOsByWhere(where);
	}
	public WfmTaskVO[] getAddSignTaskByTaskPkAndTimes(String taskPk, int times) throws WfmServiceException {
		String where = "pk_parent='" + taskPk + "' and createtype='" + Task.CreateType_BeforeAddSign + "' and addsigntimes='" + String.valueOf(times) + "'";
		return this.getTaskVOsByWhere(where);
	}
	public WfmTaskVO[] getAddSignTaskByHumActInsPk(String pk_humactins) throws WfmServiceException {
		String where = "pk_humactins='" + pk_humactins + "' and createtype='" + Task.CreateType_BeforeAddSign + "'";
		return this.getTaskVOsByWhere(where);
	}
	public WfmTaskVO[] getAddSignCmpltTaskByTaskPk(String taskPk) throws WfmServiceException {
		String where = "pk_parent='" + taskPk + "' and isnotexe='Y' and createtype='" + Task.CreateType_BeforeAddSign + "'";
		return this.getTaskVOsByWhere(where);
	}
	public WfmTaskVO[] getDeliverTaskVosByRootProInsPk(String rootProInsPk) throws WfmServiceException {
		return this.getTaskVOsByWhere("pk_rootproins ='" + rootProInsPk + "'  and createtype='" + Task.CreateType_Deliver + "'order by startdate desc");
	}
	public WfmTaskVO[] getMyUnDeliverTasks(String userPk, String proDefPk, PaginationInfo pinfo) throws WfmServiceException {
		return this.getMyUnDeliverTasksByDate(userPk, proDefPk, null, null, pinfo);
	}
	public WfmTaskVO[] getMyDeliveredTasks(String userPk, String proDefPk, PaginationInfo pinfo) throws WfmServiceException {
		return this.getMyDeliveredTasksByDate(userPk, proDefPk, null, null, pinfo);
	}
	public WfmTaskVO[] getMyUnDneTasks(String userPk, String proDefPk, PaginationInfo pinfo) throws WfmServiceException {
		return this.getMyUndneTasksByDate(userPk, proDefPk, null, null, pinfo);
	}
	public WfmTaskVO[] getMyCmpltTasks(String userPk, String proDefPk, PaginationInfo pinfo) throws WfmServiceException {
		return this.getMyCmpltTasksByDate(userPk, proDefPk, null, null, pinfo);
	}
	public WfmTaskVO[] getMyEndedTasks(String userPk, String proDefPk, PaginationInfo pinfo) throws WfmServiceException {
		return this.getMyEndedTasksByDate(userPk, proDefPk, null, null, pinfo);
	}
	public WfmTaskVO[] getTasksConAddSignStopByRootProInsPk(String proInsPk) throws WfmServiceException {
		return this.getTaskVOsByWhere("pk_rootproins ='" + proInsPk + "'  order by startdate,signdate ");
	}
	public WfmTaskVO[] getTasksNotAddSignStopByRootProInsPk(String proInsPk) throws WfmServiceException {
		return this.getTaskVOsByWhere("pk_rootproins ='" + proInsPk + "' and state not in('" + Task.State_BeforeAddSignStop + "') order by startdate,signdate");
	}
	public WfmTaskVO[] getMyUndneTasksAndUnDeliverByDate(String userPk, String proDefPk, String startDate, String endDate, PaginationInfo pinfo) throws WfmServiceException {
		String sql = "select * from wfm_task where ((pk_owner='" + userPk + "' and pk_agenter = '~') or(pk_agenter='" + userPk + "' and pk_owner<>'" + userPk + "')) ";
		sql += "and isnotexe='N'  and priority='0' ";
		sql += "and state in('" + Task.State_Run + "','" + Task.State_Plmnt + "','" + Task.State_BeforeAddSignSend + "','" + Task.State_BeforeAddSignPlmnt + "','" + Task.State_BeforeAddSignCmplt + "','"
				+ Task.State_UnRead + "')";
		if (proDefPk != null && proDefPk.length() != 0) {
			sql = sql + " and pk_prodef='" + proDefPk + "'";
		}
		if (startDate != null && endDate != null) {
			sql = sql + " and  startdate between'" + startDate + " 00:00:00' and '" + endDate + " 24:00:00' ";
		}
		String orderBy = "order by startdate desc";
		return this.getPaginationTask(sql, pinfo, orderBy);
	}
	public WfmTaskVO[] getMyUndneTasksByDate(String userPk, String proDefPk, String startDate, String endDate, PaginationInfo pinfo) throws WfmServiceException {
		String sql = "select * from wfm_task where ((pk_owner='" + userPk + "' and pk_agenter = '~') or(pk_agenter='" + userPk + "' and pk_owner<>'" + userPk + "')) ";
		sql += "and isnotexe='N'  and priority='0' ";
		sql += "and state in('" + Task.State_Run + "','" + Task.State_Plmnt + "','" + Task.State_BeforeAddSignSend + "','" + Task.State_BeforeAddSignPlmnt + "','" + Task.State_BeforeAddSignCmplt + "') ";
		if (proDefPk != null && proDefPk.length() != 0) {
			sql = sql + " and pk_prodef='" + proDefPk + "'";
		}
		if (startDate != null && endDate != null) {
			sql = sql + " and  startdate between'" + startDate + " 00:00:00' and '" + endDate + " 24:00:00' ";
		}
		String orderBy = "order by startdate desc";
		return this.getPaginationTask(sql, pinfo, orderBy);
	}
	public WfmTaskVO[] getMyCmpltTasksByDate(String userPk, String proDefPk, String startDate, String endDate, PaginationInfo pinfo) throws WfmServiceException {
		String sql = "select * from wfm_task where pk_executer='" + userPk + "' and isnotexe='Y' and createtype not in('" + Task.CreateType_Deliver + "')";
		if (proDefPk != null && proDefPk.length() != 0) {
			sql = sql + " and pk_prodef='" + proDefPk + "' ";
		}
		if (startDate != null && endDate != null) {
			sql = sql + " and  startdate between'" + startDate + " 00:00:00' and '" + endDate + " 24:00:00' ";
		}
		String orderBy = "order by enddate desc";
		return this.getPaginationTask(sql, pinfo, orderBy);
	}
	@Override public WfmTaskVO[] getMyDeliveredTasksByDate(String userPk, String proDefPk, String startDate, String endDate, PaginationInfo pinfo) throws WfmServiceException {
		String sql = "select * from wfm_task where ((pk_owner='" + userPk + "' and pk_agenter = '~') or(pk_agenter='" + userPk + "'  and pk_owner<>'" + userPk + "')) ";
		sql += "and isnotexe='Y'  and priority='0' ";
		sql += "and state in('" + Task.State_Readed + "') ";
		if (proDefPk != null && proDefPk.length() != 0) {
			sql = sql + " and pk_prodef='" + proDefPk + "'";
		}
		if (startDate != null && endDate != null) {
			sql = sql + " and  startdate between'" + startDate + " 00:00:00' and '" + endDate + " 24:00:00' ";
		}
		String orderBy = "order by startdate desc";
		return this.getPaginationTask(sql, pinfo, orderBy);
	}
	@Override public WfmTaskVO[] getMyUnDeliverTasksByDate(String userPk, String proDefPk, String startDate, String endDate, PaginationInfo pinfo) throws WfmServiceException {
		String sql = "select * from wfm_task where ((pk_owner='" + userPk + "' and pk_agenter = '~') or(pk_agenter='" + userPk + "'  and pk_owner<>'" + userPk + "')) ";
		sql += "and isnotexe='N'  and priority='0' ";
		sql += "and state in('" + Task.State_UnRead + "') ";
		if (proDefPk != null && proDefPk.length() != 0) {
			sql = sql + " and pk_prodef='" + proDefPk + "'";
		}
		if (startDate != null && endDate != null) {
			sql = sql + " and  startdate between'" + startDate + " 00:00:00' and '" + endDate + " 24:00:00' ";
		}
		String orderBy = "order by startdate desc";
		return this.getPaginationTask(sql, pinfo, orderBy);
	}
	@Override public WfmTaskVO[] getMyEndedTasksByDate(String userPk, String proDefPk, String startDate, String endDate, PaginationInfo pinfo) throws WfmServiceException {
		String sql = "select *  from wfm_task a  " + "where pk_proins in (SELECT a.pk_proins FROM wfm_task a where a.pk_executer = '" + userPk + "')  ";
		sql += " and  a.isnotended='Y' ";
		if (proDefPk != null && proDefPk.length() != 0) {
			sql = sql + " and pk_prodef='" + proDefPk + "'";
		}
		if (startDate != null && endDate != null) {
			sql = sql + " and  startdate between'" + startDate + " 00:00:00' and '" + endDate + " 24:00:00' ";
		}
		String orderBy = " order by signdate desc";
		return this.getPaginationTask(sql, pinfo, orderBy);
	}
	@Override public WfmTaskVO[] getMyTaskByState(String userPk, String proDefPk, String startDate, String endDate, PaginationInfo pinfo, String[] state) throws WfmServiceException {
		String sql = "select * from wfm_task where ((pk_owner='" + userPk + "' and pk_agenter = '~') or(pk_agenter='" + userPk + "'  and pk_owner<>'" + userPk + "')) ";
		sql += "  and priority='0' ";
		if (state != null && state.length == 1) {
			if (state[0].equals("0")) {
				sql += "and state in('" + Task.State_Run + "','" + Task.State_Plmnt + "','" + Task.State_BeforeAddSignSend + "','" + Task.State_BeforeAddSignPlmnt + "','" + Task.State_BeforeAddSignCmplt + "','"
						+ Task.State_UnRead + "') ";
				sql += "and isnotexe='N'";
			}
			if (state[0].equals("1")) {
				sql += "and state in('" + Task.State_End + "','" + Task.State_Readed + "') ";
				sql += "and isnotexe='Y'";
			}
		}
		if (proDefPk != null && proDefPk.length() != 0) {
			sql = sql + " and pk_prodef='" + proDefPk + "'";
		}
		if (startDate != null && endDate != null) {
			sql = sql + " and  startdate between'" + startDate + " 00:00:00' and '" + endDate + " 24:00:00' ";
		}
		String orderBy = "order by startdate desc";
		return this.getPaginationTask(sql, pinfo, orderBy);
	}
	public WfmTaskVO[] getTaskVOsByWhere(String where) throws WfmServiceException {
		return this.getTaskVOsByWhere(where, null);
	}
	private WfmTaskVO[] getPaginationTask(String sql, PaginationInfo pinfo, String orderBy) throws WfmServiceException {
		try {
			WfmTaskVO[] vos = (WfmTaskVO[]) CRUDHelper.getCRUDService().queryVOs(sql, WfmTaskVO.class, pinfo, orderBy, null);
			return vos;
		} catch (LfwBusinessException e) {
			LfwLogger.error(e);
			throw new WfmServiceException(e.getMessage());
		}
	}
	@SuppressWarnings("unchecked") @Override public WfmTaskVO[] getTaskVosBySql(String sql) throws WfmServiceException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			List<WfmTaskVO> list = (List<WfmTaskVO>) dao.executeQuery(sql, new BeanListProcessor(WfmTaskVO.class));
			return list.toArray(new WfmTaskVO[0]);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
	}
	public WfmTaskVO[] getTaskVOsByWhere(String where, String priority) throws WfmServiceException {
		WfmTaskVO[] tasks = null;
		PtBaseDAO dao = new PtBaseDAO();
		try {
			if (priority == null || priority.length() == 0) {
				tasks = (WfmTaskVO[]) dao.queryByCondition(WfmTaskVO.class, where);
			} else {
				tasks = (WfmTaskVO[]) dao.queryByCondition(WfmTaskVO.class, where + " and priority='" + priority + "'");
			}
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new WfmServiceException(e.getMessage());
		}
		return tasks;
	}
	public String getProInsPkByfrmInPk(String frmInsPk) throws WfmServiceException {
		String proInsPk = "";
		PtBaseDAO dao = new PtBaseDAO();
		String sql = "SELECT DISTINCT pk_proins FROM wfm_task WHERE pk_frmins='" + frmInsPk + "'";
		try {
			proInsPk = (String) dao.executeQuery(sql, new ColumnProcessor());
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new WfmServiceException(e.getMessage());
		}
		return proInsPk;
	}
	@SuppressWarnings( { "unchecked", "serial" }) public String[] getFrmInsPksByFrmDefPkAndUserPk(String frmDefPk, String userPk) throws WfmServiceException {
		String sql = "select pk_frmins from wfm_task where ((pk_owner='" + userPk + "' and pk_agenter = '~') or(pk_agenter='" + userPk + "')) and pk_frmdef='" + frmDefPk + "'";
		PtBaseDAO dao = new PtBaseDAO();
		try {
			List<String> list = (List<String>) dao.executeQuery(sql, new ArrayListProcessor() {
				List<String> result = new ArrayList<String>();
				public Object processResultSet(ResultSet rs) throws SQLException {
					while (rs.next()) {
						String frmInsPk = rs.getString("pk_frmins");
						if (frmInsPk == null || frmInsPk.length() == 0) {
							continue;
						}
						result.add(frmInsPk);
					}
					return result;
				}
			});
			if (list == null) {
				return null;
			}
			return list.toArray(new String[0]);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new WfmServiceException(e.getMessage());
		}
	}
	@Override public Set<Task> getTasksByHumActInsPk(String humActInsPk, String createType) throws WfmServiceException {
		if (humActInsPk == null || humActInsPk.length() == 0) {
			return null;
		}
		Set<Task> tasks = null;
		WfmTaskVO[] vos = this.getTaskVOsByWhere("pk_humactins='" + humActInsPk + "' and createtype!='" + createType + "'");
		if (vos != null && vos.length != 0) {
			tasks = new HashSet<Task>();
			for (int i = 0; i < vos.length; i++) {
				Task tmpTask = this.getTaskByPk(vos[i].getPk_task());
				tasks.add(tmpTask);
			}
		}
		return tasks;
	}
	@SuppressWarnings("unchecked") @Override public WfmTaskVO getLasterSuspendedTaskByProInsPk(String proInsPk) throws WfmServiceException {
		if (proInsPk == null || proInsPk.length() == 0) {
			return null;
		}
		String sql = "SELECT * FROM wfm_task WHERE startdate =(SELECT max(startdate) FROM wfm_task WHERE pk_proins='" + proInsPk + "') AND pk_proins='" + proInsPk + "'";
		PtBaseDAO dao = new PtBaseDAO();
		try {
			List<WfmTaskVO> vos = (List<WfmTaskVO>) dao.executeQuery(sql, new BeanListProcessor(WfmTaskVO.class));
			if (vos == null || vos.size() == 0) {
				return null;
			}
			return vos.get(0);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new WfmServiceException(e.getMessage());
		}
	}
	@SuppressWarnings("unchecked") @Override public boolean getTaskPortId(String pk_frmins, String[] portid) throws WfmServiceException {
		PtBaseDAO dao = new PtBaseDAO();
		String portids = "";
		for (int i = 0; i < portid.length; i++) {
			if (i == portid.length - 1) {
				portids = portids + " '" + portid[i] + "' ";
			} else {
				portids = portids + " '" + portid[i] + "', ";
			}
		}
		String sql = "SELECT *  FROM WFM_TASK  WHERE PK_FRMINS = '" + pk_frmins + "'  AND PORT_ID IN (" + portids + ")  AND state = '" + Task.State_End + "' ";
		List<WfmTaskVO> vos;
		try {
			vos = (List<WfmTaskVO>) dao.executeQuery(sql, new BeanListProcessor(WfmTaskVO.class));
			if (vos == null || vos.size() == 0) {
				return false;
			}
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
		return true;
	}
	@SuppressWarnings("unchecked") @Override public WfmTaskVO getLasterUndneTaskByProInsPk(String proInsPk, String userPk) throws WfmServiceException {
		if (proInsPk == null || proInsPk.length() == 0) {
			return null;
		}
		String sql = "SELECT * FROM wfm_task WHERE startdate =(SELECT max(startdate) FROM wfm_task WHERE pk_proins='" + proInsPk + "') AND pk_proins='" + proInsPk + "' and pk_owner='" + userPk + "'";
		PtBaseDAO dao = new PtBaseDAO();
		try {
			List<WfmTaskVO> vos = (List<WfmTaskVO>) dao.executeQuery(sql, new BeanListProcessor(WfmTaskVO.class));
			if (vos == null || vos.size() == 0) {
				return null;
			}
			return vos.get(0);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new WfmServiceException(e.getMessage());
		}
	}
	@SuppressWarnings("unchecked") @Override public WfmTaskVO getLasterCmpltTaskByProInsPk(String proInsPk, String userPk) throws WfmServiceException {
		if (proInsPk == null || proInsPk.length() == 0) {
			return null;
		}
		String sql = "SELECT * FROM wfm_task WHERE enddate =(SELECT max(enddate) FROM wfm_task WHERE pk_proins='" + proInsPk + "') AND pk_proins='" + proInsPk + "' and pk_executer='" + userPk + "'";
		PtBaseDAO dao = new PtBaseDAO();
		try {
			List<WfmTaskVO> vos = (List<WfmTaskVO>) dao.executeQuery(sql, new BeanListProcessor(WfmTaskVO.class));
			if (vos == null || vos.size() == 0) {
				return null;
			}
			return vos.get(0);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new WfmServiceException(e.getMessage());
		}
	}
	@SuppressWarnings("unchecked") public String[] getUserPksByHumActIdAndProInsPk(String proInsPk, String humActId) throws WfmServiceException {
		if (proInsPk == null || proInsPk.length() == 0) {
			return null;
		}
		String sql = "select pk_owner from wfm_task where pk_humactins=(select pk_humactins from wfm_humactins  where ts = (select max(ts)  from wfm_humactins where humact_id = '" + humActId
				+ "' and pk_proins = '" + proInsPk + "')  and humact_id = '" + humActId + "'  and pk_proins = '" + proInsPk + "')";
		PtBaseDAO dao = new PtBaseDAO();
		try {
			List<Object> vos = (List<Object>) dao.executeQuery(sql, new ArrayListProcessor());
			if (vos == null || vos.size() == 0) {
				return null;
			}
			int length = vos.size();
			String[] str = new String[length];
			for (int i = 0; i < length; i++) {
				str[i] = ((Object[]) vos.get(i))[0].toString();
			}
			return str;
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new WfmServiceException(e.getMessage());
		}
	}
}
