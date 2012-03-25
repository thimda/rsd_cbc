package nc.uap.cpb.org.impl;
import java.util.List;

import nc.bs.dao.DAOException;
import nc.jdbc.framework.SQLParameter;
import nc.jdbc.framework.processor.BeanListProcessor;
import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.itf.ICpUserGroupQry;
import nc.uap.cpb.org.util.CpbUtil;
import nc.uap.cpb.org.vos.CpResponsibilityVO;
import nc.uap.cpb.org.vos.CpUserGroupVO;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
public class CpUserGroupQry implements ICpUserGroupQry {
	public CpUserGroupVO[] getAllUserGroupExcp(String pk_usergroup) throws CpbBusinessException {
		String sql = "select * from cp_usergroup p where p.pk_usergroup != ?";
		SQLParameter parameter = new SQLParameter();
		parameter.addParam(pk_usergroup);
		List<CpUserGroupVO> list = this.queryList(sql, parameter);
		return CpbUtil.returnArray(list);
	}
	public CpUserGroupVO[] getAllUserGroups() throws CpbBusinessException {
		String sql = "select * from cp_usergroup";
		List<CpUserGroupVO> list = this.queryList(sql, null);
		return CpbUtil.returnArray(list);
	}
	public CpUserGroupVO getUserGroupByGroupCode(String pk_org, String groupcode) throws CpbBusinessException {
		String sql = "select * from cp_usergroup p where p.pk_group = ? and p.group_code = ?";
		SQLParameter parameter = new SQLParameter();
		parameter.addParam(pk_org);
		parameter.addParam(groupcode);
		List<CpUserGroupVO> list = this.queryList(sql, parameter);
		return CpbUtil.returnValue(list);
	}
	public CpUserGroupVO getUserGroupByGroupName(String groupname) throws CpbBusinessException {
		String sql = "select * from cp_usergroup p where p.group_name = ?";
		SQLParameter parameter = new SQLParameter();
		parameter.addParam(groupname);
		List<CpUserGroupVO> list = this.queryList(sql, parameter);
		return CpbUtil.returnValue(list);
	}
	public CpUserGroupVO getUserGroupByPk(String pk_usergroup) throws CpbBusinessException {
		String sql = "select * from cp_usergroup p where p.pk_usergroup = ?";
		SQLParameter parameter = new SQLParameter();
		parameter.addParam(pk_usergroup);
		List<CpUserGroupVO> list = this.queryList(sql, null);
		return CpbUtil.returnValue(list);
	}
	public CpUserGroupVO[] getUserGroupVos(String where) throws CpbBusinessException {
		PtBaseDAO baseDAO = new PtBaseDAO();
		String sql = "select * from cp_usergroup  where "+where;
		List<CpUserGroupVO> list = null;
		try {
			list = (List<CpUserGroupVO>) baseDAO.executeQuery(sql, new BeanListProcessor(CpUserGroupVO.class));
			if (list == null || list.size() < 1)
				return new CpUserGroupVO[] {};
			return list.toArray(new CpUserGroupVO[list.size()]);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new CpbBusinessException(e);
		}
	}
	public CpUserGroupVO[] getUserGroupByGroupPk(String pk_group) throws CpbBusinessException {
		String sql = "select * from cp_usergroup p where p.pk_group = ?";
		SQLParameter parameter = new SQLParameter();
		parameter.addParam(pk_group);
		List<CpUserGroupVO> list = this.queryList(sql, parameter);
		return CpbUtil.returnArray(list);
	}
	public CpUserGroupVO[] getUserGroupByParent(String pk_parent) throws CpbBusinessException{
		String sql = "select * from cp_usergroup p where p.pk_parent = ?";
		SQLParameter parameter = new SQLParameter();
		parameter.addParam(pk_parent);
		PtBaseDAO dao = new PtBaseDAO();
		List<CpUserGroupVO> list = null;
		try {
			list = (List<CpUserGroupVO>) dao.executeQuery(sql, parameter, new BeanListProcessor(CpUserGroupVO.class));
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e);
		}
		if (list == null || list.size() == 0) {
			return new CpUserGroupVO[]{};		
		}
		return list.toArray(new CpUserGroupVO[0]);
	}
	public CpUserGroupVO[] getUserGroupByPkS(String[] pk_usergroups) throws CpbBusinessException {
		if (pk_usergroups == null || pk_usergroups.length < 1) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < pk_usergroups.length; i++) {
			sb.append("'").append(pk_usergroups[i]).append("'");
			if (i != pk_usergroups.length - 1)
				sb.append(",");
		}
		String sql = "select * from cp_usergroup p where p.pk_usergroup in(" + sb.toString() + ") ";
		List<CpUserGroupVO> list = this.queryList(sql, null);
		return CpbUtil.returnArray(list);
	}
	public CpUserGroupVO getUserGroupByGroupCode(String groupcode) throws CpbBusinessException {
		String sql = "select * from cp_usergroup p where p.group_code = ?";
		SQLParameter parameter = new SQLParameter();
		parameter.addParam(groupcode);
		List<CpUserGroupVO> list = this.queryList(sql, parameter);
		return CpbUtil.returnValue(list);
	}
	private List<CpUserGroupVO> queryList(String sql, SQLParameter parameter) throws CpbBusinessException {
		return CpbUtil.queryList(sql, parameter, CpUserGroupVO.class);
	}
}
