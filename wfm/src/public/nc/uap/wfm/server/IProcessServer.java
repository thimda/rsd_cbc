package nc.uap.wfm.server;
public interface IProcessServer extends LifeCycle {
	void destory();
	void deploy();
	void monitor();
}
