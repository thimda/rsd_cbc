package nc.uap.cpb.org.impl;

import java.util.List;

import nc.bs.dao.DAOException;
import nc.bs.logging.Logger;
import nc.jdbc.framework.SQLParameter;
import nc.jdbc.framework.processor.BeanListProcessor;
import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.itf.ICpModuleQry;
import nc.uap.cpb.org.vos.CpModuleVO;
import nc.uap.cpb.persist.dao.PtBaseDAO;

public class CpModuleQry implements ICpModuleQry {

	@Override
	public CpModuleVO[] getAllModules() throws CpbBusinessException {
		PtBaseDAO baseDAO = new PtBaseDAO();
		String sql = "select * from cp_module";
		try { 
			List<CpModuleVO> list =  (List<CpModuleVO>) baseDAO.executeQuery(sql, new BeanListProcessor(CpModuleVO.class));
			if (list != null && !list.isEmpty()) {
				return list.toArray(new CpModuleVO[0]);
			}
		} catch (DAOException e) {
			Logger.error(e.getMessage(), e);
			throw new CpbBusinessException(e);
		}
		return new CpModuleVO[]{};
	}

	@Override
	public CpModuleVO getModuleById(String id) throws CpbBusinessException {
		PtBaseDAO baseDAO = new PtBaseDAO();
		String sql = "select * from cp_module where id = ? ";
		SQLParameter parameter = new SQLParameter();
		parameter.addParam(id);
		try {
			List<CpModuleVO> list =  (List<CpModuleVO>) baseDAO.executeQuery(sql, parameter,new BeanListProcessor(CpModuleVO.class));
			if (list != null && !list.isEmpty()) {
				return list.toArray(new CpModuleVO[0])[0];
			}
		} catch (DAOException e) {
			Logger.error(e.getMessage(), e);
			throw new CpbBusinessException(e);
		}
		return null;
	}

}
