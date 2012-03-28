package nc.uap.ctrl.tpl.print;

import nc.uap.ctrl.tpl.exp.TplBusinessException;
import nc.uap.ctrl.tpl.print.base.CpPrintConditionVO;
import nc.uap.ctrl.tpl.print.base.CpPrintTemplateTotalVO;
import nc.uap.ctrl.tpl.print.base.CpPrintTemplateVO;

public interface ICpPrintTemplateInnerQryService {
	public CpPrintConditionVO[] getPrintConditions(String pk_template) throws TplBusinessException;

	public CpPrintTemplateTotalVO getPrintTotalVO(String pk_template) throws TplBusinessException;

	public String getPrintTemplatePkByNode(String nodeCode) throws TplBusinessException;
	
	public CpPrintTemplateVO getPrintTemplateVO(String pk_template) throws TplBusinessException;
	
	public CpPrintTemplateVO[] getPrintTemplates(String nodecode) throws TplBusinessException;
}
