package nc.uap.ctrl.tpl.print;

import javax.servlet.http.HttpServletResponse;

import nc.uap.ctrl.tpl.exp.TplBusinessException;
import nc.uap.lfw.core.data.Dataset;

public interface ICpPrintTemplateService {
	public void print(Dataset ds) throws TplBusinessException;
	
	public void merger(String id,HttpServletResponse response) throws TplBusinessException;
}
