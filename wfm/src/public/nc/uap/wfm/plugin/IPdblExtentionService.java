package nc.uap.wfm.plugin;
/**
 * 动态表单的扩展点
 * @author tianchw
 *
 */
public interface IPdblExtentionService {
	public static final String POINTID = "pdblext";
	//表单分类
	public static final String FRMCATMGR = "frmcatmgr";
	//表单定义
	public static final String FRMDEFMGR = "frmdefmgr";
	//表单模板
	public static final String FRMTMPMGR = "frmtmpmgr";
	//表单分类
	public static final String FRMCATMGR_ADD = "add";
	public static final String FRMCATMGR_MODIFY = "modify";
	public static final String FRMCATMGR_DELETE = "delete";
	//表单定义
	public static final String FRMDEFMGR_ADD = "add";
	public static final String FRMDEFMGR_MODIFY = "edit";
	public static final String FRMDEfMGR_DELETE = "del";
	//表单模板
	public static final String FRMTMPMGR_ADD = "add";
	public static final String FRMTMPMGR_MODIFY = "modify";
	public static final String FRMTMPMGR_DELETE = "delete";
	public static final String FRMTMPMGR_ENABLE = "enable";
	/**
	 *和业务关联的逻辑后处理扩展逻辑
	 * @param functionType：业务类型：如集团、组织、角色
	 * @param actionType：动作类型：如增加、删除、修改
	 */
	public void afterAction(String functionType, String actionType, Object object);
	/**
	 * 和业务关联的逻辑前处理逻辑
	 * @param functionType：业务类型：如集团、组织、角色
	 * @param actionType：动作类型：如增加、删除、修改
	 * @param object：业务关联本身的对象
	 */
	public void beforeAction(String functionType, String actionType, Object object);
	/**
	 * 可以接受的业务类型
	 * @return
	 */
	public String acceptFunType();
}
