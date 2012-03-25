package nc.uap.wfm.listener;
import nc.bs.framework.common.NCLocator;
import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.PaginationInfo;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.event.DataLoadEvent;
import nc.uap.lfw.core.event.ctx.LfwPageContext;
import nc.uap.lfw.core.event.deft.DefaultDatasetServerListener;
import nc.uap.lfw.core.exception.LfwBusinessException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.wfm.constant.WfmConstants;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.itf.IWfmTaskQry;
import nc.vo.pub.SuperVO;
public class MyPrtptUndneTasksDsListener extends DefaultDatasetServerListener {
	public MyPrtptUndneTasksDsListener(LfwPageContext pagemeta, String widgetId) {
		super(pagemeta, widgetId);
	}
	@Override public void onDataLoad(DataLoadEvent se) {
		Dataset dataset = se.getSource();
		dataset.setCurrentKey(Dataset.MASTER_KEY);
		super.onDataLoad(se);
		if (dataset.getCurrentRowData() == null) {
			return;
		}
		Row[] rows = dataset.getCurrentRowData().getRows();
		if (rows == null || rows.length == 0) {
			return;
		}
	}
	@Override protected SuperVO[] queryVOs(PaginationInfo pinfo, SuperVO vo, String wherePart) throws LfwBusinessException {
		String userPk = LfwRuntimeEnvironment.getLfwSessionBean().getPk_user();
		String proDefPk = LfwRuntimeEnvironment.getWebContext().getParameter(WfmConstants.ProDefPk);
		SuperVO[] vos = null;
		try {
			vos = NCLocator.getInstance().lookup(IWfmTaskQry.class).getMyUnDneTasks(userPk, proDefPk, pinfo);
		} catch (WfmServiceException e) {
			LfwLogger.error(e.getMessage(), e);
		}
		this.getGlobalContext().addExecScript("setTabText('tab_task','itm_undnetask','待办的文件(" + pinfo.getRecordsCount() + ")');");
		String record = String.valueOf(pinfo.getRecordsCount());
		String str = record + "**" + "myhandlerwork_" + proDefPk;
		this.getGlobalContext().addExecScript("doPortletAction('" + str + "')");
		return vos;
	}
}
