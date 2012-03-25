package nc.uap.wfm.vo;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDateTime;
public class WfmHumActInsVO extends SuperVO {
	private static final long serialVersionUID = 1011116906315202463L;
	/**
	 * �ʵ����ʶ
	 */
	private String pk_humactins;
	/**
	 * ����ʵ����ʶ
	 */
	private String pk_proins;
	/**
	 * ������ʵ����ʶ
	 */
	private String pk_rootproins;
	/**
	 * ���ʵ����ʶ
	 */
	private String pk_parent;
	/**
	 * �˹���ڵ�ID
	 */
	private String humact_id;
	/**
	 * ���˹���ڵ�ID
	 */
	private String phumact_id;
	/**
	 * ֻҪ��һ������ִ�й�������ʵ�����Ǳ�ִ�й�
	 */
	private UFBoolean isnotexe;
	/**
	 * ����ڵ��Ƿ�ͨ����������ɲ����������Ƿ���ɣ�
	 */
	private UFBoolean isnotpas;
	/**
	 * ���������ʶ�Ƿ����˻ز����Ľڵ�
	 */
	private UFBoolean isnotreject;
	/**
	 * �ʵ��״̬
	 */
	private String state;
	private UFDateTime ts;
	private java.lang.Integer dr = 0;
	public String getPk_humactins() {
		return pk_humactins;
	}
	public void setPk_humactins(String pk_humactins) {
		this.pk_humactins = pk_humactins;
	}
	public String getPk_proins() {
		return pk_proins;
	}
	public void setPk_proins(String pk_proins) {
		this.pk_proins = pk_proins;
	}
	public UFDateTime getTs() {
		return ts;
	}
	public void setTs(UFDateTime ts) {
		this.ts = ts;
	}
	public java.lang.Integer getDr() {
		return dr;
	}
	public void setDr(java.lang.Integer dr) {
		this.dr = dr;
	}
	public String getPKFieldName() {
		return "pk_humactins";
	}
	public String getTableName() {
		return "wfm_humactins";
	}
	public String getPk_parent() {
		return pk_parent;
	}
	public void setPk_parent(String pk_parent) {
		this.pk_parent = pk_parent;
	}
	public String getHumact_id() {
		return humact_id;
	}
	public void setHumact_id(String humact_id) {
		this.humact_id = humact_id;
	}
	public UFBoolean getIsnotexe() {
		return isnotexe;
	}
	public void setIsnotexe(UFBoolean isnotexe) {
		this.isnotexe = isnotexe;
	}
	public UFBoolean getIsnotpas() {
		return isnotpas;
	}
	public void setIsnotpas(UFBoolean isnotpas) {
		this.isnotpas = isnotpas;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getPk_rootproins() {
		return pk_rootproins;
	}
	public void setPk_rootproins(String pk_rootproins) {
		this.pk_rootproins = pk_rootproins;
	}
	public String getPhumact_id() {
		return phumact_id;
	}
	public void setPhumact_id(String phumact_id) {
		this.phumact_id = phumact_id;
	}
	public UFBoolean getIsnotreject() {
		return isnotreject;
	}
	public void setIsnotreject(UFBoolean isnotreject) {
		this.isnotreject = isnotreject;
	}
}
