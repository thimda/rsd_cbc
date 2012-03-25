package nc.uap.wfm.impl;
import nc.bs.dao.DAOException;
import nc.jdbc.framework.SQLParameter;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.itf.IWfmMyVisaBill;
import nc.uap.wfm.vo.WfmMyVisaVO;
public class WfmMyVisaBill implements IWfmMyVisaBill {

	public void saveMyVisa(WfmMyVisaVO myVisaVo) {
		try {
			PtBaseDAO dao = new PtBaseDAO();
			dao.insertVO(myVisaVo);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
	}

	public void deleteMyVisByFilePk(String pk_file) throws WfmServiceException{
		PtBaseDAO dao = new PtBaseDAO();
		String sql = "update wfm_myvisa set dr = 1 WHERE pk_lfwfile = ?";
		SQLParameter param = new SQLParameter();
		param.addParam(pk_file);
		try {
			dao.executeUpdate(sql, param);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			LfwLogger.error(e.getMessage(), e);
			throw new WfmServiceException(e);
		}
		
	}
}
