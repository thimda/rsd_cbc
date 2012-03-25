package nc.uap.wfm.selffun;
import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.util.CpbServiceFacility;
import nc.uap.lfw.core.comp.MenuItem;
import nc.uap.lfw.core.crud.CRUDHelper;
import nc.uap.lfw.core.ctrl.IController;
import nc.uap.lfw.core.ctx.AppLifeCycleContext;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.event.DataLoadEvent;
import nc.uap.lfw.core.event.DatasetEvent;
import nc.uap.lfw.core.event.MouseEvent;
import nc.uap.lfw.core.exception.LfwBusinessException;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.serializer.impl.Dataset2SuperVOSerializer;
import nc.uap.lfw.core.serializer.impl.SuperVO2DatasetSerializer;
import nc.uap.lfw.util.LfwClassUtil;
import nc.uap.wfm.vo.WfmSelfFunVO;
import nc.vo.pub.SuperVO;
import nc.vo.pub.VOStatus;
/**
 * @author chouhl
 */
public class SelfFunMainCtrl implements IController {
	private static final long serialVersionUID = 1L;
	public void modify(MouseEvent<MenuItem> e) {
		LfwWidget widget = this.getView();
		Dataset dsSelfFun = widget.getViewModels().getDataset("ds_selffun");
		dsSelfFun.setEnabled(true);
	}
	public void delete(MouseEvent<MenuItem> e) {
		LfwWidget widget = this.getView();
		Dataset dsSelfFun = widget.getViewModels().getDataset("ds_selffun");
		Row selectedRow = dsSelfFun.getSelectedRow();
		if (selectedRow == null) {
			throw new LfwRuntimeException("请选择要删除的行数据");
		}
		Dataset2SuperVOSerializer<SuperVO> serializer = new Dataset2SuperVOSerializer<SuperVO>();
		SuperVO[] vos = serializer.serialize(dsSelfFun, dsSelfFun.getSelectedRow());
		try {
			CpbServiceFacility.getCpSuperVOBill().deleteSuperVO(vos[0]);
		} catch (CpbBusinessException e1) {
			throw new LfwRuntimeException("删除数据是失败");
		}
		this.onload(null);
	}
	public void cancel(MouseEvent<MenuItem> e) {
		datasetUndo("main", new String[] { "ds_selffun" });
		widgetUndo("main");
		pageUndo();
	}
	public void datasetUndo(String widgetId, String[] dssIds) {
		for (int i = 0; i < dssIds.length; i++) {
			AppLifeCycleContext.current().getApplicationContext().addExecScript("pageUI.getWidget('" + widgetId + "').getDataset('" + dssIds[i] + "').undo();\n");
		}
	}
	public void widgetUndo(String widgetId) {
		AppLifeCycleContext.current().getApplicationContext().addBeforeExecScript("pageUI.getWidget('" + widgetId + "').undo();\n");
	}
	public void pageUndo() {
		AppLifeCycleContext.current().getApplicationContext().addBeforeExecScript("pageUI.undo();\n");
	}
	public void save(MouseEvent<MenuItem> e) {
		LfwWidget widget = this.getView();
		Dataset dsSelfFun = widget.getViewModels().getDataset("ds_selffun");
		Dataset2SuperVOSerializer<SuperVO> serializer = new Dataset2SuperVOSerializer<SuperVO>();
		SuperVO[] vos = serializer.serialize(dsSelfFun, dsSelfFun.getSelectedRow());
		SuperVO superVo = vos[0];
		try {
			if (superVo.getStatus() == VOStatus.NEW) {
				CpbServiceFacility.getCpSuperVOBill().insertSuperVO(superVo);
			}
			if (superVo.getStatus() == VOStatus.UPDATED) {
				CpbServiceFacility.getCpSuperVOBill().updateSuperVO(superVo);
			}
		} catch (CpbBusinessException e1) {
			throw new LfwRuntimeException("保存数据是失败");
		}
		dsSelfFun.setEnabled(false);
	}
	public void add(MouseEvent<MenuItem> mouseEvent) {
		LfwWidget widget = this.getView();
		Dataset dsSelfFun = widget.getViewModels().getDataset("ds_selffun");
		Row emptyRow = dsSelfFun.getEmptyRow();
		dsSelfFun.addRow(emptyRow);
		dsSelfFun.setRowSelectIndex(dsSelfFun.getRowIndex(emptyRow));
		dsSelfFun.setEnabled(true);
	}
	public LfwWidget getView() {
		LfwWidget widget = AppLifeCycleContext.current().getApplicationContext().getCurrentWindowContext().getViewContext("main").getView();
		return widget;
	}
	public void onload(DataLoadEvent dataLoadEvent) {
		LfwWidget widget = this.getView();
		Dataset dsSelfFun = widget.getViewModels().getDataset("ds_selffun");
		SuperVO vo = (SuperVO) LfwClassUtil.newInstance(WfmSelfFunVO.class.getName());
		SuperVO[] vos;
		try {
			vos = CRUDHelper.getCRUDService().queryVOs(vo, null, null, null, null);
			new SuperVO2DatasetSerializer().serialize(vos, dsSelfFun, Row.STATE_NORMAL);
		} catch (LfwBusinessException e) {
			throw new LfwRuntimeException(e.getMessage());
		}
	}
	public void onAfterRowSelected(DatasetEvent datasetEvent) {}
}
