package nc.uap.wfm.handler;

import nc.uap.wfm.constant.WfmConstants;
import nc.uap.wfm.context.PwfmContext;
import nc.uap.wfm.engine.ILogicDecision;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.expression.Expression;
import nc.uap.wfm.model.ExcGat;
import nc.uap.wfm.model.GateWay;
import nc.uap.wfm.model.IEdge;
import nc.uap.wfm.model.SequenceFlow;
import nc.uap.wfm.utils.WfmClassUtil;
import nc.uap.wfm.vo.WfmFormInfoCtx;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.record.formula.functions.T;

public class GateWayLogicHandler extends AbstractHandler implements IHandler<T> {
	@Override public T handler() {
		return null;
	}
	
	public static boolean gateWayLogicHandler(GateWay gateWay) throws WfmServiceException {
		IEdge[] inEdges = PortAndEdgeHandler.getInEdges(gateWay);
		int count = 0;
		if (inEdges != null && inEdges.length > 0) {
			for (int j = 0; j < inEdges.length; j++) {
				SequenceFlow sf = (SequenceFlow) inEdges[j];
				if (sf != null) {
					Expression expression = new Expression(sf.getCondition());
					if (expression.evaluate()) {
						count++;
					}
				}
			}
		}
		String logic = gateWay.getLogic();
		boolean flag = false;
		if (StringUtils.isNotBlank(logic)) {
			if (WfmConstants.LogicAnd.equalsIgnoreCase(logic)) {
				if (inEdges.length == count) {
					flag = true;
				}
			} else if (WfmConstants.LogicOr.equalsIgnoreCase(logic)) {
				if (count >= 1) {
					flag = true;
				}
			} else if (WfmConstants.LogicByCountAnd.equalsIgnoreCase(logic)) {
				int byCount = Integer.parseInt(gateWay.getCount());
				if (count >= byCount) {
					flag = true;
				}
			}
		}
		flag = true;
		return flag;
	}
	public static boolean checkExcGatCondition(ExcGat excGat) throws WfmServiceException {
		int count = 0;
		IEdge[] outEdges = PortAndEdgeHandler.getOutEdges(excGat);
		if (outEdges == null || outEdges.length == 0) {
			return true;
		}
		for (int j = 0; j < outEdges.length; j++) {
			IEdge outEdge = outEdges[j];
			if (outEdge instanceof SequenceFlow) {
				SequenceFlow sf = (SequenceFlow) outEdge;
				ILogicDecision logic = (ILogicDecision) WfmClassUtil.loadClass(sf.getSelfDefClass());
				WfmFormInfoCtx formVo = PwfmContext.getCurrentBpmnSession().getFormVo();
				if (logic.judge(PwfmContext.getCurrentBpmnSession().getTask(), sf, formVo)) {
					count++;
				}
			}
		}
		if (count > 1) {
			throw new WfmServiceException("条件设置有问题");
		}
		return true;
	}
}
