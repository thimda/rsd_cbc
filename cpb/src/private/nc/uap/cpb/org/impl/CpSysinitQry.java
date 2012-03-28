package nc.uap.cpb.org.impl;

import java.util.List;

import nc.bs.dao.DAOException;
import nc.jdbc.framework.SQLParameter;
import nc.jdbc.framework.processor.BeanListProcessor;
import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.itf.ICpSysinitQry;
import nc.uap.cpb.org.vos.CpSysinitVO;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.lfw.core.log.LfwLogger;

/**
 * 参数设置查询服务
 * 
 * @author chenwl
 * 
 */
public class CpSysinitQry implements ICpSysinitQry {
	
	/* (non-Javadoc)
	 * @see nc.uap.cpb.org.itf.ICpSysinitQry#getSysinitByPk(java.lang.String)
	 */
	public CpSysinitVO getSysinitByPk(String pk_sysinit) throws CpbBusinessException{
		PtBaseDAO baseDAO = new PtBaseDAO();
		String sql = "select * from cp_sysinit s where s.pk_sysinit = ?";
		SQLParameter parameter = new SQLParameter();
		parameter.addParam(pk_sysinit);
		List<CpSysinitVO> list = null;
		try {
			list = (List<CpSysinitVO>) baseDAO.executeQuery(sql, new BeanListProcessor(CpSysinitVO.class));
			return list.get(0);
		} catch (DAOException e) {
			LfwLogger .error(e.getMessage(), e);
			throw new CpbBusinessException(e);
		}
	}
	
	@Override
	public CpSysinitVO getSysinitByCodeAndPkorg(String initcode,String pk_org) throws CpbBusinessException {
		PtBaseDAO baseDAO = new PtBaseDAO();
		String sql = "select * from cp_sysinit s where s.initcode = '"+ initcode + "' and s.pk_org = '" + pk_org + "'";
//		String sql = "select * from cp_sysinit s where s.initcode = ? and s.pk_org = ?";
//		SQLParameter parameter = new SQLParameter();
//		parameter.addParam(initcode);
//		parameter.addParam(pk_org);
		List<CpSysinitVO> list = null;
		try {
			list = (List<CpSysinitVO>) baseDAO.executeQuery(sql, new BeanListProcessor(CpSysinitVO.class));
			return list.get(0);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new CpbBusinessException(e);
		}
	}

	@Override
	public CpSysinitVO[] getSysinitByPkorg(String pk_org)
			throws CpbBusinessException {
		PtBaseDAO baseDAO = new PtBaseDAO();
		String sql = "select * from cp_sysinit s where s.pk_org = ?";
		SQLParameter parameter = new SQLParameter();
		parameter.addParam(pk_org);
		List<CpSysinitVO> list = null;
		try {
			list = (List<CpSysinitVO>) baseDAO.executeQuery(sql, new BeanListProcessor(CpSysinitVO.class));
			return list.toArray(new CpSysinitVO[list.size()]);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new CpbBusinessException(e);
		}
	}

	@Override
	public CpSysinitVO[] getSysinitByWhere(String where)
			throws CpbBusinessException {
		PtBaseDAO baseDAO = new PtBaseDAO();
		String sql = "select * from cp_sysinit s where " + where;
		List<CpSysinitVO> list = null;
		try {
			list = (List<CpSysinitVO>) baseDAO.executeQuery(sql, new BeanListProcessor(CpSysinitVO.class));
			return list.toArray(new CpSysinitVO[list.size()]);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new CpbBusinessException(e);
		}
	}

}
