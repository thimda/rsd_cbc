package nc.uap.cpb.org.itf;

import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.vos.MenuAdapterVO;

/**
 * �˵������ѯ�ӿ�
 * 2011-12-26 ����04:02:03
 * @author limingf
 *
 */
public interface ICpMenuQryService {
	/**
	 * ��ȡ���в˵�������˵�����,�˵���
	 * @return
	 * @throws CpbBusinessException
	 */
	public MenuAdapterVO[] getAllMenus() throws CpbBusinessException ;
	
	/**
	 * ��ȡ���в˵�����
	 * @return
	 * @throws CpbBusinessException
	 */
	public MenuAdapterVO[] getAllMenuCategorys() throws CpbBusinessException ;
}
