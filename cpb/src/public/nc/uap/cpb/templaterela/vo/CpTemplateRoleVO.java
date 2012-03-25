package nc.uap.cpb.templaterela.vo;

import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDateTime;

public class CpTemplateRoleVO extends SuperVO {
	
	private static final long serialVersionUID = -7320438445917688373L;
	private String pk_templaterole;
	private String pk_template;
	private String pk_role;
	private Integer templatetype;
	
	private UFBoolean dr;
	private UFDateTime ts;

	@Override
	public String getPKFieldName() {
		return "pk_templaterole";
	}
	@Override
	public String getTableName() {
		return "cp_templaterole";
	}
	public String getPk_templaterole() {
		return pk_templaterole;
	}
	public void setPk_templaterole(String pk_templaterole) {
		this.pk_templaterole = pk_templaterole;
	}
	public String getPk_template() {
		return pk_template;
	}
	public void setPk_template(String pk_template) {
		this.pk_template = pk_template;
	}
	public String getPk_role() {
		return pk_role;
	}
	public void setPk_role(String pk_role) {
		this.pk_role = pk_role;
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
	public Integer getTemplatetype() {
		return templatetype;
	}
	public void setTemplatetype(Integer templatetype) {
		this.templatetype = templatetype;
	}
	
	
	
	



}
