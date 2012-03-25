package nc.uap.wfm.rejectsgy;

import java.util.HashSet;
import java.util.Set;

import nc.uap.wfm.handler.PortAndEdgeHandler;
import nc.uap.wfm.model.HumAct;
import nc.uap.wfm.model.IPort;
import nc.uap.wfm.model.ProDef;
import nc.uap.wfm.model.ProIns;
import nc.uap.wfm.model.StartEvent;
import nc.uap.wfm.model.Task;

public class StartHumAct extends RejectSgyService {
	HumAct[] getRejectRange(Task task) {
		ProIns rootProIns = task.getRootProIns();
		ProDef proDef = rootProIns.getProDef();
		StartEvent startEvent = proDef.getStart();
		Set<HumAct> set = new HashSet<HumAct>();
		if (startEvent.isNotCoverMakePort()) {
			set.add(startEvent);
		} else {
			IPort[] ports = PortAndEdgeHandler.getNextHumActs(startEvent);
			if (ports != null && ports.length != 0) {
				set.add((HumAct) ports[0]);
			}
		}
		return set.toArray(new HumAct[0]);
	}
}
