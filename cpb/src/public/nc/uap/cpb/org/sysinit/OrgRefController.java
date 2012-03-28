package nc.uap.cpb.org.sysinit;

import nc.uap.lfw.core.cmd.UifPlugoutCmd;
import nc.uap.lfw.core.ctrl.IController;
import nc.uap.lfw.core.event.TextEvent;
import nc.uap.wfm.utils.AppUtil;

/**
 * @author chenwl
 */
public class OrgRefController implements IController {
	private static final long serialVersionUID = 1L;

	/**
	 * @param textEvent
	 */
	public void orgref_valueChanged(TextEvent textEvent) {
		String pk_org = textEvent.getSource().getValue();
		AppUtil.addAppAttr("pk_org", pk_org);
		new UifPlugoutCmd("orgref","plugout_orgref").execute();
	}
}
