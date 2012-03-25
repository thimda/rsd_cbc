package nc.uap.ctrl.tpl.print.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import nc.bs.framework.common.NCLocator;
import nc.bs.logging.Logger;
import nc.uap.ctrl.tpl.exp.TplBusinessException;
import nc.uap.ctrl.tpl.print.ICpPrintTemplateInnerQryService;
import nc.uap.ctrl.tpl.print.ICpPrintTemplateService;
import nc.uap.ctrl.tpl.print.ICpPrintXmlService;
import nc.uap.ctrl.tpl.print.base.CpPrintConditionVO;
import nc.uap.ctrl.tpl.print.base.CpPrintTemplateTotalVO;
import nc.uap.ctrl.tpl.print.base.CpPrintTemplateVO;
import nc.uap.lfw.app.plugin.AppControlPlugin;
import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.cmd.UifDatasetAfterSelectCmd;
import nc.uap.lfw.core.cmd.UifDatasetFieldRelCmd;
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
import nc.uap.lfw.core.data.RowData;
import nc.uap.lfw.core.data.RowSet;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.file.action.FileSystemAction;
import nc.uap.lfw.util.LfwClassUtil;
import nc.uap.oba.word.generator.MergeEngine;
import nc.uap.oba.word.generator.util.DocumentHelper;
import nc.vo.pub.SuperVO;

import org.w3c.dom.Document;

public class CpPrintTemplateServiceImpl implements ICpPrintTemplateService {

	@Override
	public void print(Dataset ds) throws TplBusinessException {
		// TODO Auto-generated method stub
		AppLifeCycleContext ctx = AppLifeCycleContext.current();
		if (ctx == null) {
			return;
		}
		LfwWidget widget = ctx.getViewContext().getView();
		ComboData[] cds = widget.getViewModels().getComboDatas();
		String nodeCode = (String) ctx.getApplicationContext().getAppAttribute(
				AppControlPlugin.NODECODE);
		new UifDatasetFieldRelCmd(ds.getId()).execute();
		new UifDatasetAfterSelectCmd(ds.getId()).execute();
		Row[] rows = ds.getSelectedRows();
		FieldRelation[] frs = ds.getFieldRelations().getFieldRelations();
		if (rows == null || rows.length < 1) {
			throw new LfwRuntimeException("请选择要打印的用户信息!");
		} else if (rows.length > 1) {
			throw new LfwRuntimeException("请选择要打印的一行用户信息!");
		}
		Row row = rows[0];
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
		try {
			String pk_template = service.getPrintTemplatePkByNode(nodeCode);
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
					new UifDatasetFieldRelCmd(detailDs.getId()).execute();
					FieldSet fieldset = detailDs.getFieldSet();
					Field[] fs = fieldset.getFields();
					String tableName = getTableName(detailDs);
					bodyChildList[index] = tableName;
					index++;
					Row[] Rows = getDataSetRows(detailDs);
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
		} catch (TplBusinessException e) {
			LfwLogger.error(e);
			throw new TplBusinessException(e.getMessage(),e);
		}
		try {
			String openurl = LfwRuntimeEnvironment.getRootPath()
					+ "/pt/word/file/down?id=" + pk_file + "," + fileName;
			String url = LfwRuntimeEnvironment.getRootPath()
					+ "/core/word.jsp?pageId=officeedit&openurl=" + openurl
					+ "&canopen=true&noieopen=true&readonly=true";
			String title = "模板打开";
			String width = "900";
			String height = "800";
			AppLifeCycleContext.current().getApplicationContext()
					.popOuterWindow(url, title, width, height);
		} catch (Exception ee) {
			Logger.error(ee.getMessage(), ee);
			throw new TplBusinessException(ee.getMessage(),ee);
		}
	}

	@Override
	public void merger(String id, HttpServletResponse response)
			throws TplBusinessException {
		// TODO Auto-generated method stub
		OutputStream out = null;
		String path = null;
		try {
			out = response.getOutputStream();
			String[] str = id.split(",");
			String pk_file = str[0];
			;
			String fileName = str[1];
			String root = LfwRuntimeEnvironment.getRealPath();
			String tmp_path = root + "/" + UUID.randomUUID().toString()
					+ ".docx";
			if (null == fileName || "".equals(fileName.trim())
					|| null == pk_file || "".equals(pk_file.trim())) {
				return;
			}
			path = root + "/" + fileName;
			File dataFile = new File(path);
			OutputStream out_tmp = null;
			try {
				out_tmp = new FileOutputStream(tmp_path);
				new FileSystemAction().getFileManager().download(pk_file,
						out_tmp);
				File template = new java.io.File(tmp_path);
				Document document = DocumentHelper.file2Document(dataFile);
				// 合并
				String data = DocumentHelper.document2String(document);

				MergeEngine generator = new MergeEngine(template, data);
				// 设置发布的文档是否为最终状态
				generator.getOptions().setPublishAsFinal(true);

				// 合并结果
				generator.generate(out);
			} catch (Exception e) {
				Logger.error(e.getMessage(), e);
				throw new TplBusinessException("文件下载失败", e);
			} finally {
				if (null != out_tmp) {
					out_tmp.flush();
					out_tmp.close();
				}
				deleteFile(tmp_path);
			}
		} catch (Exception ee) {
			throw new TplBusinessException("文件下载失败", ee);
		} finally {
			try {
				if (null != out) {
					out.flush();
					out.close();
				}
			} catch (Exception eee) {
				throw new TplBusinessException(eee.getMessage(), eee);
			} finally {
				deleteFile(path);
			}
		}
	}

	private void deleteFile(String path) {
		String rootPath = path.substring(0, path.lastIndexOf("/"));
		String fileName = path.substring(path.lastIndexOf("/") + 1);
		File file = new File(rootPath);
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (File f : files) {
				if (fileName.equals(f.getName())) {
					f.delete();
				}
			}
		}
	}

	private Row[] getDataSetRows(Dataset ds) {
		List<Row> rowList = new ArrayList<Row>();
		String currentKey = ds.getCurrentKey();
		RowSet rowset = ds.getRowSet(currentKey);
		RowData[] rowDatas = rowset.getRowDatas();
		for(RowData rowData : rowDatas){
			Row[] rows = rowData.getRows();
			for(Row row : rows){
				rowList.add(row);
			}
		}
		int len = rowList.size();
		Row[] rows = new Row[len];
		for(int index =0;index<len;index++){
			rows[index] = rowList.get(index);
		}
		return rows;
	}

	private String getTableName(Dataset ds) {
		String clazz = ds.getVoMeta();
		SuperVO vo = (SuperVO) LfwClassUtil.newInstance(clazz);
		return vo.getTableName();
	}
}
