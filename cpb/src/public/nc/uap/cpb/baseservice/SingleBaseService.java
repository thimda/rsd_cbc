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
 * Copy from uapbd,ΪЭͬ���������ṩ֧��
 * ��Ե�������VO�ĺ�̨�������� <br>
 * ����̨�������BaseDAO��ʽ���־û��������в���������Ե�������VO���еģ�
 * 
 * @since NC6.1
 * 
 * @param <T>
 */
public class SingleBaseService<T extends SuperVO> {

	/** ������Ԫ����ID */
	private String MDId;
	/** ��ȡҵ����Ϣ�Ĺ��� */
	protected IGetBizInfoUtil bizInfoUtil = new DefaultGetBizInfoByMDUtil();
	/** ҵ����־д�빤�� */
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
	 * ����ҵ����־д��ӿ�
	 * 
	 * @param busiLogUtil
	 */
	protected void setBusiLogUtil(IBDBusiLogUtil busiLogUtil) {
		this.busiLogUtil = busiLogUtil;
	}

	/**
	 * ���ҵ����־д��ӿ�
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

		// ����ʱ�ļ�������
		insertlockOperate(vo);
		// �߼�У��
		insertValidateVO(vo);

		// ���������Ϣ
		setInsertAuditInfo(vo);

		// ����ǰ�¼�֪ͨ
		fireBeforeInsertEvent(vo);

		// �����
		String pk = dbInsertVO(vo);
		vo.setPrimaryKey(pk);

		// ֪ͨ���»���
		notifyVersionChangeWhenDataInserted(vo);

		// ���¼����������VO
		T newVo = retrieveVO(vo);

		mergeVO(vo, newVo);
		
		// �����¼���֪ͨ
		fireAfterInsertEvent(vo);

		// ҵ����־
		writeInsertBusiLog(vo);

		return vo;
	}

	/**
	 * ��¼����������ҵ����־
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

		// ����ʱ�ļ�������
		updatelockOperate(vo);

		// У��汾
		BDVersionValidationUtil.validateSuperVO(vo);

		// ��ȡ����ǰ��OldVO
		T oldVO = retrieveVO(vo);

		// ҵ��У���߼�
		updateValidateVO(oldVO, vo);

		// ���������Ϣ
		setUpdateAuditInfo(vo);

		// ����ǰ�¼�����
		fireBeforeUpdateEvent(oldVO, vo);

		// �����
		dbUpdateVo(vo);

		// ���»���
		notifyVersionChangeWhenDataUpdated(vo);

		// ���¼�����������
		T newVo = retrieveVO(vo);

		mergeVO(vo, newVo);
		
		// ���º��¼�֪ͨ
		fireAfterUpdateEvent(oldVO, vo);

		// ҵ����־
		writeUpdatedBusiLog(vo);

		return vo;
	}

	/**
	 * ����VO�ϲ�
	 * @param vo
	 * @param newVo
	 */
	protected void mergeVO(T vo, T newVo) {
		vo.setAttributeValue("ts", newVo.getAttributeValue("ts"));
		vo.setPrimaryKey(newVo.getPrimaryKey());
	}
	
	/**
	 * ��¼���²�����ҵ����־
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

		// ɾ��ʱ�ļ�������
		deletelockOperate(vo);

		// У��汾
		BDVersionValidationUtil.validateSuperVO(vo);

		// ɾ��ǰ���ö���У��
		deleteValidateVO(vo);

		// ɾ��ǰ�¼�����
		fireBeforeDeleteEvent(vo);

		// ����֪ͨ
		notifyVersionChangeWhenDataDeleted(vo);

		// �����
		dbDeleteVO(vo);

		// ɾ�����¼�֪ͨ
		fireAfterDeleteEvent(vo);

		// ҵ����־
		writeDeletedBusiLog(vo);
	}

	/**
	 * ��¼ͣ�ò�����ҵ����־
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

		// LiFIXME: ����Ȩ��У��

		// �Ӽ���������������
		BDPKLockUtil.lockSuperVO(vo);

		// �汾У�飨ʱ���У�飩
		BDVersionValidationUtil.validateSuperVO(vo);

		// ҵ��У���߼�
		T oldVO = (T) new BaseDAO().retrieveByPK(vo.getClass(), vo
				.getPrimaryKey(),
				new String[] { IBaseServiceConst.ENABLESTATE_FIELD });
		Integer enable_state = (Integer) oldVO
				.getAttributeValue(IBaseServiceConst.ENABLESTATE_FIELD);
		if (IPubEnumConst.ENABLESTATE_DISABLE == enable_state) {
			// ��ǰ�����Ѿ�ͣ��
			return vo;
		}

		disableValidateVO(vo);

		// ����ͣ�ñ�־
		setDisableFlag(vo);

		// ����ͣ�õ������Ϣ
		setDisableAuditInfo(vo);

		// �¼�ǰ֪ͨ
		fireBeforeDisableEvent(vo);

		// ���ݱ��浽���ݿ�
		dbDisableVO(vo);

		// ����֪ͨ
		notifyVersionChangeWhenDataUpdated(vo);

		// �����ѱ�ͣ�õ�VO
		T disabledVO = retrieveVO(vo);

		// �¼���֪ͨ
		fireAfterDisableEvent(disabledVO);

		// ҵ����־
		writeDisableBusiLog(disabledVO);

		return disabledVO;
	}

	/**
	 * ��¼ͣ�ò�����ҵ����־
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

		// LiFIXME: ����Ȩ��У��

		// �Ӽ���������������
		BDPKLockUtil.lockSuperVO(vo);

		// �汾У�飨ʱ���У�飩
		BDVersionValidationUtil.validateSuperVO(vo);

		// ҵ��У��
		T oldVO = (T) new BaseDAO().retrieveByPK(vo.getClass(), vo
				.getPrimaryKey(),
				new String[] { IBaseServiceConst.ENABLESTATE_FIELD });
		Integer enable_state = (Integer) oldVO
				.getAttributeValue(IBaseServiceConst.ENABLESTATE_FIELD);
		if (IPubEnumConst.ENABLESTATE_ENABLE == enable_state) {
			// ��ǰ�����Ѿ�����
			return vo;
		}

		enableValidateVO(vo);

		// �������ñ�־
		setEnableFlag(vo);

		// �������õ������Ϣ
		setEnableAuditInfo(vo);

		// �¼�ǰ֪ͨ
		fireBeforeEnableEvent(oldVO, vo);

		// ���ݱ��浽���ݿ�
		dbEnableVO(vo);

		// ����֪ͨ
		notifyVersionChangeWhenDataUpdated(vo);

		// �����ѱ�����VO
		T enabledVO = retrieveVO(vo);

		// �¼���֪ͨ
		fireAfterEnableEvent(oldVO, enabledVO);

		// ҵ����־
		writeEnableBusiLog(enabledVO);

		return enabledVO;
	}

	/**
	 * ��¼���ò�����ҵ����־
	 * 
	 * @param vo
	 * @throws BusinessException
	 */
	protected void writeEnableBusiLog(T vo) throws BusinessException {
		getBusiLogUtil().writeBusiLog(IBusiOperateConst.ENABLE, null, vo);
	}

	// ����Ϊ���ò�����Ҫ�ĸ�������-------------------------------------------------
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
		// ����
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
	 * ����У��У���� <br>
	 * ������д�÷���������ҵ����Ҫ���ж���У�鼯��
	 * 
	 * @return
	 */
	protected Validator[] getEnableValidator() {
		return null;
	}

	/**
	 * �������ñ�־
	 * 
	 * @param vo
	 */
	protected void setEnableFlag(T vo) {
		vo.setAttributeValue(IBaseServiceConst.ENABLESTATE_FIELD,
				IPubEnumConst.ENABLESTATE_ENABLE);
	}

	/**
	 * �������õ������Ϣ
	 * 
	 * @param vo
	 */
	protected void setEnableAuditInfo(T vo) {

		AuditInfoUtil.updateData(vo);
	}

	// ����Ϊ��������Ҫ�ĸ�������-------------------------------------------------
	protected void fireBeforeDisableEvent(T vo) throws BusinessException {
		BDCommonEventUtil eventUtil = new BDCommonEventUtil(getMDId());
		eventUtil.dispatchEnableToDisableBeforeEvent(vo);
	}

	protected void fireAfterDisableEvent(T vo) throws BusinessException {
		BDCommonEventUtil eventUtil = new BDCommonEventUtil(getMDId());
		eventUtil.dispatchEnableToDisableAfterEvent(vo);
	}

	protected void dbDisableVO(T vo) throws BusinessException {
		// ͣ��
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
	 * ͣ��У��У���� <br>
	 * ������д�÷���������ҵ����Ҫ���ж���У�鼯��
	 * 
	 * @return
	 */
	protected Validator[] getDisableValidator() {
		return null;
	}

	/**
	 * ����ͣ�ñ�־
	 * 
	 * @param vo
	 */
	protected void setDisableFlag(T vo) {
		vo.setAttributeValue(IBaseServiceConst.ENABLESTATE_FIELD,
				IPubEnumConst.ENABLESTATE_DISABLE);
	}

	/**
	 * ����ͣ�õ������Ϣ
	 * 
	 * @param vo
	 */
	protected void setDisableAuditInfo(T vo) {

		AuditInfoUtil.updateData(vo);
	}

	// ����Ϊ����������Ҫ�ĸ�������--------------------------------------------------
	/**
	 * ����ʱ����ļ�������������ҵ���������������Ҫ�����������
	 */
	protected void insertlockOperate(T vo) throws BusinessException {
		// ��ҵ����
		BizlockDataUtil.lockDataByBizlock(vo);
	}

	/**
	 * ������ҵ���߼�У��
	 * 
	 * @param vo
	 *            �������Ķ���
	 * @throws BusinessException
	 */
	protected void insertValidateVO(T vo) throws BusinessException {
		IValidationService validateService = ValidationFrameworkUtil
				.createValidationService(getInsertValidator());
		validateService.validate(vo);
	}

	/**
	 * ��������У���� <br>
	 * ������д�÷���������ҵ����Ҫ���ж���У�鼯��
	 * 
	 * @return ҵ��У������
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
	 * ��ȡ����Ԫ����ID
	 * 
	 * @return
	 * @throws MetaDataException
	 */
	public String getMDId() {
		return MDId;
	}

	// ����Ϊ�����¼���Ҫ�ĸ�������-----------------------------------------------------
	/**
	 * �޸�ʱ����ļ���������������������ҵ������
	 */
	protected void updatelockOperate(T vo) throws BusinessException {
		// �����ֹ���
		BDPKLockUtil.lockSuperVO(vo);
		// ҵ����
		BizlockDataUtil.lockDataByBizlock(vo);
	}

	protected void updateValidateVO(T oldVO, T vo) throws BusinessException {
		IValidationService validateService = ValidationFrameworkUtil
				.createValidationService(getUpdateValidator(oldVO));
		validateService.validate(vo);
	}

	/**
	 * �������У���� <br>
	 * ������д�÷���������ҵ����Ҫ���ж���У�鼯��
	 * 
	 * @return ҵ��У������
	 */
	protected Validator[] getUpdateValidator(T oldVO) {
		Validator[] validators = new Validator[] { new BDUniqueRuleValidate() };
		return validators;
	}

	protected void setUpdateAuditInfo(T vo) {

		AuditInfoUtil.updateData(vo);
	}

	/**
	 * ����ǰ�¼�֪ͨ����
	 * 
	 * @param oldVOs
	 *            ����ǰ��OldVOs
	 * @param vo
	 *            �����µ�VOs
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
			Logger.error("����" + vo.getClass().getName() + "ʱ����", e);
			throw new BusinessException(e.getMessage(), e);
		}
	}

	protected void notifyVersionChangeWhenDataUpdated(T vo)
			throws BusinessException {
		CacheProxy.fireDataUpdated(bizInfoUtil.getTableName(vo), null);
	}

	// ����Ϊɾ���¼���Ҫ�ĸ�������----------------------------------------
	/**
	 * ɾ��ʱ����ļ�����������������������
	 */
	protected void deletelockOperate(T vo) throws BusinessException {
		// �����ֹ���
		BDPKLockUtil.lockSuperVO(vo);
	}

	/**
	 * ɾ�����߼�У��
	 * 
	 * @param vo
	 *            ��ɾ���Ķ���
	 * @throws BusinessException
	 */
	protected void deleteValidateVO(T vo) throws BusinessException {
		IValidationService validationService = ValidationFrameworkUtil
				.createValidationService(getDeleteValidator());
		validationService.validate(vo);
	}

	/**
	 * ����ɾ��У���� <br>
	 * ������д�÷���������ҵ����Ҫ���ж���У�鼯��
	 * 
	 * @return ҵ��У������
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
			Logger.error("ɾ��" + vo.getClass().getName() + "ʱ����", e);
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

	/** ************************* ����VO���� ****************************** */
	@SuppressWarnings("unchecked")
	protected T retrieveVO(T vo) throws BusinessException {
		String pk = vo.getPrimaryKey();
		if (StringUtils.isBlank(pk))
			return null;
		T retrieveVO = (T) new BaseDAO().retrieveByPK(vo.getClass(), pk);
		return retrieveVO;
	}

	/** ************************** ���� *********************************** */
}
