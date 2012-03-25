package nc.uap.wfm.filter;
import nc.uap.wfm.engine.IFlowRequest;
import nc.uap.wfm.engine.IFlowResponse;
public interface IFilter {
	public void doFilter(IFlowRequest request, IFlowResponse response, FilterChain chain);
}