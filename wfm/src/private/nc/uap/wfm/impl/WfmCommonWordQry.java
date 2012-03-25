package nc.uap.wfm.impl;

import nc.bs.dao.DAOException;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.itf.IWfmCommonWordQry;
import nc.uap.wfm.vo.WfmCommonWordVO;


/**
 * 常用语查询接口实现。
 * @author ybo
 * @version 2011-3-17
 * @since NCPortal6.0
 */
public class WfmCommonWordQry implements IWfmCommonWordQry {
	
	/* (non-Javadoc)
	 * @see nc.portal.pwfm.itf.ICommonWordQry#getUserCommonWord(java.lang.String)
	 */
	public WfmCommonWordVO[] getUserCommonWord(String userPk) throws WfmServiceException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			StringBuffer con =  new StringBuffer();
			con.append("(pk_obj='").append(userPk).append("' and scope=").append(WfmCommonWordVO.SCOPE_USER).append(")");
			con.append(" or (pk_obj in(select pk_role from pt_roleuser where pt_roleuser.pk_user='").append(userPk);
			con.append("') or scope=").append(WfmCommonWordVO.SCOPE_ROLE).append(")");
			con.append(" or scope=").append(WfmCommonWordVO.SCOPE_SYS);
			WfmCommonWordVO[] vos = (WfmCommonWordVO[]) dao.queryByCondition(WfmCommonWordVO.class,con.toString());
			return vos;
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see nc.portal.pwfm.itf.ICommonWordQry#getCommonWordByScope(java.lang.String, int)
	 */
	public WfmCommonWordVO[] getCommonWordByScopeObj(String pkObj, int scope)
			throws WfmServiceException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			String con = "(pk_obj='" + pkObj + "' and scope=" + scope;
			WfmCommonWordVO[] vos = (WfmCommonWordVO[]) dao.queryByCondition(WfmCommonWordVO.class,con);
			return vos;
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
	}
}
