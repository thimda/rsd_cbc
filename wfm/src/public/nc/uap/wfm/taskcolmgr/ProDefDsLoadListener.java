package nc.uap.wfm.taskcolmgr;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import nc.bs.framework.common.NCLocator;
import nc.uap.lfw.core.comp.text.TextComp;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.event.DataLoadEvent;
import nc.uap.lfw.core.event.DatasetEvent;
import nc.uap.lfw.core.event.ctx.LfwPageContext;
import nc.uap.lfw.core.event.deft.DefaultDatasetServerListener;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.wfm.constant.WfmConstants;
import nc.uap.wfm.contanier.ProDefsContainer;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.itf.IWfmProDefQry;
import nc.uap.wfm.itf.IWfmTaskTabColQry;
import nc.uap.wfm.model.ProDef;
import nc.uap.wfm.vo.WfmTaskVO;
import nc.uap.wfm.vo.WfmProdefVO;
import nc.uap.wfm.vo.WfmTaskTabColVO;
public class ProDefDsLoadListener extends DefaultDatasetServerListener {
	public ProDefDsLoadListener(LfwPageContext pagemeta, String widgetId) {
		super(pagemeta, widgetId);
	}
	@Override public void onDataLoad(DataLoadEvent se) {
		// todo
		WfmProdefVO[] vos = null;
		try {
			vos = NCLocator.getInstance().lookup(IWfmProDefQry.class).getAllProDef();
			if (vos == null || vos.length == 0) {
				return;
			}
			Row emptyRow = null;
			Dataset dataset = se.getSource();
			dataset.setCurrentKey(Dataset.MASTER_KEY);
			ProDef proDef = null;
			for (int i = 0; i < vos.length; i++) {
				proDef = ProDefsContainer.getProDef(vos[i]);
				emptyRow = dataset.getEmptyRow();
				emptyRow.setValue(dataset.nameToIndex("pk_prodef"), proDef.getPk_prodef());
				emptyRow.setValue(dataset.nameToIndex("prodef_id"), proDef.getId());
				emptyRow.setValue(dataset.nameToIndex("name"), proDef.getName());
				dataset.addRow(emptyRow);
			}
		} catch (WfmServiceException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
	}
	@Override public void onAfterRowSelect(DatasetEvent e) {
		LfwWidget widgetMain = this.getGlobalContext().getWidgetContext(WfmConstants.WidgetMain).getWidget();
		TextComp tabCtrl = (TextComp) widgetMain.getViewComponents().getComponent("text_tabctrl");
		tabCtrl.setVisible(true);
		String tabCtrlValue = tabCtrl.getValue();
		if (tabCtrlValue == null || tabCtrlValue.length() == 0) {
			return;
		}
		Dataset dsProDef = widgetMain.getViewModels().getDataset("ds_prodef");
		Row rowProDef = dsProDef.getSelectedRow();
		if (rowProDef == null) {
			throw new LfwRuntimeException("请选中一个流程定义");
		}
		String proDefPk = (String) rowProDef.getValue(dsProDef.nameToIndex("pk_prodef"));
		String proDefId = (String) rowProDef.getValue(dsProDef.nameToIndex("prodef_id"));
		Dataset dsLeft = widgetMain.getViewModels().getDataset("ds_taskcolleft");
		dsLeft.clear();
		dsLeft.setCurrentKey(Dataset.MASTER_KEY);
		WfmTaskTabColVO[] taskTabColVos = null;
		try {
			taskTabColVos = NCLocator.getInstance().lookup(IWfmTaskTabColQry.class).getTaskTabColVos(proDefPk, proDefId, tabCtrlValue);
		} catch (WfmServiceException e1) {
			throw new LfwRuntimeException(e1.getMessage());
		}
		Map<String, WfmTaskTabColVO> map = null;
		if (taskTabColVos == null || taskTabColVos.length == 0) {
			map = null;
		} else {
			map = new HashMap<String, WfmTaskTabColVO>();
			WfmTaskTabColVO tmpTaskTabColVo = null;
			for (int i = 0; i < taskTabColVos.length; i++) {
				tmpTaskTabColVo = taskTabColVos[i];
				map.put(tmpTaskTabColVo.getTabfield_id(), tmpTaskTabColVo);
			}
		}
		Field[] fields = WfmTaskVO.class.getDeclaredFields();
		Row tmpRow = null;
		Field tmpField = null;
		for (int i = 0; i < fields.length; i++) {
			tmpField = fields[i];
			tmpRow = dsLeft.getEmptyRow();
			if (map != null && map.get(tmpField.getName()) != null) {
				continue;
			}
			tmpRow.setValue(dsLeft.nameToIndex("code"), tmpField.getName());
			tmpRow.setValue(dsLeft.nameToIndex("name"), tmpField.getName());
			tmpRow.setValue(dsLeft.nameToIndex("pk_prodef"), proDefPk);
			tmpRow.setValue(dsLeft.nameToIndex("prodef_id"), proDefId);
			dsLeft.addRow(tmpRow);
		}
		Dataset dsRigth = widgetMain.getViewModels().getDataset("ds_taskcolright");
		dsRigth.clear();
		dsRigth.setCurrentKey(Dataset.MASTER_KEY);
		if (taskTabColVos == null || taskTabColVos.length == 0) {
			return;
		}
		WfmTaskTabColVO tmpTaskTabColVo = null;
		for (int i = 0; i < taskTabColVos.length; i++) {
			tmpTaskTabColVo = taskTabColVos[i];
			tmpRow = dsRigth.getEmptyRow();
			tmpRow.setValue(dsRigth.nameToIndex("code"), tmpTaskTabColVo.getTabfield_id());
			tmpRow.setValue(dsRigth.nameToIndex("name"), tmpTaskTabColVo.getDispname());
			tmpRow.setValue(dsRigth.nameToIndex("pk_prodef"), tmpTaskTabColVo.getPk_prodef());
			tmpRow.setValue(dsRigth.nameToIndex("prodef_id"), tmpTaskTabColVo.getProdef_id());
			dsRigth.addRow(tmpRow);
		}
	}
}
