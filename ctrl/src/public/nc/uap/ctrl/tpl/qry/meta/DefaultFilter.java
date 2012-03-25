package nc.uap.ctrl.tpl.qry.meta;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import nc.uap.ctrl.tpl.qry.operator.AndOperator;
import nc.uap.ctrl.tpl.qry.operator.ILogicalOperator;
import nc.uap.ctrl.tpl.qry.operator.IOperator;
import nc.vo.ml.LanguageVO;
import nc.vo.ml.MultiLangUtil;

public class DefaultFilter extends AbstractFilter implements Serializable{

	private static final long serialVersionUID = 6872196241639358099L;

	private FilterMeta filterMeta;
	
	private IOperator operator;
	
	private IFieldValue fieldValue;
	
	private List<IFilter> filterList= new ArrayList<IFilter>();
	
	private ILogicalOperator logicOP= AndOperator.getInstance();
	
	public DefaultFilter() {
		super();
	}


	public DefaultFilter(FilterMeta field) {
		super();
		this.filterMeta = field;
	}

	public boolean dependablityReplacable(IFilter replacement) {
		return false;
	}

	public IFilter[] getdependedFilters() {
		return null;
	}

	public IFilterMeta getFilterMeta() {
		return filterMeta;
	}


	public void setFilterMeta(IFilterMeta field) {
		this.filterMeta = (FilterMeta)field;
	}


	public IOperator getOperator() {
		return operator;
	}


	public void setOperator(IOperator operator) {
		this.operator = operator;
	}


	public IFieldValue getFieldValue() {
		return fieldValue;
	}


	public void setFieldValue(IFieldValue fieldValue) {
		this.fieldValue = fieldValue;
	}
	
	/**
	 * ewei添加算法说明:
	 * 构建一个filter返回的sql分为三个步骤
	 * step 1：构建该filter的basic sql
	 * step 2：构建该filter的组合sql，如果该filter没有合并其他filter，直接返回basic sql
	 * step 3：构建InstrumentedSql，生成终极sql
	 */

	public String getSqlString() {
		if(getOperator()==null||getFilterMeta()==null||getCombinedSql()==null)
		{
			return null;
		}else{
			return ((FilterMeta)getFilterMeta()).getInstrumentedSql(getCombinedSql());
		}
	}
	
	public String getCombinedSql(){
		if(getOperator()==null||getFilterMeta()==null||getBasicSql()==null)
			return null;
		if(filterList.size()==0)
			return getBasicSql();
		else{
			StringBuilder sqlSB = new StringBuilder(getBasicSql());
			for (IFilter filter : filterList) {
				sqlSB.append(" "+((DefaultFilter)filter).getLogicOP().getLogicOperatorCode()+" ");
				if(((DefaultFilter)filter).getFilterList().size()>0)
					sqlSB.append("(");
				sqlSB.append(((DefaultFilter)filter).getCombinedSql());
				if(((DefaultFilter)filter).getFilterList().size()>0)
					sqlSB.append(")");
			}
			return sqlSB.toString();
		}
	}
	
	public String getBasicSql(){
		if(getOperator()==null||getFilterMeta()==null)
		{
			return null;
		}else{
			String sql = getOperator().getSQLString(getAdjustedFilterMeta(), getFieldValue());
			FilterMeta meta = (FilterMeta) getFilterMeta();
			// 特殊处理多语名称参照的sql语句生成
			if (meta.isMultiLangName() && !MultiLangUtil.isDefaultLang()) {
				FilterMeta clone = (FilterMeta)meta.clone();
				clone.setLangSeq(Integer.parseInt(MultiLangUtil.getCurrentLangSeqSuffix()));
				return sql + " OR " + getOperator().getSQLString(clone,getFieldValue());
			}
			else {
				return sql;
			}
		}
	}
	
	/**
	 * 返回调整后的FilterMeta
	 * 1，为多语控件进行调整
	 * 2，为多语名称参照进行调整
	 * 
	 * 根据语种去重新设置FilterMeta的fieldCode
	 * 使用了原始FilterMeta的拷贝，只是为了生成SQL使用
	 */
	private FilterMeta getAdjustedFilterMeta() {
		FilterMeta meta = (FilterMeta) getFilterMeta();
		if (getFieldValue() != null) {
			List<IFieldValueElement> values = getFieldValue().getFieldValues();
			if (values != null && values.size() != 0) {
				FilterMeta newMeta = (FilterMeta) meta.clone();
				IFieldValueElement value = (IFieldValueElement) getFieldValue()
						.getFieldValues().get(0);
				// 多语控件
				if (isMultiLangComponent(meta, value)) {
					LanguageVO vo = (LanguageVO) value.getValueObject();
					if (vo.getLangseq() > 1) {
						newMeta.setLangSeq(vo.getLangseq());
					}
					meta = newMeta;
				}
				// 多语名称参照
				else if (isMultiLangNameRef(meta, value)) {
					RefValueObject vo = (RefValueObject) value.getValueObject();
					if (vo.isMultiLang()) {
						meta.setLangSeq(2);
					}
				}
			}
		}
		return meta;
	}

	private boolean isMultiLangComponent(FilterMeta meta,
			IFieldValueElement value) {
		return meta.getDataType() == IQueryConstants.MULTILANG
				&& value.getValueObject() instanceof LanguageVO;
	}
	
	private boolean isMultiLangNameRef(FilterMeta meta, IFieldValueElement value) {
		if(value == null) return false;
		return meta.getDataType() == IQueryConstants.UFREF
		&& value.getValueObject() instanceof RefValueObject
		&& meta.getReturnType() == IQueryConstants.RETURNNAME;
	}

	@Override
	public String toString() {
	
//		return fieldMeta.getFieldName()+" "+(operator==null?"  ":operator.toString())+" "+
//		(fieldValue==null?" ":(fieldValue.getFieldValues()==null?" ":fieldValue.getFieldValues().get(0).getShowString()));
		if(getOperator()==null||getFilterMeta()==null)
		{
			return getFilterMeta().toString();
		}
		else
		{
			return getOperator().getDescription((FilterMeta)getFilterMeta(), getFieldValue());
		}
	}


	@Override
	public Object clone() {
		return super.clone();	
	}


	public void setFilter(IFilter filter) {
		if(filter==null) 
			return;
		filterMeta = (FilterMeta)filter.getFilterMeta();
		operator = filter.getOperator();
		fieldValue = filter.getFieldValue();
		
	}


	public boolean isValidate() {
		return getSqlString()!=null;		
	}
	
	public void combine(IFilter filter){
		filterList.add(filter);
//		List<IFilter> filterlist = ((DefaultFilter)filter).getFilterList();
//		for (IFilter filter2 : filterlist) {
//			filterList.add(filter2);
//		}		
	}
	
	public boolean combinable(IFilter filter){
		FilterMeta fm1 = (FilterMeta)this.getFilterMeta();
		FilterMeta fm2 = (FilterMeta)filter.getFilterMeta();
		// 元数据的meta只需要判断属性是不是同一个父属性即可
		if(fm1 instanceof MDFilterMeta && fm2 instanceof MDFilterMeta) {
			MDFilterMeta mdfm1 = (MDFilterMeta)fm1;
			String path1 = mdfm1.getAttributePath();
			String table1 = mdfm1.getAttribute().getTable().getName();
			MDFilterMeta mdfm2 = (MDFilterMeta)fm2;
			String path2 = mdfm2.getAttributePath();
			String table2 = mdfm2.getAttribute().getTable().getName();
			if(path1.contains(".")&&path2.contains(".")) {
				String preffix1 = path1.substring(0, path1.lastIndexOf("."));
				String preffix2 = path2.substring(0, path2.lastIndexOf("."));
				return preffix1.equals(preffix2)&&table1.equals(table2);
			}
			return false;
		}
		String benchMark = fm1.getInstrumentedsql();
		String relative = fm2.getInstrumentedsql();
		if(benchMark==null||relative==null||benchMark.length()==0||relative.length()==0)
			return false;
		if(relative.equals(benchMark))
			return true;
		return false;
	}
	
	public ILogicalOperator getLogicOP() {
		return logicOP;
	}


	public void setLogicOP(ILogicalOperator logicOP) {
		this.logicOP = logicOP;
	}
	
	public void clearCombinedFilter(){
		filterList.clear();
	}
	
	public List<IFilter> getFilterList() {
		return filterList;
	}
}
