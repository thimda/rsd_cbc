package nc.uap.wfm.agent;

/**
 * ������
 * 
 * @author licza
 * 
 */
public class AgentChain {
	/** ���Ƿ�Ͽ� **/
	private boolean release = false;

	public void doFind(AgentContext ctx) {
		if (ctx.getAgent() == null || ctx.equals(""))
			ctx.setAgent(ctx.getUser());
		release();
	}

	/**
	 * �������Ƿ�Ͽ�
	 * 
	 * @return
	 */
	public boolean isBreak() {
		return release;
	}

	/**
	 * �ͷ���
	 **/
	public void release() {
		if(!release)
			release = true;
	}
	/**
	 * ������
	 */
	public void reset(){
		release = false;
	}
}
