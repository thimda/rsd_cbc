/**
 * 
 */
package nc.uap.cpb.defdoc.defdoclist;

import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.ctx.AppLifeCycleContext;
import nc.uap.lfw.core.ctx.WindowContext;
import nc.ui.bd.ref.model.DefdocDefaultRefModel;

/**
 * @author maokun
 *
 */
public class CPDefdocDefaultRefModel extends DefdocDefaultRefModel {
//	public CPDefdocDefaultRefModel(ILfwRefModel refModel){}

	@Override
	protected String getEnvWherePart() {
		// TODO Auto-generated method stub
		// return " code = '111' ";
		String pk_defdoclist = (String) LfwRuntimeEnvironment.getWebContext().getAppSession().getAttribute(
				"pk");
		if(pk_defdoclist==null)pk_defdoclist="";
		return "pk_defdoclist = '" + pk_defdoclist + "'";
	}
}
