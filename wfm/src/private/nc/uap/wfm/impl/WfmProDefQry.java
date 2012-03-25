package nc.uap.wfm.impl;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import nc.bs.dao.DAOException;
import nc.bs.logging.Logger;
import nc.jdbc.framework.SQLParameter;
import nc.jdbc.framework.processor.ArrayListProcessor;
import nc.jdbc.framework.processor.BeanListProcessor;
import nc.jdbc.framework.processor.ColumnProcessor;
import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.util.CpbServiceFacility;
import nc.uap.cpb.org.vos.CpRoleVO;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.itf.IWfmProDefQry;
import nc.uap.wfm.model.MyWork;
import nc.uap.wfm.model.Task;
import nc.uap.wfm.vo.WfmProdefVO;
import nc.vo.pub.SuperVO;
/**
 * 2010-12-17 ÏÂÎç04:24:05 limingf
 */
public class WfmProDefQry implements IWfmProDefQry {
	@SuppressWarnings("unchecked") public WfmProdefVO[] getWfmProdefVOByName(String name) throws WfmServiceException {
		PtBaseDAO baseDAO = new PtBaseDAO();
		String sql = "select * from wfm_prodef p where p.name = ?";
		SQLParameter parameter = new SQLParameter();
		parameter.addParam(name);
		try {
			List<WfmProdefVO> list = (List<WfmProdefVO>) baseDAO.executeQuery(sql, parameter, new BeanListProcessor(WfmProdefVO.class));
			if (list == null || list.size() < 1)
				return new WfmProdefVO[] {};
			return (WfmProdefVO[]) list.toArray(new WfmProdefVO[list.size()]);
		} catch (DAOException e) {
			Logger.error(e.getMessage(), e);
			throw new WfmServiceException(e);
		}
	}
	@SuppressWarnings("unchecked") public WfmProdefVO[] getWfmProdefVOByFrmdefpk(String frmdefpk) throws WfmServiceException {
		PtBaseDAO baseDAO = new PtBaseDAO();
		String sql = "select * from wfm_prodef p where p.pk_startfrm = ?";
		SQLParameter parameter = new SQLParameter();
		parameter.addParam(frmdefpk);
		try {
			List<WfmProdefVO> list = (List<WfmProdefVO>) baseDAO.executeQuery(sql, parameter, new BeanListProcessor(WfmProdefVO.class));
			if (list == null || list.size() < 1)
				return new WfmProdefVO[] {};
			return (WfmProdefVO[]) list.toArray(new WfmProdefVO[list.size()]);
		} catch (DAOException e) {
			Logger.error(e.getMessage(), e);
			throw new WfmServiceException(e);
		}
	}
	@SuppressWarnings("unchecked") public WfmProdefVO getWfmProdefVOByProDefPk(String proDefPk) throws WfmServiceException {
		PtBaseDAO baseDAO = new PtBaseDAO();
		String sql = "select * from wfm_prodef p where p.pk_prodef = ?";
		SQLParameter parameter = new SQLParameter();
		parameter.addParam(proDefPk);
		try {
			List<WfmProdefVO> list = (List<WfmProdefVO>) baseDAO.executeQuery(sql, parameter, new BeanListProcessor(WfmProdefVO.class));
			if (list == null || list.size() == 0) {
				return null;
			}
			return list.get(0);
		} catch (DAOException e) {
			Logger.error(e.getMessage(), e);
			throw new WfmServiceException(e);
		}
	}
	@SuppressWarnings("unchecked") public WfmProdefVO getWfmProdefVOByFrmDefPk(String frmDefPk) throws WfmServiceException {
		String sql = "select a.* from wfm_prodef a where a.pk_startfrm='" + frmDefPk + "' order by a.version desc";
		PtBaseDAO dao = new PtBaseDAO();
		try {
			List<WfmProdefVO> list = (List<WfmProdefVO>) dao.executeQuery(sql, new BeanListProcessor(WfmProdefVO.class));
			return list != null && list.size() != 0 ? list.get(0) : null;
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new WfmServiceException(e.getMessage());
		}
	}
	public WfmProdefVO[] getWfmProdefVOsByWhere(String where) throws WfmServiceException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			WfmProdefVO[] vos = (WfmProdefVO[]) dao.queryByCondition(WfmProdefVO.class, where);
			return vos;
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new WfmServiceException(e.getMessage());
		}
	}
	public WfmProdefVO getWfmProdefVOByProDefPk1(String proDefPk) throws WfmServiceException {
		String where = "pk_prodef='" + proDefPk + "'";
		WfmProdefVO[] vos = this.getWfmProdefVOsByWhere(where);
		return (vos != null && vos.length == 1) ? vos[0] : null;
	}
	public WfmProdefVO[] getWfmProdefVOByProDefPks(String[] proDefPks) throws WfmServiceException {
		if (proDefPks == null || proDefPks.length < 1)
			return null;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < proDefPks.length; i++) {
			sb.append("'").append(proDefPks[i]).append("'");
			if (i != proDefPks.length - 1)
				sb.append(",");
		}
		String where = "pk_prodef in(" + sb.toString() + ")";
		WfmProdefVO[] vos = this.getWfmProdefVOsByWhere(where);
		return vos;
	}
	@Override public WfmProdefVO[] getAllProDef() throws WfmServiceException {
		WfmProdefVO[] vos = this.getWfmProdefVOsByWhere("");
		return vos;
	}
	@SuppressWarnings("unchecked") @Override public WfmProdefVO[] getMyPrtptProDef(String userPk) throws WfmServiceException {
		String sql = "SELECT * FROM wfm_prodef WHERE pk_prodef IN (SELECT DISTINCT pk_prodef FROM wfm_task WHERE pk_owner='" + userPk + "' or pk_agenter='" + userPk + "')";
		PtBaseDAO dao = new PtBaseDAO();
		List<WfmProdefVO> list = null;
		try {
			list = (List<WfmProdefVO>) dao.executeQuery(sql, new BeanListProcessor(WfmProdefVO.class));
			return list.toArray(new WfmProdefVO[0]);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new WfmServiceException(e.getMessage());
		}
	}
	@SuppressWarnings("unchecked") @Override public WfmProdefVO[] getMyStartptProDef(String userPk) throws WfmServiceException {
		String sql = "SELECT * FROM wfm_prodef WHERE pk_prodef IN (SELECT DISTINCT pk_prodef FROM wfm_proins WHERE pk_starter='" + userPk + "')";
		PtBaseDAO dao = new PtBaseDAO();
		List<WfmProdefVO> list = null;
		try {
			list = (List<WfmProdefVO>) dao.executeQuery(sql, new BeanListProcessor(WfmProdefVO.class));
			return list.toArray(new WfmProdefVO[0]);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new WfmServiceException(e.getMessage());
		}
	}
	@SuppressWarnings("unchecked") @Override public WfmProdefVO[] getMyWatchptProDef(String userPk) throws WfmServiceException {
		List<String> list = new ArrayList<String>();
		CpRoleVO[] roles = null;
		try {
			roles = CpbServiceFacility.getCpRoleQry().getUserRoles(userPk, true);
			if (roles != null && roles.length > 0) {
				for (int i = 0; i < roles.length; i++)
					list.add(roles[i].getPk_role());
			}
		} catch (CpbBusinessException e) {
			LfwLogger.error(e.getMessage(), e);
		}
		String[] rolePks = list.toArray(new String[0]);
		if (rolePks == null || rolePks.length == 0) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < rolePks.length; i++) {
			if (i == rolePks.length - 1) {
				sb.append("'" + rolePks[i] + "'");
			} else {
				sb.append("'" + rolePks[i] + "',");
			}
		}
		String sql = "SELECT * FROM wfm_prodef where watchrolepks in(" + sb.toString() + ")";
		PtBaseDAO dao = new PtBaseDAO();
		List<WfmProdefVO> list1 = null;
		try {
			list1 = (List<WfmProdefVO>) dao.executeQuery(sql, new BeanListProcessor(WfmProdefVO.class));
			return list1.toArray(new WfmProdefVO[0]);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new WfmServiceException(e.getMessage());
		}
	}
	@SuppressWarnings( { "unchecked", "serial" }) @Override public List<MyWork> getMyUndoWork(String userPk) throws WfmServiceException {
		String sql = "";
		sql = "select a.pk_prodef,a.name,a.tasknum  from (SELECT a.pk_prodef, a.name, b.tasknum,a.pk_startfrm " + "  FROM (SELECT a.pk_prodef, a.name,a.pk_startfrm "
				+ "  FROM wfm_prodef a WHERE a.pk_prodef IN (SELECT DISTINCT b.pk_prodef FROM wfm_task b " + "  WHERE b.pk_owner = '"
				+ userPk
				+ "' or b.pk_agenter = '"
				+ userPk
				+ "')) a LEFT JOIN (SELECT count(*) AS tasknum, pk_prodef "
				+ "  FROM wfm_task a "
				+ "  WHERE (a.pk_owner ='"
				+ userPk
				+ "' or a.pk_agenter = "
				+ " '"
				+ userPk
				+ "') AND  "
				+ "  a.isnotexe = 'N' and a.state in  ('"
				+ Task.State_Run
				+ "', '"
				+ Task.State_Plmnt
				+ "','"
				+ Task.State_BeforeAddSignSend
				+ "', '"
				+ Task.State_BeforeAddSignPlmnt
				+ "', '"
				+ Task.State_BeforeAddSignCmplt
				+ "') and a.priority = '0' GROUP BY a.pk_prodef) b ON a.pk_prodef = b.pk_prodef)a left join pdb_formdefinition b "
				+ " on a.pk_startfrm=b.pk_formdefinition order by b.orderstr ";
		PtBaseDAO dao = new PtBaseDAO();
		try {
			List<MyWork> list = (List<MyWork>) dao.executeQuery(sql, new ArrayListProcessor() {
				public Object processResultSet(ResultSet rs) throws SQLException {
					List<MyWork> result = new ArrayList<MyWork>();
					while (rs.next()) {
						result.add(toMyWork(rs));
					}
					return result;
				}
				public MyWork toMyWork(ResultSet rs) throws SQLException {
					String proDefPk = rs.getString("pk_prodef");
					String proDefName = rs.getString("name");
					String count = String.valueOf(rs.getInt("tasknum"));
					MyWork vo = new MyWork();
					vo.setName(proDefName);
					vo.setRecordnum(count);
					vo.setPk_prodef(proDefPk);
					return vo;
				}
			});
			return list;
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new WfmServiceException(e.getMessage());
		}
	}
	@SuppressWarnings( { "unchecked", "serial" }) @Override public List<MyWork> getMyUnreadWork(String userPk) throws WfmServiceException {
		String sql = "";
		sql = "select a.pk_prodef,a.name,a.tasknum  from (SELECT a.pk_prodef, a.name, b.tasknum,a.pk_startfrm " + "  FROM (SELECT a.pk_prodef, a.name,a.pk_startfrm "
				+ "  FROM wfm_prodef a WHERE a.pk_prodef IN (SELECT DISTINCT b.pk_prodef FROM wfm_task b " + "  WHERE b.pk_owner = '" + userPk + "' or b.pk_agenter = '" + userPk
				+ "')) a LEFT JOIN (SELECT count(*) AS tasknum, pk_prodef " + "  FROM wfm_task a " + "  WHERE (a.pk_owner ='" + userPk + "' or a.pk_agenter = " + " '" + userPk + "') AND  "
				+ "  a.isnotexe = 'N' and a.state = '" + Task.State_UnRead + "' and a.priority = '0' GROUP BY a.pk_prodef) b ON a.pk_prodef = b.pk_prodef)a left join pdb_formdefinition b "
				+ " on a.pk_startfrm=b.pk_formdefinition order by b.orderstr ";
		PtBaseDAO dao = new PtBaseDAO();
		try {
			List<MyWork> list = (List<MyWork>) dao.executeQuery(sql, new ArrayListProcessor() {
				public Object processResultSet(ResultSet rs) throws SQLException {
					List<MyWork> result = new ArrayList<MyWork>();
					while (rs.next()) {
						result.add(toMyWork(rs));
					}
					return result;
				}
				public MyWork toMyWork(ResultSet rs) throws SQLException {
					String proDefPk = rs.getString("pk_prodef");
					String proDefName = rs.getString("name");
					String count = String.valueOf(rs.getInt("tasknum"));
					MyWork vo = new MyWork();
					vo.setName(proDefName);
					vo.setRecordnum(count);
					vo.setPk_prodef(proDefPk);
					return vo;
				}
			});
			return list;
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new WfmServiceException(e.getMessage());
		}
	}
	@Override public int getMyUndoWorkCount(String userPk) throws WfmServiceException {
		String sql = "";
		sql = "SELECT count(*) AS tasknum FROM wfm_task a  WHERE (a.pk_owner = '" + userPk + "' or   a.pk_agenter = '" + userPk + "')  AND a.isnotexe = 'N' " + " and a.state in ('" + Task.State_Run
				+ "', '" + Task.State_BeforeAddSignPlmnt + "', '" + Task.State_BeforeAddSignSend + "','" + Task.State_BeforeAddSignPlmnt + "','" + Task.State_BeforeAddSignCmplt + "') and a.priority = '0'";
		PtBaseDAO dao = new PtBaseDAO();
		try {
			return (Integer) dao.executeQuery(sql, new ColumnProcessor());
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new WfmServiceException(e.getMessage());
		}
	}
	@Override public int getMyUnreadWorkCount(String userPk) throws WfmServiceException {
		String sql = "";
		sql = "SELECT count(*) AS tasknum FROM wfm_task a  WHERE (a.pk_owner = '" + userPk + "' or   a.pk_agenter = '" + userPk + "')  AND a.isnotexe = 'N'  and a.state in ('" + Task.State_UnRead
				+ "') and a.priority = '0'";
		PtBaseDAO dao = new PtBaseDAO();
		try {
			return (Integer) dao.executeQuery(sql, new ColumnProcessor());
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new WfmServiceException(e.getMessage());
		}
	}
	public WfmProdefVO getProDefVOByFrmDefPk(String frmDefPk) throws WfmServiceException {
		PtBaseDAO dao = new PtBaseDAO();
		SuperVO[] superVos = null;
		try {
			superVos = dao.queryByCondition(WfmProdefVO.class, "pk_startfrm='" + frmDefPk + "'");
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
		if (superVos == null || superVos.length == 0) {
			return null;
		}
		return (WfmProdefVO) superVos[0];
	}
	public WfmProdefVO getProDefVOByProDefPk(String proDefPk) throws WfmServiceException {
		PtBaseDAO dao = new PtBaseDAO();
		SuperVO[] superVos = null;
		try {
			superVos = dao.queryByCondition(WfmProdefVO.class, "pk_prodef='" + proDefPk + "'");
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
		if (superVos == null || superVos.length == 0) {
			return null;
		}
		return (WfmProdefVO) superVos[0];
	}
	public WfmProdefVO[] getProDefVOByProDefPks(String[] proDefPks) throws WfmServiceException {
		// TODO Auto-generated method stub
		return null;
	}
	public WfmProdefVO[] getProdefVOByName(String name) throws WfmServiceException {
		// TODO Auto-generated method stub
		return null;
	}
	@SuppressWarnings("unchecked") public WfmProdefVO[] getProDefByFlwTypePk(String flwTypePk) throws WfmServiceException {
		PtBaseDAO baseDAO = new PtBaseDAO();
		String sql = "select * from wfm_prodef p where p.flwtype = ?";
		SQLParameter parameter = new SQLParameter();
		parameter.addParam(flwTypePk);
		try {
			List<WfmProdefVO> list = (List<WfmProdefVO>) baseDAO.executeQuery(sql, parameter, new BeanListProcessor(WfmProdefVO.class));
			if (list == null || list.size() == 0) {
				return null;
			}
			return list.toArray(new WfmProdefVO[0]);
		} catch (DAOException e) {
			Logger.error(e.getMessage(), e);
			throw new WfmServiceException(e);
		}
	}
}
