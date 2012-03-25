package nc.uap.cpb.persist.dao;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.List;
import nc.bs.dao.DAOException;
import nc.bs.logging.Logger;
import nc.bs.trade.comdelete.BillDelete;
import nc.jdbc.framework.JdbcSession;
import nc.jdbc.framework.PersistenceManager;
import nc.jdbc.framework.SQLParameter;
import nc.jdbc.framework.exception.DbException;
import nc.jdbc.framework.generator.SequenceGenerator;
import nc.jdbc.framework.mapping.IMappingMeta;
import nc.jdbc.framework.processor.ResultSetProcessor;
import nc.uap.cpb.org.util.CpbServiceFacility;
import nc.uap.cpb.org.util.CpbUtil;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.vo.org.GroupVO;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
/**
 * 
 * �����֯�µĻ�������
 * 
 * @author zhangxya
 * 
 */
public class PtBaseDAO {
	private String dataSource = null;
	/**
	 * Ĭ�Ϲ��캯������ʹ�õ�ǰ����Դ
	 */
	public PtBaseDAO() {}
	/**
	 * �вι��캯������ʹ��ָ������Դ
	 * 
	 * @param dataSource
	 *            ����Դ����
	 */
	public PtBaseDAO(String dataSource) {
		this.dataSource = dataSource;
	}
	/**
	 * ����������ѯ����
	 * 
	 * @param voClass
	 * @param strWhere
	 * @return
	 * @throws DAOException
	 */
	public SuperVO[] queryByCondition(Class voClass, String strWhere) throws DAOException {
		if (strWhere != null && strWhere.length() != 0)
			strWhere = " (isnull(dr,0)=0) and " + strWhere;
		else
			strWhere = "isnull(dr,0)=0";
		PersistenceManager manager = null;
		try {
			manager = createPersistenceManager(dataSource);
			List list = (List) manager.retrieveByClause(voClass, strWhere);
			return (SuperVO[]) list.toArray((SuperVO[]) Array.newInstance(voClass, 0));
		} catch (DbException e) {
			Logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			if (manager != null)
				manager.release();
		}
	}
	/**
	 * ����һ��VO���������VO������ֵ�ǿ������VO��ԭ������
	 * 
	 * @param vo
	 * @return
	 * @throws DAOException
	 */
	public String insertVOWithPK(SuperVO vo) throws DAOException {
		PersistenceManager manager = null;
		String pk = null;
		try {
			manager = createPersistenceManager(dataSource);;
			pk = manager.insertWithPK(vo);
		} catch (DbException e) {
			Logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			if (manager != null)
				manager.release();
		}
		return pk;
	}
	public String[] insertVOWithPKs(SuperVO[] vos) throws DAOException {
		PersistenceManager manager = null;
		String[] pk = null;
		try {
			manager = createPersistenceManager(dataSource);;
			pk = manager.insertWithPK(vos);
		} catch (DbException e) {
			Logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			if (manager != null)
				manager.release();
		}
		return pk;
	}
	/**
	 * ����IMappingMeta����һ��VO���󣬸�VO������ֵ�ǿ������VO��ԭ������
	 * 
	 * @param vo
	 *            VO����
	 * @param meta
	 *            IMappingMeta
	 * @return
	 * @throws DAOException
	 */
	public String insertObjectWithPK(Object vo, IMappingMeta meta) throws DAOException {
		PersistenceManager manager = null;
		try {
			manager = createPersistenceManager(dataSource);;
			return manager.insertObjectWithPK(vo, meta);
		} catch (DbException e) {
			Logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			if (manager != null)
				manager.release();
		}
	}
	/**
	 * ����IMappingMeta����VO���󼯺ϣ���VO������ֵ�ǿ������VO��ԭ������
	 * 
	 * @param vo
	 *            VO���󼯺�
	 * @param meta
	 *            IMappingMeta
	 * @return
	 * @throws DAOException
	 */
	public String[] insertObjectWithPK(Object[] vo, IMappingMeta meta) throws DAOException {
		PersistenceManager manager = null;
		try {
			manager = createPersistenceManager(dataSource);;
			return manager.insertObjectWithPK(vo, meta);
		} catch (DbException e) {
			Logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			if (manager != null)
				manager.release();
		}
	}
	/**
	 * ����vo
	 * 
	 * @param vo
	 * @return
	 * @throws DAOException
	 */
	public String insertVO(SuperVO vo) throws DAOException {
		if (vo == null)
			return null;
		return insertVOs(new SuperVO[] { vo })[0];
	}
	/**
	 * ����һ��vo
	 * 
	 * @param vos
	 * @return
	 * @throws DAOException
	 */
	public String[] insertVOs(SuperVO[] vos) throws DAOException {
		if (vos == null || vos.length == 0)
			return null;
		PersistenceManager manager = null;
		try {
			manager = createPersistenceManager(dataSource);
			
			// ����pk
			String[] pks = generatePKs(dataSource,vos.length);
			for (int i = 0; i < vos.length; i++) {
				vos[i].setPrimaryKey(pks[i]);
			}
			return manager.insertWithPK(vos);
		} catch (DbException e) {
			Logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			if (manager != null)
				manager.release();
		}
	}
	public static String[] generatePKs(String _dataSource,int len){
		String groupno = getGroupNO();
		return new SequenceGenerator(_dataSource).generate(groupno, len);
	}
	public static String generatePK(String _dataSource){
		String groupno = getGroupNO();
		return new SequenceGenerator(_dataSource).generate(groupno, 1)[0];
	}
	private static String getGroupNO() {
		String groupNo = "";
		GroupVO[] groupVos = null;
		try {
			groupVos = CpbServiceFacility.getCpGroupQry().queryGroupVOsByPKS(new String[] { CpbUtil.getCntUserPk() });
		} catch (BusinessException e) {
			Logger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
		if (groupVos != null && groupVos.length > 0) {
			groupNo = groupVos[0].getGroupno();
		} else {
			groupNo = "0000";
		}
		return groupNo;
	}
	/**
	 * ����VO����������ݿ�
	 * 
	 * @param vo
	 *            VO����
	 */
	public int updateVO(SuperVO vo) throws DAOException {
		return updateVOArray(new SuperVO[] { vo });
	}
	/**
	 * ����VO��������������ݿ�
	 * 
	 * @param vo
	 *            VO����
	 */
	public int updateVOArray(SuperVO[] vos) throws DAOException {
		return updateVOArray(vos, null);
	}
	/**
	 * ����VO����������ָ���и������ݿ�
	 * 
	 * @param vos
	 *            VO����
	 * @param fieldNames
	 *            ָ����
	 */
	public int updateVOArray(SuperVO[] vos, String[] fieldNames) throws DAOException {
		return updateVOArray(vos, fieldNames, null, null);
	}
	public int updateVOArray(final SuperVO[] vos, String[] fieldNames, String whereClause, SQLParameter param) throws DAOException {
		PersistenceManager manager = null;
		try {
			manager = createPersistenceManager(dataSource);
			return manager.update(vos, fieldNames, whereClause, param);
		} catch (DbException e) {
			Logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			if (manager != null)
				manager.release();
		}
	}
	/**
	 * �����ݿ���ɾ��һ��VO����
	 * 
	 * @param vo
	 *            VO����
	 * @throws DAOException
	 *             ���ɾ�������׳�DAOException
	 */
	public void deleteVO(SuperVO vo) throws DAOException {
		PersistenceManager manager = null;
		try {
			manager = createPersistenceManager(dataSource);
			manager.delete(vo);
		} catch (DbException e) {
			Logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			if (manager != null)
				manager.release();
		}
	}
	/**
	 * �����ݿ���ɾ��һ��VO����
	 * 
	 * @param vos
	 *            VO�������
	 * @throws DAOException
	 *             ���ɾ�������׳�DAOException
	 */
	public void deleteVOArray(SuperVO[] vos) throws DAOException {
		PersistenceManager manager = null;
		try {
			manager = createPersistenceManager(dataSource);
			manager.delete(vos);
		} catch (DbException e) {
			Logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			if (manager != null)
				manager.release();
		}
	}
	/**
	 * �����ݿ��и���������PKɾ��һ��VO���󼯺�
	 * 
	 * @param className
	 *            VO����
	 * @param pk
	 *            PKֵ
	 * @throws DAOException
	 *             ���ɾ�������׳�DAOException
	 */
	public void deleteByPK(Class className, String pk) throws DAOException {
		PersistenceManager manager = null;
		try {
			manager = createPersistenceManager(dataSource);
			manager.deleteByPK(className, pk);
		} catch (DbException e) {
			Logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			if (manager != null)
				manager.release();
		}
	}
	/**
	 * �����ݿ��и�������������ɾ������
	 * 
	 * @param className
	 *            VO����
	 * @param wherestr
	 *            ����
	 * @throws DAOException
	 *             ���ɾ�������׳�DAOException
	 */
	public void deleteByClause(Class className, String wherestr) throws DAOException {
		PersistenceManager manager = null;
		try {
			manager = createPersistenceManager(dataSource);
			manager.deleteByClause(className, wherestr);
		} catch (DbException e) {
			Logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			if (manager != null)
				manager.release();
		}
	}
	public void deleteByClause(Class className, String wherestr, SQLParameter params) throws DAOException {
		PersistenceManager manager = null;
		try {
			manager = createPersistenceManager(dataSource);
			manager.deleteByClause(className, wherestr, params);
		} catch (DbException e) {
			Logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			if (manager != null)
				manager.release();
		}
	}
	/**
	 * �����ݿ��и���������PK����ɾ��һ��VO���󼯺�
	 * 
	 * @param className
	 *            Ҫɾ����VO����
	 * @param pks
	 *            PK����
	 * @throws DAOException
	 *             ���ɾ�������׳�DAOException
	 */
	public void deleteByPKs(Class className, String[] pks) throws DAOException {
		PersistenceManager manager = null;
		try {
			manager = createPersistenceManager(dataSource);
			manager.deleteByPKs(className, pks);
		} catch (DbException e) {
			Logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			if (manager != null)
				manager.release();
		}
	}
	/**
	 * ����SQL ִ�����ݿ��ѯ,������ResultSetProcessor�����Ķ��� ���� Javadoc��
	 * 
	 * @param sql
	 *            ��ѯ��SQL
	 * @param processor
	 *            �����������
	 */
	public Object executeQuery(String sql, ResultSetProcessor processor) throws DAOException {
		PersistenceManager manager = null;
		Object value = null;
		try {
			manager = createPersistenceManager(dataSource);
			JdbcSession session = manager.getJdbcSession();
			value = session.executeQuery(sql, processor);
		} catch (DbException e) {
			Logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			if (manager != null)
				manager.release();
		}
		return value;
	}
	/**
	 * ����ָ��SQL ִ���в��������ݿ��ѯ,������ResultSetProcessor�����Ķ���
	 * 
	 * @param sql
	 *            ��ѯ��SQL
	 * @param parameter
	 *            ��ѯ����
	 * @param processor
	 *            �����������
	 */
	public Object executeQuery(String sql, SQLParameter parameter, ResultSetProcessor processor) throws DAOException {
		PersistenceManager manager = null;
		Object value = null;
		try {
			manager = createPersistenceManager(dataSource);;
			JdbcSession session = manager.getJdbcSession();
			value = session.executeQuery(sql, parameter, processor);
		} catch (DbException e) {
			Logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			if (manager != null)
				manager.release();
		}
		return value;
	}
	/**
	 * ����VO������where��������vo����
	 * 
	 * @param className
	 *            Vo������
	 * @param condition
	 *            ��ѯ����
	 * @return ����Vo����
	 * @throws DAOException
	 *             ���������׳�DAOException
	 */
	public Collection retrieveByClause(Class className, String condition) throws DAOException {
		PersistenceManager manager = null;
		Collection values = null;
		try {
			manager = createPersistenceManager(dataSource);
			values = manager.retrieveByClause(className, condition);
		} catch (DbException e) {
			Logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			if (manager != null)
				manager.release();
		}
		return values;
	}
	/**
	 * ����PK��ѯָ��VO
	 * 
	 * @param VO����
	 * @param pk
	 *            ����
	 * 
	 */
	public Object retrieveByPK(Class className, String pk) throws DAOException {
		PersistenceManager manager = null;
		Object values = null;
		try {
			manager = createPersistenceManager(dataSource);
			values = manager.retrieveByPK(className, pk);
		} catch (DbException e) {
			Logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			if (manager != null)
				manager.release();
		}
		return values;
	}
	/**
	 * ����ָ��SQL ִ���в��������ݿ���²���
	 * 
	 * @param sql
	 *            ���µ�sql
	 * @param parameter
	 *            ���²���
	 * @return
	 * @throws DAOException
	 *             ���·��������׳�DAOException
	 */
	public int executeUpdate(String sql, SQLParameter parameter) throws DAOException {
		PersistenceManager manager = null;
		int value;
		try {
			manager = createPersistenceManager(dataSource);
			JdbcSession session = manager.getJdbcSession();
			value = session.executeUpdate(sql, parameter);
		} catch (DbException e) {
			Logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			if (manager != null)
				manager.release();
		}
		return value;
	}
	/**
	 * ����ָ��SQL ִ���޲��������ݿ���²���
	 * 
	 * @param sql
	 *            ���µ�sql
	 * @return
	 * @throws DAOException
	 *             ���·��������׳�DAOException
	 */
	public int executeUpdate(String sql) throws DAOException {
		PersistenceManager manager = null;
		int value;
		try {
			manager = createPersistenceManager(dataSource);
			JdbcSession session = manager.getJdbcSession();
			value = session.executeUpdate(sql);
		} catch (DbException e) {
			Logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			if (manager != null)
				manager.release();
		}
		return value;
	}
	/**
	 * ɾ���ۺ�VO
	 * 
	 * @param billVo
	 * @throws BusinessException
	 */
	public void deleteAggVO(AggregatedValueObject billVo) throws BusinessException {
		new BillDelete().deleteBill(billVo);
	}
	private PersistenceManager createPersistenceManager(String ds) throws DbException {
		PersistenceManager manager = PersistenceManager.getInstance(ds);
		return manager;
	}
	/**
	 * ����VO������ѯ��VO��Ӧ�����������
	 * 
	 * @param className
	 *            SuperVo����
	 * 
	 * @return
	 * @throws DAOException
	 *             ���������׳�DAOException
	 */
	public Collection retrieveAll(Class className) throws DAOException {
		PersistenceManager manager = null;
		Collection values = null;
		try {
			manager = createPersistenceManager(dataSource);
			values = manager.retrieveAll(className);
		} catch (DbException e) {
			Logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			if (manager != null)
				manager.release();
		}
		return values;
	}
	/**
	 * ����VO�����MappingMeta��Ϣ�������ݿ�
	 * 
	 * @param vo
	 *            VO����
	 * @param meta
	 *            MappingMeta��Ϣ
	 */
	public int updateObject(Object vo, IMappingMeta meta) throws DAOException {
		return updateObject(vo, meta, null);
	}
	/**
	 * ���� IMappingMeta����������VO�����Ӧ�����ݿ�
	 * 
	 * @param vos
	 *            VO����
	 * @param meta
	 *            IMappingMeta
	 * @param whereClause
	 *            �������
	 * @return
	 * @throws DAOException
	 */
	public int updateObject(Object vo, IMappingMeta meta, String whereClause) throws DAOException {
		PersistenceManager manager = null;
		try {
			manager = createPersistenceManager(dataSource);
			return manager.updateObject(vo, meta, whereClause);
		} catch (DbException e) {
			Logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			if (manager != null)
				manager.release();
		}
	}
	/**
	 * ����IMappingMeta����һ��VO����
	 * 
	 * @param vo
	 *            VO����
	 * @param meta
	 *            IMappingMeta
	 */
	public String insertObject(Object vo, IMappingMeta meta) throws DAOException {
		PersistenceManager manager = null;
		try {
			manager = createPersistenceManager(dataSource);;
			return manager.insertObject(vo, meta);
		} catch (DbException e) {
			Logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			if (manager != null)
				manager.release();
		}
	}
}
