package demo1;
import java.io.Serializable;

import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.cmd.CmdInvoker;
import nc.uap.lfw.core.cmd.UifOpenViewCmd;
import nc.uap.lfw.core.ctrl.WindowController;
import nc.uap.lfw.core.event.MouseEvent;
import nc.uap.lfw.core.event.PageEvent;
public class TestController implements WindowController, Serializable {
  private static final long serialVersionUID=7532916478964732880L;
  public void sysWindowClosed(  PageEvent event){
    LfwRuntimeEnvironment.getWebContext().destroyWebSession();
  }
  public void onAdd(  MouseEvent mouseEvent){
    CmdInvoker.invoke(new UifOpenViewCmd("main"));
  }
  public void onSave(  MouseEvent mouseEvent){
	  
  }
  public void onDel(  MouseEvent mouseEvent){
  }
}
