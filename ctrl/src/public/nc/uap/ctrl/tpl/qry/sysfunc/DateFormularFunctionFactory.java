package nc.uap.ctrl.tpl.qry.sysfunc;

import java.util.ArrayList;
import java.util.List;

/**
 * ���ں�������
 * 
 * @author ����ΰ
 * 
 * �������ڣ�2010-10-19
 */
public class DateFormularFunctionFactory {
	
	/**
	 * ����Ĭ�����ں����б�
	 */
	public static List<DateFormularFunction> createDefaultFunctions() {
		List<DateFormularFunction> functions = new ArrayList<DateFormularFunction>();
		String[] functionsCodes = createDefaultFunctionsCodes();
		String[] functionsNames = createDefaultFunctionsNames();
		for (int i = 0; i < functionsCodes.length; i++) {
			functions.add(new DateFormularFunction(functionsCodes[i], functionsNames[i]));
		}
		return functions;
	}
	
	/**
	 * Ĭ�����ں�����������
	 */
	private static String[] createDefaultFunctionsCodes() {
		return new String[]{
				 "day(-1)","day(0)","day(1)",
				 "week(-1)","week(0)","week(1)",
				 "month(-1)","month(0)","month(1)",
				 "quarter(-1)","quarter(0)","quarter(1)",
				 "year(-1)","year(0)","year(1)"};
	}

	/**
	 * Ĭ�����ں�����������
	 */
	private static String[] createDefaultFunctionsNames() {
		return new String[]{
				"UPP_NewQryTemplate-0054"/* ���� */,"UPP_NewQryTemplate-0055"/* ���� */,"UPP_NewQryTemplate-0056"/* ���� */,
				"UPP_NewQryTemplate-0057"/* ���� */,"UPP_NewQryTemplate-0058"/* ���� */,"UPP_NewQryTemplate-0059"/* ���� */,
				"UPP_NewQryTemplate-0060"/* ���� */,"UPP_NewQryTemplate-0061"/* ���� */,"UPP_NewQryTemplate-0062"/* ���� */,
				"UPP_NewQryTemplate-0063"/* �ϼ� */,"UPP_NewQryTemplate-0064"/* ���� */,"UPP_NewQryTemplate-0065"/* �¼� */,
				"UPP_NewQryTemplate-0066"/* ȥ�� */,"UPP_NewQryTemplate-0067"/* ���� */,"UPP_NewQryTemplate-0068"/* ���� */};
	}
}