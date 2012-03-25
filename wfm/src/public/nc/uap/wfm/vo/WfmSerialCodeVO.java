package nc.uap.wfm.vo;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDateTime;
public class WfmSerialCodeVO extends SuperVO {
	private static final long serialVersionUID = -1311246894313199932L;
	/**
	 * 主键
	 */
	private String pk_serialcode;
	/**
	 * 当前值
	 */
	private String currentvalue;
	/**
	 * 是否占用
	 */
	private UFBoolean isoccupy;
	/**
	 * 编码元素
	 */
	private String pk_frmnumelem;
	private UFDateTime ts;
	private java.lang.Integer dr;
	public String getPk_serialcode() {
		return pk_serialcode;
	}
	public void setPk_serialcode(String pk_serialcode) {
		this.pk_serialcode = pk_serialcode;
	}
	public UFBoolean getIsoccupy() {
		return isoccupy;
	}
	public void setIsoccupy(UFBoolean isoccupy) {
		this.isoccupy = isoccupy;
	}
	public String getPk_frmnumelem() {
		return pk_frmnumelem;
	}
	public void setPk_frmnumelem(String pk_frmnumelem) {
		this.pk_frmnumelem = pk_frmnumelem;
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
		return "pk_serialcode";
	}
	public String getTableName() {
		return "wfm_serialcode";
	}
	public String getCurrentvalue() {
		return currentvalue;
	}
	public void setCurrentvalue(String currentvalue) {
		this.currentvalue = currentvalue;
	}
}
