package nc.uap.cpb.org.extention;
/**
 * �û��������չʵ��
 * 2011-6-8 ����11:11:09 
 * @author limingf 
 */
public class UsergroupExtentionServiceImpl implements ICpbExtentionService {
	public String acceptFunType() {
		return ICpbExtentionService.USERGROUPMANAGE;
	}
	@Override
	public void afterAction(String functionType, String actionType, Object obj) {
		
	}
	@Override
	public void beforeAction(String functionType, String actionType, Object object) {}
}
