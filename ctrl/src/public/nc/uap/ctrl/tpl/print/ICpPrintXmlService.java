package nc.uap.ctrl.tpl.print;

import java.util.ArrayList;

import nc.uap.ctrl.tpl.exp.TplBusinessException;

public interface ICpPrintXmlService {
	
	public void createPrintXml(ArrayList<String[]> list,String path);
	
	public void setRootElementName(String root,String rootChild,String[] bodyChildList);
	
	public void setBodyElement(ArrayList<ArrayList<ArrayList<String[]>>> detailList);
	
	public void startWritePrintXml() throws TplBusinessException;
	
	public void importXml(String pk_template) throws TplBusinessException;
}
