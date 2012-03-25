package nc.uap.cpb.templaterela.vo;

import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDateTime;

public class CpTemplateUserVO extends SuperVO {

	private static final long serialVersionUID = 1L;
	
	private String pk_templateuser;
	private String pk_template;
	private String pk_user;
	private Integer templatetype;
	
	private UFBoolean dr;
	private UFDateTime ts;
	
	@Override
	public String getPKFieldName() {
		return "pk_templateuser";
	}
	@Override
	public String getTableName() {
		return "cp_templateuser";
	}
	
	public String getPk_templateuser() {
		return pk_templateuser;
	}
	public void setPk_templateuser(String pk_templateuser) {
		this.pk_templateuser = pk_templateuser;
	}
	public String getPk_template() {
		return pk_template;
	}
	public void setPk_template(String pk_template) {
		this.pk_template = pk_template;
	}
	public String getPk_user() {
		return pk_user;
	}
	public void setPk_user(String pk_user) {
		this.pk_user = pk_user;
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
