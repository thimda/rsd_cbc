package nc.uap.wfm.flowsetting;
import java.util.Map;
import nc.uap.lfw.core.cmd.UifPlugoutCmd;
import nc.uap.lfw.core.comp.ButtonComp;
import nc.uap.lfw.core.comp.FormComp;
import nc.uap.lfw.core.comp.FormElement;
import nc.uap.lfw.core.ctrl.IController;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.event.MouseEvent;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.lfw.core.model.plug.TranslatedRow;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.serializer.impl.Dataset2SuperVOSerializer;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.facility.WfmServiceFacility;
import nc.uap.wfm.utils.AppUtil;
import nc.uap.wfm.vo.WfmFlwTypeVO;
import nc.vo.pub.SuperVO;
/**
 * @author chouhl
 */
public class WfmFlowEditCtrl implements IController {
	private static final long serialVersionUID = 1L;
	public void pluginflowedit_pluginin(Map<String, Object> map) {
		TranslatedRow r = (TranslatedRow) map.get("selectedrow");
		LfwWidget widgetEdit = AppUtil.getWidget("edit_flowtype");
		Dataset dsFlowType = widgetEdit.getViewModels().getDataset("ds_flowtype");
		String operator = (String) AppUtil.getAppAttr("operator");
		Row emptyRow = dsFlowType.getEmptyRow();
		if ("addChildren".equalsIgnoreCase(operator)) {
			dsFlowType.clear();
			emptyRow.setValue(dsFlowType.nameToIndex(WfmFlwTypeVO.PK_PARENT), (String) r.getValue(WfmFlwTypeVO.PK_FLWTYPE));
			emptyRow.setValue(dsFlowType.nameToIndex(WfmFlwTypeVO.PK_FLWCAT), AppUtil.getAppAttr("flowcatePk"));
			dsFlowType.setCurrentKey(Dataset.MASTER_KEY);
			dsFlowType.addRow(emptyRow);
			dsFlowType.setRowSelectIndex(0);
			dsFlowType.setEnabled(true);
		} else if ("modifyChildren".equalsIgnoreCase(operator)) {
			dsFlowType.clear();
			this.setValues(emptyRow, dsFlowType, map);
			dsFlowType.addRow(emptyRow);
			dsFlowType.setRowSelectIndex(0);
			dsFlowType.setEnabled(true);
			FormComp formComp = (FormComp) widgetEdit.getViewComponents().getComponent("form_flowtype");
			FormElement formElement = formComp.getElementById("pk_org_name");
			formElement.setEnabled(true);
		} else if ("modifyParent".equalsIgnoreCase(operator)) {
			dsFlowType.clear();
			this.setValues(emptyRow, dsFlowType, map);
			dsFlowType.addRow(emptyRow);
			dsFlowType.setRowSelectIndex(0);
			dsFlowType.setEnabled(true);
			FormComp formComp = (FormComp) widgetEdit.getViewComponents().getComponent("form_flowtype");
			FormElement formElement = formComp.getElementById("pk_org_name");
			formElement.setEnabled(false);
		}
	}
	public void btnOk_click(MouseEvent<ButtonComp> e) {
		LfwWidget widgetEdit = AppUtil.getWidget("edit_flowtype");
		Dataset dsFlowType = widgetEdit.getViewModels().getDataset("ds_flowtype");
		SuperVO[] vos = new Dataset2SuperVOSerializer<WfmFlwTypeVO>().serialize(dsFlowType, dsFlowType.getSelectedRow());
		if (vos == null || vos.length == 0) {
			return;
		}
		WfmFlwTypeVO vo = (WfmFlwTypeVO) vos[0];
		try {
			WfmServiceFacility.getFlwTypeBill().saveFlwType(vo);
		} catch (WfmServiceException e1) {
			LfwLogger.error(e1.getMessage(), e1);
			throw new LfwRuntimeException(e1.getMessage());
		}
		dsFlowType.getSelectedRow().setValue(dsFlowType.nameToIndex(WfmFlwTypeVO.PK_FLWTYPE), vo.getPk_flwtype());
		new UifPlugoutCmd("edit_flowtype", "flowedit_pluginout").execute();
		AppUtil.getCntAppCtx().getCurrentWindowContext().closeView("edit_flowtype");
	}
	public void btnCancel_click(MouseEvent<ButtonComp> e) {
		AppUtil.getCntAppCtx().getCurrentWindowContext().closeView("edit_flowtype");
	}
	private void setValues(Row row, Dataset ds, Map<String, Object> map) {
		TranslatedRow r = (TranslatedRow) map.get("selectedrow");
		String[] keys = r.getKeys();
		for (String key : keys) {
			row.setValue(ds.nameToIndex(key), r.getValue(key));
		}
	}
}
