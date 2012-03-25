package nc.uap.portal.plugins.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 扩展点
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PtExtensionPoint", propOrder = { "classname", "title", "point" ,"extensionList"})
public class ExPoint implements Serializable {
	
	private static final long serialVersionUID = 8814481639614547685L;
	
	/** 扩展点唯一标识 **/
	@XmlAttribute
	protected String point;
	/** 扩展点名 **/
	@XmlAttribute
	protected String title;
	/** 实现扩展类名 **/
	@XmlAttribute
	protected String classname;
	
	@XmlElement(name = "extension")
	protected List<Extension> extensionList = new ArrayList<Extension>();
	public String getPoint() {
		return point;
	}
	public void setPoint(String point) {
		this.point = point;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getClassname() {
		return classname;
	}
	public void setClassname(String classname) {
		this.classname = classname;
	}
	public List<Extension> getExtensionList() {
		return extensionList;
	}
	public void setExtensionList(List<Extension> extensionList) {
		this.extensionList = extensionList;
	}
	public void addPtExtension(Extension pt) {
		extensionList.add(pt);
	}
	public ExPoint(){
		
	}
	/**
	 * 从VO获得信息
	 * @param exp
	 */
	public ExPoint(PtExtensionPoint exp) {
		this.point = exp.getPoint();
		this.title = exp.getTitle();
		this.classname = exp.getClassname();
	}
	
	
}
