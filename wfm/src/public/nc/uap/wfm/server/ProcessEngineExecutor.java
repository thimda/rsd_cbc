package nc.uap.wfm.server;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.wfm.cmd.CommandService;
import nc.uap.wfm.cmd.ICommand;
import nc.uap.wfm.context.PwfmContext;
import nc.uap.wfm.context.WfmFlowInfoCtx;
import nc.uap.wfm.dftimpl.DefaultFormOper;
import nc.uap.wfm.engine.IFlowRequest;
import nc.uap.wfm.engine.IFlowResponse;
import nc.uap.wfm.engine.IWfmFormOper;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.model.ProDef;
import nc.uap.wfm.utils.WfmClassUtil;
import nc.uap.wfm.vo.WfmFlwTypeVO;
import nc.uap.wfm.vo.WfmFormInfoCtx;
public class ProcessEngineExecutor {
	private IFlowRequest request;
	private IFlowResponse response;
	public ProcessEngineExecutor(IFlowRequest request, IFlowResponse response) {
		super();
		this.request = request;
		this.response = response;
	}
	public IFlowRequest getRequest() {
		return request;
	}
	public void setRequest(IFlowRequest request) {
		this.request = request;
	}
	public IFlowResponse getResponse() {
		return response;
	}
	public void setResponse(IFlowResponse response) {
		this.response = response;
	}
	@SuppressWarnings("unchecked") public void exe() {
		/**
		 * 提取当前的上下文信息
		 */
		PwfmContext.BpmnSession currSession = PwfmContext.getCurrentBpmnSession();
		/**
		 * 设置流程的当前上下文
		 */
		setBpmnCtx();
		/**
		 * 保存单据信息
		 */
		saveFormInfoCtx();
		/**
		 * 调用流程引擎的命令链
		 */
		try {
			new CommandService().execute((ICommand<Void>) WfmClassUtil.loadClass(new CommandFeatch(request, response).getCommandClazz()));
		} catch (WfmServiceException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
		/**
		 * 更新单据信息
		 */
		updateFormInoCtx();
		/**
		 * 重置流程的当前上下文
		 */
		resetBpmnCtx();
		/**
		 * 还原上下文信息
		 */
		PwfmContext.seBpmnSession(currSession);
	}
	/**
	 * 保存单据信息
	 */
	protected void saveFormInfoCtx() {
		WfmFlowInfoCtx flowInoCtx = request.getFlowInfoCtx();
		WfmFormInfoCtx formInoCtx = request.getFormInfoCtx();
		ProDef proDef = flowInoCtx.getProDef();
		WfmFlwTypeVO flwTypeVo = proDef.getFlwtype();
		String serverClazz = flwTypeVo.getServerclass();
		if (serverClazz == null || serverClazz.trim().length() == 0) {
			serverClazz = DefaultFormOper.class.getName();
		}
		IWfmFormOper formOper = (IWfmFormOper) WfmClassUtil.loadClass(serverClazz);
		formInoCtx=formOper.save(formInoCtx, flowInoCtx);
		request.setFormInfoCtx(formInoCtx);
		PwfmContext.getCurrentBpmnSession().setFormVo(formInoCtx);
		
	}
	/**
	 * 更新单据信息
	 */
	protected void updateFormInoCtx() {
		WfmFlowInfoCtx flowInoCtx = request.getFlowInfoCtx();
		WfmFormInfoCtx formInoCtx = request.getFormInfoCtx();
		ProDef proDef = flowInoCtx.getProDef();
		WfmFlwTypeVO flwTypeVo = proDef.getFlwtype();
		String serverClazz = flwTypeVo.getServerclass();
		if (serverClazz == null || serverClazz.trim().length() == 0) {
			serverClazz = DefaultFormOper.class.getName();
		}
		IWfmFormOper formOper = (IWfmFormOper) WfmClassUtil.loadClass(serverClazz);
		formOper.update(formInoCtx, flowInoCtx);
	}
	/**
	 * 设置流程引擎的上下文信息
	 */
	protected void setBpmnCtx() {
		PwfmContext.BpmnSession bpmnSession = new PwfmContext().new BpmnSession();
		PwfmContext.seBpmnSession(bpmnSession);
		WfmFlowInfoCtx flowInfoCtx = (WfmFlowInfoCtx) request.getFlowInfoCtx();
		flowInfoCtx.check();
		WfmFormInfoCtx formInfoCtx = (WfmFormInfoCtx) request.getFormInfoCtx();
		bpmnSession.setRequest(request);
		bpmnSession.setResponse(response);
		bpmnSession.setFlwInfoCtx(flowInfoCtx);
		bpmnSession.setFormVo(formInfoCtx);
		bpmnSession.setCurrentUserPk(flowInfoCtx.getCntUserPk());
	}
	/**
	 * 把上下文信息置空
	 */
	protected void resetBpmnCtx() {
		PwfmContext.getCurrentBpmnSession().reset();
	}
}
