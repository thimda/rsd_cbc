package nc.uap.wfm.agent;
import java.util.List;
/**
 * 用户代理人查询辅助类
 * 
 * @author licza
 * 
 */
public class UserAgentHelper {
	/**
	 * 查找用户代理人
	 * 
	 * @param ctx
	 */
	public void doFind(AgentContext ctx) {
		List<IUserAgentPlugin> agentPlugins = getPlugins();
		AgentChain chain = new AgentChain();
		if (agentPlugins == null) {
			return;
		}
		for (int k = 0; k < agentPlugins.size(); k++) {
			/**
			 * 链未被释放
			 */
			if (!chain.isBreak())
				agentPlugins.get(k).doFind(ctx, chain);
		}
		/**
		 * 保证链释放
		 */
		chain.doFind(ctx);
	}
	/**
	 * 获得插件们
	 * 
	 * @return 实例化的插件集合
	 */
	public List<IUserAgentPlugin> getPlugins() {
		// /**
		// * 实例化的插件
		// */
		// List<IUserAgentPlugin> agentPlugin = new
		// ArrayList<IUserAgentPlugin>();
		// /**
		// * 获得插件定义
		// */
		// List<PtExtension> exs =
		// PluginManager.newIns().getExtensions(IUserAgentPlugin.PID);
		// if (!PtUtil.isNull(exs)) {
		// for (PtExtension ex : exs) {
		// try {
		// IUserAgentPlugin i = ex.newInstance(IUserAgentPlugin.class);
		// agentPlugin.add(i);
		// } catch (PortalServiceException e) {
		// LfwLogger.error("实例化代理人插件失败,插件ID:" + ex.getId(), e);
		// }
		// }
		// }
		// /**
		// * 对插件排序
		// */
		// Collections.sort(agentPlugin);
		// return agentPlugin;
		return null;
	}
}
