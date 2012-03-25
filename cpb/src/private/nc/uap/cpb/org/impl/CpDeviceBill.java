package nc.uap.cpb.org.impl;
import nc.bs.dao.DAOException;
import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.itf.ICpDeviceBill;
import nc.uap.cpb.org.vos.CpDeviceVO;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.lfw.core.log.LfwLogger;
public class CpDeviceBill implements ICpDeviceBill {
	@Override public CpDeviceVO[] getAllDevice() throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		CpDeviceVO[] vos = null;
		try {
			vos = (CpDeviceVO[]) dao.queryByCondition(CpDeviceVO.class, "");
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new CpbBusinessException(e.getMessage());
		}
		return vos;
	}
	@Override public CpDeviceVO getDeviceVoByPk(String devicePk) throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		CpDeviceVO[] vos = null;
		try {
			vos = (CpDeviceVO[]) dao.queryByCondition(CpDeviceVO.class, "pk_device='" + devicePk + "'");
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new CpbBusinessException(e.getMessage());
		}
		if (vos != null && vos.length == 1) {
			return vos[0];
		} else {
			return null;
		}
	}
}
