package nc.uap.wfm.agent;
import nc.uap.wfm.engine.IProcessSheduler;
/**
 * 刷新用户代理人设置定时器
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
	// LfwLogger.debug("===UserAgentSettingRefresher#go()=== 刷新用户部门代理设置:一共有" +
	// vos.length + "条记录要刷新");
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
	// LfwLogger.debug("===UserAgentSettingRefresher#go()=== 刷新用户表单类型代理设置:一共有" +
	// avs.length + "条记录要刷新");
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
	// LfwLogger.error("刷新用户代理设置异常", e);
	// }
	}
}
