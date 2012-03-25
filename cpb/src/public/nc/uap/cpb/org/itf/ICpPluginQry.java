package nc.uap.cpb.org.itf;

import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.vos.CpExtension;
import nc.uap.cpb.org.vos.CpExtensionPoint;

/**
 * 插件查询
 * 
 * @author licza
 * 
 */
public interface ICpPluginQry {
	/**
	 * 获得所有扩展
	 * 
	 * @param module 模块名
	 * @throws CpbBusinessException
	 * @return 扩展列表
	 */
	public CpExtension[] getAllExtension(String module) throws CpbBusinessException;

	/**
	 * 获得所有扩展
	 * 
	 * @throws CpbBusinessException
	 * @return 扩展列表
	 */
	public CpExtension[] getAllExtension() throws CpbBusinessException;

	/**
	 * 获得扩展点下的扩展
	 * 
	 * @param point 扩展点ID
	 * @return 扩展列表
	 * @throws CpbBusinessException
	 */
	public CpExtension[] getExtensionByPoint(String point) throws CpbBusinessException;

	/**
	 * 获得所有扩展点
	 * 
	 * @return
	 * @throws CpbBusinessException
	 */
	public CpExtensionPoint[] getAllExtensionPoint() throws CpbBusinessException;
	/**
	 * 根据扩展主键获得扩展
	 * @param pk_extension
	 * @return
	 * @throws CpbBusinessException
	 */
	public CpExtension getExtension(String pk_extension) throws CpbBusinessException;
	
}
