package nc.uap.cpb.org.resource;

import nc.uap.cpb.org.util.CpbUtil;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.event.DataLoadEvent;
import nc.uap.lfw.core.event.DatasetEvent;
import nc.uap.lfw.core.event.ctx.LfwPageContext;
import nc.uap.lfw.core.event.deft.DefaultDatasetServerListener;
import nc.uap.lfw.core.serializer.impl.SuperVO2DatasetSerializer;
import nc.vo.org.GroupVO;

public class CpResourceGroupDsLoadListener extends DefaultDatasetServerListener {
	public CpResourceGroupDsLoadListener(LfwPageContext pagemeta, String widgetId) {
		super(pagemeta, widgetId);
	}

	@Override public void onDataLoad(DataLoadEvent se) {
		String userPk = CpbUtil.getCntUserPk();
		GroupVO[] vos = CpbUtil.getGroupAdminByUserPk(userPk);
		new SuperVO2DatasetSerializer().serialize(vos, se.getSource(), Row.STATE_NORMAL);
	}

	@Override public void onAfterRowSelect(DatasetEvent e) {
		super.onAfterRowSelect(e);
	}

}
