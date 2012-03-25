package nc.uap.cpb.baseservice;

import java.lang.reflect.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import nc.bs.bd.baseservice.DefaultGetBizInfoByMDUtil;
import nc.bs.bd.baseservice.IBaseServiceConst;
import nc.bs.bd.baseservice.IGetBizInfoUtil;
import nc.bs.bd.baseservice.busilog.BDBusiLogUtil;
import nc.bs.bd.baseservice.busilog.IBDBusiLogUtil;
import nc.bs.bd.baseservice.busilog.IBusiOperateConst;
import nc.bs.bd.cache.CacheProxy;
import nc.bs.bd.service.ValueObjWithErrLog;
import nc.bs.businessevent.bd.BDCommonEventUtil;
import nc.bs.dao.BaseDAO;
import nc.bs.framework.common.InvocationInfoProxy;
import nc.bs.framework.core.service.TimeService;
import nc.bs.logging.Logger;
import nc.bs.uif2.validation.IValidationService;
import nc.bs.uif2.validation.ValidationFrameworkUtil;
import nc.bs.uif2.validation.Validator;
import nc.md.model.MetaDataException;
import nc.md.persist.framework.IMDPersistenceService;
import nc.md.persist.framework.MDPersistenceService;
import nc.uap.bd.util.SuperVOQry;
import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.data.Row;
import nc.vo.bd.pub.IPubEnumConst;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
import nc.vo.pub.VOStatus;
import nc.vo.pub.lang.UFDateTime;
import nc.vo.trade.sqlutil.IInSqlBatchCallBack;
import nc.vo.trade.sqlutil.InSqlBatchCaller;
import nc.vo.trade.voutils.VOUtil;
import nc.vo.util.AuditInfoUtil;
import nc.vo.util.BDPKLockUtil;
import nc.vo.util.BDReferenceChecker;
import nc.vo.util.BDUniqueRuleValidate;
import nc.vo.util.BDVersionValidationUtil;
import nc.vo.util.bizlock.BizlockDataUtil;

import org.apache.commons.lang.StringUtils;

/**
 * Copy from uapbd,为协同基础单据提供支持
 * 管理类型基本档案通用事务性数据库操作服务类 <br>
 * 即指树卡列表式档案 <br>
 * 注：
 * <li> 该服务适用于单个对象的操作；
 * <li> 此服务类是后台服务的基类，即只适用于后台；
 * <li> 本服务采用元数据方式持久化，因为针对“复合VO”；
 * 
 * 
 */
@SuppressWarnings("unchecked")
public class ManageTypeBaseService<T extends SuperVO> {

	/* 档案的元数据ID */
	private String MDId;
	/* 获取业务信息的工具 */
	protected IGetBizInfoUtil bizInfoUtil = new DefaultGetBizInfoByMDUtil();
	/* 联查VO的数据访问器 */
	private SuperVOQry qryDAO;
	/* 档案VO的类型 */
	private Class<?> entityClass;
	/* 对于复杂的管理型档案，需要联查出的子VO属性名 */
	private String[] subAttributeNames;
	/** 业务日志写入工具 */
	private IBDBusiLogUtil busiLogUtil = null;

	/* 获取档案VO的类型 */
	public Class<?> getEntityClass() {
		return entityClass;
	}

	/* 对于复杂的管理型档案，获取需要联查出的子VO属性名，子类必须实现该方法 */
	public String[] getSubAttributeNames() {
		return (subAttributeNames == null ? null : subAttributeNames.clone());
	}

	public ManageTypeBaseService(String MDId, Class<?> entityClass,
			String[] subAttributeNames) {
		this(MDId, null, entityClass, subAttributeNames);
	}

	public ManageTypeBaseService(String MDId, IGetBizInfoUtil bizInfoUtil,
			Class<?> entityClass, String[] subAttributeNames) {
		this.MDId = MDId;
		this.entityClass = entityClass;
		this.subAttributeNames = subAttributeNames;
		if (bizInfoUtil != null) {
			this.bizInfoUtil = bizInfoUtil;
		}
	}

	/**
	 * 设置业务日志写入接口
	 * 
	 * @param busiLogUtil
	 */
	protected void setBusiLogUtil(IBDBusiLogUtil busiLogUtil) {
		this.busiLogUtil = busiLogUtil;
	}

	/**
	 * 获得业务日志写入接口
	 * 
	 * @return
	 */
	protected IBDBusiLogUtil getBusiLogUtil() {
		if (busiLogUtil == null) {
			busiLogUtil = new BDBusiLogUtil(getMDId());
		}
		return busiLogUtil;
	}

	/**
	 * 获取档案元数据ID
	 * 
	 * @return
	 * @throws MetaDataException
	 */
	public String getMDId() {
		return MDId;
	}

	/** ***************** 删除操作 ********************** */
	/**
	 * 删除时所需的加锁操作（包括加主键锁）
	 */
	protected void deletelockOperate(T vo) throws BusinessException {
		// 主键乐观锁
		BDPKLockUtil.lockSuperVO(vo);
	}

	protected void deleteValidateVO(T vo) throws BusinessException {
		IValidationService validationService = ValidationFrameworkUtil
				.createValidationService(getDeleteValidator());
		validationService.validate(vo);
	}

	/**
	 * 定义删除校验类 <br>
	 * 子类重写该方法，根据业务需要自行定义校验集合
	 * 
	 * @return 业务校验数据
	 */
	protected Validator[] getDeleteValidator() {
		Validator[] validators = new Validator[] { BDReferenceChecker
				.getInstance() };
		return validators;
	}

	protected void fireBeforeDeleteEvent(T vo) throws BusinessException {
		BDCommonEventUtil eventUtil = new BDCommonEventUtil(getMDId());
		eventUtil.dispatchDeleteBeforeEvent(vo);
	}

	/**
	 * 管理型档案的单个删除操作
	 * 
	 * @param vo
	 * @throws BusinessException
	 */
	protected void dbDeleteVO(T vo) throws BusinessException {
		vo.setStatus(VOStatus.DELETED);
		getMDService().deleteBillFromDB(vo);
	}

	protected void notifyVersionChangeWhenDataDeleted(T vo)
			throws BusinessException {
		CacheProxy.fireDataDeleted(bizInfoUtil.getTableName(vo), vo
				.getPrimaryKey());
	}

	protected void fireAfterDeleteEvent(T vo) throws BusinessException {
		BDCommonEventUtil eventUtil = new BDCommonEventUtil(getMDId());
		eventUtil.dispatchDeleteAfterEvent(vo);
	}

	public void deleteVO(T vo) throws BusinessException {
		if (vo == null)
			return;

		// 删除时的加锁操作
		deletelockOperate(vo);

		// 校验版本
		BDVersionValidationUtil.validateSuperVO(vo);

		// 删除前引用对象校验
		deleteValidateVO(vo);

		// 删除前事件处理
		fireBeforeDeleteEvent(vo);

		// 缓存通知
		notifyVersionChangeWhenDataDeleted(vo);

		// 库操作
		dbDeleteVO(vo);

		// 删除后事件通知
		fireAfterDeleteEvent(vo);

		// 业务日志
		writeDeletedBusiLog(vo);
	}

	/**
	 * 记录删除操作的业务日志
	 * 
	 * @param vo
	 * @throws BusinessException
	 */
	protected void writeDeletedBusiLog(T vo) throws BusinessException {
		getBusiLogUtil().writeBusiLog(IBusiOperateConst.DELETE, null, vo);
	}

	/** ***************** 新增操作 ********************** */
	/**
	 * 新增时所需的加锁操作（包括业务锁，子类根据需要添加主键锁）
	 */
	protected void insertlockOperate(T vo) throws BusinessException {
		// 加业务锁
		BizlockDataUtil.lockDataByBizlock(vo);
	}

	protected void insertValidateVO(T vo) throws BusinessException {
		IValidationService validateService = ValidationFrameworkUtil
				.createValidationService(getInsertValidator());
		validateService.validate(vo);
	}

	/**
	 * 定义新增校验类 <br>
	 * 子类重写该方法，根据业务需要自行定义校验集合
	 * 
	 * @return 业务校验数据
	 */
	protected Validator[] getInsertValidator() {
		Validator[] validators = new Validator[] { new BDUniqueRuleValidate() };
		return validators;
	}

	/**
	 * 设置插入时的审计信息
	 * @param vo
	 */
	protected void setInsertAuditInfo(T vo) {
		if (vo == null)
			return;
		vo.setAttributeValue("creator", getCurrentUser());
		vo.setAttributeValue("creationtime", getCurrentTime());
	}

	protected void fireBeforeInsertEvent(T vo) throws BusinessException {
		BDCommonEventUtil eventUtil = new BDCommonEventUtil(getMDId());
		eventUtil.dispatchInsertBeforeEvent(vo);
	}

	protected String dbInsertVO(T vo) throws BusinessException {
		// 标识“新增”状态
		vo.setStatus(VOStatus.NEW);
		return (getMDService().saveBill(vo));
	}

	protected void fireAfterInsertEvent(T vo) throws BusinessException {
		BDCommonEventUtil eventUtil = new BDCommonEventUtil(getMDId());
		eventUtil.dispatchInsertAfterEvent(vo);
	}

	protected void notifyVersionChangeWhenDataInserted(T vo)
			throws BusinessException {
		CacheProxy.fireDataInserted(bizInfoUtil.getTableName(vo), null);
	}

	public T insertVO(T vo) throws BusinessException {

		if (vo == null)
			return vo;

		// 新增时的加锁操作
		insertlockOperate(vo);
		// 逻辑校验
		insertValidateVO(vo);

		// 设置审计信息
		setInsertAuditInfo(vo);

		// 插入前事件通知
		fireBeforeInsertEvent(vo);

		// 库操作
		String pk = dbInsertVO(vo);

		// 通知更新缓存
		notifyVersionChangeWhenDataInserted(vo);

		// 重新检索出插入的VO
		T newVo = retrieveVO(pk);

		//将新查出VO与旧VO进行合并
		mergeVO(vo, newVo);
		
		// 插入事件后通知
		fireAfterInsertEvent(vo);

		// 业务日志
		writeInsertBusiLog(vo);

		return vo;
	}

	/**
	 * 进行VO合并
	 * @param vo
	 * @param newVo
	 */
	protected void mergeVO(T vo, T newVo) {
		vo.setAttributeValue("ts", newVo.getAttributeValue("ts"));
		vo.setPrimaryKey(newVo.getPrimaryKey());
		vo.setStatus(Row.STATE_NORMAL);
	}

	/**
	 * 记录新增操作的业务日志
	 * 
	 * @param vo
	 * @throws BusinessException
	 */
	protected void writeInsertBusiLog(T vo) throws BusinessException {
		getBusiLogUtil().writeBusiLog(IBusiOperateConst.ADD, null, vo);
	}

	/** ***************** 更新操作 ********************** */
	protected void updatelockOperate(T vo) throws BusinessException {
		// 主键乐观锁
		BDPKLockUtil.lockSuperVO(vo);
		// 业务锁
		BizlockDataUtil.lockDataByBizlock(vo);
	}

	protected void updateValidateVO(T oldVO, T vo) throws BusinessException {
		IValidationService validateService = ValidationFrameworkUtil
				.createValidationService(getUpdateValidator(oldVO));
		validateService.validate(vo);
	}

	protected Validator[] getUpdateValidator(T oldVO) {
		Validator[] validators = new Validator[] { new BDUniqueRuleValidate() };
		return validators;
	}

	/**
	 * 设置更新的AggVO主表VO的审计信息
	 * @param vo AggVO主表VO
	 */
	protected void setUpdateAuditInfo(SuperVO vo) {
		if (vo == null)
			return;
		vo.setAttributeValue("modifier", getCurrentUser());
		vo.setAttributeValue("modifiedtime", getCurrentTime());
			
	}
	
	public static String getCurrentUser() {
		if(LfwRuntimeEnvironment.getLfwSessionBean() != null)
			return LfwRuntimeEnvironment.getLfwSessionBean().getPk_user();
		return InvocationInfoProxy.getInstance().getUserId();
	}
	
	@SuppressWarnings("static-access")
	public static UFDateTime getCurrentTime() {
		return new UFDateTime(new Date(TimeService.getInstance().getTime()));
	}

	protected void fireBeforeUpdateEvent(T oldVO, T vo)
			throws BusinessException {
		BDCommonEventUtil eventUtil = new BDCommonEventUtil(getMDId());
		eventUtil.setOldObjs(new Object[] { oldVO });
		eventUtil.dispatchUpdateBeforeEvent(vo);
	}

	protected void fireAfterUpdateEvent(T oldVO, T vo) throws BusinessException {
		BDCommonEventUtil eventUtil = new BDCommonEventUtil(getMDId());
		eventUtil.setOldObjs(new Object[] { oldVO });
		eventUtil.dispatchUpdateAfterEvent(vo);
	}

	/**
	 * 管理型档案批量更新操作
	 * 
	 * @param ts
	 * @throws BusinessException
	 */
	protected void dbUpdateVO(T vo) throws BusinessException {
		vo.setStatus(VOStatus.UPDATED);
		getMDService().saveBillWithRealDelete(vo);
	}

	protected void notifyVersionChangeWhenDataUpdated(T vo)
			throws BusinessException {
		CacheProxy.fireDataUpdated(bizInfoUtil.getTableName(vo), null);
	}

	public T updateVO(T vo) throws BusinessException {

		if (vo == null)
			return vo;

		// 更新时的加锁操作
		updatelockOperate(vo);

		// 校验版本
		BDVersionValidationUtil.validateSuperVO(vo);

		// 获取更新前的OldVOs
		T oldVO = retrieveVO(vo);

		// 业务校验逻辑
		updateValidateVO(oldVO, vo);

		// 设置审计信息
		setUpdateAuditInfo(vo);

		// 更新前事件处理
		fireBeforeUpdateEvent(oldVO, vo);

		// 库操作
		dbUpdateVO(vo);

		// 更新缓存
		notifyVersionChangeWhenDataUpdated(vo);

		// 重新检索出新数据
		T newVo = retrieveVO(vo);

		mergeVO(vo, newVo);
		
		// 更新后事件通知
		fireAfterUpdateEvent(oldVO, vo);

		// 业务日志
		writeUpdatedBusiLog(vo);

		return vo;
	}

	/**
	 * 记录更新操作的业务日志
	 * 
	 * @param vo
	 * @throws BusinessException
	 */
	protected void writeUpdatedBusiLog(T vo) throws BusinessException {
		getBusiLogUtil().writeBusiLog(IBusiOperateConst.EDIT, null, vo);
	}

	/** ***************** 停用 ********************** */
	public ValueObjWithErrLog disableVO(T... vos) throws BusinessException {
		ValueObjWithErrLog returnValue = null;
		T[] disabledVOs = null;

		if (vos == null || vos.length == 0)
			return null;

		// LiFIXME: 数据权限校验

		// 加技术锁（主键锁）
		BDPKLockUtil.lockSuperVO(vos);

		// 版本校验（时间戳校验）
		BDVersionValidationUtil.validateSuperVO(vos);

		// 业务校验
		returnValue = filterCanDisableVO(vos);
		T[] canDisabledVOs = (T[]) returnValue.getVos();

		if (canDisabledVOs != null && canDisabledVOs.length > 0) {
			// 设置停用标志
			setDisableFlags(canDisabledVOs);

			// 设置停用的审计信息
			setDisableAuditInfos(canDisabledVOs);

			// 事件前批量通知
			fireBeforeDisableEvent(canDisabledVOs);

			// 数据保存到数据库
			dbDisableVOs(canDisabledVOs);

			// 缓存通知
			notifyVersionChangeWhenDataUpdated(vos[0]);

			// 检索已被封存的VO
			disabledVOs = retrieveVOs(canDisabledVOs);

			// 事件后通知
			fireAfterDisableEvent(disabledVOs);
		}
		// 拼装反馈信息
		returnValue.setVos(disabledVOs);

		// 业务日志
		writeDisableBusiLog(disabledVOs);

		return returnValue;
	}

	protected void setDisableFlags(T... vos) {
		// 设置停用标志
		for (T t : vos) {
			t.setAttributeValue(IBaseServiceConst.ENABLESTATE_FIELD,
					IPubEnumConst.ENABLESTATE_DISABLE);
		}
	}

	protected void setDisableAuditInfos(T... vos) {

		AuditInfoUtil.updateData(vos);
	}

	protected void fireBeforeDisableEvent(T... vos) throws BusinessException {
		BDCommonEventUtil eventUtil = new BDCommonEventUtil(getMDId());
		eventUtil.dispatchEnableToDisableBeforeEvent((Object[]) vos);
	}

	protected void fireAfterDisableEvent(T... vos) throws BusinessException {
		BDCommonEventUtil eventUtil = new BDCommonEventUtil(getMDId());
		eventUtil.dispatchEnableToDisableAfterEvent((Object[]) vos);
	}

	protected void dbDisableVOs(T... vos) throws BusinessException {
		// 停用
		new BaseDAO().updateVOArray(vos, new String[] {
				IBaseServiceConst.ENABLESTATE_FIELD,
				IBaseServiceConst.MODIFIER_FIELD,
				IBaseServiceConst.MODIFIEDTIME_FIELD });
	}

	/**
	 * 过滤待停用数据，子类继承此方法，扩充过滤策略 <br>
	 * 默认过滤掉“已停用”的数据
	 */
	protected ValueObjWithErrLog filterCanDisableVO(T... vos) {

		T[] filteredVOs = filterAlreadyXXStateVOs(vos,
				IPubEnumConst.ENABLESTATE_DISABLE);

		ValueObjWithErrLog returnWithErrLog = new ValueObjWithErrLog();
		returnWithErrLog.setVos(filteredVOs);
		return returnWithErrLog;
	}

	public T disableSingleVO(T vo) throws BusinessException {

		if (vo == null)
			return null;

		// LiFIXME: 数据权限校验

		// 加技术锁（主键锁）
		BDPKLockUtil.lockSuperVO(vo);

		// 版本校验（时间戳校验）
		BDVersionValidationUtil.validateSuperVO(vo);

		// 业务逻辑校验
		T oldVO = (T) new BaseDAO().retrieveByPK(getEntityClass(), vo
				.getPrimaryKey(),
				new String[] { IBaseServiceConst.ENABLESTATE_FIELD });
		Integer enable_state = (Integer) oldVO.getAttributeValue(IBaseServiceConst.ENABLESTATE_FIELD);
		if (IPubEnumConst.ENABLESTATE_DISABLE == enable_state) {
			// 当前档案已经停用
			return vo;
		}
		
		disableValidateSingleVO(vo);

		// 设置停用标志
		setDisableFlag(vo);

		// 设置停用的审计信息
		setDisableAuditInfo(vo);

		// 事件前通知
		fireBeforeDisableEvent(vo);

		// 数据保存到数据库
		dbDisableVO(vo);

		// 缓存通知
		notifyVersionChangeWhenDataUpdated(vo);

		// 检索已被封存的VO
		vo = retrieveVO(vo);

		// 事件后通知
		fireAfterDisableEvent(vo);

		// 业务日志
		writeDisableBusiLog(vo);

		return vo;
	}

	/**
	 * 记录停用操作的业务日志
	 * 
	 * @param vos
	 * @throws BusinessException
	 */
	protected void writeDisableBusiLog(T... vos) throws BusinessException {
		getBusiLogUtil().writeBusiLog(IBusiOperateConst.DISABLE, null, vos);
	}

	protected void disableValidateSingleVO(T vo) throws BusinessException {
		IValidationService validateService = ValidationFrameworkUtil
				.createValidationService(getDisableValidator());
		validateService.validate(vo);
	}

	/**
	 * 定义停用校验类 <br>
	 * 子类重写该方法，根据业务需要自行定义校验集合
	 * 
	 * @return 业务校验数据
	 */
	protected Validator[] getDisableValidator() {
		return null;
	}

	protected void setDisableFlag(T vo) {
		// 设置停用标志
		vo.setAttributeValue(IBaseServiceConst.ENABLESTATE_FIELD,
				IPubEnumConst.ENABLESTATE_DISABLE);
	}

	protected void setDisableAuditInfo(T vo) {
		AuditInfoUtil.updateData(vo);
	}

	protected void fireBeforeDisableEvent(T vo) throws BusinessException {
		BDCommonEventUtil eventUtil = new BDCommonEventUtil(getMDId());
		eventUtil.dispatchEnableToDisableBeforeEvent(vo);
	}

	protected void fireAfterDisableEvent(T vo) throws BusinessException {
		BDCommonEventUtil eventUtil = new BDCommonEventUtil(getMDId());
		eventUtil.dispatchEnableToDisableAfterEvent(vo);
	}

	protected void dbDisableVO(T vo) throws BusinessException {
		// 停用
		new BaseDAO().updateVO(vo, new String[] {
				IBaseServiceConst.ENABLESTATE_FIELD,
				IBaseServiceConst.MODIFIER_FIELD,
				IBaseServiceConst.MODIFIEDTIME_FIELD });
	}

	/** ***************** 启用 ********************** */
	public ValueObjWithErrLog enableVO(T... vos) throws BusinessException {
		ValueObjWithErrLog returnValue = null;
		T[] enabledVOs = null;

		if (vos == null || vos.length == 0)
			return null;

		// LiFIXME: 数据权限校验

		// 加技术锁（主键锁）
		BDPKLockUtil.lockSuperVO(vos);

		// 版本校验（时间戳校验）
		BDVersionValidationUtil.validateSuperVO(vos);

		// 业务校验
		returnValue = filterCanEnableVO(vos);
		T[] canEnableVOs = (T[]) returnValue.getVos();
		T[] canEnableOldVOs = retireveOldVOs(
				new String[] { IBaseServiceConst.ENABLESTATE_FIELD },
				canEnableVOs);

		if (canEnableVOs != null && canEnableVOs.length > 0) {
			// 设置启用标志
			setEnableFlags(canEnableVOs);

			// 设置启用的审计信息
			setEnableAuditInfos(canEnableVOs);

			// 事件前批量通知
			fireBeforeEnableEvent(canEnableOldVOs, canEnableVOs);

			// 数据保存到数据库
			dbEnableVOs(canEnableVOs);

			// 缓存通知
			notifyVersionChangeWhenDataUpdated(vos[0]);

			// 检索已被封存的VO
			enabledVOs = retrieveVOs(canEnableVOs);

			// 事件后通知
			fireAfterEnableEvent(canEnableOldVOs, enabledVOs);
		}
		// 拼装反馈信息
		returnValue.setVos(enabledVOs);

		// 业务日志
		writeEnableBusiLog(enabledVOs);

		return returnValue;
	}

	protected void setEnableFlags(T... vos) {
		// 设置启用标志
		for (T t : vos) {
			t.setAttributeValue(IBaseServiceConst.ENABLESTATE_FIELD,
					IPubEnumConst.ENABLESTATE_ENABLE);
		}
	}

	protected void setEnableAuditInfos(T... vos) {

		AuditInfoUtil.updateData(vos);
	}

	/**
	 * 从vos中，过滤掉“启用状态”为xx_state的VO
	 * 
	 * @param vos
	 * @param xx_state
	 * @return
	 */
	private T[] filterAlreadyXXStateVOs(T[] vos, int xx_state) {
		T[] filteredVOs = null;
		if (vos != null && vos.length > 0) {
			List<T> filteredVOList = new ArrayList<T>();
			for (int i = 0; i < vos.length; i++) {
				Integer currentXXState = (Integer) vos[i]
						.getAttributeValue(IBaseServiceConst.ENABLESTATE_FIELD);
				if (currentXXState != xx_state) {
					filteredVOList.add(vos[i]);
				}
			}

			if (filteredVOList.size() > 0) {
				filteredVOs = filteredVOList.toArray((T[]) Array.newInstance(
						vos[0].getClass(), filteredVOList.size()));
			}
		}
		return filteredVOs;
	}

	/**
	 * 过滤待启用数据，子类继承此方法，扩充过滤策略 <br>
	 * 默认过滤掉已启用的VO
	 */
	protected ValueObjWithErrLog filterCanEnableVO(T... vos) {

		T[] filteredVOs = filterAlreadyXXStateVOs(vos,
				IPubEnumConst.ENABLESTATE_ENABLE);

		ValueObjWithErrLog returnWithErrLog = new ValueObjWithErrLog();
		returnWithErrLog.setVos(filteredVOs);
		return returnWithErrLog;
	}

	protected void fireBeforeEnableEvent(T[] oldVOs, T... vos)
			throws BusinessException {
		List<T> init2EnableList = new ArrayList<T>(); // 未启用集
		List<T> disable2EnableList = new ArrayList<T>(); // 停用集
		assortEnableStateOrigin(oldVOs, vos, init2EnableList,
				disable2EnableList);

		BDCommonEventUtil eventUtil = new BDCommonEventUtil(getMDId());
		T[] init2Enables = null;
		T[] disable2Enables = null;
		if (init2EnableList.size() > 0) {
			init2Enables = init2EnableList.toArray((T[]) Array.newInstance(
					init2EnableList.get(0).getClass(), init2EnableList.size()));
			eventUtil.dispatchInitToEnableBeforeEvent((Object[]) init2Enables);
		}
		if (disable2EnableList.size() > 0) {
			disable2Enables = disable2EnableList.toArray((T[]) Array
					.newInstance(disable2EnableList.get(0).getClass(),
							disable2EnableList.size()));
			eventUtil
					.dispatchDisableToEnableBeforeEvent((Object[]) disable2Enables);
		}
	}

	protected void fireAfterEnableEvent(T[] oldVOs, T... vos)
			throws BusinessException {
		List<T> init2EnableList = new ArrayList<T>(); // 未启用集
		List<T> disable2EnableList = new ArrayList<T>(); // 停用集
		assortEnableStateOrigin(oldVOs, vos, init2EnableList,
				disable2EnableList);

		BDCommonEventUtil eventUtil = new BDCommonEventUtil(getMDId());
		T[] init2Enables = null;
		T[] disable2Enables = null;
		if (init2EnableList.size() > 0) {
			init2Enables = init2EnableList.toArray((T[]) Array.newInstance(
					init2EnableList.get(0).getClass(), init2EnableList.size()));
			eventUtil.dispatchInitToEnableAfterEvent((Object[]) init2Enables);
		}
		if (disable2EnableList.size() > 0) {
			disable2Enables = disable2EnableList.toArray((T[]) Array
					.newInstance(disable2EnableList.get(0).getClass(),
							disable2EnableList.size()));
			eventUtil
					.dispatchDisableToEnableAfterEvent((Object[]) disable2Enables);
		}
	}

	/**
	 * 通过enableState字段的比较，将oldVOs, vos，按照从“未启用”到“启用”，"停用"到"启用"来划分，
	 * 分类结果填写到init2EnableList, disable2EnableList中; <br>
	 * oldVOs与vos必须按照pk,一一对应;
	 * 
	 * @param oldVOs
	 * @param vos
	 * @param init2EnableList
	 *            未启用集,不能为null，且必是size为0的List
	 * @param disable2EnableList
	 *            停用集,不能为null，且必是size为0的List
	 */
	protected void assortEnableStateOrigin(T[] oldVOs, T[] vos,
			List<T> init2EnableList, List<T> disable2EnableList) {

		for (int i = 0; i < vos.length; i++) {
			Integer oldEnableState = (Integer) vos[i]
					.getAttributeValue(IBaseServiceConst.ENABLESTATE_FIELD);
			if (oldEnableState == IPubEnumConst.ENABLESTATE_DISABLE) {
				disable2EnableList.add(vos[i]);
			} else if (oldEnableState == IPubEnumConst.ENABLESTATE_INIT) {
				init2EnableList.add(vos[i]);
			}
		}
	}

	protected void dbEnableVOs(T... vos) throws BusinessException {
		// 启用
		new BaseDAO().updateVOArray(vos, new String[] {
				IBaseServiceConst.ENABLESTATE_FIELD,
				IBaseServiceConst.MODIFIER_FIELD,
				IBaseServiceConst.MODIFIEDTIME_FIELD });
	}

	public T enableSingleVO(T vo) throws BusinessException {

		if (vo == null)
			return null;

		// LiFIXME: 数据权限校验

		// 加技术锁（主键锁）
		BDPKLockUtil.lockSuperVO(vo);

		// 版本校验（时间戳校验）
		BDVersionValidationUtil.validateSuperVO(vo);

		// 业务校验
		T oldVO = (T) new BaseDAO().retrieveByPK(getEntityClass(), vo
				.getPrimaryKey(),
				new String[] { IBaseServiceConst.ENABLESTATE_FIELD });
		Integer enable_state = (Integer) oldVO.getAttributeValue(IBaseServiceConst.ENABLESTATE_FIELD);
		if (IPubEnumConst.ENABLESTATE_ENABLE == enable_state) {
			// 当前档案已经启用
			return vo;
		}
		
		enableValidateSingleVO(vo);

		// 设置启用标志
		setEnableFlag(vo);

		// 设置启用的审计信息
		setEnableAuditInfo(vo);

		// 事件前批量通知
		fireBeforeEnableEvent(oldVO, vo);

		// 数据保存到数据库
		dbEnableVO(vo);

		// 缓存通知
		notifyVersionChangeWhenDataUpdated(vo);

		// 检索已被封存的VO
		vo = retrieveVO(vo);

		// 事件后通知
		fireAfterEnableEvent(oldVO, vo);

		// 业务日志
		writeEnableBusiLog(vo);

		return vo;
	}

	/**
	 * 记录启用操作的业务日志
	 * 
	 * @param vos
	 * @throws BusinessException
	 */
	protected void writeEnableBusiLog(T... vos) throws BusinessException {
		getBusiLogUtil().writeBusiLog(IBusiOperateConst.ENABLE, null, vos);
	}

	protected void enableValidateSingleVO(T vo) throws BusinessException {
		IValidationService validateService = ValidationFrameworkUtil
				.createValidationService(getEnableValidator());
		validateService.validate(vo);
	}

	/**
	 * 定义启用校验类 <br>
	 * 子类重写该方法，根据业务需要自行定义校验集合
	 * 
	 * @return 业务校验数据
	 */
	protected Validator[] getEnableValidator() {
		return null;
	}

	protected void setEnableFlag(T vo) {
		// 设置启用标志
		vo.setAttributeValue(IBaseServiceConst.ENABLESTATE_FIELD, IPubEnumConst.ENABLESTATE_ENABLE);
	}

	protected void setEnableAuditInfo(T vo) {
		AuditInfoUtil.updateData(vo);
	}

	protected void fireBeforeEnableEvent(T oldVO, T vo) throws BusinessException {
		BDCommonEventUtil eventUtil = new BDCommonEventUtil(getMDId());
		Integer old_state = (Integer) oldVO.getAttributeValue(IBaseServiceConst.ENABLESTATE_FIELD);
		if (IPubEnumConst.ENABLESTATE_DISABLE == old_state) {
			eventUtil.dispatchDisableToEnableBeforeEvent(vo);
		} else if (IPubEnumConst.ENABLESTATE_INIT == old_state) {
			eventUtil.dispatchInitToEnableBeforeEvent(vo);
		}
	}

	protected void fireAfterEnableEvent(T oldVO, T vo) throws BusinessException {
		BDCommonEventUtil eventUtil = new BDCommonEventUtil(getMDId());
		Integer old_state = (Integer) oldVO.getAttributeValue(IBaseServiceConst.ENABLESTATE_FIELD);
		if (IPubEnumConst.ENABLESTATE_DISABLE == old_state) {
			eventUtil.dispatchDisableToEnableAfterEvent(vo);
		} else if (IPubEnumConst.ENABLESTATE_INIT == old_state) {
			eventUtil.dispatchInitToEnableAfterEvent(vo);
		}
	}

	protected void dbEnableVO(T vo) throws BusinessException {
		// 	启用
		new BaseDAO().updateVO(vo, new String[] {
				IBaseServiceConst.ENABLESTATE_FIELD,
				IBaseServiceConst.MODIFIER_FIELD,
				IBaseServiceConst.MODIFIEDTIME_FIELD });
	}



	/** ***************** 其他 ********************** */
	protected T retrieveVO(T vo) throws BusinessException {
		if (vo == null)
			return null;

		return retrieveVO(vo.getPrimaryKey());
	}

	protected T retrieveVO(String pk) throws BusinessException {
		if (StringUtils.isBlank(pk))
			return null;

		T newVO = (T) getQryDAO().getVOByPK(getSubAttributeNames(), pk);
		return newVO;
	}


	protected T[] retrieveVOs(T... ts)
			throws BusinessException {
		if (ts == null || ts.length == 0)
			return null;

		List<String> pkList = VOUtil.extractFieldValues(ts, bizInfoUtil
				.getFieldName(ts[0], IBaseServiceConst.ID), null);
		return retrieveVOs(pkList.toArray(new String[pkList.size()]));
	}

	protected T[] retrieveVOs(String[] pks)
			throws BusinessException {
		T[] newVOs = (T[]) getQryDAO().getBatchVOByPK(getSubAttributeNames(),
				pks);
		return newVOs;
	}

	/**
	 * 查询主表OldVO, 且oldVO与vos是依据pk,一一对应的
	 * 
	 * @param fields
	 *            属性字段，为null时，返回主表VO的全部信息
	 * @param vos
	 *            待查VO
	 * @return
	 * @throws BusinessException
	 */
	protected T[] retireveOldVOs(String[] fields, T... vos)
			throws BusinessException {
		if (vos == null || vos.length == 0)
			return null;

		List<String> pkList = VOUtil.extractFieldValues(vos, bizInfoUtil
				.getFieldName(vos[0], IBaseServiceConst.ID), null);

		return retrieveOldVOs(fields,
				pkList.toArray(new String[pkList.size()]), vos);
	}

	/**
	 * 查询OldVO，且顺序是按照pks的顺序，即vos主键的顺序
	 * 
	 */
	protected T[] retrieveOldVOs(String[] fields, String[] pks, T... vos)
			throws BusinessException {
		if (pks == null || pks.length == 0)
			return null;

		List<T> resultList = doBatchPkRetrieve(fields, pks, vos);

		T[] retrieveVOs = null;
		if (resultList != null && resultList.size() > 0) {
			HashMap<String, T> pk_vo_map = new HashMap<String, T>(resultList
					.size());
			for (T element : resultList) {
				pk_vo_map.put(element.getPrimaryKey(), element);
			}

			retrieveVOs = (T[]) Array.newInstance(getEntityClass(), resultList
					.size());
			for (int i = 0; i < pks.length; i++) {
				retrieveVOs[i] = pk_vo_map.get(pks[i]);
			}
		}

		return retrieveVOs;
	}

	protected List<T> doBatchPkRetrieve(final String[] fields, String[] pks,
			final T... vos) throws BusinessException {
		List<T> resultList = null;
		InSqlBatchCaller caller = new InSqlBatchCaller(pks);
		try {
			resultList = (List<T>) caller.execute(new IInSqlBatchCallBack() {

				ArrayList<T> qryList = new ArrayList<T>();
				BaseDAO baseDAO = new BaseDAO();

				@Override
				public Object doWithInSql(String inSql)
						throws BusinessException, SQLException {
					String qrySQL = bizInfoUtil.getFieldName(vos[0],
							IBaseServiceConst.ID)
							+ " in " + inSql;
					ArrayList<T> tempList = (ArrayList<T>) baseDAO
							.retrieveByClause(getEntityClass(), qrySQL, fields);
					if (tempList != null && tempList.size() > 0) {
						qryList.addAll(tempList);
					}
					return qryList;
				}

			});
		} catch (SQLException e) {
			Logger.error(e.getMessage(), e);
			throw new BusinessException(e.getMessage());
		}

		return resultList;
	}

	/**
	 * 返回元数据持久化服务对象
	 */
	protected static IMDPersistenceService getMDService() {
		return MDPersistenceService.lookupPersistenceService();
	}

	protected SuperVOQry getQryDAO() throws BusinessException {
		if (qryDAO == null) {
			qryDAO = new SuperVOQry(getEntityClass());
		}
		return qryDAO;
	}

}
