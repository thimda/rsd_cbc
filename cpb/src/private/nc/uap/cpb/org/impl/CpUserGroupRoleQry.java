package nc.uap.cpb.org.impl;
import java.util.List;
import nc.bs.dao.DAOException;
import nc.jdbc.framework.SQLParameter;
import nc.jdbc.framework.processor.BeanListProcessor;
import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.itf.ICpUserGroupRoleQry;
import nc.uap.cpb.org.vos.CpUserGroupRoleVO;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.lfw.core.log.LfwLogger;
public class CpUserGroupRoleQry implements ICpUserGroupRoleQry {
	@SuppressWarnings("unchecked") public CpUserGroupRoleVO[] getUgRoleByUserPk(String pk_user) throws CpbBusinessException {
		PtBaseDAO baseDAO = new PtBaseDAO();
		String sql = "select a.* from cp_usergrouprole a, cp_usergroupuser b where a.pk_usergroup = b.pk_usergroup" + " and b.pk_user =  ?";
		SQLParameter parameter = new SQLParameter();
		parameter.addParam(pk_user);
		try {
			List<CpUserGroupRoleVO> list = (List<CpUserGroupRoleVO>) baseDAO.executeQuery(sql, parameter, new BeanListProcessor(CpUserGroupRoleVO.class));
			return (CpUserGroupRoleVO[]) list.toArray(new CpUserGroupRoleVO[0]);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new CpbBusinessException(e);
		}
	}
	@SuppressWarnings("unchecked") public CpUserGroupRoleVO[] getUgRoleByRolePk(String pk_role) throws CpbBusinessException {
		PtBaseDAO baseDAO = new PtBaseDAO();
		String sql = "select a.* from cp_usergrouprole a where a.pk_role =  ?";
		SQLParameter parameter = new SQLParameter();
		parameter.addParam(pk_role);
		try {
			List<CpUserGroupRoleVO> list = (List<CpUserGroupRoleVO>) baseDAO.executeQuery(sql, parameter, new BeanListProcessor(CpUserGroupRoleVO.class));
			return (CpUserGroupRoleVO[]) list.toArray(new CpUserGroupRoleVO[0]);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new CpbBusinessException(e);
		}
	}
}
