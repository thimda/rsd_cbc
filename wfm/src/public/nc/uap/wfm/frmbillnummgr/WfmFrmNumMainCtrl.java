package nc.uap.wfm.frmbillnummgr;
import java.util.Map;
import nc.bs.framework.common.NCLocator;
import nc.uap.lfw.core.InteractionUtil;
import nc.uap.lfw.core.bm.ButtonStateManager;
import nc.uap.lfw.core.cmd.CmdInvoker;
import nc.uap.lfw.core.cmd.UifDatasetAfterSelectCmd;
import nc.uap.lfw.core.cmd.UifPlugoutCmd;
import nc.uap.lfw.core.comp.MenuItem;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.data.RowData;
import nc.uap.lfw.core.event.DataLoadEvent;
import nc.uap.lfw.core.event.DatasetEvent;
import nc.uap.lfw.core.event.MouseEvent;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.model.plug.TranslatedRow;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.serializer.impl.Dataset2SuperVOSerializer;
import nc.uap.lfw.core.serializer.impl.SuperVO2DatasetSerializer;
import nc.uap.wfm.constant.WfmConstants;
import nc.uap.wfm.facility.WfmServiceFacility;
import nc.uap.wfm.itf.IWfmFrmNumElemBill;
import nc.uap.wfm.itf.IWfmFrmNumElemQry;
import nc.uap.wfm.utils.AppUtil;
import nc.uap.wfm.vo.WfmFrmNumElemVO;
import nc.uap.wfm.vo.WfmFrmNumRuleVO;
import nc.vo.pub.SuperVO;
public class WfmFrmNumMainCtrl {
	public static final String PK_FLOWTYPE = "pk_flowtype";
	public void wfmFrmNumOnload(DataLoadEvent se) {
		String flowtypePk = (String) AppUtil.getAppAttr("flowtypepk");
		WfmFrmNumRuleVO frmNumRuleVo = WfmServiceFacility.getFrmNumRuleQry().getFrmNumRulrVoByFlowTypePk(flowtypePk);
		if (frmNumRuleVo == null) {
			return;
		}
		LfwWidget widgetMain = AppUtil.getWidget("main");
		Dataset dsDataset = widgetMain.getViewModels().getDataset("ds_rule");
		new SuperVO2DatasetSerializer().serialize(new WfmFrmNumRuleVO[] { frmNumRuleVo }, dsDataset, Row.STATE_NORMAL);
		dsDataset.setRowSelectIndex(0);
		Dataset dsElem = widgetMain.getViewModels().getDataset("ds_elem");
		WfmFrmNumElemVO[] vos = WfmServiceFacility.getFrmNumElemQry().getFrmNumElemVOByFrmNumRulePk(frmNumRuleVo.getPk_frmnumrule());
		new SuperVO2DatasetSerializer().serialize(vos, dsElem, Row.STATE_NORMAL);
	}
	public void dateElem(MouseEvent<MenuItem> e) {
		AppUtil.addAppAttr("operator", "date");
		AppUtil.addAppAttr("bttton", "add");
		new UifPlugoutCmd("main", "main_pluginout1").execute();
		AppUtil.getCntAppCtx().getCurrentWindowContext().popView("date", "600", "430", "日期");
	}
	public void codeElem(MouseEvent<MenuItem> e) {
		AppUtil.addAppAttr("operator", "code");
		AppUtil.addAppAttr("bttton", "add");
		new UifPlugoutCmd("main", "main_pluginout1").execute();
		AppUtil.getCntAppCtx().getCurrentWindowContext().popView("code", "600", "430", "流水号");
	}
	public void constElem(MouseEvent<MenuItem> e) {
		AppUtil.addAppAttr("operator", "const");
		AppUtil.addAppAttr("bttton", "add");
		new UifPlugoutCmd("main", "main_pluginout1").execute();
		AppUtil.getCntAppCtx().getCurrentWindowContext().popView("const", "600", "430", "变量");
	}
	public void modifyElem(MouseEvent<MenuItem> e) {
		LfwWidget widgetMain = AppUtil.getWidget("main");
		Dataset dsElem = widgetMain.getViewModels().getDataset(WfmConstants.DsElem);
		Row rowElem = dsElem.getSelectedRow();
		if (rowElem == null) {
			return;
		}
		String elemCode = (String) rowElem.getValue(dsElem.nameToIndex("code"));
		if ("Const".equalsIgnoreCase(elemCode)) {
			AppUtil.addAppAttr("operator", "const");
			AppUtil.addAppAttr("bttton", "modify");
			new UifPlugoutCmd("main", "main_pluginout1").execute();
			AppUtil.getCntAppCtx().getCurrentWindowContext().popView("const", "600", "430", "修改常量");
		} else if ("SerialCode".equalsIgnoreCase(elemCode)) {
			AppUtil.addAppAttr("operator", "code");
			AppUtil.addAppAttr("bttton", "modify");
			new UifPlugoutCmd("main", "main_pluginout1").execute();
			AppUtil.getCntAppCtx().getCurrentWindowContext().popView("code", "600", "430", "修改流水号");
		} else if ("CurrentDate".equalsIgnoreCase(elemCode)) {
			AppUtil.addAppAttr("operator", "date");
			AppUtil.addAppAttr("bttton", "modify");
			new UifPlugoutCmd("main", "main_pluginout1").execute();
			AppUtil.getCntAppCtx().getCurrentWindowContext().popView("date", "600", "430", "修改流水号");
		}
	}
	public void deleteElem(MouseEvent<MenuItem> e) {
		InteractionUtil.showConfirmDialog("确认删除？", "确认要删除这条数据吗?");
		if (InteractionUtil.getConfirmDialogResult().booleanValue()) {
			LfwWidget widgetMain = AppUtil.getWidget("main");
			Dataset dsElem = widgetMain.getViewModels().getDataset("ds_elem");
			Row selectedRow = dsElem.getSelectedRow();
			String frmNumElemPk = (String) selectedRow.getValue(dsElem.nameToIndex(WfmFrmNumElemVO.PK_FRMNUMELEM));
			WfmServiceFacility.getFrmNumElemBill().deleteFrmNumElemByPk(frmNumElemPk);
			dsElem.removeRow(selectedRow);
		}
	}
	public void downElem(MouseEvent<MenuItem> e) {
		LfwWidget widgetMain = AppUtil.getWidget("main");
		Dataset dsElem = widgetMain.getViewModels().getDataset(WfmConstants.DsElem);
		Row selectedRow = dsElem.getSelectedRow();
		if (selectedRow == null) {
			throw new LfwRuntimeException("请选中要移动的行!");
		}
		RowData rowData = dsElem.getCurrentRowData();
		int index = rowData.getRowIndex(selectedRow);
		if (index == 0) {
			return;
		}
		Row newRow = rowData.getRow(index - 1);
		rowData.swapRow(selectedRow, newRow);
		String onePk = (String) selectedRow.getValue(dsElem.nameToIndex("pk_frmnumelem"));
		String twoPk = (String) newRow.getValue(dsElem.nameToIndex("pk_frmnumelem"));
		WfmFrmNumElemVO oneVo = NCLocator.getInstance().lookup(IWfmFrmNumElemQry.class).getFrmNumElemVoByPk(onePk);
		WfmFrmNumElemVO twoVo = NCLocator.getInstance().lookup(IWfmFrmNumElemQry.class).getFrmNumElemVoByPk(twoPk);
		String oneStr = oneVo.getOrderstr();
		String twoStr = twoVo.getOrderstr();
		oneVo.setOrderstr(twoStr);
		twoVo.setOrderstr(oneStr);
		NCLocator.getInstance().lookup(IWfmFrmNumElemBill.class).updateFrmNumElem(oneVo);
		NCLocator.getInstance().lookup(IWfmFrmNumElemBill.class).updateFrmNumElem(twoVo);
	}
	public void upElem(MouseEvent<MenuItem> e) {
		LfwWidget widgetMain = AppUtil.getWidget("main");
		Dataset dsElem = widgetMain.getViewModels().getDataset(WfmConstants.DsElem);
		Row selectedRow = dsElem.getSelectedRow();
		if (selectedRow == null) {
			throw new LfwRuntimeException("请选中要移动的行!");
		}
		RowData rowData = dsElem.getCurrentRowData();
		int index = rowData.getRowIndex(selectedRow);
		if (index == rowData.getRowCount() - 1) {
			return;
		}
		Row newRow = rowData.getRow(index + 1);
		rowData.swapRow(selectedRow, newRow);
		String onePk = (String) selectedRow.getValue(dsElem.nameToIndex("pk_frmnumelem"));
		String twoPk = (String) newRow.getValue(dsElem.nameToIndex("pk_frmnumelem"));
		WfmFrmNumElemVO oneVo = NCLocator.getInstance().lookup(IWfmFrmNumElemQry.class).getFrmNumElemVoByPk(onePk);
		WfmFrmNumElemVO twoVo = NCLocator.getInstance().lookup(IWfmFrmNumElemQry.class).getFrmNumElemVoByPk(twoPk);
		String oneStr = oneVo.getOrderstr();
		String twoStr = twoVo.getOrderstr();
		oneVo.setOrderstr(twoStr);
		twoVo.setOrderstr(oneStr);
		NCLocator.getInstance().lookup(IWfmFrmNumElemBill.class).updateFrmNumElem(oneVo);
		NCLocator.getInstance().lookup(IWfmFrmNumElemBill.class).updateFrmNumElem(twoVo);
	}
	public void addRule(MouseEvent<MenuItem> e) {
		LfwWidget widgetMain = AppUtil.getWidget("main");
		Dataset dsRule = widgetMain.getViewModels().getDataset("ds_rule");
		Row selectedRow = dsRule.getSelectedRow();
		if (selectedRow != null) {
			throw new LfwRuntimeException("一个业务类型只能对应一个编码规则");
		}
		Row emptyRow = dsRule.getEmptyRow();
		dsRule.addRow(emptyRow);
		dsRule.setEnabled(true);
		emptyRow.setValue(dsRule.nameToIndex(PK_FLOWTYPE), AppUtil.getAppAttr("flowtypepk"));
		dsRule.setRowSelectIndex(0);
		ButtonStateManager.updateButtons();
	}
	public void modifyRule(MouseEvent<MenuItem> e) {
		LfwWidget widgetMain = AppUtil.getWidget("main");
		Dataset dsRule = widgetMain.getViewModels().getDataset("ds_rule");
		dsRule.setEnabled(true);
		ButtonStateManager.updateButtons();
	}
	public void deleteRule(MouseEvent<MenuItem> e) {
		LfwWidget widgetMain = AppUtil.getWidget("main");
		Dataset dsRule = widgetMain.getViewModels().getDataset("ds_rule");
		Row selectedRow = dsRule.getSelectedRow();
		if (selectedRow == null) {
			throw new LfwRuntimeException("请选中要删除的行数据");
		}
		String frmNumRulePk = (String) selectedRow.getValue(dsRule.nameToIndex(WfmFrmNumRuleVO.PK_FRMNUMRULE));
		WfmServiceFacility.getFrmNumRuleBill().deleteFrmNumRuleByPk(frmNumRulePk);
		dsRule.removeRow(selectedRow);
		ButtonStateManager.updateButtons();
	}
	public void saveRule(MouseEvent<MenuItem> e) {
		LfwWidget widgetMain = AppUtil.getWidget("main");
		Dataset dsRule = widgetMain.getViewModels().getDataset("ds_rule");
		Row selectedRow = dsRule.getSelectedRow();
		SuperVO[] vos = new Dataset2SuperVOSerializer<SuperVO>().serialize(dsRule, selectedRow);
		if (vos == null || vos.length == 0) {
			return;
		}
		WfmServiceFacility.getFrmNumRuleBill().saveOrUpdateFrmNumRule((WfmFrmNumRuleVO) vos[0]);
		dsRule.setEnabled(false);
		ButtonStateManager.updateButtons();
	}
	public void cancelRule(MouseEvent<MenuItem> e) {
		LfwWidget widgetMain = AppUtil.getWidget("main");
		Dataset dsRule = widgetMain.getViewModels().getDataset("ds_rule");
		dsRule.setEnabled(false);
		ButtonStateManager.updateButtons();
	}
	public void pluginmain_pluginin1(Map<String, Object> paras) {
		LfwWidget widget = AppUtil.getWidget("main");
		Dataset dsElem = widget.getViewModels().getDataset("ds_elem");
		Row row = dsElem.getSelectedRow();
		String button = (String) AppUtil.getAppAttr("bttton");
		if ("add".equalsIgnoreCase(button)) {
			row = dsElem.getEmptyRow();
			dsElem.addRow(row);
			dsElem.getRowIndex(row);
		}
		this.setValues(row, dsElem, paras);
	}
	private void setValues(Row row, Dataset ds, Map<String, Object> map) {
		TranslatedRow r = (TranslatedRow) map.get("selectedrow");
		String[] keys = r.getKeys();
		for (String key : keys) {
			row.setValue(ds.nameToIndex(key), r.getValue(key));
		}
	}
	public void dsRule_onAfterRowSelect(DatasetEvent datasetEvent) {
		ButtonStateManager.updateButtons();
	}
	public void dsElem_onAfterRowSelect(DatasetEvent datasetEvent) {
		Dataset ds = datasetEvent.getSource();
		CmdInvoker.invoke(new UifDatasetAfterSelectCmd(ds.getId()));
	}
}
