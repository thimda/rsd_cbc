package nc.uap.wfm.impl;
import nc.bs.dao.DAOException;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.itf.IWfmPageAttachQry;
import nc.uap.wfm.vo.WfmPageAttachVO;
/****
 * 查询纸质附件实现类
 * @author zhangxya
 *
 */
public class WfmRealAttachBill implements IWfmPageAttachQry {
	@Override
	public WfmPageAttachVO[] getAttachFile(String sql) throws WfmServiceException {
		PtBaseDAO dao = new PtBaseDAO();
		WfmPageAttachVO[] attachFiles = null;
		try {
			attachFiles = (WfmPageAttachVO[]) dao.queryByCondition(WfmPageAttachVO.class, sql);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new WfmServiceException(e);
		}
		return attachFiles;
	}
}
