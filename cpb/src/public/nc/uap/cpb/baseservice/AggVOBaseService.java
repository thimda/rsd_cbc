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
 * AggVO��ɾ�Ĳ������ࡣ
 * @author ybo
 * @version 6.0 2011-11-18
 * @since 1.6
 */
public class AggVOBaseService<T extends AggregatedValueObject>{
	
	/** ������Ԫ����ID */
	protected String MDId;
	
	/** ��ȡҵ����Ϣ�Ĺ��� */
	protected IGetBizInfoUtil bizInfoUtil = new DefaultGetBizInfoByMDUtil();
	
	/** ҵ����־д�빤�� */
	protected IBDBusiLogUtil busiLogUtil = null;

	/**
	 * ���췽��
	 * @param MDId Ԫ����id
	 */
	public AggVOBaseService(String MDId){
		this.MDId = MDId;
	}
    /**
     * AggVO�������
     * @param <T> AggVO���Ͷ���
     * @param vo AggVO����
     * @return ������AggVO
     * @throws BusinessException
     */
    public <T extends AggregatedValueObject> T insert(T vo) throws BusinessException{
    	if(vo == null)
			return vo;
    	
        SuperVO parentVO = (SuperVO) vo.getParentVO();
        parentVO.setStatus(VOStatus.NEW);
        //����ʱ�ļ�������
		insertlockOperate(parentVO);
		// �߼�У��
		insertValidateVO(vo);

		// ���������Ϣ
		setInsertAuditInfo(parentVO);

		// ����ǰ�¼�֪ͨ
		fireBeforeInsertEvent(vo);

		// �����
		vo = (T) dbInsertVO(vo);
		//parentVO.setPrimaryKey(pk);

		// ֪ͨ���»���
		notifyVersionChangeWhenDataInserted(parentVO);

		// ���¼����������VO
		T newVo = retrieveVO(vo);
		
		//���²��VO���VO���кϲ�
		mergeVO(vo, newVo);
		
		// �����¼���֪ͨ
		fireAfterInsertEvent(vo);

		// ҵ����־
		writeInsertBusiLog(parentVO);
        return vo;
    }


    protected void mergeVO(AggregatedValueObject vo, AggregatedValueObject newVo) {
		vo.getParentVO().setAttributeValue("ts", newVo.getParentVO().getAttributeValue("ts"));
	}

    /**
     * AggVO���²���
     * @param <T> AggVO���Ͷ���
     * @param vo AggVO����
     * @return ������AggVO
     * @throws BusinessException
     */
    public <T extends AggregatedValueObject> T update(T vo) throws BusinessException {
    	if (vo == null)
			return null;
    	SuperVO parentVO = (SuperVO) vo.getParentVO();
		// ����ʱ�ļ�������
		updatelockOperate(parentVO);

		// У��汾
		BDVersionValidationUtil.validateSuperVO(parentVO);

		// ��ȡ����ǰ��OldVO
		T oldVO = retrieveVO(vo);

		// ҵ��У���߼�
		updateValidateVO(oldVO, vo);

		// ���������Ϣ
		setUpdateAuditInfo(parentVO);

		// ����ǰ�¼�����
		fireBeforeUpdateEvent(oldVO, vo);

		// �����
		vo = (T) dbUpdateVo(vo);

		// ���»���
		notifyVersionChangeWhenDataUpdated(parentVO);

		// ���¼�����������
		T newVO = retrieveVO(vo);
		
		//�ϲ�vo
		mergeVO(vo, newVO);

		// ���º��¼�֪ͨ
		fireAfterUpdateEvent(oldVO, vo);

		// ҵ����־
		writeUpdatedBusiLog(parentVO);

		return vo;
    }
    
    /**
	 * AggVOɾ������
     * @param <T> AggVO���Ͷ���
     * @param vo AggVO����
     * @throws BusinessException
     */
    public <T extends AggregatedValueObject> void deleteVO(T vo) throws BusinessException {
		if (vo == null)
			return;
    	SuperVO parentVO = (SuperVO) vo.getParentVO();

		// ɾ��ʱ�ļ�������
		deletelockOperate(parentVO);

		// У��汾
		BDVersionValidationUtil.validateSuperVO(parentVO);

		// ɾ��ǰ���ö���У��
		//updated start by liujj in 111201  
		//deleteValidateVO(vo);
		deleteValidateVO(parentVO);
		//updated end by liujj in 111201  
		// ɾ��ǰ�¼�����
		fireBeforeDeleteEvent(vo);
		
		// ����֪ͨ
		notifyVersionChangeWhenDataDeleted(parentVO);

		// �����
		dbDeleteVO(vo);

		// ɾ�����¼�֪ͨ
		fireAfterDeleteEvent(vo);

		// ҵ����־
		writeDeletedBusiLog(parentVO);
	}
    
    /* ����Ϊ����������Ҫ�ĸ�������*/
	/**
	 * ����ʱ����ļ�������������ҵ���������������Ҫ�����������
	 * @param vo ��Ҫ������AggVO�е�����
	 * @throws BusinessException
	 */
	protected void insertlockOperate(SuperVO vo) throws BusinessException {
		// ��ҵ����
		BizlockDataUtil.lockDataByBizlock(vo);
	}

	/**
	 * ������ҵ���߼�У��
	 * @param vo ��ҪУ���AggVO
	 * @throws BusinessException
	 */
	protected void insertValidateVO(AggregatedValueObject vo) throws BusinessException {
		IValidationService validateService = ValidationFrameworkUtil
				.createValidationService(getInsertValidator());
		validateService.validate(vo);
	}

	/**
	 * ȡ�ò������ʱ��Ҫ��У�������飬У������֧��AggVO
	 * @return У��������
	 */
	protected Validator[] getInsertValidator() {
		return null;
	}

	/**
	 * ���ò���ʱAggVO����������Ϣ
	 * @param vo AggVO����vo
	 */
	protected void setInsertAuditInfo(SuperVO vo) {
		if (vo == null)
			return;
		vo.setAttributeValue("creator", getCurrentUser());
		vo.setAttributeValue("creationtime", getCurrentTime());
	}

	/**
	 * ����AggVO����ǰ��ҵ���¼�
	 * @param vo AggVO����
	 * @throws BusinessException
	 */
	protected void fireBeforeInsertEvent(AggregatedValueObject vo) throws BusinessException {
		BDCommonEventUtil eventUtil = new BDCommonEventUtil(getMDId());
		eventUtil.dispatchInsertBeforeEvent(vo);
	}
	
	/**
	 * ����AggVO����ǰ��ҵ���¼�
	 * @param vo AggVO����
	 * @throws BusinessException
	 */
	protected void fireAfterInsertEvent(AggregatedValueObject vo) throws BusinessException {
		BDCommonEventUtil eventUtil = new BDCommonEventUtil(getMDId());
		eventUtil.dispatchInsertAfterEvent(vo);
	}

	/**
	 * ���ݿ�������
	 * @param vo AggVO����
	 * @return AggVO������������
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
	 * ���»���
	 * @param vo AggVO����VO
	 * @throws BusinessException
	 */
	protected void notifyVersionChangeWhenDataInserted(SuperVO vo)
			throws BusinessException {
		CacheProxy.fireDataInserted(bizInfoUtil.getTableName(vo), null);
	}

	/**
	 * ��¼��������ҵ����־
	 * @param vo AggVO����VO
	 * @throws BusinessException
	 */
	protected void writeInsertBusiLog(SuperVO vo) throws BusinessException {
		getBusiLogUtil().writeBusiLog(IBusiOperateConst.ADD, null, vo);
	}
	
	/* ����Ϊ�޸Ĳ�����Ҫ�ĸ�������*/
	/**
	 * �޸�ʱ����ļ���������������������ҵ������
	 * @param vo AggVO����VO
	 * @throws BusinessException
	 */
	protected void updatelockOperate(SuperVO vo) throws BusinessException {
		// �����ֹ���
		BDPKLockUtil.lockSuperVO(vo);
		// ҵ����
		BizlockDataUtil.lockDataByBizlock(vo);
	}

	/**
	 * ����ǰУ�� AggVO
	 * @param oldVO ԭʼVO
	 * @param vo ������VO
	 * @throws BusinessException
	 */
	protected void updateValidateVO(AggregatedValueObject oldVO, AggregatedValueObject vo) throws BusinessException {
		IValidationService validateService = ValidationFrameworkUtil
				.createValidationService(getUpdateValidator(oldVO));
		validateService.validate(vo);
	}

	/**
	 * ȡ��У��������
	 * @param vo AggVO����
	 * @return У��������
	 */
	protected Validator[] getUpdateValidator(AggregatedValueObject vo) {
		return null;
	}

	/**
	 * ���ø��µ�AggVO����VO�������Ϣ
	 * @param vo AggVO����VO
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
	 * ����AggVO����ǰҵ���¼�
	 * @param oldVO ԭʼAggVO
	 * @param vo ���µ�AggVO
	 * @throws BusinessException
	 */
	protected void fireBeforeUpdateEvent(AggregatedValueObject oldVO, AggregatedValueObject vo)
			throws BusinessException {
		BDCommonEventUtil eventUtil = new BDCommonEventUtil(getMDId());
		eventUtil.setOldObjs(new Object[] { oldVO });
		eventUtil.dispatchUpdateBeforeEvent(vo);
	}
	/**
	 * ����AggVO���º�ҵ���¼�
	 * @param oldVO ԭʼAggVO
	 * @param vo ���µ�AggVO
	 * @throws BusinessException
	 */
	protected void fireAfterUpdateEvent(AggregatedValueObject oldVO, AggregatedValueObject vo) throws BusinessException {
		BDCommonEventUtil eventUtil = new BDCommonEventUtil(getMDId());
		eventUtil.setOldObjs(new Object[] { oldVO });
		eventUtil.dispatchUpdateAfterEvent(vo);
	}

	/**
	 * �������ݿ�
	 * @param vo ���µ�AggVO
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
	 * ���»���
	 * @param vo AggVO������VO
	 * @throws BusinessException
	 */
	protected void notifyVersionChangeWhenDataUpdated(SuperVO vo)
			throws BusinessException {
		CacheProxy.fireDataUpdated(bizInfoUtil.getTableName(vo), null);
	}

	/**
	 * ��¼���²�����ҵ����־
	 * @param vo AggVO������VO
	 * @throws BusinessException
	 */
	protected void writeUpdatedBusiLog(SuperVO vo) throws BusinessException {
		getBusiLogUtil().writeBusiLog(IBusiOperateConst.EDIT, null, vo);
	}
	

	
	/* ����Ϊɾ��������Ҫ�ĸ�������*/
	/**
	 * ɾ��ʱ����ļ�����������������������
	 * @param vo AggVO����VO
	 */
	protected void deletelockOperate(SuperVO vo) throws BusinessException {
		// �����ֹ���
		BDPKLockUtil.lockSuperVO(vo);
	}

	/**
	 * ɾ��ǰУ��AggVO
	 * @param vo AggVO����
	 * @throws BusinessException
	 */
	protected void deleteValidateVO(SuperVO vo) throws BusinessException {
	//protected void deleteValidateVO(AggregatedValueObject vo) throws BusinessException {
		IValidationService validationService = ValidationFrameworkUtil
				.createValidationService(getDeleteValidator());
		validationService.validate(vo);
	}

	/**
	 * ȡ��ɾ��У��������
	 * @return ɾ��У��������
	 */
	protected Validator[] getDeleteValidator() {
		Validator[] validators = new Validator[] { BDReferenceChecker
				.getInstance() };
		return validators;
	}

	/**
	 * ����ɾ��ǰҵ���¼�
	 * @param vo ɾ����AggVO����
	 * @throws BusinessException
	 */
	protected void fireBeforeDeleteEvent(AggregatedValueObject vo) throws BusinessException {
		BDCommonEventUtil eventUtil = new BDCommonEventUtil(getMDId());
		eventUtil.dispatchDeleteBeforeEvent(vo);
	}
	
	/**
	 * ����ɾ��ǰҵ���¼�
	 * @param vo ɾ����AggVO����
	 * @throws BusinessException
	 */
	protected void fireAfterDeleteEvent(AggregatedValueObject vo) throws BusinessException {
		BDCommonEventUtil eventUtil = new BDCommonEventUtil(getMDId());
		eventUtil.dispatchDeleteAfterEvent(vo);
	}

	/**
	 * AggVO�����ݿ�ɾ������
	 * @param vo ɾ����AggVO����
	 * @throws BusinessException
	 */
	protected void dbDeleteVO(AggregatedValueObject vo) throws BusinessException {
		getMDPersistenceService().deleteBillFromDB(vo);
	}

	/**
	 * ���»���
	 * @param vo ɾ����AggVO����
	 * @throws BusinessException
	 */
	protected void notifyVersionChangeWhenDataDeleted(SuperVO vo)
			throws BusinessException {
		CacheProxy.fireDataDeleted(bizInfoUtil.getTableName(vo), vo.getPrimaryKey());
	}
	
	/**
	 * ��¼AggVOɾ����ҵ�����
	 * @param vo ɾ����AggVO����
	 * @throws BusinessException
	 */
	protected void writeDeletedBusiLog(SuperVO vo) throws BusinessException {
		getBusiLogUtil().writeBusiLog(IBusiOperateConst.DELETE, null, vo);
	}
	/* ����Ϊ������������*/
	@SuppressWarnings("unchecked")
	protected <T extends AggregatedValueObject>T retrieveVO(T vo) throws BusinessException {
		SuperVO parentVO = (SuperVO) vo.getParentVO();
		String pk = parentVO.getPrimaryKey();
		if (StringUtils.isBlank(pk))
			return null;
		return (T) getMDQueryService().queryBillOfVOByPK(vo.getClass(), pk, false);
        
	}

	/**
	 * ȡ��ҵ����־������
	 * @return ҵ����־������
	 */
	protected IBDBusiLogUtil getBusiLogUtil() {
		if (busiLogUtil == null) {
			busiLogUtil = new BDBusiLogUtil(getMDId());
		}
		return busiLogUtil;
	}
	
	/**
	 * ȡ��Ԫ�ؾݳ־û�����
	 * @return Ԫ�ؾݳ־û�����
	 */
	protected static IMDPersistenceService getMDPersistenceService(){
		return MDPersistenceService.lookupPersistenceService();
	}
	
	/**
	 * ��ȡlfw�ṩ��Ĭ�Ϸ�����
	 * @return
	 */
	protected static ILfwCudService getLfwPersitenceService(){
		return NCLocator.getInstance().lookup(ILfwCudService.class);
	}
	
    /**
     * ȡ��Ԫ���ݲ�ѯ����
     * @return Ԫ���ݲ�ѯ����
     */
    protected static IMDPersistenceQueryService getMDQueryService(){
        return MDPersistenceService.lookupPersistenceQueryService();
    }
    
	/**
	 * ȡ��Ԫ����id
	 * @return Ԫ����id
	 */
	public String getMDId() {
		return MDId;
	}
}
