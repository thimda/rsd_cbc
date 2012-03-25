package nc.uap.wfm.cmd;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.wfm.context.HumActInfoEngCtx;
import nc.uap.wfm.context.PwfmContext;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.handler.PortAndEdgeHandler;
import nc.uap.wfm.model.HumAct;
import nc.uap.wfm.model.HumActIns;
import nc.uap.wfm.model.IGraphElement;
import nc.uap.wfm.model.IPort;
import nc.uap.wfm.model.ProDef;
import nc.uap.wfm.model.ProIns;
import nc.uap.wfm.model.Task;
/**
 * 触发流程走向下一步命令
 * 
 * @author tianchw
 * 
 */
public class SignalCmd extends AbstractCommand implements ICommand<HumActIns[]> {
	public SignalCmd() {
		super();
	}
	public HumActIns[] execute() throws WfmServiceException {
		Task task = PwfmContext.getCurrentBpmnSession().getTask();
		/**
		 * 触发流程走向下一步
		 */
		ProIns rootProIns = task.getRootProIns();
		ProIns proIns = task.getProIns();
		HumActIns humActIns = task.getHumActIns();
		HumAct humAct = humActIns.getHumAct();
		IGraphElement[] ges = PortAndEdgeHandler.getOutEdges(humAct);
		HumActInfoEngCtx[] nextInfo = PwfmContext.getCurrentBpmnSession().getNextInfo();
		if (nextInfo == null || nextInfo.length == 0) {
			throw new LfwRuntimeException("无流程下一步信息");
		}
		List<IPort> list = new ArrayList<IPort>();
		int length = nextInfo.length;
		ProDef proDef = PwfmContext.getCurrentBpmnSession().getProDef();
		Map<String, IPort> ports = proDef.getPorts();
		HumActInfoEngCtx ctx = null;
		IPort port = null;
		for (int i = 0; i < length; i++) {
			ctx = nextInfo[i];
			port = ports.get(ctx.getPortId());
			list.add(port);
		}
		signal(proIns, rootProIns, humActIns, list.toArray(new IPort[0]), ges, task);
		return null;
	}
}
