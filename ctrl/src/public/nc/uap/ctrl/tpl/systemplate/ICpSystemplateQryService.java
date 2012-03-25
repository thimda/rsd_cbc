package nc.uap.ctrl.tpl.systemplate;

import nc.uap.ctrl.tpl.exp.TplBusinessException;
import nc.uap.ctrl.tpl.qry.CpTemplateParaVO;

public interface ICpSystemplateQryService {
	public String getTemplateId(CpTemplateParaVO paramVO) throws TplBusinessException;
}
