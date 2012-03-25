package nc.uap.wfm.taskcolmgr;

import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.event.DataLoadEvent;
import nc.uap.lfw.core.event.ctx.LfwPageContext;
import nc.uap.lfw.core.event.deft.DefaultDatasetServerListener;

public class TaskColLeftDsLoadListener extends DefaultDatasetServerListener {
	public TaskColLeftDsLoadListener(LfwPageContext pagemeta, String widgetId) {
		super(pagemeta, widgetId);
	}
	@Override
	public void onDataLoad(DataLoadEvent se) {
		se.getSource().setCurrentKey(Dataset.MASTER_KEY);
	}
}
