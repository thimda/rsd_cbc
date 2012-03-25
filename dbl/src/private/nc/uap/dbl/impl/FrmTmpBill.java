package nc.uap.dbl.impl;
import nc.bs.dao.DAOException;
import nc.bs.framework.common.NCLocator;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.dbl.exception.DblServiceException;
import nc.uap.dbl.itf.IFrmTmpBill;
import nc.uap.dbl.itf.IFrmTmpQry;
import nc.uap.dbl.vo.DblFormTemplateVO;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.wfm.plugin.IPdblExtentionService;
import nc.uap.wfm.plugin.PdblExtentionUtil;
import nc.vo.pub.lang.UFBoolean;
public class FrmTmpBill implements IFrmTmpBill {
	public void delFrmTmpByPk(String frmTmpPk) throws DblServiceException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			PdblExtentionUtil.notifybeforeAction(IPdblExtentionService.POINTID, IPdblExtentionService.FRMTMPMGR_DELETE, null);
			dao.deleteByPK(DblFormTemplateVO.class, frmTmpPk);
			PdblExtentionUtil.notifyAfterAction(IPdblExtentionService.POINTID, IPdblExtentionService.FRMTMPMGR_DELETE, null);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new DblServiceException(e.getMessage());
		}
	}
	public void saveFrmTmp(DblFormTemplateVO frmTmpVo) throws DblServiceException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			String frmTmpPk = frmTmpVo.getPk_formtemplate();
			if (frmTmpPk == null || frmTmpPk.length() == 0) {
				PdblExtentionUtil.notifybeforeAction(IPdblExtentionService.POINTID, IPdblExtentionService.FRMTMPMGR_ADD, frmTmpVo);
				dao.insertVO(frmTmpVo);
				PdblExtentionUtil.notifyAfterAction(IPdblExtentionService.POINTID, IPdblExtentionService.FRMTMPMGR_ADD, frmTmpVo);
			} else {
				PdblExtentionUtil.notifybeforeAction(IPdblExtentionService.POINTID, IPdblExtentionService.FRMTMPMGR_MODIFY, frmTmpVo);
				dao.updateVO(frmTmpVo);
				PdblExtentionUtil.notifyAfterAction(IPdblExtentionService.POINTID, IPdblExtentionService.FRMTMPMGR_MODIFY, frmTmpVo);
			}
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new DblServiceException(e.getMessage());
		}
	}
	public void enableFrmTmpByPk(String frmTmpPk) throws DblServiceException {
		DblFormTemplateVO frmTmpVo = NCLocator.getInstance().lookup(IFrmTmpQry.class).getFrmTmpByPk(frmTmpPk);
		if (frmTmpVo != null) {
			PdblExtentionUtil.notifybeforeAction(IPdblExtentionService.POINTID, IPdblExtentionService.FRMTMPMGR_ENABLE, frmTmpVo);
			String str = "pk_formdefinition='" + frmTmpVo.getPk_formdefinition() + "' and pk_formtemplate<>'" + frmTmpVo.getPk_formtemplate() + "'";
			PtBaseDAO dao = new PtBaseDAO();
			try {
				DblFormTemplateVO[] vos = (DblFormTemplateVO[]) dao.queryByCondition(DblFormTemplateVO.class, str);
				if (frmTmpVo.getIsnotenable().booleanValue()) {
					frmTmpVo.setIsnotenable(UFBoolean.valueOf(false));
				} else {
					frmTmpVo.setIsnotenable(UFBoolean.valueOf(true));
					if (vos != null) {
						for (int i = 0; i < vos.length; i++) {
							DblFormTemplateVO temp = (DblFormTemplateVO) vos[i];
							temp.setIsnotenable(UFBoolean.valueOf(false));
							this.saveFrmTmp(temp);
						}
					}
				}
			} catch (DAOException e) {
				LfwLogger.error(e.getMessage(), e);
				throw new DblServiceException(e.getMessage());
			}
			PdblExtentionUtil.notifyAfterAction(IPdblExtentionService.POINTID, IPdblExtentionService.FRMTMPMGR_ENABLE, frmTmpVo);
			this.saveFrmTmp(frmTmpVo);
		}
	}
}
