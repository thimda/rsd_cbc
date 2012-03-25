package nc.uap.portal.plugins.impl;

import nc.uap.portal.plugins.model.IDynamicalPlugin;

/**
 * 动态插件实现
 * 
 * @author licza
 * 
 */
public class DynamicalPluginImpl implements IDynamicalPlugin {

	protected String id;
	protected String i18nname;
	protected String title;

	@Override
	public void init(String id, String i18nname, String title) {
		this.id = id;
		this.i18nname = i18nname;
		this.title = title;
	}

	public String getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	@Override
	public String getI18nname() {
		return i18nname;
	}
}
