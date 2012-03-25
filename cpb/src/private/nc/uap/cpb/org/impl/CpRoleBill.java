package nc.uap.cpb.org.impl;
import nc.bs.dao.DAOException;
import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.extention.CpbExtentionUtil;
import nc.uap.cpb.org.extention.ICpbExtentionService;
import nc.uap.cpb.org.itf.ICpRoleBill;
import nc.uap.cpb.org.util.CpbServiceFacility;
import nc.uap.cpb.org.vos.CpRoleVO;
import nc.uap.cpb.org.vos.CpUserRoleVO;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.lfw.core.log.LfwLogger;
public class CpRoleBill implements ICpRoleBill {
	public String addPtRoleVO(CpRoleVO rolevo) throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			return dao.insertVO(rolevo);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new CpbBusinessException(e);
		}
	}
	public String[] addPtRoleVOs(CpRoleVO[] rolevos) throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			return dao.insertVOs(rolevos);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new CpbBusinessException(e);
		}
	}
	public void updatePtRoleVO(CpRoleVO rolevo) throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.updateVO(rolevo);
			CpUserRoleVO[] roleusers = CpbServiceFacility.getCpUserRoleQry().getPtRoleUserByPkRole(rolevo.getPk_role());
			for (CpUserRoleVO roleuser : roleusers) {
				CpbExtentionUtil.notifyAfterAction(ICpbExtentionService.ROLEMANAGE, ICpbExtentionService.ROLE_RELATE_USER, roleuser);
			}
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new CpbBusinessException(e);
		}
	}
	@Override public void deletePtRoleVO(String pk_role) throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.deleteByPK(CpRoleVO.class, pk_role);
			CpRoleVO rolevo = CpbServiceFacility.getCpRoleQry().getRoleByPk(pk_role);
			deleteRelates(new CpRoleVO[] { rolevo });
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new CpbBusinessException(e);
		}
	}
	public void deletePtRoleVO(CpRoleVO rolevo) throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.deleteVO(rolevo);
			deleteRelates(new CpRoleVO[] { rolevo });
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new CpbBusinessException(e);
		}
	}
	public void deletePtRoleVO(CpRoleVO[] rolevos) throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.deleteVOArray(rolevos);
			deleteRelates(rolevos);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new CpbBusinessException(e);
		}
	}
	private void deleteRelates(CpRoleVO[] rolevos) throws CpbBusinessException {
		for (int i = 0; i < rolevos.length; i++) {
			CpRoleVO rolevo = rolevos[i];
			if(rolevo==null){
				continue;
			}
			CpbServiceFacility.getCpUserRoleBill().deletePtRoleUserByRolepk(rolevo.getPk_role());
			CpbServiceFacility.getCpRoleResourceBill().deleteRoleResourceByRolePk(rolevo.getPk_role());
		}
	}
}
