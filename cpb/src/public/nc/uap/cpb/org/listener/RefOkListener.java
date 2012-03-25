package nc.uap.cpb.org.listener;
import java.util.ArrayList;
import java.util.List;
import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.util.CpbServiceFacility;
import nc.uap.lfw.core.comp.WebElement;
import nc.uap.lfw.core.crud.CRUDHelper;
import nc.uap.lfw.core.crud.ILfwCRUDService;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.event.MouseEvent;
import nc.uap.lfw.core.event.ctx.LfwPageContext;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.serializer.impl.Dataset2SuperVOSerializer;
import nc.uap.lfw.core.serializer.impl.SuperVO2DatasetSerializer;
import nc.uap.lfw.core.uif.listener.UifMouseListener;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.SuperVO;
/**
 * 关联数据确定操作基类 2010-8-19 下午02:08:08 limingf
 */
public abstract class RefOkListener<T extends WebElement> extends UifMouseListener<T> {
	public RefOkListener(LfwPageContext pagemeta, String widgetId) {
		super(pagemeta, widgetId);
	}
	public void onclick(MouseEvent<T> e) {
		LfwWidget pwidget = getGlobalContext().getPageMeta().getWidget(getPWidgetId());
		LfwWidget refwidget = getGlobalContext().getPageMeta().getWidget(getRefWidgetId());
		LfwWidget relatewidget = getGlobalContext().getPageMeta().getWidget(getRelateWidget());
		Dataset pds = pwidget.getViewModels().getDataset(getPDatasetId());
		Dataset relateDs = relatewidget.getViewModels().getDataset(getRelateDatasetId());
		Dataset ds = refwidget.getViewModels().getDataset(getRefDatasetId());
		Row prow = pds.getSelectedRow();
		Row[] rows = ds.getAllSelectedRows();
		//deleteExistRows(relateDs);
		if (rows == null || rows.length == 0) {
			refwidget.setVisible(false);
			return;
		}
		int size = rows.length;
		List<Row> relateRows = new ArrayList<Row>();
		for (int i = 0; i < size; i++) {
			Row row = rows[i];
			Row relateRow = relateDs.getEmptyRow();
			setRowValue(relateDs, relateRow, pds, prow, ds, row);
			if (onBeforeVOSave(relateDs, relateRow)) {
				relateRows.add(relateRow);
			}
		}
		if (relateRows != null && relateRows.size() != 0) {
			SuperVO[] pvos = new Dataset2SuperVOSerializer().serialize(relateDs, relateRows.toArray(new Row[0]));
			onVoSave(pvos);
			onAfterSave(pvos, relateDs);
		}
		refwidget.setVisible(false);
	}
	public void onAfterSave(SuperVO[] pvos, Dataset ds) {
		new SuperVO2DatasetSerializer().serialize(pvos, ds, Row.STATE_NORMAL);
	}
	/**
	 * 删除ds中已经存在的数据行
	 * 
	 * @param ds
	 */
	protected void deleteExistRows(Dataset ds) {
		if (ds.getCurrentRowData() == null) {
			return;
		}
		Row[] rows = ds.getCurrentRowData().getRows();
		if (rows == null || rows.length == 0) {
			return;
		}
		Dataset2SuperVOSerializer serializer = new Dataset2SuperVOSerializer();
		SuperVO[] pvos = serializer.serialize(ds, rows);
		try {
			CpbServiceFacility.getCpSuperVOBill().deleteSuperVOs(pvos);
		} catch (CpbBusinessException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
		for (int i = 0; i < rows.length; i++) {
			ds.getCurrentRowData().removeRow(rows[i]);
		}
	}
	/**
	 * 设置新增关联数据行的值
	 * 
	 * @param relateDs
	 * @param row
	 * @param parentds
	 * @param prow
	 * @param refds
	 * @param refrow
	 */
	public abstract void setRowValue(Dataset relateDs, Row row, Dataset parentds, Row prow, Dataset refds, Row refrow);
	/**
	 * 保存前操作，如重复值校验等
	 * 
	 * @param ds
	 * @param row
	 * @return
	 */
	public abstract boolean onBeforeVOSave(Dataset ds, Row row);
	protected void onVoSave(SuperVO[] pvos) {
		try {
			CpbServiceFacility.getCpSuperVOBill().insertSuperVOs(pvos);
		} catch (CpbBusinessException e) {
			LfwLogger.error(e.getMessage(), e);
		}
	}
	protected ILfwCRUDService<SuperVO, AggregatedValueObject> getCrudService() {
		return CRUDHelper.getCRUDService();
	}
	// 下面注释以用户关联角色为例
	/**
	 * 需要关联数据的widget(用户widget,user_widget)
	 * 
	 * @return
	 */
	public String getPWidgetId() {
		return null;
	}
	/**
	 * 需要关联数据的ds(用户ds,user_ds)
	 * 
	 * @return
	 */
	public String getPDatasetId() {
		return null;
	}
	/**
	 * 被关联数据的widget(角色widget,role_widget)
	 * 
	 * @return
	 */
	public String getRefWidgetId() {
		return null;
	}
	/**
	 * 被关联数据的ds(角色ds,role_ds)
	 * 
	 * @return
	 */
	public String getRefDatasetId() {
		return null;
	}
	/**
	 * 关联数据的widget(用户关联角色widget,user_role_widget)
	 * 
	 * @return
	 */
	public String getRelateWidget() {
		return null;
	}
	/**
	 * 关联数据的ds(用户关联角色ds,user_role_ds)
	 * 
	 * @return
	 */
	public String getRelateDatasetId() {
		return null;
	}
}
