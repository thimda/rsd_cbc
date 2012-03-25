package nc.uap.cpb.org.permissionqry;
import java.io.Serializable;
import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.ctrl.WindowController;
import nc.uap.lfw.core.event.PageEvent;
public class WinController implements WindowController, Serializable {
	private static final long serialVersionUID = -3790997954442242166L;

	public void sysWindowClosed(  PageEvent event){
    LfwRuntimeEnvironment.getWebContext().destroyWebSession();
  }
}
