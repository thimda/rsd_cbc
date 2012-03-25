package nc.uap.wfm.engine;
import nc.uap.wfm.exception.WfmValidateException;
import nc.uap.wfm.model.HumAct;
import nc.uap.wfm.model.Task;
import nc.uap.wfm.orgs.WfmFlowDeptDesc;
import nc.uap.wfm.orgs.WfmFlowOrgDesc;
import nc.uap.wfm.orgs.WfmFlowUserDesc;
import nc.uap.wfm.vo.WfmFlwTypeVO;
import nc.uap.wfm.vo.WfmFormInfoCtx;
public interface IHumActHandler {
	/**
	 * ���ɺ����չ����
	 */
	void beforeHumAct();
	/**
	 * ���ɺ����չ����
	 */
	void afterHumAct();
	/**
	 * ��ȡ�û��Ӧ��������չ��class
	 * 
	 * @return
	 */
	String getTaskExtClazz();
	/**
	 * �������ݼ����չ����
	 * 
	 * @param formVo
	 * @param flwType
	 * @throws WfmValidateException
	 */
	void check(WfmFormInfoCtx formVo, WfmFlwTypeVO flowTypeVo) throws WfmValidateException;
	/**
	 * �Ƿ�����ָ����չ����
	 * 
	 * @param task
	 * @param humAct
	 * @return
	 */
	boolean isAssign(Task task, HumAct humAct);
	/**
	 * ����ǰ��ǩ����֯����
	 * 
	 * @param task
	 * @return
	 */
	WfmFlowOrgDesc[] getBeforeAddSignOrgDesc(String taskPk);
	/**
	 * ����ǰ��ǩ�Ĳ�������
	 * 
	 * @param taskPk
	 * @param orgPk
	 * @return
	 */
	WfmFlowDeptDesc[] getBeforeAddSignDeptDesc(String taskPk, String orgPk);
	/**
	 * ����ǰ��ǩ���û�����
	 * 
	 * @param taskPk
	 * @param deptPk
	 * @return
	 */
	WfmFlowUserDesc[] getBeforeAddSignUserDesc(String taskPk, String deptPk);
	/**
	 * �����ĵ���֯����
	 * 
	 * @param taskPk
	 * @return
	 */
	WfmFlowOrgDesc[] getDeliverOrgDesc(String taskPk);
	/**
	 * �����ĵĲ��Ŵ���
	 * 
	 * @param taskPk
	 * @param orgPk
	 * @return
	 */
	WfmFlowDeptDesc[] getDeliverDeptDesc(String taskPk, String orgPk);
	/**
	 * chua
	 * 
	 * @param taskPk
	 * @param deptPk
	 * @return
	 */
	WfmFlowUserDesc[] getDeliverUserDesc(String taskPk, String deptPk);
}
