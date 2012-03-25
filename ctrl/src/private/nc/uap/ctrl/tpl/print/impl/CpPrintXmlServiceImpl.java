package nc.uap.ctrl.tpl.print.impl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import nc.bs.framework.common.NCLocator;
import nc.bs.logging.Logger;
import nc.uap.ctrl.tpl.exp.TplBusinessException;
import nc.uap.ctrl.tpl.print.ICpPrintFileChooserService;
import nc.uap.ctrl.tpl.print.ICpPrintTemplateInnerQryService;
import nc.uap.ctrl.tpl.print.ICpPrintXmlService;
import nc.uap.ctrl.tpl.print.base.CpPrintConditionVO;
import nc.uap.lfw.core.log.LfwLogger;
import nc.vo.jcom.xml.XMLUtil;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

public class CpPrintXmlServiceImpl implements ICpPrintXmlService {

	private String path;
	private ArrayList<String[]> list;
	private String root;
	private String rootChild;
	private String[] bodyChildList;
	private ArrayList<ArrayList<ArrayList<String[]>>> detailList;

	@Override
	public void createPrintXml(ArrayList<String[]> list, String path) {
		// TODO Auto-generated method stub

		this.list = list;
		this.path = path;
	}

	@Override
	public void setBodyElement(
			ArrayList<ArrayList<ArrayList<String[]>>> detailList) {
		// TODO Auto-generated method stub

		this.detailList = detailList;
	}

	@Override
	public void setRootElementName(String root, String rootChild,String[] bodyChildList) {
		// TODO Auto-generated method stub

		this.root = root;
		this.rootChild = rootChild;
		this.bodyChildList = bodyChildList;
	}

	@Override
	public void startWritePrintXml() throws TplBusinessException {
		// TODO Auto-generated method stub

		Document doc = XMLUtil.getNewDocument();
		Element rootelement = doc.createElement(root);
		doc.appendChild(rootelement);
		Element rootchildheaderelement = doc.createElement("表头");
		rootelement.appendChild(rootchildheaderelement);
		Element rootchildelement = doc.createElement(rootChild);
		rootchildheaderelement.appendChild(rootchildelement);
		FileOutputStream outStream = null;
		OutputStreamWriter pw = null;
		for (int index = 0; index < list.size(); index++) {
			String attr = ((String[]) list.get(index))[0].toString();
			String field = ((String[]) list.get(index))[1].toString();
			String value = ((String[]) list.get(index))[2].toString();
			Element tempElement = doc.createElement(field);
			int sp = attr.lastIndexOf(".");
			tempElement.setAttribute("attr", attr.substring(sp + 1));
			Text text = doc.createTextNode(value);
			tempElement.appendChild(text);
			rootchildelement.appendChild(tempElement);
		}
		if (null != this.detailList) {
			for (int index = 0; index < this.detailList.size(); index++) {
				if(null!=bodyChildList[index]){
					Element rootchildbodyelement = doc.createElement("表体");
					rootelement.appendChild(rootchildbodyelement);
					Element tableNameelement = doc.createElement(bodyChildList[index]);
					rootchildbodyelement.appendChild(tableNameelement);
					ArrayList<ArrayList<String[]>> rootList = this.detailList
							.get(index);
					for (int index1 = 0; index1 < rootList.size(); index1++) {
						Element bodylineelement = doc.createElement("表体行");
						tableNameelement.appendChild(bodylineelement);
						ArrayList<String[]> childList = rootList.get(index1);
						for (int index2 = 0; index2 < childList.size(); index2++) {
							String[] tmp = childList.get(index2);
							String varExpress = tmp[0];
							String varName = tmp[1];
							String fieldValue = tmp[2];
							varName = varName.replaceAll("（", "");
							varName = varName.replaceAll("）", "");
							Element ele = doc.createElement(varName);
							ele.setAttribute("attr", varExpress
									.substring(varExpress.lastIndexOf(".") + 1));
							Text text = doc.createTextNode(fieldValue);
							ele.appendChild(text);
							bodylineelement.appendChild(ele);
						}
					}
				}
			}
		}
		try {
			outStream = new FileOutputStream(this.path);
			pw = new OutputStreamWriter(outStream);
			XMLUtil.printDOMTree(pw, doc, 0, "gb2312");
		} catch (Exception e) {
			Logger.error(e.getMessage(), e);
			throw new TplBusinessException(e.getMessage(), e);
		} finally {
			try {
				pw.flush();
				pw.close();
				outStream.close();
			} catch (IOException ee) {
				Logger.error(ee.getMessage(), ee);
				throw new TplBusinessException(ee.getMessage(), ee);
			}
		}
	}

	@Override
	public void importXml(String pk_template) throws TplBusinessException {
		// TODO Auto-generated method stub
		CpPrintConditionVO[] vos = null;
		ICpPrintTemplateInnerQryService service = NCLocator.getInstance()
				.lookup(ICpPrintTemplateInnerQryService.class);
		try {
			vos = service.getPrintConditions(pk_template);
			ArrayList<String[]> list = new ArrayList<String[]>();
			String root = "";
			String rootChild = "";
			String[] bodyChildList = new String[1];
			ArrayList<ArrayList<ArrayList<String[]>>> detailList = new ArrayList<ArrayList<ArrayList<String[]>>>();
			ArrayList<ArrayList<String[]>> detail = new ArrayList<ArrayList<String[]>>();
			ArrayList<String[]> bodyList = new ArrayList<String[]>();
			for (CpPrintConditionVO vo : vos) {
				if ("".equals(root.trim())) {
					root = vo.getTablename();
				}
				String tableType = vo.getTabletype();
				String varType = vo.getVartype();
				if("MD".equals(varType)&&"MASTER".equals(tableType)){
					String[] field_value = new String[3];
					field_value[0] = vo.getVarexpress();
					field_value[1] = vo.getVarname();
					int sp = field_value[0].lastIndexOf(".");
					String tableName = field_value[0].substring(0, sp);
					if ("".equals(rootChild.trim())) {
						rootChild = tableName;
					}
					field_value[2] = "";
					list.add(field_value);
				}
				if("MD".equals(varType)&&"DETAIL".equals(tableType)){
					String[] field_value = new String[3];
					field_value[0] = vo.getVarexpress();
					if(null==bodyChildList[0]){
						bodyChildList[0] = field_value[0].substring(0, field_value[0].lastIndexOf("."));
					}
					field_value[1] = vo.getVarname();
					field_value[2] = "";
					bodyList.add(field_value);
				}
			}
			detail.add(bodyList);
			detail.add(bodyList);
			detailList.add(detail);
			ICpPrintFileChooserService choose = NCLocator.getInstance().lookup(ICpPrintFileChooserService.class);
			choose.init();
			choose.readFile();
			String path = choose.getRealPath();
			if (null != path) {
				this.list = list;
				this.path = path;
				this.root = root;
				this.rootChild = rootChild;
				this.bodyChildList = bodyChildList;
				this.detailList = detailList;
				startWritePrintXml();
			}
		} catch (TplBusinessException e) {
			LfwLogger.error(e);
			throw new TplBusinessException(e.getMessage());
		}
	}

}
