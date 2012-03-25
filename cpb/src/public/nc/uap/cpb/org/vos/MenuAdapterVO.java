package nc.uap.cpb.org.vos;

import nc.vo.pub.SuperVO;

/**
 * 菜单树项适配器
 * 
 * <pre>
 * 用于适配菜单项目 &amp; 功能节点的前台展示
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
	 * 主键
	 */
	private String id;
	/**
	 * 上级
	 */
	private String parentid;
	
	/**
	 * 名称
	 */
	private String title;

	/**
	 * 类型
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
