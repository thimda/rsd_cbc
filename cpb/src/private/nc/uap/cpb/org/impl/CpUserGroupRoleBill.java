package nc.uap.cpb.org.impl;
import nc.bs.dao.DAOException;
import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.itf.ICpUserGroupRoleBill;
import nc.uap.cpb.org.vos.CpUserGroupRoleVO;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.lfw.core.log.LfwLogger;
public class CpUserGroupRoleBill implements ICpUserGroupRoleBill {
	public String addPtUserGroupRoleVO(CpUserGroupRoleVO usergroupRolevo) throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.insertVO(usergroupRolevo);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new CpbBusinessException(e);
		}
		//PmngExtentionUtil.notifyAfterAction(IPMngExtentionService.USERGROUPMANAGE, IPMngExtentionService.USERGROUP_RELATE_ROLE, usergroupRolevo.getPk_usergroup());
		return usergroupRolevo.getPk_usergrouprole();
	}
	public void deletePtUserGroupRoleVO(CpUserGroupRoleVO usergroupRolevo) throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.deleteVO(usergroupRolevo);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new CpbBusinessException(e);
		}
		//PmngExtentionUtil.notifyAfterAction(IPMngExtentionService.USERGROUPMANAGE, IPMngExtentionService.USERGROUP_DELETE_ROLE, usergroupRolevo.getPk_usergroup());
	}
	public void updatePtUserGroupRoleVO(CpUserGroupRoleVO usergroupRolevo) throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.updateVO(usergroupRolevo);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new CpbBusinessException(e);
		}
	}
	public String[] addPtUserGroupRoleVOs(CpUserGroupRoleVO[] usergroupRolevos) throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		if (usergroupRolevos == null || usergroupRolevos.length == 0) {
			return null;
		}
		CpUserGroupRoleVO tmpVo = null;
		for (int i = 0; i < usergroupRolevos.length; i++) {
			tmpVo = usergroupRolevos[i];
			tmpVo.setDr(0);
		}
		String[] pks;
		try {
			pks = dao.insertVOs(usergroupRolevos);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new CpbBusinessException(e);
		}
		//PmngExtentionUtil.notifyAfterAction(IPMngExtentionService.USERGROUPMANAGE, IPMngExtentionService.USERGROUP_RELATE_ROLE, usergroupRolevos[0].getPk_usergroup());
		return pks;
	}
}
