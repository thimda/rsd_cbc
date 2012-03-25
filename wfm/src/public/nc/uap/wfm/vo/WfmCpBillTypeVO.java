package nc.uap.wfm.vo;

import nc.vo.pub.SuperVO;

/**
 * 协同平台单据类型
 * @author dengjt
 *
 */
public class WfmCpBillTypeVO extends SuperVO {
	private static final long serialVersionUID = -6450615728563147017L;
	private String pk_billtype;
	private String code;
	private String name;
	private String memo;
	private String handleclazz;
	private String ext1;
	private String ext2;
	private String ext3;
	public String getPk_billtype() {
		return pk_billtype;
	}
	public void setPk_billtype(String pk_billtype) {
		this.pk_billtype = pk_billtype;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getHandleclazz() {
		return handleclazz;
	}
	public void setHandleclazz(String handleclazz) {
		this.handleclazz = handleclazz;
	}
	public String getExt1() {
		return ext1;
	}
	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}
	public String getExt2() {
		return ext2;
	}
	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}
	public String getExt3() {
		return ext3;
	}
	public void setExt3(String ext3) {
		this.ext3 = ext3;
	}
}
