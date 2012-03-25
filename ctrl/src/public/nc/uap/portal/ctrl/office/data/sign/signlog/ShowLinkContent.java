package nc.uap.portal.ctrl.office.data.sign.signlog;
import nc.bs.framework.common.NCLocator;
import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.cmd.CmdInvoker;
import nc.uap.lfw.core.cmd.UifDatasetLoadCmd;
import nc.uap.lfw.core.comp.text.TextComp;
import nc.uap.lfw.core.ctrl.IController;
import nc.uap.lfw.core.ctx.AppLifeCycleContext;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.event.DataLoadEvent;
import nc.uap.lfw.core.event.DialogEvent;
import nc.uap.lfw.core.exception.LfwBusinessException;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.portal.ctrl.office.data.sign.IsignLogQuery;
import nc.uap.portal.ctrl.office.data.sign.SignlogVO;
/** 
 * @author
 */
public class ShowLinkContent implements IController {
  private static final long serialVersionUID=1L;
  public void beforeShow(  DialogEvent dialogEvent)throws LfwBusinessException{
	  String pk_signlog = (String)LfwRuntimeEnvironment.getWebContext().getAppSession().getAttribute("pk_signlog");
	  Dataset ds = getDatasetById("signLogDetail");
	  Row  emptyRow = ds.getEmptyRow();
	  ds.clear();
	  ds.addRow(emptyRow);
	 
	  SignlogVO sv = null;
	  try {
		  sv = SignLogHelper.getSignLogByPK(pk_signlog);
	} catch (LfwBusinessException e) {
		throw new LfwBusinessException(e);
	}
	for(int i = 0;i<sv.getAttributeNames().length;i++)
		for(int j = 0;j<ds.getFieldSet().getFields().length;j++){
			if(sv.getAttributeNames()[i].equals(ds.getFieldSet().getField(j).getId())){
				emptyRow.setValue(j, sv.getAttributeValue(sv.getAttributeNames()[i]));
				continue;
			}
		}
	  ds.setSelectedIndex(ds.getRowIndex(emptyRow)); 
	  
//	  TextComp tcp = (TextComp)AppLifeCycleContext.current().getViewContext().getView().getViewComponents().getComponent("showDetail");
//	  tcp.setValue(sv.getDetail());
  }
  private Dataset getDatasetById(  String id){
	  LfwWidget widget = AppLifeCycleContext.current().getViewContext().getView();
	  Dataset masterDs = widget.getViewModels().getDataset(id);
	  return masterDs;
  }
  public void onDataLoad_signLogDetail(  DataLoadEvent dataLoadEvent){
    Dataset ds = dataLoadEvent.getSource();
		CmdInvoker.invoke(new UifDatasetLoadCmd(ds.getId()));
  }
}
