package nc.uap.wfm.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProcessDiagram extends DefaultPortShape {
	private static final long serialVersionUID = 270169992051778120L;
	private String pk_prodef;
	private String id;
	private String parent_id;
	private String root_id;
	private String processRef;

	public Map<String, SequenceFlowConnector> getSequenceFlowConnectors() {
		return sequenceFlowConnectors;
	}

	public void setSequenceFlowConnectors(Map<String, SequenceFlowConnector> sequenceFlowConnectors) {
		this.sequenceFlowConnectors = sequenceFlowConnectors;
	}

	protected Map<String, TaskShape> taskShapes = new HashMap<String, TaskShape>();
	protected Map<String, EventShape> eventShapes = new HashMap<String, EventShape>();
	protected Map<String, SequenceFlowConnector> sequenceFlowConnectors = new HashMap<String, SequenceFlowConnector>();
	protected List<TaskShape> taskShapeList = new ArrayList<TaskShape>();
	protected List<EventShape> eventShapeList = new ArrayList<EventShape>();
	protected List<SequenceFlowConnector> sequenceFlowList = new ArrayList<SequenceFlowConnector>();
    	
	public String getPk_prodef() {
		return pk_prodef;
	}

	public void setPk_prodef(String pk_prodef) {
		this.pk_prodef = pk_prodef;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParent_id() {
		return parent_id;
	}

	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}

	public String getRoot_id() {
		return root_id;
	}

	public void setRoot_id(String root_id) {
		this.root_id = root_id;
	}

	public Map<String, TaskShape> getTaskShapes() {
		return taskShapes;
	}

	public void setTaskShapes(Map<String, TaskShape> taskShapes) {
		this.taskShapes = taskShapes;
	}

	public Map<String, EventShape> getEventShapes() {
		return eventShapes;
	}

	public void setEventShapes(Map<String, EventShape> eventShapes) {
		this.eventShapes = eventShapes;
	}

	public void addSequenceFlowConnectors(SequenceFlowConnector sf) {
		sequenceFlowConnectors.put(sf.getId(), sf);
		sequenceFlowList.add(sf);
	}

	public void addTaskShapes(TaskShape taskShape) {
		taskShapes.put(taskShape.getId(), taskShape);
		taskShapeList.add(taskShape);
	}

	public void addEventShapes(EventShape eventShape) {
		eventShapes.put(eventShape.getId(), eventShape);
		eventShapeList.add(eventShape);		
	}

	public String getProcessRef() {
		return processRef;
	}

	public void setProcessRef(String processRef) {
		this.processRef = processRef;
	}

	public List<TaskShape> getTaskShapeList() {
		return taskShapeList;
	}

	public List<EventShape> getEventShapeList() {
		return eventShapeList;
	}

	public List<SequenceFlowConnector> getSequenceFlowList() {
		return sequenceFlowList;
	}
	
	
	

}
