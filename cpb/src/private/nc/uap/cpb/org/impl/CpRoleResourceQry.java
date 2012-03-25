package nc.uap.cpb.org.impl;
import java.util.List;
import nc.bs.dao.DAOException;
import nc.jdbc.framework.SQLParameter;
import nc.jdbc.framework.processor.BeanListProcessor;
import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.itf.ICpRoleResourceQry;
import nc.uap.cpb.org.vos.CpRoleResVO;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.lfw.core.log.LfwLogger;
public class CpRoleResourceQry implements ICpRoleResourceQry {
	@SuppressWarnings("unchecked") public CpRoleResVO[] getPtRoleResourceByPkRoles(String[] pk_roles, int resourcetype) throws CpbBusinessException {
		PtBaseDAO baseDAO = new PtBaseDAO();
		if (pk_roles == null || pk_roles.length == 0) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < pk_roles.length; i++) {
			sb.append("'").append(pk_roles[i]).append("'");
			if (i != pk_roles.length)
				sb.append(",");
		}
		String sql = "select * from cp_roleres p  where p.pk_role in(" + sb.toString() + ") and resourcetype = " + resourcetype;
		List<CpRoleResVO> list = null;
		try {
			list = (List<CpRoleResVO>) baseDAO.executeQuery(sql, new BeanListProcessor(CpRoleResVO.class));
			if (list == null || list.size() < 1)
				return new CpRoleResVO[] {};
			return list.toArray(new CpRoleResVO[list.size()]);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new CpbBusinessException(e.getMessage());
		}
	}
	@SuppressWarnings("unchecked") public CpRoleResVO[] getRoleResourceByPk_resource(String pk_resource) throws CpbBusinessException {
		PtBaseDAO baseDAO = new PtBaseDAO();
		String sql = "select * from cp_roleres p where p.pk_resource = ?";
		SQLParameter parameter = new SQLParameter();
		parameter.addParam(pk_resource);
		try {
			List<CpRoleResVO> list = (List<CpRoleResVO>) baseDAO.executeQuery(sql, parameter, new BeanListProcessor(CpRoleResVO.class));
			return (CpRoleResVO[]) list.toArray(new CpRoleResVO[list.size()]);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new CpbBusinessException(e.getMessage());
		}
	}
}
