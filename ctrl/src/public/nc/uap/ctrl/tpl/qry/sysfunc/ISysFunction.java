package nc.uap.ctrl.tpl.qry.sysfunc;

import nc.uap.ctrl.tpl.qry.meta.RefResultVO;
import nc.uap.ctrl.tpl.qry.meta.SFType;


/**
 * ϵͳ�����ӿ�
 * <p>
 * ϵͳ�������ʽ�淶Ϊ&quot;#expression#&quot;
 * 
 * @author ����ΰ
 *
 * �������ڣ�2009-10-29
 */
public interface ISysFunction {
	
	/** ϵͳ�������ʽǰ��׺��� &quot;#&quot;*/
	public static final String TOKEN = "#";
	
	/**
	 * ϵͳ�������룬����"#code#"
	 */
	public String getCode();
	
	/**
	 * ϵͳ�������ƣ�Ҫʵ�ֶ���
	 */
	public String getName();
	
	/**
	 * ϵͳ��������
	 */
	public SFType getType();
	
	/**
	 * ϵͳ����ֵ
	 */
	public RefResultVO value();
}