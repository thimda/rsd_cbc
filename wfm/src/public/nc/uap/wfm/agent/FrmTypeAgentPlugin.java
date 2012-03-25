package nc.uap.wfm.agent;


/**
 * 单据类型代理人查询插件
 * @author licza
 *
 */
public class FrmTypeAgentPlugin implements IUserAgentPlugin{

	@Override
	public int compareTo(IUserAgentPlugin o) {
		return 1;
	}

	@Override
	public void doFind(AgentContext ctx, AgentChain chain) {
//		try {
//			String agent = PortalServiceUtil.getUserAgentQryService().getFrmAgent(ctx.getUser(), ctx.getFrmtype());
//			if(!ctx.getUser().equals(agent)){
//				ctx.setAgent(agent);
//				chain.doFind(ctx);
//			}
//		} catch (PortalServiceException e) {
//			LfwLogger.error(e.getMessage(),e);
//		}
	}

	@Override
	public String getSetingURL() {
		return "/portal/core/uimeta.um?pageId=myagentsetting";
	}
}
