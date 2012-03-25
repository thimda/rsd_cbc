package nc.uap.wfm.dftimpl;
import java.text.SimpleDateFormat;
import java.util.Date;
import nc.bs.framework.common.NCLocator;
import nc.uap.wfm.constant.WfmConstants;
import nc.uap.wfm.contanier.ProDefsContainer;
import nc.uap.wfm.engine.IFrmNumBillGen;
import nc.uap.wfm.itf.IWfmFrmNumElemQry;
import nc.uap.wfm.itf.IWfmFrmNumRuleQry;
import nc.uap.wfm.itf.IWfmSerialCodeBill;
import nc.uap.wfm.itf.IWfmSerialCodeQry;
import nc.uap.wfm.model.ProDef;
import nc.uap.wfm.vo.WfmFrmNumElemVO;
import nc.uap.wfm.vo.WfmFrmNumRuleVO;
import nc.uap.wfm.vo.WfmSerialCodeVO;
import nc.vo.pub.lang.UFBoolean;
public class DefaultFrmBillNumGen implements IFrmNumBillGen {
	public String getValue(String flowTypePk, String taskPk) {
		return null;
	}
	public boolean isBefore(String flowTypePk, String taskPk) {
		return false;
	}
	public String genFrmNumBillByFrmDefPk_RequiresNew(String flowTypePk) {
		ProDef proDef = ProDefsContainer.getProDefByFlowTypePk(flowTypePk);
		return this.genFrmNumBill(proDef.getPk_prodef(), proDef.getId());
	}
	public String genFrmNumBillByProDefPk_RequiresNew(String proDefPk) {
		ProDef proDef = ProDefsContainer.getByProDefPkAndId(proDefPk,null);
		return this.genFrmNumBill(proDef.getPk_prodef(), proDef.getId());
	}
	public String genFrmNumBill(String proDefPk, String proDefId) {
		IWfmFrmNumRuleQry frmNumRuleQry = NCLocator.getInstance().lookup(IWfmFrmNumRuleQry.class);
		WfmFrmNumRuleVO frmNumRuleVo = frmNumRuleQry.getFrmNumRuleVoByProDefPkAndId(proDefPk, proDefId);
		if (frmNumRuleVo == null) {
			return "";
		}
		IWfmFrmNumElemQry frmNumElemQry = NCLocator.getInstance().lookup(IWfmFrmNumElemQry.class);
		WfmFrmNumElemVO[] vos = frmNumElemQry.getFrmNumElemVOByFrmNumRulePk(frmNumRuleVo.getPk_frmnumrule());
		if (vos == null) {
			return "";
		}
		return this.genFrmNumBill(frmNumRuleVo, vos);
	}
	public String genFrmNumBill(WfmFrmNumRuleVO frmNumRuleVo, WfmFrmNumElemVO[] vos) {
		int length = vos.length;
		WfmFrmNumElemVO frmNumElemVo = null;
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < length; i++) {
			frmNumElemVo = vos[i];
			buffer.append(this.genFrmNumBill(frmNumRuleVo, frmNumElemVo));
		}
		return buffer.toString();
	}
	public String genFrmNumBill(WfmFrmNumRuleVO frmNumRuleVo, WfmFrmNumElemVO frmNumElemVo) {
		String codeType = frmNumElemVo.getCode();
		String currentValue = null;
		if (WfmConstants.FrmNumBillType_SerialCode.equalsIgnoreCase(codeType)) {
			currentValue = this.genFrmNumBySerialCode(frmNumRuleVo, frmNumElemVo);
		} else if (WfmConstants.FrmNumBillType_Const.equalsIgnoreCase(codeType)) {
			currentValue = this.genFrmNumByConst(frmNumElemVo);
		} else if (WfmConstants.FrmNumBillType_CurrentDate.equalsIgnoreCase(codeType)) {
			currentValue = this.genFrmNumByCurrentDate(frmNumElemVo);
		}
		return this.genFrmNumBill(currentValue, frmNumRuleVo, frmNumElemVo);
	}
	public String genFrmNumBySerialCode(WfmFrmNumRuleVO frmNumRuleVo, WfmFrmNumElemVO frmNumElemVo) {
		WfmSerialCodeVO historyVo = NCLocator.getInstance().lookup(IWfmSerialCodeQry.class).getMaxSerialCodeByFrmNumElemPk(frmNumElemVo.getPk_frmnumelem());
		String currentValue = null;
		WfmSerialCodeVO serialCodeVo = new WfmSerialCodeVO();
		serialCodeVo.setPk_frmnumelem(frmNumElemVo.getPk_frmnumelem());
		serialCodeVo.setIsoccupy(UFBoolean.TRUE);
		if (historyVo == null) {
			currentValue = frmNumElemVo.getStartvalue();
			serialCodeVo.setCurrentvalue(currentValue);
		} else {
			currentValue = historyVo.getCurrentvalue();
			currentValue = String.valueOf(Integer.parseInt(currentValue) + Integer.parseInt(frmNumElemVo.getStepvalue()));
			serialCodeVo.setCurrentvalue(currentValue);
		}
		NCLocator.getInstance().lookup(IWfmSerialCodeBill.class).addSerialCode(serialCodeVo);
		return currentValue;
	}
	public String genFrmNumBill(String currentValue, WfmFrmNumRuleVO frmNumRuleVo, WfmFrmNumElemVO frmNumElemVo) {
		if (currentValue == null || currentValue.length() == 0) {
			return "";
		}
		String strAllLength = frmNumElemVo.getAlllength();
		if (strAllLength == null || strAllLength.length() == 0) {
			return currentValue;
		}
		int intALlLength = Integer.parseInt(strAllLength);
		int intCurrentLength = currentValue.length();
		String fillValue = frmNumElemVo.getFillvalue();
		String fillType = frmNumElemVo.getFilltype();
		while (intCurrentLength < intALlLength) {
			if (WfmConstants.FrmNumFillType_Pre.equalsIgnoreCase(fillType)) {
				currentValue = fillValue + currentValue;
			}
			if (WfmConstants.FrmNumFillType_After.equalsIgnoreCase(fillType)) {
				currentValue = currentValue + fillValue;
			}
			intCurrentLength = currentValue.length();
		}
		int stepLength = intCurrentLength - intALlLength;
		if (intCurrentLength > intALlLength) {
			if (WfmConstants.FrmNumFillType_Pre.equalsIgnoreCase(fillType)) {
				currentValue = currentValue.substring(stepLength, intCurrentLength);
			}
			if (WfmConstants.FrmNumFillType_After.equalsIgnoreCase(fillType)) {
				currentValue = currentValue.substring(0, intCurrentLength - stepLength);
			}
		}
		return currentValue;
	}
	public String genFrmNumByConst(WfmFrmNumElemVO frmNumElemVo) {
		return frmNumElemVo.getConstvalue();
	}
	public String genFrmNumByCurrentDate(WfmFrmNumElemVO frmNumElemVo) {
		String pattern = frmNumElemVo.getDatepartten();
		String returnValue = new SimpleDateFormat(pattern).format(new Date());
		return returnValue;
	}
}
