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
	 * ��������Pk��ȡ������Ҫ��ת�ĵ�ַ
	 * 
	 * @param task
	 * @return
	 */
	String getUrlByTask(Task task);
	/**
	 * ���������ȡ������Ҫ��ת�ĵ�ַ
	 * 
	 * @param task
	 * @return
	 */
	String getUrlByTaskPk(String taskPk);
	/**
	 * ��ȡ�������͸��ݹ����ظ��ڵ�ID
	 * 
	 * @param pageId
	 * @return
	 */
	WfmFlwTypeVO getFlwTypeVoByPageId(String pageId);
	/**
	 * ��ȡ��������̬��һ������Ϣ
	 * 
	 * @param taskPk
	 * @param formVo
	 * @return
	 */
	List<HumActInfoPageCtx> getNextHumActInfoCtx(String taskPk, WfmFormInfoCtx formVo);
	/**
	 * ��ȡ���̷���̬����һ������Ϣ
	 * 
	 * @param frmDefPk
	 * @param formVo
	 * @return
	 */
	List<HumActInfoPageCtx> getStartHumActInfoCtx(String frmDefPk, WfmFormInfoCtx formVo);
	/**
	 * ��ȡ��������̬���˲�����Ϣ
	 * 
	 * @param taskPk
	 * @param formVo
	 * @return
	 */
	List<HumActInfoPageCtx> getRejectHumActInfoCtx(String taskPk, WfmFormInfoCtx formVo);
	/**
	 * ��ȡ����̬����ͼ
	 * 
	 * @param taskPk
	 * @return
	 */
	String getProcessImageUrlByTaskPk(String taskPk);
	/**
	 * ��ȡ����̬������ͼ
	 * 
	 * @param frmDefPk
	 * @return
	 */
	String getProcessImageUrlByFrmDefPk(String frmDefPk);
	/**
	 * ��ȡ����̬�������ĵ�Url
	 * 
	 * @param frmDefPk
	 * @return
	 */
	String getWordUrlByFrmDefPk(String frmDefPk);
	/**
	 * ��ȡ��������̬��url
	 * 
	 * @param taskPk
	 * @return
	 */
	String getWordUrlbyTaskPk(String taskPk);
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
