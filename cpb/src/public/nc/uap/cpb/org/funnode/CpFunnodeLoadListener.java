package nc.uap.cpb.org.funnode;
import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.itf.ICpAppsNodeQry;
import nc.uap.cpb.org.util.CpbServiceFacility;
import nc.uap.cpb.org.vos.CpAppsNodeVO;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.event.DataLoadEvent;
import nc.uap.lfw.core.event.ctx.LfwPageContext;
import nc.uap.lfw.core.event.deft.DefaultDatasetServerListener;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.lfw.core.serializer.impl.SuperVO2DatasetSerializer;
import org.apache.commons.lang.ArrayUtils;
public class CpFunnodeLoadListener extends DefaultDatasetServerListener {
	public CpFunnodeLoadListener(LfwPageContext pagemeta, String widgetId) {
		super(pagemeta, widgetId);
	}
	public void onDataLoad(DataLoadEvent se) {
		Dataset ds = se.getSource();
		String keys = ds.getReqParameters().getParameterValue("query_param_keys");
		String values = null;
		if (keys != null && !keys.equals("")) {
			values = ds.getReqParameters().getParameterValue("query_param_values");
		}
		int userType = 0;
		CpAppsNodeVO[] appsNodes = null;
		ICpAppsNodeQry service = CpbServiceFacility.getPortalManagerAppService();
		try {
			if (userType == 2/* CpUserVO.USERTYPE_SYSADMIN */) {
				appsNodes = service.getNodeByCategory(values);
			} else if (userType == 3/* CpUserVO.USERTYPE_GROUPADMIN */) {
				// appsNodes =
				// CpbServiceFacility.getFunQryService().getNodeByUser(values,
				// userVo.getCuserid(), "");
			}
		} catch (CpbBusinessException e) {
			// TODO Auto-generated catch block
			LfwLogger.error(e.getMessage(), e);
		}
		if (ArrayUtils.isEmpty(appsNodes)) {
			return;
		}
		for (int i = 0; i < appsNodes.length; i++) {
			appsNodes[i].setUrl(appsNodes[i].getUrl());
		}
		new SuperVO2DatasetSerializer().serialize(appsNodes, ds, Row.STATE_NORMAL);
		postProcessRowSelect(ds);
	}
}
