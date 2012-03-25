package nc.uap.cpb.org.itf;

import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.vos.MenuAdapterVO;

/**
 * 菜单适配查询接口
 * 2011-12-26 下午04:02:03
 * @author limingf
 *
 */
public interface ICpMenuQryService {
	/**
	 * 获取所有菜单项，包含菜单分组,菜单项
	 * @return
	 * @throws CpbBusinessException
	 */
	public MenuAdapterVO[] getAllMenus() throws CpbBusinessException ;
	
	/**
	 * 获取所有菜单分组
	 * @return
	 * @throws CpbBusinessException
	 */
	public MenuAdapterVO[] getAllMenuCategorys() throws CpbBusinessException ;
}
