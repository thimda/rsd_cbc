package nc.uap.cpb.org.vos;

import nc.vo.pub.SuperVO;

/**
 * ��̬���²˵�
 * 
 * <pre>
 * ��������˵���Ŀ &amp; ���ܽڵ��ǰ̨չʾ
 * </pre>
 */
public class DynamicMenuVO extends SuperVO {

	public static final String ADD_ACTION = "0";	
	public static final String UPDATE_ACTION = "1";	
	public static final String DEL_ACTION = "2";
	/**
	 * 
	 */
	private static final long serialVersionUID = -2654458751045681521L;
	//����ģʽ
	private String action;
	//�˵���
	private CpMenuItemVO menuitemvo;
	
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public CpMenuItemVO getMenuitemvo() {
		return menuitemvo;
	}
	public void setMenuitemvo(CpMenuItemVO menuitemvo) {
		this.menuitemvo = menuitemvo;
	}
	
}
