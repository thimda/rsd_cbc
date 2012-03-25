package nc.uap.cpb.templaterela.vo;

import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDateTime;

public class CpTemplateOrgVO extends SuperVO {

	private static final long serialVersionUID = 1L;
	
	private String pk_templateorg;
	private String pk_template;
	private String pk_org;
	private Integer templatetype;
	
	private UFBoolean dr;
	private UFDateTime ts;
	
	@Override
	public String getPKFieldName() {
		return "pk_templateorg";
	}
	@Override
	public String getTableName() {
		return "cp_templateorg";
	}
	
	public String getPk_templateorg() {
		return pk_templateorg;
	}
	public void setPk_templateorg(String pk_templateorg) {
		this.pk_templateorg = pk_templateorg;
	}
	public String getPk_template() {
		return pk_template;
	}
	public void setPk_template(String pk_template) {
		this.pk_template = pk_template;
	}
	public String getPk_org() {
		return pk_org;
	}
	public void setPk_org(String pk_org) {
		this.pk_org = pk_org;
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
