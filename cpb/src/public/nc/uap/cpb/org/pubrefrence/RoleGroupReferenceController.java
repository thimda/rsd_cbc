package nc.uap.cpb.org.pubrefrence;

import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.util.CpbServiceFacility;
import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.event.DataLoadEvent;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.lfw.core.refnode.RefNode;
import nc.uap.lfw.core.serializer.impl.SuperVO2DatasetSerializer;
import nc.uap.lfw.reference.app.AppReferenceController;
import nc.uap.lfw.reference.base.ILfwRefModel;
import nc.vo.pub.SuperVO;

public class RoleGroupReferenceController extends AppReferenceController {
	
	public void onDataLoad(DataLoadEvent e) {
		Dataset ds = (Dataset) e.getSource();
		try {
			SuperVO[] vos = CpbServiceFacility.getCpOrgRefefenceQry().getReferenceOrgs();
			new SuperVO2DatasetSerializer().serialize(vos, ds, Row.STATE_NORMAL);
			if(ds.getCurrentRowCount() > 0){
				ds.setRowSelectIndex(0);
			}
		} catch (CpbBusinessException e1) {
			LfwLogger.error(e1.getMessage(),e1);
		}
		
	}
	
	protected void processTreeSelWherePart(Dataset ds, RefNode rfnode, ILfwRefModel refModel) {
		String type = (String) LfwRuntimeEnvironment.getWebContext().getAppSession().getAttribute("rolegroup_type");	
		String pk_rolegroup = (String) LfwRuntimeEnvironment.getWebContext().getAppSession().getAttribute("pk_current_rolegroup");	
		String where = " type='"+type+"' and pk_rolegroup<>'"+pk_rolegroup+"'";
		refModel.setWherePart(where);
	}

}
