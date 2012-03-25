package nc.uap.wfm.handler;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.model.ProDef;
import nc.uap.wfm.model.ProIns;
public class ProDefHandler extends AbstractHandler implements IHandler<ProIns> {
	private ProDef proDef = null;
	private ProIns proIns = null;
	public ProDefHandler(ProDef proDef, ProIns proIns) {
		super();
		this.proDef = proDef;
		this.proIns = proIns;
	}
	public ProIns handler() {
		ProIns subProIns = null;
		try {
			subProIns = proInsExe.createProIns(proDef);
		} catch (WfmServiceException e) {
			throw new LfwRuntimeException(e);
		}
		subProIns.setParent(proIns);
		subProIns.asyn();
		return subProIns;
	}
}
