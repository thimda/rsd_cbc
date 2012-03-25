package nc.uap.wfm.contanier;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.Hashtable;
import nc.uap.wfm.model.ProDef;
public class ProDefsCache {
	static private ProDefsCache cache;// һ��Cacheʵ��
	private static final String seperator = "_";
	private Hashtable<String, ProDefSoftReference> proDefRefs;// ����Chche���ݵĴ洢
	private ReferenceQueue<ProDef> q;// ����Reference�Ķ���
	// �̳�SoftReference��ʹ��ÿһ��ʵ�������п�ʶ��ı�ʶ��
	private class ProDefSoftReference extends SoftReference<ProDef> {
		private String _key = "";
		public ProDefSoftReference(ProDef proDef, ReferenceQueue<ProDef> q) {
			super(proDef, q);
			_key = proDef.getPk_prodef() + seperator + proDef.getId();
		}
	}
	// ����һ��������ʵ��
	private ProDefsCache() {
		proDefRefs = new Hashtable<String, ProDefSoftReference>();
		q = new ReferenceQueue<ProDef>();
	}
	// ȡ�û�����ʵ��
	public static ProDefsCache getInstance() {
		if (cache == null) {
			cache = new ProDefsCache();
		}
		return cache;
	}
	// �������õķ�ʽ��һ��Employee�����ʵ���������ò����������
	private void cacheEmployee(ProDef proDef) {
		cleanCache();// �����������
		ProDefSoftReference ref = new ProDefSoftReference(proDef, q);
		proDefRefs.put(proDef.getPk_prodef() + proDef.getId(), ref);
	}
	// ������ָ����ID�ţ����»�ȡ��ӦEmployee�����ʵ��
	public ProDef getEmployee(String proDefPk) {
		ProDef proDef = null;
		// �������Ƿ��и�Employeeʵ���������ã�����У�����������ȡ�á�
		if (proDefRefs.containsKey(proDefPk)) {
			ProDefSoftReference ref = (ProDefSoftReference) proDefRefs.get(proDefPk);
			proDef = (ProDef) ref.get();
		}
		// ���û�������ã����ߴ��������еõ���ʵ����null�����¹���һ��ʵ����
		// �����������½�ʵ����������
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
	// ���Cache�ڵ�ȫ������
	public void clearCache() {
		cleanCache();
		proDefRefs.clear();
		System.gc();
		System.runFinalization();
	}
}
