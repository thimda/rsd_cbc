package nc.uap.cpb.org.vos;

import nc.vo.pub.SuperVO;

/**
 * �˵�����������
 * 
 * <pre>
 * ��������˵���Ŀ &amp; ���ܽڵ��ǰ̨չʾ
 * </pre>
 */
public class MenuAdapterVO extends SuperVO {
	
	public static final String CATEGORY_TYPE = "0";
	public static final String MENU_TYPE = "1";

	/**
	 * 
	 */
	private static final long serialVersionUID = -1946511985096687519L;
	/**
	 * ����
	 */
	private String id;
	/**
	 * �ϼ�
	 */
	private String parentid;
	
	/**
	 * ����
	 */
	private String title;

	/**
	 * ����
	 */
	private String type;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
}
