package nc.uap.cpb.org.itf;

import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.vos.CpExtension;
import nc.uap.cpb.org.vos.CpExtensionPoint;

/**
 * �����ѯ
 * 
 * @author licza
 * 
 */
public interface ICpPluginQry {
	/**
	 * ���������չ
	 * 
	 * @param module ģ����
	 * @throws CpbBusinessException
	 * @return ��չ�б�
	 */
	public CpExtension[] getAllExtension(String module) throws CpbBusinessException;

	/**
	 * ���������չ
	 * 
	 * @throws CpbBusinessException
	 * @return ��չ�б�
	 */
	public CpExtension[] getAllExtension() throws CpbBusinessException;

	/**
	 * �����չ���µ���չ
	 * 
	 * @param point ��չ��ID
	 * @return ��չ�б�
	 * @throws CpbBusinessException
	 */
	public CpExtension[] getExtensionByPoint(String point) throws CpbBusinessException;

	/**
	 * ���������չ��
	 * 
	 * @return
	 * @throws CpbBusinessException
	 */
	public CpExtensionPoint[] getAllExtensionPoint() throws CpbBusinessException;
	/**
	 * ������չ���������չ
	 * @param pk_extension
	 * @return
	 * @throws CpbBusinessException
	 */
	public CpExtension getExtension(String pk_extension) throws CpbBusinessException;
	
}
