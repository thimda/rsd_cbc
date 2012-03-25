package nc.uap.ctrl.tpl.qry.meta;

import java.util.ArrayList;
import java.util.List;

import nc.md.model.IAttribute;
import nc.md.model.IBean;
import nc.uap.ctrl.tpl.qry.base.MDType2QTTypeMapping;

/**
 * 系统函数类型信息
 * 
 * @author 刘晨伟
 *
 * 创建日期：2010-4-1
 */
public final class SFType {

	/** 日期型 */
	public static final int TYPE_DATE = 0;
	/** 参照型 */
	public static final int TYPE_REF = 1;
	
	/** 日期系统函数类型信息实例 */
	public static final SFType DATE = new SFType();
	/** (非元数据)引用系统函数类型信息实例(内部使用) */
	private static final SFType REF = new SFType(null);
	
	private int type;
	private String refBeanID;
	
	/**
	 *可支持的元数据集合 
	 */
	private List<String> supportBeanList = new ArrayList<String>();
	
	private SFType() {
		this.type = TYPE_DATE;
	}
	
	/**
	 * @param refBeanID
	 *            如果是参照型时引用的实体ID
	 */
	public SFType(String refBeanID) {
		this.type = TYPE_REF;
		this.refBeanID = refBeanID;
	}

	/**
	 * 返回系统函数类型
	 * @see nc.vo.querytemplate.sysfunc.SFType.TYPE_DATE
	 * @see nc.vo.querytemplate.sysfunc.SFType.TYPE_REF
	 */
	public int getType() {
		return type;
	}

	/**
	 * 返回如果是参照型时引用的实体ID
	 */
	public String getRefBeanID() {
		return refBeanID;
	}
	
	/**
	 * 根据查询条件数据类型返回对应的系统函数类型
	 * 
	 * @param datatype
	 *            数据类型 (定义在nc.vo.pub.query.IQueryConstants中的常量)
	 */
	public static SFType get(int datatype) {
		switch (datatype) {
		case IQueryConstants.DATE:
		case IQueryConstants.TIME:
		case IQueryConstants.LITERALDATE:
			return DATE;
		}
		return REF;
	}
	
	/**
	 * 根据查询条件数据类型返回对应的系统函数类型
	 */
	public static SFType get(IAttribute attribute) {
		int typeType = attribute.getDataType().getTypeType();
		int datatype = MDType2QTTypeMapping.getQTType(typeType);
		switch (datatype) {
		case IQueryConstants.DATE:
		case IQueryConstants.TIME:
		case IQueryConstants.LITERALDATE:
			return DATE;
		case IQueryConstants.UFREF:
			return new SFType(getRefBean(attribute).getID());
		}
		return null;
	}
	
	/**
	 * 返回属性所引用实体
	 */
	private static IBean getRefBean(IAttribute attr) {
		return attr.getAssociation().getEndBean();
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof SFType){
			SFType sfType = (SFType)obj;
			// 判断是否为同一类系统函数
			int sfTypeType = sfType.getType();
			if(sfTypeType == TYPE_DATE){
				return super.equals(obj);
			}
			int typeType = this.getType();
			String sfRefBeanID = sfType.getRefBeanID();
			String beanID = this.getRefBeanID();
			boolean isSameRefFunc = sfTypeType == typeType && sfTypeType == SFType.TYPE_REF && (sfRefBeanID!=null && beanID != null && sfRefBeanID.equals(beanID));
			return isSameRefFunc;
		}
		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		return type*7 + refBeanID==null ? 0 : refBeanID.hashCode();
	}

	public List<String> getSupportBeanList() {
		return supportBeanList;
	}

	public void setSupportBeanList(List<String> supportBeanList) {
		this.supportBeanList = supportBeanList;
	}

	
	
	
}