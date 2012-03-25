package nc.uap.wfm.flowsetting;
import java.util.Map;
import nc.uap.cpb.org.querycmd.QueryCmd;
import nc.uap.lfw.core.InteractionUtil;
import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.bm.ButtonStateManager;
import nc.uap.lfw.core.cmd.CmdInvoker;
import nc.uap.lfw.core.cmd.UifDatasetAfterSelectCmd;
import nc.uap.lfw.core.cmd.UifDatasetLoadCmd;
import nc.uap.lfw.core.cmd.UifPlugoutCmd;
import nc.uap.lfw.core.cmd.base.FromWhereSQL;
import nc.uap.lfw.core.comp.MenuItem;
import nc.uap.lfw.core.ctrl.IController;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.PaginationInfo;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.event.DatasetEvent;
import nc.uap.lfw.core.event.MouseEvent;
import nc.uap.lfw.core.exception.LfwBusinessException;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.lfw.core.model.plug.TranslatedRow;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.serializer.impl.SuperVO2DatasetSerializer;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.facility.WfmServiceFacility;
import nc.uap.wfm.utils.AppUtil;
import nc.uap.wfm.vo.WfmFlwTypeVO;
import nc.uap.wfm.vo.WfmProdefVO;
import nc.vo.pub.SuperVO;
import org.apache.commons.lang.StringUtils;
/**
 * @author chouhl
 */
public class WfmFlowMainCtrl implements IController {
	private static final long serialVersionUID = 1L;
	public void pluginflowmain_pluginin(Map<String, Object> map) {
		String operator = (String) AppUtil.getAppAttr("operator");
		if ("onload".equalsIgnoreCase(operator)) {
			TranslatedRow r = (TranslatedRow) map.get("selectedrow");
			final String flowcatePk = (String) r.getValue("pk_flwcat");
			AppUtil.addAppAttr("flowcatePk", flowcatePk);
			new UifDatasetLoadCmd("ds_flowtype") {
				protected SuperVO[] queryVOs(PaginationInfo pinfo, SuperVO vo, String wherePart, String orderPart) throws LfwBusinessException {
					return super.queryVOs(pinfo, vo, "pk_flwcat='" + flowcatePk + "'", orderPart);
				}
				protected String postOrderPart(Dataset ds) {
					return "ts";
				}
			}.execute();
		}
		LfwWidget mainWidget = AppUtil.getWidget("main");
		Dataset dsFlowType = mainWidget.getViewModels().getDataset("ds_flowtype");
		Row row = null;
		if ("addChildren".equalsIgnoreCase(operator)) {
			row = dsFlowType.getEmptyRow();
			setValues(row, dsFlowType, map);
			dsFlowType.addRow(row);
			dsFlowType.setRowSelectIndex(dsFlowType.getRowIndex(row));
		} else if ("modifyChildren".equalsIgnoreCase(operator) || "modifyParent".equalsIgnoreCase(operator)) {
			row = dsFlowType.getSelectedRow();
			setValues(row, dsFlowType, map);
		}
		Dataset dsProDef = mainWidget.getViewModels().getDataset("ds_prodef");
		if ("addProDef".equalsIgnoreCase(operator)) {
			row = dsProDef.getEmptyRow();
			setValues(row, dsProDef, map);
			dsProDef.addRow(row);
			dsProDef.setRowSelectIndex(dsProDef.getRowIndex(row));
		} else if ("modifyProDef".equalsIgnoreCase(operator)) {
			row = dsProDef.getSelectedRow();
			setValues(row, dsProDef, map);
		}
		AppUtil.addAppAttr("operator", "");
	}
	private void setValues(Row row, Dataset ds, Map<String, Object> map) {
		TranslatedRow r = (TranslatedRow) map.get("selectedrow");
		String[] keys = r.getKeys();
		for (String key : keys) {
			row.setValue(ds.nameToIndex(key), r.getValue(key));
		}
	}
	public void addChildren(MouseEvent<MenuItem> e) {
		AppUtil.addAppAttr("operator", "addChildren");
		new UifPlugoutCmd("main", "flowmain_pluginout").execute();
		AppUtil.getCntAppCtx().getCurrentWindowContext().popView("edit_flowtype", "600", "430", "增加业务子类型");
	}
	public void modifyChildren(MouseEvent<MenuItem> e) {
		LfwWidget widetMain = AppUtil.getWidget("main");
		Dataset dsFlowType = widetMain.getViewModels().getDataset("ds_flowtype");
		Row selectedRow = dsFlowType.getSelectedRow();
		if (selectedRow == null) {
			throw new LfwRuntimeException("请选择要修改的业务类型");
		}
		String parentPk = (String) selectedRow.getValue(dsFlowType.nameToIndex(WfmFlwTypeVO.PK_PARENT));
		if (parentPk == null) {
			this.modifyParent(e);
			return;
		}
		AppUtil.addAppAttr("operator", "modifyChildren");
		new UifPlugoutCmd("main", "flowmain_pluginout").execute();
		AppUtil.getCntAppCtx().getCurrentWindowContext().popView("edit_flowtype", "600", "430", "修改业务子类型");
	}
	public void deleteChildern(MouseEvent<MenuItem> e) {
		LfwWidget widgetMain = AppUtil.getWidget("main");
		Dataset dsFlowType = widgetMain.getViewModels().getDataset("ds_flowtype");
		Row selectedRow = dsFlowType.getSelectedRow();
		if (selectedRow == null) {
			throw new LfwRuntimeException("请选择要删除的业务子类型");
		}
		String parentPk = (String) selectedRow.getValue(dsFlowType.nameToIndex(WfmFlwTypeVO.PK_PARENT));
		if (parentPk == null) {
			throw new LfwRuntimeException("请选择要删除的业务子类型");
		}
		InteractionUtil.showConfirmDialog("确认删除", "确认要删除这条数据吗?");
		if (InteractionUtil.getConfirmDialogResult().booleanValue()) {
			String flowTypePk = (String) selectedRow.getValue(dsFlowType.nameToIndex(WfmFlwTypeVO.PK_FLWTYPE));
			try {
				WfmServiceFacility.getFlwTypeBill().deleteFlwTypeByPk(flowTypePk);
			} catch (WfmServiceException e1) {
				LfwLogger.error(e1.getMessage(), e1);
				throw new LfwRuntimeException(e1.getMessage());
			}
			dsFlowType.removeRow(selectedRow);
		}
	}
	public void modifyParent(MouseEvent<MenuItem> e) {
		LfwWidget widetMain = AppUtil.getWidget("main");
		Dataset dsFlowType = widetMain.getViewModels().getDataset("ds_flowtype");
		Row selectedRow = dsFlowType.getSelectedRow();
		if (selectedRow == null) {
			throw new LfwRuntimeException("请选择要修改的业务类型");
		}
		String parentPk = (String) selectedRow.getValue(dsFlowType.nameToIndex(WfmFlwTypeVO.PK_PARENT));
		if (parentPk != null) {
			throw new LfwRuntimeException("请选择要修改的业务类型");
		}
		AppUtil.addAppAttr("operator", "modifyParent");
		new UifPlugoutCmd("main", "flowmain_pluginout").execute();
		AppUtil.getCntAppCtx().getCurrentWindowContext().popView("edit_flowtype", "600", "430", "修改业务类型");
	}
	public void wfmFlowTypeOnAfterRow(DatasetEvent se) {
		LfwWidget widetMain = AppUtil.getWidget("main");
		Dataset dsFlowType = widetMain.getViewModels().getDataset("ds_flowtype");
		Row selectedRow = dsFlowType.getSelectedRow();
		if (selectedRow == null) {
			return;
		}
		String flowTypePk = (String) selectedRow.getValue(dsFlowType.nameToIndex(WfmFlwTypeVO.PK_FLWTYPE));
		if (flowTypePk == null || flowTypePk.length() == 0) {
			return;
		}
		WfmProdefVO[] vos = null;
		try {
			vos = WfmServiceFacility.getProDefQry().getProDefByFlwTypePk(flowTypePk);
		} catch (WfmServiceException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
		Dataset dsProDef = widetMain.getViewModels().getDataset("ds_prodef");
		dsProDef.clear();
		new SuperVO2DatasetSerializer().serialize(vos, dsProDef, Row.STATE_NORMAL);
		ButtonStateManager.updateButtons();
	}
	public void addProDef(MouseEvent<MenuItem> e) {
		LfwWidget widetMain = AppUtil.getWidget("main");
		Dataset dsFlowType = widetMain.getViewModels().getDataset("ds_flowtype");
		Row selectedRow = dsFlowType.getSelectedRow();
		if (selectedRow == null) {
			throw new LfwRuntimeException("请选择业务类型");
		}
		AppUtil.addAppAttr("flowtypepk", (String) selectedRow.getValue(dsFlowType.nameToIndex(WfmFlwTypeVO.PK_FLWTYPE)));
		AppUtil.addAppAttr("operator", "addProDef");
		new UifPlugoutCmd("main", "prodef_pluginout").execute();
		AppUtil.getCntAppCtx().getCurrentWindowContext().popView("edit_prodef", "600", "430", "增加流程定义");
	}
	public void modifyProDef(MouseEvent<MenuItem> e) {
		AppUtil.addAppAttr("operator", "modifyProDef");
		new UifPlugoutCmd("main", "prodef_pluginout").execute();
		AppUtil.getCntAppCtx().getCurrentWindowContext().popView("edit_prodef", "600", "430", "修改流程定义");
	}
	public void deleteProDef(MouseEvent<MenuItem> e) {
		LfwWidget widgetMain = AppUtil.getWidget("main");
		Dataset dsProDef = widgetMain.getViewModels().getDataset("ds_prodef");
		Row selectedRow = dsProDef.getSelectedRow();
		if (selectedRow == null) {
			throw new LfwRuntimeException("请选择要删除的流程定义");
		}
		InteractionUtil.showConfirmDialog("确认删除", "确认要删除这条数据吗?");
		if (InteractionUtil.getConfirmDialogResult().booleanValue()) {
			String proDefPk = (String) selectedRow.getValue(dsProDef.nameToIndex(WfmProdefVO.PK_PRODEF));
			try {
				WfmServiceFacility.getProDefBill().deleteProDefByPk(proDefPk);
			} catch (WfmServiceException e1) {
				LfwLogger.error(e1.getMessage(), e1);
				throw new LfwRuntimeException(e1.getMessage());
			}
			dsProDef.removeRow(selectedRow);
		}
	}
	public void designProDef(MouseEvent<MenuItem> e) {
		String projectPath = LfwRuntimeEnvironment.getRootPath();
		LfwWidget widgetMain = AppUtil.getWidget("main");
		Dataset dsProDef = widgetMain.getViewModels().getDataset("ds_prodef");
		Row selectedRow = dsProDef.getSelectedRow();
		if (selectedRow == null) {
			throw new LfwRuntimeException("请选择一条流程定义");
		}
		String proDefPk = selectedRow.getString(dsProDef.nameToIndex("pk_prodef"));
		String url = projectPath + "/html/bin-release/wfdesigner.jsp?";
		if (StringUtils.isNotBlank(proDefPk)) {
			url = url + "&proDefPk=" + proDefPk;
		}
		String groupPk = LfwRuntimeEnvironment.getLfwSessionBean().getPk_unit();
		if (StringUtils.isNotBlank(groupPk)) {
			url = url + "&groupPk=" + groupPk;
		}
		url = "window.open ('" + url + "', '流程设计', 'height=768, width=1024, top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=n o, status=no')";
		AppUtil.getCntAppCtx().addExecScript(url);
	}
	public void codeRuleEdit(MouseEvent<MenuItem> e) {
		LfwWidget widgetMain = AppUtil.getWidget("main");
		Dataset dsFlowtype = widgetMain.getViewModels().getDataset("ds_flowtype");
		Row selectedRow = dsFlowtype.getSelectedRow();
		String flowTypePk = (String) selectedRow.getValue(dsFlowtype.nameToIndex(WfmFlwTypeVO.PK_FLWTYPE));
		AppUtil.addAppAttr("flowtypepk", flowTypePk);
		AppUtil.getCntAppCtx().navgateTo("wfm_frmbillnummgr", "", "800", "600");
	}
	public void allotProDef(MouseEvent<MenuItem> e) {
		{}
	}
	public void startUpProDef(MouseEvent<MenuItem> e) {
		{}
	}
	public void pluginsimpleQuery_plugin(Map<Object, Object> keys) {
		FromWhereSQL whereSql = (FromWhereSQL) keys.get("whereSql");
		String wheresql = whereSql.getWhere();
		QueryCmd cmd = new QueryCmd("main", "ds_flowtype", wheresql);
		cmd.excute();
	}
	public void proDef_onAfterRowSelect(DatasetEvent datasetEvent) {
		Dataset ds = datasetEvent.getSource();
		CmdInvoker.invoke(new UifDatasetAfterSelectCmd(ds.getId()));
	}
}
