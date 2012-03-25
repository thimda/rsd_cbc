package nc.uap.ctrl.tpl.qry.base;
	
import java.util.Comparator;

import nc.vo.ml.MultiLangUtil;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.MultiLangText;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.query.BlobUtil;
import nc.vo.querytemplate.queryscheme.QuerySchemeObject;

public class QuerySchemeVO extends SuperVO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5559268214892493968L;
	
	private java.lang.String pk_queryscheme;
	private java.lang.String parentid;
	private java.lang.String name;
	private java.lang.String name2;
	private java.lang.String name3;
	private java.lang.String name4;
	private java.lang.String name5;
	private java.lang.String name6;
	private java.lang.String cuserid;
	private java.lang.String pk_org;
	private java.lang.String pk_template;
	private java.lang.String funcode;
	private byte[] qsobject;
	private nc.vo.pub.lang.UFBoolean iscomplete;
	private nc.vo.pub.lang.UFBoolean isprepared;
	private nc.vo.pub.lang.UFBoolean isdefault;
	private nc.vo.pub.lang.UFBoolean isautorun;
	// �Ƿ��ѱ��û��޸Ĺ����ñ��ֻ����Ԥ�÷��������ķ���������
	// ����޸���name��name2��name3����qsobject����Ϊ�û��޸Ĺ�������ģ�壬һ���޸Ĺ�����Զ��true
	private nc.vo.pub.lang.UFBoolean ismodified;
	private nc.vo.pub.lang.UFBoolean isdeleted;
	private java.lang.Integer sequenc;
	private java.lang.Integer dr = 0;
	private nc.vo.pub.lang.UFDateTime ts;
	//����ģ����������������⣬����ǰ��Ҫ��ģ����ɾ�����еĲ�ѯ����
	private java.lang.String modulename;
	private nc.vo.pub.lang.UFBoolean isquickqs;

	
	public static final String PK_QUERYSCHEME = "pk_queryscheme";
	public static final String PARENTID = "parentid";
	public static final String NAME = "name";
	public static final String NAME2 = "name2";
	public static final String NAME3 = "name3";
	public static final String NAME4 = "name4";
	public static final String NAME5 = "name5";
	public static final String NAME6 = "name6";
	public static final String CUSERID = "cuserid";
	public static final String PK_ORG = "pk_org";
	public static final String PK_TEMPLATE = "pk_template";
	public static final String FUNCODE = "funcode";
	public static final String QSOBJECT = "qsobject";
	public static final String ISPREPARED = "isprepared";
	public static final String ISDEFAULT = "isdefault";
	public static final String ISAUTORUN = "isautorun";
	public static final String ISMODIFIED = "ismodified";
	public static final String ISDELETED = "isdeleted";
	public static final String SEQUENC = "sequence";
	public static final String MODULENAME = "modulename";
	public static final String ISQUICKQS = "isquickqs";
			
	/**
	 * ����pk_queryscheme��Getter����.
	 * ��������:$vmObject.createdDate
	 * @return java.lang.String
	 */
	public java.lang.String getPk_queryscheme () {
		return pk_queryscheme;
	}   
	/**
	 * ����pk_queryscheme��Setter����.
	 * ��������:$vmObject.createdDate
	 * @param newPk_queryscheme java.lang.String
	 */
	public void setPk_queryscheme (java.lang.String newPk_queryscheme ) {
	 	this.pk_queryscheme = newPk_queryscheme;
	} 	  
	/**
	 * ����parent��Getter����.
	 * ��������:$vmObject.createdDate
	 * @return java.lang.String
	 */
	public java.lang.String getParentid () {
		return parentid;
	}   
	/**
	 * ����parent��Setter����.
	 * ��������:$vmObject.createdDate
	 * @param newParent java.lang.String
	 */
	public void setParentid (java.lang.String newParent ) {
	 	this.parentid = newParent;
	} 	  
	/**
	 * ����name��Getter����.
	 * ��������:$vmObject.createdDate
	 * @return java.lang.String
	 */
	public java.lang.String getName () {
		return name;
	}   
	/**
	 * ����name��Setter����.
	 * ��������:$vmObject.createdDate
	 * @param newName java.lang.String
	 */
	public void setName (java.lang.String newName ) {
	 	this.name = newName;
	} 	  
	/**
	 * ����name2��Getter����.
	 * ��������:$vmObject.createdDate
	 * @return java.lang.String
	 */
	public java.lang.String getName2 () {
		return name2;
	}   
	/**
	 * ����name2��Setter����.
	 * ��������:$vmObject.createdDate
	 * @param newName2 java.lang.String
	 */
	public void setName2 (java.lang.String newName2 ) {
	 	this.name2 = newName2;
	} 	  
	/**
	 * ����name3��Getter����.
	 * ��������:$vmObject.createdDate
	 * @return java.lang.String
	 */
	public java.lang.String getName3 () {
		return name3;
	}   
	/**
	 * ����name3��Setter����.
	 * ��������:$vmObject.createdDate
	 * @param newName3 java.lang.String
	 */
	public void setName3 (java.lang.String newName3 ) {
	 	this.name3 = newName3;
	} 	  
	public java.lang.String getName4() {
		return name4;
	} 
	public void setName4(java.lang.String newName4 ) {
	 	this.name4 = newName4;
	}
	public java.lang.String getName5() {
		return name5;
	} 
	public void setName5(java.lang.String newName5 ) {
	 	this.name5 = newName5;
	}
	public java.lang.String getName6() {
		return name6;
	} 
	public void setName6(java.lang.String newName6 ) {
	 	this.name6 = newName6;
	} 
	/**
	 * ����cuserid��Getter����.
	 * ��������:$vmObject.createdDate
	 * @return java.lang.String
	 */
	public java.lang.String getCuserid () {
		return cuserid;
	}   
	/**
	 * ����cuserid��Setter����.
	 * ��������:$vmObject.createdDate
	 * @param newCuserid java.lang.String
	 */
	public void setCuserid (java.lang.String newCuserid ) {
	 	this.cuserid = newCuserid;
	} 	  
	/**
	 * ����pk_org��Getter����.
	 * ��������:$vmObject.createdDate
	 * @return java.lang.String
	 */
	public java.lang.String getPk_org () {
		return pk_org;
	}   
	/**
	 * ����pk_org��Setter����.
	 * ��������:$vmObject.createdDate
	 * @param newPk_org java.lang.String
	 */
	public void setPk_org (java.lang.String newPk_org ) {
	 	this.pk_org = newPk_org;
	} 	  
	/**
	 * ����pk_template��Getter����.
	 * ��������:$vmObject.createdDate
	 * @return java.lang.String
	 */
	public java.lang.String getPk_template () {
		return pk_template;
	}   
	/**
	 * ����pk_template��Setter����.
	 * ��������:$vmObject.createdDate
	 * @param newPk_template java.lang.String
	 */
	public void setPk_template (java.lang.String newPk_template ) {
	 	this.pk_template = newPk_template;
	} 	  
	/**
	 * ����funcode��Getter����.
	 * ��������:$vmObject.createdDate
	 * @return java.lang.String
	 */
	public java.lang.String getFuncode () {
		return funcode;
	}   
	/**
	 * ����funcode��Setter����.
	 * ��������:$vmObject.createdDate
	 * @param newFuncode java.lang.String
	 */
	public void setFuncode (java.lang.String newFuncode ) {
	 	this.funcode = newFuncode;
	} 	   
	/**
	 * ����qsobject��Getter����.
	 * ��������:$vmObject.createdDate
	 * @return java.lang.Object
	 */
	public byte[] getQsobject () {
		return qsobject;
	}   
	/**
	 * ����qsobject��Setter����.
	 * ��������:$vmObject.createdDate
	 * @param newQsobject java.lang.Object
	 */
	public void setQsobject (byte[] newQsobject ) {
	 	this.qsobject = newQsobject;
	} 	  
	/**
	 * ����isprepared��Getter����.
	 * ��������:$vmObject.createdDate
	 * @return nc.vo.pub.lang.UFBoolean
	 */
	public nc.vo.pub.lang.UFBoolean getIscomplete () {
		return iscomplete;
	}   
	/**
	 * ����isprepared��Setter����.
	 * ��������:$vmObject.createdDate
	 * @param newIsprepared nc.vo.pub.lang.UFBoolean
	 */
	public void setIscomplete (nc.vo.pub.lang.UFBoolean newIscomplete ) {
	 	this.iscomplete = newIscomplete;
	} 	  
	/**
	 * ����isprepared��Getter����.
	 * ��������:$vmObject.createdDate
	 * @return nc.vo.pub.lang.UFBoolean
	 */
	public nc.vo.pub.lang.UFBoolean getIsprepared () {
		return isprepared;
	}   
	/**
	 * ����isprepared��Setter����.
	 * ��������:$vmObject.createdDate
	 * @param newIsprepared nc.vo.pub.lang.UFBoolean
	 */
	public void setIsprepared (nc.vo.pub.lang.UFBoolean newIsprepared ) {
	 	this.isprepared = newIsprepared;
	} 	  
	/**
	 * ����isdefault��Getter����.
	 * ��������:$vmObject.createdDate
	 * @return nc.vo.pub.lang.UFBoolean
	 */
	public nc.vo.pub.lang.UFBoolean getIsdefault () {
		return isdefault;
	}   
	/**
	 * ����isdefault��Setter����.
	 * ��������:$vmObject.createdDate
	 * @param newIsdefault nc.vo.pub.lang.UFBoolean
	 */
	public void setIsdefault (nc.vo.pub.lang.UFBoolean newIsdefault ) {
	 	this.isdefault = newIsdefault;
	} 	  
	/**
	 * ����isautorun��Getter����.
	 * ��������:$vmObject.createdDate
	 * @return nc.vo.pub.lang.UFBoolean
	 */
	public nc.vo.pub.lang.UFBoolean getIsautorun () {
		return isautorun;
	}   
	/**
	 * ����isautorun��Setter����.
	 * ��������:$vmObject.createdDate
	 * @param newIsautorun nc.vo.pub.lang.UFBoolean
	 */
	public void setIsautorun (nc.vo.pub.lang.UFBoolean newIsautorun ) {
	 	this.isautorun = newIsautorun;
	} 	  
	/**
	 * �Ƿ��ѱ��û��޸Ĺ�
	 * ��������:$vmObject.createdDate
	 * @return nc.vo.pub.lang.UFBoolean
	 */
	public nc.vo.pub.lang.UFBoolean getIsmodified () {
		return ismodified;
	}   
	/**
	 * �����Ƿ��ѱ��û��޸Ĺ�
	 * ��������:$vmObject.createdDate
	 * @param newIsmodified nc.vo.pub.lang.UFBoolean
	 */
	public void setIsmodified (nc.vo.pub.lang.UFBoolean newIsmodified ) {
	 	this.ismodified = newIsmodified;
	} 	  
	/**
	 * ����isdeleted��Getter����.
	 * ��������:$vmObject.createdDate
	 * @return nc.vo.pub.lang.UFBoolean
	 */
	public nc.vo.pub.lang.UFBoolean getIsdeleted () {
		return isdeleted;
	}   
	/**
	 * ����isdeleted��Setter����.
	 * ��������:$vmObject.createdDate
	 * @param newIsdeleted nc.vo.pub.lang.UFBoolean
	 */
	public void setIsdeleted (nc.vo.pub.lang.UFBoolean newIsdeleted ) {
	 	this.isdeleted = newIsdeleted;
	} 	  
	/**
	 * ����sequenc��Getter����.
	 * ��������:$vmObject.createdDate
	 * @return java.lang.Integer
	 */
	public java.lang.Integer getSequenc () {
		return sequenc;
	}   
	/**
	 * ����sequence��Setter����.
	 * ��������:$vmObject.createdDate
	 * @param newSequenc java.lang.Integer
	 */
	public void setSequenc (java.lang.Integer newSequenc ) {
	 	this.sequenc = newSequenc;
	} 	  
	/**
	 * ����dr��Getter����.
	 * ��������:$vmObject.createdDate
	 * @return java.lang.Integer
	 */
	public java.lang.Integer getDr () {
		return dr;
	}   
	/**
	 * ����dr��Setter����.
	 * ��������:$vmObject.createdDate
	 * @param newDr java.lang.Integer
	 */
	public void setDr (java.lang.Integer newDr ) {
	 	this.dr = newDr;
	} 	  
	/**
	 * ����ts��Getter����.
	 * ��������:$vmObject.createdDate
	 * @return nc.vo.pub.lang.UFDateTime
	 */
	public nc.vo.pub.lang.UFDateTime getTs () {
		return ts;
	}   
	/**
	 * ����ts��Setter����.
	 * ��������:$vmObject.createdDate
	 * @param newTs nc.vo.pub.lang.UFDateTime
	 */
	public void setTs (nc.vo.pub.lang.UFDateTime newTs ) {
	 	this.ts = newTs;
	} 	  
 
	/**
	  * <p>ȡ�ø�VO�����ֶ�.
	  * <p>
	  * ��������:${vmObject.createdDate}
	  * @return java.lang.String
	  */
	public java.lang.String getParentPKFieldName() {
	    return null;
	}   
    
	/**
	  * <p>ȡ�ñ�����.
	  * <p>
	  * ��������:${vmObject.createdDate}
	  * @return java.lang.String
	  */
	public java.lang.String getPKFieldName() {
	  return "pk_queryscheme";
	}
    
	/**
	 * <p>���ر�����.
	 * <p>
	 * ��������:${vmObject.createdDate}
	 * @return java.lang.String
	 */
	public java.lang.String getTableName() {
		return "pub_queryscheme";
	}    
	
	/**
	 * <p>���ر�����.
	 * <p>
	 * ��������:${vmObject.createdDate}
	 * @return java.lang.String
	 */
	public static java.lang.String getDefaultTableName() {
		return "pub_queryscheme";
	}    
    
    /**
	  * ����Ĭ�Ϸ�ʽ����������.
	  *
	  * ��������:${vmObject.createdDate}
	  */
     public QuerySchemeVO() {
		super();	
	}    
     
     /**
      * ��ݽӿ�����QuerySchemeObject�����ڲ��ὫQuerySchemeObjectת����byte[]
      */
    public void setQSObject4Blob(QuerySchemeObject qsobject) {
    	 setQsobject(BlobUtil.convert(qsobject));
     }
     
     /**
     * ��ݽӿڷ���QuerySchemeObject�����ڲ��Ὣbyte[]ת����QuerySchemeObject
     */
    public QuerySchemeObject getQSObject4Blob() {
    	 return (QuerySchemeObject) BlobUtil.convert(getQsobject());
     }
     
	public String toString() {
		return name;
	}
	
	public MultiLangText getMultiLangName() {
		MultiLangText ml = new MultiLangText();
		ml.setText(getName());
		ml.setText2(getName2());
		ml.setText3(getName3());
		ml.setText4(getName4());
		ml.setText5(getName5());
		ml.setText6(getName6());
		return ml;
	}
	
	public void setMultiLangName(MultiLangText mltext) {
		if(mltext == null) {
			mltext = new MultiLangText();
		}
		setName(mltext.getText());
		setName2(mltext.getText2());
		setName3(mltext.getText3());
		setName4(mltext.getText4());
		setName5(mltext.getText5());
		setName6(mltext.getText6());
	}
	
	/** ���·�����Ϊ�˼򻯿ͻ��˳����ʹ�� */
	
	/**
	 * �Ƿ������Ʒ���
	 */
	public boolean isComplete() {
		return convert(getIscomplete());
	}
	
	public boolean isDefault() {
		return convert(getIsdefault());
	}

	public boolean isPrepared() {
		return convert(getIsprepared());
	}

	public boolean isAutorun() {
		return convert(getIsautorun());
	}
	
	/**
	 * �Ƿ��ѱ��û��޸Ĺ�
	 */
	public boolean isModified() {
		return convert(getIsmodified());
	}
	
	public boolean isDeleted() {
		return convert(getIsdeleted());
	}
	
	public void setComplete(boolean b) {
		setIscomplete(UFBoolean.valueOf(b));
	}
	
	public void setDefault(boolean b) {
		setIsdefault(UFBoolean.valueOf(b));
	}
	
	public void setPrepared(boolean b) {
		setIsprepared(UFBoolean.valueOf(b));
	}
	
	public void setAutorun(boolean b) {
		setIsautorun(UFBoolean.valueOf(b));
	}
	
	/**
	 * �Ƿ��ѱ��û��޸Ĺ����ñ��ֻ����Ԥ�÷��������ķ���������
	 * ����޸���name��name2��name3����qsobject����Ϊ�û��޸Ĺ�������ģ�壬һ���޸Ĺ�����Զ��true
	 */
	public void setModified(boolean b) {
		setIsmodified(UFBoolean.valueOf(b));
	}
	
	public void setDeleted(boolean b) {
		setIsdeleted(UFBoolean.valueOf(b));
	}
	
	private static boolean convert(UFBoolean ufboolean) {
		return ufboolean == null ? false : ufboolean.booleanValue();
	}
	
	/**
	 * �Ƿ��ǿ��ٲ�ѯ����
	 */
	public boolean isQuickQueryScheme() {
		// ��ʱ��ͨ���������ж�
		return "���ٲ�ѯ".equals(getName());
	}
	
	/**
	 * ���ػ������(sequence)��Comparator������VO����
	 */
	public static Comparator<QuerySchemeVO> bySequence() {
		
		return new Comparator<QuerySchemeVO>() {

			public int compare(QuerySchemeVO o1, QuerySchemeVO o2) {
				if (o1.getSequenc() == null) return 1;
				if (o2.getSequenc() == null) return 1;
				return o1.getSequenc().compareTo(o2.getSequenc());
			}
		};
	}
	public java.lang.String getModulename() {
		return modulename;
	}
	public void setModulename(java.lang.String modulename) {
		this.modulename = modulename;
	}
	
	public nc.vo.pub.lang.UFBoolean getIsquickqs() {
		return isquickqs;
	}
	public void setIsquickqs(nc.vo.pub.lang.UFBoolean isquickqs) {
		this.isquickqs = isquickqs;
	}
	
	public String getCurLangNamefield(){
		String s = null;
		switch (MultiLangUtil.getCurrentLangSeq()) {
		case 1:
			s = NAME;
			break;
		case 2:
			s = NAME2;
			break;
		case 3:
			s = NAME3;
			break;
		case 4:
			s = NAME4;
			break;
		case 5:
			s = NAME5;
			break;
		case 6:
			s = NAME6;
			break;
		default:
			break;
		}
		//����ݵ�½����δȡ��ֵ��Ĭ��ȡ�������ֶ�Ӧֵ
		if(s == null) {
			s = NAME;
		}
		return s;
	}
}