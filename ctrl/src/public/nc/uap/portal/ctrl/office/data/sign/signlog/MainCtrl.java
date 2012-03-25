package nc.uap.portal.ctrl.office.data.sign.signlog;
import nc.uap.lfw.core.cmd.CmdInvoker;
import java.util.Map;
import nc.uap.lfw.core.event.DataLoadEvent;
import nc.uap.cpb.org.querycmd.QueryCmd;
import nc.uap.lfw.core.cmd.UifDatasetLoadCmd;
import nc.uap.lfw.core.ctrl.IController;
import nc.uap.lfw.core.cmd.base.FromWhereSQL;
import nc.uap.lfw.core.data.Dataset;
/** 
 * @author 
 */
public class MainCtrl implements IController {
  private static final long serialVersionUID=1L;
  public void onDataLoad_signlog(  DataLoadEvent dataLoadEvent){
    Dataset ds = dataLoadEvent.getSource();
		CmdInvoker.invoke(new UifDatasetLoadCmd(ds.getId()));
  }
  public void pluginsimpleQuery_plugin(  Map keys){
    FromWhereSQL whereSql = (FromWhereSQL) keys.get("whereSql");
	     String wheresql = whereSql.getWhere();
	     QueryCmd cmd = new QueryCmd("main", "signlog", wheresql);
	     cmd.excute();
  }
}
