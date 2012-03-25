package nc.uap.ctrl.tpl.qry.meta;

import java.io.Serializable;

/**
 * 此处插入类型说明。
 * 创建日期：(2004-11-5 16:58:48)
 * @author：hxr
 */
public class DefaultConstEnum implements IConstEnum,Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7634115571828705954L;
	private Object value;
	private String name;
public DefaultConstEnum(Object value, String name)
{
	this.value = value;
	this.name = name;
}
/**
 * getShowName 方法注解。
 */
public String getName() {
	return name;
}
/**
 * getValue 方法注解。
 */
public Object getValue() {
	return value;
}
/**
 * 此处插入方法说明。
 * 创建日期：(2004-11-8 14:58:53)
 * @return java.lang.String
 */
public String toString() {
	return getName();
}

@Override
public boolean equals(Object obj) {
	if(obj instanceof IConstEnum)
	{
		if(value==null&&obj==null)
			return true;
		else if(value!=null)
		{
			return value.equals(((IConstEnum)obj).getValue());
		}
		
	}
	return false;
}
}
