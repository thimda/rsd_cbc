package nc.uap.cpb.baseservice;

import nc.bs.bd.baseservice.DefaultGetBizInfoByMDUtil;
import nc.bs.bd.baseservice.IBaseServiceConst;
import nc.bs.bd.baseservice.IGetBizInfoUtil;
import nc.bs.bd.baseservice.busilog.BDBusiLogUtil;
import nc.bs.bd.baseservice.busilog.IBDBusiLogUtil;
import nc.bs.bd.baseservice.busilog.IBusiOperateConst;
import nc.bs.bd.cache.CacheProxy;
import nc.bs.businessevent.bd.BDCommonEventUtil;
import nc.bs.dao.BaseDAO;
import nc.bs.dao.DAOException;
import nc.bs.logging.Logger;
import nc.bs.uif2.validation.IValidationService;
import nc.bs.uif2.validation.ValidationFrameworkUtil;
import nc.bs.uif2.validation.Validator;
import nc.md.model.MetaDataException;
import nc.vo.bd.pub.IPubEnumConst;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
import nc.vo.util.AuditInfoUtil;
import nc.vo.util.BDPKLockUtil;
import nc.vo.util.BDReferenceChecker;
import nc.vo.util.BDUniqueRuleValidate;
import nc.vo.util.BDVersionValidationUtil;
import nc.vo.util.bizlock.BizlockDataUtil;

import org.apache.commons.lang.StringUtils;

/**
 * Copy from uapbd,为协同基础单据提供支持
 * 针对单个档案VO的后台基础服务 <br>
 * 本后台服务采用BaseDAO方式做持久化，且所有操作都是针对单个档案VO进行的；
 * 
 * @since NC6.1
 * 
 * @param <T>
 */
public class SingleBaseService<T extends SuperVO> {

	/** 档案的元数据ID */
	private String MDId;
	/** 获取业务信息的工具 */
	protected IGetBizInfoUtil bizInfoUtil = new DefaultGetBizInfoByMDUtil();
	/** 业务日志写入工具 */
	private IBDBusiLogUtil busiLogUtil = null;

	public SingleBaseService(String MDId) {
		this(MDId, null);
	}

	public SingleBaseService(String MDId, IGetBizInfoUtil bizInfoUtil) {
		this.MDId = MDId;
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
		vo.setPrimaryKey(pk);

		// 通知更新缓存
		notifyVersionChangeWhenDataInserted(vo);

		// 重新检索出插入的VO
		T newVo = retrieveVO(vo);

		mergeVO(vo, newVo);
		
		// 插入事件后通知
		fireAfterInsertEvent(vo);

		// 业务日志
		writeInsertBusiLog(vo);

		return vo;
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

	public T updateVO(T vo) throws BusinessException {

		if (vo == null)
			return null;

		// 更新时的加锁操作
		updatelockOperate(vo);

		// 校验版本
		BDVersionValidationUtil.validateSuperVO(vo);

		// 获取更新前的OldVO
		T oldVO = retrieveVO(vo);

		// 业务校验逻辑
		updateValidateVO(oldVO, vo);

		// 设置审计信息
		setUpdateAuditInfo(vo);

		// 更新前事件处理
		fireBeforeUpdateEvent(oldVO, vo);

		// 库操作
		dbUpdateVo(vo);

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
	 * 进行VO合并
	 * @param vo
	 * @param newVo
	 */
	protected void mergeVO(T vo, T newVo) {
		vo.setAttributeValue("ts", newVo.getAttributeValue("ts"));
		vo.setPrimaryKey(newVo.getPrimaryKey());
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
	 * 记录停用操作的业务日志
	 * 
	 * @param vo
	 * @throws BusinessException
	 */
	protected void writeDeletedBusiLog(T vo) throws BusinessException {
		getBusiLogUtil().writeBusiLog(IBusiOperateConst.DELETE, null, vo);
	}

	@SuppressWarnings("unchecked")
	public T disableVO(T vo) throws BusinessException {
		if (vo == null)
			return null;

		// LiFIXME: 数据权限校验

		// 加技术锁（主键锁）
		BDPKLockUtil.lockSuperVO(vo);

		// 版本校验（时间戳校验）
		BDVersionValidationUtil.validateSuperVO(vo);

		// 业务校验逻辑
		T oldVO = (T) new BaseDAO().retrieveByPK(vo.getClass(), vo
				.getPrimaryKey(),
				new String[] { IBaseServiceConst.ENABLESTATE_FIELD });
		Integer enable_state = (Integer) oldVO
				.getAttributeValue(IBaseServiceConst.ENABLESTATE_FIELD);
		if (IPubEnumConst.ENABLESTATE_DISABLE == enable_state) {
			// 当前档案已经停用
			return vo;
		}

		disableValidateVO(vo);

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

		// 检索已被停用的VO
		T disabledVO = retrieveVO(vo);

		// 事件后通知
		fireAfterDisableEvent(disabledVO);

		// 业务日志
		writeDisableBusiLog(disabledVO);

		return disabledVO;
	}

	/**
	 * 记录停用操作的业务日志
	 * 
	 * @param vo
	 * @throws BusinessException
	 */
	protected void writeDisableBusiLog(T vo) throws BusinessException {
		getBusiLogUtil().writeBusiLog(IBusiOperateConst.DISABLE, null, vo);
	}

	@SuppressWarnings("unchecked")
	public T enableVO(T vo) throws BusinessException {
		if (vo == null)
			return null;

		// LiFIXME: 数据权限校验

		// 加技术锁（主键锁）
		BDPKLockUtil.lockSuperVO(vo);

		// 版本校验（时间戳校验）
		BDVersionValidationUtil.validateSuperVO(vo);

		// 业务校验
		T oldVO = (T) new BaseDAO().retrieveByPK(vo.getClass(), vo
				.getPrimaryKey(),
				new String[] { IBaseServiceConst.ENABLESTATE_FIELD });
		Integer enable_state = (Integer) oldVO
				.getAttributeValue(IBaseServiceConst.ENABLESTATE_FIELD);
		if (IPubEnumConst.ENABLESTATE_ENABLE == enable_state) {
			// 当前档案已经启用
			return vo;
		}

		enableValidateVO(vo);

		// 设置启用标志
		setEnableFlag(vo);

		// 设置启用的审计信息
		setEnableAuditInfo(vo);

		// 事件前通知
		fireBeforeEnableEvent(oldVO, vo);

		// 数据保存到数据库
		dbEnableVO(vo);

		// 缓存通知
		notifyVersionChangeWhenDataUpdated(vo);

		// 检索已被封存的VO
		T enabledVO = retrieveVO(vo);

		// 事件后通知
		fireAfterEnableEvent(oldVO, enabledVO);

		// 业务日志
		writeEnableBusiLog(enabledVO);

		return enabledVO;
	}

	/**
	 * 记录启用操作的业务日志
	 * 
	 * @param vo
	 * @throws BusinessException
	 */
	protected void writeEnableBusiLog(T vo) throws BusinessException {
		getBusiLogUtil().writeBusiLog(IBusiOperateConst.ENABLE, null, vo);
	}

	// 以下为启用操作需要的辅助方法-------------------------------------------------
	protected void fireBeforeEnableEvent(T oldVO, T vo)
			throws BusinessException {
		BDCommonEventUtil eventUtil = new BDCommonEventUtil(getMDId());
		Integer old_state = (Integer) oldVO
				.getAttributeValue(IBaseServiceConst.ENABLESTATE_FIELD);
		if (IPubEnumConst.ENABLESTATE_DISABLE == old_state) {
			eventUtil.dispatchDisableToEnableBeforeEvent(vo);
		} else if (IPubEnumConst.ENABLESTATE_INIT == old_state) {
			eventUtil.dispatchInitToEnableBeforeEvent(vo);
		}
	}

	protected void fireAfterEnableEvent(T oldVO, T vo) throws BusinessException {
		BDCommonEventUtil eventUtil = new BDCommonEventUtil(getMDId());
		Integer old_state = (Integer) oldVO
				.getAttributeValue(IBaseServiceConst.ENABLESTATE_FIELD);
		if (IPubEnumConst.ENABLESTATE_DISABLE == old_state) {
			eventUtil.dispatchDisableToEnableAfterEvent(vo);
		} else if (IPubEnumConst.ENABLESTATE_INIT == old_state) {
			eventUtil.dispatchInitToEnableAfterEvent(vo);
		}
	}

	protected void dbEnableVO(T vo) throws BusinessException {
		// 启用
		new BaseDAO().updateVO(vo, new String[] {
				IBaseServiceConst.ENABLESTATE_FIELD,
				IBaseServiceConst.MODIFIER_FIELD,
				IBaseServiceConst.MODIFIEDTIME_FIELD });
	}

	protected void enableValidateVO(T vo) throws BusinessException {
		IValidationService validateService = ValidationFrameworkUtil
				.createValidationService(getEnableValidator());
		validateService.validate(vo);
	}

	/**
	 * 启用校验校验类 <br>
	 * 子类重写该方法，根据业务需要自行定义校验集合
	 * 
	 * @return
	 */
	protected Validator[] getEnableValidator() {
		return null;
	}

	/**
	 * 设置启用标志
	 * 
	 * @param vo
	 */
	protected void setEnableFlag(T vo) {
		vo.setAttributeValue(IBaseServiceConst.ENABLESTATE_FIELD,
				IPubEnumConst.ENABLESTATE_ENABLE);
	}

	/**
	 * 设置启用的审计信息
	 * 
	 * @param vo
	 */
	protected void setEnableAuditInfo(T vo) {

		AuditInfoUtil.updateData(vo);
	}

	// 以下为封存操作需要的辅助方法-------------------------------------------------
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

	protected void disableValidateVO(T vo) throws BusinessException {
		IValidationService validateService = ValidationFrameworkUtil
				.createValidationService(getDisableValidator());
		validateService.validate(vo);
	}

	/**
	 * 停用校验校验类 <br>
	 * 子类重写该方法，根据业务需要自行定义校验集合
	 * 
	 * @return
	 */
	protected Validator[] getDisableValidator() {
		return null;
	}

	/**
	 * 设置停用标志
	 * 
	 * @param vo
	 */
	protected void setDisableFlag(T vo) {
		vo.setAttributeValue(IBaseServiceConst.ENABLESTATE_FIELD,
				IPubEnumConst.ENABLESTATE_DISABLE);
	}

	/**
	 * 设置停用的审计信息
	 * 
	 * @param vo
	 */
	protected void setDisableAuditInfo(T vo) {

		AuditInfoUtil.updateData(vo);
	}

	// 以下为新增操作需要的辅助方法--------------------------------------------------
	/**
	 * 新增时所需的加锁操作（包括业务锁，子类根据需要添加主键锁）
	 */
	protected void insertlockOperate(T vo) throws BusinessException {
		// 加业务锁
		BizlockDataUtil.lockDataByBizlock(vo);
	}

	/**
	 * 新增的业务逻辑校验
	 * 
	 * @param vo
	 *            待新增的对象
	 * @throws BusinessException
	 */
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

	protected void setInsertAuditInfo(T vo) {

		AuditInfoUtil.addData(vo);
	}

	protected void fireBeforeInsertEvent(T vo) throws BusinessException {
		BDCommonEventUtil eventUtil = new BDCommonEventUtil(getMDId());
		eventUtil.dispatchInsertBeforeEvent(vo);
	}

	protected void fireAfterInsertEvent(T vo) throws BusinessException {
		BDCommonEventUtil eventUtil = new BDCommonEventUtil(getMDId());
		eventUtil.dispatchInsertAfterEvent(vo);
	}

	protected String dbInsertVO(T vo) throws BusinessException {
		BaseDAO dao = new BaseDAO();
		String pk = vo.getPrimaryKey();
		if (StringUtils.isNotBlank(pk)) {
			dao.insertVOWithPK(vo);
		} else {
			pk = dao.insertVO(vo);
		}
		return pk;
	}

	protected void notifyVersionChangeWhenDataInserted(T vo)
			throws BusinessException {
		CacheProxy.fireDataInserted(bizInfoUtil.getTableName(vo), null);
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

	// 以下为更新事件需要的辅助方法-----------------------------------------------------
	/**
	 * 修改时所需的加锁操作（包括主键锁和业务锁）
	 */
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

	/**
	 * 定义更新校验类 <br>
	 * 子类重写该方法，根据业务需要自行定义校验集合
	 * 
	 * @return 业务校验数据
	 */
	protected Validator[] getUpdateValidator(T oldVO) {
		Validator[] validators = new Validator[] { new BDUniqueRuleValidate() };
		return validators;
	}

	protected void setUpdateAuditInfo(T vo) {

		AuditInfoUtil.updateData(vo);
	}

	/**
	 * 更新前事件通知操作
	 * 
	 * @param oldVOs
	 *            更新前的OldVOs
	 * @param vo
	 *            待更新的VOs
	 * @throws BusinessException
	 */
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

	protected void dbUpdateVo(T vo) throws BusinessException {
		BaseDAO dao = new BaseDAO();
		try {
			dao.updateVO(vo);
		} catch (DAOException e) {
			Logger.error("更新" + vo.getClass().getName() + "时出错！", e);
			throw new BusinessException(e.getMessage(), e);
		}
	}

	protected void notifyVersionChangeWhenDataUpdated(T vo)
			throws BusinessException {
		CacheProxy.fireDataUpdated(bizInfoUtil.getTableName(vo), null);
	}

	// 以下为删除事件需要的辅助方法----------------------------------------
	/**
	 * 删除时所需的加锁操作（包括加主键锁）
	 */
	protected void deletelockOperate(T vo) throws BusinessException {
		// 主键乐观锁
		BDPKLockUtil.lockSuperVO(vo);
	}

	/**
	 * 删除的逻辑校验
	 * 
	 * @param vo
	 *            待删除的对象
	 * @throws BusinessException
	 */
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

	protected void fireAfterDeleteEvent(T vo) throws BusinessException {
		BDCommonEventUtil eventUtil = new BDCommonEventUtil(getMDId());
		eventUtil.dispatchDeleteAfterEvent(vo);
	}

	protected void dbDeleteVO(T vo) throws BusinessException {
		BaseDAO dao = new BaseDAO();
		try {
			dao.deleteVO(vo);
		} catch (DAOException e) {
			Logger.error("删除" + vo.getClass().getName() + "时出错！", e);
			throw new BusinessException(e.getMessage(), e);
		}
	}

	protected void fireBeforeDeleteEvent(T vo) throws BusinessException {
		BDCommonEventUtil eventUtil = new BDCommonEventUtil(getMDId());
		eventUtil.dispatchDeleteBeforeEvent(vo);
	}

	protected void notifyVersionChangeWhenDataDeleted(T vo)
			throws BusinessException {
		CacheProxy.fireDataDeleted(bizInfoUtil.getTableName(vo), vo.getPrimaryKey());
	}

	/** ************************* 检索VO操作 ****************************** */
	@SuppressWarnings("unchecked")
	protected T retrieveVO(T vo) throws BusinessException {
		String pk = vo.getPrimaryKey();
		if (StringUtils.isBlank(pk))
			return null;
		T retrieveVO = (T) new BaseDAO().retrieveByPK(vo.getClass(), pk);
		return retrieveVO;
	}

	/** ************************** 其他 *********************************** */
}
