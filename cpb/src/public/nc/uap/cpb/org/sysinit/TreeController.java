package nc.uap.cpb.org.sysinit;

import nc.uap.lfw.core.cmd.CmdInvoker;
import nc.uap.lfw.core.cmd.UifDatasetLoadCmd;
import nc.uap.lfw.core.cmd.UifPlugoutCmd;
import nc.uap.lfw.core.ctrl.IController;
import nc.uap.lfw.core.ctx.AppLifeCycleContext;
import nc.uap.lfw.core.ctx.WindowContext;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.event.DataLoadEvent;
import nc.uap.lfw.core.event.DatasetEvent;
import nc.uap.wfm.utils.AppUtil;
import nc.vo.pub.SuperVO;

/**
 * @author chenwl
 * 
 */
public class TreeController implements IController {
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
	 * ����ģ������ܽڵ�����һ���ڵ�
	 * 
	 * @param dataLoadEvent
	 */
	public void onDataLoad_cp_moudle_ds(DataLoadEvent dataLoadEvent) {
		Dataset ds = dataLoadEvent.getSource();
		CmdInvoker.invoke(new UifDatasetLoadCmd(ds.getId()));
	}

	/**
	 * ���ع��ܽڵ����Ķ����ڵ�
	 * 
	 * @param dataLoadEvent
	 */
	public void onDataLoad_cp_appscategory_ds(DataLoadEvent dataLoadEvent) {
		Dataset ds = dataLoadEvent.getSource();
		CmdInvoker.invoke(new UifDatasetLoadCmd(ds.getId()) {
			protected String postProcessQueryVO(SuperVO vo, Dataset ds) {
				String where = " pk_parent = '~' ";
				ds.setLastCondition(where);
				return where;
			}
		});
	}
	
	/**
	 * ����ڵ���ˢ�²�����
	 * 
	 * @param datasetEvent
	 */
	public void onAfterRowSelect_cp_appscategory_ds(DatasetEvent datasetEvent) {
		Dataset navDs = datasetEvent.getSource();
		int index = navDs.getFieldSet().nameToIndex("id");
		String moudleId = (String) navDs.getSelectedRow().getValue(index);
		AppUtil.addAppAttr("moudleId", moudleId);
		new UifPlugoutCmd("tree","plugout_tree").execute();
	}
	
}
