package nc.uap.dbl.vo;

import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDateTime;

import org.apache.commons.lang.StringUtils;

public class DblFormItemVO extends SuperVO{
	private static final long serialVersionUID = 1825817529893288053L;
	private String pk_formitem;
	private String pk_formtemplate;
	private String occupydomain;
	private String namezh;
	private String defaultvalue;
	private String labelandvalue;
	private String checkformatter;
	private UFBoolean isnotdisplay;
	private UFBoolean isnoteditable;
	private UFBoolean isnotenable;
	private UFBoolean isnotrich;
	private UFBoolean isnotrequired;
	private UFBoolean isnotmulti;
	private Integer minlen;
	private Integer maxlen;
	private Integer rowsa;
	private Integer cols;
	private String width;
	private String height;
	private Integer sizea;
	private String filesize;
	private String filetype;
	private String tabname;
	private String fieldtype;
	private String componenttype;
	private String containertype;
	private String container_id;
	private String reftype;
	private String envvar;
	private String serverclass;
	private String renderclass;
	private String orderstr;
	private UFDateTime ts;
	private UFBoolean dr;
	public UFDateTime getTs() {
		return ts;
	}
	public void setTs(UFDateTime ts) {
		this.ts = ts;
	}
	public UFBoolean getDr() {
		return dr;
	}
	public void setDr(UFBoolean dr) {
		this.dr = dr;
	}
	public String getNamezh() {
		return StringUtils.replace(StringUtils.replace(namezh, "\r\n", StringUtils.EMPTY), "\n", StringUtils.EMPTY);
	}
	public void setNamezh(String namezh) {
		this.namezh = namezh;
	}
	public String getComponenttype() {
		return componenttype;
	}
	public void setComponenttype(String componenttype) {
		this.componenttype = componenttype;
	}
	public String getOccupydomain() {
		return occupydomain;
	}
	public void setOccupydomain(String occupydomain) {
		this.occupydomain = occupydomain;
	}
	public UFBoolean getIsnotdisplay() {
		return isnotdisplay;
	}
	public void setIsnotdisplay(UFBoolean isnotdisplay) {
		this.isnotdisplay = isnotdisplay;
	}
	public UFBoolean getIsnoteditable() {
		return isnoteditable;
	}
	public void setIsnoteditable(UFBoolean isnoteditable) {
		this.isnoteditable = isnoteditable;
	}
	public UFBoolean getIsnotenable() {
		return isnotenable;
	}
	public void setIsnotenable(UFBoolean isnotenable) {
		this.isnotenable = isnotenable;
	}
	public UFBoolean getIsnotrich() {
		return isnotrich;
	}
	public void setIsnotrich(UFBoolean isnotrich) {
		this.isnotrich = isnotrich;
	}
	public UFBoolean getIsnotrequired() {
		return isnotrequired;
	}
	public void setIsnotrequired(UFBoolean isnotrequired) {
		this.isnotrequired = isnotrequired;
	}
	public String getCheckformatter() {
		return checkformatter;
	}
	public void setCheckformatter(String checkformatter) {
		this.checkformatter = checkformatter;
	}
	public Integer getMinlen() {
		return minlen;
	}
	public void setMinlen(Integer minlen) {
		this.minlen = minlen;
	}
	public Integer getMaxlen() {
		return maxlen;
	}
	public void setMaxlen(Integer maxlen) {
		this.maxlen = maxlen;
	}
	public Integer getRowsa() {
		return rowsa;
	}
	public void setRowsa(Integer rows) {
		this.rowsa = rows;
	}
	public Integer getCols() {
		return cols;
	}
	public void setCols(Integer cols) {
		this.cols = cols;
	}
	public String getDefaultvalue() {
		return defaultvalue;
	}
	public void setDefaultvalue(String defaultvalue) {
		this.defaultvalue = defaultvalue;
	}
	public UFBoolean getIsnotmulti() {
		return isnotmulti;
	}
	public void setIsnotmulti(UFBoolean isnotmulti) {
		this.isnotmulti = isnotmulti;
	}
	public Integer getSizea() {
		return sizea;
	}
	public String getPk_formitem() {
		return pk_formitem;
	}
	public void setPk_formitem(String pk_formitem) {
		this.pk_formitem = pk_formitem;
	}
	public String getPk_formtemplate() {
		return pk_formtemplate;
	}
	public void setPk_formtemplate(String pk_formtemplate) {
		this.pk_formtemplate = pk_formtemplate;
	}
	public void setSizea(Integer sizea) {
		this.sizea = sizea;
	}
	public String getFieldtype() {
		return fieldtype;
	}
	public void setFieldtype(String fieldtype) {
		this.fieldtype = fieldtype;
	}
	public String getLabelandvalue() {
		return labelandvalue;
	}
	public void setLabelandvalue(String labelandvalue) {
		this.labelandvalue = labelandvalue;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getTabname() {
		return tabname;
	}
	public void setTabname(String tabname) {
		this.tabname = tabname;
	}
	public String getEnvvar() {
		return envvar;
	}
	public void setEnvvar(String envvar) {
		this.envvar = envvar;
	}
	public String getContainertype() {
		return containertype;
	}
	public void setContainertype(String containertype) {
		this.containertype = containertype;
	}
	public String getContainer_id() {
		return container_id;
	}
	public void setContainer_id(String container_id) {
		this.container_id = container_id;
	}
	public String getFilesize() {
		return filesize;
	}
	public void setFilesize(String filesize) {
		this.filesize = filesize;
	}
	public String getFiletype() {
		return filetype;
	}
	public void setFiletype(String filetype) {
		this.filetype = filetype;
	}
	public String getReftype() {
		return reftype;
	}
	public void setReftype(String reftype) {
		this.reftype = reftype;
	}
	public String getServerclass() {
		return serverclass;
	}
	public void setServerclass(String serverclass) {
		this.serverclass = serverclass;
	}
	public String getRenderclass() {
		return renderclass;
	}
	public void setRenderclass(String renderclass) {
		this.renderclass = renderclass;
	}
	public String getPKFieldName() {
		return "pk_formitem";
	}
	public String getTableName() {
		return "dbl_formitem";
	}
	public String getOrderstr() {
		return orderstr;
	}
	public void setOrderstr(String orderstr) {
		this.orderstr = orderstr;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((componenttype == null) ? 0 : componenttype.hashCode());
		result = prime * result + ((container_id == null) ? 0 : container_id.hashCode());
		result = prime * result + ((containertype == null) ? 0 : containertype.hashCode());
		result = prime * result + ((occupydomain == null) ? 0 : occupydomain.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DblFormItemVO other = (DblFormItemVO) obj;
		if (componenttype == null) {
			if (other.componenttype != null)
				return false;
		} else if (!componenttype.equals(other.componenttype))
			return false;
		if (container_id == null) {
			if (other.container_id != null)
				return false;
		} else if (!container_id.equals(other.container_id))
			return false;
		if (containertype == null) {
			if (other.containertype != null)
				return false;
		} else if (!containertype.equals(other.containertype))
			return false;
		if (occupydomain == null) {
			if (other.occupydomain != null)
				return false;
		} else if (!occupydomain.equals(other.occupydomain))
			return false;
		return true;
	}

}
