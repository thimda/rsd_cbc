package nc.uap.ctrl.tpl.qry;

import nc.uap.ctrl.tpl.exp.TplBusinessException;
import nc.uap.ctrl.tpl.qry.base.CpQueryConditionVO;
import nc.uap.ctrl.tpl.qry.base.CpQueryTemplateVO;

public interface ICpQryTemplateInnerService {
	public void initTemplate(CpQueryTemplateVO templateVo) throws TplBusinessException;
	public void updateTemplate(CpQueryTemplateVO templateVo) throws TplBusinessException;
	public void deleteTemplate(String pk_template) throws TplBusinessException;
	public void initConditons(String pk_template, CpQueryConditionVO[] conditions) throws TplBusinessException;
}
