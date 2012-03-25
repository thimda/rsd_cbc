package nc.uap.wfm.rejectsgy;
import java.util.List;
import nc.jdbc.framework.processor.BeanListProcessor;
import nc.uap.cpb.org.vos.CpUserVO;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.wfm.model.HumAct;
import nc.uap.wfm.model.HumActIns;
import nc.uap.wfm.model.RejectStrategy;
import nc.uap.wfm.model.Task;
public class RejectSgyManager implements IRejectSgy {
	private static RejectSgyManager instance = null;
	private RejectSgyService sgyService = null;
	private void setSgyService(RejectSgyService sgyService) {
		this.sgyService = sgyService;
	}
	private RejectSgyManager() {};
	synchronized public static RejectSgyManager getInstance() {
		if (instance != null)
			return instance;
		else {
			instance = new RejectSgyManager();
		}
		return instance;
	}
	public HumAct[] getRejectRange(Task task) {
		HumActIns humActIns = task.getHumActIns();
		HumAct humAct = humActIns.getHumAct();
		this.setService(this.getSgy(humAct));
		if (sgyService == null) {
			return null;
		}
		return sgyService.getRejectRange(task);
	}
	public boolean isNotPermit(Task task) {
		HumActIns humActIns = task.getHumActIns();
		HumAct humAct = humActIns.getHumAct();
		this.setService(this.getSgy(humAct));
		if (sgyService != null) {
			return sgyService.isNotPermit(humAct);
		} else {
			return false;
		}
	}
	@SuppressWarnings("unchecked") public CpUserVO[] getRejectUsersByTaskAndHumAct(Task task, HumAct rejectHumAct) {
		String proInsPk = task.getProIns().getPk_proins();
		String rejectHumActId = rejectHumAct.getId();
		String sql = "select * from cp_user where cuserid in (select pk_owner from wfm_task  where finishtype not in('" + Task.FinishType_Tramsmit + "') and  createtype in ('"
				+ Task.CreateType_Normal + "' ,'" + Task.CreateType_Reject + "','" + Task.CreateType_Tramsmit + "') and  pk_humactins = (select pk_humactins from wfm_humactins   where humact_id = '"
				+ rejectHumActId + "' and" + " pk_proins = '" + proInsPk + "' and  ts = (select max(ts)  from wfm_humactins   where humact_id = '" + rejectHumActId + "' and   pk_proins = '"
				+ proInsPk + "')))";
		PtBaseDAO dao = new PtBaseDAO();
		try {
			List<CpUserVO> userVos = (List<CpUserVO>) dao.executeQuery(sql, new BeanListProcessor(CpUserVO.class));
			return userVos.toArray(new CpUserVO[0]);
		} catch (Exception e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
	}
	private int getSgy(HumAct humAct) {
		RejectStrategy sgy = humAct.getRejectStrategy();
		if (sgy != null)
			return sgy.getStrategyType();
		else
			return 0;
	}
	private void setService(int sgy) {
		switch (sgy) {
		case RejectSgy_PreviousHumAct:
			this.setSgyService(new PreviousHumAct());
			break;
		case RejectSgy_AppointHumAct:
			this.setSgyService(new AppointHumActSgy());
			break;
		case RejectSgy_AllHumAct:
			this.setSgyService(new AllHumAct());
			break;
		case RejectSgy_StartHumAct:
			this.setSgyService(new StartHumAct());
			break;
		}
	}
}
