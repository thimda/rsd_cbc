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
 * Copy from uapbd,ΪЭͬ���������ṩ֧��
 * �������ͻ�������ͨ�����������ݿ���������� <br>
 * ��ָ�����б�ʽ���� <br>
 * ע��
 * <li> �÷��������ڵ�������Ĳ�����
 * <li> �˷������Ǻ�̨����Ļ��࣬��ֻ�����ں�̨��
 * <li> ���������Ԫ���ݷ�ʽ�־û�����Ϊ��ԡ�����VO����
 * 
 * 
 */
@SuppressWarnings("unchecked")
public class ManageTypeBaseService<T extends SuperVO> {

	/* ������Ԫ����ID */
	private String MDId;
	/* ��ȡҵ����Ϣ�Ĺ��� */
	protected IGetBizInfoUtil bizInfoUtil = new DefaultGetBizInfoByMDUtil();
	/* ����VO�����ݷ����� */
	private SuperVOQry qryDAO;
	/* ����VO������ */
	private Class<?> entityClass;
	/* ���ڸ��ӵĹ����͵�������Ҫ���������VO������ */
	private String[] subAttributeNames;
	/** ҵ����־д�빤�� */
	private IBDBusiLogUtil busiLogUtil = null;

	/* ��ȡ����VO������ */
	public Class<?> getEntityClass() {
		return entityClass;
	}

	/* ���ڸ��ӵĹ����͵�������ȡ��Ҫ���������VO���������������ʵ�ָ÷��� */
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

	/**
	 * ��ȡ����Ԫ����ID
	 * 
	 * @return
	 * @throws MetaDataException
	 */
	public String getMDId() {
		return MDId;
	}

	/** ***************** ɾ������ ********************** */
	/**
	 * ɾ��ʱ����ļ�����������������������
	 */
	protected void deletelockOperate(T vo) throws BusinessException {
		// �����ֹ���
		BDPKLockUtil.lockSuperVO(vo);
	}

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

	protected void fireBeforeDeleteEvent(T vo) throws BusinessException {
		BDCommonEventUtil eventUtil = new BDCommonEventUtil(getMDId());
		eventUtil.dispatchDeleteBeforeEvent(vo);
	}

	/**
	 * �����͵����ĵ���ɾ������
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
	 * ��¼ɾ��������ҵ����־
	 * 
	 * @param vo
	 * @throws BusinessException
	 */
	protected void writeDeletedBusiLog(T vo) throws BusinessException {
		getBusiLogUtil().writeBusiLog(IBusiOperateConst.DELETE, null, vo);
	}

	/** ***************** �������� ********************** */
	/**
	 * ����ʱ����ļ�������������ҵ���������������Ҫ�����������
	 */
	protected void insertlockOperate(T vo) throws BusinessException {
		// ��ҵ����
		BizlockDataUtil.lockDataByBizlock(vo);
	}

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

	/**
	 * ���ò���ʱ�������Ϣ
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
		// ��ʶ��������״̬
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

		// ֪ͨ���»���
		notifyVersionChangeWhenDataInserted(vo);

		// ���¼����������VO
		T newVo = retrieveVO(pk);

		//���²��VO���VO���кϲ�
		mergeVO(vo, newVo);
		
		// �����¼���֪ͨ
		fireAfterInsertEvent(vo);

		// ҵ����־
		writeInsertBusiLog(vo);

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
		vo.setStatus(Row.STATE_NORMAL);
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

	/** ***************** ���²��� ********************** */
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

	protected Validator[] getUpdateValidator(T oldVO) {
		Validator[] validators = new Validator[] { new BDUniqueRuleValidate() };
		return validators;
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
	 * �����͵����������²���
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

		// ����ʱ�ļ�������
		updatelockOperate(vo);

		// У��汾
		BDVersionValidationUtil.validateSuperVO(vo);

		// ��ȡ����ǰ��OldVOs
		T oldVO = retrieveVO(vo);

		// ҵ��У���߼�
		updateValidateVO(oldVO, vo);

		// ���������Ϣ
		setUpdateAuditInfo(vo);

		// ����ǰ�¼�����
		fireBeforeUpdateEvent(oldVO, vo);

		// �����
		dbUpdateVO(vo);

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
	 * ��¼���²�����ҵ����־
	 * 
	 * @param vo
	 * @throws BusinessException
	 */
	protected void writeUpdatedBusiLog(T vo) throws BusinessException {
		getBusiLogUtil().writeBusiLog(IBusiOperateConst.EDIT, null, vo);
	}

	/** ***************** ͣ�� ********************** */
	public ValueObjWithErrLog disableVO(T... vos) throws BusinessException {
		ValueObjWithErrLog returnValue = null;
		T[] disabledVOs = null;

		if (vos == null || vos.length == 0)
			return null;

		// LiFIXME: ����Ȩ��У��

		// �Ӽ���������������
		BDPKLockUtil.lockSuperVO(vos);

		// �汾У�飨ʱ���У�飩
		BDVersionValidationUtil.validateSuperVO(vos);

		// ҵ��У��
		returnValue = filterCanDisableVO(vos);
		T[] canDisabledVOs = (T[]) returnValue.getVos();

		if (canDisabledVOs != null && canDisabledVOs.length > 0) {
			// ����ͣ�ñ�־
			setDisableFlags(canDisabledVOs);

			// ����ͣ�õ������Ϣ
			setDisableAuditInfos(canDisabledVOs);

			// �¼�ǰ����֪ͨ
			fireBeforeDisableEvent(canDisabledVOs);

			// ���ݱ��浽���ݿ�
			dbDisableVOs(canDisabledVOs);

			// ����֪ͨ
			notifyVersionChangeWhenDataUpdated(vos[0]);

			// �����ѱ�����VO
			disabledVOs = retrieveVOs(canDisabledVOs);

			// �¼���֪ͨ
			fireAfterDisableEvent(disabledVOs);
		}
		// ƴװ������Ϣ
		returnValue.setVos(disabledVOs);

		// ҵ����־
		writeDisableBusiLog(disabledVOs);

		return returnValue;
	}

	protected void setDisableFlags(T... vos) {
		// ����ͣ�ñ�־
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
		// ͣ��
		new BaseDAO().updateVOArray(vos, new String[] {
				IBaseServiceConst.ENABLESTATE_FIELD,
				IBaseServiceConst.MODIFIER_FIELD,
				IBaseServiceConst.MODIFIEDTIME_FIELD });
	}

	/**
	 * ���˴�ͣ�����ݣ�����̳д˷�����������˲��� <br>
	 * Ĭ�Ϲ��˵�����ͣ�á�������
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

		// LiFIXME: ����Ȩ��У��

		// �Ӽ���������������
		BDPKLockUtil.lockSuperVO(vo);

		// �汾У�飨ʱ���У�飩
		BDVersionValidationUtil.validateSuperVO(vo);

		// ҵ���߼�У��
		T oldVO = (T) new BaseDAO().retrieveByPK(getEntityClass(), vo
				.getPrimaryKey(),
				new String[] { IBaseServiceConst.ENABLESTATE_FIELD });
		Integer enable_state = (Integer) oldVO.getAttributeValue(IBaseServiceConst.ENABLESTATE_FIELD);
		if (IPubEnumConst.ENABLESTATE_DISABLE == enable_state) {
			// ��ǰ�����Ѿ�ͣ��
			return vo;
		}
		
		disableValidateSingleVO(vo);

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

		// �����ѱ�����VO
		vo = retrieveVO(vo);

		// �¼���֪ͨ
		fireAfterDisableEvent(vo);

		// ҵ����־
		writeDisableBusiLog(vo);

		return vo;
	}

	/**
	 * ��¼ͣ�ò�����ҵ����־
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
	 * ����ͣ��У���� <br>
	 * ������д�÷���������ҵ����Ҫ���ж���У�鼯��
	 * 
	 * @return ҵ��У������
	 */
	protected Validator[] getDisableValidator() {
		return null;
	}

	protected void setDisableFlag(T vo) {
		// ����ͣ�ñ�־
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
		// ͣ��
		new BaseDAO().updateVO(vo, new String[] {
				IBaseServiceConst.ENABLESTATE_FIELD,
				IBaseServiceConst.MODIFIER_FIELD,
				IBaseServiceConst.MODIFIEDTIME_FIELD });
	}

	/** ***************** ���� ********************** */
	public ValueObjWithErrLog enableVO(T... vos) throws BusinessException {
		ValueObjWithErrLog returnValue = null;
		T[] enabledVOs = null;

		if (vos == null || vos.length == 0)
			return null;

		// LiFIXME: ����Ȩ��У��

		// �Ӽ���������������
		BDPKLockUtil.lockSuperVO(vos);

		// �汾У�飨ʱ���У�飩
		BDVersionValidationUtil.validateSuperVO(vos);

		// ҵ��У��
		returnValue = filterCanEnableVO(vos);
		T[] canEnableVOs = (T[]) returnValue.getVos();
		T[] canEnableOldVOs = retireveOldVOs(
				new String[] { IBaseServiceConst.ENABLESTATE_FIELD },
				canEnableVOs);

		if (canEnableVOs != null && canEnableVOs.length > 0) {
			// �������ñ�־
			setEnableFlags(canEnableVOs);

			// �������õ������Ϣ
			setEnableAuditInfos(canEnableVOs);

			// �¼�ǰ����֪ͨ
			fireBeforeEnableEvent(canEnableOldVOs, canEnableVOs);

			// ���ݱ��浽���ݿ�
			dbEnableVOs(canEnableVOs);

			// ����֪ͨ
			notifyVersionChangeWhenDataUpdated(vos[0]);

			// �����ѱ�����VO
			enabledVOs = retrieveVOs(canEnableVOs);

			// �¼���֪ͨ
			fireAfterEnableEvent(canEnableOldVOs, enabledVOs);
		}
		// ƴװ������Ϣ
		returnValue.setVos(enabledVOs);

		// ҵ����־
		writeEnableBusiLog(enabledVOs);

		return returnValue;
	}

	protected void setEnableFlags(T... vos) {
		// �������ñ�־
		for (T t : vos) {
			t.setAttributeValue(IBaseServiceConst.ENABLESTATE_FIELD,
					IPubEnumConst.ENABLESTATE_ENABLE);
		}
	}

	protected void setEnableAuditInfos(T... vos) {

		AuditInfoUtil.updateData(vos);
	}

	/**
	 * ��vos�У����˵�������״̬��Ϊxx_state��VO
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
	 * ���˴��������ݣ�����̳д˷�����������˲��� <br>
	 * Ĭ�Ϲ��˵������õ�VO
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
		List<T> init2EnableList = new ArrayList<T>(); // δ���ü�
		List<T> disable2EnableList = new ArrayList<T>(); // ͣ�ü�
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
		List<T> init2EnableList = new ArrayList<T>(); // δ���ü�
		List<T> disable2EnableList = new ArrayList<T>(); // ͣ�ü�
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
	 * ͨ��enableState�ֶεıȽϣ���oldVOs, vos�����մӡ�δ���á��������á���"ͣ��"��"����"�����֣�
	 * ��������д��init2EnableList, disable2EnableList��; <br>
	 * oldVOs��vos���밴��pk,һһ��Ӧ;
	 * 
	 * @param oldVOs
	 * @param vos
	 * @param init2EnableList
	 *            δ���ü�,����Ϊnull���ұ���sizeΪ0��List
	 * @param disable2EnableList
	 *            ͣ�ü�,����Ϊnull���ұ���sizeΪ0��List
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
		// ����
		new BaseDAO().updateVOArray(vos, new String[] {
				IBaseServiceConst.ENABLESTATE_FIELD,
				IBaseServiceConst.MODIFIER_FIELD,
				IBaseServiceConst.MODIFIEDTIME_FIELD });
	}

	public T enableSingleVO(T vo) throws BusinessException {

		if (vo == null)
			return null;

		// LiFIXME: ����Ȩ��У��

		// �Ӽ���������������
		BDPKLockUtil.lockSuperVO(vo);

		// �汾У�飨ʱ���У�飩
		BDVersionValidationUtil.validateSuperVO(vo);

		// ҵ��У��
		T oldVO = (T) new BaseDAO().retrieveByPK(getEntityClass(), vo
				.getPrimaryKey(),
				new String[] { IBaseServiceConst.ENABLESTATE_FIELD });
		Integer enable_state = (Integer) oldVO.getAttributeValue(IBaseServiceConst.ENABLESTATE_FIELD);
		if (IPubEnumConst.ENABLESTATE_ENABLE == enable_state) {
			// ��ǰ�����Ѿ�����
			return vo;
		}
		
		enableValidateSingleVO(vo);

		// �������ñ�־
		setEnableFlag(vo);

		// �������õ������Ϣ
		setEnableAuditInfo(vo);

		// �¼�ǰ����֪ͨ
		fireBeforeEnableEvent(oldVO, vo);

		// ���ݱ��浽���ݿ�
		dbEnableVO(vo);

		// ����֪ͨ
		notifyVersionChangeWhenDataUpdated(vo);

		// �����ѱ�����VO
		vo = retrieveVO(vo);

		// �¼���֪ͨ
		fireAfterEnableEvent(oldVO, vo);

		// ҵ����־
		writeEnableBusiLog(vo);

		return vo;
	}

	/**
	 * ��¼���ò�����ҵ����־
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
	 * ��������У���� <br>
	 * ������д�÷���������ҵ����Ҫ���ж���У�鼯��
	 * 
	 * @return ҵ��У������
	 */
	protected Validator[] getEnableValidator() {
		return null;
	}

	protected void setEnableFlag(T vo) {
		// �������ñ�־
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
		// 	����
		new BaseDAO().updateVO(vo, new String[] {
				IBaseServiceConst.ENABLESTATE_FIELD,
				IBaseServiceConst.MODIFIER_FIELD,
				IBaseServiceConst.MODIFIEDTIME_FIELD });
	}



	/** ***************** ���� ********************** */
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
	 * ��ѯ����OldVO, ��oldVO��vos������pk,һһ��Ӧ��
	 * 
	 * @param fields
	 *            �����ֶΣ�Ϊnullʱ����������VO��ȫ����Ϣ
	 * @param vos
	 *            ����VO
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
	 * ��ѯOldVO����˳���ǰ���pks��˳�򣬼�vos������˳��
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
	 * ����Ԫ���ݳ־û��������
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
