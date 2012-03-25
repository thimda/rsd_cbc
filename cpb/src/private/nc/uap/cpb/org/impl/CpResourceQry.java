package nc.uap.cpb.org.impl;

import java.util.List;

import nc.bs.dao.DAOException;
import nc.jdbc.framework.SQLParameter;
import nc.jdbc.framework.processor.BeanListProcessor;
import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.itf.ICpResourceQry;
import nc.uap.cpb.org.vos.CpResourceVO;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.lfw.core.log.LfwLogger;

import org.apache.commons.lang.StringUtils;

public class CpResourceQry implements ICpResourceQry {

	@SuppressWarnings("unchecked") @Override public CpResourceVO[] queryResources(String wherePart) throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			List<CpResourceVO> list = (List<CpResourceVO>) dao.retrieveByClause(CpResourceVO.class, wherePart);
			if (list != null && !list.isEmpty()) {
				return list.toArray(new CpResourceVO[0]);
			}
		} catch (DAOException e) {
			LfwLogger.error("资源查询失败!", e);
			throw new CpbBusinessException(e);
		}
		return new CpResourceVO[] {};
	}

	@Override public CpResourceVO[] getModuleResByType(String module, Integer tp) {
		StringBuffer where = new StringBuffer();
		where.append(" pk_group = '00000000000000000000' and  resourcetype=" + tp + " and originalid like '" + module + "%' ");
		try {
			return queryResources(where.toString());
		} catch (CpbBusinessException e) {
			LfwLogger.error("资源查询失败!", e);
			return null;
		}
	}

	@SuppressWarnings("unchecked") @Override public CpResourceVO[] getResoureces(String pk_user) throws CpbBusinessException {
		PtBaseDAO baseDAO = new PtBaseDAO();
		String sql = "select * from pt_resource p1 join pt_permission p2 on p1.pk_resource = p2.pk_resource where p2.pk_role in (select pk_role from pt_roleuser p3 where p3.pk_user = ?) ";
		List<CpResourceVO> list = null;
		SQLParameter param = new SQLParameter();
		param.addParam(pk_user);
		try {
			list = (List<CpResourceVO>) baseDAO.executeQuery(sql, param, new BeanListProcessor(CpResourceVO.class));
			if (list == null || list.size() < 1)
				return new CpResourceVO[] {};
			return list.toArray(new CpResourceVO[list.size()]);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new CpbBusinessException(e);
		}
	}

	@SuppressWarnings("unchecked") public CpResourceVO[] getResourcesByRoles(String pkroles[]) throws CpbBusinessException {
		String param = StringUtils.join(pkroles, "','");
		StringBuffer wherePart = new StringBuffer("select * from pt_resource p1 join pt_permission p2 on p1.pk_resource = p2.pk_resource where p2.pk_role in ('");
		wherePart.append(param);
		wherePart.append("') ");
		List<CpResourceVO> list = null;
		try {
			list = (List<CpResourceVO>) new PtBaseDAO().executeQuery(wherePart.toString(), new BeanListProcessor(CpResourceVO.class));
			if (list == null || list.size() < 1)
				return new CpResourceVO[] {};
			return list.toArray(new CpResourceVO[list.size()]);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new CpbBusinessException(e);
		}
	}
}
