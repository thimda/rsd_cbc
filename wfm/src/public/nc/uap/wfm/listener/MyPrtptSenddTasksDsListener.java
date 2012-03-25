package nc.uap.wfm.listener;
import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.data.PaginationInfo;
import nc.uap.lfw.core.event.ctx.LfwPageContext;
import nc.uap.lfw.core.event.deft.DefaultDatasetServerListener;
import nc.uap.lfw.core.exception.LfwBusinessException;
import nc.uap.wfm.constant.WfmConstants;
import nc.vo.pub.SuperVO;
public class MyPrtptSenddTasksDsListener extends DefaultDatasetServerListener {
	public MyPrtptSenddTasksDsListener(LfwPageContext pagemeta, String widgetId) {
		super(pagemeta, widgetId);
	}
	@Override
	protected SuperVO[] queryVOs(PaginationInfo pinfo, SuperVO vo, String wherePart) throws LfwBusinessException {
		String userPk = LfwRuntimeEnvironment.getLfwSessionBean().getPk_user();
		String proDefPk = LfwRuntimeEnvironment.getWebContext().getParameter(WfmConstants.ProDefPk);
		wherePart = "pk_creater='" + userPk + "'";
		if (proDefPk == null || proDefPk.length() == 0) {
			return super.queryVOs(pinfo, vo, wherePart);
		} else {
			wherePart = wherePart + "and pk_prodef='" + proDefPk + "'";
			return super.queryVOs(pinfo, vo, wherePart, "order by startdate desc");
		}
	}
}
