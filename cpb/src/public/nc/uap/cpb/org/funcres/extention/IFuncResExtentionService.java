package nc.uap.cpb.org.funcres.extention;

import nc.uap.cpb.org.vos.CpFuncResVO;


/**
 * ������Դɨ����չ��
 *
 */
public interface IFuncResExtentionService {

	//������Դɨ����չ��
	public static final String FUNCRES_SCAN = "funcresmanage";
	
	//��Դ����
	//���ܽڵ�
	public static final int TYPE_FUNC = 1;
	//portalҳ��
	public static final int TYPE_PORTALPAGE = 2;
	//portlet
	public static final int TYPE_PORTLET = 3;
	//......
	
	public int getFuncResType();
	
	public CpFuncResVO[] getFuncResVos();
	
}
