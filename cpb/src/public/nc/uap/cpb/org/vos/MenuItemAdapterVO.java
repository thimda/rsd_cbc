package nc.uap.cpb.org.vos;

import java.util.ArrayList;
import java.util.List;

import nc.uap.cpb.org.vos.CpAppsNodeVO;
import nc.uap.cpb.org.vos.CpMenuItemVO;
import nc.vo.pub.SuperVO;

/**
 * 菜单项适配器
 * 
 * <pre>
 * 用于适配菜单项目 &amp; 功能节点的前台展示
 * </pre>
 * 
 * @author licza
 * 
 */
public class MenuItemAdapterVO extends SuperVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1946511985096687519L;
	/**
	 * 菜单项主键
	 */
	private String pk_menuitem;
	/**
	 * 上级项目
	 */
	private String pk_parent;
	
	/**
	 * 名称
	 */
	private String title;
	
	/**
	 * 国际化名称
	 */
	private String i18nname;
	
	/**
	 * 功能节点主键
	 */
	private String pk_funnode;
	/**
	 * 菜单项
	 */
	private CpMenuItemVO menuitem;
	/**
	 * 功能节点
	 */
	private CpAppsNodeVO funnode;

	private List<MenuItemAdapterVO> ChildrenMenu = null;
	
	public String getPk_menuitem() {
		return pk_menuitem;
	}

	public void setPk_menuitem(String pk_menuitem) {
		this.pk_menuitem = pk_menuitem;
	}

	public String getPk_parent() {
		return pk_parent;
	}

	public void setPk_parent(String pk_parent) {
		this.pk_parent = pk_parent;
	}

	public String getPk_funnode() {
		return pk_funnode;
	}

	public void setPk_funnode(String pk_funnode) {
		this.pk_funnode = pk_funnode;
	}

	public CpMenuItemVO getMenuitem() {
		return menuitem;
	}

	public void setMenuitem(CpMenuItemVO menuitem) {
		this.menuitem = menuitem;
	}

	public CpAppsNodeVO getFunnode() {
		return funnode;
	}

	public void setFunnode(CpAppsNodeVO funnode) {
		this.funnode = funnode;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getI18nname() {
		return i18nname;
	}

	public void setI18nname(String i18nname) {
		this.i18nname = i18nname;
	}

	public List<MenuItemAdapterVO> getChildrenMenu() {
		if(ChildrenMenu == null)
			ChildrenMenu = new ArrayList<MenuItemAdapterVO>();
		return ChildrenMenu;
	}

	public void setChildrenMenu(List<MenuItemAdapterVO> childrenMenu) {
		ChildrenMenu = childrenMenu;
	}
	
	public void addChildMenu(MenuItemAdapterVO child){
		getChildrenMenu().add(child);
	}

}
