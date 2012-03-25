package nc.uap.wfm.execution;
import nc.uap.wfm.context.PwfmContext;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.model.ProDef;
import nc.uap.wfm.model.ProIns;
import nc.vo.pub.lang.UFDate;
public class ProInsExecution {
	private static ProInsExecution instance = null;
	private ProInsExecution() {}
	synchronized public static ProInsExecution getInstance() {
		if (instance != null)
			return instance;
		else {
			instance = new ProInsExecution();
			return instance;
		}
	}
	/**
	 * 创建流程实例
	 * @param proDef
	 * @return
	 * @throws WfmServiceException
	 */
	public ProIns createProIns(ProDef proDef) throws WfmServiceException {
		ProIns proIns = new ProIns();
		proIns.setStartDate(new UFDate());
		proIns.setProDef(proDef);
		proIns.setState(ProIns.Run);
		proIns.setPk_starter(PwfmContext.getCurrentBpmnSession().getCurrentUserPk());
		return proIns;
	}
	/**
	 * 启动流程
	 * @param proIns
	 * @return
	 * @throws WfmServiceException
	 */
	public ProIns startProIns(ProIns proIns) throws WfmServiceException {
		proIns.setState(ProIns.Run);
		return proIns;
	}
	/**
	 * 结束流程
	 * @param proIns
	 * @return
	 * @throws WfmServiceException
	 */
	public ProIns endProIns(ProIns proIns) throws WfmServiceException {
		proIns.setState(ProIns.End);
		proIns.setEndDate(new UFDate());
		return proIns;
	}
	/**
	 * 回恢流程
	 * @param proIns
	 * @return
	 * @throws WfmServiceException
	 */
	public ProIns resumeProIns(ProIns proIns) throws WfmServiceException {
		proIns.setState(ProIns.Run);
		return proIns;
	}
	/**
	 * 挂起流程
	 * @param proIns
	 * @return
	 * @throws WfmServiceException
	 */
	public ProIns suspendedProIns(ProIns proIns) throws WfmServiceException {
		proIns.setState(ProIns.Suspended);
		return proIns;
	}
}
