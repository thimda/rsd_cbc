package nc.uap.wfm.actorsgy;
import nc.uap.wfm.engine.ISelfDefActors;
import nc.uap.wfm.vo.WfmFormInfoCtx;
/**
 * Ϊ֧������ģʽ���ӵĳ����ࡣ
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
	 * ����ģʽ��ȡ�����в�����
	 * 
	 * @return ����������
	 */
	public abstract String[] getOnlineActors(WfmFormInfoCtx formVo);
	/**
	 * ����ģʽ��ȡ�����в�����
	 * 
	 * @return ����������
	 */
	public abstract String[] getOfflineActors();
}
