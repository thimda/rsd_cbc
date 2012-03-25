package nc.uap.wfm.impl;
import nc.bs.dao.DAOException;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.itf.IWfmTaskTokenQry;
import nc.uap.wfm.vo.WfmTaskTokenVO;
public class WfmTaskTokenQry implements IWfmTaskTokenQry {
	@Override
	public WfmTaskTokenVO getTaskTokenVoByTokenId(String tokenkId) throws WfmServiceException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			WfmTaskTokenVO[] vos = (WfmTaskTokenVO[]) dao.queryByCondition(WfmTaskTokenVO.class, "token_id='" + tokenkId + "'");
			if (vos != null && vos.length == 1) {
				return vos[0];
			} else {
				return null;
			}
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new WfmServiceException(e.getMessage());
		}
	}
}
