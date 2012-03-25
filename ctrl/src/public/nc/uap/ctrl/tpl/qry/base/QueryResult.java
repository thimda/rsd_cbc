package nc.uap.ctrl.tpl.qry.base;

import java.io.Serializable;

import nc.uap.ctrl.tpl.qry.meta.IRule;

@SuppressWarnings("serial")
public class QueryResult implements Serializable {
	private IRule resultRule;

	/**
	 * 编辑类型.或为常规,或为高级.定义见 <code>ICriteria</code>
	 */
	private int editorType;

	public int getEditorType() {
		return editorType;
	}

	public void setEditorType(int editorType) {
		this.editorType = editorType;
	}

	public IRule getResultRule() {
		return resultRule;
	}

	public void setResultRule(IRule resultRule) {
		this.resultRule = resultRule;
	}

}
