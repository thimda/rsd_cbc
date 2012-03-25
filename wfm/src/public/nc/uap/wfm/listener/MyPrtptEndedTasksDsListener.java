package nc.uap.wfm.listener;
import nc.bs.framework.common.NCLocator;
import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.data.PaginationInfo;
import nc.uap.lfw.core.event.ctx.LfwPageContext;
import nc.uap.lfw.core.event.deft.DefaultDatasetServerListener;
import nc.uap.lfw.core.exception.LfwBusinessException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.wfm.constant.WfmConstants;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.itf.IWfmTaskQry;
import nc.vo.pub.SuperVO;
public class MyPrtptEndedTasksDsListener extends DefaultDatasetServerListener {
	public MyPrtptEndedTasksDsListener(LfwPageContext pagemeta, String widgetId) {
		super(pagemeta, widgetId);
	}
	protected SuperVO[] queryVOs(PaginationInfo pinfo, SuperVO vo, String wherePart) throws LfwBusinessException {
		String userPk = LfwRuntimeEnvironment.getLfwSessionBean().getPk_user();
		String proDefPk = LfwRuntimeEnvironment.getWebContext().getParameter(WfmConstants.ProDefPk);
		SuperVO[] vos = null;
		try {
			vos = NCLocator.getInstance().lookup(IWfmTaskQry.class).getMyEndedTasks(userPk, proDefPk, pinfo);
		} catch (WfmServiceException e) {
			LfwLogger.error(e.getMessage(), e);
		}
		return vos;
	}
}
