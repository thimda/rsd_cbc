package nc.uap.wfm.server;
import nc.uap.wfm.filter.FilterChain;
public class FilterExecutor {
	public void exe() {
		new FilterChain().doFilter(null, null);
	}
}
