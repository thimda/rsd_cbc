package nc.uap.wfm.wfdesign;
import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.event.ctx.LfwPageContext;
import nc.uap.lfw.core.event.deft.DefaultDatasetServerListener;
import nc.vo.pub.SuperVO;
public class WfFlowTypeDsLoadListener extends DefaultDatasetServerListener {
	public WfFlowTypeDsLoadListener(LfwPageContext pagemeta, String widgetId) {
		super(pagemeta, widgetId);
	}
	protected String postProcessQueryVO(SuperVO vo, Dataset ds) {
		String groupPk = LfwRuntimeEnvironment.getLfwSessionBean().getPk_unit();
		return "pk_group='" + groupPk + "' or pk_group='~'";
	}
}
