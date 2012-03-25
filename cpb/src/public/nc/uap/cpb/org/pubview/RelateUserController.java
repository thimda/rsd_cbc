package nc.uap.cpb.org.pubview;
import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.cmd.CmdInvoker;
import nc.uap.lfw.core.cmd.UifDatasetAfterSelectCmd;
import nc.uap.lfw.core.cmd.UifDatasetLoadCmd;
import nc.uap.lfw.core.ctx.AppLifeCycleContext;
import nc.uap.lfw.core.ctrl.IController;
import nc.uap.lfw.core.event.DataLoadEvent;
import nc.uap.lfw.core.event.DatasetEvent;
import nc.uap.lfw.core.event.MouseEvent;
import nc.uap.lfw.core.ctx.WindowContext;
import nc.uap.lfw.core.data.Dataset;
import nc.vo.pub.SuperVO;
/** 
 */
public class RelateUserController implements IController {
	  private static final long serialVersionUID=1L;
	  public static final String PUBLIC_VIEW_USER = "user";
	  
	  private WindowContext getCurrentWinCtx(){
	    return AppLifeCycleContext.current().getApplicationContext().getCurrentWindowContext();
	  }
	  public void onCancelClick(  MouseEvent<?> mouseEvent){
	    AppLifeCycleContext.current().getApplicationContext().getCurrentWindowContext().closeViewDialog("user");
	  }
	  public void onDataLoad(  DataLoadEvent dataLoadEvent){
	    Dataset ds = dataLoadEvent.getSource();
			CmdInvoker.invoke(new UifDatasetLoadCmd(ds.getId()){
				protected String postProcessQueryVO(SuperVO vo, Dataset ds) {
					String pk_group = LfwRuntimeEnvironment.getLfwSessionBean().getPk_unit();
					String where = " pk_group = '"+pk_group+"'";
					ds.setLastCondition(where);
					return where;
				}
			});
	  }
	  public void onAfterRowSelect(  DatasetEvent datasetEvent){
	    Dataset ds = datasetEvent.getSource();
			CmdInvoker.invoke(new UifDatasetAfterSelectCmd(ds.getId()));
	  }
	}