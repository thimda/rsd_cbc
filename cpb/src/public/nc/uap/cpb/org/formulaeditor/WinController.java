package nc.uap.cpb.org.formulaeditor;
import java.io.Serializable;
import java.util.Map;

import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.ctrl.WindowController;
import nc.uap.lfw.core.ctx.AppLifeCycleContext;
import nc.uap.lfw.core.ctx.ViewContext;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.Field;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.event.DatasetEvent;
import nc.uap.lfw.core.event.GridRowEvent;
import nc.uap.lfw.core.event.PageEvent;
import nc.uap.lfw.core.page.LfwWidget;
import nc.ui.pub.formulaparse.FormulaParse;
import nc.vo.pub.formulaset.FormulaParseFather;
import nc.vo.pub.formulaset.IFormulaConstant;
import nc.vo.pub.formulaset.function.PostfixMathCommand;
public class WinController implements WindowController, Serializable {
  private static final long serialVersionUID=7532916478964732880L;
  public void sysWindowClosed(  PageEvent event){
    LfwRuntimeEnvironment.getWebContext().destroyWebSession();
  }
  public void onAfterPageInit(  PageEvent pageEvent){
	  AppLifeCycleContext ctx = AppLifeCycleContext.current();
	  ViewContext viewctx = ctx.getViewContext();
	  LfwWidget widget = viewctx.getView();
	  
	  //LfwWidget parentwidget = LfwRuntimeEnvironment.getWebContext().getCrossPageMeta("lmwin").getWidget("main");
	  
	 initFunctionData(widget);
	  initDsData(widget);
  }
  /** 
   * 加载父窗口中dataset
   * @param editorview
   */
    private void initDsData(  LfwWidget editorview){
        Dataset[] dss = editorview.getViewModels().getDatasets();
  	  Dataset datasetds = editorview.getViewModels().getDataset("datasetds");
  	  
  	  for(int i=0;i<dss.length;i++){
  		  Dataset tmp = dss[i];
  		  Row row = datasetds.getEmptyRow();
  		  row.setValue(datasetds.nameToIndex("id"), tmp.getId());
  		  row.setValue(datasetds.nameToIndex("description"),tmp.getId());
  		  datasetds.addRow(row);
  	  }
    }
    private void initFunctionData(  LfwWidget widget){
      Dataset mathds = widget.getViewModels().getDataset("mathds");
  	  Dataset dateds = widget.getViewModels().getDataset("dateds");
  	  Dataset databaseds = widget.getViewModels().getDataset("databaseds");
  	  Dataset stringds = widget.getViewModels().getDataset("stringds");
  	  Dataset financeds = widget.getViewModels().getDataset("financeds");
//  	  Dataset selfdefineds = widget.getViewModels().getDataset("selfdefineds");
  	  Dataset commonds = widget.getViewModels().getDataset("commonds");
//  	  Dataset controlformulads = widget.getViewModels().getDataset("controlformulads");
//  	  
//  	  FormulaTypeManager manager = new FormulaTypeManager();
//  	  manager.init();
//  	  List<FormulaType> typeList =  manager.getTypeList();
  	  
  	  FormulaParseFather formulaParse =  new FormulaParse();	  
  	  Map<String, PostfixMathCommand> name2FunctionMap = (Map<String, PostfixMathCommand>) formulaParse.getJepParser().getFunctions();
  	 
  	  for (String fundesc : name2FunctionMap.keySet()) {
  			PostfixMathCommand element = name2FunctionMap.get(fundesc); 
  			int type = element.getFunctionType();
  			switch(type){
  				case IFormulaConstant.FUN_MATH:
  					addFunData(mathds,fundesc,element);
  					break;
  				case IFormulaConstant.FUN_DATE:
  					addFunData(dateds,fundesc,element);
  					break;
  				case IFormulaConstant.FUN_DB:
  					addFunData(databaseds,fundesc,element);
  					break;
  				case IFormulaConstant.FUN_STRING:
  					addFunData(stringds,fundesc,element);
  					break;
  				case IFormulaConstant.FUN_GL:
  					addFunData(financeds,fundesc,element);
  					break;
  				case IFormulaConstant.FUN_CUSTOM:
  					addFunData(commonds,fundesc,element);
  					break;
  				}
  	  }

  		
//  	  for (FormulaType formulaType : typeList) {
//  			 String typeid =  formulaType.getTypeId();
//  			 String typename = formulaType.getTypeName();
//  		  }
    }
    private void addFunData(  Dataset ds,  String funname,  PostfixMathCommand element){
      Row row = ds.getEmptyRow();
  	  row.setValue(ds.nameToIndex("id"), funname.toLowerCase());
  	  row.setValue(ds.nameToIndex("description"), element.getFunctionDesc());
  	  ds.addRow(row);
    }
    /** 
   * 加载数据集下字段
   * @param datasetEvent
   */
    public void onDatasetDsAfterSelect(  DatasetEvent datasetEvent){
      AppLifeCycleContext ctx = AppLifeCycleContext.current();
  	  ViewContext viewctx = ctx.getViewContext();
  	  LfwWidget widget = viewctx.getView();
  	  
        Dataset datasetds = datasetEvent.getSource();
        Row row = datasetds.getSelectedRow();
        //取得父view中id=id的dataset
        Dataset ds = widget.getViewModels().getDataset(row.getString(datasetds.nameToIndex("id")));
      
        Dataset propertyds = widget.getViewModels().getDataset("propertyds");
        propertyds.clear();
        Field[] fields = ds.getFieldSet().getFields();
        for(int i=0;i<fields.length;i++){
      	  Row tmprow = propertyds.getEmptyRow();
  		  tmprow.setValue(propertyds.nameToIndex("id"),fields[i].getId());
  		  tmprow.setValue(propertyds.nameToIndex("description"),fields[i].getId());
  		  propertyds.addRow(tmprow);
       }
    }
    public void onDateGridDbClick(  GridRowEvent gridRowEvent){
    }
  }

