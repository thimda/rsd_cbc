package nc.uap.wfm.rejectsgy;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import nc.uap.wfm.model.HumAct;
import nc.uap.wfm.model.HumActIns;
import nc.uap.wfm.model.IPort;
import nc.uap.wfm.model.ProDef;
import nc.uap.wfm.model.Task;
public class AllHumAct extends RejectSgyService {
	HumAct[] getRejectRange(Task task) {
		HumActIns humActIns = task.getHumActIns();
		ProDef proDef = humActIns.getProIns().getProDef();
		Set<HumAct> set = new HashSet<HumAct>();
		this.recursPutPort(proDef, set);
		return set.toArray(new HumAct[set.size()]);
	}
	public void recursPutPort(ProDef proDef, Set<HumAct> set) {
		Map<String, IPort> ports = proDef.getPorts();
		Set<String> keys = ports.keySet();
		Iterator<String> iter = keys.iterator();
		while (iter.hasNext()) {
			String key = iter.next();
			IPort port = ports.get(key);
			if (port instanceof HumAct) {
				HumAct humAct = (HumAct) port;
				set.add(humAct);
			}
			if (port instanceof ProDef) {
				ProDef subProDef = (ProDef) port;
				this.recursPutPort(subProDef, set);
			}
		}
	}
}
