package nc.uap.wfm.dftimpl;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.wfm.engine.ILogicDecision;
import nc.uap.wfm.expression.LogicDecision;
import nc.uap.wfm.expression.LogicParser;
import nc.uap.wfm.model.SequenceFlow;
import nc.uap.wfm.model.Task;
import nc.uap.wfm.vo.WfmFormInfoCtx;
public class DefaultLogicDecision implements ILogicDecision {
	@Override public boolean judge(Task task, SequenceFlow sf, WfmFormInfoCtx formVo) {
		try {
			boolean flag = true;
			String expression = sf.getCondition();
			if (expression == null || expression.trim().length() == 0) {
				return flag;
			}
			String replaceExpre = LogicParser.getExpressionValue(task, sf, formVo);
			flag = new LogicDecision().judge(replaceExpre);
			return flag;
		} catch (Exception e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
	}
}
