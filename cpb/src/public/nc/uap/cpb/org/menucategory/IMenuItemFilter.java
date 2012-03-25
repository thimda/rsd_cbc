package nc.uap.cpb.org.menucategory;

import nc.uap.cpb.org.vos.CpMenuItemVO;
import nc.uap.cpb.org.vos.MenuItemAdapterVO;

public interface IMenuItemFilter {
	public MenuItemAdapterVO[] filter(MenuItemAdapterVO[] menuitems) ;
	
	public CpMenuItemVO[] filter(CpMenuItemVO[] menuitems);
}
