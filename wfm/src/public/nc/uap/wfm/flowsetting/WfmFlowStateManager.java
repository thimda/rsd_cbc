package nc.uap.wfm.flowsetting;
import nc.uap.lfw.core.bm.IStateManager;
import nc.uap.lfw.core.bm.dft.AbstractStateManager;
import nc.uap.lfw.core.comp.WebComponent;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.wfm.vo.WfmFlwTypeVO;
public class WfmFlowStateManager extends AbstractStateManager {
	public State getState(WebComponent target, LfwWidget view) {
		Dataset dsFlowType = view.getViewModels().getDataset("ds_flowtype");
		Row selectedRow = dsFlowType.getSelectedRow();
		if (selectedRow == null) {
			return IStateManager.State.DISABLED;
		}
		String parentPk = (String) selectedRow.getValue(dsFlowType.nameToIndex(WfmFlwTypeVO.PK_PARENT));
		String itemId = target.getId();
		if (parentPk == null) {
			if ("item_add".equalsIgnoreCase(itemId)) {
				return State.ENABLED;
			} else if ("item_modify".equalsIgnoreCase(itemId)) {
				return State.ENABLED;
			} else if ("item_delete".equalsIgnoreCase(itemId)) {
				return State.ENABLED;
			} else if ("item_editcode".equalsIgnoreCase(itemId)) {
				return State.ENABLED;
			}
		} else {
			if ("item_add".equalsIgnoreCase(itemId)) {
				return State.DISABLED;
			} else if ("item_modify".equalsIgnoreCase(itemId)) {
				return State.ENABLED;
			} else if ("item_delete".equalsIgnoreCase(itemId)) {
				return State.ENABLED;
			} else if ("item_editcode".equalsIgnoreCase(itemId)) {
				return State.ENABLED;
			}
		}
		return IStateManager.State.DISABLED;
	}
}
