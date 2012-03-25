package nc.uap.wfm.engine;
import nc.uap.wfm.model.HumAct;
import nc.uap.wfm.model.ProIns;
import nc.uap.wfm.model.Task;
import nc.uap.wfm.vo.WfmFormInfoCtx;
/**
 * 自定义参与者接口。
 * @author tchw
 * @version 6.0 2011-5-10
 * @since 1.6
 */
public interface ISelfDefActors {
	/**
	 * 获取自定义参与者
	 * @param formVo
	 * @return
	 */
	public String[] getActors(WfmFormInfoCtx formVo, ProIns proIns, HumAct humAct, Task parentTask);
}
