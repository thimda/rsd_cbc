package nc.uap.ctrl.tpl.qry.init;

import java.util.List;

import nc.md.model.IAttribute;
import nc.md.model.IBusinessEntity;
import nc.md.model.type.IEnumType;
import nc.md.model.type.IType;
import nc.uap.ctrl.tpl.qry.base.CpQueryConditionVO;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.data.RowData;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.serializer.impl.SuperVO2DatasetSerializer;
import nc.ui.querytemplate.operator.IOperator;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.query.IQueryConstants;
import nc.vo.querytemplate.md.MDTemplateUtil;

public class CpQryInitTool {
	private static final String ID = "id";
	private static final String NAME = "name";
	public void addLine(Dataset sourceDs, Dataset targetDs, IBusinessEntity entity, String pk_template){
		Row row = sourceDs.getSelectedRow();
		if(row != null){
			String id = (String) getRowValue(sourceDs, row, ID);
			IAttribute attribute = getAttribute(entity, id);
			if(id != null && (attribute.getDataType().getTypeType() != IType.COLLECTION))
				addOneLine(sourceDs, row, attribute, targetDs, pk_template);	
			else{
				List<Row> childrens = getChildRows(sourceDs, row);
				if(childrens != null){
					for (Row crow : childrens) {
						String cAbsoluteAttributePath = (String) getRowValue(sourceDs, crow, ID);
						IAttribute cAttribute = getAttribute(entity, cAbsoluteAttributePath);
						addOneLine(sourceDs, crow, cAttribute, targetDs, pk_template);
					}
				}
			}
		}
		else{
			Row[] childrens = sourceDs.getCurrentRowData().getRows();
			if(childrens != null){
				for (Row crow : childrens) {
					String cAbsoluteAttributePath = (String) getRowValue(sourceDs, crow, ID);
					IAttribute cAttribute = getAttribute(entity, cAbsoluteAttributePath);
					addOneLine(sourceDs, crow, cAttribute, targetDs, pk_template);
				}
			}
		}
	}
	private List<Row> getChildRows(Dataset ds, Row row) {
		return null;
	}
	private IAttribute getAttribute(IBusinessEntity entity, String id) {
		return entity.getAttributeByPath(id);
	}
	private Object getRowValue(Dataset ds, Row row, String key) {
		return row.getValue(ds.nameToIndex(key));
	}
	private void addOneLine(Dataset ds, Row row, IAttribute attribute, Dataset targetDs, String pk_template) {
		String name = (String) getRowValue(ds, row, NAME);//absoluteAttributePath();
		if(alreadyIn(targetDs, name)){
			throw new LfwRuntimeException("元数据对象"+name+"已经存在");
		}
		//设置默认数据
		int index = findMaxIndex() + 1;
		
		//得到存有默认数据的vo
		CpQueryConditionVO  cvo = getDefaultItemVO(index);
		cvo.setPk_query_template(pk_template);
		cvo.setFieldcode((String) getRowValue(ds, row, ID));
		cvo.setFieldname(name);
		cvo.setResid(attribute.getResID());
		cvo.setIscondition(UFBoolean.valueOf(!(attribute.isCalculation() || attribute.isNotSerialize())));
		int datatype = MDTemplateUtil.getQTDataType(attribute.getDataType().getTypeType());
		// 处理编码名称类非一级节点的相关属性设置
		// 后来老大们又决定不对名称属性进行参照展现了
//		if (!is1LevelNode(absoluteAttributePath)) {
//				IAttribute attr = attribute;
//				if (isCodeAttr(attr)) {
//					datatype = IQueryConstants.UFREF;
//					cvo.setIfAutoCheck(UFBoolean.FALSE);
//					// 编码、名称的参照应该和其父节点一致
//					// 如公司编码(pk_corp.code)的参照和公司(pk_corp)使用的参照是一样的
//					String refModelName = node.getParentNode().getAttribute().getRefModelName();
//					((Attribute)attr).setRefModelName(refModelName);
//					cvo.setReturnType(IQueryConstants.RETURNCODE);
//					cvo.setDispType(IQueryConstants.DISPCODE);
//				}
//		}
		cvo.setDatatype(datatype);
		cvo.setOperacode(MDTemplateUtil.getQTOperatorsByType(datatype,cvo.getReturntype()));
		cvo.setOperaname(getOperators(cvo.getOperacode()));
		if (datatype == IQueryConstants.DATE
					|| datatype == IQueryConstants.TIME
					|| datatype == IQueryConstants.LITERALDATE) {// 日期型默认使用系统函数参照
				cvo.setIfsysfuncrefused(UFBoolean.TRUE);
			}
		if(datatype==IQueryConstants.UFREF)
			cvo.setConsultcode(attribute.getRefModelName()==null?"-99":attribute.getRefModelName());
		else if(datatype==IQueryConstants.USERCOMBO)
			cvo.setConsultcode(getCombodesc(attribute));
		//对于数值型的查询条件去元数据上取精度
		else if(datatype == IQueryConstants.DECIMAL){
			cvo.setConsultcode(String.valueOf(attribute.getPrecise()));
		}
		else
			cvo.setConsultcode("-99");
		
		SuperVO2DatasetSerializer s = new SuperVO2DatasetSerializer();
		//s.serialize(new SuperVO[]{cvo}, targetDs, Row.STATE_NORMAL);
		
		Row newrow = s.vo2DataSet(cvo, targetDs, targetDs.getEmptyRow());
		targetDs.addRow(newrow);
								
//		int last_row = ds.getRowCount() - 1;
//		getBillCardPanel().getBillModel().setBodyRowVO(cvo,last_row);
	}
	
    /**
     * 返回操作符数组
     */
    private String getOperators(String operaCode) {
		String token = IOperator.TOKEN;
		if (operaCode == null || operaCode.equals("")){
			return null;
		} 
		else {
			StringBuffer buf = new StringBuffer();
			if (!operaCode.endsWith(token)) {
				operaCode = operaCode + token;
			}
			String[] codes = operaCode.split(token);
			for (int i = 0; i < codes.length; i++) {
				buf.append(getOperaName(codes[i]));
				if(i != codes.length - 1)
					buf.append(IOperator.TOKEN);
			}
			return buf.toString();
		}
	}
    
    private String getOperaName(String operaCode){
    	if(operaCode.equals("="))
    		return "等于";
    	else if(operaCode.equals(">"))
    		return "大于";
    	else if(operaCode.equals("<"))
    		return "小于";
    	else if(operaCode.equals("like"))
    		return "包含";
    	else if(operaCode.equals("between"))
    		return "介于";
    	else if(operaCode.equals(">="))
    		return "大于等于";
    	else if(operaCode.equals("<="))
    		return "小于等于";
    	else if(operaCode.equals("left like"))
    		return "左包含";
    	else if(operaCode.equals("right like"))
    		return "右包含";
    	return "";
    }
	
//	/**
//     * 判断属性是否是编码属性
//     */
//    private boolean isCodeAttr(IAttribute attr){
//    	return MDTemplateUtil.isCodeAttribute(attr);
//    }
//    
//	/**
//	 * 判断MDNode是否是1级结点
//	 */
//    private boolean is1LevelNode(String attrpath) {
//		return !attrpath.contains(".");
//	}
	/**
	 * 检验元数据编码是否已存在与子表中
	 * @param targetDs 
	 * @param code
	 * @return
	 */
	private boolean alreadyIn(Dataset targetDs, String code){
		RowData rd = targetDs.getCurrentRowData();
		if(rd == null)
			return false;
		Row[] rows = rd.getRows();
		int index = targetDs.nameToIndex("fieldcode");
		for (int i = 0; i < rows.length; i++) {
			Row row = rows[i];
			String name = (String) row.getValue(index);
			if(name.equals(code))
				return true;
		}
		return false;
	}
	
	/**
	 * 初始化一个带默认取值的表体VO.
	 * 创建日期:(2002-10-15 9:15:07)
	 * @return nc.vo.train.QueryTempletItemVO
	 */
	public CpQueryConditionVO getDefaultItemVO(int row) {
		CpQueryConditionVO item = new CpQueryConditionVO();

		item.setDispsequence(new Integer(row));
		//修改为默认不显示2011.05.11
		item.setIfdefault(UFBoolean.FALSE);
		item.setIfused(UFBoolean.TRUE);
		item.setDatatype(new Integer(0));
		item.setIfimmobility(UFBoolean.FALSE);
		item.setConsultcode("-99");
		item.setIfsum(UFBoolean.FALSE);
		item.setIfgroup(UFBoolean.FALSE);
		item.setIforder(UFBoolean.FALSE);
		item.setOrdersequence(new Integer(0));
		item.setIfautocheck(UFBoolean.TRUE);
		item.setIfmust(UFBoolean.FALSE);
		item.setDisptype(new Integer(1));
		item.setIscondition(UFBoolean.TRUE);
		item.setReturntype(new Integer(2));


		//item.setIfDefault(UFBoolean.TRUE);
		//item.setIfDefault(UFBoolean.TRUE);

		return item;
	}

    /**
     * 返回当前查询条件中显示顺序的最大值
     */
    private int findMaxIndex() {
//		QueryConditionVO[] vos = (QueryConditionVO[]) getBillCardPanel()
//				.getBillModel().getBodyValueVOs(QueryConditionVO.class.getName());
//		if (vos == null || vos.length == 0)
//			return -1;
//		int max = 0;
//		for (QueryConditionVO vo : vos) {
//			if (vo.getDispSequence() != null && vo.getDispSequence() > max) {
//				max = vo.getDispSequence();
//			}
//		}
//		return Math.max(max,vos.length - 1);
    	return 0;
	}
    
	private String getCombodesc(IAttribute attr) {
		String combo_desc = "-99";
		IEnumType type = (IEnumType) attr.getDataType();
		if (type.getReturnType().getTypeType() == IType.TYPE_Integer) {
			combo_desc = IQueryConstants.COMBO_INTEGER_META + ","
					+ type.getID();// "IM";
		} else {
			combo_desc = IQueryConstants.COMBO_STRING_META + "," + type.getID(); // "SM";
		}
		return combo_desc;
	}
}
