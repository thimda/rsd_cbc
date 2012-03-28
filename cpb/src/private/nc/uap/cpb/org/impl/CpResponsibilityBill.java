package nc.uap.cpb.org.impl;

import nc.bs.dao.DAOException;
import nc.jdbc.framework.SQLParameter;
import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.extention.CpbExtentionUtil;
import nc.uap.cpb.org.extention.ICpbExtentionService;
import nc.uap.cpb.org.itf.ICpResponsibilityBill;
import nc.uap.cpb.org.vos.CpRespFuncVO;
import nc.uap.cpb.org.vos.CpResponsibilityVO;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.lfw.core.log.LfwLogger;
public class CpResponsibilityBill implements ICpResponsibilityBill {

	@Override 
	public String[] add(CpResponsibilityVO[] vos) throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			return dao.insertVOs(vos);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e.getCause());
			throw new CpbBusinessException(e);
		}
	}
 
	@Override
	public void update(CpResponsibilityVO[] vos)
			throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.updateVOArray(vos);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new CpbBusinessException(e);
		}
	}

	@Override
	public String[] addRespFuncVos(CpRespFuncVO[] vos)
			throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			return dao.insertVOs(vos);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e.getCause());
			throw new CpbBusinessException(e);
		}
	}

	@Override
	public void delAllRespFuncVos(String pk_responsibility) throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		SQLParameter params = new SQLParameter();
		params.addParam(pk_responsibility);
		try {
			dao.deleteByClause(CpRespFuncVO.class, " pk_responsibility = ?", params);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(),e);
			throw new CpbBusinessException(e);
		}
	}

	@Override
	public void delResponsibilityVo(CpResponsibilityVO vo)
			throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.deleteVO(vo);
			CpbExtentionUtil.notifyAfterAction(ICpbExtentionService.RESPMANAGE, ICpbExtentionService.DELETE, vo.getPk_responsibility());
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(),e);
			throw new CpbBusinessException(e);
		}
	}
	
}
