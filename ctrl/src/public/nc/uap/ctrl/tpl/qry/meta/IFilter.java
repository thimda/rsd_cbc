package nc.uap.ctrl.tpl.qry.meta;

import nc.uap.ctrl.tpl.qry.operator.IOperator;

public interface IFilter extends Cloneable {

	public IFilter[] getdependedFilters();
	
	public boolean dependablityReplacable(IFilter replacement);
	
	public IFilterMeta getFilterMeta() ;

	public void setFilterMeta(IFilterMeta meta) ;
	
	public IOperator getOperator();
	
	public void setOperator(IOperator operator);

	public IFieldValue getFieldValue();
	
	public void setFieldValue(IFieldValue fieldValue);
	
	public String getSqlString();
	
	public String getBasicSql();
	
	public String getCombinedSql();
	
	public void setFilter(IFilter filter);
	
	public boolean isValidate();
	
	public boolean combinable(IFilter filter);
	
	public void combine(IFilter filter);
	
	public void clearCombinedFilter();
	
	public Object clone();
	
}
