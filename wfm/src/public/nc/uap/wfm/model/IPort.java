package nc.uap.wfm.model;
/**
 * µã
 * @author tianchw
 *
 */
public interface IPort extends IGraphElement {
	IEdge[] getAllEdges();
	void removeInEdge(IEdge edge);
	void removeOutEdge(IEdge edge);
}