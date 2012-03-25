package nc.uap.cpb.org.impl;
import nc.bs.dao.DAOException;
import nc.jdbc.framework.SQLParameter;
import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.itf.ICpRoleResourceBill;
import nc.uap.cpb.org.vos.CpRoleResVO;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.lfw.core.log.LfwLogger;
public class CpRoleResourceBill implements ICpRoleResourceBill {
	public void deleteRoleResourceByPk_resource(String pk_resource) throws CpbBusinessException {
		PtBaseDAO baseDAO = new PtBaseDAO();
		SQLParameter parameter = new SQLParameter();
		parameter.addParam(pk_resource);
		try {
			baseDAO.deleteByClause(CpRoleResVO.class, " pk_resource = ? ", parameter);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new CpbBusinessException(e);
		}
	}
	public void deleteRoleResourceByRolePk(String pk_role) throws CpbBusinessException {
		PtBaseDAO baseDAO = new PtBaseDAO();
		SQLParameter parameter = new SQLParameter();
		parameter.addParam(pk_role);
		try {
			baseDAO.deleteByClause(CpRoleResVO.class, " pk_role = ? ", parameter);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new CpbBusinessException(e);
		}
	}
	public void deleteRoleResourceByRoleResoPk(String roleResoPk) throws CpbBusinessException {
		PtBaseDAO baseDAO = new PtBaseDAO();
		SQLParameter parameter = new SQLParameter();
		parameter.addParam(roleResoPk);
		try {
			baseDAO.deleteByClause(CpRoleResVO.class, " pk_roleres = ? ", parameter);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new CpbBusinessException(e);
		}
	}
}
