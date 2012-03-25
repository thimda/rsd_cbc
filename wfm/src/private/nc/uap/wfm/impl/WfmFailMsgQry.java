package nc.uap.wfm.impl;
import nc.bs.dao.DAOException;
import nc.bs.logging.Logger;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.wfm.itf.IWfmFailMsgQry;
import nc.uap.wfm.vo.WfmFailMsgVO;
public class WfmFailMsgQry implements IWfmFailMsgQry {
	@Override
	public WfmFailMsgVO[] getFailMsgByTabName(String tableName) {
		PtBaseDAO dao = new PtBaseDAO();
		WfmFailMsgVO[] vos = null;
		try {
			vos = (WfmFailMsgVO[]) dao.queryByCondition(WfmFailMsgVO.class, "tabname='" + tableName + "'");
		} catch (DAOException e) {
			Logger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage(), e);
		}
		return vos;
	}
}
