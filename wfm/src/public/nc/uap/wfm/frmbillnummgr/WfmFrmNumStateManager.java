package nc.uap.wfm.frmbillnummgr;
import nc.uap.lfw.core.bm.IStateManager;
import nc.uap.lfw.core.bm.dft.AbstractStateManager;
import nc.uap.lfw.core.comp.WebComponent;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.page.LfwWidget;
public class WfmFrmNumStateManager extends AbstractStateManager {
	public State getState(WebComponent target, LfwWidget view) {
		Dataset dsFlowType = view.getViewModels().getDataset("ds_rule");
		Row selectedRow = dsFlowType.getSelectedRow();
		String itemId = target.getId();
		if (selectedRow == null) {
			if ("mnuitm_add".equalsIgnoreCase(itemId)) {
				return State.ENABLED;
			} else if ("mnuitm_modify".equalsIgnoreCase(itemId)) {
				return State.DISABLED;
			} else if ("mnuitm_delete".equalsIgnoreCase(itemId)) {
				return State.DISABLED;
			} else if ("mnuitm_save".equalsIgnoreCase(itemId)) {
				return State.DISABLED;
			} else if ("mnuitm_cancel".equalsIgnoreCase(itemId)) {
				return State.DISABLED;
			}
		} else {
			if (dsFlowType.isEnabled()) {
				if ("mnuitm_add".equalsIgnoreCase(itemId)) {
					return State.DISABLED;
				} else if ("mnuitm_modify".equalsIgnoreCase(itemId)) {
					return State.DISABLED;
				} else if ("mnuitm_delete".equalsIgnoreCase(itemId)) {
					return State.DISABLED;
				} else if ("mnuitm_save".equalsIgnoreCase(itemId)) {
					return State.ENABLED;
				} else if ("mnuitm_cancel".equalsIgnoreCase(itemId)) {
					return State.ENABLED;
				}
			} else {
				if ("mnuitm_add".equalsIgnoreCase(itemId)) {
					return State.DISABLED;
				} else if ("mnuitm_modify".equalsIgnoreCase(itemId)) {
					return State.ENABLED;
				} else if ("mnuitm_delete".equalsIgnoreCase(itemId)) {
					return State.ENABLED;
				} else if ("mnuitm_save".equalsIgnoreCase(itemId)) {
					return State.DISABLED;
				} else if ("mnuitm_cancel".equalsIgnoreCase(itemId)) {
					return State.DISABLED;
				}
			}
		}
		return IStateManager.State.DISABLED;
	}
}
