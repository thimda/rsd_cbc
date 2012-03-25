package nc.uap.ctrl.tpl.print;

import nc.uap.ctrl.tpl.exp.TplBusinessException;
import nc.uap.ctrl.tpl.print.base.CpPrintConditionVO;
import nc.uap.ctrl.tpl.print.base.CpPrintTemplateVO;

public interface ICpPrintTemplateInnerService {
	public void initTemplate(CpPrintTemplateVO templateVo) throws TplBusinessException;
	public void updateTemplate(CpPrintTemplateVO templateVo) throws TplBusinessException;
	public void deleteTemplate(String pk_template) throws TplBusinessException;
	public void initConditons(String pk_template, CpPrintConditionVO[] conditions) throws TplBusinessException;
}
