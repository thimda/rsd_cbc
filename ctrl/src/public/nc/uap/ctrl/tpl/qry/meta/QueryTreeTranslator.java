package nc.uap.ctrl.tpl.qry.meta;

import nc.ui.querytemplate.querytree.QueryTree;

/**
 * �̶�������ѯ��������
 * <p>
 * �ṩQueryTree������ֽ�����֮���໥����Ĺ���
 * 
 * @author ����ΰ
 *
 * �������ڣ�2009-9-17
 */
public class QueryTreeTranslator {

	/**
	 * ���ؽ��ֽ������ѹ��ָ���QueryTree����
	 * 
	 * @param bytes
	 *            ѹ������ֽ�����
	 */
	public static QueryTree translate(byte[] bytes) {
		return (QueryTree) BlobUtil.convert(bytes);
	}
	
    /**
	 * ���ؽ�QueryTree�������ɵ�XML��ͨ��GZipѹ�����ֽ�����
	 * 
	 * @param queryTree
	 *            ��QueryTree���ɵ�XML��
	 */
	public static byte[] translate(QueryTree queryTree) {
		return BlobUtil.convert(queryTree);
	}
}