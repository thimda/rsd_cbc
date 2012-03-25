package nc.uap.cpb.org.itf;

import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.portal.plugins.model.PtExtension;
import nc.uap.portal.plugins.model.PtExtensionPoint;

/**
 * 插件管理
 * 
 * @author licza
 * @since 2010年9月10日10:53:18
 */
public interface IPtPluginService {
	/**
	 * 增加一个扩展点
	 * 
	 * @param ex
	 * @throws CpbBusinessException
	 */
	public void add(PtExtensionPoint ex) throws CpbBusinessException;

	/**
	 * 增加一组扩展点
	 * 
	 * @param exs
	 * @throws CpbBusinessException
	 */
	public void add(PtExtensionPoint[] exs) throws CpbBusinessException;

	/**
	 * 修改一个扩展点
	 * 
	 * @param ex
	 * @throws CpbBusinessException
	 */
	public void update(PtExtensionPoint ex) throws CpbBusinessException;

	/**
	 * 修改一组扩展点
	 * 
	 * @param exs
	 * @throws CpbBusinessException
	 */
	public void update(PtExtensionPoint[] exs) throws CpbBusinessException;

	/**
	 * 删除一个扩展点
	 * 
	 * @param ex
	 * @throws CpbBusinessException
	 */
	public void delete(PtExtensionPoint ex) throws CpbBusinessException;

	/**
	 * 删除一组扩展点
	 * 
	 * @param exs
	 * @throws CpbBusinessException
	 */
	public void delete(PtExtensionPoint[] exs) throws CpbBusinessException;

	/**
	 * 增加一个扩展
	 * 
	 * @param ex
	 * @throws CpbBusinessException
	 */
	public void add(PtExtension ex) throws CpbBusinessException;

	/**
	 * 增加一组扩展
	 * 
	 * @param exs
	 * @throws CpbBusinessException
	 */
	public void add(PtExtension[] exs) throws CpbBusinessException;

	/**
	 * 修改一个扩展
	 * 
	 * @param ex
	 * @throws CpbBusinessException
	 */
	public void update(PtExtension ex) throws CpbBusinessException;

	/**
	 * 修改一组扩展
	 * 
	 * @param exs
	 * @throws CpbBusinessException
	 */
	public void update(PtExtension[] exs) throws CpbBusinessException;

	/**
	 * 删除一个扩展
	 * 
	 * @param ex
	 * @throws CpbBusinessException
	 */
	public void delete(PtExtension ex) throws CpbBusinessException;

	/**
	 * 删除一组扩展
	 * 
	 * @param exs
	 * @throws CpbBusinessException
	 */
	public void delete(PtExtension[] exs) throws CpbBusinessException;
	/**
	 * 清理一个模块下的插件
	 * @param moduleName
	 * @throws CpbBusinessException
	 */
	public void clearModule(String moduleName)  throws CpbBusinessException;
}
