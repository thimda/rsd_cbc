package nc.uap.cpb.org.user;

import nc.uap.cpb.org.vos.CpUserVO;
import nc.uap.lfw.core.bm.IStateManager;
import nc.uap.lfw.core.bm.dft.AbstractStateManager;
import nc.uap.lfw.core.comp.WebComponent;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.page.LfwWidget;

public class CpUser_EnableState__StateManager extends AbstractStateManager{

	@Override
	public State getState(WebComponent target, LfwWidget widget) {
		String id = target.getId();
		Dataset ds = getCtrlDataset(widget);
		if(ds == null)
			return IStateManager.State.ENABLED;
		Row row = ds.getSelectedRow();
		if(row == null)
			return IStateManager.State.DISABLED;
		Object enablestate = row.getValue(ds.nameToIndex(CpUserVO.ENABLESTATE));
		if("stop".equals(id)&&ICpUserConst.ENABLESTATE_ACTIVE.equals(enablestate))
			return IStateManager.State.ENABLED;
		else if("enable".equals(id)&&!ICpUserConst.ENABLESTATE_ACTIVE.equals(enablestate))
			return IStateManager.State.ENABLED;
		return  IStateManager.State.DISABLED;
	}

}
