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
 * �ͻ��˹�ʽ������,��Ҫ�Ѿ�����ģ�������publicʹ�ã�ֻ�ü�������public����ڲ���һ�������� User: cch Date:
 * 2004-9-27 Time: 9:24:22 To change this template use File | Settings | File
 * Templates.
 */
public class FormulaParse extends FormulaParseFather {
	
	private static final String NC_FORMULA_MIN_RUN_IN_SERVER_SIZE = "nc.formula.minRunInServerSize";

	protected final static String uiDelegator = "nc.ui.pub.fomulaparse.FormulaParseUIDelegator";

	IFormulaParseDelegator delegator = null;

//	private FormulaRemoteCallInfo formulaCallInfo = new FormulaRemoteCallInfo();

	/**
	 * UI�˹�ʽ���������캯��������UI��ר�к���
	 * 
	 */
	public FormulaParse() {
		super();
		initParser();
	}

	/**
	 * UI�˹�ʽ���������캯��������UI��ר�к���
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
				throw new BusinessRuntimeException("��ʽ������ʼ��ʧ��!");
			}
		} else
			delegator = new FormulaParseBSDelegator();

		delegator.addFunctions(jepParser);
		//ע��ΨһID
//		formulaCallInfo.setSessionID(UUID.randomUUID().toString());
	}

	@Override
	public Object[][] getValueOArray() {
		//����ں�ִ̨�У���ֱ�ӷ���
		if(RuntimeEnv.getInstance().isRunningInServer())
			return super.getValueOArray();
//		JEPExpressionAnalyser jepExpressionAnalyser = new JEPExpressionAnalyser();
		JEPExpressionAnalyser jepExpressionAnalyser = new JEPExpressionNoDependcyAnalyser();
		jepExpressionAnalyser.setExpressions(formulavos);
		JEPAnalyseResult expResult = jepExpressionAnalyser.analyse();
		
		//����ǰ̨ �� ��̨Ҫִ�е� ��ʽ
		//if(ֻ��ǰ̨)
		List<JEPExpression> backExpRegion = expResult.getBackExpRegion();
		List<JEPExpression> foreExpRegion = expResult.getForeExpRegion();
		
		Logger.debug("���ں�̨�Ĺ�ʽ�У�");
		for (JEPExpression expression : backExpRegion) {
			Logger.debug(expression.toString());
		}
		Logger.debug("����ǰ̨�Ĺ�ʽ�У�");
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
		//ǰ��̨����
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
			//��ԭ
			m_expressCount = allFormulaExpressions.size();
			formulavos = allFormulaExpressions;
		}
		//�Թ�ʽ����ֵ���д���
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
	 * �õ������Ա���,��̨Ĭ��Ϊ��������
	 * 
	 * @return
	 */
	public String getLangCode() {
		return System.getProperty("Login_Lang_Code");
	}

}
