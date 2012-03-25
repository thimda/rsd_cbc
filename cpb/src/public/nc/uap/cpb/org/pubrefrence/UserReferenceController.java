package nc.uap.cpb.org.pubrefrence;

import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.refnode.RefNode;
import nc.uap.lfw.reference.app.AppReferenceController;
import nc.uap.lfw.reference.base.ILfwRefModel;
import nc.uap.lfw.reference.nc.NcAdapterTreeGridRefModel;

public class UserReferenceController extends AppReferenceController {
	protected void processSelfWherePart(Dataset ds, RefNode rfnode,
			String filterValue, ILfwRefModel refModel) {
	String pk_group = LfwRuntimeEnvironment.getLfwSessionBean().getPk_unit();
	String where = " pk_group = '"+pk_group+"'";
	((NcAdapterTreeGridRefModel)refModel).setClassWherePart(where);
	}

}
