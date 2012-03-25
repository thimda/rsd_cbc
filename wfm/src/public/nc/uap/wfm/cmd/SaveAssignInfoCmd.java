package nc.uap.wfm.cmd;
import java.util.ArrayList;
import java.util.List;
import nc.bs.framework.common.NCLocator;
import nc.uap.wfm.context.HumActInfoEngCtx;
import nc.uap.wfm.context.PwfmContext;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.itf.IWfmAssignActorsBill;
import nc.uap.wfm.vo.WfmAssignActorsVO;
/**
 * 完成任务命令
 * @author tianchw
 *
 */
public class SaveAssignInfoCmd extends AbstractCommand implements ICommand<Void> {
	public SaveAssignInfoCmd() {
		super();
	}
	public Void execute() throws WfmServiceException {
		HumActInfoEngCtx[] nextInfo = PwfmContext.getCurrentBpmnSession().getNextInfo();
		if (nextInfo == null || nextInfo.length == 0) {
			return null;
		}
		HumActInfoEngCtx ctx = null;
		String rootProInsPk = PwfmContext.getCurrentBpmnSession().getProIns().getPk_proins();
		String proDefId = PwfmContext.getCurrentBpmnSession().getProDef().getId();
		List<WfmAssignActorsVO> list = new ArrayList<WfmAssignActorsVO>();
		int outLength = nextInfo.length;
		for (int i = 0; i < outLength; i++) {
			ctx = nextInfo[i];
			String[] userPks = ctx.getUserPks();
			if (userPks == null || userPks.length == 0) {
				continue;
			}
			WfmAssignActorsVO vo = null;
			int innerLength = userPks.length;
			for (int j = 0; j < innerLength; j++) {
				vo = new WfmAssignActorsVO();
				vo.setPk_user(userPks[j]);
				vo.setHumact_id(ctx.getPortId());
				vo.setPk_rootproins(rootProInsPk);
				vo.setProdef_id(proDefId);
				list.add(vo);
			}
		}
		NCLocator.getInstance().lookup(IWfmAssignActorsBill.class).saveAssignActors(list.toArray(new WfmAssignActorsVO[0]));
		return null;
	}
}
