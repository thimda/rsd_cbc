package nc.uap.cpb.org.menuitem;

import nc.uap.lfw.core.comp.MenuItem;
import nc.uap.lfw.core.comp.MenubarComp;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.event.ctx.LfwPageContext;
import nc.vo.pub.lang.UFBoolean;

/**
 * 菜单注册节点-菜单管理工具类
 * 2011-10-12 下午02:59:09  limingf
 */

public class MenuUtil{
	public static void setMenuState(Dataset ds,LfwPageContext context){		
		Row row = ds.getSelectedRow();
		UFBoolean isnotleaf = row.getUFBoolean(ds.nameToIndex("isleaf"));
		
		MenubarComp menubar = context.getPageMeta().getViewMenus().getMenuBar("menu");
		MenuItem add = menubar.getItem("add");
		
		if(isnotleaf!=null&&Boolean.TRUE.equals(isnotleaf.booleanValue()))
			add.setEnabled(true);
		else add.setEnabled(false);
	}

}
