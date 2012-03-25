package nc.uap.cpb.org.user;
import java.util.ArrayList;
import java.util.List;
import nc.bs.dao.DAOException;
import nc.bs.logging.Logger;
import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.listener.RefOkListener;
import nc.uap.cpb.org.util.CpbServiceFacility;
import nc.uap.cpb.org.vos.CpUserRoleVO;
import nc.uap.cpb.org.vos.CpUserVO;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.lfw.core.comp.WebElement;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.event.MouseEvent;
import nc.uap.lfw.core.event.ctx.LfwPageContext;
import nc.uap.lfw.core.exception.LfwBusinessException;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.serializer.impl.Dataset2SuperVOSerializer;
import nc.uap.lfw.core.serializer.impl.SuperVO2DatasetSerializer;
import nc.vo.pub.SuperVO;
/**
 * 2010-8-19 下午04:48:12 limingf
 */
public class UserRefOkListener<T extends WebElement> extends RefOkListener<T> {
	@Override public void onclick(MouseEvent<T> e) {
		LfwWidget pwidget = getGlobalContext().getPageMeta().getWidget(getPWidgetId());
		LfwWidget refwidget = getGlobalContext().getPageMeta().getWidget(getRefWidgetId());
		LfwWidget relatewidget = getGlobalContext().getPageMeta().getWidget(getRelateWidget());
		Dataset pds = pwidget.getViewModels().getDataset(getPDatasetId());
		Dataset ds = refwidget.getViewModels().getDataset(getRefDatasetId());
		Row prow = pds.getSelectedRow();
		// 得到选中的所有行
		Row[] rows = ds.getAllSelectedRows();
		if (rows == null)
			return;
		Dataset relateDs = relatewidget.getViewModels().getDataset(getRelateDatasetId());
		// 原来关联角色信息
		Row[] originalRow = null;
		if (relateDs.getCurrentRowData() != null) {
			originalRow = relateDs.getCurrentRowData().getRows();
		}
		List<Row> relateRows = new ArrayList<Row>();
		for (int i = 0; i < rows.length; i++) {
			Row newRow = rows[i];
			String pk_role = (String) newRow.getValue(ds.nameToIndex("pk_role"));
			if (originalRow == null || originalRow.length == 0)
				relateRows.add(newRow);
			else {
				for (int j = 0; j < originalRow.length; j++) {
					Row oriRow = originalRow[j];
					String rolePk = (String) oriRow.getValue(relateDs.nameToIndex("pk_role"));
					if (pk_role.equals(rolePk))
						break;
					if (j == originalRow.length - 1)
						relateRows.add(newRow);
				}
			}
		}
		if (relateRows.size() == 0) {
			refwidget.setVisible(false);
			return;
		}
		int size = relateRows.size();
		List<Row> relateUserRows = new ArrayList<Row>();
		relateDs.setCurrentKey(Dataset.MASTER_KEY);
		for (int i = 0; i < size; i++) {
			Row row = relateRows.get(i);
			Row relateRow = relateDs.getEmptyRow();
			setRowValue(relateDs, relateRow, pds, prow, ds, row);
			relateUserRows.add(relateRow);
			if (relateDs.getCurrentKey() == null || "".equals(relateDs.getCurrentKey()))
				relateDs.setCurrentKey(Dataset.MASTER_KEY);
		}
		Dataset2SuperVOSerializer serializer = new Dataset2SuperVOSerializer();
		SuperVO[] pvos = serializer.serialize(relateDs, relateUserRows.toArray(new Row[0]));
		onVoSave(pvos);
		new SuperVO2DatasetSerializer().serialize(pvos, relateDs, Row.STATE_NORMAL);
		// 关闭对话框
		refwidget.setVisible(false);
	}
	protected void deleteExistRows(Dataset ds) {
		// TODO Auto-generated method stub
		LfwWidget pwidget = getGlobalContext().getPageMeta().getWidget(getPWidgetId());
		Dataset pds = pwidget.getViewModels().getDataset(getPDatasetId());
		Row prow = pds.getSelectedRow();
		String pk_user = (String) prow.getValue(pds.nameToIndex("pk_user"));
		PtBaseDAO bao = new PtBaseDAO();
		try {
			bao.deleteByClause(CpUserRoleVO.class, "pk_user = '" + pk_user + "'");
		} catch (DAOException e) {
			Logger.error(e.getMessage(), e);
		}
		String currentKey = ds.getCurrentKey();
		ds.clear();
		ds.setCurrentKey(currentKey);
	}
	public UserRefOkListener(LfwPageContext pagemeta, String widgetId) {
		super(pagemeta, widgetId);
	}
	/**
	 * 保存前重复校验
	 */
	@Override public boolean onBeforeVOSave(Dataset ds, Row row) {
		String pk_user = (String) row.getValue(ds.nameToIndex("pk_user"));
		String pk_role = (String) row.getValue(ds.nameToIndex("pk_role"));
		CpUserRoleVO vo = new CpUserRoleVO();
		vo.setPk_user(pk_user);
		vo.setPk_role(pk_role);
		SuperVO[] vos = null;
		try {
			vos = getCrudService().queryVOs(vo, null, null);
		} catch (LfwBusinessException e) {
			LfwLogger.error(e.getMessage(), e);
			return false;
		}
		if (vos == null || vos.length < 1)
			return true;
		return false;
	}
	protected void onVoSave(SuperVO[] pvos) {
		CpUserRoleVO[] vos = new CpUserRoleVO[pvos.length];
		for (int i = 0; i < pvos.length; i++) {
			vos[i] = (CpUserRoleVO) pvos[i];
			vos[i].setDr(0);
		}
		try {
			CpbServiceFacility.getCpUserRoleBill().addPtRoleUserVOS(vos);
		} catch (CpbBusinessException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException("关联角色出错!");
		}
	}
	@Override public void setRowValue(Dataset relateDs, Row row, Dataset parentds, Row prow, Dataset refds, Row refrow) {
		row.setValue(relateDs.nameToIndex("pk_user"), prow.getValue(parentds.nameToIndex(CpUserVO.CUSERID)));
		row.setValue(relateDs.nameToIndex("pk_role"), refrow.getValue(refds.nameToIndex("pk_role")));
	}
	public String getRefWidgetId() {
		return UserMgrConstants.Widget_Rela;
	}
	public String getRefDatasetId() {
		return UserMgrConstants.Ds_Role;
	}
	public String getPWidgetId() {
		return UserMgrConstants.Widget_Main;
	}
	public String getPDatasetId() {
		return UserMgrConstants.Ds_User;
	}
	public String getRelateWidget() {
		return getPWidgetId();
	}
	public String getRelateDatasetId() {
		return UserMgrConstants.Ds_UserRole;
	}
}
