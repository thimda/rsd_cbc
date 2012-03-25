package nc.uap.wfm.model;
public class DefaultEdgeShape implements IEdgeShape {
	private static final long serialVersionUID = -4146858381281514034L;
	protected String id;
	protected String sourceRef;
	protected String targetRef;
	public String getSourceRef() {
		return sourceRef;
	}
	public void setSourceRef(String sourceRef) {
		this.sourceRef = sourceRef;
	}
	public String getTargetRef() {
		return targetRef;
	}
	public void setTargetRef(String targetRef) {
		this.targetRef = targetRef;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
