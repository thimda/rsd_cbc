package nc.uap.cpb.baseservice;
import java.util.Date;

import nc.bs.bd.baseservice.DefaultGetBizInfoByMDUtil;
import nc.bs.bd.baseservice.IGetBizInfoUtil;
import nc.bs.bd.baseservice.busilog.BDBusiLogUtil;
import nc.bs.bd.baseservice.busilog.IBDBusiLogUtil;
import nc.bs.bd.baseservice.busilog.IBusiOperateConst;
import nc.bs.bd.cache.CacheProxy;
import nc.bs.businessevent.bd.BDCommonEventUtil;
import nc.bs.framework.common.InvocationInfoProxy;
import nc.bs.framework.common.NCLocator;
import nc.bs.framework.core.service.TimeService;
import nc.bs.uif2.validation.IValidationService;
import nc.bs.uif2.validation.ValidationFrameworkUtil;
import nc.bs.uif2.validation.Validator;
import nc.md.persist.framework.IMDPersistenceQueryService;
import nc.md.persist.framework.IMDPersistenceService;
import nc.md.persist.framework.MDPersistenceService;
import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.crud.CRUDHelper;
import nc.uap.lfw.core.crud.ILfwCRUDService;
import nc.uap.lfw.core.exception.LfwBusinessException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.lfw.crud.itf.ILfwCudService;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
import nc.vo.pub.VOStatus;
import nc.vo.pub.lang.UFDateTime;
import nc.vo.util.BDPKLockUtil;
import nc.vo.util.BDReferenceChecker;
import nc.vo.util.BDVersionValidationUtil;
import nc.vo.util.bizlock.BizlockDataUtil;

import org.apache.commons.lang.StringUtils;


/**
 * AggVO增删改操作基类。
 * @author ybo
 * @version 6.0 2011-11-18
 * @since 1.6
 */
public class AggVOBaseService<T extends AggregatedValueObject>{
	
	/** 档案的元数据ID */
	protected String MDId;
	
	/** 获取业务信息的工具 */
	protected IGetBizInfoUtil bizInfoUtil = new DefaultGetBizInfoByMDUtil();
	
	/** 业务日志写入工具 */
	protected IBDBusiLogUtil busiLogUtil = null;

	/**
	 * 构造方法
	 * @param MDId 元数据id
	 */
	public AggVOBaseService(String MDId){
		this.MDId = MDId;
	}
    /**
     * AggVO插入操作
     * @param <T> AggVO类型定义
     * @param vo AggVO对象
     * @return 保存后的AggVO
     * @throws BusinessException
     */
    public <T extends AggregatedValueObject> T insert(T vo) throws BusinessException{
    	if(vo == null)
			return vo;
    	
        SuperVO parentVO = (SuperVO) vo.getParentVO();
        parentVO.setStatus(VOStatus.NEW);
        //新增时的加锁操作
		insertlockOperate(parentVO);
		// 逻辑校验
		insertValidateVO(vo);

		// 设置审计信息
		setInsertAuditInfo(parentVO);

		// 插入前事件通知
		fireBeforeInsertEvent(vo);

		// 库操作
		vo = (T) dbInsertVO(vo);
		//parentVO.setPrimaryKey(pk);

		// 通知更新缓存
		notifyVersionChangeWhenDataInserted(parentVO);

		// 重新检索出插入的VO
		T newVo = retrieveVO(vo);
		
		//将新查出VO与旧VO进行合并
		mergeVO(vo, newVo);
		
		// 插入事件后通知
		fireAfterInsertEvent(vo);

		// 业务日志
		writeInsertBusiLog(parentVO);
        return vo;
    }


    protected void mergeVO(AggregatedValueObject vo, AggregatedValueObject newVo) {
		vo.getParentVO().setAttributeValue("ts", newVo.getParentVO().getAttributeValue("ts"));
	}

    /**
     * AggVO更新操作
     * @param <T> AggVO类型定义
     * @param vo AggVO对象
     * @return 保存后的AggVO
     * @throws BusinessException
     */
    public <T extends AggregatedValueObject> T update(T vo) throws BusinessException {
    	if (vo == null)
			return null;
    	SuperVO parentVO = (SuperVO) vo.getParentVO();
		// 更新时的加锁操作
		updatelockOperate(parentVO);

		// 校验版本
		BDVersionValidationUtil.validateSuperVO(parentVO);

		// 获取更新前的OldVO
		T oldVO = retrieveVO(vo);

		// 业务校验逻辑
		updateValidateVO(oldVO, vo);

		// 设置审计信息
		setUpdateAuditInfo(parentVO);

		// 更新前事件处理
		fireBeforeUpdateEvent(oldVO, vo);

		// 库操作
		vo = (T) dbUpdateVo(vo);

		// 更新缓存
		notifyVersionChangeWhenDataUpdated(parentVO);

		// 重新检索出新数据
		T newVO = retrieveVO(vo);
		
		//合并vo
		mergeVO(vo, newVO);

		// 更新后事件通知
		fireAfterUpdateEvent(oldVO, vo);

		// 业务日志
		writeUpdatedBusiLog(parentVO);

		return vo;
    }
    
    /**
	 * AggVO删除操作
     * @param <T> AggVO类型定义
     * @param vo AggVO对象
     * @throws BusinessException
     */
    public <T extends AggregatedValueObject> void deleteVO(T vo) throws BusinessException {
		if (vo == null)
			return;
    	SuperVO parentVO = (SuperVO) vo.getParentVO();

		// 删除时的加锁操作
		deletelockOperate(parentVO);

		// 校验版本
		BDVersionValidationUtil.validateSuperVO(parentVO);

		// 删除前引用对象校验
		//updated start by liujj in 111201  
		//deleteValidateVO(vo);
		deleteValidateVO(parentVO);
		//updated end by liujj in 111201  
		// 删除前事件处理
		fireBeforeDeleteEvent(vo);
		
		// 缓存通知
		notifyVersionChangeWhenDataDeleted(parentVO);

		// 库操作
		dbDeleteVO(vo);

		// 删除后事件通知
		fireAfterDeleteEvent(vo);

		// 业务日志
		writeDeletedBusiLog(parentVO);
	}
    
    /* 以下为新增操作需要的辅助方法*/
	/**
	 * 新增时所需的加锁操作（包括业务锁，子类根据需要添加主键锁）
	 * @param vo 需要加锁的AggVO中的主表
	 * @throws BusinessException
	 */
	protected void insertlockOperate(SuperVO vo) throws BusinessException {
		// 加业务锁
		BizlockDataUtil.lockDataByBizlock(vo);
	}

	/**
	 * 新增的业务逻辑校验
	 * @param vo 需要校验的AggVO
	 * @throws BusinessException
	 */
	protected void insertValidateVO(AggregatedValueObject vo) throws BusinessException {
		IValidationService validateService = ValidationFrameworkUtil
				.createValidationService(getInsertValidator());
		validateService.validate(vo);
	}

	/**
	 * 取得插入调用时需要的校验器数组，校验器需支持AggVO
	 * @return 校验器数组
	 */
	protected Validator[] getInsertValidator() {
		return null;
	}

	/**
	 * 设置插入时AggVO主表的审计信息
	 * @param vo AggVO主表vo
	 */
	protected void setInsertAuditInfo(SuperVO vo) {
		if (vo == null)
			return;
		vo.setAttributeValue("creator", getCurrentUser());
		vo.setAttributeValue("creationtime", getCurrentTime());
	}

	/**
	 * 出发AggVO插入前的业务事件
	 * @param vo AggVO对象
	 * @throws BusinessException
	 */
	protected void fireBeforeInsertEvent(AggregatedValueObject vo) throws BusinessException {
		BDCommonEventUtil eventUtil = new BDCommonEventUtil(getMDId());
		eventUtil.dispatchInsertBeforeEvent(vo);
	}
	
	/**
	 * 出发AggVO插入前的业务事件
	 * @param vo AggVO对象
	 * @throws BusinessException
	 */
	protected void fireAfterInsertEvent(AggregatedValueObject vo) throws BusinessException {
		BDCommonEventUtil eventUtil = new BDCommonEventUtil(getMDId());
		eventUtil.dispatchInsertAfterEvent(vo);
	}

	/**
	 * 数据库插入操作
	 * @param vo AggVO对象
	 * @return AggVO对象主表主键
	 * @throws BusinessException
	 */
	protected AggregatedValueObject dbInsertVO(AggregatedValueObject vo) throws BusinessException {
		AggregatedValueObject aggVo = null;
		try {
			aggVo = getCrudService().saveBusinessVO(vo);
		} catch (LfwBusinessException e) {
			// TODO Auto-generated catch block
			LfwLogger.error(e.getMessage(), e);
		}
		return aggVo;
	}
	
	protected ILfwCRUDService<SuperVO, AggregatedValueObject> getCrudService() {
		return CRUDHelper.getCRUDService();
	}


	/**
	 * 更新缓存
	 * @param vo AggVO主表VO
	 * @throws BusinessException
	 */
	protected void notifyVersionChangeWhenDataInserted(SuperVO vo)
			throws BusinessException {
		CacheProxy.fireDataInserted(bizInfoUtil.getTableName(vo), null);
	}

	/**
	 * 记录主表插入的业务日志
	 * @param vo AggVO主表VO
	 * @throws BusinessException
	 */
	protected void writeInsertBusiLog(SuperVO vo) throws BusinessException {
		getBusiLogUtil().writeBusiLog(IBusiOperateConst.ADD, null, vo);
	}
	
	/* 以下为修改操作需要的辅助方法*/
	/**
	 * 修改时所需的加锁操作（包括主键锁和业务锁）
	 * @param vo AggVO主表VO
	 * @throws BusinessException
	 */
	protected void updatelockOperate(SuperVO vo) throws BusinessException {
		// 主键乐观锁
		BDPKLockUtil.lockSuperVO(vo);
		// 业务锁
		BizlockDataUtil.lockDataByBizlock(vo);
	}

	/**
	 * 更新前校验 AggVO
	 * @param oldVO 原始VO
	 * @param vo 变更后的VO
	 * @throws BusinessException
	 */
	protected void updateValidateVO(AggregatedValueObject oldVO, AggregatedValueObject vo) throws BusinessException {
		IValidationService validateService = ValidationFrameworkUtil
				.createValidationService(getUpdateValidator(oldVO));
		validateService.validate(vo);
	}

	/**
	 * 取得校验器数组
	 * @param vo AggVO对象
	 * @return 校验器数组
	 */
	protected Validator[] getUpdateValidator(AggregatedValueObject vo) {
		return null;
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

	/**
	 * 触发AggVO更新前业务事件
	 * @param oldVO 原始AggVO
	 * @param vo 更新的AggVO
	 * @throws BusinessException
	 */
	protected void fireBeforeUpdateEvent(AggregatedValueObject oldVO, AggregatedValueObject vo)
			throws BusinessException {
		BDCommonEventUtil eventUtil = new BDCommonEventUtil(getMDId());
		eventUtil.setOldObjs(new Object[] { oldVO });
		eventUtil.dispatchUpdateBeforeEvent(vo);
	}
	/**
	 * 触发AggVO更新后业务事件
	 * @param oldVO 原始AggVO
	 * @param vo 更新的AggVO
	 * @throws BusinessException
	 */
	protected void fireAfterUpdateEvent(AggregatedValueObject oldVO, AggregatedValueObject vo) throws BusinessException {
		BDCommonEventUtil eventUtil = new BDCommonEventUtil(getMDId());
		eventUtil.setOldObjs(new Object[] { oldVO });
		eventUtil.dispatchUpdateAfterEvent(vo);
	}

	/**
	 * 更新数据库
	 * @param vo 更新的AggVO
	 * @throws BusinessException
	 */
	protected AggregatedValueObject dbUpdateVo(AggregatedValueObject vo) throws BusinessException {
		//getMDPersistenceService().saveBillWithRealDelete(vo);
		AggregatedValueObject aggVo = null;
		try {
			aggVo = getCrudService().saveBusinessVO(vo);
		} catch (LfwBusinessException e) {
			// TODO Auto-generated catch block
			LfwLogger.error(e.getMessage(), e);
		}
		return aggVo;
	}

	/**
	 * 更新缓存
	 * @param vo AggVO的主表VO
	 * @throws BusinessException
	 */
	protected void notifyVersionChangeWhenDataUpdated(SuperVO vo)
			throws BusinessException {
		CacheProxy.fireDataUpdated(bizInfoUtil.getTableName(vo), null);
	}

	/**
	 * 记录更新操作的业务日志
	 * @param vo AggVO的主表VO
	 * @throws BusinessException
	 */
	protected void writeUpdatedBusiLog(SuperVO vo) throws BusinessException {
		getBusiLogUtil().writeBusiLog(IBusiOperateConst.EDIT, null, vo);
	}
	

	
	/* 以下为删除操作需要的辅助方法*/
	/**
	 * 删除时所需的加锁操作（包括加主键锁）
	 * @param vo AggVO主表VO
	 */
	protected void deletelockOperate(SuperVO vo) throws BusinessException {
		// 主键乐观锁
		BDPKLockUtil.lockSuperVO(vo);
	}

	/**
	 * 删除前校验AggVO
	 * @param vo AggVO对象
	 * @throws BusinessException
	 */
	protected void deleteValidateVO(SuperVO vo) throws BusinessException {
	//protected void deleteValidateVO(AggregatedValueObject vo) throws BusinessException {
		IValidationService validationService = ValidationFrameworkUtil
				.createValidationService(getDeleteValidator());
		validationService.validate(vo);
	}

	/**
	 * 取得删除校验器数组
	 * @return 删除校验器数组
	 */
	protected Validator[] getDeleteValidator() {
		Validator[] validators = new Validator[] { BDReferenceChecker
				.getInstance() };
		return validators;
	}

	/**
	 * 触发删除前业务事件
	 * @param vo 删除的AggVO对象
	 * @throws BusinessException
	 */
	protected void fireBeforeDeleteEvent(AggregatedValueObject vo) throws BusinessException {
		BDCommonEventUtil eventUtil = new BDCommonEventUtil(getMDId());
		eventUtil.dispatchDeleteBeforeEvent(vo);
	}
	
	/**
	 * 触发删除前业务事件
	 * @param vo 删除的AggVO对象
	 * @throws BusinessException
	 */
	protected void fireAfterDeleteEvent(AggregatedValueObject vo) throws BusinessException {
		BDCommonEventUtil eventUtil = new BDCommonEventUtil(getMDId());
		eventUtil.dispatchDeleteAfterEvent(vo);
	}

	/**
	 * AggVO的数据库删除操作
	 * @param vo 删除的AggVO对象
	 * @throws BusinessException
	 */
	protected void dbDeleteVO(AggregatedValueObject vo) throws BusinessException {
		getMDPersistenceService().deleteBillFromDB(vo);
	}

	/**
	 * 更新缓存
	 * @param vo 删除的AggVO对象
	 * @throws BusinessException
	 */
	protected void notifyVersionChangeWhenDataDeleted(SuperVO vo)
			throws BusinessException {
		CacheProxy.fireDataDeleted(bizInfoUtil.getTableName(vo), vo.getPrimaryKey());
	}
	
	/**
	 * 记录AggVO删除的业务操作
	 * @param vo 删除的AggVO对象
	 * @throws BusinessException
	 */
	protected void writeDeletedBusiLog(SuperVO vo) throws BusinessException {
		getBusiLogUtil().writeBusiLog(IBusiOperateConst.DELETE, null, vo);
	}
	/* 以下为其他辅助方法*/
	@SuppressWarnings("unchecked")
	protected <T extends AggregatedValueObject>T retrieveVO(T vo) throws BusinessException {
		SuperVO parentVO = (SuperVO) vo.getParentVO();
		String pk = parentVO.getPrimaryKey();
		if (StringUtils.isBlank(pk))
			return null;
		return (T) getMDQueryService().queryBillOfVOByPK(vo.getClass(), pk, false);
        
	}

	/**
	 * 取得业务日志工具类
	 * @return 业务日志工具类
	 */
	protected IBDBusiLogUtil getBusiLogUtil() {
		if (busiLogUtil == null) {
			busiLogUtil = new BDBusiLogUtil(getMDId());
		}
		return busiLogUtil;
	}
	
	/**
	 * 取得元素据持久化服务
	 * @return 元素据持久化服务
	 */
	protected static IMDPersistenceService getMDPersistenceService(){
		return MDPersistenceService.lookupPersistenceService();
	}
	
	/**
	 * 获取lfw提供的默认服务类
	 * @return
	 */
	protected static ILfwCudService getLfwPersitenceService(){
		return NCLocator.getInstance().lookup(ILfwCudService.class);
	}
	
    /**
     * 取得元数据查询服务
     * @return 元数据查询服务
     */
    protected static IMDPersistenceQueryService getMDQueryService(){
        return MDPersistenceService.lookupPersistenceQueryService();
    }
    
	/**
	 * 取得元数据id
	 * @return 元数据id
	 */
	public String getMDId() {
		return MDId;
	}
}
