package nc.uap.ctrl.tpl.qry.value;

import java.io.Serializable;

/**
 * �������
 * 
 * @author ����ΰ
 *
 * �������ڣ�2009-10-19
 */
public class Attribute implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4516219450985128387L;
	
	private String code;
	private String name;
	
	private String attrpath;// ��Ӧ��Ԫ��������·��
	
	public Attribute() {
		super();
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getAttrpath() {
		return attrpath;
	}
	
	public void setAttrpath(String attrpath) {
		this.attrpath = attrpath;
	}
}