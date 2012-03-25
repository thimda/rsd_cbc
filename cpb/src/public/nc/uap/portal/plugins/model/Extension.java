package nc.uap.portal.plugins.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * 扩展
 * @author licza
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Extension", propOrder = { "classname", "title", "id", "i18nname", "isactive" })
public class Extension implements Serializable {
	
	private static final long serialVersionUID = -1878982475179428227L;
	
	/** 扩展名 **/
	@XmlAttribute
	protected String id;
	/** 类名 **/
	@XmlAttribute
	protected String classname;
	/** 名称 **/
	@XmlAttribute
	protected String title;
	/** 国际化名称 **/
	@XmlAttribute
	protected String i18nname;
	@XmlAttribute
	private Boolean isactive;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getI18nname() {
		return i18nname;
	}

	public void setI18nname(String i18nname) {
		this.i18nname = i18nname;
	}

	public Boolean getIsactive() {
		return isactive;
	}

	public void setIsactive(Boolean isactive) {
		this.isactive = isactive;
	}
	public Extension(){
		
	}
	
	public Extension(PtExtension ex) {
		this.id = ex.id;
		this.classname = ex.classname;
		this.title = ex.title;
		this.i18nname = ex.i18nname;
		this.isactive = ex.getIsactive() == null ? Boolean.FALSE : ex.getIsactive().booleanValue();
	}

}
