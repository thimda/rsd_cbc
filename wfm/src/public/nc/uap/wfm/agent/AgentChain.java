package nc.uap.wfm.agent;

/**
 * 代理链
 * 
 * @author licza
 * 
 */
public class AgentChain {
	/** 链是否断开 **/
	private boolean release = false;

	public void doFind(AgentContext ctx) {
		if (ctx.getAgent() == null || ctx.equals(""))
			ctx.setAgent(ctx.getUser());
		release();
	}

	/**
	 * 返回链是否断开
	 * 
	 * @return
	 */
	public boolean isBreak() {
		return release;
	}

	/**
	 * 释放链
	 **/
	public void release() {
		if(!release)
			release = true;
	}
	/**
	 * 重置链
	 */
	public void reset(){
		release = false;
	}
}
