package nc.uap.ctrl.tpl.qry.value;
import java.util.ArrayList;
import java.util.List;

import nc.uap.ctrl.tpl.qry.meta.IFieldValue;
import nc.uap.ctrl.tpl.qry.meta.IFieldValueElement;

public class DefaultFieldValue implements IFieldValue {

	private static final long serialVersionUID = 5469391978836518287L;
	private List<IFieldValueElement> elements = new ArrayList<IFieldValueElement>();
	
	public void add(IFieldValueElement element)
	{
		elements.add(element);
	}
	public void add(List<IFieldValueElement> elements)
	{
		this.elements.addAll(elements);
	}	
	public List<IFieldValueElement> getFieldValues() {
		return elements;
	}
	
	//对于普通的操作符,通过此得到其真正有效值! --介于除外
	public IFieldValueElement getFirstFieldValue(){
		return elements.get(0);
	}

	
}
