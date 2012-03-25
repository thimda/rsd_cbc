package nc.uap.wfm.shceduler;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import nc.bs.framework.execute.Executor;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.wfm.engine.IProcessSheduler;
public class TaskSheduler extends Thread {
	private static final long TIME_SPLIT = 1000L;
	private Map<String, Long> circle = new ConcurrentHashMap<String, Long>();
	private Map<String, Long> modifiedSince = new ConcurrentHashMap<String, Long>();
	private Map<String, Tasker> tasker = new ConcurrentHashMap<String, Tasker>();
	private List<IProcessSheduler> list = getPlugins();
	/**
	 * 执行
	 */
	public void doIt() {
		/**
		 * 周期
		 */
		if (list.isEmpty()) {
			return;
		}
		for (IProcessSheduler scl : list) {
			circle.put(scl.getId(), scl.getSeperator());
			tasker.put(scl.getId(), new Tasker(scl));
			modifiedSince.put(scl.getId(), System.currentTimeMillis());
		}
		while (true) {
			try {
				long currentTimeSpan = System.currentTimeMillis();
				if (!tasker.isEmpty()) {
					for (Tasker tk : tasker.values()) {
						String id = tk.getScl().getId();
						if (currentTimeSpan >= modifiedSince.get(id) + circle.get(id)) {
							new Executor(tk).start();
							modifiedSince.put(id, currentTimeSpan);
						}
					}
				}
				Thread.sleep(TIME_SPLIT);
			} catch (InterruptedException e) {
				LfwLogger.error(e.getMessage(), e);
			}
		}
	}
	/**
	 * 获得插件
	 * 
	 * @return 实例化的插件集合
	 */
	public List<IProcessSheduler> getPlugins() {
		/**
		 * 实例化的插件
		 */
		List<IProcessSheduler> agentPlugin = new ArrayList<IProcessSheduler>();
		return agentPlugin;
	}
	public void run() {
		/**
		 * 执行任务调度计划
		 */
		doIt();
	}
}
/**
 * 任务
 * 
 * @author licza
 * 
 */
class Tasker implements Runnable {
	IProcessSheduler scl = null;
	public Tasker(IProcessSheduler scl) {
		this.scl = scl;
	}
	@Override public void run() {
		scl.shceduler();
	}
	public IProcessSheduler getScl() {
		return scl;
	}
}
