package nc.uap.ctrl.tpl.gz;
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
import nc.uap.cpb.org.constant.DialogConstant;
import nc.uap.ctrl.tpl.exp.TplBusinessException;
import nc.uap.ctrl.tpl.gz.base.CpGzConditionVO;
import nc.uap.ctrl.tpl.gz.base.CpGzTemplateVO;
import nc.uap.ctrl.tpl.print.ICpPrintTemplateInnerService;
import nc.uap.ctrl.tpl.print.TemplateConstantArgs;
import nc.uap.ctrl.tpl.print.base.CpPrintTemplateVO;
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
import nc.uap.lfw.core.event.DialogEvent;
import nc.uap.lfw.core.event.MouseEvent;
import nc.uap.lfw.core.event.PageEvent;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.lfw.core.model.plug.TranslatedRow;
import nc.uap.lfw.core.serializer.impl.Dataset2SuperVOSerializer;
import nc.uap.lfw.core.serializer.impl.SuperVO2DatasetSerializer;
import nc.uap.lfw.core.vo.LfwExAggVO;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.SuperVO;
public class CpGzInitCtrl implements WindowController, Serializable {
  private static final String CURRENT_ROW="row";
  private static final long serialVersionUID=7532916478964732880L;
  public void sysWindowClosed(  PageEvent event){
    LfwRuntimeEnvironment.getWebContext().destroyWebSession();
  }
  public void onAdd(  MouseEvent mouseEvent){
    AppLifeCycleContext ctx = AppLifeCycleContext.current();
		ctx.getWindowContext().removeAppAttribute(CURRENT_ROW);
		ctx.getWindowContext().addAppAttribute(TemplateConstantArgs.OPERATE_STATUS, TemplateConstantArgs.ADD_OPERATE);
		ctx.getWindowContext().popView(TemplateConstantArgs.EDIT, DialogConstant.DEFAULT_WIDTH, DialogConstant.FIVE_ELE_HEIGHT, "编辑模板");
  }
  public void onEdit(  MouseEvent mouseEvent){
    AppLifeCycleContext ctx = AppLifeCycleContext.current();
		ViewContext viewCtx = ctx.getApplicationContext().getCurrentWindowContext().getCurrentViewContext();
		Dataset ds = viewCtx.getView().getViewModels().getDataset(TemplateConstantArgs.LISTDS);
		Row row = ds.getSelectedRow();
		if (row == null)
			throw new LfwRuntimeException("请先选中待编辑模板");
		ctx.getWindowContext().addAppAttribute(CURRENT_ROW, row);
		ctx.getWindowContext().addAppAttribute(TemplateConstantArgs.OPERATE_STATUS, TemplateConstantArgs.EDIT_OPERATE);
		ctx.getWindowContext().popView(TemplateConstantArgs.EDIT, DialogConstant.DEFAULT_WIDTH, DialogConstant.FIVE_ELE_HEIGHT, "编辑模板");
  }
  public void onDel(  MouseEvent mouseEvent){
	  AppLifeCycleContext ctx = AppLifeCycleContext.current();
		ViewContext viewCtx = ctx.getApplicationContext().getCurrentWindowContext().getCurrentViewContext();
		Dataset ds = viewCtx.getView().getViewModels().getDataset(TemplateConstantArgs.LISTDS);
		Row row = ds.getSelectedRow();
		if (row == null)
			throw new LfwRuntimeException("请先选中待删除模板");
	  	UifDelCmd cmd = new UifDelCmd(TemplateConstantArgs.LISTDS,LfwExAggVO.class.getName());
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
		Dataset ds = viewCtx.getView().getViewModels().getDataset(TemplateConstantArgs.LISTDS);
		Row row = ds.getSelectedRow();
		if (row == null)
			throw new LfwRuntimeException("请先选中待编辑模板");
		String metaclass = (String) row.getValue(ds.nameToIndex(TemplateConstantArgs.METACLASS));
		String pktemplate = (String) row.getValue(ds
				.nameToIndex(TemplateConstantArgs.PK_GZ_TEMPLATE));
		ctx.getWindowContext().addAppAttribute(TemplateConstantArgs.METACLASS, metaclass);
		ctx.getWindowContext().addAppAttribute(TemplateConstantArgs.PK_GZ_TEMPLATE, pktemplate);
		ctx.getWindowContext().popView(TemplateConstantArgs.CONDITION, "1000", "500", "编辑查询条件");
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
		Dataset ds = viewCtx.getView().getViewModels().getDataset(TemplateConstantArgs.LISTDS);
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
				ICpGzTemplateInnerService service = NCLocator.getInstance()
						.lookup(ICpGzTemplateInnerService.class);
				try {
					CpGzTemplateVO vo = (CpGzTemplateVO) aggvo
							.getParentVO();
					if (vo.getPk_gz_template() == null
							|| vo.getPk_gz_template().length() == 0) {
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
				TemplateConstantArgs.PK_GZ_TEMPLATE);
		IBusinessEntity bean;
		try {
			if (null == metaclass) {
				throw new LfwRuntimeException("元数据主实体为空");
			}
			bean = (IBusinessEntity) mdqry.getBeanByID(metaclass);
			new CpGzInitTool().addLine(sourceDs, targetDs, bean, pk_template);
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
		Dataset2SuperVOSerializer<CpGzConditionVO> s = new Dataset2SuperVOSerializer<CpGzConditionVO>();
		CpGzConditionVO[] vos = s.serialize(targetDs);
		ICpGzTemplateInnerService qry = NCLocator.getInstance().lookup(ICpGzTemplateInnerService.class);

		String pk_template = (String) ctx.getWindowContext().getAppAttribute(
				TemplateConstantArgs.PK_GZ_TEMPLATE);
		try {
			qry.initConditons(pk_template, vos);
			CmdInvoker.invoke(new UifCloseViewCmd(TemplateConstantArgs.CONDITION));
		} catch (TplBusinessException e) {
			LfwLogger.error(e);
		}
  }
  public void conditionBeforeShow(  DialogEvent dialogEvent){
    AppLifeCycleContext ctx = AppLifeCycleContext.current();
		ViewContext viewCtx = ctx.getApplicationContext()
				.getCurrentWindowContext().getCurrentViewContext();
		Dataset entityDs = viewCtx.getView().getViewModels().getDataset("entityds");
		entityDs.clear();
		Dataset conditionDs = viewCtx.getView().getViewModels().getDataset(TemplateConstantArgs.CONDITIONDS);
		conditionDs.clear();
		try {
			IMDQueryFacade mdqry = MDBaseQueryFacade.getInstance();
			String metaclass = (String) ctx.getWindowContext().getAppAttribute(
					TemplateConstantArgs.METACLASS);
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
					.getAppAttribute(TemplateConstantArgs.PK_GZ_TEMPLATE);
			ICpGzTemplateInnerQryService qryService = NCLocator.getInstance().lookup(ICpGzTemplateInnerQryService.class);
			CpGzConditionVO[] conds = qryService.getGzConditions(pk_template);
			SuperVO2DatasetSerializer s = new SuperVO2DatasetSerializer();
			s.serialize(conds, conditionDs);
			conditionDs.setEnabled(true);
		} catch (TplBusinessException e) {
			LfwLogger.error(e);
			throw new LfwRuntimeException(e.getMessage());
		}
  }
  /** 
 * 主界面列表加载
 * @param dataLoadEvent
 */
  public void onDataLoad_listds(  DataLoadEvent dataLoadEvent){
    Dataset ds = dataLoadEvent.getSource();
		CmdInvoker.invoke(new UifDatasetLoadCmd(ds.getId()));
  }
  public void onDataLoad_entityds(  DataLoadEvent dataLoadEvent){
    Dataset ds = dataLoadEvent.getSource();
		CmdInvoker.invoke(new UifDatasetLoadCmd(ds.getId()));
  }
}
