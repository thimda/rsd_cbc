package nc.uap.wfm.model;

import nc.uap.wfm.constant.WfmConstants;

/**
 * 连线实体类
 * 
 * @author tianchw
 * 
 */
public class SequenceFlow extends DefaultEdge {
	private static final long serialVersionUID = -8151343215721056332L;
	private String id;
	private String name;
	private String description;
	private String sourceRef;
	private String targetRef;
	private String condition;
	private String selfDefClass;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

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

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getSelfDefClass() {
		if (this.selfDefClass == null || this.selfDefClass.length() == 0) {
			this.selfDefClass = WfmConstants.LogicDecisionClass;
		}
		return selfDefClass;
	}

	public void setSelfDefClass(String selfDefClass) {
		this.selfDefClass = selfDefClass;
	}
}
