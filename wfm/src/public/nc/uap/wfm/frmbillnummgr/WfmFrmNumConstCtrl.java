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
public class WfmFrmNumConstCtrl {
	public void pluginconst_pluginin1(Map<String, Object> paras) {
		String operator = (String) AppUtil.getAppAttr("operator");
		String button = (String) AppUtil.getAppAttr("bttton");
		LfwWidget widgetConst = AppUtil.getWidget("const");
		Dataset dsConst = widgetConst.getViewModels().getDataset("ds_const");
		if ("const".equalsIgnoreCase(operator)) {
			if ("add".equalsIgnoreCase(button)) {
				Row emptyRow = dsConst.getEmptyRow();
				dsConst.addRow(emptyRow);
				emptyRow.setValue(dsConst.nameToIndex(WfmFrmNumElemVO.PK_FRMNUMRULE), this.getFrmNumRulePk(paras));
				emptyRow.setValue(dsConst.nameToIndex("code"), "Const");
				emptyRow.setValue(dsConst.nameToIndex("name"), "³£Á¿");
				emptyRow.setValue(dsConst.nameToIndex("orderstr"), WfmServiceFacility.getFrmNumElemQry().getOrderStr(this.getFrmNumRulePk(paras)));
			}
			if ("modify".equalsIgnoreCase(button)) {
				dsConst.clear();
				WfmFrmNumElemVO frmNumElemVo = WfmServiceFacility.getFrmNumElemQry().getFrmNumElemVoByPk(this.getFrmNumElemPk(paras));
				new SuperVO2DatasetSerializer().serialize(new SuperVO[] { frmNumElemVo }, dsConst, Row.STATE_NORMAL);
			}
			dsConst.setRowSelectIndex(0);
			dsConst.setEnabled(true);
		}
	}
	public void btnOk_click(MouseEvent<ButtonComp> e) {
		LfwWidget widgetCode = AppUtil.getWidget("const");
		Dataset dsCode = widgetCode.getViewModels().getDataset("ds_const");
		Row selectedRow = dsCode.getSelectedRow();
		SuperVO[] vos = new Dataset2SuperVOSerializer<SuperVO>().serialize(dsCode, selectedRow);
		if (vos == null || vos.length == 0) {
			return;
		}
		WfmServiceFacility.getFrmNumElemBill().saveOrUpdateFrmNumElem((WfmFrmNumElemVO) vos[0]);
		new UifPlugoutCmd("const", "const_pluginout1").execute();
		AppUtil.getCntAppCtx().getCurrentWindowContext().closeView("const");
	}
	public void btnCancel_click(MouseEvent<ButtonComp> e) {
		AppUtil.getCntAppCtx().getCurrentWindowContext().closeView("const");
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
