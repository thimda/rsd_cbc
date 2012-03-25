package nc.uap.wfm.impl;

import java.util.HashMap;
import java.util.Map;

import nc.bs.dao.DAOException;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.itf.IWfmFrmItmDeviceQry;
import nc.uap.wfm.vo.WfmFrmDeviceVO;

public class WfmFrmItmDeviceQry implements IWfmFrmItmDeviceQry {
	@Override
	public WfmFrmDeviceVO[] getFrmItmDeviceVos(String pk_device, String proDefPk, String proDefId, String portId) throws WfmServiceException {
		PtBaseDAO dao = new PtBaseDAO();
		String where = "pk_device='" + pk_device + "' and pk_prodef='" + proDefPk + "' and prodef_id='" + proDefId + "' and port_id='" + portId + "'";
		WfmFrmDeviceVO[] vos = null;
		try {
			vos = (WfmFrmDeviceVO[]) dao.queryByCondition(WfmFrmDeviceVO.class, where);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new WfmServiceException(e.getMessage());
		}
		return vos;
	}
	@Override
	public WfmFrmDeviceVO[] getFrmItmDeviceVos(String proDefPk, String proDefId, String portId) throws WfmServiceException {
		PtBaseDAO dao = new PtBaseDAO();
		String where = "pk_prodef='" + proDefPk + "' and prodef_id='" + proDefId + "' and port_id='" + portId + "'";
		WfmFrmDeviceVO[] vos = null;
		try {
			vos = (WfmFrmDeviceVO[]) dao.queryByCondition(WfmFrmDeviceVO.class, where);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new WfmServiceException(e.getMessage());
		}
		return vos;
	}
	@Override
	public Map<String, WfmFrmDeviceVO> getFrmItmDeviceMap(String pk_device, String proDefPk, String proDefId, String portId) throws WfmServiceException {
		WfmFrmDeviceVO[] vos = this.getFrmItmDeviceVos(pk_device, proDefPk, proDefId, portId);
		if (vos == null || vos.length == 0) {
			return null;
		}
		Map<String, WfmFrmDeviceVO> map = new HashMap<String, WfmFrmDeviceVO>();
		for (int i = 0; i < vos.length; i++) {
			map.put(vos[i].getPk_frmitm(), vos[i]);
		}
		return map;
	}
}
