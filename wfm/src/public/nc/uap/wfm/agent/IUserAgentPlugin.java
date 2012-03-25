package nc.uap.wfm.agent;

/**
 * �û����������ò��
 * 
 * @author licza
 * 
 */
public interface IUserAgentPlugin extends Comparable<IUserAgentPlugin> {
	public static final String PID = "userAgentPlugin";
	
	/**
	 * ���Ҵ���������
	 * @param ctx
	 * @param chain
	 */
	public void doFind(AgentContext ctx, AgentChain chain);
	
	/**
	 * ��ô�����������Ϣ�༭ҳ��
	 * @return
	 */
	public String getSetingURL();
	
	/**
	 * ������ȼ�(ԽС���ȼ�Խ��)
	 * @return ���ȼ�
	 */
	public int compareTo(IUserAgentPlugin o);
}
