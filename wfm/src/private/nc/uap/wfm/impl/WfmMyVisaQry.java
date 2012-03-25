package nc.uap.wfm.impl;
import nc.bs.dao.DAOException;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.wfm.itf.IWfmMyVisaQry;
import nc.uap.wfm.vo.WfmMyVisaVO;
public class WfmMyVisaQry implements IWfmMyVisaQry {
	@Override
	public WfmMyVisaVO getMyVisaByMyVisaPk(String myVisaPk) {
		try {
			PtBaseDAO dao = new PtBaseDAO();
			WfmMyVisaVO[] vos = (WfmMyVisaVO[]) dao.queryByCondition(WfmMyVisaVO.class, "pk_myvisa='" + myVisaPk + "'");
			if (vos != null && vos.length == 1) {
				return vos[0];
			}
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
		return null;
	}
	@Override
	public WfmMyVisaVO[] getMyVisasByUserPk(String userPk) {
		try {
			PtBaseDAO dao = new PtBaseDAO();
			WfmMyVisaVO[] vos = (WfmMyVisaVO[]) dao.queryByCondition(WfmMyVisaVO.class, "pk_user='" + userPk + "'");
			return vos;
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
	}
}
