package nc.uap.cpb.org.menuitem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import nc.uap.cpb.org.vos.MenuItemAdapterVO;

/**
 * ²Ëµ¥¸ù
 * @author licza
 *
 */
public class MenuRoot implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7843153918532661101L;
	
	private String title;
	private String pk_parent;
	private String code;
	private String icon;
	private List<MenuItemAdapterVO> nodes;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<MenuItemAdapterVO> getNodes() {
		if(nodes == null)
			nodes = new ArrayList<MenuItemAdapterVO>();
		return nodes;
	}
	public void setNodes(List<MenuItemAdapterVO> nodes) {
		this.nodes = nodes;
	}
	 public void addNode(MenuItemAdapterVO node){
		 getNodes().add(node);
	 }
	public MenuRoot(String title,String code) {
		super();
		this.title = title;
		this.code = code;
	}
	public MenuItemAdapterVO[] doGetNodeArray(){
		return getNodes().toArray(new MenuItemAdapterVO[0]);
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getPk_parent() {
		return pk_parent;
	}
	public void setPk_parent(String pk_parent) {
		this.pk_parent = pk_parent;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	
}
