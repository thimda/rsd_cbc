package nc.uap.wfm.frmbillnummgr;
import java.util.Map;
import nc.uap.lfw.core.cmd.UifPlugoutCmd;
import nc.uap.lfw.core.comp.ButtonComp;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.event.MouseEvent;
import nc.uap.lfw.core.model.plug.TranslatedRow;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.serializer.impl.Dataset2SuperVOSerializer;
import nc.uap.lfw.core.serializer.impl.SuperVO2DatasetSerializer;
import nc.uap.wfm.facility.WfmServiceFacility;
import nc.uap.wfm.utils.AppUtil;
import nc.uap.wfm.vo.WfmFrmNumElemVO;
import nc.vo.pub.SuperVO;
public class WfmFrmNumDateCtrl {
	public void plugindate_pluginin1(Map<String, Object> paras) {
		String operator = (String) AppUtil.getAppAttr("operator");
		String button = (String) AppUtil.getAppAttr("bttton");
		LfwWidget widgetDate = AppUtil.getWidget("date");
		Dataset dsElem = widgetDate.getViewModels().getDataset("ds_date");
		if ("date".equalsIgnoreCase(operator)) {
			if ("add".equalsIgnoreCase(button)) {
				Row emptyRow = dsElem.getEmptyRow();
				dsElem.addRow(emptyRow);
				emptyRow.setValue(dsElem.nameToIndex(WfmFrmNumElemVO.PK_FRMNUMRULE), this.getFrmNumRulePk(paras));
				emptyRow.setValue(dsElem.nameToIndex("code"), "CurrentDate");
				emptyRow.setValue(dsElem.nameToIndex("name"), "当前日期");
				emptyRow.setValue(dsElem.nameToIndex("orderstr"), WfmServiceFacility.getFrmNumElemQry().getOrderStr(this.getFrmNumRulePk(paras)));
			}
			if ("modify".equalsIgnoreCase(button)) {
				dsElem.clear();
				WfmFrmNumElemVO frmNumElemVo = WfmServiceFacility.getFrmNumElemQry().getFrmNumElemVoByPk(this.getFrmNumElemPk(paras));
				new SuperVO2DatasetSerializer().serialize(new SuperVO[] { frmNumElemVo }, dsElem, Row.STATE_NORMAL);
			}
			dsElem.setRowSelectIndex(0);
			dsElem.setEnabled(true);
		}
	}
	public void btnOk_click(MouseEvent<ButtonComp> e) {
		LfwWidget widgetCode = AppUtil.getWidget("date");
		Dataset dsCode = widgetCode.getViewModels().getDataset("ds_date");
		Row selectedRow = dsCode.getSelectedRow();
		SuperVO[] vos = new Dataset2SuperVOSerializer<SuperVO>().serialize(dsCode, selectedRow);
		if (vos == null || vos.length == 0) {
			return;
		}
		WfmServiceFacility.getFrmNumElemBill().saveOrUpdateFrmNumElem((WfmFrmNumElemVO) vos[0]);
		new UifPlugoutCmd("date", "date_pluginout1").execute();
		AppUtil.getCntAppCtx().getCurrentWindowContext().closeView("date");
	}
	public void btnCancel_click(MouseEvent<ButtonComp> e) {
		AppUtil.getCntAppCtx().getCurrentWindowContext().closeView("date");
	}
	public String getFrmNumRulePk(Map<String, Object> paras) {
		TranslatedRow r = (TranslatedRow) paras.get("selectedrow");
		String frmnumrulePk = (String) r.getValue(WfmFrmNumElemVO.PK_FRMNUMRULE);
		return frmnumrulePk;
	}
	public String getFrmNumElemPk(Map<String, Object> paras) {
		TranslatedRow r = (TranslatedRow) paras.get("selectedrow1");
		String frmnumElemPk = (String) r.getValue(WfmFrmNumElemVO.PK_FRMNUMELEM);
		return frmnumElemPk;
	}
}
