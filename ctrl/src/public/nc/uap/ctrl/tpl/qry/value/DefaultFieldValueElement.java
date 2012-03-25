package nc.uap.ctrl.tpl.qry.value;

import nc.uap.ctrl.tpl.qry.meta.IConstEnum;
import nc.uap.ctrl.tpl.qry.meta.IFieldValueElement;
import nc.uap.ctrl.tpl.qry.meta.IQueryConstants;
import nc.uap.ctrl.tpl.qry.meta.RefResultVO;
import nc.uap.ctrl.tpl.qry.sysfunc.SystemFunction;

public class DefaultFieldValueElement implements IFieldValueElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9042010226968068658L;

	private String showString;
	
	private String sqlString;
	
	private Object valueObject;
	
	private int returnType = 2;
	
	public DefaultFieldValueElement(Integer i)
	{
		this.showString = String.valueOf(i);
		this.sqlString = String.valueOf(i);
		this.valueObject = i;
	}
	
	public DefaultFieldValueElement(IConstEnum e)
	{
		if(e==null)
			throw new IllegalArgumentException("para cann't be null");
		this.showString = e.getName();
		this.sqlString = e.getValue().toString();
		this.valueObject = e;
	}
	
	public DefaultFieldValueElement(String showString, String sqlString,
			Object valueObject) {
		super();
		this.showString = showString;
		this.sqlString = sqlString;
		this.valueObject = valueObject;
	}

	public String getShowString() {
		// 对系统函数要进行实时运算得出showString
		if (valueObject instanceof SystemFunction) {
			SystemFunction sf = (SystemFunction) valueObject;
			return sf.getName();
		}
		return showString;
	}

	public String getSqlString() {
		// 对系统函数要进行实时运算得出sqlString
		if (valueObject instanceof SystemFunction) {
			SystemFunction sf = (SystemFunction) valueObject;
			RefResultVO value = sf.getFunctionValue();
			if(returnType == IQueryConstants.RETURNCODE){
				return value.getRefCode();
			}else if(returnType == IQueryConstants.RETURNNAME){
				return value.getRefName();
			}else{
				return value.getRefPK();
			}
		}
		return sqlString;
	}

	public Object getValueObject() {
		return valueObject;
	}
	
	public void setReturnType(int returnType) {
		this.returnType = returnType;
	}

}