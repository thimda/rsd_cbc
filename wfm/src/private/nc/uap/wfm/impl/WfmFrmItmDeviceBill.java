package nc.uap.wfm.impl;

import nc.bs.dao.DAOException;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.itf.IWfmFrmItmDeviceBill;
import nc.uap.wfm.vo.WfmFrmDeviceVO;

public class WfmFrmItmDeviceBill implements IWfmFrmItmDeviceBill {
	@Override
	public void saveOrUpdate(WfmFrmDeviceVO[] vos) throws WfmServiceException {
		if (vos == null || vos.length == 0) {
			return;
		}
		PtBaseDAO dao = new PtBaseDAO();
		WfmFrmDeviceVO vo = null;
		String frmItmDevicePk = null;
		try {
			for (int i = 0; i < vos.length; i++) {
				vo = vos[i];
				frmItmDevicePk = vo.getPk_frmdevice();
				if (frmItmDevicePk == null || frmItmDevicePk.length() == 0) {
					dao.insertVO(vo);
				} else {
					dao.updateVO(vo);
				}
			}
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new WfmServiceException(e.getMessage());
		}
	}
	@Override
	public void saveOrUpdate(WfmFrmDeviceVO vo) throws WfmServiceException {
		this.saveOrUpdate(new WfmFrmDeviceVO[] { vo });
	}
	@Override
	public void delete(String proDefPk, String proDefId, String portId) throws WfmServiceException {
		PtBaseDAO dao = new PtBaseDAO();
		String sql = "delete from wfm_frmdevice where pk_prodef='" + proDefPk + "' and prodef_id='" + proDefId + "' and port_id='" + portId + "'";
		try {
			dao.executeUpdate(sql);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new WfmServiceException(e.getMessage());
		}
	}
}
