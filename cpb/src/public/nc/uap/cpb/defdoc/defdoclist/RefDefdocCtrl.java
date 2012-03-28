package nc.uap.cpb.defdoc.defdoclist;

import java.util.List;

import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.common.DatasetConstant;
import nc.uap.lfw.core.ctx.AppLifeCycleContext;
import nc.uap.lfw.core.ctx.ViewContext;
import nc.uap.lfw.core.ctx.WindowContext;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.DatasetRelation;
import nc.uap.lfw.core.data.DatasetRelations;
import nc.uap.lfw.core.data.PaginationInfo;
import nc.uap.lfw.core.data.Parameter;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.data.RowSet;
import nc.uap.lfw.core.event.DataLoadEvent;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.page.PageMeta;
import nc.uap.lfw.core.refnode.RefNode;
import nc.uap.lfw.core.serializer.impl.List2DatasetSerializer;
import nc.uap.lfw.reference.ReferenceConstant;
import nc.uap.lfw.reference.app.AppReferenceController;
import nc.uap.lfw.reference.base.ILfwRefModel;
import nc.uap.lfw.reference.base.RefResult;
import nc.uap.lfw.reference.base.TreeGridRefModel;
import nc.uap.lfw.reference.base.TwoTreeGridRefModel;
import nc.uap.lfw.reference.base.TwoTreeRefModel;
import nc.uap.lfw.reference.nc.NcAdapterGridRefModel;
import nc.uap.lfw.reference.util.LfwRefUtil;
import nc.uap.lfw.util.JsURLDecoder;
import nc.ui.bd.ref.model.DefdocDefaultRefModel;

public class RefDefdocCtrl extends AppReferenceController{
	
	private static final String RELATION_WHERE_SQL = "relationWhereSql";
	
	@Override
	protected void processTreeSelWherePart(Dataset ds, RefNode rfnode,
			ILfwRefModel refModel) {

	}
	public void processSelfWherePart(Dataset ds, RefNode rfnode,
			String filterValue, ILfwRefModel refModel) {
		String pk_defdoclist = (String)LfwRuntimeEnvironment.getWebContext()
				.getAppSession().getAttribute("pk_defdoclist");
		String pk_org = (String)LfwRuntimeEnvironment.getWebContext().getAppSession()
				.getAttribute("pk_org");
		String pk_group = (String)LfwRuntimeEnvironment.getWebContext().getAppSession()
				.getAttribute("pk_group");
		if (pk_defdoclist != null && pk_defdoclist.trim().length() != 0)
		((NcAdapterGridRefModel) refModel).getNcModel().setPara1(pk_defdoclist);
		if (pk_org != null && pk_org.trim().length() != 0)
			((NcAdapterGridRefModel) refModel).getNcModel().setPk_org(pk_org);
		if (pk_group != null && pk_group.trim().length() != 0)
			((NcAdapterGridRefModel) refModel).getNcModel().setPk_group(
					pk_group);
	}
	private WindowContext getCurrentWinCtx(){
	    return AppLifeCycleContext.current().getApplicationContext()
						.getCurrentWindowContext();
	  }
}
