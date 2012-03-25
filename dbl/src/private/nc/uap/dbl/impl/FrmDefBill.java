package nc.uap.dbl.impl;

import nc.bs.dao.DAOException;
import nc.bs.logging.Logger;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.dbl.exception.DblServiceException;
import nc.uap.dbl.itf.IFrmDefBill;
import nc.uap.dbl.vo.DblFormDefinitionVO;
import nc.uap.wfm.plugin.IPdblExtentionService;
import nc.uap.wfm.plugin.PdblExtentionUtil;
import nc.vo.pub.SuperVO;

import org.apache.commons.lang.StringUtils;

public class FrmDefBill implements IFrmDefBill {
	public void saveFrmDef(DblFormDefinitionVO frmDefVO) throws DblServiceException {
		PtBaseDAO dao = new PtBaseDAO();
		check(frmDefVO, false);
		try {
			dao.insertVO(frmDefVO);
		} catch (DAOException e) {
			Logger.error(e.getMessage(), e);
			throw new DblServiceException(e.getMessage());
		}
	}
	public void saveOrUpdateFrmDef(DblFormDefinitionVO frmDefVo) throws DblServiceException {
		String frmDefPk = frmDefVo.getPk_formdefinition();
		PtBaseDAO dao = new PtBaseDAO();
		try {
			if (frmDefPk == null || frmDefPk.length() == 0) {
				check(frmDefVo, false);
				PdblExtentionUtil.notifybeforeAction(IPdblExtentionService.POINTID, IPdblExtentionService.FRMDEFMGR_ADD, frmDefVo);
				dao.insertVO(frmDefVo);
				PdblExtentionUtil.notifyAfterAction(IPdblExtentionService.POINTID, IPdblExtentionService.FRMDEFMGR_ADD, frmDefVo);
			} else {
				check(frmDefVo, true);
				PdblExtentionUtil.notifybeforeAction(IPdblExtentionService.POINTID, IPdblExtentionService.FRMDEFMGR_MODIFY, frmDefVo);
				dao.updateVO(frmDefVo);
				PdblExtentionUtil.notifyAfterAction(IPdblExtentionService.POINTID, IPdblExtentionService.FRMDEFMGR_MODIFY, frmDefVo);
			}
		} catch (DAOException e) {
			e.printStackTrace();
			Logger.error(e.getMessage(), e);
			throw new DblServiceException(e.getMessage(), e);
		}
	}
	@Override
	public void delFrmDefByPk(String pk) throws DblServiceException {
		PtBaseDAO dao = new PtBaseDAO();
		String sql = "delete from dbl_formdefinition where pk_formdefinition='" + pk + "'";
		try {
			PdblExtentionUtil.notifybeforeAction(IPdblExtentionService.POINTID, IPdblExtentionService.FRMDEfMGR_DELETE, null);
			dao.executeUpdate(sql);
			PdblExtentionUtil.notifyAfterAction(IPdblExtentionService.POINTID, IPdblExtentionService.FRMDEfMGR_DELETE, null);
		} catch (DAOException e) {
			e.printStackTrace();
			Logger.error(e.getMessage(), e);
			throw new DblServiceException(e.getMessage(), e);
		}
	}
	public void check(DblFormDefinitionVO frmDefVO, boolean isNotUpdate) throws DblServiceException {
		PtBaseDAO dao = new PtBaseDAO();
		SuperVO[] vos = null;
		String name = frmDefVO.getName();
		if (!StringUtils.isNotBlank(name)) {
			throw new DblServiceException("表单定义名称不能为空");
		}
		try {
			vos = dao.queryByCondition(DblFormDefinitionVO.class, "name='" + name + "'");
		} catch (DAOException e) {
			e.printStackTrace();
			Logger.error(e.getMessage(), e);
			throw new DblServiceException(e.getMessage(), e);
		}
		if (vos != null && vos.length > 0) {
			DblFormDefinitionVO tmpVO = (DblFormDefinitionVO) vos[0];
			if (isNotUpdate) {
				if (!tmpVO.getPk_formdefinition().equalsIgnoreCase(frmDefVO.getPk_formdefinition())) {
					throw new DblServiceException("表单定义名称重复，请修改表单定义名称");
				}
			} else {
				throw new DblServiceException("表单定义名称重复，请修改表单定义名称");
			}
		}
		String tableName = frmDefVO.getTabname();
		if (!StringUtils.isNotBlank(tableName)) {
			throw new DblServiceException("数据库名称不能为空");
		}
		vos = null;
		try {
			vos = dao.queryByCondition(DblFormDefinitionVO.class, "tabname='" + tableName + "'");
			if (vos != null && vos.length > 0) {
				DblFormDefinitionVO tmpVO = (DblFormDefinitionVO) vos[0];
				if (isNotUpdate) {
					if (!tmpVO.getPk_formdefinition().equalsIgnoreCase(frmDefVO.getPk_formdefinition())) {
						throw new DblServiceException("数据库表名称重复，请修改数据库表名称");
					}
				} else {
					throw new DblServiceException("数据库表名称重复，请修改数据库表名称");
				}
			}
		} catch (DAOException e) {
			e.printStackTrace();
			Logger.error(e.getMessage(), e);
			throw new DblServiceException(e.getMessage(), e);
		}
	}
}
