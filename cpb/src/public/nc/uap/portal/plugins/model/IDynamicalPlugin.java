package nc.uap.portal.plugins.model;

/**
 * ��̬���
 * <pre>PluginManager�ڲ����ʼ����ʱ��,���ֲ��ʵ����IDynamicalPlugin�ӿ�,�����init����,��Plugin�����õ�����,id,������Ϣ��̬ע��</pre>
 * @author licza
 *
 */
public interface IDynamicalPlugin {
	
	/**
	 * ��ʼ�����
	 * @param id
	 * @param i18nname
	 * @param title
	 */
	void init(String id,String i18nname,String title);
	String getId();
	String getI18nname();
	String getTitle();
}
