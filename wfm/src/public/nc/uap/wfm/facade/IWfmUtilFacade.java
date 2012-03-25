package nc.uap.wfm.facade;
import java.util.List;
import nc.uap.wfm.context.HumActInfoPageCtx;
import nc.uap.wfm.model.Task;
import nc.uap.wfm.orgs.WfmFlowDeptDesc;
import nc.uap.wfm.orgs.WfmFlowOrgDesc;
import nc.uap.wfm.orgs.WfmFlowUserDesc;
import nc.uap.wfm.vo.WfmFlwTypeVO;
import nc.uap.wfm.vo.WfmFormInfoCtx;
public interface IWfmUtilFacade {
	/**
	 * 根据任务Pk获取任务需要跳转的地址
	 * 
	 * @param task
	 * @return
	 */
	String getUrlByTask(Task task);
	/**
	 * 根据任务获取任务需要跳转的地址
	 * 
	 * @param task
	 * @return
	 */
	String getUrlByTaskPk(String taskPk);
	/**
	 * 获取单据类型根据功能呢个节点ID
	 * 
	 * @param pageId
	 * @return
	 */
	WfmFlwTypeVO getFlwTypeVoByPageId(String pageId);
	/**
	 * 获取流程运行态下一步的信息
	 * 
	 * @param taskPk
	 * @param formVo
	 * @return
	 */
	List<HumActInfoPageCtx> getNextHumActInfoCtx(String taskPk, WfmFormInfoCtx formVo);
	/**
	 * 获取流程发起态的下一步的信息
	 * 
	 * @param frmDefPk
	 * @param formVo
	 * @return
	 */
	List<HumActInfoPageCtx> getStartHumActInfoCtx(String frmDefPk, WfmFormInfoCtx formVo);
	/**
	 * 获取流程运行态回退步的信息
	 * 
	 * @param taskPk
	 * @param formVo
	 * @return
	 */
	List<HumActInfoPageCtx> getRejectHumActInfoCtx(String taskPk, WfmFormInfoCtx formVo);
	/**
	 * 获取运行态流程图
	 * 
	 * @param taskPk
	 * @return
	 */
	String getProcessImageUrlByTaskPk(String taskPk);
	/**
	 * 获取发起态的流程图
	 * 
	 * @param frmDefPk
	 * @return
	 */
	String getProcessImageUrlByFrmDefPk(String frmDefPk);
	/**
	 * 获取发起态流程正文的Url
	 * 
	 * @param frmDefPk
	 * @return
	 */
	String getWordUrlByFrmDefPk(String frmDefPk);
	/**
	 * 获取流程运行态的url
	 * 
	 * @param taskPk
	 * @return
	 */
	String getWordUrlbyTaskPk(String taskPk);
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
