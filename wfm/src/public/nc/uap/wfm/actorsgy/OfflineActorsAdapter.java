package nc.uap.wfm.actorsgy;
import nc.uap.wfm.engine.ISelfDefActors;
import nc.uap.wfm.model.HumAct;
import nc.uap.wfm.model.ProIns;
import nc.uap.wfm.model.Task;
import nc.uap.wfm.utils.WfmClassUtil;
import nc.uap.wfm.vo.WfmFormInfoCtx;
/**
 * 为支持离线模式增加的抽象类。
 * 
 * @author ybo
 * @version 6.0 2011-5-10
 * @since 1.6
 */
public class OfflineActorsAdapter extends AbstractActors {
	/**
	 * 适配的原有类
	 */
	private ISelfDefActors source = null;
	/**
	 * 构造方法
	 * 
	 * @param source
	 *            适配的自定义参与者获取类
	 */
	public OfflineActorsAdapter(ISelfDefActors source) {
		this.source = source;
	}
	/**
	 * 构造方法
	 * 
	 * @param source
	 *            适配的自定义参与者获取类
	 */
	public OfflineActorsAdapter(String className) {
		this.source = (ISelfDefActors) WfmClassUtil.loadClass(className);
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see nc.portal.pwfm.engine.AbstractSelfDefActors#getOnlineActors()
	 */
	@Override public String[] getOnlineActors(WfmFormInfoCtx formVo) {
		return source.getActors(formVo, null, null, null);
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see nc.portal.pwfm.engine.AbstractSelfDefActors#getOfflineActors()
	 */
	@Override public String[] getOfflineActors() {
		if (source instanceof AbstractActors) {
			return ((AbstractActors) source).getOfflineActors();
		}
		return null;
	}
	@Override public String[] getActors(WfmFormInfoCtx formVo, ProIns proIns, HumAct humAct, Task parentTask) {
		return null;
	}
}
