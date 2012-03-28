package nc.uap.ctrl.tpl.print;

import nc.uap.ctrl.tpl.exp.TplBusinessException;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.page.LfwWidget;

public interface ICpPopWordTemplateService {

	public void open(String pk_template,Dataset ds,LfwWidget widget) throws TplBusinessException;
}
