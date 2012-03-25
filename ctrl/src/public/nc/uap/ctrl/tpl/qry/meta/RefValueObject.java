package nc.uap.ctrl.tpl.qry.meta;

import java.io.Serializable;

public class RefValueObject implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = -3734496012767216173L;

	private String pk;

	private String code;

	private String name;

	private String multiSelectionSql;
	//  «∑Ò√˚≥∆∂‡”Ô
	private boolean isMultiLang;

	public RefValueObject() {
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMultiSelectionSql() {
		return multiSelectionSql;
	}

	public void setMultiSelectionSql(String multiSelectionSql) {
		this.multiSelectionSql = multiSelectionSql;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	public boolean isMultiLang() {
		return isMultiLang;
	}

	public void setMultiLang(boolean isMultiLang) {
		this.isMultiLang = isMultiLang;
	}

	@Override
	public String toString() {
		return "Code: "+getCode()+" Name: "+getName()+" PK: "+getPk()+" sql: "+getMultiSelectionSql();
	}
}