package nc.uap.wfm.agent;
import nc.uap.wfm.engine.IProcessSheduler;
/**
 * ˢ���û����������ö�ʱ��
 * 
 * @author licza
 * 
 */
public class UserAgentSettingRefresher implements IProcessSheduler {
	public String getId() {
		return this.getClass().getName();
	}
	public long getSeperator() {
		return 60000L;
	}
	public void shceduler() {
	// try {
	// PtUseragentVO[] vos =
	// PortalServiceUtil.getUserAgentQryService().getBeActiveDeptAgents();
	// if (LfwLogger.isDebugEnabled()) {
	// LfwLogger.debug("===UserAgentSettingRefresher#go()=== ˢ���û����Ŵ�������:һ����" +
	// vos.length + "����¼Ҫˢ��");
	// }
	// IPtUserAgentService uas = PortalServiceUtil.getUserAgentService();
	// if (!PtUtil.isNull(vos)) {
	// for (PtUseragentVO vo : vos) {
	// vo.setUseflag(UFBoolean.TRUE);
	// }
	// uas.update(vos);
	// }
	//			
	// PtFrmAgentVO[] avs =
	// PortalServiceUtil.getUserAgentQryService().getBeActiveFrmAgents();
	// if (LfwLogger.isDebugEnabled()) {
	// LfwLogger.debug("===UserAgentSettingRefresher#go()=== ˢ���û������ʹ�������:һ����" +
	// avs.length + "����¼Ҫˢ��");
	// }
	//			 
	// if (!PtUtil.isNull(avs)) {
	// for (PtFrmAgentVO vo : avs) {
	// vo.setUseflag(UFBoolean.TRUE);
	// }
	// uas.update(avs);
	// }
	//			
	// } catch (PortalServiceException e) {
	// LfwLogger.error("ˢ���û����������쳣", e);
	// }
	}
}
