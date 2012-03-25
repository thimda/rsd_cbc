package nc.uap.wfm.itf;
import nc.uap.wfm.vo.WfmFrmNumElemVO;
public interface IWfmFrmNumElemBill {
	void addFrmNumElem(WfmFrmNumElemVO frmNumElemVo);
	void updateFrmNumElem(WfmFrmNumElemVO frmNumElemVo);
	void deleteFrmNumElemByPk(String frmNumElemPk);
	void saveOrUpdateFrmNumElem(WfmFrmNumElemVO frmNumElemVo);

}
