package nc.uap.cpb.org.menucategory;

import nc.uap.cpb.org.vos.CpMenuItemVO;
import nc.uap.cpb.org.vos.MenuItemAdapterVO;

public class DefaultMenuItemFilter implements IMenuItemFilter {

	@Override
	public MenuItemAdapterVO[] filter(MenuItemAdapterVO[] menuitems) {
		return menuitems;
	}
	public CpMenuItemVO[] filter(CpMenuItemVO[] menuitems){
		return menuitems;
	}

}
