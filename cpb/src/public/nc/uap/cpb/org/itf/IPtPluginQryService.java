package nc.uap.cpb.org.itf;

import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.portal.plugins.model.PtExtension;
import nc.uap.portal.plugins.model.PtExtensionPoint;

/**
 * �����ѯ
 * 
 * @author licza
 * 
 */
public interface IPtPluginQryService {
	/**
	 * ���������չ
	 * 
	 * @param module ģ����
	 * @throws CpbBusinessException
	 * @return ��չ�б�
	 */
	public PtExtension[] getAllExtension(String module) throws CpbBusinessException;

	/**
	 * ���������չ
	 * 
	 * @throws CpbBusinessException
	 * @return ��չ�б�
	 */
	public PtExtension[] getAllExtension() throws CpbBusinessException;

	/**
	 * �����չ���µ���չ
	 * 
	 * @param point ��չ��ID
	 * @return ��չ�б�
	 * @throws CpbBusinessException
	 */
	public PtExtension[] getExtensionByPoint(String point) throws CpbBusinessException;

	/**
	 * ���������չ��
	 * 
	 * @return
	 * @throws CpbBusinessException
	 */
	public PtExtensionPoint[] getAllExtensionPoint() throws CpbBusinessException;
	/**
	 * ������չ���������չ
	 * @param pk_extension
	 * @return
	 * @throws CpbBusinessException
	 */
	public PtExtension getExtension(String pk_extension) throws CpbBusinessException;
	
}
