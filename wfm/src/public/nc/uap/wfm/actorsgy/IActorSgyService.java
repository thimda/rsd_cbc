package nc.uap.wfm.actorsgy;
import nc.uap.wfm.model.HumAct;
import nc.uap.wfm.model.ProIns;
public interface IActorSgyService {
	String[] getActors(ProIns proIns, HumAct humAct);
}
