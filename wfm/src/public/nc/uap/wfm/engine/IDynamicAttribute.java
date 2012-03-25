package nc.uap.wfm.engine;
public interface IDynamicAttribute {
	Object getAttribute(Object key);
	void setAttribute(Object key, Object obj);
}
