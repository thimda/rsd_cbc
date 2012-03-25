package nc.uap.cpb.org.sysinit;

import nc.uap.lfw.core.bm.IStateManager;
import nc.uap.lfw.core.bm.dft.AbstractStateManager;
import nc.uap.lfw.core.comp.WebComponent;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.page.LfwWidget;

public class EditStateManager extends AbstractStateManager {

	@Override
	public State getState(WebComponent target, LfwWidget view) {
		Dataset ds = view.getViewModels().getDataset("cp_sysinit_ds");
		if(ds.isEnabled()){
			return IStateManager.State.HIDDEN;
		}
		return IStateManager.State.VISIBLE;
	}

}
