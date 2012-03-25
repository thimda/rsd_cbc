package nc.uap.ctrl.tpl.print;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import nc.bs.framework.common.NCLocator;
import nc.md.IMDQueryFacade;
import nc.md.MDBaseQueryFacade;
import nc.md.model.IAttribute;
import nc.md.model.IBusinessEntity;
import nc.md.model.MetaDataException;
import nc.md.model.type.IType;
import nc.md.model.type.impl.RefType;
import nc.uap.cpb.template.TemplateConstant;
import nc.uap.ctrl.tpl.exp.TplBusinessException;
import nc.uap.ctrl.tpl.print.base.CpPrintConditionVO;
import nc.uap.ctrl.tpl.print.base.CpPrintTemplateVO;
import nc.uap.ctrl.tpl.print.init.CpPrintInitTool;
import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.cmd.CmdInvoker;
import nc.uap.lfw.core.cmd.UifCloseViewCmd;
import nc.uap.lfw.core.cmd.UifDatasetLoadCmd;
import nc.uap.lfw.core.cmd.UifPlugoutCmd;
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
import nc.vo.pub.SuperVO;

public class CpPrintInitCtrl implements WindowController, Serializable {
	private static final String CURRENT_ROW = "row";
	private static final long serialVersionUID = 7532916478964732880L;

	public void sysWindowClosed(PageEvent event) {
		LfwRuntimeEnvironment.getWebContext().destroyWebSession();
	}

	public void onAdd(MouseEvent mouseEvent) {
		AppLifeCycleContext ctx = AppLifeCycleContext.current();
		ctx.getWindowContext().removeAppAttribute(CURRENT_ROW);
		ctx.getWindowContext().addAppAttribute("OPERATE_STATUS", "ADD_OPERATE");
		ctx.getWindowContext().popView("edit", "800", "500", "编辑模板");
	}

	 public void onTemplateAssignEvent(  MouseEvent<?> mouseEvent){
		 AppLifeCycleContext.current().getApplicationContext().navgateTo("cp_templateassign", "模板分配", "800", "600");
	 }
	
	public void onEdit(MouseEvent mouseEvent) {
		AppLifeCycleContext ctx = AppLifeCycleContext.current();
		ViewContext viewCtx = ctx.getApplicationContext()
				.getCurrentWindowContext().getCurrentViewContext();
		Dataset ds = viewCtx.getView().getViewModels().getDataset("listds");
		Row row = ds.getSelectedRow();
		if (row == null)
			throw new LfwRuntimeException("请先选中待编辑模板");
		ctx.getWindowContext().addAppAttribute(CURRENT_ROW, row);
		ctx.getWindowContext()
				.addAppAttribute("OPERATE_STATUS", "EDIT_OPERATE");
		ctx.getWindowContext().popView("edit", "800", "500", "编辑模板");
	}

	public void onDel(MouseEvent mouseEvent) {
		AppLifeCycleContext ctx = AppLifeCycleContext.current();
		ViewContext viewCtx = ctx.getApplicationContext()
				.getCurrentWindowContext().getCurrentViewContext();
		Dataset ds = viewCtx.getView().getViewModels().getDataset("listds");
		Row row = ds.getSelectedRow();
		String pk_template = (String) row.getValue(ds
				.nameToIndex("pk_print_template"));
		Dataset2SuperVOSerializer s = new Dataset2SuperVOSerializer();
		ICpPrintTemplateInnerService service = NCLocator.getInstance().lookup(
				ICpPrintTemplateInnerService.class);
		SuperVO[] vos = s.serialize(ds, row);
		try {
			service.deleteTemplate(pk_template);
		} catch (TplBusinessException e) {
			LfwLogger.error(e);
			throw new LfwRuntimeException(e.getMessage());
		}
		ds.removeRow(row);
	}

	/**
	 * 编辑查询条件
	 * 
	 * @param mouseEvent
	 */
	public void onCondition(MouseEvent mouseEvent) {
		AppLifeCycleContext ctx = AppLifeCycleContext.current();
		ViewContext viewCtx = ctx.getApplicationContext()
				.getCurrentWindowContext().getCurrentViewContext();
		Dataset ds = viewCtx.getView().getViewModels().getDataset("listds");
		Row row = ds.getSelectedRow();
		if (row == null)
			throw new LfwRuntimeException("请先选中待编辑模板");
		String metaclass = (String) row.getValue(ds.nameToIndex("metaclass"));
		String pktemplate = (String) row.getValue(ds
				.nameToIndex("pk_print_template"));
		ctx.getWindowContext().addAppAttribute("metaclass", metaclass);
		ctx.getWindowContext().addAppAttribute("pk_print_template", pktemplate);
		ctx.getWindowContext().popView("condition", "1000", "500", "编辑查询条件");
	}

	/**
	 * 模板编辑打开之前相应
	 * 
	 * @param dialogEvent
	 */
	public void editBeforeShow(DialogEvent dialogEvent) {
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
	 * 
	 * @param paramMap
	 */
	public void pluginaddPlugin(Map paramMap) {
		TranslatedRow transRow = (TranslatedRow) paramMap.get(CURRENT_ROW);
		ViewContext viewCtx = AppLifeCycleContext.current()
				.getApplicationContext().getCurrentWindowContext()
				.getCurrentViewContext();
		Dataset ds = viewCtx.getView().getViewModels().getDataset("listds");
		Row row = UifRowTranslator.translateRowToRow(ds, transRow);
		ds.addRow(row);
		ds.setRowSelectIndex(ds.getRowCount() - 1);
	}

	/**
	 * 模板编辑保存按钮
	 * 
	 * @param mouseEvent
	 */
	public void saveBtClick(MouseEvent mouseEvent) {
		AppLifeCycleContext ctx = AppLifeCycleContext.current();
		ViewContext viewCtx = ctx.getApplicationContext()
				.getCurrentWindowContext().getCurrentViewContext();
		String operStatus = (String) ctx.getWindowContext().getAppAttribute(
				"OPERATE_STATUS");
		Dataset ds = viewCtx.getView().getViewModels().getDataset("formds");
		Row row = ds.getSelectedRow();
		Dataset2SuperVOSerializer s = new Dataset2SuperVOSerializer();
		ICpPrintTemplateInnerService service = NCLocator.getInstance().lookup(
				ICpPrintTemplateInnerService.class);
		SuperVO[] vos = s.serialize(ds, row);
		try {
			if ("ADD_OPERATE".equals(operStatus.trim())) {
				service.initTemplate((CpPrintTemplateVO) vos[0]);
				CmdInvoker.invoke(new UifPlugoutCmd(ds.getWidget().getId(),
						"addPlugout"));
			}
			if ("EDIT_OPERATE".equals(operStatus.trim())) {
				service.updateTemplate((CpPrintTemplateVO) vos[0]);
			}
			CmdInvoker.invoke(new UifCloseViewCmd("edit"));
		} catch (TplBusinessException e) {
			LfwLogger.error(e);
			throw new LfwRuntimeException(e.getMessage());
		}
	}

	/**
	 * 模板编辑取消按钮
	 * 
	 * @param mouseEvent
	 */
	public void cancelBtClick(MouseEvent mouseEvent) {
		CmdInvoker.invoke(new UifCloseViewCmd("edit"));
	}

	/**
	 * 主界面列表加载
	 * 
	 * @param dataLoadEvent
	 */
	public void onDataLoad_listds(DataLoadEvent dataLoadEvent) {
		Dataset ds = dataLoadEvent.getSource();
		CmdInvoker.invoke(new UifDatasetLoadCmd(ds.getId()));
	}

	/**
	 * 条件编辑向左按钮
	 * 
	 * @param mouseEvent
	 */
	public void toLeftBt(MouseEvent mouseEvent) {
		AppLifeCycleContext ctx = AppLifeCycleContext.current();
		ViewContext viewCtx = ctx.getApplicationContext()
				.getCurrentWindowContext().getCurrentViewContext();
		Dataset targetDs = viewCtx.getView().getViewModels().getDataset(
				"conditionds");
		Row[] rows = targetDs.getSelectedRows();
		for (int i = 0; i < rows.length; i++) {
			targetDs.removeRow(rows[i]);
		}
	}

	/**
	 * 条件编辑取消按钮
	 * 
	 * @param mouseEvent
	 */
	public void conditionCancelBt(MouseEvent mouseEvent) {
		CmdInvoker.invoke(new UifCloseViewCmd("condition"));
	}

	/**
	 * 条件编辑向右按钮
	 * 
	 * @param mouseEvent
	 */
	public void toRightBt(MouseEvent mouseEvent) {
		AppLifeCycleContext ctx = AppLifeCycleContext.current();
		ViewContext viewCtx = ctx.getApplicationContext()
				.getCurrentWindowContext().getCurrentViewContext();
		Dataset sourceDs = viewCtx.getView().getViewModels().getDataset(
				"entityds");
		Dataset targetDs = viewCtx.getView().getViewModels().getDataset(
				"conditionds");
		IMDQueryFacade mdqry = MDBaseQueryFacade.getInstance();
		String metaclass = (String) ctx.getWindowContext().getAppAttribute(
				"metaclass");
		String pk_template = (String) ctx.getWindowContext().getAppAttribute(
				"pk_print_template");
		IBusinessEntity bean;
		try {
			bean = (IBusinessEntity) mdqry.getBeanByID(metaclass);
			new CpPrintInitTool()
					.addLine(sourceDs, targetDs, bean, pk_template);
		} catch (MetaDataException e) {
			LfwLogger.error(e);
		}
	}

	/**
	 * 条件编辑确定按钮
	 * 
	 * @param mouseEvent
	 */
	public void conditionOkBt(MouseEvent mouseEvent) {
		AppLifeCycleContext ctx = AppLifeCycleContext.current();
		ViewContext viewCtx = ctx.getApplicationContext()
				.getCurrentWindowContext().getCurrentViewContext();
		Dataset targetDs = viewCtx.getView().getViewModels().getDataset(
				"conditionds");
		Dataset2SuperVOSerializer<CpPrintConditionVO> s = new Dataset2SuperVOSerializer<CpPrintConditionVO>();
		CpPrintConditionVO[] vos = null;
		SuperVO[] tmp = s.serialize(targetDs);
		if (tmp.length > 0) {
			vos = s.serialize(targetDs);
		}
		ICpPrintTemplateInnerService print = NCLocator.getInstance().lookup(
				ICpPrintTemplateInnerService.class);
		String pk_template = (String) ctx.getWindowContext().getAppAttribute(
				"pk_print_template");
		try {
			print.initConditons(pk_template, vos);
			CmdInvoker.invoke(new UifCloseViewCmd("condition"));
		} catch (TplBusinessException e) {
			LfwLogger.error(e);
		}
	}

	public void conditionBeforeShow(DialogEvent dialogEvent) {
		AppLifeCycleContext ctx = AppLifeCycleContext.current();
		ViewContext viewCtx = ctx.getApplicationContext()
				.getCurrentWindowContext().getCurrentViewContext();
		Dataset entityDs = viewCtx.getView().getViewModels().getDataset(
				"entityds");
		entityDs.clear();
		Dataset conditionDs = viewCtx.getView().getViewModels().getDataset(
				"conditionds");
		conditionDs.clear();
		try {
			IMDQueryFacade mdqry = MDBaseQueryFacade.getInstance();
			String metaclass = (String) ctx.getWindowContext().getAppAttribute(
					"metaclass");
			if(null==metaclass){
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
					.getAppAttribute("pk_print_template");
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

	public void onImportXml(MouseEvent mouseEvent) {
		AppLifeCycleContext ctx = AppLifeCycleContext.current();
		ViewContext viewCtx = ctx.getApplicationContext()
				.getCurrentWindowContext().getCurrentViewContext();
		Dataset ds = viewCtx.getView().getViewModels().getDataset("listds");
		Row row = ds.getSelectedRow();
		if (row == null)
			throw new LfwRuntimeException("请先选中待编辑模板");
		String pk_template = (String) row.getValue(ds
				.nameToIndex("pk_print_template"));
		ICpPrintXmlService importXml =  NCLocator.getInstance()
		.lookup(ICpPrintXmlService.class);
		try{
			importXml.importXml(pk_template);
		}catch(TplBusinessException e){
			throw new LfwRuntimeException(e.getMessage(),e);
		}
	}

	public void onWordUpload(MouseEvent mouseEvent) {
		AppLifeCycleContext ctx = AppLifeCycleContext.current();
		ViewContext viewCtx = ctx.getApplicationContext()
				.getCurrentWindowContext().getCurrentViewContext();
		Dataset ds = viewCtx.getView().getViewModels().getDataset("listds");
		Row row = ds.getSelectedRow();
		if (row == null)
			throw new LfwRuntimeException("请先选中待编辑模板");
		String pktemplate = (String) row.getValue(ds
				.nameToIndex("pk_print_template"));
		String filepk = (String) row.getValue(ds.nameToIndex("pk_file"));
		ctx.getWindowContext().addAppAttribute(CURRENT_ROW, row);
		boolean isquick = false;
		String extendclass= "nc.uap.ctrl.tpl.print.WordUploadExtender";
		String url = LfwRuntimeEnvironment.getRootPath() + "/core/file.jsp?pageId=file&isquick="+isquick+"&extendclass="+extendclass+"&billitem="+pktemplate;
		if(filepk != null && !"".equals(filepk.trim())){
			url += "&filepk=" + filepk;
		}
		String title = "模板上传";
		String width = "450";
		String height = "430";
		AppLifeCycleContext.current().getApplicationContext().popOuterWindow(
				url, title, width, height);
		ds.setCurrentKey(ds.MASTER_KEY);
	}
	
	
	 public void pluginappscategory_plugin(  Map keys){
	    LfwWidget main = AppLifeCycleContext.current().getWindowContext().getViewContext("main").getView();
		  Dataset ds = main.getViewModels().getDataset("listds");
		  TranslatedRow r = (TranslatedRow) keys.get("appscategory_click");
		  final String pk_appscategory = (String)r.getValue("id");
		 // getCurrentWinCtx().addAppAttribute("pk_appscategory",pk_appscategory);
		  CmdInvoker.invoke(new UifDatasetLoadCmd(ds.getId()){
			  protected String postProcessQueryVO(SuperVO vo, Dataset ds) {
					String where =" nodecode='"+pk_appscategory+"'";
					ds.setLastCondition(where);
					return where ;			  
				}
			});
	 	}
	 
	  public void onAfterRowSelect(  DatasetEvent datasetEvent){
	    Dataset ds = datasetEvent.getSource();
		Row row = ds.getSelectedRow();
		String pk_template = (String) row.getValue(ds.nameToIndex("pk_print_template"));
		//设置模板pk
		AppLifeCycleContext.current().getApplicationContext().addAppAttribute(TemplateConstant.PK_TEMPLATE, pk_template);
		//设置模板类型
		AppLifeCycleContext.current().getApplicationContext().addAppAttribute(TemplateConstant.TEMPLATE_TYPE, TemplateConstant.TEMPLATE_PRINT);
		
	  }
}
