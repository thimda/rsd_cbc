package nc.uap.cpb.org.impl;

import nc.bs.dao.DAOException;
import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.itf.ICpRoleRespBill;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.lfw.core.log.LfwLogger;

public class CpRoleRespBill implements ICpRoleRespBill {

	@Override
	public void deleteCpRoleRespByRolepk(String[] pk_roles)
			throws CpbBusinessException {
		if (pk_roles == null || pk_roles.length < 1)
			return;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < pk_roles.length; i++) {
			sb.append("'").append(pk_roles[i]).append("'");
			if (i != pk_roles.length - 1)
				sb.append(",");
		}
		PtBaseDAO dao = new PtBaseDAO();
		String sql = "delete from cp_roleresp where pk_role in(" + sb.toString() + ")";
		try {
			dao.executeUpdate(sql);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new CpbBusinessException(e);
		}
	}

	@Override
	public void deleteCpRoleRespByResppks(String[] pk_responsibilitys)
			throws CpbBusinessException {
		if (pk_responsibilitys == null || pk_responsibilitys.length < 1)
			return;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < pk_responsibilitys.length; i++) {
			sb.append("'").append(pk_responsibilitys[i]).append("'");
			if (i != pk_responsibilitys.length - 1)
				sb.append(",");
		}
		PtBaseDAO dao = new PtBaseDAO();
		String sql = "delete from cp_roleresp where pk_responsibility in(" + sb.toString() + ")";
		try {
			dao.executeUpdate(sql);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new CpbBusinessException(e);
		}
	}

}
