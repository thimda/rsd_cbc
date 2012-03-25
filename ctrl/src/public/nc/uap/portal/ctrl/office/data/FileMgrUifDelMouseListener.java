package nc.uap.portal.ctrl.office.data;
import nc.bs.framework.common.NCLocator;
import nc.uap.lfw.core.InteractionUtil;
import nc.uap.lfw.core.comp.WebElement;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.event.MouseEvent;
import nc.uap.lfw.core.event.ctx.LfwPageContext;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.page.LfwWidget;

public class FileMgrUifDelMouseListener<T extends WebElement> extends nc.uap.lfw.core.uif.listener.UifDelMouseListener<T>{
	public FileMgrUifDelMouseListener(LfwPageContext arg0,String arg1){
		super(arg0,arg1);
	}
	@Override
	public void onclick(MouseEvent<T> e) {
		LfwWidget widget = getGlobalContext().getPageMeta().getWidget(getWidgetId());		
		Dataset masterDs = widget.getViewModels().getDataset(getDatasetId());
		Row[] delRows = masterDs.getSelectedRows();
		if( delRows != null && delRows.length > 0){
			InteractionUtil.showConfirmDialog("确认对话框", "确认删除吗?");
			if (InteractionUtil.getConfirmDialogResult().equals(Boolean.FALSE)) return;
			String[] pks = new String[delRows.length];
			for(int i=0;i<delRows.length ;i++){
				pks[i] = (String)delRows[i].getValue(masterDs.nameToIndex("pk_file"));
			}
			IOfficeFileQuery qry =  NCLocator.getInstance().lookup(IOfficeFileQuery.class);
			qry.deleteBypks(pks);
			for(int i=0;i<delRows.length;i++){
				masterDs.removeRow(delRows[i]);
			}
		}
		
	}
	public	String	getWidgetId(){
		return "main";
	}
	public	String	getDatasetId(){
		return "files";
	}
}
