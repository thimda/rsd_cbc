package nc.uap.wfm.flowagent;
import java.util.Map;
import nc.uap.cpb.org.util.CpbUtil;
import nc.uap.lfw.core.cmd.UifPlugoutCmd;
import nc.uap.lfw.core.comp.ButtonComp;
import nc.uap.lfw.core.ctrl.IController;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.event.MouseEvent;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.serializer.impl.Dataset2SuperVOSerializer;
import nc.uap.wfm.facility.WfmServiceFacility;
import nc.uap.wfm.utils.AppUtil;
import nc.uap.wfm.vo.WfmFlowAgentVO;
import nc.uap.wfm.vo.WfmFlwTypeVO;
/**
 * @author chouhl
 */
public class FlowAgentEditCtrl implements IController {
	private static final long serialVersionUID = 1L;
	public void btn_cancel_onclick(MouseEvent<ButtonComp> mouseEvent) {
		AppUtil.getCntAppCtx().getCurrentWindowContext().closeView("edit");
	}
	public void btn_ok_onclick(MouseEvent<ButtonComp> mouseEvent) {
		LfwWidget widgetEdit = AppUtil.getWidget("edit");
		Dataset dsFlowAgent = widgetEdit.getViewModels().getDataset("ds_flowagent");
		Row selectedRow = dsFlowAgent.getSelectedRow();
		if (selectedRow == null) {
			return;
		}
		WfmFlowAgentVO vos[] = new Dataset2SuperVOSerializer<WfmFlowAgentVO>().serialize(dsFlowAgent, selectedRow);
		if (vos == null || vos.length == 0) {
			return;
		}
		WfmServiceFacility.getFlowAgentBill().saveOrUpdateFlowAgent(vos[0]);
		selectedRow.setValue(dsFlowAgent.nameToIndex(WfmFlowAgentVO.PK_FLOWAGENT), vos[0].getPk_flowagent());
		AppUtil.addAppAttr("selectedrow", selectedRow);
		new UifPlugoutCmd("edit", "edit_out_flowagent").execute();
		AppUtil.getCntAppCtx().getCurrentWindowContext().closeView("edit");
	}
	public void plugineidt_in_flowagent(Map<String, Object> keys) {
		String operator = (String) AppUtil.getAppAttr("operator");
		LfwWidget widgetEdit = AppUtil.getWidget("edit");
		Dataset dsFlowAgent = widgetEdit.getViewModels().getDataset("ds_flowagent");
		String cntTabItemId = (String) AppUtil.getAppAttr("cntTabCompItem");
		if ("add".equalsIgnoreCase(operator)) {
			dsFlowAgent.clear();
			dsFlowAgent.setCurrentKey(Dataset.MASTER_KEY);
			Row emptyRow = dsFlowAgent.getEmptyRow();
			dsFlowAgent.addRow(emptyRow);
			emptyRow.setValue(dsFlowAgent.nameToIndex(WfmFlowAgentVO.PK_USER), CpbUtil.getCntUserPk());
			emptyRow.setValue(dsFlowAgent.nameToIndex(WfmFlowAgentVO.PK_FLOWTYPE), AppUtil.getAppAttr(WfmFlwTypeVO.PK_FLWTYPE));
			dsFlowAgent.setRowSelectIndex(0);
			dsFlowAgent.setEnabled(true);
			if ("0".equalsIgnoreCase(cntTabItemId)) {
				emptyRow.setValue(dsFlowAgent.nameToIndex(WfmFlowAgentVO.PK_AGENTTYPE), "0");
			}
			if ("1".equalsIgnoreCase(cntTabItemId)) {
				emptyRow.setValue(dsFlowAgent.nameToIndex(WfmFlowAgentVO.PK_AGENTTYPE), "1");
			}
		}
		if ("modify".equalsIgnoreCase(operator)) {
			Row selectedRow = (Row) AppUtil.getAppAttr("selectedrow");
			if (selectedRow == null) {
				throw new LfwRuntimeException("请选择要修改的数据行");
			}
			dsFlowAgent.clear();
			dsFlowAgent.setCurrentKey(Dataset.MASTER_KEY);
			dsFlowAgent.addRow(selectedRow);
			dsFlowAgent.setRowSelectIndex(0);
			dsFlowAgent.setEnabled(true);
			if ("0".equalsIgnoreCase(cntTabItemId)) {
				selectedRow.setValue(dsFlowAgent.nameToIndex(WfmFlowAgentVO.PK_AGENTTYPE), "0");
			}
			if ("1".equalsIgnoreCase(cntTabItemId)) {
				selectedRow.setValue(dsFlowAgent.nameToIndex(WfmFlowAgentVO.PK_AGENTTYPE), "1");
			}
		}
	}
}
