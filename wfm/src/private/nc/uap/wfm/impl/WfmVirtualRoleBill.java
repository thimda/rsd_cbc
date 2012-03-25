package nc.uap.wfm.impl;
import nc.bs.dao.DAOException;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.wfm.itf.IWfmVirtualRoleBill;
import nc.uap.wfm.vo.WfmVirtualRoleVO;
public class WfmVirtualRoleBill implements IWfmVirtualRoleBill {
	public void deleteWfmVirtRoleByPk(String virtRolePk) {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.deleteByClause(WfmVirtualRoleVO.class, WfmVirtualRoleVO.PK_VIRTUALROLE + "='" + virtRolePk + "'");
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
	}
	public void saveWfmVirtRole(WfmVirtualRoleVO vo) {
		PtBaseDAO dao = new PtBaseDAO();
		vo.setDr(0);
		if (vo.getPk_virtualrole() == null || vo.getPk_virtualrole().length() == 0) {
			try {
				dao.insertVO(vo);
			} catch (DAOException e) {
				LfwLogger.error(e.getMessage(), e);
				throw new LfwRuntimeException(e.getMessage());
			}
		} else {
			try {
				dao.updateVO(vo);
			} catch (DAOException e) {
				LfwLogger.error(e.getMessage(), e);
				throw new LfwRuntimeException(e.getMessage());
			}
		}
	}
}
