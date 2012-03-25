package nc.uap.ctrl.tpl.systemplate;

import nc.uap.ctrl.tpl.qry.CpTemplateParaVO;
import nc.vo.pub.BusinessException;

public interface ICpSystemplateService {

	public String getTemplateId(CpTemplateParaVO tptParaVo) throws BusinessException;
}
