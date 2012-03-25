package nc.uap.cpb.org.provider;

import java.util.HashMap;
import java.util.Map;

import nc.uap.lfw.core.cmd.CmdInvoker;
import nc.uap.lfw.core.cmd.UifPlugoutCmd;
import nc.uap.lfw.core.comp.ReferenceComp;
import nc.uap.lfw.core.ctrl.IController;
import nc.uap.lfw.core.ctx.AppLifeCycleContext;
import nc.uap.lfw.core.event.TextEvent;

public class PubOrgController implements IController{

	private static final long serialVersionUID = 1L;
	
	public void textValueChange(TextEvent textEvent){
		ReferenceComp refcomp = (ReferenceComp) textEvent.getSource();
		AppLifeCycleContext.current().getApplicationContext().addAppAttribute("orgPk", refcomp.getValue());
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("orgValue", refcomp.getValue());
		
		CmdInvoker.invoke(new UifPlugoutCmd("navorg", "orgout", paramMap));
	}
}
