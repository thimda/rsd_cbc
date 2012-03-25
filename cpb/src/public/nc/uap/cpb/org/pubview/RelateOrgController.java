package nc.uap.cpb.org.pubview;

import nc.uap.cpb.org.util.CpbServiceFacility;
import nc.uap.lfw.core.cmd.CmdInvoker;
import nc.uap.lfw.core.cmd.UifDatasetLoadCmd;
import nc.uap.lfw.core.ctrl.IController;
import nc.uap.lfw.core.ctx.AppLifeCycleContext;
import nc.uap.lfw.core.ctx.WindowContext;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.PaginationInfo;
import nc.uap.lfw.core.event.DataLoadEvent;
import nc.uap.lfw.core.event.MouseEvent;
import nc.uap.lfw.core.exception.LfwBusinessException;
import nc.vo.pub.SuperVO;

public class RelateOrgController implements IController {
	private static final long serialVersionUID = 1L;
	
	public static final String PUBLIC_VIEW_ORG = "relateorg";

	private WindowContext getCurrentWinCtx() {
		return AppLifeCycleContext.current().getApplicationContext()
				.getCurrentWindowContext();
	}

	public void onCancelClick(MouseEvent<?> mouseEvent) {
		getCurrentWinCtx().closeViewDialog(PUBLIC_VIEW_ORG);
	}

	public void onDataLoad(DataLoadEvent dataLoadEvent) {
		Dataset ds = dataLoadEvent.getSource();
		CmdInvoker.invoke(new UifDatasetLoadCmd(ds.getId()){
			protected SuperVO[] queryVOs(PaginationInfo pinfo, SuperVO vo, String wherePart) throws LfwBusinessException {
				SuperVO[] vos = CpbServiceFacility.getCpOrgRefefenceQry().getReferenceOrgs();
				return vos;
			}
		});
	}
}