package nc.uap.ctrl.tpl.qry.base;

import java.util.ArrayList;
import java.util.List;

import nc.bs.framework.common.NCLocator;
import nc.bs.framework.common.RuntimeEnv;
import nc.bs.logging.Logger;
import nc.bs.pub.formulaparse.FormulaParseBSDelegator;
import nc.ui.pub.formulaparse.IFormulaParseDelegator;
import nc.vo.pub.BusinessRuntimeException;
import nc.vo.pub.formulaset.FormulaParseFather;
import nc.vo.pub.formulaset.core.ASTFunNode;
import nc.vo.pub.formulaset.function.IForeDBFunction;
import nc.vo.pub.formulaset.function.PostfixMathCommand;
import nc.vo.pub.formulaset.jep.JEPAnalyseResult;
import nc.vo.pub.formulaset.jep.JEPExpression;
import nc.vo.pub.formulaset.jep.JEPExpressionAnalyser;
import nc.vo.pub.formulaset.jep.JEPExpressionNoDependcyAnalyser;
import nc.vo.pub.formulaset.jep.JEPExpressionParser;
import nc.vo.pub.formulaset.service.IDBFunctionQueryData;
import nc.vo.pub.formulaset.service.IFormulaService;

/**
 * 客户端公式解析器,主要已经被各模块大量在public使用，只好继续放在public里，但内部做一定的适配 User: cch Date:
 * 2004-9-27 Time: 9:24:22 To change this template use File | Settings | File
 * Templates.
 */
public class FormulaParse extends FormulaParseFather {
	
	private static final String NC_FORMULA_MIN_RUN_IN_SERVER_SIZE = "nc.formula.minRunInServerSize";

	protected final static String uiDelegator = "nc.ui.pub.fomulaparse.FormulaParseUIDelegator";

	IFormulaParseDelegator delegator = null;

//	private FormulaRemoteCallInfo formulaCallInfo = new FormulaRemoteCallInfo();

	/**
	 * UI端公式解析器构造函数，加入UI端专有函数
	 * 
	 */
	public FormulaParse() {
		super();
		initParser();
	}

	/**
	 * UI端公式解析器构造函数，加入UI端专有函数
	 * 
	 */
	public FormulaParse(String ownModuleID) {
		super(ownModuleID);
		initParser();
//		formulaCallInfo.setOwnModuleID(ownModuleID);
	}

	private void initParser() {

		JEPExpressionParser jepParser = getJepParser();
		if (!RuntimeEnv.getInstance().isRunningInServer()) {
			try {
				delegator = (IFormulaParseDelegator) Class.forName(uiDelegator)
						.newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (Exception e) {
				Logger.error("", e);
				throw new BusinessRuntimeException("公式解析初始化失败!");
			}
		} else
			delegator = new FormulaParseBSDelegator();

		delegator.addFunctions(jepParser);
		//注册唯一ID
//		formulaCallInfo.setSessionID(UUID.randomUUID().toString());
	}

	@Override
	public Object[][] getValueOArray() {
		//如果在后台执行，则直接返回
		if(RuntimeEnv.getInstance().isRunningInServer())
			return super.getValueOArray();
//		JEPExpressionAnalyser jepExpressionAnalyser = new JEPExpressionAnalyser();
		JEPExpressionAnalyser jepExpressionAnalyser = new JEPExpressionNoDependcyAnalyser();
		jepExpressionAnalyser.setExpressions(formulavos);
		JEPAnalyseResult expResult = jepExpressionAnalyser.analyse();
		
		//分析前台 和 后台要执行的 公式
		//if(只有前台)
		List<JEPExpression> backExpRegion = expResult.getBackExpRegion();
		List<JEPExpression> foreExpRegion = expResult.getForeExpRegion();
		
		Logger.debug("放在后台的公式有：");
		for (JEPExpression expression : backExpRegion) {
			Logger.debug(expression.toString());
		}
		Logger.debug("放在前台的公式有：");
		for (JEPExpression expression : foreExpRegion) {
			Logger.debug(expression.toString());
		}
		
		List<Integer> backAnalyserRegion = expResult.getBackExpAnalyserRegion();
		List<Integer> foreAnalyserRegion = expResult.getForeExpAnalyserRegion();
		String minBackFormulaSize = System.getProperty(NC_FORMULA_MIN_RUN_IN_SERVER_SIZE, "2");
		Object[][] result = null;
		if(backExpRegion == null || backExpRegion.size() < Integer.parseInt(minBackFormulaSize))
			return super.getValueOArray();
		else if(foreExpRegion == null || foreExpRegion.size() == 0)
		{
			// call remote service
			result = getDBValueFromBackend(backExpRegion);
		}
		//前后台都有
		else
		{
			//get fore 
			List allFormulaExpressions = formulavos;
			formulavos = foreExpRegion;
			Object[][] foreResults = super.getValueOArray();
			//get backend
			Object[][] backendResults = getDBValueFromBackend(backExpRegion);
			//merage
			result = new Object[allFormulaExpressions.size()][];
			if(foreResults!=null)
			{
				for (int i = 0; i < foreExpRegion.size(); i++) {
					result[foreAnalyserRegion.get(i)] = foreResults[i];
				}
			}
			if(backendResults!=null)
			{
				for (int i = 0; i < backExpRegion.size(); i++) {
					
					result[backAnalyserRegion.get(i)] = backendResults[i];
				}
			}
			//还原
			m_expressCount = allFormulaExpressions.size();
			formulavos = allFormulaExpressions;
		}
		//对公式返回值进行处理
		result = dealResultValue(result);
		return result;
	}

//	private Object[][] getValueFromBackend() {
//		
//		IFormulaService formulaService = (IFormulaService) NCLocator
//				.getInstance().lookup(IFormulaService.class.getName());
//		// zip
//		ICompressStragety zipStgy = new GZipCompressStrategy();
//		ICompressedData result = formulaService.evaluteFormula(zipStgy
//				.compress(formulaCallInfo));
//		return (Object[][]) zipStgy.unCompress(result);
//		
//	}
	
	private Object[][] getDBValueFromBackend(List<JEPExpression> expressions) {
		
		IFormulaService formulaService = (IFormulaService) NCLocator
				.getInstance().lookup(IFormulaService.class.getName());
		
		List<IDBFunctionQueryData> queryDatas = new ArrayList<IDBFunctionQueryData>();
		for (JEPExpression expression : expressions) {
			ASTFunNode topnode = (ASTFunNode) expression.getTopNode();
			if(topnode.getPFMC() instanceof IForeDBFunction)
			{
				queryDatas.add(((IForeDBFunction)topnode.getPFMC()).getQueryData(expression));
			}
		}
		Object[][] result= formulaService.excuteBatchDBFuntionBackend(queryDatas);
		if(result!=null&&result.length>0)
		{
			for (int i = 0; i < result.length; i++) {
				result[i] = PostfixMathCommand.dealGetColValuelResult(queryDatas.get(i).getResArr(), result[i]);
			}
		}
		return result;
	}

	/**
	 * 得到多语言编码,后台默认为简体中文
	 * 
	 * @return
	 */
	public String getLangCode() {
		return System.getProperty("Login_Lang_Code");
	}

}
