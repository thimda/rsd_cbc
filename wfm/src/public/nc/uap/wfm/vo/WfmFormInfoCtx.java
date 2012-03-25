package nc.uap.wfm.vo;
import java.io.Serializable;
public interface WfmFormInfoCtx extends Serializable {
	/**
	 * ��ȡ�Ƶ�������
	 * @return
	 */
	public String getBillMakeDateField();
	/**
	 * ��ȡ�Ƶ�����
	 * @return
	 */
	public String getBillMakeUserField();
	/**
	 * ��ȡ�Ƶ�����
	 * @return
	 */
	public String getBillMakeNumbField();
	/**
	 * ��ȡ�Ƶ�������
	 * @return
	 */
	public String getBillMakeDeptField();
	/**
	 * ��ȡ�������
	 * @return
	 */
	public String getFrmAuditUserField();
	/**
	 * ��ȡ���������
	 * @return
	 */
	public String getFrmAuditDateField();
	/**
	 * ��ȡ����״̬��
	 * @return
	 */
	public String getFrmStateField();
	/**
	 * ��ȡ����VO����
	 * @return
	 */
	public String getFrmInsPk();
	/**
	 * ��ȡ����
	 * @param attrbute
	 * @return
	 */
	public Object getAttributeValue(String attrbute);
	/**
	 * ��������
	 * @param name
	 * @param value
	 */
	public void setAttributeValue(String name, Object value);
}
