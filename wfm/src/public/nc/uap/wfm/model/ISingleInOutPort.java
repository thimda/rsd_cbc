package nc.uap.wfm.model;
/**
 * ���������ĵ�
 * @author tianchw
 *
 */
public interface ISingleInOutPort extends IPort {
	IEdge getInEdge();
	IEdge getOutEdge();
	void setInEdge(IEdge edge);
	void setOutEdge(IEdge edge);
}
