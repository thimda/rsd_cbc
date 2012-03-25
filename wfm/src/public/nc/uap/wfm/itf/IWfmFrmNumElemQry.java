package nc.uap.wfm.itf;
import nc.uap.wfm.vo.WfmFrmNumElemVO;
public interface IWfmFrmNumElemQry {
	WfmFrmNumElemVO getFrmNumElemVoByPk(String frmNumElemPk);
	WfmFrmNumElemVO getFrmNumElemVOByFrmNumRulePkAndCode(String frmNumRulePk, String code);
	WfmFrmNumElemVO[] getFrmNumElemVOByFrmNumRulePk(String frmNumRulePk);
	String getOrderStr(String frmNumRulePk);
}
