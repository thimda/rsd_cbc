package nc.uap.wfm.agent;

/**
 * 用户代理人设置插件
 * 
 * @author licza
 * 
 */
public interface IUserAgentPlugin extends Comparable<IUserAgentPlugin> {
	public static final String PID = "userAgentPlugin";
	
	/**
	 * 查找代理人设置
	 * @param ctx
	 * @param chain
	 */
	public void doFind(AgentContext ctx, AgentChain chain);
	
	/**
	 * 获得代理人配置信息编辑页面
	 * @return
	 */
	public String getSetingURL();
	
	/**
	 * 插件优先级(越小优先级越高)
	 * @return 优先级
	 */
	public int compareTo(IUserAgentPlugin o);
}
