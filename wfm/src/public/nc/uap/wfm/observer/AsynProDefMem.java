package nc.uap.wfm.observer;
public class AsynProDefMem implements java.util.Observer {
	
	private static AsynProDefMem instance = null;
	/**
	 * 所有的目标对象共有一个观察者实例
	 * @return
	 */
	synchronized public static AsynProDefMem getInstance() {
		if (instance != null)
			return instance;
		instance = new AsynProDefMem();
		return instance;
	}
	/**
	 * 私有构造方法，防止构建多个同步器
	 */
	private AsynProDefMem() {}
	@Override
	public void update(java.util.Observable o, Object arg) {
		// ProcessParser parse = ProcessParser.getInstance();
//		if (o instanceof ProcessDefinition) {
//			String filePath = Thread.currentThread().getContextClassLoader().getResource((String) arg).toString();
//			try {
//				ProcessDefinition proDef = parse.parse(filePath);
//				ProDefsContext.add(proDef);
//			} catch (PwfmBusinessException e) {
//				Logger.error(e.getMessage(),e);
//				e.printStackTrace();
//			}
//		}
	}
}
