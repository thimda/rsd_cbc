package nc.uap.cpb.org.pubview.mode;

import java.io.Serializable;
import java.util.Map;

import nc.bs.framework.common.NCLocator;
import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.itf.ICpUserQry;
import nc.uap.cpb.org.provider.PubOrgController;
import nc.uap.cpb.org.vos.CpUserVO;
import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.comp.ReferenceComp;
import nc.uap.lfw.core.ctx.AppLifeCycleContext;
import nc.uap.lfw.core.event.conf.EventConf;
import nc.uap.lfw.core.event.conf.TextListener;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.lfw.core.model.IWidgetContentProvider;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.page.PageMeta;
import nc.uap.lfw.core.page.PlugoutDesc;
import nc.uap.lfw.core.refnode.NCRefNode;
import nc.uap.lfw.core.refnode.SelfDefRefNode;
import nc.uap.lfw.reference.base.ILfwRefModel;
import nc.uap.lfw.reference.util.LfwRefUtil;
import nc.vo.sm.UserVO;

public class ModeOrgWidgetProvider implements IWidgetContentProvider {
	
	@Override
	public LfwWidget buildWidget(PageMeta pm, LfwWidget conf,
			Map<String, Object> paramMap, String currWidgetId) {
		//String refCode = (String) conf.getExtendAttributeValue();
		ModeOrgFilter filter = new ModeOrgFilter();
		if(paramMap.containsKey("global")){
			filter.setIsneedGlobal(Boolean.getBoolean(paramMap.get("global").toString()));
		}
		if(paramMap.containsKey("filterSecurity")){
			filter.setIsfilterSecurity(Boolean.getBoolean(paramMap.get("filterSecurity").toString()));
		}
		
		String pk_user = LfwRuntimeEnvironment.getLfwSessionBean().getPk_user();
		ICpUserQry userqry = NCLocator.getInstance().lookup(ICpUserQry.class);
		CpUserVO user = null;
		UserVO ncuser = null;
		 try {
			user = userqry.getUserByPk(pk_user);
			ncuser = userqry.getNCUserByUserPk(pk_user);
		} catch (CpbBusinessException e) {
			LfwLogger.error(e);
		}
		if(ncuser != null){
			int type = ncuser.getUser_type();
			if(type == 0 || type == 2 )
				filter.setIsgrpadmin(true);
		}
		if(user != null){
			int type = user.getUser_type();
			if(type == 0 || type == 2 )
				filter.setIsgrpadmin(true);
		}
		AppLifeCycleContext.current().getApplicationContext().addAppAttribute(ModeOrgHelper.ModeFilter_AttID, filter);
		LfwWidget widget = new LfwWidget();
		
		widget.setControllerClazz(PubOrgController.class.getName());
		
		widget.setId(currWidgetId);
		
		ReferenceComp refcomp = new ReferenceComp();
		refcomp.setId("refcomp");
		refcomp.setWidget(widget);
		refcomp.setValue("");
		refcomp.setText("◊È÷Ø");
		refcomp.setEditorType("Reference");
		refcomp.setEnabled(true);
		widget.getViewComponents().addComponent(refcomp);
		
		// …Ë÷√refnode
		SelfDefRefNode refNode = new SelfDefRefNode();
		refNode.setDialog(false);
		refNode.setId("pub_orgref");
		refNode.setPath("pub_orgref/add?model="+ ModeRefOrgPageModel.class.getName());
		
		refNode.setWidget(widget);
		widget.getViewModels().addRefNode(refNode);
		refcomp.setRefcode(refNode.getId());
		
		EventConf event = new EventConf();
		event.setName(TextListener.VALUE_CHANGED);
		event.setMethodName("textValueChange");
		event.setJsEventClaszz(TextListener.class.getName());
		refcomp.addEventConf(event);
		
		PlugoutDesc out = new PlugoutDesc();
		out.setId("orgout");
		widget.addPlugoutDescs(out);
		
		return widget;
	}

}
