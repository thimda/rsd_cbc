package nc.uap.cpb.org.vos;


public class CpFuncResVO {
	/**
	 * <p>pk_busifunc��Ӧ���ܽڵ��cfunid
	 * <p>ҳǩ��pk_page��ҵ����pk_busiactive
	 */
	private String pk_busifunc;
	
	private String pk_funcres;
	
	private String pk_parent;
	/**
	 * code��Ӧ�˵���ҳǩ�Ͱ�ť��code
	 */
	private String code;
	/**
	 * name��Ӧ�˵���ҳǩ��ҵ����name
	 */
	private String name;
	/**
	 * type��Ӧְ���ܵ�ҵ��������
	 */
	private Integer type;
	
	/**
	 * �Ƿ�����ڵ�
	 */
	private String isVirtual;

	
	public String getPk_busifunc() {
		return pk_busifunc;
	}
	public void setPk_busifunc(String pk_busifunc) {
		this.pk_busifunc = pk_busifunc;
	}
	public String getPk_funcres() {
		return pk_funcres;
	}
	public void setPk_funcres(String pk_funcres) {
		this.pk_funcres = pk_funcres;
	}
	public String getPk_parent() {
		return pk_parent;
	}
	public void setPk_parent(String pk_parent) {
		this.pk_parent = pk_parent;
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
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getIsVirtual() {
		return isVirtual;
	}
	public void setIsVirtual(String isVirtual) {
		this.isVirtual = isVirtual;
	}
	
	

}
