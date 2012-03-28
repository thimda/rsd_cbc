package nc.uap.cpb.org.pubview.mode;

import nc.uap.lfw.core.comp.TreeViewComp;
import nc.uap.lfw.core.event.AppRequestProcessor;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.page.PageMeta;
import nc.uap.lfw.mock.MockTreePageModel;

public class ModeRefOrgPageModel extends MockTreePageModel {
	protected void constructWidget(LfwWidget widget) {
		widget.setControllerClazz(ModeRefOrgController.class.getName());
	}
	
	@Override
	protected PageMeta createPageMeta() {
		PageMeta pm =  super.createPageMeta();		
		pm.setProcessorClazz(AppRequestProcessor.class.getName());
		return pm;
	}
	
	protected void constructTree(TreeViewComp tree) {
		if(tree != null){
			tree.setWithRoot(false);
			tree.setRootOpen(true);
			tree.setOpenLevel(2);
			
			tree.setWithCheckBox(false);	
			tree.setCheckBoxModel(0);
		}
	}
}
