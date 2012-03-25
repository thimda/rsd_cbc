package nc.uap.cpb.org.menuitem;

import nc.uap.lfw.core.cmd.CmdInvoker;
import nc.uap.lfw.core.cmd.UifDatasetAfterSelectCmd;
import nc.uap.lfw.core.cmd.UifDatasetLoadCmd;
import nc.uap.lfw.core.cmd.base.AbstractWidgetController;
import nc.uap.lfw.core.comp.WebElement;
import nc.uap.lfw.core.ctrl.IController;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.event.DataLoadEvent;
import nc.uap.lfw.core.event.DatasetEvent;


public class MenucategoryController<T extends WebElement> extends
		AbstractWidgetController implements IController {

	@Override
	public String getMasterDsId() {
		return "ds_menucategory";
	}
	
	public void onDataLoad_ds_menucategory(DataLoadEvent dataLoadEvent) {
		Dataset ds = dataLoadEvent.getSource();
		CmdInvoker.invoke(new UifDatasetLoadCmd(ds.getId()){
			protected void postProcessRowSelect(Dataset ds) {
				if(ds.getCurrentRowCount() > 0){
					ds.setSelectedIndex(0);
				}
			}
		});
	}

	public void onAfterRowSelect_ds_menucategory(DatasetEvent datasetEvent) {
		Dataset ds = datasetEvent.getSource();
		CmdInvoker.invoke(new UifDatasetAfterSelectCmd(ds.getId()));
	}

}