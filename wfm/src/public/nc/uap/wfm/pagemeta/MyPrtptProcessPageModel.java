package nc.uap.wfm.pagemeta;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import nc.bs.framework.common.NCLocator;
import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.common.EditorTypeConst;
import nc.uap.lfw.core.common.StringDataTypeConst;
import nc.uap.lfw.core.comp.FormComp;
import nc.uap.lfw.core.comp.FormElement;
import nc.uap.lfw.core.comp.GridColumn;
import nc.uap.lfw.core.comp.GridComp;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.Field;
import nc.uap.lfw.core.data.FieldSet;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.lfw.core.model.PageModel;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.page.ViewComponents;
import nc.uap.wfm.constant.WfmConstants;
import nc.uap.wfm.contanier.ProDefsContainer;
import nc.uap.wfm.engine.IProDefExtHandler;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.itf.IWfmTaskTabColQry;
import nc.uap.wfm.model.ProDef;
import nc.uap.wfm.utils.WfmClassUtil;
import nc.uap.wfm.vo.WfmTaskTabColVO;
public class MyPrtptProcessPageModel extends PageModel {
	protected void initPageMetaStruct() {
		String proDefPk = LfwRuntimeEnvironment.getWebContext().getParameter(WfmConstants.ProDefPk);
		getClientSession().setAttribute(WfmConstants.ProDefPk, proDefPk, true);
		ProDef proDef =  ProDefsContainer.getByProDefPkAndId(proDefPk, "");
		String serverClass = "nc.portal.pwfm.engine.DefaultProDefExt";
		IProDefExtHandler handler = (IProDefExtHandler) WfmClassUtil.loadClass(serverClass);
		this.handlerExtAttr(handler, proDef);
		this.initQryWidget(handler, proDef);
		this.doOther();
	}
	protected void doOther() {};
	private void initQryWidget(IProDefExtHandler handler, ProDef proDef) {
		LfwWidget widget = this.getPageMeta().getWidget(WfmConstants.WidgetQuery);
		Dataset dsTaskAttr = widget.getViewModels().getDataset("ds_taskattr");
		FormComp frmTaskAttr = (FormComp) widget.getViewComponents().getComponent("frm_taskattr");
		FieldSet fieldSet = new FieldSet();
		dsTaskAttr.setFieldSet(fieldSet);
		this.addTaskStartDate(fieldSet, frmTaskAttr);
		this.addTaskEndDate(fieldSet, frmTaskAttr);
		this.addExtAttr(handler, proDef, fieldSet, frmTaskAttr);
	}
	private void addExtAttr(IProDefExtHandler handler, ProDef proDef, FieldSet fieldSet, FormComp frmTaskAttr) {
		if (handler == null) {
			return;
		}
		Map<String, String> map = handler.getQryTaskAttr(proDef, "");
		Set<String> keys = map.keySet();
		Iterator<String> iter = keys.iterator();
		String key = null;
		String value = null;
		Field startField = null;
		FormElement element = null;
		while (iter.hasNext()) {
			key = iter.next();
			value = map.get(key);
			startField = new Field();
			startField.setId(key);
			startField.setText(value);
			startField.setDataType(StringDataTypeConst.STRING);
			fieldSet.addField(startField);
			element = new FormElement();
			element.setId(key);
			element.setText(value);
			element.setEditorType(EditorTypeConst.STRINGTEXT);
			element.setDataType(StringDataTypeConst.STRING);
			element.setField(key);
			frmTaskAttr.addElement(element);
		}
	}
	private void addTaskStartDate(FieldSet fieldSet, FormComp frmTaskAttr) {
		String startDate = "startdate";
		Field startField = new Field();
		startField.setId(startDate);
		startField.setText("任务开始日期");
		startField.setDataType(StringDataTypeConst.STRING);
		fieldSet.addField(startField);
		FormElement element = new FormElement();
		element.setId(startDate);
		element.setText("任务开始日期");
		element.setEditorType(EditorTypeConst.DATETEXT);
		element.setDataType(StringDataTypeConst.STRING);
		element.setField(startDate);
		frmTaskAttr.addElement(element);
	}
	private void addTaskEndDate(FieldSet fieldSet, FormComp frmTaskAttr) {
		String endDate = "enddate";
		Field startField = new Field();
		startField.setId(endDate);
		startField.setText("任务结束日期");
		startField.setDataType(StringDataTypeConst.STRING);
		fieldSet.addField(startField);
		FormElement element = new FormElement();
		element.setId(endDate);
		element.setText("任务结束日期");
		element.setEditorType(EditorTypeConst.DATETEXT);
		element.setDataType(StringDataTypeConst.STRING);
		element.setField(endDate);
		frmTaskAttr.addElement(element);
	}
	public void handlerExtAttr(IProDefExtHandler handler, ProDef proDef) {
		if (proDef == null) {
			return;
		}
		LfwWidget mainWidget = this.getPageMeta().getWidget(WfmConstants.WidgetMain);
		if (handler == null) {
			return;
		}
		ViewComponents vcs = mainWidget.getViewComponents();
		String proDefPk = proDef.getPk_prodef();
		String proDefId = proDef.getId();
		String str[][] = { { "undne", "grid_undnetask" }, { "cmplt", "grid_cmplttask" }, { "ended", "grid_endedtask" }, { "sended", "grid_sendtask" } };
		WfmTaskTabColVO[] taskTabColVos = null;
		for (int i = 0; i < str.length; i++) {
			try {
				taskTabColVos = NCLocator.getInstance().lookup(IWfmTaskTabColQry.class).getTaskTabColVos(proDefPk, proDefId, str[i][0]);
			} catch (WfmServiceException e) {
				LfwLogger.error(e.getMessage(),e);
			}
			Map<String, WfmTaskTabColVO> map = null;
			if (taskTabColVos == null || taskTabColVos.length == 0) {
				map = null;
			} else {
				map = new HashMap<String, WfmTaskTabColVO>();
				WfmTaskTabColVO tmpTaskTabColVo = null;
				for (int j = 0; j < taskTabColVos.length; j++) {
					tmpTaskTabColVo = taskTabColVos[i];
					map.put(tmpTaskTabColVo.getTabfield_id(), tmpTaskTabColVo);
				}
			}
			GridComp grid = (GridComp) vcs.getComponent(str[i][1]);
			GridColumn column = null;
			if (map == null) {
				return;
			}
			String key = null;
			WfmTaskTabColVO tmpTaskTabColVo = null;
			grid.getColumnList().clear();
			for (Iterator<String> iter = map.keySet().iterator(); iter.hasNext();) {
				key = iter.next();
				tmpTaskTabColVo = map.get(key);
				column = new GridColumn();
				column.setId(key);
				column.setAutoExpand(true);
				column.setText(tmpTaskTabColVo.getDispname());
				column.setVisible(true);
				column.setWidth(100);
				column.setField(key);
				column.setEditorType("StringText");
				if (grid != null) {
					grid.insertColumn(0, column);
				}
			}
		}
	}
}
