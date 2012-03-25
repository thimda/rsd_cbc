package nc.uap.wfm.contanier;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.Hashtable;
import nc.uap.wfm.model.ProDef;
public class ProDefsCache {
	static private ProDefsCache cache;// 一个Cache实例
	private static final String seperator = "_";
	private Hashtable<String, ProDefSoftReference> proDefRefs;// 用于Chche内容的存储
	private ReferenceQueue<ProDef> q;// 垃圾Reference的队列
	// 继承SoftReference，使得每一个实例都具有可识别的标识。
	private class ProDefSoftReference extends SoftReference<ProDef> {
		private String _key = "";
		public ProDefSoftReference(ProDef proDef, ReferenceQueue<ProDef> q) {
			super(proDef, q);
			_key = proDef.getPk_prodef() + seperator + proDef.getId();
		}
	}
	// 构建一个缓存器实例
	private ProDefsCache() {
		proDefRefs = new Hashtable<String, ProDefSoftReference>();
		q = new ReferenceQueue<ProDef>();
	}
	// 取得缓存器实例
	public static ProDefsCache getInstance() {
		if (cache == null) {
			cache = new ProDefsCache();
		}
		return cache;
	}
	// 以软引用的方式对一个Employee对象的实例进行引用并保存该引用
	private void cacheEmployee(ProDef proDef) {
		cleanCache();// 清除垃圾引用
		ProDefSoftReference ref = new ProDefSoftReference(proDef, q);
		proDefRefs.put(proDef.getPk_prodef() + proDef.getId(), ref);
	}
	// 依据所指定的ID号，重新获取相应Employee对象的实例
	public ProDef getEmployee(String proDefPk) {
		ProDef proDef = null;
		// 缓存中是否有该Employee实例的软引用，如果有，从软引用中取得。
		if (proDefRefs.containsKey(proDefPk)) {
			ProDefSoftReference ref = (ProDefSoftReference) proDefRefs.get(proDefPk);
			proDef = (ProDef) ref.get();
		}
		// 如果没有软引用，或者从软引用中得到的实例是null，重新构建一个实例，
		// 并保存对这个新建实例的软引用
		if (proDef == null) {
			this.cacheEmployee(proDef);
		}
		return proDef;
	}
	private void cleanCache() {
		ProDefSoftReference ref = null;
		while ((ref = (ProDefSoftReference) q.poll()) != null) {
			proDefRefs.remove(ref._key);
		}
	}
	// 清除Cache内的全部内容
	public void clearCache() {
		cleanCache();
		proDefRefs.clear();
		System.gc();
		System.runFinalization();
	}
}
