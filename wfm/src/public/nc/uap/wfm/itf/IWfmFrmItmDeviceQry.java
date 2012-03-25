package nc.uap.wfm.itf;

import java.util.Map;

import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.vo.WfmFrmDeviceVO;

public interface IWfmFrmItmDeviceQry {
	WfmFrmDeviceVO[] getFrmItmDeviceVos(String pk_device, String proDefPk, String proDefId, String portId) throws WfmServiceException;
	Map<String, WfmFrmDeviceVO> getFrmItmDeviceMap(String pk_device, String proDefPk, String proDefId, String portId) throws WfmServiceException;
	WfmFrmDeviceVO[] getFrmItmDeviceVos(String proDefPk, String proDefId, String portId) throws WfmServiceException;
}
