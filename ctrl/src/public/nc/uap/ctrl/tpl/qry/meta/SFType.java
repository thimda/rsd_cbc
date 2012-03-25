package nc.uap.ctrl.tpl.qry.meta;

import java.util.ArrayList;
import java.util.List;

import nc.md.model.IAttribute;
import nc.md.model.IBean;
import nc.uap.ctrl.tpl.qry.base.MDType2QTTypeMapping;

/**
 * ϵͳ����������Ϣ
 * 
 * @author ����ΰ
 *
 * �������ڣ�2010-4-1
 */
public final class SFType {

	/** ������ */
	public static final int TYPE_DATE = 0;
	/** ������ */
	public static final int TYPE_REF = 1;
	
	/** ����ϵͳ����������Ϣʵ�� */
	public static final SFType DATE = new SFType();
	/** (��Ԫ����)����ϵͳ����������Ϣʵ��(�ڲ�ʹ��) */
	private static final SFType REF = new SFType(null);
	
	private int type;
	private String refBeanID;
	
	/**
	 *��֧�ֵ�Ԫ���ݼ��� 
	 */
	private List<String> supportBeanList = new ArrayList<String>();
	
	private SFType() {
		this.type = TYPE_DATE;
	}
	
	/**
	 * @param refBeanID
	 *            ����ǲ�����ʱ���õ�ʵ��ID
	 */
	public SFType(String refBeanID) {
		this.type = TYPE_REF;
		this.refBeanID = refBeanID;
	}

	/**
	 * ����ϵͳ��������
	 * @see nc.vo.querytemplate.sysfunc.SFType.TYPE_DATE
	 * @see nc.vo.querytemplate.sysfunc.SFType.TYPE_REF
	 */
	public int getType() {
		return type;
	}

	/**
	 * ��������ǲ�����ʱ���õ�ʵ��ID
	 */
	public String getRefBeanID() {
		return refBeanID;
	}
	
	/**
	 * ���ݲ�ѯ�����������ͷ��ض�Ӧ��ϵͳ��������
	 * 
	 * @param datatype
	 *            �������� (������nc.vo.pub.query.IQueryConstants�еĳ���)
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
	 * ���ݲ�ѯ�����������ͷ��ض�Ӧ��ϵͳ��������
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
	 * ��������������ʵ��
	 */
	private static IBean getRefBean(IAttribute attr) {
		return attr.getAssociation().getEndBean();
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof SFType){
			SFType sfType = (SFType)obj;
			// �ж��Ƿ�Ϊͬһ��ϵͳ����
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