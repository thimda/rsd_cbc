package nc.uap.wfm.engine;
import nc.uap.wfm.model.SequenceFlow;
import nc.uap.wfm.model.Task;
import nc.uap.wfm.vo.WfmFormInfoCtx;
/**
 * 自定义逻辑扩展
 * 
 * @param task
 * @param sf
 * @param formVo
 * @return
 */
public interface ILogicDecision {
	/**
	 * 自定义逻辑扩展
	 * 
	 * @param task
	 * @param sf
	 * @param formVo
	 * @return
	 */
	boolean judge(Task task, SequenceFlow sf, WfmFormInfoCtx formVo);
}
