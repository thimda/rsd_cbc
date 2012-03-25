package nc.uap.portal.plugins.model;

import java.io.Serializable;

import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.lfw.util.LfwClassUtil;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFBoolean;

import org.apache.commons.lang.StringUtils;

/**
 * ��չ
 * 
 * @author licza
 * @since 2010��9��9��15:12:17
 */
public class PtExtension extends SuperVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6245810623877791443L;
	/** ��չ�� **/
	protected String id;
	/** ���� **/
	protected String classname;
	/** ���� **/
	protected String title;
	/** ��չ�����������ӿ����� **/
	protected String point;
	/** ���ʻ����� **/
	protected String i18nname;
	/** ɾ����־ **/
	private java.lang.Integer dr = 0;
	/** �������¼� **/
	private nc.vo.pub.lang.UFDateTime ts;
	/** ���� **/
	public String pk_extension;
	/** ģ�� **/
	public String module;
	
	private UFBoolean isactive;

	/**
	 * ���һ��ʵ��
	 * 
	 * @return
	 */
	public Object newInstance() {
			if(isactive != null && !isactive.booleanValue())
				throw new SecurityException("this plugin is unreachable!");
			Object ins = LfwClassUtil.newInstance(classname);
			if (ins instanceof IDynamicalPlugin) {
				IDynamicalPlugin dp = (IDynamicalPlugin) ins;
				dp.init(getId(), getI18nname(), getTitle());
			}
			return ins;
	}
	/**
	 * ���һ��ʵ��
	 * @param <T>
	 * @param itf
	 * @return
	 * @throws CpbBusinessException
	 */
	@SuppressWarnings("unchecked")
	public <T> T newInstance(Class<T> itf){
			return (T) newInstance();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPoint() {
		return point;
	}

	public void setPoint(String point) {
		this.point = point;
	}

	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	public String getPk_extension() {
		return pk_extension;
	}

	public void setPk_extension(String pk_extension) {
		this.pk_extension = pk_extension;
	}

	@Override
	public String getPKFieldName() {
		return "pk_extension";
	}

	@Override
	public String getTableName() {
		return "pt_extension";
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

	public String getI18nname() {
		return i18nname;
	}

	public void setI18nname(String i18nname) {
		this.i18nname = i18nname;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof PtExtension) {
			PtExtension ex = (PtExtension) obj;
			return StringUtils.equals(ex.getId(), id)  && StringUtils.equals(ex.getPoint(), point);
		} else {
			return false;
		}
	}

	public boolean same(PtExtension ex){
		if(ex != null){
			return StringUtils.equals(ex.getId(), id) && StringUtils.equals(ex.getClassname(), classname) && StringUtils.equals(ex.getI18nname(), i18nname)
					&& StringUtils.equals(ex.getTitle(), title) && StringUtils.equals(ex.getPoint(), point);
		} else {
			return false;
		}
	}
	
 	/**
	 * ����
	 * 
	 * @param ex
	 */
	public void copy(PtExtension ex) {
		this.classname = ex.getClassname();
		this.id = ex.getId();
		this.i18nname = ex.getI18nname();
		this.point = ex.getPoint();
		this.title = ex.getTitle();
	}
	/**
	 * ��XML�л��
	 * @param ex
	 * @param point
	 */
	public void fromXML(Extension ex,String point){
		this.classname = ex.getClassname();
		this.id = ex.getId();
		this.i18nname = ex.getI18nname();
		this.point = point;
		this.title = ex.getTitle();
		this.isactive = ex.getIsactive() == null ? UFBoolean.TRUE : UFBoolean.valueOf(ex.getIsactive());
	}
	
	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public UFBoolean getIsactive() {
		return isactive;
	}

	public void setIsactive(UFBoolean isactive) {
		this.isactive = isactive;
	}

}
