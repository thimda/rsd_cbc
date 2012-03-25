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
	
	//������ͨ�Ĳ�����,ͨ���˵õ���������Чֵ! --���ڳ���
	public IFieldValueElement getFirstFieldValue(){
		return elements.get(0);
	}

	
}
