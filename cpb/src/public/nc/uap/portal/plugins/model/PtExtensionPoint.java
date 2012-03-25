package nc.uap.portal.plugins.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.StringUtils;

import nc.vo.pub.SuperVO;

/**
 * 扩展点
 * 
 * @author licza
 * @since 2010年9月9日14:33:10
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PtExtensionPoint", propOrder = { "classname", "title", "point", "dr", "ts", "pk_extpoint" })
public class PtExtensionPoint extends SuperVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8350649195253142839L;
	/** 扩展点唯一标识 **/
	@XmlAttribute
	protected String point;
	/** 扩展点名 **/
	@XmlAttribute
	protected String title;
	/** 实现扩展类名 **/
	@XmlAttribute
	protected String classname;;

	/** 删除标志 **/
	private java.lang.Integer dr = 0;
	/** 最后操作事件 **/
	private nc.vo.pub.lang.UFDateTime ts;
	/** 主键 **/
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
	 * 从XML中获得定义
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
