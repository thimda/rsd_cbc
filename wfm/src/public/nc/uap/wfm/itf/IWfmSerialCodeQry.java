package nc.uap.wfm.itf;
import nc.uap.wfm.vo.WfmSerialCodeVO;
public interface IWfmSerialCodeQry {
	WfmSerialCodeVO[] getSerialCodeByFrmNumElemPk(String frmNumElemPk);
	WfmSerialCodeVO getMaxSerialCodeByFrmNumElemPk(String frmNumElemPk);
	WfmSerialCodeVO[] getNotOccupySerialCodeByFrmNumElemPk(String frmNumElemPk);
}
