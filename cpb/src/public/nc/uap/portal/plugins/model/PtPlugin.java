package nc.uap.portal.plugins.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 扩展定义
 * 
 * @author licza
 * 
 */
@XmlRootElement(name = "plugin")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PtPlugin", propOrder = { "id", "name", "version","provider", "extensionPointList" })
public class PtPlugin implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4549979218827360028L;
	/** 编号 **/
	@XmlAttribute
	protected String id;
	/** 名称 **/
	@XmlAttribute
	protected String name;
	/** 版本 **/
	@XmlAttribute
	protected String version;
	/*** 作者 */
	@XmlAttribute
	protected String provider; 
	/**
	 * 扩展点
	 */
	@XmlElement(name = "extension-point")
	protected List<ExPoint> extensionPointList = new ArrayList<ExPoint>();
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

 
	public List<ExPoint> getExtensionPointList() {
		return extensionPointList;
	}

	public void setExtensionPointList(List<ExPoint> extensionPointList) {
		this.extensionPointList = extensionPointList;
	} 

	public void addPtExtensionPoint(ExPoint pt) {
		extensionPointList.add(pt);
	}
}
