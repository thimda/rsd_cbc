package nc.uap.cpb.org.listener;
import nc.itf.org.IOrgConst;
import nc.uap.cpb.org.util.CpbServiceFacility;
import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.event.DataLoadEvent;
import nc.uap.lfw.core.event.ctx.LfwPageContext;
import nc.uap.lfw.core.event.deft.DefaultDatasetServerListener;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.serializer.impl.SuperVO2DatasetSerializer;
import nc.vo.org.OrgVO;
import nc.vo.pub.BusinessException;
public class OrgsDsLoadListener extends DefaultDatasetServerListener {
	public OrgsDsLoadListener(LfwPageContext pagemeta, String widgetId) {
		super(pagemeta, widgetId);
	}
	public void onDataLoad(DataLoadEvent se) {
		String groupPk = LfwRuntimeEnvironment.getLfwSessionBean().getPk_unit();
		try {
			OrgVO[] vos = CpbServiceFacility.getCpOrgQry().getAllOrgVOSByGroupIDAndOrgTypes(groupPk, new String[] { IOrgConst.CORPORGTYPE, IOrgConst.DEPTORGTYPE }, false);
			new SuperVO2DatasetSerializer().serialize(vos, se.getSource(), Row.STATE_NORMAL);
			postProcessRowSelect(se.getSource());
		} catch (BusinessException e) {
			throw new LfwRuntimeException(e.getMessage());
		}
	}
}
