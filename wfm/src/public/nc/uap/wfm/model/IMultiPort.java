package nc.uap.wfm.model;


public interface IMultiPort extends IPort {
	IEdge[] getInEdges();
	IEdge[] getOutEdges();
	boolean addInEdge(IEdge edge);
	boolean addOutEdge(IEdge edge);
}
