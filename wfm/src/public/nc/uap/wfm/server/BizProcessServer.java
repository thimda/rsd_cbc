package nc.uap.wfm.server;
import java.util.ArrayList;
import java.util.List;
import nc.bs.framework.common.NCLocator;
import nc.bs.framework.execute.Executor;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.wfm.contanier.ProDefsContainer;
import nc.uap.wfm.context.WfmFlowInfoCtx;
import nc.uap.wfm.dftimpl.BpmnFlowRequest;
import nc.uap.wfm.dftimpl.BpmnFlowResponse;
import nc.uap.wfm.engine.IFlowRequest;
import nc.uap.wfm.engine.IFlowResponse;
import nc.uap.wfm.engine.IProcessEngine;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.facility.WfmServiceFacility;
import nc.uap.wfm.filter.BpmnFilter1;
import nc.uap.wfm.filter.BpmnFilter2;
import nc.uap.wfm.filter.BpmnFlowFilter;
import nc.uap.wfm.filter.IFilter;
import nc.uap.wfm.shceduler.TaskSheduler;
import nc.uap.wfm.vo.WfmFormInfoCtx;
import nc.uap.wfm.vo.WfmProdefVO;
public class BizProcessServer implements IProcessServer {
	public static BizProcessServer instance = null;
	public static WfmServiceFacility serviceFactory = new WfmServiceFacility();
	public static List<IFilter> filters = new ArrayList<IFilter>();;
	synchronized public static BizProcessServer getInstance() {
		if (instance == null) {
			instance = new BizProcessServer();
		}
		return instance;
	}
	public void deploy() {
		WfmProdefVO[] proDefVos = null;
		try {
			proDefVos = WfmServiceFacility.getProDefQry().getAllProDef();
		} catch (WfmServiceException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
		if (proDefVos == null) {
			return;
		}
		for (int i = 0; i < proDefVos.length; i++) {
			ProDefsContainer.getProDef(proDefVos[i]);
		}
	}
	public void destory() {
		ProDefsContainer.getProDefs().clear();
	}
	public List<IFilter> getFilters() {
		filters.clear();
		filters.add(new BpmnFilter1());
		filters.add(new BpmnFilter2());
		filters.add(new BpmnFlowFilter());
		return filters;
	}
	public void monitor() {
		new Executor(new TaskSheduler()).start();
	}
	public void start() throws Exception {
		this.deploy();
		this.monitor();
	}
	public void stop() throws Exception {
		this.destory();
	}
	public static IFlowRequest createFlowRequest(WfmFormInfoCtx formInfoCtx, WfmFlowInfoCtx flowInfoCtx) {
		IFlowRequest request = new BpmnFlowRequest();
		request.setFlowInfoCtx(flowInfoCtx);
		request.setFormInfoCtx(formInfoCtx);
		return request;
	}
	public static IFlowResponse createFlowResponse() {
		IFlowResponse response = new BpmnFlowResponse();
		return response;
	}
	public static void exe(IFlowRequest request, IFlowResponse response) {
		NCLocator.getInstance().lookup(IProcessEngine.class).exe(request, response);
	}
}
