package nc.uap.wfm.agent;
import java.util.List;
/**
 * �û������˲�ѯ������
 * 
 * @author licza
 * 
 */
public class UserAgentHelper {
	/**
	 * �����û�������
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
			 * ��δ���ͷ�
			 */
			if (!chain.isBreak())
				agentPlugins.get(k).doFind(ctx, chain);
		}
		/**
		 * ��֤���ͷ�
		 */
		chain.doFind(ctx);
	}
	/**
	 * ��ò����
	 * 
	 * @return ʵ�����Ĳ������
	 */
	public List<IUserAgentPlugin> getPlugins() {
		// /**
		// * ʵ�����Ĳ��
		// */
		// List<IUserAgentPlugin> agentPlugin = new
		// ArrayList<IUserAgentPlugin>();
		// /**
		// * ��ò������
		// */
		// List<PtExtension> exs =
		// PluginManager.newIns().getExtensions(IUserAgentPlugin.PID);
		// if (!PtUtil.isNull(exs)) {
		// for (PtExtension ex : exs) {
		// try {
		// IUserAgentPlugin i = ex.newInstance(IUserAgentPlugin.class);
		// agentPlugin.add(i);
		// } catch (PortalServiceException e) {
		// LfwLogger.error("ʵ���������˲��ʧ��,���ID:" + ex.getId(), e);
		// }
		// }
		// }
		// /**
		// * �Բ������
		// */
		// Collections.sort(agentPlugin);
		// return agentPlugin;
		return null;
	}
}
