package nc.uap.portal.ctrl.office.controller;
import nc.uap.lfw.core.ctx.AppLifeCycleContext;
import nc.uap.lfw.core.cmd.UifDatasetAfterSelectCmd;
import nc.uap.lfw.core.ctrl.WindowController;
import nc.uap.lfw.core.cmd.CmdInvoker;
import nc.uap.lfw.core.event.PageEvent;
import nc.uap.lfw.core.event.DataLoadEvent;
import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.vo.pub.SuperVO;
import nc.uap.lfw.core.event.DatasetEvent;
import nc.uap.lfw.core.cmd.UifDatasetLoadCmd;
import nc.uap.lfw.core.data.Dataset;
import java.io.Serializable;
public class SignSelectControl implements WindowController, Serializable {
  private static final long serialVersionUID=7532916478964732880L;
  public void sysWindowClosed(  PageEvent event){
    LfwRuntimeEnvironment.getWebContext().destroyWebSession();
  }
  public void onDataLoad_ekeys(  DataLoadEvent dataLoadEvent){
    Dataset ds = dataLoadEvent.getSource();
		final String user = (String) LfwRuntimeEnvironment.getWebContext()
				.getWebSession().getOriginalParameter("user");
		AppLifeCycleContext.current().getWindowContext().addAppAttribute("user", user);
		CmdInvoker.invoke(new UifDatasetLoadCmd(ds.getId()) {
			protected String postProcessQueryVO(SuperVO vo, Dataset ds) {
				String where = " pk_sign in (select pk_sign from uapcp_usersigns where pk_user = '"+user+"') ";
				where = "";
				ds.setLastCondition(where);
				return where;
			}
		});
  }
  
}
