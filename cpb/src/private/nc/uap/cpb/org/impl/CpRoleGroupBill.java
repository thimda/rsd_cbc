package nc.uap.cpb.org.impl;

import nc.bs.dao.DAOException;
import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.itf.ICpRoleGroupBill;
import nc.uap.cpb.org.util.CpbServiceFacility;
import nc.uap.cpb.org.vos.CpRoleGroupVO;
import nc.uap.cpb.org.vos.CpRoleVO;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.log.LfwLogger;
import nc.vo.pub.lang.UFDate;

public class CpRoleGroupBill implements ICpRoleGroupBill {
	
	@Override
	public CpRoleGroupVO[] initRoleGroupVos(String pk_org) throws CpbBusinessException {
		CpRoleGroupVO vo1 = new CpRoleGroupVO();
		vo1.setDr(0);
		vo1.setGroupcode("admin");
		vo1.setGroupname("管理类型");
		vo1.setPk_org(pk_org);
		vo1.setDatecreated(new UFDate());
		vo1.setType(CpRoleGroupVO.ROLEGROUPTYPE_ADMIN);
		vo1.setUsercreated(LfwRuntimeEnvironment.getLfwSessionBean().getPk_user());
		
		CpRoleGroupVO vo2 = new CpRoleGroupVO();
		vo2.setDr(0);
		vo2.setGroupcode("business");
		vo2.setGroupname("业务类型");
		vo2.setPk_org(pk_org);
		vo2.setDatecreated(new UFDate());
		vo2.setType(CpRoleGroupVO.ROLEGROUPTYPE_BUSINESS);
		vo2.setUsercreated(LfwRuntimeEnvironment.getLfwSessionBean().getPk_user());
		
		CpRoleGroupVO[] rolegroups = new CpRoleGroupVO[]{vo1,vo2};
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.insertVOs(rolegroups);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new CpbBusinessException(e);
		}	
		return rolegroups;
	}

	public String addPtRoleGroupVO(CpRoleGroupVO rolegroupvo) throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			return dao.insertVO(rolegroupvo);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new CpbBusinessException(e);
		}
	}

	public void deletePtRoleGroupVO(String pk_rolegroup) throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.deleteByPK(CpRoleGroupVO.class, pk_rolegroup);
			CpRoleVO[] rolevos = CpbServiceFacility.getCpRoleQry().getRoleByRoleGroup(pk_rolegroup);
			CpbServiceFacility.getCpRoleBill().deletePtRoleVO(rolevos);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new CpbBusinessException(e);
		}
	}

	public void deletePtRoleGroupVO(CpRoleGroupVO rolegroupvo) throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.deleteVO(rolegroupvo);
			CpRoleVO[] rolevos = CpbServiceFacility.getCpRoleQry().getRoleByRoleGroup(rolegroupvo.getPk_rolegroup());
			CpbServiceFacility.getCpRoleBill().deletePtRoleVO(rolevos);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new CpbBusinessException(e);
		}
	}

	@Override
	public void deletePtRoleGroupVOs(CpRoleGroupVO[] rolegroupvos) throws CpbBusinessException {
		if (rolegroupvos == null || rolegroupvos.length < 1)
			return;
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.deleteVOArray(rolegroupvos);
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < rolegroupvos.length; i++) {
				CpRoleGroupVO rg = rolegroupvos[i];
				sb.append("'").append(rg.getPk_rolegroup()).append("'");
				if (i != rolegroupvos.length - 1)
					sb.append(",");
			}
			String condition = " pk_rolegroup in(" + sb.toString() + ") ";
			CpRoleVO[] rolevos = CpbServiceFacility.getCpRoleQry().getAllRoleByCondition(CpRoleVO.class, condition);
			CpbServiceFacility.getCpRoleBill().deletePtRoleVO(rolevos);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new CpbBusinessException(e);
		}
	}

	public void updatePtRoleGroupVO(CpRoleGroupVO rolegroupvo) throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.updateVO(rolegroupvo);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new CpbBusinessException(e);
		}
	}

	public String[] addPtRoleGroupVOs(CpRoleGroupVO[] rolegroupvos) throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			return dao.insertVOs(rolegroupvos);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new CpbBusinessException(e);
		}
	}
}
