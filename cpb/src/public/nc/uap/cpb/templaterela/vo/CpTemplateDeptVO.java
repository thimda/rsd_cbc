package nc.uap.cpb.templaterela.vo;

import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDateTime;

public class CpTemplateDeptVO extends SuperVO {

	private static final long serialVersionUID = 1L;
	
	private String pk_templatedept;
	private String pk_template;
	private String pk_dept;
	private Integer templatetype;
	
	private UFBoolean dr;
	private UFDateTime ts;
	
	public String getPk_templatedept() {
		return pk_templatedept;
	}
	public void setPk_templatedept(String pk_templatedept) {
		this.pk_templatedept = pk_templatedept;
	}
	public String getPk_template() {
		return pk_template;
	}
	public void setPk_template(String pk_template) {
		this.pk_template = pk_template;
	}
	public String getPk_dept() {
		return pk_dept;
	}
	public void setPk_dept(String pk_dept) {
		this.pk_dept = pk_dept;
	}
	public Integer getTemplatetype() {
		return templatetype;
	}
	public void setTemplatetype(Integer templatetype) {
		this.templatetype = templatetype;
	}
	public UFBoolean getDr() {
		return dr;
	}
	public void setDr(UFBoolean dr) {
		this.dr = dr;
	}
	public UFDateTime getTs() {
		return ts;
	}
	public void setTs(UFDateTime ts) {
		this.ts = ts;
	}
	@Override
	public String getPKFieldName() {
		return "pk_templatedept";
	}
	@Override
	public String getTableName() {
		return "cp_templatedept";
	}

}
