package nc.uap.wfm.itf;

import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.vo.WfmFrmDeviceVO;

public interface IWfmFrmItmDeviceBill {
	void saveOrUpdate(WfmFrmDeviceVO[] vos) throws WfmServiceException;
	void saveOrUpdate(WfmFrmDeviceVO vo) throws WfmServiceException;
	void delete(String proDefPk,String proDefId,String portId) throws WfmServiceException;
}
