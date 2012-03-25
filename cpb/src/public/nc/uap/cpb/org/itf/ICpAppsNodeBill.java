package nc.uap.cpb.org.itf;

import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.vos.CpAppsCategoryVO;
import nc.uap.cpb.org.vos.CpAppsNodeVO;


/**
 * 管理节点增加、修改接口
 * 
 * @author licza
 * 
 */
public interface ICpAppsNodeBill {
	/**
	 * 增加一个Node
	 * 
	 * @param vo
	 * @return
	 * @throws CpApps
	 */
	public String add(CpAppsNodeVO vo) throws CpbBusinessException;

	/**
	 * 增加一组Node
	 * 
	 * @param vo
	 * @return
	 * @throws CpApps
	 */
	public void add(CpAppsNodeVO[] vo) throws CpbBusinessException;

	/**
	 * 更新一个Node
	 * 
	 * @param vo
	 * @throws CpApps
	 */
	public void update(CpAppsNodeVO vo) throws CpbBusinessException;
	
	/**
	 * 删除一个Node
	 * @param vo
	 * @return
	 * @throws CpbBusinessException
	 */
	public String delNode(CpAppsNodeVO vo) throws CpbBusinessException;
	
	/**
	 * 根据pk删除一个Node
	 * @param pk_funnode
	 * @return
	 * @throws CpbBusinessException
	 */
	public String delNode(String pk_funnode) throws CpbBusinessException;
	
	
	/**
	 * 增加一个NodeCategory
	 * 
	 * @param vo
	 * @return
	 * @throws CpApps
	 */
	public String add(CpAppsCategoryVO vo) throws CpbBusinessException;

	/**
	 * 增加一个NodeCategory
	 * 
	 * @param vo
	 * @return
	 * @throws CpApps
	 */
	public String[] add(CpAppsCategoryVO[] vo) throws CpbBusinessException;

	/**
	 * 更新一个NodeCategory
	 * 
	 * @param vo
	 * @throws CpApps
	 */
	public void update(CpAppsCategoryVO vo) throws CpbBusinessException;
	
	/**
	 * 删除一个分类
	 * @param vo
	 * @return
	 * @throws CpbBusinessException
	 */
	public String delAppsCategory(CpAppsCategoryVO vo) throws CpbBusinessException;
		
		/**
		 * 根据pk删除一个Node
		 * @param pk_funnode
		 * @return
		 * @throws CpbBusinessException
		 */
	public String delAppsCategory(String pk_category) throws CpbBusinessException;
	
//
//	/**
//	 * 增加一个管理员节点
//	 * 
//	 * @param vo
//	 * @return
//	 * @throws CpApps
//	 */
//	public String add(CpAppsTypeVO vo) throws CpbBusinessException;

//	/**
//	 * 增加一组管理员节点
//	 * 
//	 * @param vo
//	 * @return
//	 * @throws CpApps
//	 */
//	public String[] add(CpAppsTypeVO[] vo) throws CpbBusinessException;

//	/**
//	 * 更新一个管理员节点
//	 * 
//	 * @param vo
//	 * @throws CpApps
//	 */
//	public void update(CpAppsTypeVO vo) throws CpbBusinessException;
//	
//	



}
