package nc.uap.wfm.server;
import nc.uap.wfm.cmd.BackTaskCmd;
import nc.uap.wfm.cmd.CreateAddSignTaskCmd;
import nc.uap.wfm.cmd.CreateBeforeAddSignTaskCmd;
import nc.uap.wfm.cmd.CreateDeliverTaskCmd;
import nc.uap.wfm.cmd.InterimTaskCmd;
import nc.uap.wfm.cmd.NextTaskCmd;
import nc.uap.wfm.cmd.RejectTaskCmd;
import nc.uap.wfm.cmd.RestartAddSignTaskCmd;
import nc.uap.wfm.cmd.RetractTaskCmd;
import nc.uap.wfm.cmd.SignTaskCmd;
import nc.uap.wfm.cmd.StartProInsCmd;
import nc.uap.wfm.cmd.StopAddSignTaskCmd;
import nc.uap.wfm.cmd.TransmitTaskCmd;
import nc.uap.wfm.context.AfterAddSignTaskInfoCtx;
import nc.uap.wfm.context.BackTaskInfoCtx;
import nc.uap.wfm.context.BeforeAddSignTaskInfoCtx;
import nc.uap.wfm.context.DeliverInfoCtx;
import nc.uap.wfm.context.InterimTaskInfoCtx;
import nc.uap.wfm.context.NextTaskInfoCtx;
import nc.uap.wfm.context.ReStartAddSignTaskInfoCtx;
import nc.uap.wfm.context.RejectTaskInfoCtx;
import nc.uap.wfm.context.RetractTaskInfoCtx;
import nc.uap.wfm.context.SignTaskInfoCtx;
import nc.uap.wfm.context.StopAddSignTaskInfoCtx;
import nc.uap.wfm.context.TransmitTaskInfoCtx;
import nc.uap.wfm.context.UnStartProInsInfoCtx;
import nc.uap.wfm.context.WfmFlowInfoCtx;
import nc.uap.wfm.engine.IFlowRequest;
import nc.uap.wfm.engine.IFlowResponse;
public class CommandFeatch {
	private IFlowRequest request;
	private IFlowResponse response;
	public CommandFeatch(IFlowRequest request, IFlowResponse response) {
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
	public String getCommandClazz() {
		String commandClazz = null;
		WfmFlowInfoCtx flowInfoCtx = request.getFlowInfoCtx();
		if (flowInfoCtx instanceof UnStartProInsInfoCtx) {// Á÷³ÌÆô¶¯
			commandClazz = StartProInsCmd.class.getName();
		} else if (flowInfoCtx instanceof NextTaskInfoCtx) {
			commandClazz = NextTaskCmd.class.getName();
		} else if (flowInfoCtx instanceof RejectTaskInfoCtx) {
			commandClazz = RejectTaskCmd.class.getName();
		} else if (flowInfoCtx instanceof DeliverInfoCtx) {
			commandClazz = CreateDeliverTaskCmd.class.getName();
		} else if (flowInfoCtx instanceof TransmitTaskInfoCtx) {
			commandClazz = TransmitTaskCmd.class.getName();
		} else if (flowInfoCtx instanceof StopAddSignTaskInfoCtx) {
			commandClazz = StopAddSignTaskCmd.class.getName();
		} else if (flowInfoCtx instanceof ReStartAddSignTaskInfoCtx) {
			commandClazz = RestartAddSignTaskCmd.class.getName();
		} else if (flowInfoCtx instanceof BackTaskInfoCtx) {
			commandClazz = BackTaskCmd.class.getName();
		} else if (flowInfoCtx instanceof RetractTaskInfoCtx) {
			commandClazz = RetractTaskCmd.class.getName();
		} else if (flowInfoCtx instanceof SignTaskInfoCtx) {
			commandClazz = SignTaskCmd.class.getName();
		} else if (flowInfoCtx instanceof InterimTaskInfoCtx) {
			commandClazz = InterimTaskCmd.class.getName();
		} else if (flowInfoCtx instanceof AfterAddSignTaskInfoCtx) {
			commandClazz = CreateAddSignTaskCmd.class.getName();
		} else if (flowInfoCtx instanceof BeforeAddSignTaskInfoCtx) {
			commandClazz = CreateBeforeAddSignTaskCmd.class.getName();
		}
		return commandClazz;
	}
}
