package nc.uap.ctrl.tpl.qry.meta;
/**
 * 
 *
 */
public class QueryRule implements IRule {

	private static final long serialVersionUID = 79122814953707699L;

	private IFilter formatObject; // 默认为IFilter格式

	public QueryRule() {
	}

	public IFilter getFormatObject() {
		return formatObject;
	}

	public void setFormatObject(IFilter sqlFormatObject) {
		this.formatObject = sqlFormatObject;
	}
	public String toString(){
		return "<li>"+getFormatObject().toString()+"</li>";
	}

}
