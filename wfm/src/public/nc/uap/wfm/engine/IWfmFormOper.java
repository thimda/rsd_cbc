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
	 * 单据保存
	 * 
	 * @param frmInfoCtx
	 * @param flwInfoCtx
	 */
	WfmFormInfoCtx save(WfmFormInfoCtx frmInfoCtx, WfmFlowInfoCtx flwInfoCtx);
	/**
	 * 单据更新
	 * 
	 * @param frmInfoCtx
	 * @param flwInfoCtx
	 */
	WfmFormInfoCtx update(WfmFormInfoCtx frmInfoCtx, WfmFlowInfoCtx flwInfoCtx);
	/**
	 * 根据任务执行获取Url
	 */
	String getHanlderUrlByTask(Task task);
	/**
	 * 根据流程类型获取流程定义
	 * 
	 * @param flowTypeVo
	 * @return
	 */
	ProDef getProDefByFlowType(WfmFlwTypeVO flowTypeVo);
	/**
	 * 获取流程节点上扩张属性的Url
	 * 
	 * @param humAct
	 * @return
	 */
	String getExtAttrSettingUrl(HumAct humAct);
	/**
	 * 保存或更新流程定义后的扩展
	 * 
	 * @param proDef
	 */
	void afterSaveOrUpdateProDef(ProDef proDef);
	/**
	 * 删除流程定义后的扩展
	 * 
	 * @param proDef
	 */
	void afterDeleteProDef(ProDef proDef);
	/**
	 * 获取业务元数据
	 */
	Class<SuperVO>[] getBizMetaDataDesc(WfmFlwTypeVO flowTypeVo);
}
