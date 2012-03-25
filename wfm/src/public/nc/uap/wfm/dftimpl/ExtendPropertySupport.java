package nc.uap.wfm.dftimpl;
import java.util.HashMap;
import java.util.Map;
import nc.uap.wfm.engine.IDynamicAttribute;
import org.w3c.dom.Element;
public class ExtendPropertySupport implements IDynamicAttribute {
	private Map<Object, Object> propMap = new HashMap<Object, Object>();
	protected void add2Property(Object key, Object value) {
		propMap.put(key, value);
	}
	protected void add2Property(Object key, Element ele) {
		propMap.put(key, ele);
	}
	protected void set2Property(Object key, Object value) {
		propMap.put(key, value);
	}
	protected Object get2Property(Object key) {
		return propMap.get(key);
	}
	public Object getAttribute(Object key) {
		return this.get2Property(key);
	}
	public void setAttribute(Object key, Object value) {
		this.add2Property(key, value);
	}
}
