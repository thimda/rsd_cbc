package nc.uap.portal.ctrl.office.action;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.WebSession;
import nc.uap.lfw.core.comp.GridColumn;
import nc.uap.lfw.core.comp.GridComp;
import nc.uap.lfw.core.comp.IGridColumn;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.Field;
import nc.uap.lfw.core.data.FieldSet;
import nc.uap.lfw.core.model.PageModel;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.page.PageMeta;

/**
 * http://localhost/portal/core/uimeta.um?pageId=officefileselect&model=nc.uap.portal.ctrl.office.action.FileSelectModel
 * @author lisw
 *
 */
public class FileSelectModel extends PageModel {
	/**
	 * 初始化页面
	 */
	@Override
	protected void initPageMetaStruct() {
		
		PageMeta pageMeta = getPageMeta();
		
		
		//重构grid列 
		LfwWidget widget=pageMeta.getWidget("main");
		GridComp grid  =  (GridComp)widget.getViewComponents().getComponent("filesgrid");
		List<IGridColumn> columns = grid.getColumnList();
		while(columns.iterator().hasNext())
		{
			IGridColumn column = columns.iterator().next();
			grid.removeColumn(column);
		}
		
		String filetype =  LfwRuntimeEnvironment.getWebContext().getParameter("f");
		if(filetype == null || filetype == "")
			filetype  = "redhead";
		columns = GetGridColumns(filetype);
		for(IGridColumn column :columns)
		{
			grid.addColumn(column);
		}
		
		Dataset ds = widget.getViewModels().getDataset("filesvo");
		FieldSet fs = GetFileSet(filetype);
		ds.setFieldSet(fs);		
		super.initPageMetaStruct();
		
		HttpServletRequest request = LfwRuntimeEnvironment.getWebContext().getRequest();
		Enumeration enu = request.getParameterNames();
		WebSession ses = getWebContext().getWebSession();
		if(enu != null){
			while(enu.hasMoreElements()){
				String en = (String) enu.nextElement();
				String val = request.getParameter(en);
				ses.setAttribute(en, val);
			}
		}
	}
	/**
	 * 根据文档类型获取grid 列
	 * @param filetype
	 * @return
	 */
	private List<IGridColumn> GetGridColumns(String filetype)
	{
		List<IGridColumn> columns = null;
		if(filetype.equalsIgnoreCase("redhead"))
		{
			columns = GetGridColumnsBytemple();
		}
		if(filetype.equalsIgnoreCase("redend"))
		{
			columns = GetGridColumnsBytemple();
		}
		if(filetype.equalsIgnoreCase("templete"))
		{
			columns = GetGridColumnsBytemple();
		}
		if(filetype.equalsIgnoreCase("sign"))
		{
			columns = GetGridColumnsBySign();
		}
		
		if(columns == null)
			columns = new ArrayList();
		return columns;
	}
	/**
	 * 模板grid结构
	 * @return 
	 */
	private List<IGridColumn> GetGridColumnsBytemple()
	{
		List<IGridColumn> columns = new ArrayList<IGridColumn>();
		GridColumn column = new GridColumn();
		column.setId("name");
		column.setField("name");
		column.setText("名称");
		column.setWidth(200);
		column.setVisible(true);
		column.setEditorType("StringText");
		columns.add(column);		
		
		
		column = new GridColumn();
		column.setId("url");
		column.setField("url");
		column.setText("路径");
		column.setWidth(320);
		column.setVisible(false);
		column.setEditorType("StringText");
		columns.add(column);
		
		column = new GridColumn();
		column.setId("pk_file");
		column.setField("pk_file");
		column.setText("pk_file");
		column.setWidth(320);
		column.setVisible(false);
		column.setEditorType("StringText");
		columns.add(column);
		
		return columns;
	}
	/**
	 * 印章grid 结构
	 * @return
	 */
	private List<IGridColumn> GetGridColumnsBySign()
	{
		List<IGridColumn> columns = new ArrayList<IGridColumn>();
		GridColumn column = new GridColumn();
		column.setId("name");
		column.setField("name");
		column.setEditorType("StringText");
		column.setText("名称");
		column.setWidth(120);
		column.setVisible(true);
		columns.add(column);
		
		column = new GridColumn();
		column.setId("url");
		column.setField("url");
		column.setEditorType("StringText");
		column.setText("路径");
		column.setWidth(120);
		column.setVisible(false);
		columns.add(column);
		
		column = new GridColumn();
		column.setId("username");
		column.setField("username");
		column.setEditorType("StringText");
		column.setText("使用者名称");
		column.setWidth(120);
		column.setVisible(false);
		columns.add(column);
		
		column = new GridColumn();
		column.setId("usercode");
		column.setField("usercode");
		column.setEditorType("StringText");
		column.setText("使用者编码");
		column.setWidth(120);
		column.setVisible(true);
		columns.add(column);
		
		column = new GridColumn();
		column.setId("pk_file");
		column.setField("pk_file");
		column.setText("pk_file");
		column.setWidth(320);
		column.setVisible(false);
		column.setEditorType("StringText");
		columns.add(column);
		
		return columns;
	}

	/**
	 * 返回字段集
	 * @return
	 */
	private FieldSet GetFileSet(String filetype)
	{
		FieldSet fieldset = null;
		if(filetype.equalsIgnoreCase("redhead"))
		{
			fieldset = GetFiledSetByTemplete();
		}
		if(filetype.equalsIgnoreCase("redend"))
		{
			fieldset = GetFiledSetByTemplete();
		}
		if(filetype.equalsIgnoreCase("templete"))
		{
			fieldset = GetFiledSetByTemplete();
		}
		if(filetype.equalsIgnoreCase("sign"))
		{
			fieldset = GetFiledSetBySign();
		}
		if(fieldset == null)
			fieldset = new FieldSet();
		return fieldset;
	}
	
	/**
	 * 返回模板类的数据集的字段集
	 */
	private FieldSet GetFiledSetByTemplete()
	{
		FieldSet fs = new FieldSet();
		Field field = new Field();
		field.setField("name");
		field.setId("name");
		field.setDataType("String");
		field.setDefaultValue("");		
		fs.addField(field);
		
		field = new Field();
		field.setField("url");
		field.setId("url");
		field.setDataType("String");
		field.setDefaultValue("");		
		fs.addField(field);
		
		field = new Field();
		field.setField("pk_file");
		field.setId("pk_file");
		field.setDataType("String");
		field.setDefaultValue("");		
		fs.addField(field);
		
		return fs;
	}
	/**
	 * 返回 签名类 字符集
	 * @return
	 */
	private FieldSet GetFiledSetBySign()
	{
		FieldSet fs = new FieldSet();
		Field field = new Field();
		field.setField("name");
		field.setId("name");
		field.setDataType("String");
		field.setDefaultValue("");
		fs.addField(field);
		
		field = new Field();
		field.setField("url");
		field.setId("url");
		field.setDataType("String");
		field.setDefaultValue("");
		fs.addField(field);
		
		
		field = new Field();
		field.setField("username");
		field.setId("username");
		field.setDataType("String");
		field.setDefaultValue("");
		fs.addField(field);
		
		field = new Field();
		field.setField("usercode");
		field.setId("usercode");
		field.setDataType("String");
		field.setDefaultValue("");
		fs.addField(field);
		
		field = new Field();
		field.setField("pk_file");
		field.setId("pk_file");
		field.setDataType("String");
		field.setDefaultValue("");		
		fs.addField(field);
		
		return fs;
	}
}
