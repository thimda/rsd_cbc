package nc.uap.wfm.flowsetting;
import java.util.Map;
import nc.uap.lfw.core.cmd.UifPlugoutCmd;
import nc.uap.lfw.core.comp.ButtonComp;
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
import nc.uap.wfm.vo.WfmProdefVO;
import nc.vo.pub.SuperVO;
/**
 * @author chouhl
 */
public class WfmProDefEditCtrl implements IController {
	private static final long serialVersionUID = 1L;
	public void pluginprodef_pluginin(Map<String, Object> map) {
		LfwWidget widgetEdit = AppUtil.getWidget("edit_prodef");
		Dataset dsProDef = widgetEdit.getViewModels().getDataset("ds_prodef");
		String operator = (String) AppUtil.getAppAttr("operator");
		Row emptyRow = dsProDef.getEmptyRow();
		if ("addProDef".equalsIgnoreCase(operator)) {
			dsProDef.clear();
			emptyRow.setValue(dsProDef.nameToIndex(WfmProdefVO.FLWTYPE), AppUtil.getAppAttr("flowtypepk"));
			dsProDef.setCurrentKey(Dataset.MASTER_KEY);
			dsProDef.addRow(emptyRow);
			dsProDef.setRowSelectIndex(0);
			dsProDef.setEnabled(true);
		} else if ("modifyProDef".equalsIgnoreCase(operator)) {
			dsProDef.clear();
			this.setValues(emptyRow, dsProDef, map);
			dsProDef.addRow(emptyRow);
			dsProDef.setRowSelectIndex(0);
			dsProDef.setEnabled(true);
		}
	}
	public void btnOk_click(MouseEvent<ButtonComp> e) {
		LfwWidget widgetEdit = AppUtil.getWidget("edit_prodef");
		Dataset dsProDef = widgetEdit.getViewModels().getDataset("ds_prodef");
		SuperVO[] vos = new Dataset2SuperVOSerializer<SuperVO>().serialize(dsProDef, dsProDef.getSelectedRow());
		if (vos == null || vos.length == 0) {
			return;
		}
		WfmProdefVO vo = (WfmProdefVO) vos[0];
		try {
			if (vo.getPk_prodef() == null || vo.getPk_prodef().length() == 0) {
				WfmServiceFacility.getProDefBill().insertProdef(vo);
			} else {
				WfmServiceFacility.getProDefBill().updateProdef(vo);
			}
			dsProDef.getSelectedRow().setValue(dsProDef.nameToIndex(WfmProdefVO.PK_PRODEF), vo.getPk_prodef());
		} catch (WfmServiceException e1) {
			LfwLogger.error(e1.getMessage(), e1);
			throw new LfwRuntimeException(e1.getMessage());
		}
		new UifPlugoutCmd("edit_prodef", "prodef_pluginout").execute();
		AppUtil.getCntAppCtx().getCurrentWindowContext().closeView("edit_prodef");
	}
	public void btnCancel_click(MouseEvent<ButtonComp> e) {
		AppUtil.getCntAppCtx().getCurrentWindowContext().closeView("edit_prodef");
	}
	private void setValues(Row row, Dataset ds, Map<String, Object> map) {
		TranslatedRow r = (TranslatedRow) map.get("selectedrow");
		String[] keys = r.getKeys();
		for (String key : keys) {
			row.setValue(ds.nameToIndex(key), r.getValue(key));
		}
	}
}
