package nc.uap.wfm.model;
/**
 *±ß
 * @author tianchw
 *
 */
public interface IEdge extends IGraphElement  {
	IPort getSource();
	IPort getTarget();
	void setSource(IPort port);
	void setTarget(IPort port);
}
