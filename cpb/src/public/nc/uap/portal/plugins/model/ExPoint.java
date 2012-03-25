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
 * ��չ��
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PtExtensionPoint", propOrder = { "classname", "title", "point" ,"extensionList"})
public class ExPoint implements Serializable {
	
	private static final long serialVersionUID = 8814481639614547685L;
	
	/** ��չ��Ψһ��ʶ **/
	@XmlAttribute
	protected String point;
	/** ��չ���� **/
	@XmlAttribute
	protected String title;
	/** ʵ����չ���� **/
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
	 * ��VO�����Ϣ
	 * @param exp
	 */
	public ExPoint(PtExtensionPoint exp) {
		this.point = exp.getPoint();
		this.title = exp.getTitle();
		this.classname = exp.getClassname();
	}
	
	
}
