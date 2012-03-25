package nc.uap.wfm.model;
import java.util.Observable;

import nc.uap.wfm.observer.AsynDataBase;
public abstract class AbstractObservable extends Observable {
	/**
	 * 构建目标对象，给目标对象增加同步器
	 */
	public AbstractObservable() {
		this.addObserver(AsynDataBase.getInstance());
	}
	/**
	 * 同步器同步数据
	 */
	public void asyn() {
		setChanged();
		notifyObservers();
	}
	/**
	 * 同步器同步数据
	 */
	public void asyn(String args) {
		setChanged();
		notifyObservers(args);
	}
}
