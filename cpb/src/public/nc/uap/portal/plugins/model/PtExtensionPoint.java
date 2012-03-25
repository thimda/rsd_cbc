package nc.uap.portal.plugins.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.StringUtils;

import nc.vo.pub.SuperVO;

/**
 * ��չ��
 * 
 * @author licza
 * @since 2010��9��9��14:33:10
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PtExtensionPoint", propOrder = { "classname", "title", "point", "dr", "ts", "pk_extpoint" })
public class PtExtensionPoint extends SuperVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8350649195253142839L;
	/** ��չ��Ψһ��ʶ **/
	@XmlAttribute
	protected String point;
	/** ��չ���� **/
	@XmlAttribute
	protected String title;
	/** ʵ����չ���� **/
	@XmlAttribute
	protected String classname;;

	/** ɾ����־ **/
	private java.lang.Integer dr = 0;
	/** �������¼� **/
	private nc.vo.pub.lang.UFDateTime ts;
	/** ���� **/
	public String pk_extpoint;

	public String getPoint() {
		return point;
	}

	public void setPoint(String id) {
		this.point = id;
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

	public java.lang.Integer getDr() {
		return dr;
	}

	public void setDr(java.lang.Integer dr) {
		this.dr = dr;
	}

	public nc.vo.pub.lang.UFDateTime getTs() {
		return ts;
	}

	public void setTs(nc.vo.pub.lang.UFDateTime ts) {
		this.ts = ts;
	}

	public String getPk_extpoint() {
		return pk_extpoint;
	}

	public void setPk_extpoint(String pk_extpoint) {
		this.pk_extpoint = pk_extpoint;
	}

	@Override
	public String getPKFieldName() {
		return "pk_extpoint";
	}

	@Override
	public String getTableName() {
		return "pt_extpoint";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof PtExtensionPoint) {
			PtExtensionPoint _ex = (PtExtensionPoint) obj;
			return StringUtils.equals(point, _ex.getPoint()) && StringUtils.equals(title, _ex.getTitle()) && StringUtils.equals(classname, _ex.getClassname());
		} else {
			return false;
		}
	}
	/**
	 * ��XML�л�ö���
	 * @param exp
	 */
	public void formXML(ExPoint exp){
		this.point = exp.getPoint();
		this.title=exp.getTitle();
		this.classname=exp.getClassname();
	}
	
	public void copy(PtExtensionPoint exp) {
		if(exp!=null){
			this.title=exp.getTitle();
			this.classname=exp.getClassname();
		}
	}
}
