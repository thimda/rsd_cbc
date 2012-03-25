package nc.uap.cpb.org.itf;
import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.vo.pub.SuperVO;
/**
 * SuperVO子类增删改统一接口(如果需要扩展实现的操作不能通过调用该接口) 2011-6-8 下午03:55:03 limingf
 */
public interface ICpSuperVOBill {
	/**
	 * 在数据库中插入一组VO对象
	 * 
	 * @param supervo
	 * @return
	 * @throws PortalServiceException
	 */
	public String insertSuperVO(SuperVO vo) throws CpbBusinessException;
	/**
	 * 合并数据，如果数据存在则更新，否则创建
	 * 
	 * @param vo
	 * @return
	 * @throws CpbBusinessException
	 */
	public String mergeSuperVO(SuperVO vo) throws CpbBusinessException;
	/**
	 * 在数据库中插入一组VO对象。
	 * 
	 * @param supervos
	 * @return
	 * @throws PortalServiceException
	 */
	public String[] insertSuperVOs(SuperVO[] vos) throws CpbBusinessException;
	/**
	 * 在数据库中删除一组VO对象。
	 * 
	 * @param supervo
	 * @throws PortalServiceException
	 */
	public void updateSuperVO(SuperVO vo) throws CpbBusinessException;
	/**
	 * 在数据库中删除一组VO对象。
	 * 
	 * @param supervos
	 * @throws PortalServiceException
	 */
	public void updateSuperVOs(SuperVO[] vos) throws CpbBusinessException;
	/**
	 * 在数据库中删除一组VO对象。
	 * 
	 * @param supervo
	 * @throws PortalServiceException
	 */
	public void deleteSuperVO(SuperVO vo) throws CpbBusinessException;
	/**
	 * 根据PK删除
	 * 
	 * @param className
	 * @param pk
	 * @throws CpbBusinessException
	 */
	public void deleteByPK(Class<?> className, String pk) throws CpbBusinessException;
	/**
	 * 在数据库中删除一组VO对象。
	 * 
	 * @param supervos
	 * @throws PortalServiceException
	 */
	public void deleteSuperVOs(SuperVO[] vos) throws CpbBusinessException;
}
