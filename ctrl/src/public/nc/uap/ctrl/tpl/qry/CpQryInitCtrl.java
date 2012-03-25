package nc.uap.ctrl.tpl.qry;
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
import nc.uap.ctrl.tpl.qry.base.CpQueryConditionVO;
import nc.uap.ctrl.tpl.qry.base.CpQueryTemplateVO;
import nc.uap.ctrl.tpl.qry.init.CpQryInitTool;
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
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.SuperVO;
public class CpQryInitCtrl implements WindowController, Serializable {
  private static final String CURRENT_ROW="row";
  public static final String OPERATE_STATUS="OPERATE_STATUS";
  public static final String ADD_OPERATE="ADD_STATUS";
  public static final String EDIT_OPERATE="EDIT_STATUS";
  private static final long serialVersionUID=7532916478964732880L;
  public void sysWindowClosed(  PageEvent event){
    LfwRuntimeEnvironment.getWebContext().destroyWebSession();
  }
  public void onAdd(  MouseEvent mouseEvent){
    AppLifeCycleContext ctx = AppLifeCycleContext.current();
		ctx.getWindowContext().removeAppAttribute(CURRENT_ROW);
		ctx.getApplicationContext().getCurrentWindowContext().addAppAttribute(OPERATE_STATUS,ADD_OPERATE);
		ctx.getWindowContext().popView("edit", "800", "500", "编辑模板");
  }
  public void onEdit(  MouseEvent mouseEvent){
    AppLifeCycleContext ctx = AppLifeCycleContext.current();
		ViewContext viewCtx = ctx.getApplicationContext().getCurrentWindowContext().getCurrentViewContext();
		Dataset ds = viewCtx.getView().getViewModels().getDataset("listds");
		Row row = ds.getSelectedRow();
		if (row == null)
			throw new LfwRuntimeException("请先选中待编辑模板");
		ctx.getWindowContext().addAppAttribute(CURRENT_ROW, row);
		ctx.getApplicationContext().getCurrentWindowContext().addAppAttribute(OPERATE_STATUS,EDIT_OPERATE);
		ctx.getWindowContext().popView("edit", "800", "500", "编辑模板");
  }
  public void onDel(  MouseEvent mouseEvent){
    UifDelCmd cmd = new UifDelCmd("listds", LfwExAggVO.class.getName());
		cmd.execute();
  }
  public void onTemplateAssignEvent(  MouseEvent<?> mouseEvent){
    AppLifeCycleContext.current().getApplicationContext().navgateTo("cp_templateassign", "模板分配", "800", "600");
  }
  /** 
 * 编辑查询条件
 * @param mouseEvent
 */
  public void onCondition(  MouseEvent mouseEvent){
    AppLifeCycleContext ctx = AppLifeCycleContext.current();
		ViewContext viewCtx = ctx.getApplicationContext()
				.getCurrentWindowContext().getCurrentViewContext();
		Dataset ds = viewCtx.getView().getViewModels().getDataset("listds");
		Row row = ds.getSelectedRow();
		if (row == null)
			throw new LfwRuntimeException("请先选中待编辑模板");
		String metaclass = (String) row.getValue(ds.nameToIndex("metaclass"));
		String pktemplate = (String) row.getValue(ds
				.nameToIndex("pk_query_template"));
		ctx.getWindowContext().addAppAttribute("metaclass", metaclass);
		ctx.getWindowContext().addAppAttribute("pk_query_template", pktemplate);
		ctx.getWindowContext().popView("condition", "1000", "500", "编辑查询条件");
  }
  /** 
 * 模板编辑打开之前相应
 * @param dialogEvent
 */
  public void editBeforeShow(  DialogEvent dialogEvent){
    AppLifeCycleContext ctx = AppLifeCycleContext.current();
		ViewContext viewCtx = ctx.getApplicationContext().getCurrentWindowContext().getCurrentViewContext();
		Dataset ds = viewCtx.getView().getViewModels().getDataset("formds");
		ds.clear();
		
		Row row = (Row) ctx.getWindowContext().getAppAttributeAndRemove(CURRENT_ROW);
		Row targetRow = null;
		if(row != null)
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
		Dataset ds = viewCtx.getView().getViewModels().getDataset("listds");
		String opeStatus = (String) AppLifeCycleContext.current()
		.getApplicationContext().getCurrentWindowContext().getAppAttribute(OPERATE_STATUS);
		Row row = null;
		if (ADD_OPERATE.equals(opeStatus)) {
			// 新增行并选中
			row = ds.getEmptyRow();
			row = UifRowTranslator.translateRowToRow(ds, transRow);
			ds.addRow(row);
			ds.setSelectedIndex(ds.getRowIndex(row));
		} else if (EDIT_OPERATE.equals(opeStatus)) {
			row = ds.getSelectedRow();
			row = UifRowTranslator.translateRowToRow(ds,row,transRow);
		}
		UifSaveCmd cmd = new UifSaveCmd("listds",null,LfwExAggVO.class.getName(),false){			
			protected void onVoSave(AggregatedValueObject aggvo) {				
				ICpQryTemplateInnerService service = NCLocator.getInstance().lookup(
						ICpQryTemplateInnerService.class);
				try {
					CpQueryTemplateVO vo = (CpQueryTemplateVO) aggvo.getParentVO();
					if (vo.getPk_query_template() == null|| vo.getPk_query_template().length() == 0) {
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
		ViewContext viewCtx = ctx.getApplicationContext().getCurrentWindowContext().getCurrentViewContext();
		Dataset ds = viewCtx.getView().getViewModels().getDataset("formds");
		CmdInvoker.invoke(new UifPlugoutCmd(ds.getWidget().getId(),"addPlugout"));
		CmdInvoker.invoke(new UifCloseViewCmd("edit"));
//		Row row = ds.getSelectedRow();
//		Dataset2SuperVOSerializer s = new Dataset2SuperVOSerializer();
//		SuperVO[] vos = s.serialize(ds, row);
//		ICpQryTemplateInnerService service = NCLocator.getInstance().lookup(
//				ICpQryTemplateInnerService.class);
//		try {
//			service.initTemplate((CpQueryTemplateVO) vos[0]);
//			CmdInvoker.invoke(new UifPlugoutCmd(ds.getWidget().getId(),
//					"addPlugout"));
//
//			CmdInvoker.invoke(new UifCloseViewCmd("edit"));
//		} catch (TplBusinessException e) {
//			LfwLogger.error(e);
//			throw new LfwRuntimeException(e.getMessage());
//		}
  }
  /** 
 * 模板编辑取消按钮
 * @param mouseEvent
 */
  public void cancelBtClick(  MouseEvent mouseEvent){
    CmdInvoker.invoke(new UifCloseViewCmd("edit"));
  }
  /** 
 * 主界面列表加载
 * @param dataLoadEvent
 */
  public void onDataLoad_listds(  DataLoadEvent dataLoadEvent){
    Dataset ds = dataLoadEvent.getSource();
		CmdInvoker.invoke(new UifDatasetLoadCmd(ds.getId()));
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
				"conditionds");
		Row[] rows = targetDs.getSelectedRows();
		for (int i = 0; i < rows.length; i++) {
			targetDs.removeRow(rows[i]);
		}
  }
  /** 
 * 条件编辑取消按钮
 * @param mouseEvent
 */
  public void conditionCancelBt(  MouseEvent mouseEvent){
    CmdInvoker.invoke(new UifCloseViewCmd("condition"));
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
				"conditionds");
		IMDQueryFacade mdqry = MDBaseQueryFacade.getInstance();
		String metaclass = (String) ctx.getWindowContext().getAppAttribute(
				"metaclass");
		String pk_template = (String) ctx.getWindowContext().getAppAttribute(
				"pk_query_template");
		IBusinessEntity bean;
		try {
			bean = (IBusinessEntity) mdqry.getBeanByID(metaclass);
			new CpQryInitTool().addLine(sourceDs, targetDs, bean, pk_template);
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
				"conditionds");
		Dataset2SuperVOSerializer<CpQueryConditionVO> s = new Dataset2SuperVOSerializer<CpQueryConditionVO>();
		CpQueryConditionVO[] vos = s.serialize(targetDs);
		ICpQryTemplateInnerService qry = NCLocator.getInstance().lookup(
				ICpQryTemplateInnerService.class);

		String pk_template = (String) ctx.getWindowContext().getAppAttribute(
				"pk_query_template");
		try {
			qry.initConditons(pk_template, vos);
			CmdInvoker.invoke(new UifCloseViewCmd("condition"));
		} catch (TplBusinessException e) {
			LfwLogger.error(e);
		}
  }
  public void conditionBeforeShow(  DialogEvent dialogEvent){
    AppLifeCycleContext ctx = AppLifeCycleContext.current();
		ViewContext viewCtx = ctx.getApplicationContext()
				.getCurrentWindowContext().getCurrentViewContext();
		try {
			Dataset entityDs = viewCtx.getView().getViewModels().getDataset(
					"entityds");
			entityDs.clear();
			IMDQueryFacade mdqry = MDBaseQueryFacade.getInstance();
			String metaclass = (String) ctx.getWindowContext().getAppAttribute(
					"metaclass");
			IBusinessEntity bean = (IBusinessEntity) mdqry
					.getBeanByID(metaclass);
			String key = entityDs.getReqParameters().getParameterValue(
					DatasetConstant.QUERY_KEYVALUE);
			if (key != null) {
				bean = (IBusinessEntity) ((RefType) bean
						.getAttributeByPath(key).getDataType()).getRefType();
			}
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
				Row row = entityDs.getEmptyRow();
				if (key != null) {
					row.setValue(idIndex, key + "." + id);
				} else {
					row.setValue(idIndex, id);
				}
				row.setValue(nameIndex, name);

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
					.getAppAttribute("pk_query_template");
			ICpQryTemplateInnerQryService qryService = NCLocator.getInstance()
					.lookup(ICpQryTemplateInnerQryService.class);
			CpQueryConditionVO[] conds = qryService
					.getQueryConditions(pk_template);
			Dataset conditionDs = viewCtx.getView().getViewModels().getDataset(
					"conditionds");
			conditionDs.clear();
			SuperVO2DatasetSerializer s = new SuperVO2DatasetSerializer();
			s.serialize(conds, conditionDs);
			conditionDs.setEnabled(true);
		} catch (TplBusinessException e) {
			LfwLogger.error(e);
			throw new LfwRuntimeException(e.getMessage());
		}
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
	String pk_template = (String) row.getValue(ds.nameToIndex("pk_query_template"));
	//设置模板pk
	AppLifeCycleContext.current().getApplicationContext().addAppAttribute(TemplateConstant.PK_TEMPLATE, pk_template);
	//设置模板类型
	AppLifeCycleContext.current().getApplicationContext().addAppAttribute(TemplateConstant.TEMPLATE_TYPE, TemplateConstant.TEMPLATE_QUERY_TEMPLATE);
	
  }
}
