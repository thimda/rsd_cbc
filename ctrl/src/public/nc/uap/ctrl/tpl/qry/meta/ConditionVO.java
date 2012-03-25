package nc.uap.ctrl.tpl.qry.meta;


import java.util.ArrayList;
import java.util.List;

import nc.bs.logging.Logger;
import nc.vo.pub.lang.UFBoolean;

/**
 * �û��趨������VO ��������:(2001-4-25 16:06:04)
 * 
 * @author:������
 * 
 * �û�ѡ��Ĳ�ѯ����,����ϳ�����ʱ,��ƴ�ӵõ�where���SQL���. �޸�����:2001-07-05
 * ��������:���������ϵ����,�����ŵ�������������,���ע�� �޸���:����
 * 
 * �޸�����:2001-08-17 ��������:����˲��շ��ؽ��RefResultVO refResult �޸���:����
 */

public class ConditionVO extends nc.vo.pub.ValueObject implements
        IQueryConstants {

    public final static String SEPARATOR_INNER = ":"; //ð��
    public final static String SEPARATOR_OUTTER = ";"; //�ֺ�

    private boolean logic = true; //��������֮�������ϵ,ȱʡΪ���롯.
    private boolean noLeft = true; //����������,ȱʡΪ���ޡ�.
    private boolean noRight = true; //����������,ȱʡΪ���ޡ�.
    private UFBoolean ifDesc;
    private UFBoolean ifSysFunc; //�Ƿ�ϵͳ����,�̶�ֵ��������Ҫʹ��

    private String tableCode; //�����
    private String tableName; //������
    private String fieldCode; //�ֶα���(������)
    private String fieldName; //�ֶ�����
    private String operaCode; //����������, �Ƿ�Ӧͳһ�������������?
    private String operaName; //����������,�̶�ֵ��������Ҫʹ��
    private String value; //����ȡֵ
    private String comboType = COMBO_NULL;//�����򷵻�����
    private RefResultVO refResult; //���ս��,refCode, refName, refPk, object

    private int dataType; //��������
    private int orderSequence; //����˳��
    private int comboIndex; //�������ѡ�е�index
    
    private boolean fixCondition = false;

    private static final long serialVersionUID = -6061741910913318960L;

    public final String DISP_AND = 
                    nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID(
                            "_Template", "UPP_Template-000515")/* @res "����" */;
    public final String DISP_OR = 
                    nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID(
                            "_Template", "UPP_Template-000516")/* @res "����" */;

    /**
     * ConditionVO ������ע��.
     */
    public ConditionVO() {
        super();
    }
    /**
     * ConditionVO ������,ֱ�ӴӲ�ѯģ��VO����һ����ѯ����VO. ע��:������������operaCode��ȡֵValue
     */
    public ConditionVO(QueryConditionVO queryVO) {
        super();
        setFieldCode(queryVO.getFieldCode());
        setFieldName(queryVO.getFieldName());
        setTableCode(queryVO.getTableCode());
        setTableName(queryVO.getTableName());
        setDataType(queryVO.getDataType().intValue());
        setIfDesc(queryVO.getIfDesc());
        setOrderSequence(queryVO.getOrderSequence().intValue());
    }
    /**
     * �˴����뷽��˵��. ��������:(2001-7-12 14:44:08)
     * 
     * @return java.lang.String
     */
    public String getChStr() {
        return getChStr(false);
    }
    /**
     * �˴����뷽��˵��. ��������:(2002-01-24 11:40:56)
     * 
     * @return java.lang.String
     * @param conditions
     *            nc.vo.pub.query.ConditionVO[]
     */
    public String getCHStr(ConditionVO[] conditions) {
        if ((conditions == null) || (conditions.length == 0))
            return null;

        String chStr = "";

        if (conditions != null)
            for (int i = 0; i < conditions.length; i++)
                chStr += conditions[i].getChStr();
        chStr = chStr.trim();

        //ȥ����һ��and/or
        if (chStr.indexOf(" ") > 0)
            chStr = chStr.substring(chStr.indexOf(" "));
        else
            chStr = null;

        return chStr;
    }
    /**
     * �˴����뷽��˵��. ��������:(2001-7-12 14:44:08)
     * 
     * @return java.lang.String
     */
    public String getChStr(boolean isMultiTable) {
        String str = "";
        if (logic)
            str += " " + DISP_AND;
        else
            str += " " + DISP_OR;

        if (noLeft)
            str += "";
        else
            str += " (";

        if (isMultiTable) {
          str += " (" + tableName + "." + fieldName ;
//            str += " ("
//                    + nc.ui.pub.ClientEnvironment.getInstance().getModuleLang()
//                            .getString(tableName + "." + fieldName);
        }
        else {
            str += " (" + fieldName ;
//            str += " ("
//                    + nc.ui.pub.ClientEnvironment.getInstance().getModuleLang()
//                            .getString(fieldName);
        }
        if (operaCode.equalsIgnoreCase(IOperatorConstants.ISNULL)
                || operaCode.equals(IOperatorConstants.ISNULL_INCLUDE_SPACE))
            str += " "
                    + nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID(
                            "_Template", "UPP_Template-000511")/* @res "Ϊ��" */
                    + ")";
        else if (operaCode.equalsIgnoreCase(IOperatorConstants.ISNOTNULL)
                || operaCode.equals(IOperatorConstants.ISNOTNULL_INCLUDE_SPACE))
            str += " "
                    + nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID(
                            "_Template", "UPP_Template-000512")/* @res "��Ϊ��" */
                    + ")";
        else {
            str += " " + operaName;
            if (operaCode.trim().equalsIgnoreCase("in"))
                str += " (" + value + "))";

            else if ((dataType == INTEGER) || (dataType == DECIMAL)
                    || (dataType == USERCOMBO && comboType.equals(COMBO_INDEX)))
                str += " " + value + ")";
            else
                str += " '" + value + "')";
        }
        if (noRight)
            str += "";
        else
            str += " )";

        return str;
    }
    /**
     * �˴����뷽��˵��. ��������:(2001-10-17 11:17:21)
     * 
     * @return int
     */
    public int getComboIndex() {
        return comboIndex;
    }
    /**
     * �˴����뷽��˵��. ��������:(2003-11-03 13:04:27)
     * 
     * @return int
     */
    public String getComboType() {
        return comboType;
    }
    /**
     * ��������:(2001-4-25 16:45:19)
     * 
     * @return int
     */
    public int getDataType() {
        return dataType;
    }
    /**
     * ������ֵ�������ʾ����.
     * 
     * ��������:(2001-2-15 14:18:08)
     * 
     * @return java.lang.String ������ֵ�������ʾ����.
     */
    public String getEntityName() {
        return null;
    }
    /**
     * ��������:(2001-4-25 16:07:15)
     * 
     * @return int
     */
    public String getFieldCode() {
        return fieldCode;
    }
    /**
     * ��������:(2001-5-21 15:53:09)
     * 
     * @return java.lang.String
     */
    public java.lang.String getFieldName() {
        return fieldName;
    }
    /**
     * @return String
     */
    public String getFieldNoT() {
        if (fieldCode != null && fieldCode.length() != 0) {
            int index = fieldCode.indexOf(".");
            String field = null;
            if (index >= 0)
                field = fieldCode.substring(index + 1, fieldCode.length());
            else
                field = fieldCode;
            return field;
        }
        else
            return null;

    }
    /**
     * ����ifDesc��Getter����.
     * 
     * ��������:(2001-6-14)
     * 
     * @return UFBoolean
     */
    public UFBoolean getIfDesc() {
        return ifDesc;
    }
    /**
     * ����ifSysFunc��Getter����.
     * 
     * ��������:(2001-6-14)
     * 
     * @return UFBoolean
     */
    public UFBoolean getIfSysFunc() {
        return ifSysFunc;
    }
    /**
     * �˴����뷽��˵��. ��������:(2001-7-5 12:55:07)
     * 
     * @return boolean
     */
    public boolean getLogic() {
        return logic;
    }
    /**
     * �˴����뷽��˵��. ��������:(2001-7-5 12:55:07)
     * 
     * @return boolean
     */
    public boolean getNoLeft() {
        return noLeft;
    }
    /**
     * �˴����뷽��˵��. ��������:(2001-7-5 12:55:07)
     * 
     * @return boolean
     */
    public boolean getNoRight() {
        return noRight;
    }
    /**
     * ��������:(2001-4-25 16:45:08)
     * 
     * @return java.lang.String
     */
    public java.lang.String getOperaCode() {
        return operaCode;
    }
    /**
     * ��������:(2001-4-25 16:45:08)
     * 
     * @return java.lang.String
     */
    public java.lang.String getOperaName() {
        return operaName;
    }
    /**
     * ��������:(2001-4-25 16:45:19)
     * 
     * @return int
     */
    public int getOrderSequence() {
        return orderSequence;
    }
    /**
     * �˴����뷽��˵��. ��������:(2001-8-17 9:11:07)
     * 
     * @return nc.vo.pub.query.RefResultVO
     */
    public RefResultVO getRefResult() {
        return refResult;
    }
    /**
     * �˴����뷽��˵��. ��������:(2001-7-12 14:44:08)
     * 
     * @return java.lang.String
     */
    public String getSQLStr() {
        return getSQLStr(false);
    }
    /**
     * �˴����뷽��˵��. ��������:(2002-01-24 11:40:56)
     * 
     * @return java.lang.String
     * @param conditions
     *            nc.vo.pub.query.ConditionVO[]
     */
    public String getSQLStr(ConditionVO[] conditions) {
        if ((conditions == null) || (conditions.length == 0))
            return null;
        return getWhereSQL(conditions);
//        String str = "";
//        for (int i = 0; i < conditions.length; i++)
//            str += conditions[i].getSQLStr();
//
//        //ȥ����һ��and/or
//        if (str.length() > 3) {
//            try {
//                str = str.substring(str.indexOf(" ", 1));
//            }
//            catch (Throwable e) {
//                e.printStackTrace();
//            }
//        }
//        else {
//            str = null;
//        }
//
//        return str;
    }
    /**
     * �˴����뷽��˵��. ��������:(2001-7-12 14:44:08)
     * 
     * @return java.lang.String
     */
    private String getSQLStr(boolean isNullWithSpace) {
        StringBuffer str = new StringBuffer();
        if (logic)
            str.append(" and");
        else
            str.append(" or");

        if (!noLeft)
            str.append(" (");

        boolean isAllNull = false;
        boolean isAllNotNull = false;
        if (operaCode.trim().equalsIgnoreCase(IOperatorConstants.ISNULL_INCLUDE_SPACE)
                || (isNullWithSpace && operaCode.trim().equalsIgnoreCase(
                        IOperatorConstants.ISNULL)))
            isAllNull = true;
        else if (operaCode.trim().equalsIgnoreCase(IOperatorConstants.ISNOTNULL_INCLUDE_SPACE)
                || (isNullWithSpace && operaCode.trim().equalsIgnoreCase(
                		IOperatorConstants.ISNOTNULL)))
            isAllNotNull = true;
        if (isAllNull && !isDigital())
        str.append(" ((isnull(" + fieldCode + ",'~')='~') or (ltrim(rtrim("
                + fieldCode + ")) = ''))");
        else if (isAllNotNull && !isDigital())
            str.append(" ((isnull(" + fieldCode + ",'~')<>'~') and (ltrim(rtrim("
                    + fieldCode + ")) <> ''))");
        else {
            String strOpera = operaCode.trim();
            if (isAllNull){
            	strOpera = IOperatorConstants.ISNULL;
            }else if (isAllNotNull){
            	strOpera = IOperatorConstants.ISNOTNULL;
            }else if (operaCode.trim().equals(IOperatorConstants.EQ2)){
            	strOpera = IOperatorConstants.EQ;
            }

            String newValue = value;
            if (operaCode.equalsIgnoreCase("like") && value.indexOf("%") < 0)
                newValue = "%" + newValue + "%";
            if (!isDigital())
                newValue = "'" + newValue + "'";

            str.append(" (" + fieldCode + " " + strOpera);
            if ((!isNullWithSpace && (strOpera.equalsIgnoreCase(IOperatorConstants.ISNULL) || strOpera
                    .equalsIgnoreCase(IOperatorConstants.ISNOTNULL)))) {
                str.append(")");
            }
            else if (operaCode.trim().equalsIgnoreCase("in")||operaCode.trim().indexOf(" in")!=-1) {
                if (value.startsWith("(") && value.endsWith(")")) { //����in
                                                                    // �Ĳ�����,��value��()����,�����ж���������
                    str.append(" " + value + ")");
                }
                else {
                    str.append(" (" + newValue + "))");
                }
            }else if(operaCode.trim().equalsIgnoreCase("between")) {//���ڽ��ڲ�������;valueΪ��,�ֿ����ַ������
            	
            	//between and �Ĵ���
//            	if(value.startsWith("(") && value.endsWith(")")) {
//            		String[] values =value.substring(1, value.length()-1).split(",");
//            		str.append(" "+values[0]+" and "+ values[1] +")");
//            	}else {//��value����()���棬����Ҫ�ж���������..between and Ҳ�����ַ� 
//            		String[] values =value.split(",");
//            		if(isDigital()) {
//            			str.append(" "+values[0]+" and "+ values[1] +")");	
//            		}else {
//            			str.append(" '"+values[0]+"' and '"+ values[1] +"')");
//            		}
//            	}
            	//��>= �� <= ������
            	String[] values = value.split(",");
            	str.delete(str.length()-7, str.length());
            	boolean firstEff = false;
            	if(!values[0].equals(IOperatorConstants.ISNULL_INCLUDE_SPACE)) {
            		firstEff = true;
            		str.append(">="+getSqlValue(values[0]));            		
            	}
            	if(!values[1].equals(IOperatorConstants.ISNULL_INCLUDE_SPACE)) {
            		if(firstEff)
            			str.append(" and "+ fieldCode +" <= "+getSqlValue(values[1]));
            		else
            			str.append(" <= "+ getSqlValue(values[1]));
            	}
            	str.append(")");
            }
            
            else //����������
            {
                str.append(" " + newValue + ")");
            }
        }
        if (!noRight)
            str.append(" )");
        return str.toString();
    }
    private String getSqlValue(String value) {
    	if(!isDigital())
    		return "'"+value+"'";
    	else return value;
    }
    /**
     * �˴����뷽��˵��. ��������:(2001-7-12 14:44:08)
     * 
     * @return java.lang.String
     */
    public String getSQLStrForNull() {
        return getSQLStr(true);
    }
    /**
     * ��������:(2001-4-25 16:07:15)
     * 
     * @return int
     */
    public String getTableCodeForMultiTable() {
        return tableCode;
    }
    /**
     * @return String
     */
    public String getTableName() {
        if (fieldCode != null && fieldCode.length() != 0) {
            int index = fieldCode.indexOf(".");
            String tableName = null;
            if (index >= 0)
                tableName = fieldCode.substring(0, index);
            return tableName;
        }
        else
            return null;

    }
    /**
     * ��������:(2001-5-21 15:53:09)
     * 
     * @return java.lang.String
     */
    public java.lang.String getTableNameForMultiTable() {
        return tableName;
    }
    /**
     * ��������:(2001-4-25 16:48:29)
     * 
     * @return java.lang.String
     */
    public java.lang.String getValue() {
        return value;
    }
    /**
     * �˴����뷽��˵��. ��������:(2002-01-24 11:40:56)
     * 
     * @return java.lang.String
     * @param conditions
     *            nc.vo.pub.query.ConditionVO[]
     */
    public String getWhereSQL(ConditionVO[] conditions) {
		if ((conditions == null) || (conditions.length == 0))
            return null;
		List<ConditionVO> fixConditions = new ArrayList<ConditionVO>();
		List<ConditionVO> customConditions = new ArrayList<ConditionVO>();
		for (ConditionVO vo : conditions)
		{
			if(vo.isFixCondition())
			{
				fixConditions.add(vo);
			}
			else
			{
				customConditions.add(vo);
			}
		}
		Logger.debug("fixcondition size:"+ fixConditions.size());
		Logger.debug("customConditions size" + customConditions.size());
    	if(!fixConditions.isEmpty()&&!customConditions.isEmpty())
    	{
    		return getWhereSqlHelper(fixConditions.toArray(new ConditionVO[0])) + " and ("+
    		getWhereSqlHelper(customConditions.toArray(new ConditionVO[0])) +")";
    	}
    	else if(fixConditions.isEmpty()&&!customConditions.isEmpty())
    	{
    		return getWhereSqlHelper(customConditions.toArray(new ConditionVO[0]));
    	}
    	else if(!fixConditions.isEmpty()&&customConditions.isEmpty())
    	{
    		return getWhereSqlHelper(fixConditions.toArray(new ConditionVO[0]));
    	}
    	return null;
    }
	private String getWhereSqlHelper(ConditionVO[] conditions) {
		if ((conditions == null) || (conditions.length == 0))
            return null;

        String str = "";
        for (int i = 0; i < conditions.length; i++)
            str += conditions[i].getSQLStr();

        //ȥ����һ��and/or
        if (str.length() > 3) {
            try {
                str = str.substring(str.indexOf(" ", 1));
            }
            catch (Throwable e) {
                e.printStackTrace();
            }
        }
        else {
            str = null;
        }

        return str;
	}
    /**
     * �˴����뷽��˵��. ��������:(2004-4-7 16:38:07)
     * 
     * @return boolean
     */
    public boolean isDigital() {
        if (dataType == INTEGER || dataType == DECIMAL
                || (dataType == USERCOMBO && comboType.equals(COMBO_INDEX)))
            return true;

        return false;
    }
    /**
     * �˴����뷽��˵��. ��������:(2003-10-27 10:46:38)
     * 
     * @return nc.vo.pub.query.ConditionVO[]
     * @param strConditions
     *            java.lang.String
     */
    public static ConditionVO[] parseString(String strConditions) {
        if (strConditions == null || strConditions.length() == 0) {
            return null;
        }
        java.util.StringTokenizer st = new java.util.StringTokenizer(
                strConditions, SEPARATOR_OUTTER, false);

        //�߼���ϵ
        int count = st.countTokens();
        ConditionVO[] conditions = new ConditionVO[count];
        for (int i = 0; i < count; i++) {
            conditions[i] = parseStringToOne(st.nextToken());
        }
        return conditions;
    }
    /**
     * �˴����뷽��˵��. ��������:(2003-10-27 10:53:38)
     * 
     * @return nc.vo.pub.query.ConditionVO
     * @param strCon
     *            java.lang.String
     */
    private static ConditionVO parseStringToOne(String describer) {
        if (describer == null || describer.length() == 0) {
            return null;
        }
        try {
            ConditionVO condition = new ConditionVO();
            java.util.StringTokenizer st = new java.util.StringTokenizer(
                    describer, SEPARATOR_INNER, false);

            //�߼���ϵ
            String boolVal = st.nextToken();
            condition.setLogic(boolVal.equals("Y"));
            //����������
            boolVal = st.nextToken();
            condition.setNoLeft(boolVal.equals("Y"));
            //����������
            boolVal = st.nextToken();
            condition.setNoRight(boolVal.equals("Y"));

            //�Ƿ���
            boolVal = st.nextToken();
            condition.setIfDesc(UFBoolean.valueOf((boolVal.equals("Y"))));
            //�Ƿ�ϵͳ����
            boolVal = st.nextToken();
            condition.setIfSysFunc(UFBoolean.valueOf(boolVal.equals("Y")));

            //�����
            String str = st.nextToken();
            condition.setTableCode(str.equals("null") ? null : str);
            //������
            str = st.nextToken();
            condition.setTableName(str.equals("null") ? null : str);
            //�ֶα���(������)
            str = st.nextToken();
            condition.setFieldCode(str.equals("null") ? null : str);
            //�ֶ�����
            str = st.nextToken();
            condition.setFieldName(str.equals("null") ? null : str);
            //����������
            str = st.nextToken();
            condition.setOperaCode(str.equals("null") ? null : str);
            //����������
            str = st.nextToken();
            condition.setOperaName(str.equals("null") ? null : str);
            //����ȡֵ
            str = st.nextToken();
            condition.setValue(str.equals("null") ? null : str);
            //����ȡֵ
            str = st.nextToken();
            condition.setComboType(str.equals("null") ? COMBO_NULL : str);

            //���ս��
            RefResultVO ref = new RefResultVO();
            str = st.nextToken();
            ref.setRefCode(str.equals("null") ? null : str);
            str = st.nextToken();
            ref.setRefName(str.equals("null") ? null : str);
            str = st.nextToken();
            ref.setRefPK(str.equals("null") ? null : str);
            if (ref.getRefCode() != null || ref.getRefName() != null
                    || ref.getRefPK() != null)
                condition.setRefResult(ref);

            //��������
            condition.setDataType(Integer.parseInt(st.nextToken()));
            //����˳��
            condition.setOrderSequence(Integer.parseInt(st.nextToken()));
            //�������ѡ�е�index
            condition.setComboIndex(Integer.parseInt(st.nextToken()));

            return condition;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * �˴����뷽��˵��. ��������:(2001-10-17 11:16:55)
     * 
     * @param index
     *            int
     */
    public void setComboIndex(int index) {
        comboIndex = index;
    }
    /**
     * �˴����뷽��˵��. ��������:(2003-11-03 13:04:27)
     * 
     * @param newComboType
     *            int
     */
    public void setComboType(String newComboType) {
        if (newComboType == null)
            comboType = COMBO_NULL;
        else
            comboType = newComboType;
    }
    /**
     * ��������:(2001-4-25 16:45:19)
     * 
     * @param newDataType
     *            int
     */
    public void setDataType(int newDataType) {
        dataType = newDataType;
    }
    /**
     * ��������:(2001-4-25 16:07:15)
     * 
     * @param newM_fieldCode
     *            int
     */
    public void setFieldCode(String newFieldCode) {
        fieldCode = newFieldCode;
    }
    /**
     * ��������:(2001-5-21 15:53:09)
     * 
     * @param newFieldName
     *            java.lang.String
     */
    public void setFieldName(java.lang.String newFieldName) {
        fieldName = newFieldName;
    }
    /**
     * ����ifDesc��setter����.
     * 
     * ��������:(2001-6-14)
     * 
     * @param newIfDesc
     *            UFBoolean
     */
    public void setIfDesc(UFBoolean newIfDesc) {

        ifDesc = newIfDesc;
    }
    /**
     * ����ifSysFunc��setter����.
     * 
     * ��������:(2001-6-14)
     * 
     * @param newIfSysFunc
     *            UFBoolean
     */
    public void setIfSysFunc(UFBoolean newIfSysFunc) {

        ifSysFunc = newIfSysFunc;
    }
    /**
     * �˴����뷽��˵��. ��������:(2001-7-5 12:56:27)
     * 
     * @param b
     *            boolean
     */
    public void setLogic(boolean b) {
        logic = b;
    }
    /**
     * �˴����뷽��˵��. ��������:(2001-7-5 12:56:27)
     * 
     * @param b
     *            boolean
     */
    public void setNoLeft(boolean b) {
        noLeft = b;
    }
    /**
     * �˴����뷽��˵��. ��������:(2001-7-5 12:56:27)
     * 
     * @param b
     *            boolean
     */
    public void setNoRight(boolean b) {
        noRight = b;
    }
    /**
     * ��������:(2001-4-25 16:45:08)
     * 
     * @param newOpera
     *            java.lang.String
     */
    public void setOperaCode(java.lang.String newOpera) {
        operaCode = newOpera;
    }
    /**
     * ��������:(2001-4-25 16:45:08)
     * 
     * @param newOpera
     *            java.lang.String
     */
    public void setOperaName(java.lang.String newOpera) {
        operaName = newOpera;
    }
    /**
     * ��������:(2001-4-25 16:45:19)
     * 
     * @param newDataType
     *            int
     */
    public void setOrderSequence(int newOrder) {
        orderSequence = newOrder;
    }
    /**
     * �˴����뷽��˵��. ��������:(2001-8-17 9:10:08)
     * 
     * @param result
     *            nc.vo.pub.query.RefResultVO
     */
    public void setRefResult(RefResultVO result) {
        refResult = result;
    }
    /**
     * ��������:(2001-4-25 16:07:15)
     * 
     * @param newM_fieldCode
     *            int
     */
    public void setTableCode(String newTableCode) {
        tableCode = newTableCode;
    }
    /**
     * ��������:(2001-5-21 15:53:09)
     * 
     * @param newFieldName
     *            java.lang.String
     */
    public void setTableName(java.lang.String newTableName) {
        tableName = newTableName;
    }
    /**
     * ��������:(2001-4-25 16:48:29)
     * 
     * @param newValueLower
     *            java.lang.String
     */
    public void setValue(java.lang.String newValue) {
        value = newValue;
    }
    /**
     * �˴����뷽��˵��. ��������:(2003-10-27 10:45:53)
     * 
     * @return java.lang.String
     * @param vos
     *            nc.vo.pub.query.ConditionVO[]
     */
    public static String toStringInfo(ConditionVO[] vos) {
        if (vos == null || vos.length == 0)
            return null;
        StringBuffer sbuffer = new StringBuffer();
        for (int i = 0; i < vos.length; i++) {
            sbuffer.append(toStringOneInfo(vos[i]));
            sbuffer.append(SEPARATOR_OUTTER);
        }
        return sbuffer.toString();

    }
    /**
     * �˴����뷽��˵��. ��������:(2003-10-27 10:45:53)
     * 
     * @return java.lang.String
     * @param vos
     *            nc.vo.pub.query.ConditionVO[]
     */
    private static String toStringOneInfo(ConditionVO con) {
        StringBuffer strBuf = new StringBuffer();
        String space = SEPARATOR_INNER;

        //�߼���ϵ
        String boolVal = con.getLogic() ? "Y" : "N";
        strBuf.append(boolVal + space);
        //û��������
        boolVal = con.getNoLeft() ? "Y" : "N";
        strBuf.append(boolVal + space);
        //û��������
        boolVal = con.getNoRight() ? "Y" : "N";
        strBuf.append(boolVal + space);
        //�Ƿ���
        boolVal = (con.getIfDesc() != null && con.getIfDesc().booleanValue()) ? "Y"
                : "N";
        strBuf.append(boolVal + space);
        //�Ƿ�ϵͳ����
        boolVal = (con.getIfSysFunc() != null && con.getIfSysFunc()
                .booleanValue()) ? "Y" : "N";
        strBuf.append(boolVal + space);

        //�����,������,�ֶα���,�ֶ�����,����������,����������,����ȡֵ
        strBuf.append((con.getTableCodeForMultiTable() == null || con
                .getTableCodeForMultiTable().length() == 0) ? "null" : con
                .getTableCodeForMultiTable());
        strBuf.append(space);
        strBuf.append((con.getTableNameForMultiTable() == null || con
                .getTableNameForMultiTable().length() == 0) ? "null" : con
                .getTableNameForMultiTable());
        strBuf.append(space);
        strBuf.append((con.getFieldCode() == null || con.getFieldCode()
                .length() == 0) ? "null" : con.getFieldCode());
        strBuf.append(space);
        strBuf.append((con.getFieldName() == null || con.getFieldName()
                .length() == 0) ? "null" : con.getFieldName());
        strBuf.append(space);
        strBuf.append((con.getOperaCode() == null || con.getOperaCode()
                .length() == 0) ? "null" : con.getOperaCode());
        strBuf.append(space);
        strBuf.append((con.getOperaName() == null || con.getOperaName()
                .length() == 0) ? "null" : con.getOperaName());
        strBuf.append(space);
        strBuf
                .append((con.getValue() == null || con.getValue().length() == 0) ? "null"
                        : con.getValue());
        strBuf.append(space);
        strBuf.append((con.getComboType() == null || con.getComboType()
                .length() == 0) ? "null" : con.getComboType());
        strBuf.append(space);

        //���ս��,refCode, refName, refPk
        if (con.getRefResult() == null)
            strBuf.append("null:null:null:");
        else {
            RefResultVO ref = con.getRefResult();
            strBuf.append((ref.getRefCode() == null || ref.getRefCode()
                    .length() == 0) ? "null" : ref.getRefCode());
            strBuf.append(space);
            strBuf.append((ref.getRefName() == null || ref.getRefName()
                    .length() == 0) ? "null" : ref.getRefName());
            strBuf.append(space);
            strBuf
                    .append((ref.getRefPK() == null || ref.getRefPK().length() == 0) ? "null"
                            : ref.getRefPK());
            strBuf.append(space);
        }

        //��������,����˳��,�������ѡ�е�index
        strBuf.append(con.getDataType() + space + con.getOrderSequence()
                + space + con.getComboIndex() + space);

        return strBuf.toString();
    }
    /**
     * ��֤���������֮��������߼���ȷ��.
     * 
     * ��������:(2001-2-15 11:47:35)
     * 
     * @exception nc.vo.pub.ValidationException
     *                �����֤ʧ��,�׳� ValidationException,�Դ�����н���.
     */
    public void validate() throws nc.vo.pub.ValidationException {
    }
    
    /* (non-Javadoc)
     * @see nc.vo.pub.ValueObject#clone()
     */
    //Ӧ ���Ӣ ��Ҫ����� 
    @Override
    public Object clone() {
    	ConditionVO vo = (ConditionVO) super.clone();
    	if(vo.getRefResult()!=null)
    	{
    		vo.setRefResult((RefResultVO) vo.getRefResult().clone());
    	}
    	return vo;
    }
	public boolean isFixCondition() {
		return fixCondition;
	}
	public void setFixCondition(boolean fixCondition) {
		this.fixCondition = fixCondition;
	}
}