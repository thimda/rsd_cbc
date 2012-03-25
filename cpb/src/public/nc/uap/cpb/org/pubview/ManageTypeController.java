package nc.uap.cpb.org.pubview;

import nc.uap.cpb.org.vos.ManageTypeVO;
import nc.uap.lfw.core.ctrl.IController;
import nc.uap.lfw.core.ctx.AppLifeCycleContext;
import nc.uap.lfw.core.ctx.WindowContext;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.event.DataLoadEvent;

/** 
 */
public class ManageTypeController implements IController {
	private static final long serialVersionUID = 1L;
	public static final String PUBLIC_VIEW_MANAGE_TYPE = "manage_type";

	private WindowContext getCurrentWinCtx() {
		return AppLifeCycleContext.current().getApplicationContext()
				.getCurrentWindowContext();
	}

	public void onDataLoad(DataLoadEvent dataLoadEvent) {
		Dataset ds = dataLoadEvent.getSource();
		Row adminrow = ds.getEmptyRow();
		adminrow.setValue(ds.nameToIndex("id"), ManageTypeVO.ADMIN_TYPE_ID);
		adminrow.setValue(ds.nameToIndex("value"), ManageTypeVO.ADMIN_TYPE_VALUE);
		adminrow.setValue(ds.nameToIndex("name"), ManageTypeVO.ADMIN_TYPE_NAME);
		
		Row businessrow = ds.getEmptyRow();
		businessrow.setValue(ds.nameToIndex("id"), ManageTypeVO.BUSINESS_TYPE_ID);
		businessrow.setValue(ds.nameToIndex("value"), ManageTypeVO.BUSINESS_TYPE_VALUE);
		businessrow.setValue(ds.nameToIndex("name"), ManageTypeVO.BUSINESS_TYPE_NAME);
		
		ds.addRow(adminrow);
		ds.addRow(businessrow);
	}
}
