package testapp1;
import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.cmd.CmdInvoker;
import nc.uap.lfw.core.cmd.UifDatasetLoadCmd;
import nc.uap.lfw.core.cmd.UifOpenViewCmd;
import nc.uap.lfw.core.ctrl.IController;
import nc.uap.lfw.core.ctx.AppLifeCycleContext;
import nc.uap.lfw.core.ctx.ViewContext;
import nc.uap.lfw.core.ctx.WindowContext;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.event.DataLoadEvent;
import nc.uap.lfw.core.event.DialogEvent;
import nc.uap.lfw.core.event.MouseEvent;
/** 
 * @author chouhl
 */
public class Ctrl implements IController {
  private static final long serialVersionUID=1L;
  public void onAdd(  MouseEvent mouseEvent){
    CmdInvoker.invoke(new UifOpenViewCmd("edit", "500", "400"));
    AppLifeCycleContext ctx = AppLifeCycleContext.current();
    WindowContext viewCtx = ctx.getApplicationContext().getCurrentWindowContext();    
    Dataset ds = LfwRuntimeEnvironment.getWebContext().getParentPageMeta().getWidget("main").getViewModels().getDataset("ds1");
    ds.addRow(ds.getEmptyRow());
  }
  public void onDel(  MouseEvent mouseEvent){
    AppLifeCycleContext ctx = AppLifeCycleContext.current();
    ViewContext viewCtx = ctx.getApplicationContext().getCurrentWindowContext().getCurrentViewContext();
    Dataset ds = viewCtx.getView().getViewModels().getDataset("ds1");
    Row row = ds.getSelectedRow();
  }
  public void onDataLoad_ds1(  DataLoadEvent dataLoadEvent){
    Dataset ds = dataLoadEvent.getSource();
	  CmdInvoker.invoke(new UifDatasetLoadCmd(ds.getId()));
	  ds.setEnabled(true);
  }
  public void beforeShow(  DialogEvent dialogEvent){
  }
}
