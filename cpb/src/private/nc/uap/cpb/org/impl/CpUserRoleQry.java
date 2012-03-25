package nc.uap.cpb.org.impl;
import java.util.List;
import nc.bs.dao.DAOException;
import nc.bs.logging.Logger;
import nc.jdbc.framework.SQLParameter;
import nc.jdbc.framework.processor.BeanListProcessor;
import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.itf.ICpUserRoleQry;
import nc.uap.cpb.org.vos.CpUserRoleVO;
import nc.uap.cpb.persist.dao.PtBaseDAO;
public class CpUserRoleQry implements ICpUserRoleQry {
	@SuppressWarnings("unchecked") public CpUserRoleVO[] getPtRoleUserByPkuser(String pk_user) throws CpbBusinessException {
		PtBaseDAO baseDAO = new PtBaseDAO();
		String sql = "select * from cp_userrole p  where p.pk_user = ?";
		SQLParameter parameter = new SQLParameter();
		parameter.addParam(pk_user);
		List<CpUserRoleVO> list = null;
		try {
			list = (List<CpUserRoleVO>) baseDAO.executeQuery(sql, parameter, new BeanListProcessor(CpUserRoleVO.class));
			if (list == null || list.size() < 1)
				return new CpUserRoleVO[] {};
			return list.toArray(new CpUserRoleVO[list.size()]);
		} catch (DAOException e) {
			Logger.error(e.getMessage(), e);
			throw new CpbBusinessException(e);
		}
	}
	@SuppressWarnings("unchecked") public CpUserRoleVO[] getPtRoleUserByPkRole(String pk_role) throws CpbBusinessException {
		PtBaseDAO baseDAO = new PtBaseDAO();
		String sql = "select * from cp_userrole p  where p.pk_role = ?";
		SQLParameter parameter = new SQLParameter();
		parameter.addParam(pk_role);
		List<CpUserRoleVO> list = null;
		try {
			list = (List<CpUserRoleVO>) baseDAO.executeQuery(sql, parameter, new BeanListProcessor(CpUserRoleVO.class));
			if (list == null || list.size() < 1)
				return new CpUserRoleVO[] {};
			return list.toArray(new CpUserRoleVO[list.size()]);
		} catch (DAOException e) {
			Logger.error(e.getMessage(), e);
			throw new CpbBusinessException(e);
		}
	}
}
