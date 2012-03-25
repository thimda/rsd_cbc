package nc.uap.portal.ctrl.office.controller;

import java.util.HashMap;

import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.cmd.UifPlugoutCmd;
import nc.uap.lfw.core.comp.ButtonComp;
import nc.uap.lfw.core.comp.text.TextComp;
import nc.uap.lfw.core.ctx.AppLifeCycleContext;
import nc.uap.lfw.core.event.ScriptEvent;
import nc.uap.lfw.core.event.SelfDefEvent;
import nc.uap.lfw.core.event.PageEvent;
import nc.uap.lfw.core.event.MouseEvent;
import nc.uap.lfw.core.exception.LfwBusinessException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.page.PageMeta;
import nc.uap.portal.ctrl.office.data.SignHelper;
import nc.uap.portal.ctrl.office.data.sign.ServersignVO;

public class SignEditControl {
	public void newonclick(MouseEvent mouseEvent) {

	}

	public void signafterPageInit(PageEvent pageEvent) {
		PageMeta pm = pageEvent.getSource();
		LfwWidget widget = pm.getWidget("main");
		TextComp user = (TextComp) widget.getViewComponents().getComponent(
				"textuser");
		user.setValue(LfwRuntimeEnvironment.getLfwSessionBean() == null ? ""
				: LfwRuntimeEnvironment.getLfwSessionBean().getUser_code());
		ButtonComp btn = (ButtonComp) widget.getViewComponents().getComponent(
				"btnok");
		btn.setEnabled(false);

		HashMap map = (HashMap) LfwRuntimeEnvironment.getWebContext()
				.getAppSession().getAttribute("signeditor");
		String type = "";
		String pk = "";
		if (map != null) {
			type = (String) map.get("edittype");
			pk = (String) map.get("pk");
		}
		AppLifeCycleContext.current().getApplicationContext().getClientSession().setAttribute("edittype",type );
		AppLifeCycleContext.current().getApplicationContext().addExecScript("seteditType() \n");
		if (type != null ) {
			if(type.equals("edit")){
				ServersignVO sign;
				try {
					sign = SignHelper.GetServerSignByPK(pk);
					if (sign != null) {
						StringBuffer buf = new StringBuffer("openRemoteSign('"
								+ sign.getPk_lfwfile() + "','" + sign.getPk_sign()
								+ "')\n");
						AppLifeCycleContext.current().getApplicationContext().addExecScript(buf.toString());
					}
				} catch (LfwBusinessException e) {
					LfwLogger.error(e);
				}
			}
			else if(type.equals("ekeyedit")){
				StringBuffer buf = new StringBuffer("openFromEkey()\n");
				AppLifeCycleContext.current().getApplicationContext().addExecScript(buf.toString());
			}
		}
	}
	
	public void onCancelClick(MouseEvent<?> mouseEvent) {
		AppLifeCycleContext.current().getApplicationContext().closeWinDialog();
	}
	public void SaveSignEvent(ScriptEvent event) {
		String pk_sign = AppLifeCycleContext.current().getParameter("pk");// ²ÎÊý
		AppLifeCycleContext.current().getWindowContext().addAppAttribute("signpk",pk_sign);
		new UifPlugoutCmd("sign_editor", "main", "sign_plugout").execute();
		
	}
}
