package nc.uap.wfm.impl;
import nc.bs.dao.DAOException;
import nc.jdbc.framework.processor.ColumnProcessor;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.itf.IWfmProInsStateQry;
import nc.uap.wfm.model.ProIns;
public class WfmProInsStateQry implements IWfmProInsStateQry {
	@Override
	public String getProInsState(String proInsPk) throws WfmServiceException {
		if (proInsPk == null || proInsPk.length() == 0) {
			return null;
		}
		String state = "";
		String sql = "select top 1 a.state from wfm_proinsstate a where a.pk_proins='" + proInsPk + "' order by a.actiondate desc";
		PtBaseDAO dao = new PtBaseDAO();
		try {
			state = (String) dao.executeQuery(sql, new ColumnProcessor());
			if (state == null || state.length() == 0) {
				return ProIns.Run;
			} else {
				return state.trim();
			}
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new WfmServiceException(e.getMessage());
		}
	}
}
