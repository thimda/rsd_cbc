package nc.uap.dbl.impl;

import nc.bs.dao.DAOException;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.dbl.exception.DblServiceException;
import nc.uap.dbl.itf.IFrmCatQry;
import nc.uap.dbl.vo.DblFormCategoryVO;
import nc.uap.lfw.core.log.LfwLogger;


public class FrmCatQry implements IFrmCatQry {
	@Override
	public DblFormCategoryVO[] getAllFrmCat() throws DblServiceException {
		PtBaseDAO dao = new PtBaseDAO();
		DblFormCategoryVO[] vos = null;
		try {
			vos = (DblFormCategoryVO[]) dao.queryByCondition(DblFormCategoryVO.class, "");
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new DblServiceException(e.getMessage());
		}
		return vos;
	}
	@Override
	public DblFormCategoryVO[] getFrmCatsByGroupPk(String groupPk) throws DblServiceException {
		PtBaseDAO dao = new PtBaseDAO();
		DblFormCategoryVO[] vos = null;
		try {
			vos = (DblFormCategoryVO[]) dao.queryByCondition(DblFormCategoryVO.class, "pk_group='" + groupPk + "'");
		} catch (DAOException e) {
			e.printStackTrace();
			LfwLogger.error(e.getMessage(), e);
			throw new DblServiceException(e.getMessage());
		}
		return vos;
	}
}
