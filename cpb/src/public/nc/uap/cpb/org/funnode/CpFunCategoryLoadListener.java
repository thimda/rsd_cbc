package nc.uap.cpb.org.funnode;
import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.itf.ICpAppsNodeQry;
import nc.uap.cpb.org.util.CpbServiceFacility;
import nc.uap.cpb.org.vos.CpAppsCategoryVO;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.event.DataLoadEvent;
import nc.uap.lfw.core.event.ctx.LfwPageContext;
import nc.uap.lfw.core.event.deft.DefaultDatasetServerListener;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.lfw.core.serializer.impl.SuperVO2DatasetSerializer;
public class CpFunCategoryLoadListener extends DefaultDatasetServerListener {
	public CpFunCategoryLoadListener(LfwPageContext pagemeta, String widgetId) {
		super(pagemeta, widgetId);
	}
	public void onDataLoad(DataLoadEvent se) {
		Dataset ds = se.getSource();
		ICpAppsNodeQry service = CpbServiceFacility.getPortalManagerAppService();
		CpAppsCategoryVO[] categories = null;
		try {
			categories = service.getAllCategory("0001AA100000000001ST");
		} catch (CpbBusinessException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
		new SuperVO2DatasetSerializer().serialize(categories, ds, Row.STATE_NORMAL);
		postProcessRowSelect(ds);
	}
}
