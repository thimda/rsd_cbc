package nc.uap.wfm.taskcolmgr;

import java.util.ArrayList;
import java.util.List;

import nc.bs.framework.common.NCLocator;
import nc.uap.lfw.core.InteractionUtil;
import nc.uap.lfw.core.comp.ButtonComp;
import nc.uap.lfw.core.comp.text.TextComp;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.data.RowData;
import nc.uap.lfw.core.event.MouseEvent;
import nc.uap.lfw.core.event.ctx.LfwPageContext;
import nc.uap.lfw.core.event.listener.MouseServerListener;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.wfm.constant.WfmConstants;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.itf.IWfmTaskTabColBill;
import nc.uap.wfm.vo.WfmTaskTabColVO;

public class TaskColBtnMouserListener extends MouseServerListener<ButtonComp> {
	public static final String BtnLeft = "button_left";
	public static final String BtnRight = "button_right";
	public static final String BtnUp = "button_up";
	public static final String BtnDown = "button_down";
	public static final String BtnOk = "button_ok";
	public static final String BtnEdit = "button_edit";
	public static final String DsTaskTabColLeft = "ds_taskcolleft";
	public static final String DsTaskTabColRight = "ds_taskcolright";
	public static final String DsProDef = "ds_prodef";
	private Dataset dsLeft;
	private Dataset dsRight;
	private Dataset dsProDef;
	private LfwWidget mainWidget;
	public TaskColBtnMouserListener(LfwPageContext context, String widgetId) {
		super(context, widgetId);
	}
	@Override
	public void onclick(MouseEvent<ButtonComp> e) {
		ButtonComp button = e.getSource();
		String buttonId = button.getId();
		mainWidget = getGlobalContext().getPageMeta().getWidget(WfmConstants.WidgetMain);
		dsLeft = mainWidget.getViewModels().getDataset(DsTaskTabColLeft);
		dsRight = mainWidget.getViewModels().getDataset(DsTaskTabColRight);
		dsProDef = mainWidget.getViewModels().getDataset(DsProDef);
		if (BtnRight.equalsIgnoreCase(buttonId)) {
			this.handlerRight();
		} else if (BtnLeft.equalsIgnoreCase(buttonId)) {
			this.handlerLeft();
		} else if (BtnUp.equalsIgnoreCase(buttonId)) {
			this.handlerUp();
		} else if (BtnDown.equalsIgnoreCase(buttonId)) {
			this.hanlderDown();
		} else if (BtnOk.equalsIgnoreCase(buttonId)) {
			this.handlerOk();
		} else if (BtnEdit.equalsIgnoreCase(buttonId)) {
			this.handlerEdit();
		}
	}
	private void handlerEdit() {
		dsRight.setEnabled(true);
	}
	private void handlerOk() {
		TextComp tabCtrl = (TextComp) mainWidget.getViewComponents().getComponent("text_tabctrl");
		String tabCtrlValue = tabCtrl.getValue();
		if (tabCtrlValue == null || tabCtrlValue.length() == 0) {
			throw new LfwRuntimeException("请选中页签状态");
		}
		Row rowProDef = dsProDef.getSelectedRow();
		if (rowProDef == null) {
			throw new LfwRuntimeException("请选中一条流程定义");
		}
		String proDefPk = (String) rowProDef.getValue(dsProDef.nameToIndex("pk_prodef"));
		String proDefId = (String) rowProDef.getValue(dsProDef.nameToIndex("prodef_id"));
		Row[] rows = dsRight.getCurrentRowData().getRows();
		Row tmpRow = null;
		String str = null;
		List<WfmTaskTabColVO> list = new ArrayList<WfmTaskTabColVO>();
		WfmTaskTabColVO taskTabColVo = null;
		for (int i = 0; i < rows.length; i++) {
			tmpRow = rows[i];
			taskTabColVo = new WfmTaskTabColVO();
			str = (String) tmpRow.getValue(dsRight.nameToIndex("code"));
			taskTabColVo.setTabfield_id(str);
			str = (String) tmpRow.getValue(dsRight.nameToIndex("name"));
			taskTabColVo.setDispname(str);
			str = (String) tmpRow.getValue(dsRight.nameToIndex("pk_prodef"));
			taskTabColVo.setPk_prodef(str);
			str = (String) tmpRow.getValue(dsRight.nameToIndex("prodef_id"));
			taskTabColVo.setProdef_id(str);
			taskTabColVo.setOrderstr(String.valueOf(i + 1));
			taskTabColVo.setTabctrlvalue(tabCtrlValue);
			list.add(taskTabColVo);
		}
		IWfmTaskTabColBill taskTabColBill = NCLocator.getInstance().lookup(IWfmTaskTabColBill.class);
		try {
			taskTabColBill.deleteTaskTabCol(proDefPk, proDefId,tabCtrlValue);
		    taskTabColBill.saveTaskTabCol(list.toArray(new WfmTaskTabColVO[0]));
		} catch (WfmServiceException e) {
			throw new LfwRuntimeException(e.getMessage());
		}
		dsRight.setEnabled(false);
		InteractionUtil.showMessageDialog("保存成功");
	}
	private void hanlderDown() {
		Row row = dsRight.getSelectedRow();
		if (row == null) {
			throw new LfwRuntimeException("请选中要移动的行!");
		}
		RowData rowData = dsRight.getCurrentRowData();
		int index = rowData.getRowIndex(row);
		if (index == rowData.getRowCount() - 1) {
			return;
		}
		Row newRow = rowData.getRow(index + 1);
		rowData.swapRow(row, newRow);
	}
	private void handlerUp() {
		Row row = dsRight.getSelectedRow();
		if (row == null) {
			throw new LfwRuntimeException("请选中要移动的行!");
		}
		RowData rowData = dsRight.getCurrentRowData();
		int index = rowData.getRowIndex(row);
		if (index == 0) {
			return;
		}
		Row newRow = rowData.getRow(index - 1);
		rowData.swapRow(row, newRow);
	}
	private void handlerLeft() {
		Row rightRow = dsRight.getSelectedRow();
		if (rightRow == null) {
			return;
		}
		dsRight.removeRow(rightRow);
		String code = (String) rightRow.getValue(dsRight.nameToIndex("code"));
		String name = (String) rightRow.getValue(dsRight.nameToIndex("name"));
		String proDefPk = (String) rightRow.getValue(dsRight.nameToIndex("pk_prodef"));
		String proDefId = (String) rightRow.getValue(dsRight.nameToIndex("prodef_id"));
		Row leftRow = dsLeft.getEmptyRow();
		dsLeft.addRow(leftRow);
		leftRow.setValue(dsLeft.nameToIndex("code"), code);
		leftRow.setValue(dsLeft.nameToIndex("name"), name);
		leftRow.setValue(dsLeft.nameToIndex("pk_prodef"), proDefPk);
		leftRow.setValue(dsLeft.nameToIndex("prodef_id"), proDefId);
	}
	private void handlerRight() {
		Row leftRow = dsLeft.getSelectedRow();
		if (leftRow == null) {
			return;
		}
		dsLeft.removeRow(leftRow);
		String code = (String) leftRow.getValue(dsLeft.nameToIndex("code"));
		String name = (String) leftRow.getValue(dsLeft.nameToIndex("name"));
		String proDefPk = (String) leftRow.getValue(dsLeft.nameToIndex("pk_prodef"));
		String proDefId = (String) leftRow.getValue(dsLeft.nameToIndex("prodef_id"));
		Row rightRow = dsRight.getEmptyRow();
		dsRight.addRow(rightRow);
		rightRow.setValue(dsRight.nameToIndex("code"), code);
		rightRow.setValue(dsRight.nameToIndex("name"), name);
		rightRow.setValue(dsRight.nameToIndex("pk_prodef"), proDefPk);
		rightRow.setValue(dsRight.nameToIndex("prodef_id"), proDefId);
	}
}
