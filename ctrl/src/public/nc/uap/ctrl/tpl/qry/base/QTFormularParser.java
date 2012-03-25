package nc.uap.ctrl.tpl.qry.base;

import nc.ui.pub.formulaparse.FormulaParse;

/**
 * 查询模板公式解析器
 * 
 * @author 刘晨伟
 *
 * 创建日期：2009-7-15
 */
public class QTFormularParser {

	private static QTFormularParser instance = new QTFormularParser();

	private FormulaParse fp = null;

	private QTFormularParser() {
	}

	public static QTFormularParser getInstance() {
		return instance;
	}

	public FormulaParse getParser() {
		if (fp == null) {
			// 对应于目录：NC_HOME\resources\formulaconfig\custfunction\querytemplate
			// 去该目录下的default.xml文件中获取公式信息
			fp = new FormulaParse("querytemplate");
		}
		return fp;
	}
}