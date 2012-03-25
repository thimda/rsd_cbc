package nc.uap.cpb.org.impl;
import java.util.ArrayList;
import java.util.List;
import nc.bs.dao.DAOException;
import nc.bs.logging.Logger;
import nc.jdbc.framework.SQLParameter;
import nc.jdbc.framework.processor.BeanListProcessor;
import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.itf.ICpRoleQry;
import nc.uap.cpb.org.util.CpbUtil;
import nc.uap.cpb.org.vos.CpResponsibilityVO;
import nc.uap.cpb.org.vos.CpRoleVO;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.lfw.core.log.LfwLogger;
public class CpRoleQry implements ICpRoleQry {
	public CpRoleVO[] getAllRoles() throws CpbBusinessException {
		String sql = "select * from cp_role";
		return CpbUtil.returnArray(this.queryList(sql, null));
	}
	public CpRoleVO[] getAllRolesExcp(String pk_role) throws CpbBusinessException {
		String sql = "select * from cp_role where pk_role <> ?";
		SQLParameter parameter = new SQLParameter();
		parameter.addParam(pk_role);
		return CpbUtil.returnArray(this.queryList(sql, parameter));
	}
	public CpRoleVO getRoleByPk(String pk_role) throws CpbBusinessException {
		String sql = "select * from cp_role p where  p.pk_role = ?";
		SQLParameter parameter = new SQLParameter();
		parameter.addParam(pk_role);
		return CpbUtil.returnValue(this.queryList(sql, parameter));
	} 
	
	public CpRoleVO[] getRoleByRoleGroup(String pk_rolegroup) throws CpbBusinessException {
		String sql = "select p.* from cp_role p where p.pk_rolegroup = ?";
		SQLParameter parameter = new SQLParameter();
		parameter.addParam(pk_rolegroup);
		return CpbUtil.returnArray(this.queryList(sql, parameter));
	}
	public CpRoleVO getRoleByRolecode(String rolecode) throws CpbBusinessException {
		String sql = "select * from cp_role where rolecode='" + rolecode + "'";
		return CpbUtil.returnValue(this.queryList(sql, null));
	}
	public CpRoleVO getRoleByRolename(String rolename) throws CpbBusinessException {
		String sql = "select * from cp_role where rolename = ?";
		return CpbUtil.returnValue(this.queryList(sql, null));
	}
	public CpRoleVO[] getRolesByPk(String[] pk_roles) throws CpbBusinessException {
		if (pk_roles == null || pk_roles.length == 0) {
			return null;
		}
		String str = CpbUtil.joinQryArrays(pk_roles);
		String sql = "select * from cp_role p  where p.pk_role in(" + str + ")";
		return CpbUtil.returnArray(this.queryList(sql, null));
	}
	public CpRoleVO[] getUserRoles(String pk_user) throws CpbBusinessException {
		String sql = "select a.* from cp_role a, cp_userrole b where a.pk_role = b.pk_role and b.pk_user = ?";
		SQLParameter parameter = new SQLParameter();
		parameter.addParam(pk_user);
		return CpbUtil.returnArray(this.queryList(sql, parameter));
	}
	public CpRoleVO[] getRoleByDeptPk(String pk_dept) throws CpbBusinessException {
		String sql = "select a.rolecode, a.rolename, a.comments from cp_role a, cp_deptrole b where a.pk_role = b.pk_role and b.pk_dept = ? ";
		SQLParameter parameter = new SQLParameter();
		parameter.addParam(pk_dept);
		return CpbUtil.returnArray(this.queryList(sql, null));
	}
	protected List<CpRoleVO> queryList(String sql, SQLParameter parameter) throws CpbBusinessException {
		return CpbUtil.queryList(sql, parameter, CpRoleVO.class);
	}
	public CpRoleVO[] getAllRoleByCondition(Class<?> voClass, String condition) throws CpbBusinessException {
		PtBaseDAO baseDAO = new PtBaseDAO();
		try {
			return (CpRoleVO[]) baseDAO.queryByCondition(voClass, condition);
		} catch (DAOException e) {
			Logger.error(e.getMessage(), e);
			throw new CpbBusinessException(e);
		}
	}
	public CpRoleVO getRoleByRole(String pk_rolegroup, String rolecode, String rolename) throws CpbBusinessException {
		String sql = "select p.* from cp_role p where p.pk_rolegroup = ? and p.rolecode = ?";
		SQLParameter parameter = new SQLParameter();
		parameter.addParam(pk_rolegroup);
		parameter.addParam(rolecode);
		List<CpRoleVO> list = CpbUtil.queryList(sql, parameter, CpRoleVO.class);
		if (list == null || list.size() < 1) {
			sql = "select p.*from cp_role p where p.pk_rolegroup = ? and p.rolename = ?";
			parameter.clearParams();
			parameter.addParam(pk_rolegroup);
			parameter.addParam(rolename);
			return CpbUtil.returnValue(this.queryList(sql, parameter));
		} else {
			return null;
		}
	}
	public CpRoleVO getRoleByRole(int type, String rolecode, String rolename) throws CpbBusinessException {
		String sql = "select p.* from cp_role p where p.type = ? and p.rolecode = ?";
		SQLParameter parameter = new SQLParameter();
		parameter.addParam(type);
		parameter.addParam(rolecode);
		List<CpRoleVO> list = this.queryList(sql, parameter);
		if (list == null || list.size() == 0) {
			sql = "select p.*from cp_role p where p.type = ? and p.rolename = ?";
			parameter.clearParams();
			parameter.addParam(type);
			parameter.addParam(rolename);
			return CpbUtil.returnValue(this.queryList(sql, parameter));
		} else {
			return null;
		}
	}
	public CpRoleVO[] getUserRoles(String pk_user, boolean withGroup) throws CpbBusinessException {
		if (withGroup) {
			List<String> rolePkList = new ArrayList<String>();
			CpRoleVO[] ptRoles = getUserRoles(pk_user);
			List<CpRoleVO> roleList = new ArrayList<CpRoleVO>();
			if(ptRoles!=null){
				for (int i = 0; i < ptRoles.length; i++) {
					roleList.add(ptRoles[i]);
				}
			}
			String sql = "select a.* from cp_role a, cp_usergroupuser b, cp_usergrouprole c where" + " a.pk_role = c.pk_role and b.pk_usergroup = c.pk_usergroup and b.pk_user = ?";
			SQLParameter parameter = new SQLParameter();
			parameter.addParam(pk_user);
			if (roleList != null) {
				for (int i = 0; i < roleList.size(); i++) {
					rolePkList.add(roleList.get(i).getPk_role());
				}
			}
			List<CpRoleVO> userGroupRoleList = this.queryList(sql, parameter);
			if (userGroupRoleList != null) {
				for (int i = 0; i < userGroupRoleList.size(); i++) {
					if (!rolePkList.contains(userGroupRoleList.get(i).getPk_role())) {
						roleList.add(userGroupRoleList.get(i));
					}
				}
			}
			return (CpRoleVO[]) roleList.toArray(new CpRoleVO[roleList.size()]);
		} else {
			return getUserRoles(pk_user);
		}
	}
	public String[] getRolePksByUserPk(String userPk) throws CpbBusinessException {
		return null;
	}
	@Override
	public CpRoleVO[] getRoleVos(String where) throws CpbBusinessException {
		PtBaseDAO baseDAO = new PtBaseDAO();
		String sql = "select * from cp_role  where "+where;
		List<CpRoleVO> list = null;
		try {
			list = (List<CpRoleVO>) baseDAO.executeQuery(sql, new BeanListProcessor(CpRoleVO.class));
			if (list == null || list.size() < 1)
				return new CpRoleVO[] {};
			return list.toArray(new CpRoleVO[list.size()]);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new CpbBusinessException(e);
		}
	}
}
