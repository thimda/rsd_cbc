package nc.uap.cpb.org.extention;

import java.util.List;

import nc.uap.portal.plugins.PluginManager;
import nc.uap.portal.plugins.model.PtExtension;

/**
 * Эͬ������չ�㹫��ʵ����
 * 
 */
public class CpbExtentionUtil {

	public static void notifybeforeAction(String functionType, String actionType, Object object) {
	}

	public static void notifyAfterAction(String functionType, String actionType, Object object) {
		 //�õ�Эͬ���ù�����չ��
		 List<PtExtension> exs = PluginManager.newIns().getExtensions(ICpbExtentionService.UAPCPMANAGE);
		 if (exs == null || exs.size() == 0) {
			 return;
		 }
		 for (PtExtension ex : exs) {
			 String exId = ex.getId();
			 if (!exId.contains(functionType)) {
				 continue;
			 }
			 ICpbExtentionService cpbExtService = null;
			 cpbExtService = (ICpbExtentionService) ex.newInstance();
			 String acctype = cpbExtService.acceptFunType();
			 if(functionType != null && acctype != null &&functionType.equals(acctype))
				 cpbExtService.afterAction(functionType, actionType, object);
		}
	}
}
