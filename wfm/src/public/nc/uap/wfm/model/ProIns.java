package nc.uap.wfm.model;
import java.util.Set;
import nc.vo.pub.lang.UFDate;
public class ProIns extends AbstractObservable {
	public static final String NottStart = "NottStart";// 拟稿中
	public static final String End = "End";// 结束
	public static final String Run = "Run";// 运行中
	public static final String Suspended = "Suspended";// 挂起
	public static final String Cancellation = "Cancellation";//作废
	private static final long serialVersionUID = -8297906942120854958L;
	private String pk_proins;
	private String pk_starter;
	private ProDef proDef;
	private ProIns parent;
	private ProIns pproIns;
	private Set<ProIns> subProIns;
	private UFDate startDate;
	private UFDate endDate;
	private String state;
	private Set<HumActIns> humActInses;
	private String pk_startfrmins;
	private String pk_starttask;
	public String getPk_proins() {
		return pk_proins;
	}
	public void setPk_proins(String pk_proins) {
		this.pk_proins = pk_proins;
	}
	public String getPk_starter() {
		return pk_starter;
	}
	public void setPk_starter(String pk_starter) {
		this.pk_starter = pk_starter;
	}
	public ProDef getProDef() {
		return proDef;
	}
	public void setProDef(ProDef proDef) {
		this.proDef = proDef;
	}
	public ProIns getParent() {
		return parent;
	}
	public void setParent(ProIns parent) {
		this.parent = parent;
	}
	public Set<ProIns> getSubProIns() {
		return subProIns;
	}
	public void setSubProIns(Set<ProIns> subProIns) {
		this.subProIns = subProIns;
	}
	public UFDate getStartDate() {
		return startDate;
	}
	public void setStartDate(UFDate startDate) {
		this.startDate = startDate;
	}
	public UFDate getEndDate() {
		return endDate;
	}
	public void setEndDate(UFDate endDate) {
		this.endDate = endDate;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Set<HumActIns> getHumActInses() {
		return humActInses;
	}
	public void setHumActInses(Set<HumActIns> humActInses) {
		this.humActInses = humActInses;
	}
	public String getPk_startfrmins() {
		return pk_startfrmins;
	}
	public void setPk_startfrmins(String pk_startfrmins) {
		this.pk_startfrmins = pk_startfrmins;
	}
	public ProIns getPproIns() {
		return pproIns;
	}
	public void setPproIns(ProIns pproIns) {
		this.pproIns = pproIns;
	}
	public String getPk_starttask() {
		return pk_starttask;
	}
	public void setPk_starttask(String pk_starttask) {
		this.pk_starttask = pk_starttask;
	}
}
