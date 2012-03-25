package nc.uap.cpb.log.datalog;
import nc.uap.cpb.log.DataLogHelper;
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
	  String pk_businesslog = (String)LfwRuntimeEnvironment.getWebContext().getAppSession().getAttribute("pk_businesslog");
	  Dataset ds = getDatasetById("dataLogDetail");
	  Row  emptyRow = ds.getEmptyRow();
	  ds.clear();
	  ds.addRow(emptyRow);
	  
	  DataLogVO dv = null;
	  try{
		  dv = DataLogHelper.getLogByPK(pk_businesslog);
	  }catch(Exception e){
		  throw new LfwBusinessException(e);
	  }
	  for(int i = 0;i<dv.getAttributeNames().length;i++)
			for(int j = 0;j<ds.getFieldSet().getFields().length;j++){
				if(dv.getAttributeNames()[i].equals(ds.getFieldSet().getField(j).getId())){
					emptyRow.setValue(j, dv.getAttributeValue(dv.getAttributeNames()[i]));
					continue;
				}
			}
	  ds.setSelectedIndex(ds.getRowIndex(emptyRow)); 
//	  TextComp tcp = (TextComp)AppLifeCycleContext.current().getViewContext().getView().getViewComponents().getComponent("showDetail");
//	  tcp.setValue(dv.getDetail());
  }
  private Dataset getDatasetById(  String id){
	  LfwWidget widget = AppLifeCycleContext.current().getViewContext().getView();
	  Dataset masterDs = widget.getViewModels().getDataset(id);
	  return masterDs;
  }
}
