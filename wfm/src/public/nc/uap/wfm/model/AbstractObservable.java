package nc.uap.wfm.model;
import java.util.Observable;

import nc.uap.wfm.observer.AsynDataBase;
public abstract class AbstractObservable extends Observable {
	/**
	 * ����Ŀ����󣬸�Ŀ���������ͬ����
	 */
	public AbstractObservable() {
		this.addObserver(AsynDataBase.getInstance());
	}
	/**
	 * ͬ����ͬ������
	 */
	public void asyn() {
		setChanged();
		notifyObservers();
	}
	/**
	 * ͬ����ͬ������
	 */
	public void asyn(String args) {
		setChanged();
		notifyObservers(args);
	}
}
