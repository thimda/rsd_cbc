package nc.uap.ctrl.tpl.qry.sysfunc;

import nc.uap.ctrl.tpl.qry.meta.RefResultVO;
import nc.uap.ctrl.tpl.qry.meta.SFType;


/**
 * 系统函数接口
 * <p>
 * 系统函数表达式规范为&quot;#expression#&quot;
 * 
 * @author 刘晨伟
 *
 * 创建日期：2009-10-29
 */
public interface ISysFunction {
	
	/** 系统函数表达式前后缀标记 &quot;#&quot;*/
	public static final String TOKEN = "#";
	
	/**
	 * 系统函数编码，形如"#code#"
	 */
	public String getCode();
	
	/**
	 * 系统函数名称，要实现多语
	 */
	public String getName();
	
	/**
	 * 系统函数类型
	 */
	public SFType getType();
	
	/**
	 * 系统函数值
	 */
	public RefResultVO value();
}