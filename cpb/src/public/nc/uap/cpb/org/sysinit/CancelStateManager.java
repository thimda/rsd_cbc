package nc.uap.cpb.org.sysinit;

import nc.uap.lfw.core.bm.IStateManager;
import nc.uap.lfw.core.bm.dft.AbstractStateManager;
import nc.uap.lfw.core.comp.WebComponent;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.page.LfwWidget;

public class CancelStateManager extends AbstractStateManager  {

	@Override
	public State getState(WebComponent target, LfwWidget view) {
		Dataset ds = view.getViewModels().getDataset("cp_sysinit_ds");
		return ds.isEnabled() ? IStateManager.State.VISIBLE : IStateManager.State.HIDDEN;
	}
}
