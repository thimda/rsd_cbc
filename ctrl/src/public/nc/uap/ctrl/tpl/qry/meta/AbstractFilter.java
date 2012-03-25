package nc.uap.ctrl.tpl.qry.meta;

import nc.bs.logging.Logger;
import nc.uap.ctrl.tpl.qry.operator.IOperator;

public abstract class AbstractFilter implements IFilter{
	
	public abstract IFilter[] getdependedFilters();
	
	public abstract boolean dependablityReplacable(IFilter replacement);
	
	public abstract IFilterMeta getFilterMeta() ;

	public abstract void setFilterMeta(IFilterMeta meta) ;
	
	public abstract IOperator getOperator();
	
	public abstract void setOperator(IOperator operator);

	public abstract IFieldValue getFieldValue();
	
	public abstract void setFieldValue(IFieldValue fieldValue);
	
	public abstract String getSqlString();
	
	public  String getBasicSql(){
		return null;
	}
	
	public String getCombinedSql(){
		return null;
	}
	
	public abstract void setFilter(IFilter filter);
	
	public abstract boolean isValidate();
	
	public boolean combinable(IFilter filter){
		return false;
	}
	
	public void combine(IFilter filter){}
	
	public  Object clone(){
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			Logger.error(e.getMessage(), e);
		}
		return null;
	}
	
	public void clearCombinedFilter(){
	}
}
