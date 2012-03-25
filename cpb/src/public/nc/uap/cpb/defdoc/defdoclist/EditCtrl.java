package nc.uap.cpb.defdoc.defdoclist;
import nc.uap.lfw.core.event.DialogEvent;
import nc.uap.lfw.core.ctx.AppLifeCycleContext;
import nc.uap.lfw.core.ctx.WindowContext;

import java.util.Map;
import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.comp.FormComp;
import nc.uap.lfw.core.ctrl.IController;
import nc.uap.lfw.core.data.Field;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.data.Dataset;
/** 
 * @author chouhl
 */
public class EditCtrl implements IController {
  private static final long serialVersionUID=1L;
  public void beforeShow(DialogEvent dialogEvent) {
		// FormComp fcp =
		// (FormComp)AppLifeCycleContext.current().getViewContext().getView().getViewComponents().getComponent("editForm");
		String edit = (String)getCurrentWinCtx().getAppAttribute("edit");
		if(edit == null)edit="";
		if (edit.equals("add")) {
			Dataset ds = AppLifeCycleContext.current().getViewContext()
					.getView().getViewModels().getDataset("defdoclist");
			Row emptyRow = ds.getEmptyRow();
			ds.addRow(emptyRow);
			ds.setSelectedIndex(ds.getRowIndex(emptyRow));
			ds.setEnabled(true);
		} else if (edit.equals("edit")) {
			Dataset ds = AppLifeCycleContext.current().getViewContext()
					.getView().getViewModels().getDataset("defdoclist");
			Row oldRow = (Row) getCurrentWinCtx().getAppAttribute("data");
			Row emptyRow = ds.getEmptyRow();
			Field[] fields = ds.getFieldSet().getFields();
			  for(Field field:fields){
				  int index = ds.nameToIndex(field.getField());
				  if(index<0)continue;
				  emptyRow.setValue(index, oldRow.getValue(index));
			  }
			ds.addRow(emptyRow);
			ds.setSelectedIndex(ds.getRowIndex(emptyRow));
			ds.setEnabled(true);
		}
	}
  private WindowContext getCurrentWinCtx(){
	    return AppLifeCycleContext.current().getApplicationContext().getCurrentWindowContext();
	  }
}
