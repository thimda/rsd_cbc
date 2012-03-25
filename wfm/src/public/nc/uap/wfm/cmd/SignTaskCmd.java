package nc.uap.wfm.cmd;
import nc.uap.wfm.context.WfmFlowInfoCtx;
import nc.uap.wfm.context.PwfmContext;
import nc.uap.wfm.context.SignTaskInfoCtx;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.model.Task;
public class SignTaskCmd extends AbstractCommand implements ICommand<Void> {
	public SignTaskCmd() {
		super();
	}
	public Void execute() throws WfmServiceException {
		Task task = PwfmContext.getCurrentBpmnSession().getTask();
		WfmFlowInfoCtx flwInfoCtx = PwfmContext.getCurrentBpmnSession().getFlwInfoCtx();
		if (flwInfoCtx instanceof SignTaskInfoCtx) {
			SignTaskInfoCtx signCtx = (SignTaskInfoCtx) flwInfoCtx;
			String signOpinion = signCtx.getSignOpinion();
			task.setOpinion(signOpinion);
			task.asyn();
		}
		return null;
	}
}
