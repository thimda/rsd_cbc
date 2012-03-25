package nc.uap.ctrl.tpl.qry.sysfunc;

import java.util.ArrayList;
import java.util.List;

/**
 * 日期函数工厂
 * 
 * @author 刘晨伟
 * 
 * 创建日期：2010-10-19
 */
public class DateFormularFunctionFactory {
	
	/**
	 * 创建默认日期函数列表
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
	 * 默认日期函数编码数组
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
	 * 默认日期函数名称数组
	 */
	private static String[] createDefaultFunctionsNames() {
		return new String[]{
				"UPP_NewQryTemplate-0054"/* 昨天 */,"UPP_NewQryTemplate-0055"/* 今天 */,"UPP_NewQryTemplate-0056"/* 明天 */,
				"UPP_NewQryTemplate-0057"/* 上周 */,"UPP_NewQryTemplate-0058"/* 本周 */,"UPP_NewQryTemplate-0059"/* 下周 */,
				"UPP_NewQryTemplate-0060"/* 上月 */,"UPP_NewQryTemplate-0061"/* 本月 */,"UPP_NewQryTemplate-0062"/* 下月 */,
				"UPP_NewQryTemplate-0063"/* 上季 */,"UPP_NewQryTemplate-0064"/* 本季 */,"UPP_NewQryTemplate-0065"/* 下季 */,
				"UPP_NewQryTemplate-0066"/* 去年 */,"UPP_NewQryTemplate-0067"/* 今年 */,"UPP_NewQryTemplate-0068"/* 明年 */};
	}
}