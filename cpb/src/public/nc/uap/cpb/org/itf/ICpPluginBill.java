package nc.uap.cpb.org.itf;

import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.vos.CpExtension;
import nc.uap.cpb.org.vos.CpExtensionPoint;

/**
 * 插件管理
 * 
 * @author licza
 * @since 2010年9月10日10:53:18
 */
public interface ICpPluginBill {
	/**
	 * 增加一个扩展点
	 * 
	 * @param ex
	 * @throws CpbBusinessException
	 */
	public void add(CpExtensionPoint ex) throws CpbBusinessException;

	/**
	 * 增加一组扩展点
	 * 
	 * @param exs
	 * @throws CpbBusinessException
	 */
	public void add(CpExtensionPoint[] exs) throws CpbBusinessException;

	/**
	 * 修改一个扩展点
	 * 
	 * @param ex
	 * @throws CpbBusinessException
	 */
	public void update(CpExtensionPoint ex) throws CpbBusinessException;

	/**
	 * 修改一组扩展点
	 * 
	 * @param exs
	 * @throws CpbBusinessException
	 */
	public void update(CpExtensionPoint[] exs) throws CpbBusinessException;

	/**
	 * 删除一个扩展点
	 * 
	 * @param ex
	 * @throws CpbBusinessException
	 */
	public void delete(CpExtensionPoint ex) throws CpbBusinessException;

	/**
	 * 删除一组扩展点
	 * 
	 * @param exs
	 * @throws CpbBusinessException
	 */
	public void delete(CpExtensionPoint[] exs) throws CpbBusinessException;

	/**
	 * 增加一个扩展
	 * 
	 * @param ex
	 * @throws CpbBusinessException
	 */
	public void add(CpExtension ex) throws CpbBusinessException;

	/**
	 * 增加一组扩展
	 * 
	 * @param exs
	 * @throws CpbBusinessException
	 */
	public void add(CpExtension[] exs) throws CpbBusinessException;

	/**
	 * 修改一个扩展
	 * 
	 * @param ex
	 * @throws CpbBusinessException
	 */
	public void update(CpExtension ex) throws CpbBusinessException;

	/**
	 * 修改一组扩展
	 * 
	 * @param exs
	 * @throws CpbBusinessException
	 */
	public void update(CpExtension[] exs) throws CpbBusinessException;

	/**
	 * 删除一个扩展
	 * 
	 * @param ex
	 * @throws CpbBusinessException
	 */
	public void delete(CpExtension ex) throws CpbBusinessException;

	/**
	 * 删除一组扩展
	 * 
	 * @param exs
	 * @throws CpbBusinessException
	 */
	public void delete(CpExtension[] exs) throws CpbBusinessException;
	/**
	 * 清理一个模块下的插件
	 * @param moduleName
	 * @throws CpbBusinessException
	 */
	public void clearModule(String moduleName)  throws CpbBusinessException;
}
