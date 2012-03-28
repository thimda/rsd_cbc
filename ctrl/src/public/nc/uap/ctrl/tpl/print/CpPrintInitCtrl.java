package nc.uap.ctrl.tpl.print;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import nc.bs.framework.common.NCLocator;
import nc.bs.logging.Logger;
import nc.md.IMDQueryFacade;
import nc.md.MDBaseQueryFacade;
import nc.md.model.IAttribute;
import nc.md.model.IBusinessEntity;
import nc.md.model.MetaDataException;
import nc.md.model.type.IType;
import nc.md.model.type.impl.RefType;
import nc.uap.cpb.org.constant.DialogConstant;
import nc.uap.cpb.template.TemplateConstant;
import nc.uap.ctrl.tpl.exp.TplBusinessException;
import nc.uap.ctrl.tpl.print.base.CpPrintConditionVO;
import nc.uap.ctrl.tpl.print.base.CpPrintTemplateVO;
import nc.uap.ctrl.tpl.print.impl.CpPrintFileChooserServiceImpl;
import nc.uap.ctrl.tpl.print.init.CpPrintInitTool;
import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.cmd.CmdInvoker;
import nc.uap.lfw.core.cmd.UifCloseViewCmd;
import nc.uap.lfw.core.cmd.UifDatasetLoadCmd;
import nc.uap.lfw.core.cmd.UifDelCmd;
import nc.uap.lfw.core.cmd.UifPlugoutCmd;
import nc.uap.lfw.core.cmd.UifSaveCmd;
import nc.uap.lfw.core.cmd.util.UifRowTranslator;
import nc.uap.lfw.core.common.DatasetConstant;
import nc.uap.lfw.core.ctrl.WindowController;
import nc.uap.lfw.core.ctx.AppLifeCycleContext;
import nc.uap.lfw.core.ctx.ViewContext;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.event.DataLoadEvent;
import nc.uap.lfw.core.event.DatasetEvent;
import nc.uap.lfw.core.event.DialogEvent;
import nc.uap.lfw.core.event.MouseEvent;
import nc.uap.lfw.core.event.PageEvent;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.lfw.core.model.plug.TranslatedRow;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.serializer.impl.Dataset2SuperVOSerializer;
import nc.uap.lfw.core.serializer.impl.SuperVO2DatasetSerializer;
import nc.uap.lfw.core.vo.LfwExAggVO;
import nc.uap.lfw.file.action.FileSystemAction;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.SuperVO;
public class CpPrintInitCtrl implements WindowController, Serializable {
  private static final String CURRENT_ROW="row";
  private static final long serialVersionUID=7532916478964732880L;
  public void sysWindowClosed(  PageEvent event){
    LfwRuntimeEnvironment.getWebContext().destroyWebSession();
  }
  public void onAdd(  MouseEvent mouseEvent){
    AppLifeCycleContext ctx = AppLifeCycleContext.current();
		ctx.getWindowContext().removeAppAttribute(CURRENT_ROW);
		ctx.getWindowContext().addAppAttribute(
				TemplateConstantArgs.OPERATE_STATUS,
				TemplateConstantArgs.ADD_OPERATE);
		ctx.getWindowContext().popView(TemplateConstantArgs.EDIT,
				DialogConstant.DEFAULT_WIDTH, DialogConstant.SIX_ELE_HEIGHT,
				"编辑模板");
  }
  public void onTemplateAssignEvent(  MouseEvent<?> mouseEvent){
    AppLifeCycleContext ctx = AppLifeCycleContext.current();
		ViewContext viewCtx = ctx.getApplicationContext()
				.getCurrentWindowContext().getCurrentViewContext();
		Dataset ds = viewCtx.getView().getViewModels().getDataset(
				TemplateConstantArgs.LISTDS);
		Row row = ds.getSelectedRow();
		if (row == null)
			throw new LfwRuntimeException("请先选中待编辑模板");
		ctx.getApplicationContext().navgateTo("cp_templateassign", "模板分配",
				"800", "600");
  }
  public void onEdit(  MouseEvent mouseEvent){
    AppLifeCycleContext ctx = AppLifeCycleContext.current();
		ViewContext viewCtx = ctx.getApplicationContext()
				.getCurrentWindowContext().getCurrentViewContext();
		Dataset ds = viewCtx.getView().getViewModels().getDataset(
				TemplateConstantArgs.LISTDS);
		Row row = ds.getSelectedRow();
		if (row == null)
			throw new LfwRuntimeException("请先选中待编辑模板");
		ctx.getWindowContext().addAppAttribute(CURRENT_ROW, row);
		ctx.getWindowContext().addAppAttribute(
				TemplateConstantArgs.OPERATE_STATUS,
				TemplateConstantArgs.EDIT_OPERATE);
		ctx.getWindowContext().popView(TemplateConstantArgs.EDIT,
				DialogConstant.DEFAULT_WIDTH, DialogConstant.SIX_ELE_HEIGHT,
				"编辑模板");
  }
  public void onDel(  MouseEvent mouseEvent){
    AppLifeCycleContext ctx = AppLifeCycleContext.current();
		ViewContext viewCtx = ctx.getApplicationContext()
				.getCurrentWindowContext().getCurrentViewContext();
		Dataset ds = viewCtx.getView().getViewModels().getDataset(
				TemplateConstantArgs.LISTDS);
		Row row = ds.getSelectedRow();
		if (row == null)
			throw new LfwRuntimeException("请先选中待删除模板");
		UifDelCmd cmd = new UifDelCmd(TemplateConstantArgs.LISTDS,
				LfwExAggVO.class.getName());
		cmd.execute();
  }
  /** 
 * 编辑查询条件
 * @param mouseEvent
 */
  public void onCondition(  MouseEvent mouseEvent){
    AppLifeCycleContext ctx = AppLifeCycleContext.current();
		ViewContext viewCtx = ctx.getApplicationContext()
				.getCurrentWindowContext().getCurrentViewContext();
		Dataset ds = viewCtx.getView().getViewModels().getDataset(
				TemplateConstantArgs.LISTDS);
		Row row = ds.getSelectedRow();
		if (row == null)
			throw new LfwRuntimeException("请先选中待编辑模板");
		String metaclass = (String) row.getValue(ds
				.nameToIndex(TemplateConstantArgs.METACLASS));
		String pktemplate = (String) row.getValue(ds
				.nameToIndex(TemplateConstantArgs.PK_PRINT_TEMPLATE));
		ctx.getWindowContext().addAppAttribute(TemplateConstantArgs.METACLASS,
				metaclass);
		ctx.getWindowContext().addAppAttribute(
				TemplateConstantArgs.PK_PRINT_TEMPLATE, pktemplate);
		ctx.getWindowContext().popView(TemplateConstantArgs.CONDITION, "1000",
				"500", "编辑查询条件");
  }
  /** 
 * 模板编辑打开之前相应
 * @param dialogEvent
 */
  public void editBeforeShow(  DialogEvent dialogEvent){
    AppLifeCycleContext ctx = AppLifeCycleContext.current();
		ViewContext viewCtx = ctx.getApplicationContext()
				.getCurrentWindowContext().getCurrentViewContext();
		Dataset ds = viewCtx.getView().getViewModels().getDataset("formds");
		ds.clear();

		Row row = (Row) ctx.getWindowContext().getAppAttributeAndRemove(
				CURRENT_ROW);
		Row targetRow = null;
		if (row != null)
			targetRow = UifRowTranslator.translateRowToRow(ds, row);
		else
			targetRow = ds.getEmptyRow();
		ds.addRow(targetRow);
		ds.setRowSelectIndex(0);
		ds.setEnabled(true);
  }
  /** 
 * 模板编辑片段触发主片段
 * @param paramMap
 */
  public void pluginaddPlugin(  Map paramMap){
    TranslatedRow transRow = (TranslatedRow) paramMap.get(CURRENT_ROW);
		ViewContext viewCtx = AppLifeCycleContext.current()
				.getApplicationContext().getCurrentWindowContext()
				.getCurrentViewContext();
		Dataset ds = viewCtx.getView().getViewModels().getDataset(
				TemplateConstantArgs.LISTDS);
		String opeStatus = (String) AppLifeCycleContext.current()
		.getApplicationContext().getCurrentWindowContext()
		.getAppAttribute(TemplateConstantArgs.OPERATE_STATUS);
		Row row =null;
		if (TemplateConstantArgs.ADD_OPERATE.equals(opeStatus)) {
			// 新增行并选中
			row = ds.getEmptyRow();
			row = UifRowTranslator.translateRowToRow(ds, transRow);
			ds.addRow(row);
			ds.setSelectedIndex(ds.getRowIndex(row));
		} else if (TemplateConstantArgs.EDIT_OPERATE.equals(opeStatus)) {
			row = ds.getSelectedRow();
			row = UifRowTranslator.translateRowToRow(ds, row, transRow);
		}
		UifSaveCmd cmd = new UifSaveCmd(TemplateConstantArgs.LISTDS, null,
				LfwExAggVO.class.getName(), false) {
			protected void onVoSave(AggregatedValueObject aggvo) {
				ICpPrintTemplateInnerService service = NCLocator.getInstance()
						.lookup(ICpPrintTemplateInnerService.class);
				try {
					CpPrintTemplateVO vo = (CpPrintTemplateVO) aggvo
							.getParentVO();
					if (vo.getPk_print_template() == null
							|| vo.getPk_print_template().length() == 0) {
						service.initTemplate(vo);
					} else {
						service.updateTemplate(vo);
					}
				} catch (TplBusinessException e1) {
					LfwLogger.error(e1.getMessage(), e1);
					throw new LfwRuntimeException(e1.getMessage());
				}
			}
		};
		cmd.execute();
  }
  /** 
 * 模板编辑保存按钮
 * @param mouseEvent
 */
  public void saveBtClick(  MouseEvent mouseEvent){
    AppLifeCycleContext ctx = AppLifeCycleContext.current();
		ViewContext viewCtx = ctx.getApplicationContext()
				.getCurrentWindowContext().getCurrentViewContext();
		Dataset ds = viewCtx.getView().getViewModels().getDataset("formds");
		CmdInvoker.invoke(new UifPlugoutCmd(ds.getWidget().getId(),"addPlugout"));
		CmdInvoker.invoke(new UifCloseViewCmd(TemplateConstantArgs.EDIT));
  }
  /** 
 * 模板编辑取消按钮
 * @param mouseEvent
 */
  public void cancelBtClick(  MouseEvent mouseEvent){
    CmdInvoker.invoke(new UifCloseViewCmd(TemplateConstantArgs.EDIT));
  }
  /** 
 * 条件编辑向左按钮
 * @param mouseEvent
 */
  public void toLeftBt(  MouseEvent mouseEvent){
    AppLifeCycleContext ctx = AppLifeCycleContext.current();
		ViewContext viewCtx = ctx.getApplicationContext()
				.getCurrentWindowContext().getCurrentViewContext();
		Dataset targetDs = viewCtx.getView().getViewModels().getDataset(
				TemplateConstantArgs.CONDITIONDS);
		Row[] rows = targetDs.getSelectedRows();
		if (null == rows) {
			throw new LfwRuntimeException("请先选中待删除编辑条件");
		}
		for (int i = 0; i < rows.length; i++) {
			targetDs.removeRow(rows[i]);
		}
  }
  /** 
 * 条件编辑取消按钮
 * @param mouseEvent
 */
  public void conditionCancelBt(  MouseEvent mouseEvent){
    CmdInvoker.invoke(new UifCloseViewCmd(TemplateConstantArgs.CONDITION));
  }
  /** 
 * 条件编辑向右按钮
 * @param mouseEvent
 */
  public void toRightBt(  MouseEvent mouseEvent){
    AppLifeCycleContext ctx = AppLifeCycleContext.current();
		ViewContext viewCtx = ctx.getApplicationContext()
				.getCurrentWindowContext().getCurrentViewContext();
		Dataset sourceDs = viewCtx.getView().getViewModels().getDataset(
				"entityds");
		Dataset targetDs = viewCtx.getView().getViewModels().getDataset(
				TemplateConstantArgs.CONDITIONDS);
		IMDQueryFacade mdqry = MDBaseQueryFacade.getInstance();
		String metaclass = (String) ctx.getWindowContext().getAppAttribute(
				TemplateConstantArgs.METACLASS);
		String pk_template = (String) ctx.getWindowContext().getAppAttribute(
				TemplateConstantArgs.PK_PRINT_TEMPLATE);
		IBusinessEntity bean;
		try {
			if (null == metaclass) {
				throw new LfwRuntimeException("元数据主实体为空");
			}
			bean = (IBusinessEntity) mdqry.getBeanByID(metaclass);
			new CpPrintInitTool()
					.addLine(sourceDs, targetDs, bean, pk_template);
		} catch (MetaDataException e) {
			LfwLogger.error(e);
		}
  }
  /** 
 * 条件编辑确定按钮
 * @param mouseEvent
 */
  public void conditionOkBt(  MouseEvent mouseEvent){
    AppLifeCycleContext ctx = AppLifeCycleContext.current();
		ViewContext viewCtx = ctx.getApplicationContext()
				.getCurrentWindowContext().getCurrentViewContext();
		Dataset targetDs = viewCtx.getView().getViewModels().getDataset(
				TemplateConstantArgs.CONDITIONDS);
		Dataset2SuperVOSerializer<CpPrintConditionVO> s = new Dataset2SuperVOSerializer<CpPrintConditionVO>();
		CpPrintConditionVO[] vos = null;
		SuperVO[] tmp = s.serialize(targetDs);
		if (null != tmp && tmp.length > 0) {
			vos = s.serialize(targetDs);
		}
		ICpPrintTemplateInnerService print = NCLocator.getInstance().lookup(
				ICpPrintTemplateInnerService.class);
		String pk_template = (String) ctx.getWindowContext().getAppAttribute(
				TemplateConstantArgs.PK_PRINT_TEMPLATE);
		try {
			print.initConditons(pk_template, vos);
			CmdInvoker.invoke(new UifCloseViewCmd(
					TemplateConstantArgs.CONDITION));
		} catch (TplBusinessException e) {
			LfwLogger.error(e);
		}
  }
  public void conditionBeforeShow(  DialogEvent dialogEvent){
    AppLifeCycleContext ctx = AppLifeCycleContext.current();
		ViewContext viewCtx = ctx.getApplicationContext()
				.getCurrentWindowContext().getCurrentViewContext();
		Dataset entityDs = viewCtx.getView().getViewModels().getDataset(
				"entityds");
		entityDs.clear();
		Dataset conditionDs = viewCtx.getView().getViewModels().getDataset(
				TemplateConstantArgs.CONDITIONDS);
		conditionDs.clear();
		try {
			IMDQueryFacade mdqry = MDBaseQueryFacade.getInstance();
			String metaclass = (String) ctx.getWindowContext().getAppAttribute(
					TemplateConstantArgs.METACLASS);
			if (null == metaclass) {
				return;
			}
			IBusinessEntity bean = (IBusinessEntity) mdqry
					.getBeanByID(metaclass);
			String key = entityDs.getReqParameters().getParameterValue(
					DatasetConstant.QUERY_KEYVALUE);
			if (key != null) {
				bean = (IBusinessEntity) ((RefType) bean
						.getAttributeByPath(key).getDataType()).getRefType();
			}
			String tableName = bean.getTable().getID();
			List<IAttribute> attrList = bean.getAttributes();
			Iterator<IAttribute> it = attrList.iterator();
			while (it.hasNext()) {
				IAttribute attr = it.next();
				String id = attr.getName();
				String name = attr.getDisplayName();
				int idIndex = entityDs.nameToIndex("id");
				int nameIndex = entityDs.nameToIndex("name");
				int pIdIndex = entityDs.nameToIndex("pid");
				int loadFieldIndex = entityDs.nameToIndex("loadfield");
				int tableNameIndex = entityDs.nameToIndex("tablename");
				Row row = entityDs.getEmptyRow();
				if (key != null) {
					row.setValue(idIndex, key + "." + id);
				} else {
					row.setValue(idIndex, id);
				}
				row.setValue(nameIndex, name);
				row.setValue(tableNameIndex, tableName);

				int typeType = attr.getDataType().getTypeType();
				if (typeType == IType.ENTITY || typeType == IType.REF)
					row.setValue(loadFieldIndex, "1");
				if (key != null)
					row.setValue(pIdIndex, key);
				entityDs.addRow(row);
			}
		} catch (MetaDataException e) {
			LfwLogger.error(e);
		}
		try {
			String pk_template = (String) ctx.getWindowContext()
					.getAppAttribute(TemplateConstantArgs.PK_PRINT_TEMPLATE);
			ICpPrintTemplateInnerQryService qryService = NCLocator
					.getInstance()
					.lookup(ICpPrintTemplateInnerQryService.class);
			CpPrintConditionVO[] conds = qryService
					.getPrintConditions(pk_template);
			SuperVO2DatasetSerializer s = new SuperVO2DatasetSerializer();
			s.serialize(conds, conditionDs);
			conditionDs.setEnabled(true);
		} catch (TplBusinessException e) {
			LfwLogger.error(e);
			throw new LfwRuntimeException(e.getMessage());
		}
  }
  public void onImportXml(  MouseEvent mouseEvent){
    AppLifeCycleContext ctx = AppLifeCycleContext.current();
		ViewContext viewCtx = ctx.getApplicationContext()
				.getCurrentWindowContext().getCurrentViewContext();
		Dataset ds = viewCtx.getView().getViewModels().getDataset(
				TemplateConstantArgs.LISTDS);
		Row row = ds.getSelectedRow();
		if (row == null)
			throw new LfwRuntimeException("请先选中待编辑模板");
		String pk_template = (String) row.getValue(ds
				.nameToIndex(TemplateConstantArgs.PK_PRINT_TEMPLATE));
		ICpPrintXmlService importXml = NCLocator.getInstance().lookup(
				ICpPrintXmlService.class);
		try {
			importXml.importXml(pk_template);
		} catch (TplBusinessException e) {
			throw new LfwRuntimeException(e.getMessage(), e);
		}
  }
  public void onWordUpload(  MouseEvent mouseEvent){
    AppLifeCycleContext ctx = AppLifeCycleContext.current();
		ViewContext viewCtx = ctx.getApplicationContext()
				.getCurrentWindowContext().getCurrentViewContext();
		Dataset ds = viewCtx.getView().getViewModels().getDataset(
				TemplateConstantArgs.LISTDS);
		Row row = ds.getSelectedRow();
		if (row == null)
			throw new LfwRuntimeException("请先选中待编辑模板");
		String pktemplate = (String) row.getValue(ds
				.nameToIndex(TemplateConstantArgs.PK_PRINT_TEMPLATE));
		String filepk = (String) row.getValue(ds.nameToIndex("pk_file"));
		ctx.getWindowContext().addAppAttribute(CURRENT_ROW, row);
		boolean isquick = false;
		String extendclass = "nc.uap.ctrl.tpl.print.WordUploadExtender";
		String url = LfwRuntimeEnvironment.getRootPath()
				+ "/core/file.jsp?pageId=file&isquick=" + isquick
				+ "&extendclass=" + extendclass + "&billitem=" + pktemplate;
		if (filepk != null && !"".equals(filepk.trim())) {
			url += "&filepk=" + filepk;
		}
		AppLifeCycleContext.current().getApplicationContext().popOuterWindow(
				url, "模板上传", DialogConstant.DEFAULT_WIDTH,
				DialogConstant.SEVEN_ELE_HEIGHT);
		ds.setCurrentKey(ds.MASTER_KEY);
  }
  public void pluginappscategory_plugin(  Map keys){
    LfwWidget main = AppLifeCycleContext.current().getWindowContext()
				.getViewContext("main").getView();
		Dataset ds = main.getViewModels().getDataset(
				TemplateConstantArgs.LISTDS);
		TranslatedRow r = (TranslatedRow) keys.get("appscategory_click");
		final String pk_appscategory = (String) r.getValue("id");
		// getCurrentWinCtx().addAppAttribute("pk_appscategory",pk_appscategory);
		CmdInvoker.invoke(new UifDatasetLoadCmd(ds.getId()) {
			protected String postProcessQueryVO(SuperVO vo, Dataset ds) {
				String where = " nodecode='" + pk_appscategory + "'";
				ds.setLastCondition(where);
				return where;
			}
		});
  }
  public void onAfterRowSelect(  DatasetEvent datasetEvent){
    Dataset ds = datasetEvent.getSource();
		Row row = ds.getSelectedRow();
		String pk_template = (String) row.getValue(ds
				.nameToIndex(TemplateConstantArgs.PK_PRINT_TEMPLATE));
		// 设置模板pk
		AppLifeCycleContext.current().getApplicationContext().addAppAttribute(
				TemplateConstant.PK_TEMPLATE, pk_template);
		// 设置模板类型
		AppLifeCycleContext.current().getApplicationContext()
				.addAppAttribute(TemplateConstant.TEMPLATE_TYPE,
						TemplateConstant.TEMPLATE_PRINT);
  }
  /** 
 * 主界面列表加载
 * @param dataLoadEvent
 */
  public void onDataLoad_listds(  DataLoadEvent dataLoadEvent){
    Dataset ds = dataLoadEvent.getSource();
		CmdInvoker.invoke(new UifDatasetLoadCmd(ds.getId()));
  }
  public void onWordDownload(  MouseEvent mouseEvent){
	  AppLifeCycleContext ctx = AppLifeCycleContext.current();
		ViewContext viewCtx = ctx.getApplicationContext()
				.getCurrentWindowContext().getCurrentViewContext();
		Dataset ds = viewCtx.getView().getViewModels().getDataset(
				TemplateConstantArgs.LISTDS);
		Row row = ds.getSelectedRow();
		if (row == null)
			throw new LfwRuntimeException("请先选中待编辑模板");
		String filepk = (String) row.getValue(ds.nameToIndex("pk_file"));
		if(null==filepk || "".equals(filepk.trim())){
			throw new LfwRuntimeException("不存在供下载的Word模板文件");
		}
		try{
			ICpPrintFileChooserService choose = NCLocator.getInstance().lookup(ICpPrintFileChooserService.class);
			choose.init();
			choose.setType(CpPrintFileChooserServiceImpl.DOCX);
			choose.readFile();
			String path = choose.getRealPath();
			if(null==path || "".equals(path.trim())){
				return;
			}
			OutputStream out = null;
			try{
				out = new FileOutputStream(path);
				new FileSystemAction().getFileManager().download(filepk, out);
			}catch (Exception e) {
				Logger.error(e.getMessage(), e);
				throw new TplBusinessException("文件下载失败", e);
			}finally{
				if(null!=out){
					out.flush();
					out.close();
				}
			}	
		}catch (Exception ee) {
			LfwLogger.error(ee);
			throw new LfwRuntimeException(ee.getMessage(),ee);
		}
		
  }
}
