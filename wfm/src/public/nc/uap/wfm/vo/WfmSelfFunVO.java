/***************************************************************\
 *     The skeleton of this class is generated by an automatic *
 * code generator for NC product. It is based on Velocity.     *
\***************************************************************/
package nc.uap.wfm.vo;
import nc.vo.pub.SuperVO;
/**
 * <b> 在此处简要描述此类的功能 </b>
 * <p>
 * 在此处添加此类的描述信息
 * </p>
 * 创建日期:
 * 
 * @author
 * @version NCPrj ??
 */
@SuppressWarnings("serial") public class WfmSelfFunVO extends SuperVO {
	private java.lang.String pk_selffunc;
	private java.lang.String funname;
	private java.lang.String clazz;
	private java.lang.String funcode;
	private java.lang.Integer dr = 0;
	private nc.vo.pub.lang.UFDateTime ts;
	public static final String PK_SELFFUNC = "pk_selffunc";
	public static final String FUNNAME = "funname";
	public static final String CLAZZ = "clazz";
	public static final String FUNCODE = "funcode";
	/**
	 * 属性pk_selffunc的Getter方法.属性名：主键 创建日期:
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getPk_selffunc() {
		return pk_selffunc;
	}
	/**
	 * 属性pk_selffunc的Setter方法.属性名：主键 创建日期:
	 * 
	 * @param newPk_selffunc
	 *            java.lang.String
	 */
	public void setPk_selffunc(java.lang.String newPk_selffunc) {
		this.pk_selffunc = newPk_selffunc;
	}
	/**
	 * 属性funname的Getter方法.属性名：函数名称 创建日期:
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getFunname() {
		return funname;
	}
	/**
	 * 属性funname的Setter方法.属性名：函数名称 创建日期:
	 * 
	 * @param newFunname
	 *            java.lang.String
	 */
	public void setFunname(java.lang.String newFunname) {
		this.funname = newFunname;
	}
	/**
	 * 属性clazz的Getter方法.属性名：实现类 创建日期:
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getClazz() {
		return clazz;
	}
	/**
	 * 属性clazz的Setter方法.属性名：实现类 创建日期:
	 * 
	 * @param newClazz
	 *            java.lang.String
	 */
	public void setClazz(java.lang.String newClazz) {
		this.clazz = newClazz;
	}
	/**
	 * 属性funcode的Getter方法.属性名：函数编码 创建日期:
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getFuncode() {
		return funcode;
	}
	/**
	 * 属性funcode的Setter方法.属性名：函数编码 创建日期:
	 * 
	 * @param newFuncode
	 *            java.lang.String
	 */
	public void setFuncode(java.lang.String newFuncode) {
		this.funcode = newFuncode;
	}
	/**
	 * 属性dr的Getter方法.属性名：dr 创建日期:
	 * 
	 * @return java.lang.Integer
	 */
	public java.lang.Integer getDr() {
		return dr;
	}
	/**
	 * 属性dr的Setter方法.属性名：dr 创建日期:
	 * 
	 * @param newDr
	 *            java.lang.Integer
	 */
	public void setDr(java.lang.Integer newDr) {
		this.dr = newDr;
	}
	/**
	 * 属性ts的Getter方法.属性名：ts 创建日期:
	 * 
	 * @return nc.vo.pub.lang.UFDateTime
	 */
	public nc.vo.pub.lang.UFDateTime getTs() {
		return ts;
	}
	/**
	 * 属性ts的Setter方法.属性名：ts 创建日期:
	 * 
	 * @param newTs
	 *            nc.vo.pub.lang.UFDateTime
	 */
	public void setTs(nc.vo.pub.lang.UFDateTime newTs) {
		this.ts = newTs;
	}
	/**
	 * <p>
	 * 取得父VO主键字段.
	 * <p>
	 * 创建日期:
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getParentPKFieldName() {
		return null;
	}
	/**
	 * <p>
	 * 取得表主键.
	 * <p>
	 * 创建日期:
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getPKFieldName() {
		return "pk_selffunc";
	}
	/**
	 * <p>
	 * 返回表名称.
	 * <p>
	 * 创建日期:
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getTableName() {
		return "wfm_selffunc";
	}
	/**
	 * <p>
	 * 返回表名称.
	 * <p>
	 * 创建日期:
	 * 
	 * @return java.lang.String
	 */
	public static java.lang.String getDefaultTableName() {
		return "wfm_selffunc";
	}
	/**
	 * 按照默认方式创建构造子.
	 * 
	 * 创建日期:
	 */
	public WfmSelfFunVO() {
		super();
	}
}
