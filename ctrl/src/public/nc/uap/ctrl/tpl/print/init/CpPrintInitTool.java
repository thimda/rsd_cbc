package nc.uap.ctrl.tpl.print.init;

import java.util.List;

import nc.md.model.IAttribute;
import nc.md.model.IBusinessEntity;
import nc.md.model.type.IType;
import nc.uap.ctrl.tpl.print.base.CpPrintConditionVO;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.data.RowData;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.serializer.impl.SuperVO2DatasetSerializer;
import nc.vo.pub.SuperVO;
import nc.vo.querytemplate.md.MDTemplateUtil;

public class CpPrintInitTool {
	private static final String ID = "id";
	private static final String NAME = "name";
	private static final String TABLENAME ="tablename";
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
		String tableName = (String)getRowValue(ds, row, TABLENAME);
		String id = (String) getRowValue(ds, row, ID);
		if(alreadyIn(targetDs, name)){
			throw new LfwRuntimeException("元数据对象"+name+"已经存在");
		}
		//设置默认数据
		int index = 1;
		
		//得到存有默认数据的vo
		CpPrintConditionVO  cvo = getDefaultItemVO(index);
		cvo.setPk_print_template(pk_template);
		cvo.setResid(attribute.getResID());
		cvo.setVarname(name);
		cvo.setVartype("MD");
		cvo.setTabletype("MASTER");
		
		int datatype = MDTemplateUtil.getQTDataType(attribute.getDataType().getTypeType());
		cvo.setVarexpress(tableName+"."+id);
		cvo.setDatatype(datatype);
		SuperVO2DatasetSerializer s = new SuperVO2DatasetSerializer();
		s.serialize(new SuperVO[]{cvo}, targetDs, false);
	}
	
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
		int index = targetDs.nameToIndex("varname");
		for (int i = 0; i < rows.length; i++) {
			Row row = rows[i];
			String name = (String) row.getValue(index);
			if(name.equals(code))
				return true;
		}
		return false;
	}
	
	/**
	 * 初始化一个带默认取值的表体VO
	 * 
	 * @return 
	 */
	public CpPrintConditionVO getDefaultItemVO(int row) {
		CpPrintConditionVO item = new CpPrintConditionVO();
		item.setDatatype(new Integer(0));

		return item;
	}
}
