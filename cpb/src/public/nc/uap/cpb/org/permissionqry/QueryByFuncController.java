package nc.uap.cpb.org.permissionqry;

import java.util.List;
import java.util.Map;

import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.util.CpbServiceFacility;
import nc.uap.lfw.core.comp.ReferenceComp;
import nc.uap.lfw.core.ctx.AppLifeCycleContext;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.Field;
import nc.uap.lfw.core.data.PaginationInfo;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.event.DataLoadEvent;
import nc.uap.lfw.core.event.MouseEvent;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.lfw.core.page.LfwWidget;

public class QueryByFuncController {
	public static final String VIEW = "main";
	public static final String REF_TEXT = "ref_text";
	public static final String DS_RESULT = "ds_result";

	private void loadData() {
		LfwWidget main = AppLifeCycleContext.current().getWindowContext()
				.getViewContext(VIEW).getView();
		ReferenceComp ref_text = (ReferenceComp) main.getViewComponents()
				.getComponent(REF_TEXT);
		String pk_func = ref_text.getValue();
		Dataset ds = main.getViewModels().getDataset(DS_RESULT);
		if (ds.getCurrentKey() == null || "".equals(ds.getCurrentKey())) {
			ds.setCurrentKey(Dataset.MASTER_KEY);
		}
		PaginationInfo pinfo = ds.getCurrentRowSet().getPaginationInfo();
		List<Map<String, Object>> list = null;
		try {
			list = CpbServiceFacility.getCpPermissionQryService()
					.getPermissionsByFunc(new String[] { pk_func },pinfo);
		} catch (CpbBusinessException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException("查询权限出错了！");
		}
		if(ds.getRowCount()>0)
			ds.getCurrentRowSet().clear();
		if (list == null || list.size() < 1)
			return;
		for (Map<String, Object> map : list) {
			Row row = ds.getEmptyRow();
			Field[] fields = ds.getFieldSet().getFields();
			for (Field field : fields) {
				String key = field.getId();
				row.setValue(ds.nameToIndex(key), map.get(key));
			}
			ds.addRow(row);
		}
	}

	public void onOkClick(MouseEvent<?> mouseEvent) {
		loadData();
	}

	public void onDataLoad(DataLoadEvent dataLoadEvent) {
		loadData();
	}

}
