package nc.uap.wfm.actorsgy;

import java.util.ArrayList;
import java.util.List;
import nc.uap.wfm.model.HumAct;
import nc.uap.wfm.model.ProIns;
import nc.uap.wfm.model.Task;
import nc.uap.wfm.vo.WfmFormInfoCtx;

public class ProInsStart implements IVirtualRoleActor {

	@Override
	public String[] getActors(WfmFormInfoCtx formVo, ProIns proIns, HumAct humAct, Task parentTask) {
		List<String> list = new ArrayList<String>();
		list.add(proIns.getPk_starter());
		return list.toArray(new String[0]);

	}

}
