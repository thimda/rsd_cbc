package nc.uap.wfm.gui;
/**
 * 2011-5-26 ����09:30:33 limingf
 */
public class Element {
	private static final long serialVersionUID = 2448015201519911936L;
	private String id = "";
	private String name = "";
	// �ڵ�ִ��״̬,0,δ����Ĭ��ֵ����1,���죬2���ڰ�,3,�Ѱ죬4��ͣ�죬5���˻أ�
	// _state+8,��ǰ״̬��_state+16,��ǩ��_state+24������
	private int state = 0;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
}
