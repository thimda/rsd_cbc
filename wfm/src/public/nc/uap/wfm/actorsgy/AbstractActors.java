package nc.uap.wfm.actorsgy;
import nc.uap.wfm.engine.ISelfDefActors;
import nc.uap.wfm.vo.WfmFormInfoCtx;
/**
 * 为支持离线模式增加的抽象类。
 * 
 * @author ybo
 * @version 6.0 2011-5-10
 * @since 1.6
 */
public abstract class AbstractActors implements ISelfDefActors {
	public String[] getActors(WfmFormInfoCtx formVo) {
		return getOnlineActors(formVo);
	}
	/**
	 * 在线模式下取得所有参与者
	 * 
	 * @return 参与者数组
	 */
	public abstract String[] getOnlineActors(WfmFormInfoCtx formVo);
	/**
	 * 离线模式下取得所有参与者
	 * 
	 * @return 参与者数组
	 */
	public abstract String[] getOfflineActors();
}
