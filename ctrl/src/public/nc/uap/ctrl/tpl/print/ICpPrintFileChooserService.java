package nc.uap.ctrl.tpl.print;

import nc.uap.ctrl.tpl.exp.TplBusinessException;

public interface ICpPrintFileChooserService {

	public void init();
	
	public void readFile() throws TplBusinessException;
	
	public String getRealPath();
}
