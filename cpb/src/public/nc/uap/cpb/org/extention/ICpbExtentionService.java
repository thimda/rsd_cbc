package nc.uap.cpb.org.extention;


/**
 * ��֯�������չ��
 *
 */
public interface ICpbExtentionService {

	//��֯������չ��
	public static final String UAPCPMANAGE = "uapcpmanage";
	// ��֯������
	//����
	public static final String GROUPMANAGE = "groupmanage";
	
	
	//��ɫ
	public static final String ROLEMANAGE = "rolemanage";
	
	//��ɫ��
	public static final String ROLEGROUPMANAGE = "rolegroupmanage";
	
	//�û���
	public static final String USERGROUPMANAGE = "usergroupmanage";
	
	//�û�
	public static final String USERMANAGE = "usermanage";
	
	//ְ��
	public static final String RESPMANAGE = "respmanage";
	
	//actiontype������
	public static final String ADD = "add";
	public static final String EDIT = "edit";
	public static final String DELETE = "delete";
	
	//�û�����������
	/**�û�������ɫ*/
	public static final String USER_RELATE_ROLE = "user_relate_role";
	/**�û�ɾ��������ɫ*/
	public static final String USER_DELETE_ROLE = "user_delete_role";
	
	//��ɫ����������
	/**��ɫ�����û�*/
	public static final String ROLE_RELATE_USER = "role_relate_user";
	/**��ɫɾ�������û�*/
	public static final String ROLE_DELETE_USER = "role_delete_user";

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
