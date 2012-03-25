package nc.uap.cpb.log.operatorlog;
import nc.uap.cpb.log.OperatorLogHelper;
import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.comp.text.TextComp;
import nc.uap.lfw.core.ctrl.IController;
import nc.uap.lfw.core.ctx.AppLifeCycleContext;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.event.DialogEvent;
import nc.uap.lfw.core.exception.LfwBusinessException;
import nc.uap.lfw.core.page.LfwWidget;
/** 
 * @author 
 */
public class ShowLinkContent implements IController {
  private static final long serialVersionUID=1L;
  public void beforeShow(  DialogEvent dialogEvent) throws LfwBusinessException{
	  String pk_funcoperlog = (String)LfwRuntimeEnvironment.getWebContext().getAppSession().getAttribute("pk_funcoperlog"); 
	  Dataset ds = getDatasetById("operatorlogDetail");
	  
	  Row  emptyRow = ds.getEmptyRow();
	  ds.clear();
	  ds.addRow(emptyRow);
	  
	  OperatorLogVO ov = null;
	  try{
		  ov = OperatorLogHelper.getLogByPK(pk_funcoperlog);
	  }catch(Exception e){
		  throw new LfwBusinessException(e);
	  }
	  for(int i = 0;i<ov.getAttributeNames().length;i++)
			for(int j = 0;j<ds.getFieldSet().getFields().length;j++){
				if(ov.getAttributeNames()[i].equals(ds.getFieldSet().getField(j).getId())){
					emptyRow.setValue(j, ov.getAttributeValue(ov.getAttributeNames()[i]));
					continue;
				}
			}
	  ds.setSelectedIndex(ds.getRowIndex(emptyRow));
//	  TextComp tcp = (TextComp)AppLifeCycleContext.current().getViewContext().getView().getViewComponents().getComponent("showDetail");
//	  tcp.setValue(ov.getDetail()==null?"":ov.getDetail());
//	  tcp.getValue();
  }
  private Dataset getDatasetById(  String id){
	  LfwWidget widget = AppLifeCycleContext.current().getViewContext().getView();
	  Dataset masterDs = widget.getViewModels().getDataset(id);
	  return masterDs;
  }
}
