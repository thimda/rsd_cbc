package nc.uap.wfm.handler;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.model.IEdge;
import nc.uap.wfm.model.IPort;
public interface IPortAndEdgeHandler {
	IPort[] getNextPorts(IPort port) throws WfmServiceException;
	IEdge[] getOutEdges(IPort port) throws WfmServiceException;
	IEdge[] getInEdges(IPort port) throws WfmServiceException;
	IPort[] getTargetPorts(IEdge edge) throws WfmServiceException;
	IPort[] getSourcePorts(IEdge edge) throws WfmServiceException;
}
