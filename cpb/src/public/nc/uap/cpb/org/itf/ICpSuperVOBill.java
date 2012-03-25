package nc.uap.cpb.org.itf;
import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.vo.pub.SuperVO;
/**
 * SuperVO������ɾ��ͳһ�ӿ�(�����Ҫ��չʵ�ֵĲ�������ͨ�����øýӿ�) 2011-6-8 ����03:55:03 limingf
 */
public interface ICpSuperVOBill {
	/**
	 * �����ݿ��в���һ��VO����
	 * 
	 * @param supervo
	 * @return
	 * @throws PortalServiceException
	 */
	public String insertSuperVO(SuperVO vo) throws CpbBusinessException;
	/**
	 * �ϲ����ݣ�������ݴ�������£����򴴽�
	 * 
	 * @param vo
	 * @return
	 * @throws CpbBusinessException
	 */
	public String mergeSuperVO(SuperVO vo) throws CpbBusinessException;
	/**
	 * �����ݿ��в���һ��VO����
	 * 
	 * @param supervos
	 * @return
	 * @throws PortalServiceException
	 */
	public String[] insertSuperVOs(SuperVO[] vos) throws CpbBusinessException;
	/**
	 * �����ݿ���ɾ��һ��VO����
	 * 
	 * @param supervo
	 * @throws PortalServiceException
	 */
	public void updateSuperVO(SuperVO vo) throws CpbBusinessException;
	/**
	 * �����ݿ���ɾ��һ��VO����
	 * 
	 * @param supervos
	 * @throws PortalServiceException
	 */
	public void updateSuperVOs(SuperVO[] vos) throws CpbBusinessException;
	/**
	 * �����ݿ���ɾ��һ��VO����
	 * 
	 * @param supervo
	 * @throws PortalServiceException
	 */
	public void deleteSuperVO(SuperVO vo) throws CpbBusinessException;
	/**
	 * ����PKɾ��
	 * 
	 * @param className
	 * @param pk
	 * @throws CpbBusinessException
	 */
	public void deleteByPK(Class<?> className, String pk) throws CpbBusinessException;
	/**
	 * �����ݿ���ɾ��һ��VO����
	 * 
	 * @param supervos
	 * @throws PortalServiceException
	 */
	public void deleteSuperVOs(SuperVO[] vos) throws CpbBusinessException;
}
