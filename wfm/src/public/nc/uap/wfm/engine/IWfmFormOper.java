package nc.uap.wfm.engine;
import nc.uap.wfm.context.WfmFlowInfoCtx;
import nc.uap.wfm.model.HumAct;
import nc.uap.wfm.model.ProDef;
import nc.uap.wfm.model.Task;
import nc.uap.wfm.vo.WfmFlwTypeVO;
import nc.uap.wfm.vo.WfmFormInfoCtx;
import nc.vo.pub.SuperVO;
public interface IWfmFormOper {
	/**
	 * ���ݱ���
	 * 
	 * @param frmInfoCtx
	 * @param flwInfoCtx
	 */
	WfmFormInfoCtx save(WfmFormInfoCtx frmInfoCtx, WfmFlowInfoCtx flwInfoCtx);
	/**
	 * ���ݸ���
	 * 
	 * @param frmInfoCtx
	 * @param flwInfoCtx
	 */
	WfmFormInfoCtx update(WfmFormInfoCtx frmInfoCtx, WfmFlowInfoCtx flwInfoCtx);
	/**
	 * ��������ִ�л�ȡUrl
	 */
	String getHanlderUrlByTask(Task task);
	/**
	 * �����������ͻ�ȡ���̶���
	 * 
	 * @param flowTypeVo
	 * @return
	 */
	ProDef getProDefByFlowType(WfmFlwTypeVO flowTypeVo);
	/**
	 * ��ȡ���̽ڵ����������Ե�Url
	 * 
	 * @param humAct
	 * @return
	 */
	String getExtAttrSettingUrl(HumAct humAct);
	/**
	 * �����������̶�������չ
	 * 
	 * @param proDef
	 */
	void afterSaveOrUpdateProDef(ProDef proDef);
	/**
	 * ɾ�����̶�������չ
	 * 
	 * @param proDef
	 */
	void afterDeleteProDef(ProDef proDef);
	/**
	 * ��ȡҵ��Ԫ����
	 */
	Class<SuperVO>[] getBizMetaDataDesc(WfmFlwTypeVO flowTypeVo);
}
