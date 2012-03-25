package nc.uap.cpb.org.itf;

import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.vos.CpDeviceVO;

public interface ICpDeviceQry {
	CpDeviceVO getDeviceVoByPk(String devicePk) throws CpbBusinessException;
	CpDeviceVO[] getAllDevice() throws CpbBusinessException;
}
