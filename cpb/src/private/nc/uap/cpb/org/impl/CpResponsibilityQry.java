package nc.uap.cpb.org.impl;

import java.util.List;

import nc.bs.dao.DAOException;
import nc.jdbc.framework.SQLParameter;
import nc.jdbc.framework.processor.BeanListProcessor;
import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.itf.ICpResponsibilityQry;
import nc.uap.cpb.org.vos.CpResponsibilityVO;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.lfw.core.log.LfwLogger;

public class CpResponsibilityQry implements ICpResponsibilityQry {

	@Override
	public CpResponsibilityVO getResponsibilityVoByPk(String pk_responsibility)
			throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		CpResponsibilityVO[] vos = null;
		try {
			vos = (CpResponsibilityVO[]) dao.queryByCondition(CpResponsibilityVO.class, "pk_responsibility='" + pk_responsibility + "'");
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new CpbBusinessException(e.getMessage());
		}
		if (vos != null && vos.length == 1) {
			return vos[0];
		} else {
			return null;
		}
	}

	@Override
	public CpResponsibilityVO[] getResponsibilityVos(String pk_group,String code) throws CpbBusinessException {
		PtBaseDAO baseDAO = new PtBaseDAO();
		String sql = "select * from cp_responsibility  where pk_group =? and code = ?";
		List<CpResponsibilityVO> list = null;
		SQLParameter param = new SQLParameter();
		param.addParam(pk_group);
		param.addParam(code);
		try {
			list = (List<CpResponsibilityVO>) baseDAO.executeQuery(sql, param, new BeanListProcessor(CpResponsibilityVO.class));
			if (list == null || list.size() < 1)
				return new CpResponsibilityVO[] {};
			return list.toArray(new CpResponsibilityVO[list.size()]);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new CpbBusinessException(e);
		}
	}

	@Override
	public CpResponsibilityVO[] getResponsibilityVos(String where)
			throws CpbBusinessException {
		PtBaseDAO baseDAO = new PtBaseDAO();
		String sql = "select * from cp_responsibility  where "+where;
		List<CpResponsibilityVO> list = null;
		try {
			list = (List<CpResponsibilityVO>) baseDAO.executeQuery(sql, new BeanListProcessor(CpResponsibilityVO.class));
			if (list == null || list.size() < 1)
				return new CpResponsibilityVO[] {};
			return list.toArray(new CpResponsibilityVO[list.size()]);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new CpbBusinessException(e);
		}
	}
	
}
