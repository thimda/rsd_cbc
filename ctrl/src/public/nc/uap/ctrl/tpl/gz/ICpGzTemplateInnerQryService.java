package nc.uap.ctrl.tpl.gz;

import nc.uap.ctrl.tpl.exp.TplBusinessException;
import nc.uap.ctrl.tpl.gz.base.CpGzConditionVO;
import nc.uap.ctrl.tpl.gz.base.CpGzTemplateTotalVO;

public interface ICpGzTemplateInnerQryService {
	public CpGzConditionVO[] getGzConditions(String pk_template) throws TplBusinessException;

	public CpGzTemplateTotalVO getQueryTotalVO(String pk_template) throws TplBusinessException;

	public String getGzTemplatePkByNode(String nodeCode) throws TplBusinessException;
}
