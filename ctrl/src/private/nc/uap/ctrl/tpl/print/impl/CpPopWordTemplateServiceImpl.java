package nc.uap.ctrl.tpl.print.impl;

import java.util.ArrayList;
import java.util.UUID;

import nc.bs.framework.common.NCLocator;
import nc.bs.logging.Logger;
import nc.uap.ctrl.tpl.exp.TplBusinessException;
import nc.uap.ctrl.tpl.print.ICpPopWordTemplateService;
import nc.uap.ctrl.tpl.print.ICpPrintTemplateInnerQryService;
import nc.uap.ctrl.tpl.print.ICpPrintXmlService;
import nc.uap.ctrl.tpl.print.base.CpPrintConditionVO;
import nc.uap.ctrl.tpl.print.base.CpPrintTemplateTotalVO;
import nc.uap.ctrl.tpl.print.base.CpPrintTemplateVO;
import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.combodata.CombItem;
import nc.uap.lfw.core.combodata.ComboData;
import nc.uap.lfw.core.ctx.AppLifeCycleContext;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.DatasetRelation;
import nc.uap.lfw.core.data.DatasetRelations;
import nc.uap.lfw.core.data.Field;
import nc.uap.lfw.core.data.FieldRelation;
import nc.uap.lfw.core.data.FieldSet;
import nc.uap.lfw.core.data.MatchField;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.util.LfwClassUtil;
import nc.vo.pub.SuperVO;

public class CpPopWordTemplateServiceImpl implements ICpPopWordTemplateService {

	@Override
	public void open(String pk_template,Dataset ds,LfwWidget widget) throws TplBusinessException {
		// TODO Auto-generated method stub
		ComboData[] cds = widget.getViewModels().getComboDatas();
		ICpPrintTemplateInnerQryService service = NCLocator.getInstance()
				.lookup(ICpPrintTemplateInnerQryService.class);
		ICpPrintXmlService printXml = NCLocator.getInstance().lookup(
				ICpPrintXmlService.class);
		CpPrintTemplateVO printVo = null;
		CpPrintConditionVO[] vos = null;
		CpPrintTemplateTotalVO totalVo = null;
		String fileName = null;
		String path = null;
		String pk_file = null;
		Row row = ds.getSelectedRows()[0];
		FieldRelation[] frs = ds.getFieldRelations().getFieldRelations();
		try{
			totalVo = service.getPrintTotalVO(pk_template);
			printVo = (CpPrintTemplateVO) totalVo.getParentVO();
			vos = (CpPrintConditionVO[]) totalVo.getChildrenVO();
			ArrayList<String[]> list = new ArrayList<String[]>();
			String root = "";
			String rootChild = "";
			for (CpPrintConditionVO vo : vos) {
				if ("".equals(root.trim())) {
					root = vo.getTablename();
				}
				String tableType = vo.getTabletype();
				String varType = vo.getVartype();
				if(!("MD".equals(varType)&&"MASTER".equals(tableType))){
					continue;
				}
				String[] field_value = new String[3];
				field_value[0] = vo.getVarexpress();
				field_value[1] = vo.getVarname();
				int dataType = vo.getDatatype();
				int sp = field_value[0].lastIndexOf(".");
				String tableName = field_value[0].substring(0, sp);
				String field = field_value[0].substring(sp + 1);
				String fieldVlaue = null;
				if (dataType == 5) {
					for (FieldRelation fr : frs) {
						if (field.equals(fr.getWhereField().getValue())) {
							MatchField[] mfs = fr.getMatchFields();
							for (MatchField mf : mfs) {
								if (null == fieldVlaue
										&& null != mf.getWriteField()
										&& null != row
												.getValue(ds.nameToIndex(mf
														.getWriteField()))) {
									fieldVlaue = row.getValue(
											ds.nameToIndex(mf.getWriteField()))
											.toString();
								}
							}
						}
					}
				} else if (dataType == 6) {
					if (null != row.getValue(ds.nameToIndex(field))) {
						String value = row.getValue(ds.nameToIndex(field))
								.toString();
						for (ComboData cd : cds) {
							CombItem[] combItems = cd.getAllCombItems();
							if (cd.getId().contains(tableName)
									&& cd.getId().contains(field)) {
								for (CombItem item : combItems) {
									if (value.equals(item.getValue())) {
										fieldVlaue = item.getText();
									}
								}
							}
						}
					}
				} else {
					if (null != row.getValue(ds.nameToIndex(field))) {
						fieldVlaue = row.getValue(ds.nameToIndex(field))
								.toString();
					}
				}
				if ("".equals(rootChild.trim())) {
					rootChild = tableName;
				}
				if (null == fieldVlaue) {
					fieldVlaue = "";
				}
				field_value[2] = fieldVlaue;
				list.add(field_value);
			}
			ArrayList<ArrayList<ArrayList<String[]>>> detailList = new ArrayList<ArrayList<ArrayList<String[]>>>();
			DatasetRelations dsRels = widget.getViewModels().getDsrelations();
			String[] bodyChildList = null;
			if (null != dsRels) {
				DatasetRelation[] masterRels = dsRels
						.getDsRelations(ds.getId());		
				bodyChildList = new String[masterRels.length];
				int index = 0;
				for (DatasetRelation dr : masterRels) {
					// 获取子对应的外键值，并设置到VO条件中
					ArrayList<ArrayList<String[]>> detail = new ArrayList<ArrayList<String[]>>();
					Dataset detailDs = widget.getViewModels().getDataset(
							dr.getDetailDataset());
//					new UifDatasetFieldRelCmd(detailDs.getId(),widget.getId()).execute();
					FieldSet fieldset = detailDs.getFieldSet();
					Field[] fs = fieldset.getFields();
					String tableName = getTableName(detailDs);
					bodyChildList[index] = tableName;
					index++;
					Row[] Rows = detailDs.getSelectedRows();
					if(null==Rows){
						Rows = new Row[0];
					}
					for (Row r : Rows) {
						ArrayList<String[]> lineList = new ArrayList<String[]>();
						for (Field f : fs) {
							String[] field_value = new String[3];
							String field = null;
							if (null == f.getField()) {
								field = f.getId();
							} else {
								field = f.getField();
							}
							field_value[0] = tableName + "." + field;
							field_value[1] = f.getText();
							String fieldVlaue = "";
							if (null != field
									&& null != r.getValue(detailDs
											.nameToIndex(field))) {
								field_value[2] = r.getValue(
										detailDs.nameToIndex(field)).toString();
							} else {
								field_value[2] = fieldVlaue;
							}
							lineList.add(field_value);
						}
						detail.add(lineList);
					}
					detailList.add(detail);
				}
			}
			String rootPath = LfwRuntimeEnvironment.getRealPath();
			fileName = UUID.randomUUID().toString() + ".xml";
			path = rootPath + "/" + fileName;
			printXml.createPrintXml(list, path);
			printXml.setRootElementName(root, rootChild, bodyChildList);
			printXml.setBodyElement(detailList);
			printXml.startWritePrintXml();
			if (null != printVo) {
				pk_file = printVo.getPk_file();
			}
		}catch (TplBusinessException e) {
			LfwLogger.error(e);
			throw new TplBusinessException(e.getMessage(),e);
		}
		try {
			String openurl = LfwRuntimeEnvironment.getRootPath()
					+ "/pt/word/file/down?id=" + pk_file + "," + fileName;
			String url = LfwRuntimeEnvironment.getRootPath()
					+ "/core/word.jsp?pageId=officeedit&openurl=" + openurl
					+ "&canopen=true&noieopen=true&readonly=true";
			AppLifeCycleContext.current().getApplicationContext()
					.popOuterWindow(url, "模板打开", "1000", "800");
		} catch (Exception ee) {
			Logger.error(ee.getMessage(), ee);
			throw new TplBusinessException(ee.getMessage(),ee);
		}
	}
	
	


	private String getTableName(Dataset ds) {
		String clazz = ds.getVoMeta();
		SuperVO vo = (SuperVO) LfwClassUtil.newInstance(clazz);
		return vo.getTableName();
	}

}
