package nc.uap.ctrl.tpl.qry.meta;

import nc.ui.querytemplate.querytree.QueryTree;

/**
 * 固定条件查询树翻译器
 * <p>
 * 提供QueryTree对象和字节数组之间相互翻译的功能
 * 
 * @author 刘晨伟
 *
 * 创建日期：2009-9-17
 */
public class QueryTreeTranslator {

	/**
	 * 返回将字节数组解压后恢复成QueryTree对象
	 * 
	 * @param bytes
	 *            压缩后的字节数组
	 */
	public static QueryTree translate(byte[] bytes) {
		return (QueryTree) BlobUtil.convert(bytes);
	}
	
    /**
	 * 返回将QueryTree对象生成的XML串通过GZip压缩成字节数组
	 * 
	 * @param queryTree
	 *            由QueryTree生成的XML串
	 */
	public static byte[] translate(QueryTree queryTree) {
		return BlobUtil.convert(queryTree);
	}
}