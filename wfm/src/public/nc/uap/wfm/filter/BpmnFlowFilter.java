package nc.uap.wfm.filter;
import nc.uap.wfm.engine.IFlowRequest;
import nc.uap.wfm.engine.IFlowResponse;
import nc.uap.wfm.server.ProcessEngineExecutor;
public class BpmnFlowFilter implements IFilter {
	public void doFilter(IFlowRequest request, IFlowResponse response, FilterChain chain) {
		new ProcessEngineExecutor(request, response).exe();
		chain.doFilter(request, response);
	}
}
