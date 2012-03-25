package nc.uap.cpb.org.impl;
import java.util.ArrayList;

import nc.bs.dao.DAOException;
import nc.jdbc.framework.processor.ColumnListProcessor;
import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.itf.ICpUserGroupBill;
import nc.uap.cpb.org.vos.CpUserGroupVO;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
public class CpUserGroupBill implements ICpUserGroupBill {
	 public String addPtUserGroupVO(CpUserGroupVO usergroupvo) throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			return dao.insertVO(usergroupvo);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new CpbBusinessException(e);
		}
	}
	 public void addPtUserGroupVOWithPk(CpUserGroupVO[] usergroupvos) throws CpbBusinessException {
			PtBaseDAO dao = new PtBaseDAO();
			try {
				dao.insertVOWithPKs(usergroupvos);
			} catch (DAOException e) {
				LfwLogger.error(e.getMessage(), e);
				throw new CpbBusinessException(e);
			}
		}
	public void deletePtUserGroupVO(String pk_usergroup) throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.deleteByPK(CpUserGroupVO.class, pk_usergroup);
			this.deleteReleted(pk_usergroup);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new CpbBusinessException(e);
		}
	}
	public void deletePtUserGroupVO(CpUserGroupVO usergroupvo) throws CpbBusinessException {
		this.deletePtUserGroupVOs(new CpUserGroupVO[] { usergroupvo });
	}
	public void deletePtUserGroupVOs(CpUserGroupVO[] usergroupvos) throws CpbBusinessException {
		if (usergroupvos == null || usergroupvos.length == 0) {
			return;
		}
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.deleteVOArray(usergroupvos);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
		StringBuffer sb = new StringBuffer();
		int length = usergroupvos.length;
		for (int i = 0; i < length; i++) {
			sb.append("'").append(usergroupvos[i].getPk_usergroup()).append("'");
			if (i == length - 1) {
				break;
			} else {
				sb.append(",");
			}
		}
		this.deleteReleted(sb.toString());
	}
	private void deleteReleted(String userGroupPks) throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			String sql1 = "delete cp_usergrouprole where pk_usergroup in(" + userGroupPks + ")";
			dao.executeUpdate(sql1);
			String sql2 = "delete cp_usergroupuser where pk_usergroup in(" + userGroupPks + ")";
			dao.executeUpdate(sql2);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new CpbBusinessException(e);
		}
	}
	public void updatePtUserGroupVO(CpUserGroupVO usergroupvo) throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.updateVO(usergroupvo);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new CpbBusinessException(e);
		}
	}
	@Override
	public boolean isReference(CpUserGroupVO usergroupvo)
			throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			String sql = "select count(cuserid) from cp_user where pk_usergroupforcreate = '"+usergroupvo.getPk_group()+"'";
			ArrayList<Integer> obj = (ArrayList<Integer>) dao.executeQuery(sql, new ColumnListProcessor());
			if(obj.get(0)>0)
				return true;
			} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new CpbBusinessException(e);
		}
		return false;
	}
}
