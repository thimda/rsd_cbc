package nc.uap.wfm.model;
public abstract class DefaultEdge implements IEdge {
	private static final long serialVersionUID = -3991681834126788464L;
	private IPort source;
	private IPort target;
	public IPort getSource() {
		return source;
	}
	public void setSource(IPort source) {
		this.source = source;
	}
	public IPort getTarget() {
		return target;
	}
	public void setTarget(IPort target) {
		this.target = target;
	}
}
