package nc.uap.wfm.builder;
import java.util.ArrayList;
import java.util.List;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.Row;
import nc.uap.wfm.constant.WfmConstants;
import nc.uap.wfm.context.HumActInfoEngCtx;
import nc.uap.wfm.context.WfmFlowInfoCtx;
public class WfmAssginFlowInfoCtxBuilder extends WfmFlowInfoCtxBuilder {
	public WfmAssginFlowInfoCtxBuilder(String flowTypePk, String taskPk) {
		super(flowTypePk, taskPk);
	}
	/**
	 * 构造指派信息
	 * 
	 * @param dataset
	 * @return
	 */
	public List<HumActInfoEngCtx> getAssginCtx(Dataset dataset) {
		if (dataset == null) {
			return null;
		}
		List<HumActInfoEngCtx> list = new ArrayList<HumActInfoEngCtx>();
		Row[] portRows = dataset.getSelectedRows();
		if (portRows == null || portRows.length == 0) {
			return list;
		}
		HumActInfoEngCtx humActInfoEngCtx = null;
		Row tmpRow = null;
		String tmpPortId = null;
		String userPks = null;
		for (int i = 0; i < portRows.length; i++) {
			humActInfoEngCtx = new HumActInfoEngCtx();
			tmpRow = portRows[i];
			tmpPortId = (String) tmpRow.getValue(dataset.nameToIndex(WfmConstants.HumActId));
			humActInfoEngCtx.setPortId(tmpPortId);
			list.add(humActInfoEngCtx);
			userPks = (String) tmpRow.getValue(dataset.nameToIndex(WfmConstants.UserPks));
			if (userPks == null || userPks.length() == 0) {
				continue;
			}
			humActInfoEngCtx.setUserPks(userPks.split(","));
		}
		return list;
	}
	@Override public WfmFlowInfoCtx builder() {
		return super.builder();
	}
	
	
}
