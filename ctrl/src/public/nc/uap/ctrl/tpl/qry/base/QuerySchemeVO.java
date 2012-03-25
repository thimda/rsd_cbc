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
	// 是否已被用户修改过，该标记只对由预置方案派生的方案有意义
	// 如果修改了name、name2、name3或者qsobject则认为用户修改过了派生模板，一旦修改过就永远是true
	private nc.vo.pub.lang.UFBoolean ismodified;
	private nc.vo.pub.lang.UFBoolean isdeleted;
	private java.lang.Integer sequenc;
	private java.lang.Integer dr = 0;
	private nc.vo.pub.lang.UFDateTime ts;
	//增加模块名，解决升级问题，升级前需要按模块先删掉所有的查询方案
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
	 * 属性pk_queryscheme的Getter方法.
	 * 创建日期:$vmObject.createdDate
	 * @return java.lang.String
	 */
	public java.lang.String getPk_queryscheme () {
		return pk_queryscheme;
	}   
	/**
	 * 属性pk_queryscheme的Setter方法.
	 * 创建日期:$vmObject.createdDate
	 * @param newPk_queryscheme java.lang.String
	 */
	public void setPk_queryscheme (java.lang.String newPk_queryscheme ) {
	 	this.pk_queryscheme = newPk_queryscheme;
	} 	  
	/**
	 * 属性parent的Getter方法.
	 * 创建日期:$vmObject.createdDate
	 * @return java.lang.String
	 */
	public java.lang.String getParentid () {
		return parentid;
	}   
	/**
	 * 属性parent的Setter方法.
	 * 创建日期:$vmObject.createdDate
	 * @param newParent java.lang.String
	 */
	public void setParentid (java.lang.String newParent ) {
	 	this.parentid = newParent;
	} 	  
	/**
	 * 属性name的Getter方法.
	 * 创建日期:$vmObject.createdDate
	 * @return java.lang.String
	 */
	public java.lang.String getName () {
		return name;
	}   
	/**
	 * 属性name的Setter方法.
	 * 创建日期:$vmObject.createdDate
	 * @param newName java.lang.String
	 */
	public void setName (java.lang.String newName ) {
	 	this.name = newName;
	} 	  
	/**
	 * 属性name2的Getter方法.
	 * 创建日期:$vmObject.createdDate
	 * @return java.lang.String
	 */
	public java.lang.String getName2 () {
		return name2;
	}   
	/**
	 * 属性name2的Setter方法.
	 * 创建日期:$vmObject.createdDate
	 * @param newName2 java.lang.String
	 */
	public void setName2 (java.lang.String newName2 ) {
	 	this.name2 = newName2;
	} 	  
	/**
	 * 属性name3的Getter方法.
	 * 创建日期:$vmObject.createdDate
	 * @return java.lang.String
	 */
	public java.lang.String getName3 () {
		return name3;
	}   
	/**
	 * 属性name3的Setter方法.
	 * 创建日期:$vmObject.createdDate
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
	 * 属性cuserid的Getter方法.
	 * 创建日期:$vmObject.createdDate
	 * @return java.lang.String
	 */
	public java.lang.String getCuserid () {
		return cuserid;
	}   
	/**
	 * 属性cuserid的Setter方法.
	 * 创建日期:$vmObject.createdDate
	 * @param newCuserid java.lang.String
	 */
	public void setCuserid (java.lang.String newCuserid ) {
	 	this.cuserid = newCuserid;
	} 	  
	/**
	 * 属性pk_org的Getter方法.
	 * 创建日期:$vmObject.createdDate
	 * @return java.lang.String
	 */
	public java.lang.String getPk_org () {
		return pk_org;
	}   
	/**
	 * 属性pk_org的Setter方法.
	 * 创建日期:$vmObject.createdDate
	 * @param newPk_org java.lang.String
	 */
	public void setPk_org (java.lang.String newPk_org ) {
	 	this.pk_org = newPk_org;
	} 	  
	/**
	 * 属性pk_template的Getter方法.
	 * 创建日期:$vmObject.createdDate
	 * @return java.lang.String
	 */
	public java.lang.String getPk_template () {
		return pk_template;
	}   
	/**
	 * 属性pk_template的Setter方法.
	 * 创建日期:$vmObject.createdDate
	 * @param newPk_template java.lang.String
	 */
	public void setPk_template (java.lang.String newPk_template ) {
	 	this.pk_template = newPk_template;
	} 	  
	/**
	 * 属性funcode的Getter方法.
	 * 创建日期:$vmObject.createdDate
	 * @return java.lang.String
	 */
	public java.lang.String getFuncode () {
		return funcode;
	}   
	/**
	 * 属性funcode的Setter方法.
	 * 创建日期:$vmObject.createdDate
	 * @param newFuncode java.lang.String
	 */
	public void setFuncode (java.lang.String newFuncode ) {
	 	this.funcode = newFuncode;
	} 	   
	/**
	 * 属性qsobject的Getter方法.
	 * 创建日期:$vmObject.createdDate
	 * @return java.lang.Object
	 */
	public byte[] getQsobject () {
		return qsobject;
	}   
	/**
	 * 属性qsobject的Setter方法.
	 * 创建日期:$vmObject.createdDate
	 * @param newQsobject java.lang.Object
	 */
	public void setQsobject (byte[] newQsobject ) {
	 	this.qsobject = newQsobject;
	} 	  
	/**
	 * 属性isprepared的Getter方法.
	 * 创建日期:$vmObject.createdDate
	 * @return nc.vo.pub.lang.UFBoolean
	 */
	public nc.vo.pub.lang.UFBoolean getIscomplete () {
		return iscomplete;
	}   
	/**
	 * 属性isprepared的Setter方法.
	 * 创建日期:$vmObject.createdDate
	 * @param newIsprepared nc.vo.pub.lang.UFBoolean
	 */
	public void setIscomplete (nc.vo.pub.lang.UFBoolean newIscomplete ) {
	 	this.iscomplete = newIscomplete;
	} 	  
	/**
	 * 属性isprepared的Getter方法.
	 * 创建日期:$vmObject.createdDate
	 * @return nc.vo.pub.lang.UFBoolean
	 */
	public nc.vo.pub.lang.UFBoolean getIsprepared () {
		return isprepared;
	}   
	/**
	 * 属性isprepared的Setter方法.
	 * 创建日期:$vmObject.createdDate
	 * @param newIsprepared nc.vo.pub.lang.UFBoolean
	 */
	public void setIsprepared (nc.vo.pub.lang.UFBoolean newIsprepared ) {
	 	this.isprepared = newIsprepared;
	} 	  
	/**
	 * 属性isdefault的Getter方法.
	 * 创建日期:$vmObject.createdDate
	 * @return nc.vo.pub.lang.UFBoolean
	 */
	public nc.vo.pub.lang.UFBoolean getIsdefault () {
		return isdefault;
	}   
	/**
	 * 属性isdefault的Setter方法.
	 * 创建日期:$vmObject.createdDate
	 * @param newIsdefault nc.vo.pub.lang.UFBoolean
	 */
	public void setIsdefault (nc.vo.pub.lang.UFBoolean newIsdefault ) {
	 	this.isdefault = newIsdefault;
	} 	  
	/**
	 * 属性isautorun的Getter方法.
	 * 创建日期:$vmObject.createdDate
	 * @return nc.vo.pub.lang.UFBoolean
	 */
	public nc.vo.pub.lang.UFBoolean getIsautorun () {
		return isautorun;
	}   
	/**
	 * 属性isautorun的Setter方法.
	 * 创建日期:$vmObject.createdDate
	 * @param newIsautorun nc.vo.pub.lang.UFBoolean
	 */
	public void setIsautorun (nc.vo.pub.lang.UFBoolean newIsautorun ) {
	 	this.isautorun = newIsautorun;
	} 	  
	/**
	 * 是否已被用户修改过
	 * 创建日期:$vmObject.createdDate
	 * @return nc.vo.pub.lang.UFBoolean
	 */
	public nc.vo.pub.lang.UFBoolean getIsmodified () {
		return ismodified;
	}   
	/**
	 * 设置是否已被用户修改过
	 * 创建日期:$vmObject.createdDate
	 * @param newIsmodified nc.vo.pub.lang.UFBoolean
	 */
	public void setIsmodified (nc.vo.pub.lang.UFBoolean newIsmodified ) {
	 	this.ismodified = newIsmodified;
	} 	  
	/**
	 * 属性isdeleted的Getter方法.
	 * 创建日期:$vmObject.createdDate
	 * @return nc.vo.pub.lang.UFBoolean
	 */
	public nc.vo.pub.lang.UFBoolean getIsdeleted () {
		return isdeleted;
	}   
	/**
	 * 属性isdeleted的Setter方法.
	 * 创建日期:$vmObject.createdDate
	 * @param newIsdeleted nc.vo.pub.lang.UFBoolean
	 */
	public void setIsdeleted (nc.vo.pub.lang.UFBoolean newIsdeleted ) {
	 	this.isdeleted = newIsdeleted;
	} 	  
	/**
	 * 属性sequenc的Getter方法.
	 * 创建日期:$vmObject.createdDate
	 * @return java.lang.Integer
	 */
	public java.lang.Integer getSequenc () {
		return sequenc;
	}   
	/**
	 * 属性sequence的Setter方法.
	 * 创建日期:$vmObject.createdDate
	 * @param newSequenc java.lang.Integer
	 */
	public void setSequenc (java.lang.Integer newSequenc ) {
	 	this.sequenc = newSequenc;
	} 	  
	/**
	 * 属性dr的Getter方法.
	 * 创建日期:$vmObject.createdDate
	 * @return java.lang.Integer
	 */
	public java.lang.Integer getDr () {
		return dr;
	}   
	/**
	 * 属性dr的Setter方法.
	 * 创建日期:$vmObject.createdDate
	 * @param newDr java.lang.Integer
	 */
	public void setDr (java.lang.Integer newDr ) {
	 	this.dr = newDr;
	} 	  
	/**
	 * 属性ts的Getter方法.
	 * 创建日期:$vmObject.createdDate
	 * @return nc.vo.pub.lang.UFDateTime
	 */
	public nc.vo.pub.lang.UFDateTime getTs () {
		return ts;
	}   
	/**
	 * 属性ts的Setter方法.
	 * 创建日期:$vmObject.createdDate
	 * @param newTs nc.vo.pub.lang.UFDateTime
	 */
	public void setTs (nc.vo.pub.lang.UFDateTime newTs ) {
	 	this.ts = newTs;
	} 	  
 
	/**
	  * <p>取得父VO主键字段.
	  * <p>
	  * 创建日期:${vmObject.createdDate}
	  * @return java.lang.String
	  */
	public java.lang.String getParentPKFieldName() {
	    return null;
	}   
    
	/**
	  * <p>取得表主键.
	  * <p>
	  * 创建日期:${vmObject.createdDate}
	  * @return java.lang.String
	  */
	public java.lang.String getPKFieldName() {
	  return "pk_queryscheme";
	}
    
	/**
	 * <p>返回表名称.
	 * <p>
	 * 创建日期:${vmObject.createdDate}
	 * @return java.lang.String
	 */
	public java.lang.String getTableName() {
		return "pub_queryscheme";
	}    
	
	/**
	 * <p>返回表名称.
	 * <p>
	 * 创建日期:${vmObject.createdDate}
	 * @return java.lang.String
	 */
	public static java.lang.String getDefaultTableName() {
		return "pub_queryscheme";
	}    
    
    /**
	  * 按照默认方式创建构造子.
	  *
	  * 创建日期:${vmObject.createdDate}
	  */
     public QuerySchemeVO() {
		super();	
	}    
     
     /**
      * 便捷接口设置QuerySchemeObject对象，内部会将QuerySchemeObject转换成byte[]
      */
    public void setQSObject4Blob(QuerySchemeObject qsobject) {
    	 setQsobject(BlobUtil.convert(qsobject));
     }
     
     /**
     * 便捷接口返回QuerySchemeObject对象，内部会将byte[]转换成QuerySchemeObject
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
	
	/** 以下方法是为了简化客户端程序的使用 */
	
	/**
	 * 是否是完善方案
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
	 * 是否已被用户修改过
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
	 * 是否已被用户修改过，该标记只对由预置方案派生的方案有意义
	 * 如果修改了name、name2、name3或者qsobject则认为用户修改过了派生模板，一旦修改过就永远是true
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
	 * 是否是快速查询方案
	 */
	public boolean isQuickQueryScheme() {
		// 暂时先通过名称来判断
		return "快速查询".equals(getName());
	}
	
	/**
	 * 返回基于序号(sequence)的Comparator，用于VO排序
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
		//如根据登陆语种未取到值，默认取中文语种对应值
		if(s == null) {
			s = NAME;
		}
		return s;
	}
}