package nc.uap.wfm.observer;
import java.util.Observable;
import java.util.Observer;
import nc.bs.framework.common.NCLocator;
import nc.bs.logging.Logger;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.itf.IWfmHumActInsBill;
import nc.uap.wfm.itf.IWfmProInsBill;
import nc.uap.wfm.itf.IWfmTaskBill;
import nc.uap.wfm.model.HumActIns;
import nc.uap.wfm.model.ProIns;
import nc.uap.wfm.model.Task;
public class AsynDataBase implements Observer {
	private static AsynDataBase instance = null;
	/**
	 * 所有的目标对象共有一个观察者实例
	 * @return
	 */
	synchronized public static AsynDataBase getInstance() {
		if (instance != null)
			return instance;
		instance = new AsynDataBase();
		return instance;
	}
	private AsynDataBase() {}
	/**
	 * 数据变更后同步信息到数据库
	 */
	public void update(Observable o, Object arg) {
		try {
			if (o instanceof ProIns) {
				ProIns proIns = (ProIns) o;
				IWfmProInsBill proInsService = NCLocator.getInstance().lookup(IWfmProInsBill.class);
				proIns = proInsService.asynProIns(proIns);
				return;
			}
			if (o instanceof HumActIns) {
				HumActIns humActIns = (HumActIns) o;
				IWfmHumActInsBill humActInsBill = NCLocator.getInstance().lookup(IWfmHumActInsBill.class);
				humActIns = humActInsBill.asynHumActIns(humActIns);
				return;
			}
			if (o instanceof Task) {
				Task task = (Task) o;
				IWfmTaskBill taskBill = NCLocator.getInstance().lookup(IWfmTaskBill.class);
				taskBill.asynTask(task);
				return;
			}
		} catch (WfmServiceException e) {
			e.printStackTrace();
			Logger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage(), e);
		}
	}
}
