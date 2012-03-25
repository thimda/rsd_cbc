package nc.uap.ctrl.tpl.qry.base;

import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFDateTime;
import nc.vo.uap.pf.OrganizeUnitTypes;

public class SystemplateVO extends SuperVO {
	private static final long serialVersionUID = 3474871471571906701L;

	/**
	 * 被分配主体类型-用户
	 */
	public static final int OPERATOR_TYPE_USER = OrganizeUnitTypes.Operator_INT;

	/**
	 * 被分配主体类型-角色
	 */
	public static final int OPERATOR_TYPE_ROLE = OrganizeUnitTypes.Role_INT;

	public String pk_systemplate;

	public Integer dr;

	public String appid;

	public String nodekey;

	public String operator;

	/* 当组织类型为公司时,表示公司主键;当组织类型为主体账簿时,表示主体账簿主键等等 */
	public String pk_org;

	public String templateid;

	public Integer tempstyle;

	public UFDateTime ts;

	public Integer operatortype; // 分配主体类型：1，用户；2，角色

	public String getPk_systemplate() {
		return pk_systemplate;
	}

	public void setPk_systemplate(String pk_systemplate) {
		this.pk_systemplate = pk_systemplate;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getNodekey() {
		return nodekey;
	}

	public void setNodekey(String nodekey) {
		this.nodekey = nodekey;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getPk_org() {
		return pk_org;
	}

	public void setPk_org(String pk_org) {
		this.pk_org = pk_org;
	}

	public String getTemplateid() {
		return templateid;
	}

	public void setTemplateid(String templateid) {
		this.templateid = templateid;
	}

	public Integer getTempstyle() {
		return tempstyle;
	}

	public void setTempstyle(Integer tempstyle) {
		this.tempstyle = tempstyle;
	}

	public Integer getOperatortype() {
		return operatortype;
	}

	public void setOperatortype(Integer operatortype) {
		this.operatortype = operatortype;
	}
}