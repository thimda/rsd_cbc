package nc.uap.cpb.org.role;
import nc.uap.lfw.core.cmd.CmdInvoker;
import nc.uap.lfw.core.ctrl.IController;
import nc.uap.lfw.core.ctx.AppLifeCycleContext;
import nc.uap.lfw.core.ctx.WindowContext;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.event.DataLoadEvent;
import nc.uap.lfw.core.event.MouseEvent;
/** 
 */
public class RelateRespController implements IController {
  private static final long serialVersionUID=1L;
  private WindowContext getCurrentWinCtx(){
    return AppLifeCycleContext.current().getApplicationContext().getCurrentWindowContext();
  }
  public void onCancelClick(  MouseEvent<?> mouseEvent){
    AppLifeCycleContext.current().getApplicationContext().getCurrentWindowContext().closeViewDialog("relateresp");
  }
  public void onDataLoad(  DataLoadEvent dataLoadEvent){
	Dataset ds = dataLoadEvent.getSource();
   	CmdInvoker.invoke(new UifLoadResCmd(ds.getId()));
  }
}
