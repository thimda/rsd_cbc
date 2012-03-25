package nc.uap.wfm.filter;
import nc.uap.wfm.engine.IFlowRequest;
import nc.uap.wfm.engine.IFlowResponse;
public class BpmnFilter2 implements IFilter {
	public void doFilter(IFlowRequest request, IFlowResponse response, FilterChain chain) {
		System.err.println("filter2=start");
		chain.doFilter(request, response);
		System.err.println("filter2=end");
	}
}
