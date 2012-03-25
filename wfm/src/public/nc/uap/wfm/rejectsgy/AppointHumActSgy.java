package nc.uap.wfm.rejectsgy;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import nc.uap.wfm.model.HumAct;
import nc.uap.wfm.model.HumActIns;
import nc.uap.wfm.model.IPort;
import nc.uap.wfm.model.ProDef;
import nc.uap.wfm.model.RejectStrategy;
import nc.uap.wfm.model.Task;

public class AppointHumActSgy extends RejectSgyService {
	HumAct[] getRejectRange(Task task) {
		HumActIns humActIns = task.getHumActIns();
		HumAct humAct = humActIns.getHumAct();
		RejectStrategy sgy = humAct.getRejectStrategy();
		String str[] = sgy.getHumActs();
		ProDef proDef = humActIns.getProIns().getProDef();
		Set<HumAct> set = new HashSet<HumAct>();
		Map<String, IPort> map = proDef.getPorts();
		if (str == null || str.length == 0) {
			return null;
		}
		String key = null;
		IPort port = null;
		for (int i = 0; i < str.length; i++) {
			key = str[i];
			port = map.get(key);
			if (port instanceof HumAct) {
				HumAct tmpHumAct = (HumAct) port;
				set.add(tmpHumAct);
			}
		}
		return set.toArray(new HumAct[set.size()]);
	}
}
