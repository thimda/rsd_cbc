package nc.uap.cpb.org.itf;

import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.portal.plugins.model.PtExtension;
import nc.uap.portal.plugins.model.PtExtensionPoint;

/**
 * �������
 * 
 * @author licza
 * @since 2010��9��10��10:53:18
 */
public interface IPtPluginService {
	/**
	 * ����һ����չ��
	 * 
	 * @param ex
	 * @throws CpbBusinessException
	 */
	public void add(PtExtensionPoint ex) throws CpbBusinessException;

	/**
	 * ����һ����չ��
	 * 
	 * @param exs
	 * @throws CpbBusinessException
	 */
	public void add(PtExtensionPoint[] exs) throws CpbBusinessException;

	/**
	 * �޸�һ����չ��
	 * 
	 * @param ex
	 * @throws CpbBusinessException
	 */
	public void update(PtExtensionPoint ex) throws CpbBusinessException;

	/**
	 * �޸�һ����չ��
	 * 
	 * @param exs
	 * @throws CpbBusinessException
	 */
	public void update(PtExtensionPoint[] exs) throws CpbBusinessException;

	/**
	 * ɾ��һ����չ��
	 * 
	 * @param ex
	 * @throws CpbBusinessException
	 */
	public void delete(PtExtensionPoint ex) throws CpbBusinessException;

	/**
	 * ɾ��һ����չ��
	 * 
	 * @param exs
	 * @throws CpbBusinessException
	 */
	public void delete(PtExtensionPoint[] exs) throws CpbBusinessException;

	/**
	 * ����һ����չ
	 * 
	 * @param ex
	 * @throws CpbBusinessException
	 */
	public void add(PtExtension ex) throws CpbBusinessException;

	/**
	 * ����һ����չ
	 * 
	 * @param exs
	 * @throws CpbBusinessException
	 */
	public void add(PtExtension[] exs) throws CpbBusinessException;

	/**
	 * �޸�һ����չ
	 * 
	 * @param ex
	 * @throws CpbBusinessException
	 */
	public void update(PtExtension ex) throws CpbBusinessException;

	/**
	 * �޸�һ����չ
	 * 
	 * @param exs
	 * @throws CpbBusinessException
	 */
	public void update(PtExtension[] exs) throws CpbBusinessException;

	/**
	 * ɾ��һ����չ
	 * 
	 * @param ex
	 * @throws CpbBusinessException
	 */
	public void delete(PtExtension ex) throws CpbBusinessException;

	/**
	 * ɾ��һ����չ
	 * 
	 * @param exs
	 * @throws CpbBusinessException
	 */
	public void delete(PtExtension[] exs) throws CpbBusinessException;
	/**
	 * ����һ��ģ���µĲ��
	 * @param moduleName
	 * @throws CpbBusinessException
	 */
	public void clearModule(String moduleName)  throws CpbBusinessException;
}
