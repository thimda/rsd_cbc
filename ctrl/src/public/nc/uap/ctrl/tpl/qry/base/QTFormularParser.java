package nc.uap.ctrl.tpl.qry.base;

import nc.ui.pub.formulaparse.FormulaParse;

/**
 * ��ѯģ�幫ʽ������
 * 
 * @author ����ΰ
 *
 * �������ڣ�2009-7-15
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
			// ��Ӧ��Ŀ¼��NC_HOME\resources\formulaconfig\custfunction\querytemplate
			// ȥ��Ŀ¼�µ�default.xml�ļ��л�ȡ��ʽ��Ϣ
			fp = new FormulaParse("querytemplate");
		}
		return fp;
	}
}