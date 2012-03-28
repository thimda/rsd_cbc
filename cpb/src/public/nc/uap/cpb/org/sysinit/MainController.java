package nc.uap.cpb.org.sysinit;

import java.util.List;
import java.util.Map;

import nc.bs.logging.Logger;
import nc.jdbc.framework.JdbcSession;
import nc.jdbc.framework.PersistenceManager;
import nc.jdbc.framework.exception.DbException;
import nc.jdbc.framework.processor.BeanListProcessor;
import nc.jdbc.framework.processor.ResultSetProcessor;
import nc.uap.cpb.org.vos.CpSysinitVO;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.lfw.core.bm.ButtonStateManager;
import nc.uap.lfw.core.cmd.CmdInvoker;
import nc.uap.lfw.core.cmd.UifCancelCmd;
import nc.uap.lfw.core.combodata.CombItem;
import nc.uap.lfw.core.combodata.ComboData;
import nc.uap.lfw.core.ctrl.IController;
import nc.uap.lfw.core.ctx.AppLifeCycleContext;
import nc.uap.lfw.core.ctx.WindowContext;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.event.DataLoadEvent;
import nc.uap.lfw.core.event.DatasetCellEvent;
import nc.uap.lfw.core.event.DatasetEvent;
import nc.uap.lfw.core.event.DialogEvent;
import nc.uap.lfw.core.event.MouseEvent;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.lfw.core.serializer.impl.Dataset2SuperVOSerializer;
import nc.uap.lfw.core.serializer.impl.SuperVO2DatasetSerializer;
import nc.uap.wfm.utils.AppUtil;
import nc.vo.pub.SuperVO;

/**
 * @author chenwl
 * @param<T>
 */
public class MainController<T> implements IController {
	private static final long serialVersionUID = 1L;

	/**
	 * ��õ�ǰwindow������
	 * 
	 * @return
	 */
	private WindowContext getCurrentWinCtx() {
		return AppLifeCycleContext.current().getApplicationContext().getCurrentWindowContext();
	}

	/**
	 * ��õ�ǰview��dataset
	 * 
	 * @param dsid
	 * @return
	 */
	public Dataset getDataset(String dsid) {
		return getCurrentWinCtx().getViewContext("main").getView().getViewModels().getDataset(dsid);
	}
	
	/**
	 * ҳ���ʼ��ʱ���ð�ť״̬
	 * @param dialogEvent
	 */
	public void beforeShow(DialogEvent dialogEvent) {
		ButtonStateManager.updateButtons();
	}
	
	/**
	 * ҳ���ʼ�����ز�����
	 * 
	 * @param dataLoadEvent
	 */
	public void onDataLoad_cp_systinit_ds(DataLoadEvent dataLoadEvent) {
//		Dataset ds = dataLoadEvent.getSource();
//		StringBuffer sql = new StringBuffer();
//		sql.append("select * from cp_sysinit s,cp_sysinittemp st,cp_appscategory ac ")
//			.append("where s.sysinit = st.pk_sysinittemp and s.dr=0 and st.dr=0 and ac.id = st.domainflag ");
//		loadSysinit(ds, sql.toString());
	}

	/**
	 * �����������ز�����
	 * 
	 * @param ds
	 * @param sql
	 */
	private void loadSysinit(Dataset ds, String sql) {
		try {
			PersistenceManager pm = null;
			pm = PersistenceManager.getInstance();
			JdbcSession ses = pm.getJdbcSession();
			ResultSetProcessor rp = new BeanListProcessor(CpSysinitVO.class);
			Object list = ses.executeQuery(sql, null, new BeanListProcessor(CpSysinitVO.class));
			ses.closeAll();
			SuperVO[] vos = (SuperVO[]) ((List) list).toArray(new CpSysinitVO[0]);
			new SuperVO2DatasetSerializer().serialize(vos, ds, Row.STATE_NORMAL);
			if (ds.getRowCount() > 0) {
				ds.setRowSelectIndex(0);
			}
		} catch (DbException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
	}

	/**
	 * ����˵��޸İ�ť
	 * 
	 * @param mouseEvent
	 */
	public void onMenuEdit(MouseEvent<T> mouseEvent) {
		Dataset ds = getDataset("cp_sysinit_ds");
		ds.setEnabled(true);
		ButtonStateManager.updateButtons();
	}

	/**
	 * ����˵����水ť
	 * 
	 * @param mouseEvent
	 */
	public void onMenuSave(MouseEvent<T> mouseEvent) {
		Dataset ds = getDataset("cp_sysinit_ds");
		Dataset2SuperVOSerializer<SuperVO> ser = new Dataset2SuperVOSerializer<SuperVO>();
		SuperVO[] vos = ser.serialize(ds, new Row[] {});
		if (vos == null)
			return;
		try {
			new PtBaseDAO().updateVOArray(vos);
		} catch (Exception e) {
			Logger.error(e, e);
			throw new LfwRuntimeException(e.getMessage());
		}

		// ���������, ��������ҳ������
		SuperVO2DatasetSerializer s = new SuperVO2DatasetSerializer();
		s.update(vos, ds);
		ds.setEnabled(false);
		ButtonStateManager.updateButtons();
	}

	/**
	 * ����˵�ȡ����ť
	 * 
	 * @param mouseEvent
	 */
	public void onMenuCancel(MouseEvent<T> mouseEvent) {
		Dataset ds = getDataset("cp_sysinit_ds");
		CmdInvoker.invoke(new UifCancelCmd(ds.getId()));
	}

	/**
	 * Ϊ����ֵ�����������
	 * 
	 * @param datasetEvent
	 */
	public void onAfterRowSelect_cp_sysinit_ds(DatasetEvent datasetEvent) {
		ComboData comboData = getCurrentWinCtx().getViewContext("main").getView().getViewModels().getComboData("combo_sysinit_value");
		comboData.removeAllComboItems();

		Dataset ds = getDataset("cp_sysinit_ds");
		String valuelists = (String) ds.getSelectedRow().getValue(ds.nameToIndex("valuelist"));
		if (valuelists != null && !"".equals(valuelists.trim())) {
			if (valuelists.contains(",")) {
				fillData(comboData, valuelists, ",");
			} else if (valuelists.contains("/")) {
				fillData(comboData, valuelists, "/");
			} else {
			}
		}
	}

	private void fillData(ComboData comboData, String valuelists, String split) {
		String[] valuelist = valuelists.split(split);
		for (String value : valuelist) {
			CombItem combItem = new CombItem();
			combItem.setValue(value);
			combItem.setText(value);
			comboData.addCombItem(combItem);;
		}
	}

	/**
	 * У�����ֵ�޸ĺ��Ƿ�Ϸ�
	 * 
	 * @param datasetCellEvent
	 */
	public void onAfterDataChange_cp_sysinit_ds(
			DatasetCellEvent datasetCellEvent) {
		if (datasetCellEvent.getColIndex() == 2) {// ֻ��Բ����ֶν���У��
			Dataset ds = getDataset("cp_sysinit_ds");
			int index = ds.nameToIndex("valuelist");
			String valuelists = (String) ds.getSelectedRow().getValue(index);
			String[] valuelist = valuelists.split("-");
			if (valuelist.length == 2) {// ���ַ�Χ��ʽΪ:n-n
				if (!toNumberFlag(datasetCellEvent.getNewValue())) { // ��Ϊ����
					ds.getSelectedRow().setValue(ds.nameToIndex("value"),valuelist[0]);
					// AppLifeCycleContext.current().getApplicationContext().addExecScript("showErrorDialog('ֻ��Ϊ���֣�');");
				} else {
					int newValue = Integer.parseInt((String) datasetCellEvent.getNewValue());
					if (Integer.parseInt(valuelist[0]) > newValue|| Integer.parseInt(valuelist[1]) < newValue) {
						ds.getSelectedRow().setValue(ds.nameToIndex("value"),valuelist[0]);
						// AppLifeCycleContext.current().getApplicationContext().addExecScript("showErrorDialog('�������ַ�Χ��');");
					}
				}
			}
		}
	}

	/**
	 * �����Ƿ�Ϊ����
	 * 
	 * @param value
	 * @return
	 */
	private boolean toNumberFlag(Object value) {
		boolean toNumFlag = true;
		try {
			Integer.parseInt((String) value);
		} catch (Exception e) {
			toNumFlag = false;
		}
		return toNumFlag;
	}

	/**
	 * �������ڵ�id��ѯ
	 * 
	 * @param keys
	 */
	public void pluginplugin_main(Map keys) {
		String moudleId = (String) AppUtil.getAppAttr("moudleId");
		StringBuffer sql = new StringBuffer();
		sql.append("select * from cp_sysinit s,cp_sysinittemp st,cp_appscategory ac ")
			.append("where s.sysinit = st.pk_sysinittemp and s.dr=0 and st.dr=0 and ac.id = st.domainflag ")
			.append(" and ac.id = '" + moudleId + "'");

		// ��õ�ǰ�����Ѿ�ѡ�е���֯��Ԫid
//		ReferenceComp rc =(ReferenceComp)getCurrentWinCtx().getViewContext("orgref").getView().getViewComponents().getComponent("ref_org_textfield");
//		String pk_org = rc.getValue();
		String pk_org = (String)AppUtil.getAppAttr("pk_org");
		if (pk_org != null && !"".equals(pk_org)) {
			sql.append(" and s.pk_org = '" + pk_org + "'");
		}

		Dataset ds = getDataset("cp_sysinit_ds");
		loadSysinit(ds, sql.toString());
	}

	/**
	 * ������֯id��ѯ
	 * 
	 * @param keys
	 */
	public void pluginplugin_orgref(Map keys) {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from cp_sysinit s,cp_sysinittemp st,cp_appscategory ac ")
			.append("where s.sysinit = st.pk_sysinittemp and s.dr=0 and st.dr=0 and ac.id = st.domainflag ");
		//��õ�ǰѡ�����Ѿ�ѡ�еĽڵ�id
		String moudleId = (String)AppUtil.getAppAttr("moudleId");
		if (moudleId != null && !"".equals(moudleId)) {
			sql.append(" and ac.id = '" + moudleId + "'");
		}
		// ��õ�ǰ������ѡ�е���֯��Ԫid
		String pk_org = (String) AppUtil.getAppAttr("pk_org");
		if (pk_org != null && !"".equals(pk_org)) {
			sql.append(" and s.pk_org = '" + pk_org + "'");
		}
		Dataset ds = getDataset("cp_sysinit_ds");
		loadSysinit(ds, sql.toString());
	}
}
