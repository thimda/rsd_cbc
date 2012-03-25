package nc.uap.cpb.org.vos;

import nc.vo.pub.SuperVO;

/**
 * 动态更新菜单
 * 
 * <pre>
 * 用于适配菜单项目 &amp; 功能节点的前台展示
 * </pre>
 */
public class DynamicMenuVO extends SuperVO {

	public static final String ADD_ACTION = "0";	
	public static final String UPDATE_ACTION = "1";	
	public static final String DEL_ACTION = "2";
	/**
	 * 
	 */
	private static final long serialVersionUID = -2654458751045681521L;
	//操作模式
	private String action;
	//菜单项
	private CpMenuItemVO menuitemvo;
	
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public CpMenuItemVO getMenuitemvo() {
		return menuitemvo;
	}
	public void setMenuitemvo(CpMenuItemVO menuitemvo) {
		this.menuitemvo = menuitemvo;
	}
	
}
