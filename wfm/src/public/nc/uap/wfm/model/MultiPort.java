package nc.uap.wfm.model;
import java.util.HashSet;
import org.apache.commons.collections.CollectionUtils;
public abstract class MultiPort implements IMultiPort {
	private static final long serialVersionUID = -6634914075421828262L;
	private String id;
	private String name;
	private String description;
	private String prodef_id;
	private String pk_formdefinition;
	protected HashSet<IEdge> inEdges = new HashSet<IEdge>(4, 0.75f);
	protected HashSet<IEdge> outEdges = new HashSet<IEdge>(4, 0.75f);
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
	public boolean addInEdge(IEdge edge) {
		return inEdges.add(edge);
	}
	public boolean addOutEdge(IEdge edge) {
		return outEdges.add(edge);
	}
	public IEdge[] getInEdges() {
		return (IEdge[]) inEdges.toArray(new IEdge[outEdges.size()]);
	}
	public IEdge[] getOutEdges() {
		return (IEdge[]) outEdges.toArray(new IEdge[outEdges.size()]);
	}
	public IEdge[] getAllEdges() {
		return (IEdge[]) CollectionUtils.union(inEdges, outEdges).toArray();
	}
	public void removeInEdge(IEdge edge) {
		inEdges.remove(edge);
	}
	public void removeOutEdge(IEdge edge) {
		outEdges.remove(edge);
	}
}
