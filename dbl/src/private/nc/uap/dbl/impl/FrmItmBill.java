package nc.uap.dbl.impl;
import nc.bs.dao.DAOException;
import nc.bs.framework.common.NCLocator;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.dbl.context.DblContext;
import nc.uap.dbl.exception.DblServiceException;
import nc.uap.dbl.itf.IFrmItmBill;
import nc.uap.dbl.itf.IFrmItmQry;
import nc.uap.dbl.itf.IFrmTmpQry;
import nc.uap.dbl.vo.DblFormItemVO;
import nc.uap.dbl.vo.DblFormTemplateVO;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.wfm.contanier.ProDefsContainer;
import nc.uap.wfm.model.ProDef;
public class FrmItmBill implements IFrmItmBill {
	public void delFrmItmsByFrmTmpPk(String frmTmpPk) throws DblServiceException {
		DblFormTemplateVO frmTmpVo = NCLocator.getInstance().lookup(IFrmTmpQry.class).getFrmTmpByPk(frmTmpPk);
		ProDef proDef = ProDefsContainer.getProDefByFlowTypePk(frmTmpVo.getPk_formdefinition());
		if (proDef == null) {
			return;
		}
		DblFormItemVO[] frmItmVos = NCLocator.getInstance().lookup(IFrmItmQry.class).getOldFrmItmsByFrmTmpPk(frmTmpPk);
		DblFormItemVO frmItmVo = null;
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < frmItmVos.length; i++) {
			frmItmVo = frmItmVos[i];
			buf.append("'").append(frmItmVo.getPk_formitem()).append("'");
			if (i < frmItmVos.length - 1) {
				buf.append(",");
			}
		}
		PtBaseDAO dao = new PtBaseDAO();
		String sql = "delete from pdb_formitem  where pk_formtemplate='" + frmTmpPk + "'";
		try {
			dao.executeUpdate(sql);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new DblServiceException(e);
		}
		sql = "delete from pwfm_flwfrm where pk_prodef='" + proDef.getPk_prodef() + "'";
		if (buf.toString().length() != 0) {
			sql = sql + "and pk_frmitm not in(" + buf.toString() + ")";
		}
		try {
			dao.executeUpdate(sql);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new DblServiceException(e);
		}
		sql = "delete from pwfm_frmdevice where pk_prodef='" + proDef.getPk_prodef() + "'";
		if (buf.toString().length() != 0) {
			sql = sql + "and pk_frmitm not in(" + buf.toString() + ")";
		}
		try {
			dao.executeUpdate(sql);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new DblServiceException(e);
		}
	}
	public void saveFrmItm(DblFormItemVO[] frmItms) throws DblServiceException {
		PtBaseDAO dao = new PtBaseDAO();
		DblFormTemplateVO frmTmpVo = DblContext.getCurrentFormSession().getFrmTmpVO();
		delFrmItmsByFrmTmpPk(frmTmpVo.getPk_formtemplate());
		try {
			for (int i = 0; i < frmItms.length; i++) {
				System.out.println(frmItms[i].getOccupydomain() + "," + frmItms[i].getPk_formitem() + "\r\n");
			}
			if (frmItms != null) {
				dao.insertVOWithPKs(frmItms);
				// dao.insertVOs();
			}
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new DblServiceException(e.getMessage());
		}
		// lm-- if (frmTmpVo.getIsnotenable().booleanValue()) {
		// if (frmDefVo != null && frmItms != null && frmItms.length != 0) {
		// IAdjustTable adjustTable = AdjustTableFactory.getInstance();
		// adjustTable.execute(frmTmpVo);
		// }
		// }
	}
}
