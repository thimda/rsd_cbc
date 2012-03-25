package nc.uap.cpb.org.user;

import nc.uap.lfw.core.ctx.AppLifeCycleContext;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.refnode.RefNode;
import nc.uap.lfw.reference.app.AppReferenceController;
import nc.uap.lfw.reference.base.ILfwRefModel;
import nc.uap.lfw.reference.nc.NcAdapterTreeGridRefModel;

public class UserGroupReferenceController extends AppReferenceController {
	protected void processSelfWherePart(Dataset ds, RefNode rfnode,
			String filterValue, ILfwRefModel refModel) {
		 LfwWidget widget = AppLifeCycleContext.current().getApplicationContext().getWindowContext("cp_user").getViewContext(UserMgrConstants.Widget_Edit).getView();
		Dataset dsUser = widget.getViewModels().getDataset(UserMgrConstants.Ds_User);
		Row row = dsUser.getSelectedRow();
		String pk_org = row.getString(dsUser.nameToIndex("pk_org"));
		String where = " pk_org = '"+pk_org+"'";
		((NcAdapterTreeGridRefModel)refModel).setClassWherePart(where);
		}

}
