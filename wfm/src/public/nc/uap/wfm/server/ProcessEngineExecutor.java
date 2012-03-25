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
		 * ��ȡ��ǰ����������Ϣ
		 */
		PwfmContext.BpmnSession currSession = PwfmContext.getCurrentBpmnSession();
		/**
		 * �������̵ĵ�ǰ������
		 */
		setBpmnCtx();
		/**
		 * ���浥����Ϣ
		 */
		saveFormInfoCtx();
		/**
		 * �������������������
		 */
		try {
			new CommandService().execute((ICommand<Void>) WfmClassUtil.loadClass(new CommandFeatch(request, response).getCommandClazz()));
		} catch (WfmServiceException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
		/**
		 * ���µ�����Ϣ
		 */
		updateFormInoCtx();
		/**
		 * �������̵ĵ�ǰ������
		 */
		resetBpmnCtx();
		/**
		 * ��ԭ��������Ϣ
		 */
		PwfmContext.seBpmnSession(currSession);
	}
	/**
	 * ���浥����Ϣ
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
	 * ���µ�����Ϣ
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
	 * ���������������������Ϣ
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
	 * ����������Ϣ�ÿ�
	 */
	protected void resetBpmnCtx() {
		PwfmContext.getCurrentBpmnSession().reset();
	}
}
