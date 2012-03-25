package nc.uap.wfm.model;
public class SequenceFlowConnector extends DefaultEdgeShape {
	private static final long serialVersionUID = 5596457511348438523L;
	private String sequenceFlowRef;
	public String getSequenceFlowRef() {
		return sequenceFlowRef;
	}
	public void setSequenceFlowRef(String sequenceFlowRef) {
		this.sequenceFlowRef = sequenceFlowRef;
	}
}
