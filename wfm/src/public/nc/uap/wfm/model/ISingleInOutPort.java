package nc.uap.wfm.model;
/**
 * 单进单出的点
 * @author tianchw
 *
 */
public interface ISingleInOutPort extends IPort {
	IEdge getInEdge();
	IEdge getOutEdge();
	void setInEdge(IEdge edge);
	void setOutEdge(IEdge edge);
}
