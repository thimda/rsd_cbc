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
	 * 活动完成后的扩展操作
	 */
	void beforeHumAct();
	/**
	 * 活动完成后的扩展操作
	 */
	void afterHumAct();
	/**
	 * 获取该活动对应的任务扩展的class
	 * 
	 * @return
	 */
	String getTaskExtClazz();
	/**
	 * 单据数据检查扩展操作
	 * 
	 * @param formVo
	 * @param flwType
	 * @throws WfmValidateException
	 */
	void check(WfmFormInfoCtx formVo, WfmFlwTypeVO flowTypeVo) throws WfmValidateException;
	/**
	 * 是否允许指派扩展操作
	 * 
	 * @param task
	 * @param humAct
	 * @return
	 */
	boolean isAssign(Task task, HumAct humAct);
	/**
	 * 任务前加签的组织描述
	 * 
	 * @param task
	 * @return
	 */
	WfmFlowOrgDesc[] getBeforeAddSignOrgDesc(String taskPk);
	/**
	 * 任务前加签的部门描述
	 * 
	 * @param taskPk
	 * @param orgPk
	 * @return
	 */
	WfmFlowDeptDesc[] getBeforeAddSignDeptDesc(String taskPk, String orgPk);
	/**
	 * 任务前加签的用户描述
	 * 
	 * @param taskPk
	 * @param deptPk
	 * @return
	 */
	WfmFlowUserDesc[] getBeforeAddSignUserDesc(String taskPk, String deptPk);
	/**
	 * 任务传阅的组织描述
	 * 
	 * @param taskPk
	 * @return
	 */
	WfmFlowOrgDesc[] getDeliverOrgDesc(String taskPk);
	/**
	 * 任务传阅的部门传阅
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
