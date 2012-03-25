package nc.uap.cpb.org.impl;
import nc.bs.dao.DAOException;
import nc.uap.cpb.org.itf.ICpCommonWordQry;
import nc.uap.cpb.org.vos.CpCommonWordVO;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
/**
 * 常用语查询接口实现。
 * 
 * @author ybo
 * @version 2011-3-17
 * @since NCPortal6.0
 */
public class CpCommonWordQry implements ICpCommonWordQry {
	public CpCommonWordVO[] getUserCommonWord(String userPk) {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			StringBuffer con = new StringBuffer();
			con.append("(pk_obj='").append(userPk).append("' and scope=").append(CpCommonWordVO.SCOPE_USER).append(")");
			con.append(" or (pk_obj in(select pk_role from pt_roleuser where pt_roleuser.pk_user='").append(userPk);
			con.append("') or scope=").append(CpCommonWordVO.SCOPE_ROLE).append(")");
			con.append(" or scope=").append(CpCommonWordVO.SCOPE_SYS);
			CpCommonWordVO[] vos = (CpCommonWordVO[]) dao.queryByCondition(CpCommonWordVO.class, con.toString());
			return vos;
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
	}
	public CpCommonWordVO[] getCommonWordByScopeObj(String pkObj, int scope) {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			String con = "(pk_obj='" + pkObj + "' and scope=" + scope;
			CpCommonWordVO[] vos = (CpCommonWordVO[]) dao.queryByCondition(CpCommonWordVO.class, con);
			return vos;
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
	}
}
