package nc.uap.cpb.org.impl;
import nc.bs.dao.DAOException;
import nc.jdbc.framework.SQLParameter;
import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.extention.CpbExtentionUtil;
import nc.uap.cpb.org.extention.ICpbExtentionService;
import nc.uap.cpb.org.itf.ICpUserRoleBill;
import nc.uap.cpb.org.util.CpbServiceFacility;
import nc.uap.cpb.org.vos.CpUserRoleVO;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.lfw.core.log.LfwLogger;
public class CpUserRoleBill implements ICpUserRoleBill {
	public String addPtRoleUserVO(CpUserRoleVO roleUservo) throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.insertVO(roleUservo);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new CpbBusinessException(e);
		}
		 CpbExtentionUtil.notifyAfterAction(ICpbExtentionService.USERMANAGE,
				 ICpbExtentionService.USER_RELATE_ROLE, roleUservo.getPk_user());
		return roleUservo.getPk_userrole();
	}
	public void deletePtRoleUserVO(CpUserRoleVO roleUservo) throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.deleteVO(roleUservo);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new CpbBusinessException(e);
		}
		CpbExtentionUtil.notifyAfterAction(ICpbExtentionService.USERMANAGE,
				ICpbExtentionService.USER_DELETE_ROLE, roleUservo.getPk_user());
	}
	public void updatePtRoleUserVO(CpUserRoleVO roleUservo) throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.updateVO(roleUservo);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new CpbBusinessException(e);
		}
	}
	public void addPtRoleUserVOS(CpUserRoleVO[] roleUservos) throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.insertVOs(roleUservos);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new CpbBusinessException(e);
		}
		for(CpUserRoleVO roleUservo:roleUservos){
			CpbExtentionUtil.notifyAfterAction(ICpbExtentionService.USERMANAGE,
				 ICpbExtentionService.USER_RELATE_ROLE, roleUservo.getPk_user());
		}
	}
	@Override public void deletePtRoleUserByUserpks(String[] pk_users) throws CpbBusinessException {
		if (pk_users == null || pk_users.length < 1)
			return;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < pk_users.length; i++) {
			sb.append("'").append(pk_users[i]).append("'");
			if (i != pk_users.length - 1)
				sb.append(",");
		}
		PtBaseDAO dao = new PtBaseDAO();
		String sql = "delete from cp_userrole where pk_user in(" + sb.toString() + ")";
		try {
			dao.executeUpdate(sql);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new CpbBusinessException(e);
		}
		for (String pk_user : pk_users) {
			CpbExtentionUtil.notifyAfterAction(ICpbExtentionService.USERMANAGE, ICpbExtentionService.USER_DELETE_ROLE, pk_user);
		}
	}
	@Override public void deletePtRoleUserByRolepk(String pk_role) throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		String sql = "delete from cp_userrole where pk_role = ?";
		SQLParameter parameter = new SQLParameter();
		parameter.addParam(pk_role);
		try {
			dao.executeUpdate(sql, parameter);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new CpbBusinessException(e);
		}
		CpUserRoleVO[] roleusers = CpbServiceFacility.getCpUserRoleQry().getPtRoleUserByPkRole(pk_role);
		for (CpUserRoleVO roleuser : roleusers) {
			CpbExtentionUtil.notifyAfterAction(ICpbExtentionService.USERMANAGE, ICpbExtentionService.USER_RELATE_ROLE, roleuser.getPk_user());
		}
	}
}