package nc.uap.cpb.org.pubview;

import nc.uap.lfw.core.ctrl.IController;
import nc.uap.lfw.core.ctx.AppLifeCycleContext;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.event.DataLoadEvent;
import nc.uap.lfw.core.event.DialogEvent;
import nc.uap.lfw.core.event.MouseEvent;
import nc.uap.lfw.core.page.LfwWidget;
import nc.vo.pub.lang.UFDate;

public class EnabledateController implements IController {
	private static final long serialVersionUID = 1L;
	
	public static final String PUBLIC_VIEW_ENABLEDATE = "enabledate";
	public void onCancelClick(MouseEvent<?> mouseEvent) {
		AppLifeCycleContext.current().getApplicationContext()
				.getCurrentWindowContext().closeViewDialog(PUBLIC_VIEW_ENABLEDATE);
	}
	
	  public void onBeforeShow(  DialogEvent dialogEvent){
		  LfwWidget enabledate = AppLifeCycleContext.current().getWindowContext().getViewContext(PUBLIC_VIEW_ENABLEDATE).getView();
		  Dataset ds = enabledate.getViewModels().getDataset("ds_date");
		  if(ds.getCurrentKey()==null||"".equals(ds.getCurrentKey())){
			  ds.setCurrentKey(Dataset.MASTER_KEY);
		  }
		  Row row = ds.getEmptyRow();
		  row.setValue(ds.nameToIndex("enabledate"), new UFDate());
		  row.setValue(ds.nameToIndex("disabledate"), new UFDate());
		  ds.addRow(row); 
		  ds.setSelectedIndex(ds.getRowIndex(row));
		  ds.setEnabled(true);
	  }

	public void onDataLoad(DataLoadEvent dataLoadEvent) {
	}
}