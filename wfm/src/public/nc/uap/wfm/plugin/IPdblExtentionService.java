package nc.uap.wfm.plugin;
/**
 * ��̬������չ��
 * @author tianchw
 *
 */
public interface IPdblExtentionService {
	public static final String POINTID = "pdblext";
	//������
	public static final String FRMCATMGR = "frmcatmgr";
	//������
	public static final String FRMDEFMGR = "frmdefmgr";
	//��ģ��
	public static final String FRMTMPMGR = "frmtmpmgr";
	//������
	public static final String FRMCATMGR_ADD = "add";
	public static final String FRMCATMGR_MODIFY = "modify";
	public static final String FRMCATMGR_DELETE = "delete";
	//������
	public static final String FRMDEFMGR_ADD = "add";
	public static final String FRMDEFMGR_MODIFY = "edit";
	public static final String FRMDEfMGR_DELETE = "del";
	//��ģ��
	public static final String FRMTMPMGR_ADD = "add";
	public static final String FRMTMPMGR_MODIFY = "modify";
	public static final String FRMTMPMGR_DELETE = "delete";
	public static final String FRMTMPMGR_ENABLE = "enable";
	/**
	 *��ҵ��������߼�������չ�߼�
	 * @param functionType��ҵ�����ͣ��缯�š���֯����ɫ
	 * @param actionType���������ͣ������ӡ�ɾ�����޸�
	 */
	public void afterAction(String functionType, String actionType, Object object);
	/**
	 * ��ҵ��������߼�ǰ�����߼�
	 * @param functionType��ҵ�����ͣ��缯�š���֯����ɫ
	 * @param actionType���������ͣ������ӡ�ɾ�����޸�
	 * @param object��ҵ���������Ķ���
	 */
	public void beforeAction(String functionType, String actionType, Object object);
	/**
	 * ���Խ��ܵ�ҵ������
	 * @return
	 */
	public String acceptFunType();
}
