package nc.uap.wfm.expression;
import nc.uap.wfm.model.SequenceFlow;
import nc.uap.wfm.model.Task;
import nc.uap.wfm.vo.WfmFormInfoCtx;
public interface ISelfFunction {
	/**
	 * ����ִ�нӿ�
	 * 
	 * @param task
	 * @param sf
	 * @param formVo
	 * @return
	 */
	String exec(Task task, SequenceFlow sf, WfmFormInfoCtx formVo);
}
