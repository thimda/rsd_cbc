package nc.uap.wfm.impl;
import nc.bs.dao.DAOException;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.wfm.itf.IWfmSerialCodeQry;
import nc.uap.wfm.vo.WfmSerialCodeVO;
public class WfmSerialCodeQry implements IWfmSerialCodeQry {
	@Override
	public WfmSerialCodeVO getMaxSerialCodeByFrmNumElemPk(String frmNumElemPk) {
		PtBaseDAO dao = new PtBaseDAO();
		String strWhere = "currentvalue= (select max(currentvalue) from wfm_serialcode where pk_frmnumelem='" + frmNumElemPk + "') and pk_frmnumelem='" + frmNumElemPk + "'";
		WfmSerialCodeVO[] vos = null;
		try {
			vos = (WfmSerialCodeVO[]) dao.queryByCondition(WfmSerialCodeVO.class, strWhere);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
		}
		if (vos == null || vos.length == 0) {
			return null;
		}
		if (vos.length == 1) {
			return vos[0];
		} else {
			return null;
		}
	}
	@Override
	public WfmSerialCodeVO[] getNotOccupySerialCodeByFrmNumElemPk(String frmNumElemPk) {
		return null;
	}
	@Override
	public WfmSerialCodeVO[] getSerialCodeByFrmNumElemPk(String frmNumElemPk) {
		return null;
	}
}
