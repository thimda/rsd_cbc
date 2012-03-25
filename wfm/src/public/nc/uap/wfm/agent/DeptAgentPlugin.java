package nc.uap.wfm.agent;


/**
 * 部门代理人查询插件
 * @author licza
 * 
 */
public class DeptAgentPlugin implements IUserAgentPlugin {

	@Override
	public void doFind(AgentContext ctx, AgentChain chain) {
//		try {
//			String agent = PortalServiceUtil.getUserAgentQryService().getDeptAgent(ctx.getUser(), ctx.getDept());
//			if(!ctx.getUser().equals(agent)){
//				ctx.setAgent(agent);
//				chain.doFind(ctx);
//			}
//		} catch (PortalServiceException e) {
//			LfwLogger.error(e.getMessage(),e);
//		}
	}

	@Override
	public int compareTo(IUserAgentPlugin o) {
		return 0;
	}
	
	@Override
	public String getSetingURL() {
		return "/portal/core/uimeta.um?pageId=agentmgr";
	}
}
