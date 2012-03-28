package nc.uap.wfm.flowagent;
import java.util.Map;
import nc.uap.cpb.org.constant.DialogConstant;
import nc.uap.cpb.org.util.CpbUtil;
import nc.uap.lfw.core.AppInteractionUtil;
import nc.uap.lfw.core.cmd.UifPlugoutCmd;
import nc.uap.lfw.core.comp.MenuItem;
import nc.uap.lfw.core.ctrl.IController;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.Field;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.event.DataLoadEvent;
import nc.uap.lfw.core.event.MouseEvent;
import nc.uap.lfw.core.event.TabEvent;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.serializer.impl.SuperVO2DatasetSerializer;
import nc.uap.lfw.jsp.uimeta.UITabComp;
import nc.uap.wfm.facility.WfmServiceFacility;
import nc.uap.wfm.utils.AppUtil;
import nc.uap.wfm.vo.WfmFlowAgentVO;
import nc.uap.wfm.vo.WfmFlwTypeVO;
/**
 * @author chouhl
 */
public class FlowAgentMainCtrl implements IController {
	private static final long serialVersionUID = 1L;
	public void itemadd_onclick(MouseEvent<MenuItem> mouseEvent) {
		AppUtil.getCntAppCtx().getCurrentWindowContext().popView("edit", DialogConstant.FIVE_ELE_WIDTH, DialogConstant.FIVE_ELE_HEIGHT, "新增代理");
		new UifPlugoutCmd("main", "main_out_flowagent").execute();
		AppUtil.addAppAttr("operator", "add");
	}
	public void itemdelete_onclick(MouseEvent<MenuItem> mouseEvent) {
		LfwWidget widgetMain = AppUtil.getWidget("main");
		Dataset dsFlowAgent = null;
		String cntTabItemId = (String) AppUtil.getAppAttr("cntTabCompItem");
		if ("0".equalsIgnoreCase(cntTabItemId)) {
			dsFlowAgent = widgetMain.getViewModels().getDataset("ds_flowagent1");
		} else if ("1".equalsIgnoreCase(cntTabItemId)) {
			dsFlowAgent = widgetMain.getViewModels().getDataset("ds_flowagent2");
		}
		Row selectedRow = dsFlowAgent.getSelectedRow();
		if (selectedRow == null) {
			throw new LfwRuntimeException("请选中要删除的行");
		}
		AppInteractionUtil.showConfirmDialog("确认删除", "确定要删除？");
		if (AppInteractionUtil.getConfirmDialogResult()) {
			String flowAgentPk = (String) selectedRow.getValue(dsFlowAgent.nameToIndex(WfmFlowAgentVO.PK_FLOWAGENT));
			WfmServiceFacility.getFlowAgentBill().deleteFlowAgent(flowAgentPk);
			dsFlowAgent.removeRow(selectedRow);
			AppInteractionUtil.showMessageDialog("删除成功");
		}
	}
	public void itemmodify_onclick(MouseEvent<MenuItem> mouseEvent) {
		AppUtil.getCntAppCtx().getCurrentWindowContext().popView("edit", DialogConstant.FIVE_ELE_WIDTH, DialogConstant.FIVE_ELE_HEIGHT, "修改代理");
		LfwWidget widgetMain = AppUtil.getWidget("main");
		Dataset dsFlowAgent = null;
		String cntTabItemId = (String) AppUtil.getAppAttr("cntTabCompItem");
		if ("0".equalsIgnoreCase(cntTabItemId)) {
			dsFlowAgent = widgetMain.getViewModels().getDataset("ds_flowagent1");
		} else if ("1".equalsIgnoreCase(cntTabItemId)) {
			dsFlowAgent = widgetMain.getViewModels().getDataset("ds_flowagent2");
		}
		if (dsFlowAgent == null) {
			return;
		}
		Row selectedRow = dsFlowAgent.getSelectedRow();
		if (selectedRow == null) {
			throw new LfwRuntimeException("请选择要修改的行数据");
		}
		AppUtil.addAppAttr("selectedrow", selectedRow);
		AppUtil.addAppAttr("operator", "modify");
		new UifPlugoutCmd("main", "main_out_flowagent").execute();
		
	}
	public void flowagent1_onDataLoad(DataLoadEvent dataLoadEvent) {
		Dataset ds = dataLoadEvent.getSource();
		String flowTypePk = (String) AppUtil.getAppAttr(WfmFlwTypeVO.PK_FLWTYPE);
		String sql = "pk_flowtype='" + flowTypePk + "'";
		sql = sql + " and pk_agenttype='0' and pk_user='" + CpbUtil.getCntUserPk() + "'";
		WfmFlowAgentVO[] vos = WfmServiceFacility.getFlowAgentQry().getFlowAgentVosByWhere(sql);
		new SuperVO2DatasetSerializer().serialize(vos, ds, Row.STATE_NORMAL);
	}
	public void flowagent2_onDataLoad(DataLoadEvent dataLoadEvent) {
		Dataset ds = dataLoadEvent.getSource();
		String flowTypePk = (String) AppUtil.getAppAttr(WfmFlwTypeVO.PK_FLWTYPE);
		String sql = "pk_flowtype='" + flowTypePk + "'";
		sql = sql + " and pk_agenttype='1' and pk_user='" + CpbUtil.getCntUserPk() + "'";
		WfmFlowAgentVO[] vos = WfmServiceFacility.getFlowAgentQry().getFlowAgentVosByWhere(sql);
		new SuperVO2DatasetSerializer().serialize(vos, ds, Row.STATE_NORMAL);
	}
	public void afterActivedTabItemChange(TabEvent tabEvent) {
		UITabComp tabComp = tabEvent.getSource();
		String cntTabCompItem = tabComp.getCurrentItem();
		AppUtil.addAppAttr("cntTabCompItem", cntTabCompItem);
		LfwWidget widgetMain = AppUtil.getWidget("main");
		Dataset dsFlowAgent = null;
		DataLoadEvent serverEvent = null;
		if ("0".equalsIgnoreCase(cntTabCompItem)) {
			dsFlowAgent = widgetMain.getViewModels().getDataset("ds_flowagent1");
			serverEvent = new DataLoadEvent(dsFlowAgent);
			this.flowagent1_onDataLoad(serverEvent);
		} else if ("1".equalsIgnoreCase(cntTabCompItem)) {
			dsFlowAgent = widgetMain.getViewModels().getDataset("ds_flowagent2");
			serverEvent = new DataLoadEvent(dsFlowAgent);
			this.flowagent2_onDataLoad(serverEvent);
		}
	}
	public void setValues(Row oldRow, Row newRow, Dataset dsDataset) {
		Field[] fields = dsDataset.getFieldSet().getFields();
		for (Field field : fields) {
			oldRow.setValue(dsDataset.nameToIndex(field.getId()), newRow.getValue(dsDataset.nameToIndex(field.getId())));
		}
	}
	public void pluginmain_in_flowagent(Map<String, Object> keys) {
		String operator = (String) AppUtil.getAppAttr("operator");
		String cntTabItemId = (String) AppUtil.getAppAttr("cntTabCompItem");
		LfwWidget widgetMain = AppUtil.getWidget("main");
		Dataset dsFlowAgent = null;
		if ("0".equalsIgnoreCase(cntTabItemId)) {
			dsFlowAgent = widgetMain.getViewModels().getDataset("ds_flowagent1");
		} else if ("1".equalsIgnoreCase(cntTabItemId)) {
			dsFlowAgent = widgetMain.getViewModels().getDataset("ds_flowagent2");
		}
		Row newRow = (Row) AppUtil.getAppAttr("selectedrow");
		if ("add".equalsIgnoreCase(operator)) {
			dsFlowAgent.addRow(newRow);
			dsFlowAgent.setRowSelectIndex(dsFlowAgent.getRowIndex(newRow));
		}
		if ("modify".equalsIgnoreCase(operator)) {
			Row oldRow = dsFlowAgent.getSelectedRow();
			this.setValues(oldRow, newRow, dsFlowAgent);
		}
		if ("twoDs_onAfterRowSelect".equalsIgnoreCase(operator)) {
			DataLoadEvent serverEvent = new DataLoadEvent(dsFlowAgent);
			if ("0".equalsIgnoreCase(cntTabItemId)) {
				this.flowagent1_onDataLoad(serverEvent);
			}
			if ("1".equalsIgnoreCase(cntTabItemId)) {
				this.flowagent2_onDataLoad(serverEvent);
			}
		}
		AppUtil.getCntAppCtx().addAppAttribute("operator", "");
	}
}
