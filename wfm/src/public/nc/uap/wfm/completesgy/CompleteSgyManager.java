package nc.uap.wfm.completesgy;
import nc.uap.wfm.model.CompleteStrategy;
import nc.uap.wfm.model.HumActIns;
/**
 * 完成策略管理器
 * @author tianchw
 *
 */
public class CompleteSgyManager implements ICompleteSgy {
	private static CompleteSgyManager instance = null;
	private ICompleteSgyService sgyService = null;
	private void setSgyService(ICompleteSgyService sgyService) {
		this.sgyService = sgyService;
	}
	private CompleteSgyManager() {};
	synchronized public static CompleteSgyManager getInstance() {
		if (instance != null)
			return instance;
		else {
			instance = new CompleteSgyManager();
		}
		return instance;
	}
	public boolean isNotComplete(HumActIns humActIns, Integer count) {
		this.setService(this.getSgy(humActIns));
		if (sgyService == null) {
			return true;
		}
		boolean flag = sgyService.isNotComplete(humActIns, count);
		return flag;
	}
	private int getSgy(HumActIns humActIns) {
		CompleteStrategy sgy = humActIns.getHumAct().getCompleteStrategy();
		if (sgy != null)
			return sgy.getStrategyType();
		else
			return 0;
	}
	private void setService(int sgy) {
		switch (sgy) {
		case CompleteSgy_Occupy:
			this.setSgyService(new OccupySgy());
			break;
		case CompleteSgy_Countersign:
			this.setSgyService(new CountsignSgy());
			break;
		case CompleteSgy_ByCount:
			this.setSgyService(new ByCountSgy());
			break;
		case CompleteSgy_ByPercent:
			this.setSgyService(new ByPercentSgy());
			break;
		}
	}
}
