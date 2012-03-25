package nc.uap.cpb.org.impl;
import nc.bs.dao.DAOException;
import nc.jdbc.framework.SQLParameter;
import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.itf.ICpUserGroupUserBill;
import nc.uap.cpb.org.vos.CpUserGroupUserVO;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.lfw.core.log.LfwLogger;
public class CpUserGroupUserBill implements ICpUserGroupUserBill {
	public void deleteUserGroupUserByUserGroupUserPk(String userGroupUserPk) throws CpbBusinessException {
		PtBaseDAO baseDAO = new PtBaseDAO();
		SQLParameter parameter = new SQLParameter();
		parameter.addParam(userGroupUserPk);
		try {
			baseDAO.deleteByClause(CpUserGroupUserVO.class, " PK_USERGROUPUSER = ? ", parameter);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new CpbBusinessException(e);
		}
	}
}
