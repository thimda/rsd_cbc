package nc.uap.wfm.vo;
import java.io.Serializable;
public interface WfmFormInfoCtx extends Serializable {
	/**
	 * 获取制单日期域
	 * @return
	 */
	public String getBillMakeDateField();
	/**
	 * 获取制单人域
	 * @return
	 */
	public String getBillMakeUserField();
	/**
	 * 获取制单号域
	 * @return
	 */
	public String getBillMakeNumbField();
	/**
	 * 获取制单部门域
	 * @return
	 */
	public String getBillMakeDeptField();
	/**
	 * 获取审核人域
	 * @return
	 */
	public String getFrmAuditUserField();
	/**
	 * 获取审核日期域
	 * @return
	 */
	public String getFrmAuditDateField();
	/**
	 * 获取单据状态域
	 * @return
	 */
	public String getFrmStateField();
	/**
	 * 获取单据VO主键
	 * @return
	 */
	public String getFrmInsPk();
	/**
	 * 获取属性
	 * @param attrbute
	 * @return
	 */
	public Object getAttributeValue(String attrbute);
	/**
	 * 设置属性
	 * @param name
	 * @param value
	 */
	public void setAttributeValue(String name, Object value);
}
