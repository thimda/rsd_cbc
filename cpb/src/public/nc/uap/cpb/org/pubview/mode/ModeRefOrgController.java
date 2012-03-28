package nc.uap.cpb.org.pubview.mode;

import nc.itf.org.IOrgConst;
import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.ctx.AppLifeCycleContext;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.event.DataLoadEvent;
import nc.uap.lfw.core.event.MouseEvent;
import nc.uap.lfw.core.exception.LfwBusinessException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.lfw.mock.MockTreeViewController;
import nc.vo.org.OrgVO;

public class ModeRefOrgController extends MockTreeViewController {
	
	@Override
	public void okButtonClick(MouseEvent e) {
		
	}
	@Override
	public void cancelButtonClick(MouseEvent e) {
		super.cancelButtonClick(e);
	}
	@Override
	public void dataLoad(DataLoadEvent e) {
		Dataset ds = e.getSource();
		
		String curuserpk = LfwRuntimeEnvironment.getLfwSessionBean().getPk_user();
		String curGrouppk = LfwRuntimeEnvironment.getLfwSessionBean().getPk_unit();
		ModeOrgFilter filter = (ModeOrgFilter)AppLifeCycleContext.current().getApplicationContext().getAppAttribute(ModeOrgHelper.ModeFilter_AttID);
		if(filter == null){
			filter = new ModeOrgFilter();
		}
		ds.clear();
		try {
			OrgVO[] orgs = ModeOrgHelper.GetOrg(curuserpk, curGrouppk, filter);
			if(orgs != null){
				for(OrgVO org : orgs){
					Row row = ds.getEmptyRow();
					ds.addRow(row);
					row.setValue(0, org.getPk_org());
					String pk_father = org.getPk_fatherorg();
					if(IOrgConst.GLOBEORG.equals(org.getPk_org())){
						pk_father = "";
					} else if(curGrouppk.equals(org.getPk_org())){
						if(filter.isIsneedGlobal())
							pk_father = IOrgConst.GLOBEORG;
						else
							pk_father = "";
					}
					else if(pk_father == null || pk_father.equals("") || pk_father.equals("~") ){
						pk_father = curGrouppk;
					}
					row.setValue(1, pk_father);
					row.setValue(2, org.getName());
					row.setValue(3, "0");
					ds.addRow(row);
				}
			}
		} catch (LfwBusinessException e1) {
			LfwLogger.error(e1);
		}
		
	}
}
