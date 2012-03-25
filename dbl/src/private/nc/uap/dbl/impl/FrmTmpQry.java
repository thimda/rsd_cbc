package nc.uap.dbl.impl;
import nc.bs.dao.DAOException;
import nc.bs.logging.Logger;
import nc.jdbc.framework.processor.ColumnProcessor;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.dbl.exception.DblServiceException;
import nc.uap.dbl.itf.IFrmTmpQry;
import nc.uap.dbl.vo.DblFormTemplateVO;
import nc.uap.lfw.core.log.LfwLogger;
public class FrmTmpQry implements IFrmTmpQry {
	public DblFormTemplateVO getFrmTmpByPk(String frmTmpPk) throws DblServiceException {
		PtBaseDAO dao = new PtBaseDAO();
		DblFormTemplateVO[] vos = null;
		try {
			vos = (DblFormTemplateVO[]) dao.queryByCondition(DblFormTemplateVO.class, "pk_formtemplate='" + frmTmpPk + "'");
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new DblServiceException(e.getMessage());
		}
		return (vos == null || vos.length == 0) ? null : vos[0];
	}
	public String getMaxVerByFrmtDefPk(String frmDefPk) throws DblServiceException {
		String maxVersion = "";
		PtBaseDAO dao = new PtBaseDAO();
		String sql = "select max(a.versionStr) from dbl_formtemplate a where a.pk_formdefinition='" + frmDefPk + "'";
		try {
			maxVersion = (String) dao.executeQuery(sql, new ColumnProcessor());
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new DblServiceException(e.getMessage());
		}
		return maxVersion;
	}
	public DblFormTemplateVO getFrmTmpByFrmDefPk(String frmDefPk) throws DblServiceException {
		PtBaseDAO dao = new PtBaseDAO();
		DblFormTemplateVO[] vos = null;
		try {
			vos = (DblFormTemplateVO[]) dao.queryByCondition(DblFormTemplateVO.class, "pk_formdefinition='" + frmDefPk + "' and  isnotenable='Y'");
		} catch (DAOException e) {
			Logger.error(e.getMessage(), e);
			throw new DblServiceException(e);
		}
		return (vos == null || vos.length == 0) ? null : vos[0];
	}
	
	public DblFormTemplateVO[] getFrmTmps() throws DblServiceException {
		PtBaseDAO dao = new PtBaseDAO();
		DblFormTemplateVO[] vos = null;
		try {
			vos = (DblFormTemplateVO[]) dao.queryByCondition(DblFormTemplateVO.class, "isnotenable='Y'");
			return vos;
		} 
		catch (DAOException e) {
			Logger.error(e.getMessage(), e);
			throw new DblServiceException(e);
		}
	}
}
