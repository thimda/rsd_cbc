package nc.uap.cpb.org.impl;
import java.util.List;

import nc.bs.dao.DAOException;
import nc.jdbc.framework.SQLParameter;
import nc.jdbc.framework.processor.BeanListProcessor;
import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.itf.ICpRoleGroupQry;
import nc.uap.cpb.org.util.CpbUtil;
import nc.uap.cpb.org.vos.CpRoleGroupVO;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
public class CpRoleGroupQry implements ICpRoleGroupQry {
	public CpRoleGroupVO[] getAllRoleGroups() throws CpbBusinessException {
		String sql = "select * from cp_rolegroup";
		return CpbUtil.returnArray(CpbUtil.queryList(sql, null, CpRoleGroupVO.class));
	}
	public CpRoleGroupVO getRoleGroupByPk(String pk_rolegroup) throws CpbBusinessException {
		String sql = "select * from cp_rolegroup where pk_rolegroup = ?";
		SQLParameter parameter = new SQLParameter();
		parameter.addParam(pk_rolegroup);
		return CpbUtil.returnValue(CpbUtil.queryList(sql, parameter, CpRoleGroupVO.class));
	}
	public CpRoleGroupVO[] getRoleGroupByPkcorp(String pk_corp) throws CpbBusinessException {
		String sql = "select * from cp_rolegroup where pk_corp = ? and type = 2";
		SQLParameter parameter = new SQLParameter();
		parameter.addParam(pk_corp);
		return CpbUtil.returnArray(CpbUtil.queryList(sql, null, CpRoleGroupVO.class));
	}
	public CpRoleGroupVO[] getRoleGroupByParent(String pk_parent) throws CpbBusinessException {
		String sql = "select * from cp_rolegroup where pk_parent = ? ";
		SQLParameter parameter = new SQLParameter();
		parameter.addParam(pk_parent);
		PtBaseDAO dao = new PtBaseDAO();
		List<CpRoleGroupVO> list = null;
		try {
			list = (List<CpRoleGroupVO>) dao.executeQuery(sql, parameter, new BeanListProcessor(CpRoleGroupVO.class));
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e);
		}
		if (list == null || list.size() == 0) {
			return new CpRoleGroupVO[]{};
		
		}
		return list.toArray(new CpRoleGroupVO[0]);
	}
	
	public CpRoleGroupVO[] getRoleGroupByPkS(String[] pk_rolegroups) throws CpbBusinessException {
		String sql = "select * from cp_rolegroup where pk_rolegroup in(" + ")";
		return CpbUtil.returnArray(CpbUtil.queryList(sql, null, CpRoleGroupVO.class));
	}
	public CpRoleGroupVO getRoleGroupByGroupCode(String groupcode) throws CpbBusinessException {
		String sql = "select * from cp_rolegroup where groupcode = ?";
		SQLParameter parameter = new SQLParameter();
		parameter.addParam(groupcode);
		return CpbUtil.returnValue(CpbUtil.queryList(sql, parameter, CpRoleGroupVO.class));
	}
	public CpRoleGroupVO getRoleGroupByGroupName(String groupname) throws CpbBusinessException {
		String sql = "select * from cp_rolegroup where groupname = ?";
		SQLParameter parameter = new SQLParameter();
		parameter.addParam(groupname);
		return CpbUtil.returnValue(CpbUtil.queryList(sql, null, CpRoleGroupVO.class));
	}
	public CpRoleGroupVO[] getRoleGroupExc(String pk_rolegroup) throws CpbBusinessException {
		String sql = "select * from cp_rolegroup where pk_rolegroup <> ?";
		SQLParameter parameter = new SQLParameter();
		parameter.addParam(pk_rolegroup);
		return CpbUtil.returnArray(CpbUtil.queryList(sql, null, CpRoleGroupVO.class));
	}
	public CpRoleGroupVO[] getRoleGroupByPkgroup(String pk_group, String type) throws CpbBusinessException {
		String sql = "select * from cp_rolegroup where pk_group = ? and type = ? ";
		SQLParameter parameter = new SQLParameter();
		parameter.addParam(pk_group);
		parameter.addParam(type);
		return CpbUtil.returnArray(CpbUtil.queryList(sql, null, CpRoleGroupVO.class));
	}
	public CpRoleGroupVO[] getRoleGroupByPkgroup(String pk_group) throws CpbBusinessException {
		String sql = "select * from cp_rolegroup where pk_group = ?";
		SQLParameter parameter = new SQLParameter();
		parameter.addParam(pk_group);
		return CpbUtil.returnArray(CpbUtil.queryList(sql, null, CpRoleGroupVO.class));
	}
	public CpRoleGroupVO[] getRoleGroupByPkorgs(String[] pk_orgs) throws CpbBusinessException {
		if (pk_orgs == null || pk_orgs.length == 0) {
			return null;
		}
		String str = CpbUtil.joinQryArrays(pk_orgs);
		String sql = "select * from cp_rolegroup where pk_org in(" + str + ")";
		return CpbUtil.returnArray(CpbUtil.queryList(sql, null, CpRoleGroupVO.class));
	}
	@Override
	public CpRoleGroupVO[] getRoleGroupVos(String where)
			throws CpbBusinessException {
		String sql = "select * from cp_rolegroup where "+where;
		return CpbUtil.returnArray(CpbUtil.queryList(sql, null, CpRoleGroupVO.class));
	}
}
