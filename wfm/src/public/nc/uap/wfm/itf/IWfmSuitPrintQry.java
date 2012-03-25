package nc.uap.wfm.itf;
import nc.uap.wfm.vo.WfmSuitPrintVO;
public interface IWfmSuitPrintQry {
	WfmSuitPrintVO[] getSuitPrintVo(String pk_formdefintion);
	WfmSuitPrintVO getSuitPrintVoByPk(String suitPrintPk);
}
