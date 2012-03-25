package nc.uap.wfm.filter;
import java.util.List;
import nc.uap.wfm.engine.IFlowRequest;
import nc.uap.wfm.engine.IFlowResponse;
import nc.uap.wfm.server.BizProcessServer;
public class FilterChain {
	private List<IFilter> filters = BizProcessServer.getInstance().getFilters();
	private int index = 0;
	public void addFilter(IFilter f) {
		filters.add(f);
	}
	public void doFilter(IFlowRequest request, IFlowResponse response) {
		if (index == filters.size()) {
			return;
		}
		IFilter filter = filters.get(index);
		index++;
		filter.doFilter(request, response, this);
	}
}
