package nc.uap.cpb.org.itf;

import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.vos.CpResourceVO;

/**
 * Portal��Դ��ѯ����
 * 
 * @author licza
 * 
 */
public interface ICpResourceQry {

	/**
	 * ����������ѯPortal��Դ
	 * 
	 * @param wherePart
	 *            ����
	 * @return
	 * @throws PortalServiceException
	 */
	public CpResourceVO[] queryResources(String wherePart) throws CpbBusinessException;

	/**
	 * ������Դ���ͻ��ģ�����Ѿ����ڵ���Դ
	 * 
	 * @param module
	 *            ģ����
	 * @param tp
	 *            ��Դ����
	 * @return
	 */
	public CpResourceVO[] getModuleResByType(String module, Integer tp) throws CpbBusinessException;

	/**
	 * ����û���Դ
	 * 
	 * @param pk_user
	 * @return
	 * @throws PortalServiceException
	 */
	public CpResourceVO[] getResoureces(String pk_user) throws  CpbBusinessException;

	/**
	 * ���ݽ�ɫPK�����Դ
	 * 
	 * @param pkroles
	 * @return
	 * @throws PortalServiceException
	 */
	public CpResourceVO[] getResourcesByRoles(String[] pkroles) throws CpbBusinessException;
}
