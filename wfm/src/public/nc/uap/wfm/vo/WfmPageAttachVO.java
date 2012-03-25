package nc.uap.wfm.vo;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDateTime;
/**
 * 表单关联的纸质附件
 * @author zhangxya
 *
 */
public class WfmPageAttachVO extends SuperVO {
	private static final long serialVersionUID = 1L;
	private String pk_attchlist;
	private String pk_billitem;
	private String pk_submitter;
	private String filename;
	private UFDate uploadtime;
	private UFDateTime ts;
	private Integer dr;
	public UFDateTime getTs() {
		return ts;
	}
	public void setTs(UFDateTime ts) {
		this.ts = ts;
	}
	public Integer getDr() {
		return dr;
	}
	public void setDr(Integer dr) {
		this.dr = dr;
	}
	public String getPk_attchlist() {
		return pk_attchlist;
	}
	public void setPk_attchlist(String pk_attchlist) {
		this.pk_attchlist = pk_attchlist;
	}
	public String getPk_submitter() {
		return pk_submitter;
	}
	public void setPk_submitter(String pk_submitter) {
		this.pk_submitter = pk_submitter;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getPk_billitem() {
		return pk_billitem;
	}
	public void setPk_billitem(String pk_billitem) {
		this.pk_billitem = pk_billitem;
	}
	public UFDate getUploadtime() {
		return uploadtime;
	}
	public void setUploadtime(UFDate uploadtime) {
		this.uploadtime = uploadtime;
	}
	@Override
	public String getPKFieldName() {
		return "pk_attchlist";
	}
	public String getTableName() {
		return "wfm_attchlist";
	}
}
