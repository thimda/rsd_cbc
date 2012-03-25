package nc.uap.wfm.impl;
import nc.bs.dao.DAOException;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.wfm.itf.IWfmFlowCateQry;
import nc.uap.wfm.vo.WfmFlwCatVO;
public class WfmFlowCateQry implements IWfmFlowCateQry {
	public WfmFlwCatVO[] getAllFlowCate() {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			WfmFlwCatVO[] vos = (WfmFlwCatVO[]) dao.queryByCondition(WfmFlwCatVO.class, null);
			return vos;
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
	}
}
