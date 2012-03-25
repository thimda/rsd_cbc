package nc.uap.cpb.org.impl;
import java.util.List;
import nc.bs.dao.DAOException;
import nc.jdbc.framework.SQLParameter;
import nc.jdbc.framework.processor.BeanListProcessor;
import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.itf.ICpUserGroupUserQry;
import nc.uap.cpb.org.vos.CpUserGroupUserVO;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.lfw.core.log.LfwLogger;
public class CpUserGroupUserQry implements ICpUserGroupUserQry {
	@SuppressWarnings("unchecked") public CpUserGroupUserVO[] getUserGroupUserVosByUserPk(String userPk) throws CpbBusinessException {
		PtBaseDAO baseDAO = new PtBaseDAO();
		String sql = "select * from cp_usergroupuser p  where p.pk_user = ?";
		SQLParameter parameter = new SQLParameter();
		parameter.addParam(userPk);
		List<CpUserGroupUserVO> list = null;
		try {
			list = (List<CpUserGroupUserVO>) baseDAO.executeQuery(sql, parameter, new BeanListProcessor(CpUserGroupUserVO.class));
			if (list == null || list.size() == 0) {
				return new CpUserGroupUserVO[] {};
			}
			return list.toArray(new CpUserGroupUserVO[list.size()]);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new CpbBusinessException(e);
		}
	}
}
