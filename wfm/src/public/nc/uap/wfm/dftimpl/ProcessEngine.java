package nc.uap.wfm.dftimpl;
import nc.uap.wfm.engine.IFlowRequest;
import nc.uap.wfm.engine.IFlowResponse;
import nc.uap.wfm.engine.IProcessEngine;
import nc.uap.wfm.filter.FilterChain;
public class ProcessEngine implements IProcessEngine {
	public void exe(IFlowRequest reqeust, IFlowResponse response) {
		new FilterChain().doFilter(reqeust, response);
	}
}
