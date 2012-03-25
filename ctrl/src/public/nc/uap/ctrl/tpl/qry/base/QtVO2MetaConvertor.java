package nc.uap.ctrl.tpl.qry.base;

import java.util.ArrayList;
import java.util.List;

import nc.uap.ctrl.tpl.qry.meta.ConditionVO;
import nc.uap.ctrl.tpl.qry.meta.DefaultConstEnum;
import nc.uap.ctrl.tpl.qry.meta.DefaultFilter;
import nc.uap.ctrl.tpl.qry.meta.FilterMeta;
import nc.uap.ctrl.tpl.qry.meta.IFieldValueElement;
import nc.uap.ctrl.tpl.qry.meta.IFilter;
import nc.uap.ctrl.tpl.qry.meta.IQueryConstants;
import nc.uap.ctrl.tpl.qry.meta.RefResultVO;
import nc.uap.ctrl.tpl.qry.meta.RefValueObject;
import nc.uap.ctrl.tpl.qry.operator.IOperator;
import nc.uap.ctrl.tpl.qry.operator.OperatorFactory;
import nc.uap.ctrl.tpl.qry.value.DefaultFieldValue;
import nc.uap.ctrl.tpl.qry.value.DefaultFieldValueElement;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDouble;


public class QtVO2MetaConvertor {

	public QtVO2MetaConvertor() {
		super();
	}

	public FilterMeta convert(CpQueryConditionVO vo)
	{
		FilterMeta meta = new FilterMeta();
		
		meta.setFieldCode(vo.getFieldcode());
		meta.setFieldName(vo.getFieldname());
		meta.setOperators(OperatorFactory.getInstance().getOperators(vo.getOperacode()));
		meta.setTableCode(vo.getTabcode());
		meta.setTableName(vo.getTabname());
		meta.setIfAutoCheck(vo.getIfautocheck()==null?false:vo.getIfautocheck().booleanValue());
		meta.setIsEditable(vo.getIfimmobility()==null?false:vo.getIfimmobility().booleanValue());
		meta.setDispType(vo.getDisptype());
		meta.setReturnType(vo.getReturntype());
		meta.setValueEditorDescription(vo.getConsultcode());
		meta.setDataType(vo.getDatatype());
		meta.setDefaultValue(vo.getValue());
		meta.setFixCondition(vo.getIfimmobility()==null?false:vo.getIfimmobility().booleanValue());
		meta.setRequired(vo.getIfmust()==null?false:vo.getIfmust().booleanValue());
		meta.setDefault(vo.getIfdefault()==null?false:vo.getIfdefault().booleanValue());
		meta.setDataPower(vo.getIfdatapower() == null ? false : vo.getIfdatapower().booleanValue());
		meta.setUserDef(vo.getUserdefflag() == null ? false : vo.getUserdefflag().booleanValue());
		meta.setCondition(vo.getIscondition() == null ? true : vo.getIscondition().booleanValue());
		meta.setInstrumentedsql(vo.getInstrumentedsql());
		meta.setOrder(vo.getIforder().booleanValue());
		return meta;
		
	}
	
//	public List<FilterMeta> convert(List<QueryConditionVO> vos) {
//		ArrayList<FilterMeta> metas = new ArrayList<FilterMeta>();
//		if(vos!=null) {
//			for (QueryConditionVO vo : vos) {
//				metas.add(convert(vo));
//			}
//		}
//		return metas;
//	}
	
	public IFilter converDefaultMeta(FilterMeta meta)
	{
		String strValue = meta.getDefaultValue();
		if(strValue==null || strValue.trim().length() == 0)
		{
			return null;
		}
		else
		{
			DefaultFilter filter = new DefaultFilter();
			filter.setFilterMeta(meta);
			filter.setOperator(meta.getOperators()[0]);
			DefaultFieldValue fieldValue = createDefaultFieldValue(meta, strValue, null);
			filter.setFieldValue(fieldValue);
			return filter;
		}
	}
	
	public IFilter convertConditionVO(FilterMeta meta, ConditionVO vo)
	{
		DefaultFilter filter = new DefaultFilter();
		filter.setFilterMeta(meta);
		IOperator operator = OperatorFactory.getInstance().getOperator(vo.getOperaCode());
		if(operator!=null)
		{
			filter.setOperator(operator);
		}
		else
		{
			filter.setOperator(meta.getOperators()[0]);
		}
		
		String strValue = vo.getValue();
		RefResultVO refVO = vo.getRefResult();
		
		DefaultFieldValue value = createDefaultFieldValue(meta, strValue, refVO);				

		
		filter.setFieldValue(value);
		
		return filter;
	}

	protected DefaultFieldValue createDefaultFieldValue(FilterMeta meta, String strValue, RefResultVO refVO)
	{

		DefaultFieldValue value = new DefaultFieldValue();

		String[] strValues = strValue.split(",");
		List<IFieldValueElement> elems = createFieldValueElements(meta, strValues, refVO);
		for (IFieldValueElement element : elems)
		{
			value.add(element);
		}

		return value;
	}
	private List<IFieldValueElement> createFieldValueElements(FilterMeta meta, String[] strValues, RefResultVO refVO)
	{
		List<IFieldValueElement> elementList = new ArrayList<IFieldValueElement>();
		for (int i = 0; i < strValues.length; i++)
		{

			final String strValue = strValues[i];
			if (isSysFuntin(strValue))
			{
				elementList.addAll(createFieldValueElementsForSysFunction(meta, strValue));
			}
			else
			{
				elementList.add(createFieldValueElement(meta, strValue, null));
			}

		}
		return elementList;
	}

	private List<IFieldValueElement> createFieldValueElementsForSysFunction(FilterMeta meta, final String strValue)
	{
		List<IFieldValueElement> tempElementList =  new ArrayList<IFieldValueElement>();
//		SystemConsElementCreator syselemcrt = new SystemConsElementCreator(strValue, meta);
//		if (syselemcrt.isSingleValue())
//		{
//			IFieldValueElement elem = syselemcrt.createSingleFieldValueElement();
//			tempElementList.add(elem);
//		}
//		else
//		{
//			List<IFieldValueElement> elems = syselemcrt.createListFieldValueElement();
//			tempElementList.addAll(elems);
//		}
		return tempElementList;
	}

	public IFieldValueElement createFieldValueElement(FilterMeta meta, String strValue, RefResultVO refVO) {
		

		IFieldValueElement elem = null;
		switch (meta.getDataType()) {
			case IQueryConstants.STRING: {
				elem = new DefaultFieldValueElement(strValue,strValue,strValue);
				
				break;
			}
	
			case IQueryConstants.INTEGER: {
				Integer i = Integer.valueOf(strValue);
				elem = new DefaultFieldValueElement(strValue,strValue,i);
				
				break;				
	
			}
			case IQueryConstants.DECIMAL: {
				UFDouble dbl = new UFDouble(strValue);
				elem = new DefaultFieldValueElement(strValue,strValue,dbl);
				
				break;					
			}
			case IQueryConstants.BOOLEAN: {
				UFBoolean b = UFBoolean.valueOf(strValue);
				DefaultConstEnum enu = new DefaultConstEnum(b,b.booleanValue()?"是":"否");
				elem = new DefaultFieldValueElement(enu);
				
				break;				
			}
			case IQueryConstants.USERCOMBO: {
				DefaultConstEnum enu = null;
				if(meta.getValueEditorDescription().startsWith(IQueryConstants.COMBO_INDEX))
				{
					enu = new DefaultConstEnum(Integer.valueOf(strValue),strValue);
				}
				else
				{
					enu = new DefaultConstEnum(strValue,strValue);
				}
				elem = new DefaultFieldValueElement(enu);
//				if(context!=null)
//				{
//					ArrayList<IFieldValueElement> tmp = new ArrayList<IFieldValueElement>();
//					tmp.add(elem);
//					IFieldValueElementEditor editor = context.getFieldValueElementEditorManager().createFieldValueElementEditor(meta);
//					editor.setValues(tmp);
//					List<IFieldValueElement> newValues = editor.getValues();
//					if(newValues!=null&&newValues.size()>0)
//					{
//						elem = newValues.get(0);
//					}
//				}
				break;
			}
			case IQueryConstants.UFREF: {
				if(refVO==null) //认为strValue是PK  
				{
					RefValueObject r = new RefValueObject();
					r.setPk(strValue);
					elem = new DefaultFieldValueElement(strValue,strValue,r);
//					if(context!=null){
//						ArrayList<IFieldValueElement> tmp = new ArrayList<IFieldValueElement>();
//						tmp.add(elem);
//						IFieldValueElementEditor editor = context.getFieldValueElementEditorManager().createFieldValueElementEditor(meta);
//						editor.setValues(tmp);
//						List<IFieldValueElement> newValues = editor.getValues();
//						if(newValues!=null&&newValues.size()>0)
//						{
//							elem = newValues.get(0);
//						}
//					}
				}
				else
				{
					//返回编码 返回名称
					RefValueObject r = new RefValueObject();
					r.setPk(refVO.getRefPK());
					String showString = meta.getDispType()==IQueryConstants.DISPCODE?refVO.getRefCode():refVO.getRefName();
					String sqlString = (meta.getReturnType()==IQueryConstants.RETURNCODE)?refVO.getRefCode():
						((meta.getReturnType()==IQueryConstants.RETURNNAME?
							refVO.getRefName():refVO.getRefPK()));
					r.setCode(refVO.getRefCode());
					r.setName(refVO.getRefName());
					elem = new DefaultFieldValueElement(showString,sqlString,r);
				}
				break;
			}
			case IQueryConstants.DATE: // 日期
			{
				UFDate date = UFDate.getDate(strValue);
				elem = new DefaultFieldValueElement(date.toString(),date.toString(),date);
				break;
			}
			default:
				// TODO: 处理异常情况
				break;
		}
		return elem;
	}
	
	private static boolean isSysFuntin(String strValue)
	{

		return strValue==null?false:strValue.startsWith("#")&&strValue.endsWith("#");
	}
}
