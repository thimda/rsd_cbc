package nc.uap.wfm.engine;
import java.util.Map;
import nc.uap.wfm.model.ProDef;
/**
 * ���̶�����չ�ӿ�
 * 
 * @author tianchw
 * 
 */
public interface IProDefExtHandler {
	/**
	 * ���̶�Ӧ����ˮ�ţ��������̺ţ����ɹ��������� ��Ӧ�ӿ��ࣨnc.uap.wfm.engine.IFrmNumBillGen��
	 * 
	 * @return
	 */
	String getFrmNumBillServerClass();
	/**
	 * ����Ǩ���������ã��������Ե��������ԣ�
	 * 
	 * @param proDef
	 * @param frmDefPk
	 * @return
	 */
	public Map<String, String> getExtAttr(ProDef proDef, String frmDefPk);
	/**
	 * �����ͷչ�ֵ���չ��������
	 * 
	 * @param proDef
	 * @param frmDefPk
	 * @return
	 */
	public Map<String, String> getExtAttrName(ProDef proDef, String frmDefPk);
	/**
	 * �����ѯ��չ����
	 * 
	 * @param proDef
	 * @param frmDefPk
	 * @return
	 */
	public Map<String, String> getQryTaskAttr(ProDef proDef, String frmDefPk);
	/**
	 * �����������չPageModel
	 * 
	 * @param frmDefPk
	 * @return
	 */
	public String getMyPrtptPageModel(String frmDefPk);
}
