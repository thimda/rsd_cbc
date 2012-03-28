package nc.uap.cpb.org.extention;
/**
 * 用户组管理扩展实现
 * 2011-6-8 上午11:11:09 
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
