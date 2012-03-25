package nc.uap.cpb.org.itf;

import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.vos.CpExtension;
import nc.uap.cpb.org.vos.CpExtensionPoint;

/**
 * �������
 * 
 * @author licza
 * @since 2010��9��10��10:53:18
 */
public interface ICpPluginBill {
	/**
	 * ����һ����չ��
	 * 
	 * @param ex
	 * @throws CpbBusinessException
	 */
	public void add(CpExtensionPoint ex) throws CpbBusinessException;

	/**
	 * ����һ����չ��
	 * 
	 * @param exs
	 * @throws CpbBusinessException
	 */
	public void add(CpExtensionPoint[] exs) throws CpbBusinessException;

	/**
	 * �޸�һ����չ��
	 * 
	 * @param ex
	 * @throws CpbBusinessException
	 */
	public void update(CpExtensionPoint ex) throws CpbBusinessException;

	/**
	 * �޸�һ����չ��
	 * 
	 * @param exs
	 * @throws CpbBusinessException
	 */
	public void update(CpExtensionPoint[] exs) throws CpbBusinessException;

	/**
	 * ɾ��һ����չ��
	 * 
	 * @param ex
	 * @throws CpbBusinessException
	 */
	public void delete(CpExtensionPoint ex) throws CpbBusinessException;

	/**
	 * ɾ��һ����չ��
	 * 
	 * @param exs
	 * @throws CpbBusinessException
	 */
	public void delete(CpExtensionPoint[] exs) throws CpbBusinessException;

	/**
	 * ����һ����չ
	 * 
	 * @param ex
	 * @throws CpbBusinessException
	 */
	public void add(CpExtension ex) throws CpbBusinessException;

	/**
	 * ����һ����չ
	 * 
	 * @param exs
	 * @throws CpbBusinessException
	 */
	public void add(CpExtension[] exs) throws CpbBusinessException;

	/**
	 * �޸�һ����չ
	 * 
	 * @param ex
	 * @throws CpbBusinessException
	 */
	public void update(CpExtension ex) throws CpbBusinessException;

	/**
	 * �޸�һ����չ
	 * 
	 * @param exs
	 * @throws CpbBusinessException
	 */
	public void update(CpExtension[] exs) throws CpbBusinessException;

	/**
	 * ɾ��һ����չ
	 * 
	 * @param ex
	 * @throws CpbBusinessException
	 */
	public void delete(CpExtension ex) throws CpbBusinessException;

	/**
	 * ɾ��һ����չ
	 * 
	 * @param exs
	 * @throws CpbBusinessException
	 */
	public void delete(CpExtension[] exs) throws CpbBusinessException;
	/**
	 * ����һ��ģ���µĲ��
	 * @param moduleName
	 * @throws CpbBusinessException
	 */
	public void clearModule(String moduleName)  throws CpbBusinessException;
}
