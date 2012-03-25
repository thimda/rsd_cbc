package nc.uap.portal.ctrl.office.data;

import java.util.List;

import nc.bs.framework.common.NCLocator;
import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.event.DataLoadEvent;
import nc.uap.lfw.core.event.ctx.LfwPageContext;
import nc.uap.lfw.core.event.deft.DefaultDatasetServerListener;

public class FileMgrFilesDsListener extends DefaultDatasetServerListener
{

	public FileMgrFilesDsListener(LfwPageContext pagemeta, String widgetId) {
		super(pagemeta, widgetId);
	}
	@Override
	public void onDataLoad(DataLoadEvent se) {
		Dataset ds = se.getSource();
		String filetype =  (String) LfwRuntimeEnvironment.getWebContext().getWebSession().getAttribute("f");
		IOfficeFileQuery qry = NCLocator.getInstance().lookup(IOfficeFileQuery.class);
		List<OfficeFileVO> files = qry.queryByCondition(filetype, "");
		if(files != null){
			for(OfficeFileVO file : files){				
				Row row = ds.getEmptyRow();
				row.setValue(ds.nameToIndex("pk_file"), file.getPk_file());
				row.setValue(ds.nameToIndex("filetype"), file.getFiletype());
				row.setValue(ds.nameToIndex("doctype"), file.getDoctype());
				row.setValue(ds.nameToIndex("filename"), file.getFilename());
				row.setValue(ds.nameToIndex("fileurl"), file.getFileurl());
				row.setValue(ds.nameToIndex("displayname"), file.getDisplayname());
				row.setValue(ds.nameToIndex("edit"), "edit");
				row.setValue(ds.nameToIndex("ts"), file.getTs());
				row.setValue(ds.nameToIndex("modifyby"), file.getModifyby());
				ds.addRow(row);
			}
		}
	}
}
