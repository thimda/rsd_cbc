package nc.uap.dbl.impl;

import java.util.ArrayList;
import java.util.List;

import nc.bs.dao.BaseDAO;
import nc.bs.dao.DAOException;
import nc.bs.framework.common.NCLocator;
import nc.bs.logging.Logger;
import nc.jdbc.framework.processor.BeanListProcessor;
import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.itf.ICpRoleQry;
import nc.uap.cpb.org.vos.CpRoleVO;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.dbl.exception.DblServiceException;
import nc.uap.dbl.itf.IFrmDefQry;
import nc.uap.dbl.vo.DblFormDefinitionVO;
import nc.uap.lfw.core.log.LfwLogger;

public class FrmDefQry implements IFrmDefQry {
	public DblFormDefinitionVO getFrmDefByPk(String frmDefPk) throws DblServiceException {
		PtBaseDAO dao = new PtBaseDAO();
		DblFormDefinitionVO[] vos = null;
		try {
			vos = (DblFormDefinitionVO[]) dao.queryByCondition(DblFormDefinitionVO.class, "pk_formdefinition='" + frmDefPk + "'");
		} catch (DAOException e) {
			Logger.error(e.getMessage(), e);
			throw new DblServiceException(e.getMessage());
		}
		return (vos == null || vos.length == 0) ? null : vos[0];
	}
	@SuppressWarnings("unchecked")
	public DblFormDefinitionVO[] getFrmDefByPks(String[] frmDefPks) throws DblServiceException {
		BaseDAO baseDAO = new BaseDAO();
		if (frmDefPks == null || frmDefPks.length < 1)
			return null;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < frmDefPks.length; i++) {
			sb.append("'").append(frmDefPks[i]).append("'");
			if (i != frmDefPks.length - 1)
				sb.append(",");
		}
		String sql = "select * from dbl_formdefinition p  where p.pk_formdefinition in(" + sb.toString() + ")";
		List<DblFormDefinitionVO> list = null;
		try {
			list = (List<DblFormDefinitionVO>) baseDAO.executeQuery(sql, new BeanListProcessor(DblFormDefinitionVO.class));
			if (list == null || list.size() < 1)
				return new DblFormDefinitionVO[] {};
			return list.toArray(new DblFormDefinitionVO[0]);
		} catch (DAOException e) {
			Logger.error(e.getMessage(), e);
			throw new DblServiceException(e);
		}
	}
	public DblFormDefinitionVO[] getFrmDefsByFrmCaPk(String frmCatPk) throws DblServiceException {
		PtBaseDAO dao = new PtBaseDAO();
		DblFormDefinitionVO[] vos = null;
		try {
			vos = (DblFormDefinitionVO[]) dao.queryByCondition(DblFormDefinitionVO.class, "pk_formcategory='" + frmCatPk + "'");
		} catch (DAOException e) {
			Logger.error(e.getMessage(), e);
			throw new DblServiceException(e.getMessage());
		}
		return vos;
	}
	public DblFormDefinitionVO[] getAllFrmDef() throws DblServiceException {
		PtBaseDAO dao = new PtBaseDAO();
		DblFormDefinitionVO[] vos = null;
		try {
			vos = (DblFormDefinitionVO[]) dao.queryByCondition(DblFormDefinitionVO.class, "");
		} catch (DAOException e) {
			Logger.error(e.getMessage(), e);
			throw new DblServiceException(e.getMessage());
		}
		return vos;
	}
	@Override
	public DblFormDefinitionVO[] getFrmDefsByRolePks(String[] rolePks) throws DblServiceException {
		if (rolePks == null || rolePks.length == 0) {
			return null;
		}
		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append("rolepks ");
		for (int i = 0; i < rolePks.length; i++) {
			sqlStr.append("like '%" + rolePks[i] + "%'");
			if (i == rolePks.length - 1) {
				break;
			} else {
				sqlStr.append(" or rolepks ");
			}
		}
		PtBaseDAO dao = new PtBaseDAO();
		DblFormDefinitionVO[] vos = null;
		try {
			vos = (DblFormDefinitionVO[]) dao.queryByCondition(DblFormDefinitionVO.class, sqlStr.toString() + " order by orderstr");
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new DblServiceException(e.getMessage());
		}
		return vos;
	}
	@Override
	public DblFormDefinitionVO[] getFrmDefsByUserPk(String userPk) throws DblServiceException {
		List<String> list = new ArrayList<String>();
		CpRoleVO[] roles = null;
		try {
			roles = NCLocator.getInstance().lookup(ICpRoleQry.class).getUserRoles(userPk, true);
			if (roles != null && roles.length > 0) {
				for (int i = 0; i < roles.length; i++)
					list.add(roles[i].getPk_role());
			}
		} catch (CpbBusinessException e) {
			LfwLogger.error(e.getMessage(), e);
		}
		String[] rolePks = list.toArray(new String[0]);
		DblFormDefinitionVO frmDefVos[] = this.getFrmDefsByRolePks(rolePks);
		return frmDefVos;
	}
	@Override
	public DblFormDefinitionVO[] getMyPrtptFrmDefsUserPk(String userPk) throws DblServiceException {
		PtBaseDAO dao = new PtBaseDAO();
		DblFormDefinitionVO[] vos = null;
		try {
			String where = " pk_formdefinition in( " + " select distinct pk_frmdef from wfm_task" + " where pk_executer='" + userPk + "' or pk_creater='" + userPk + "') order by orderstr";
			vos = (DblFormDefinitionVO[]) dao.queryByCondition(DblFormDefinitionVO.class, where);
		} catch (DAOException e) {
			Logger.error(e.getMessage(), e);
			throw new DblServiceException(e.getMessage());
		}
		return vos;
	}
}
