package nc.uap.wfm.engine;
import nc.uap.wfm.model.SequenceFlow;
import nc.uap.wfm.model.Task;
import nc.uap.wfm.vo.WfmFormInfoCtx;
/**
 * �Զ����߼���չ
 * 
 * @param task
 * @param sf
 * @param formVo
 * @return
 */
public interface ILogicDecision {
	/**
	 * �Զ����߼���չ
	 * 
	 * @param task
	 * @param sf
	 * @param formVo
	 * @return
	 */
	boolean judge(Task task, SequenceFlow sf, WfmFormInfoCtx formVo);
}
