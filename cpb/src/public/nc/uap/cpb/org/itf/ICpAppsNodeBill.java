package nc.uap.cpb.org.itf;

import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.vos.CpAppsCategoryVO;
import nc.uap.cpb.org.vos.CpAppsNodeVO;


/**
 * ����ڵ����ӡ��޸Ľӿ�
 * 
 * @author licza
 * 
 */
public interface ICpAppsNodeBill {
	/**
	 * ����һ��Node
	 * 
	 * @param vo
	 * @return
	 * @throws CpApps
	 */
	public String add(CpAppsNodeVO vo) throws CpbBusinessException;

	/**
	 * ����һ��Node
	 * 
	 * @param vo
	 * @return
	 * @throws CpApps
	 */
	public void add(CpAppsNodeVO[] vo) throws CpbBusinessException;

	/**
	 * ����һ��Node
	 * 
	 * @param vo
	 * @throws CpApps
	 */
	public void update(CpAppsNodeVO vo) throws CpbBusinessException;
	
	/**
	 * ɾ��һ��Node
	 * @param vo
	 * @return
	 * @throws CpbBusinessException
	 */
	public String delNode(CpAppsNodeVO vo) throws CpbBusinessException;
	
	/**
	 * ����pkɾ��һ��Node
	 * @param pk_funnode
	 * @return
	 * @throws CpbBusinessException
	 */
	public String delNode(String pk_funnode) throws CpbBusinessException;
	
	
	/**
	 * ����һ��NodeCategory
	 * 
	 * @param vo
	 * @return
	 * @throws CpApps
	 */
	public String add(CpAppsCategoryVO vo) throws CpbBusinessException;

	/**
	 * ����һ��NodeCategory
	 * 
	 * @param vo
	 * @return
	 * @throws CpApps
	 */
	public String[] add(CpAppsCategoryVO[] vo) throws CpbBusinessException;

	/**
	 * ����һ��NodeCategory
	 * 
	 * @param vo
	 * @throws CpApps
	 */
	public void update(CpAppsCategoryVO vo) throws CpbBusinessException;
	
	/**
	 * ɾ��һ������
	 * @param vo
	 * @return
	 * @throws CpbBusinessException
	 */
	public String delAppsCategory(CpAppsCategoryVO vo) throws CpbBusinessException;
		
		/**
		 * ����pkɾ��һ��Node
		 * @param pk_funnode
		 * @return
		 * @throws CpbBusinessException
		 */
	public String delAppsCategory(String pk_category) throws CpbBusinessException;
	
//
//	/**
//	 * ����һ������Ա�ڵ�
//	 * 
//	 * @param vo
//	 * @return
//	 * @throws CpApps
//	 */
//	public String add(CpAppsTypeVO vo) throws CpbBusinessException;

//	/**
//	 * ����һ�����Ա�ڵ�
//	 * 
//	 * @param vo
//	 * @return
//	 * @throws CpApps
//	 */
//	public String[] add(CpAppsTypeVO[] vo) throws CpbBusinessException;

//	/**
//	 * ����һ������Ա�ڵ�
//	 * 
//	 * @param vo
//	 * @throws CpApps
//	 */
//	public void update(CpAppsTypeVO vo) throws CpbBusinessException;
//	
//	



}
