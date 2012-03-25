package nc.uap.wfm.model;
import java.util.HashSet;
import java.util.Set;
public abstract class SingleInOutPort implements ISingleInOutPort {
	private static final long serialVersionUID = -62495709952538810L;
	private String id;
	private String name;
	private String description;
	private String prodef_id;
	private String pk_formdefinition;
	private IEdge inEdge;
	private IEdge outEdge;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getProdef_id() {
		return prodef_id;
	}
	public void setProdef_id(String prodef_id) {
		this.prodef_id = prodef_id;
	}
	public String getPk_formdefinition() {
		return pk_formdefinition;
	}
	public void setPk_formdefinition(String pk_formdefinition) {
		this.pk_formdefinition = pk_formdefinition;
	}
	public IEdge getInEdge() {
		return inEdge;
	}
	public IEdge getOutEdge() {
		return outEdge;
	}
	public void removeInEdge(IEdge edge) {
		inEdge = null;
	}
	public void removeOutEdge(IEdge edge) {
		outEdge = null;
	}
	public void setInEdge(IEdge edge) {
		inEdge = edge;
	}
	public void setOutEdge(IEdge edge) {
		outEdge = edge;
	}
	public IEdge[] getAllEdges() {
		Set<IEdge> set = new HashSet<IEdge>();
		set.add(inEdge);
		set.add(outEdge);
		return (IEdge[]) set.toArray();
	}
}
