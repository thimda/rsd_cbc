package nc.uap.ctrl.tpl.gz;

import nc.uap.ctrl.tpl.exp.TplBusinessException;
import nc.uap.ctrl.tpl.gz.base.CpGzConditionVO;
import nc.uap.ctrl.tpl.gz.base.CpGzTemplateVO;

/**
 * 规则查询模板
 * @author zhangxya
 *
 */
public interface ICpGzTemplateInnerService {
	public void initTemplate(CpGzTemplateVO templateVo) throws TplBusinessException;
	public void updateTemplate(CpGzTemplateVO templateVo) throws TplBusinessException;
	public void deleteTemplate(String pk_template) throws TplBusinessException;
	public void initConditons(String pk_template, CpGzConditionVO[] conditions) throws TplBusinessException;
}
