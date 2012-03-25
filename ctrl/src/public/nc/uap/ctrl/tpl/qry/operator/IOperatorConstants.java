package nc.uap.ctrl.tpl.qry.operator;

public class IOperatorConstants {
	public final static String ISNULL = "is null";

	public final static String ISNOTNULL = "is not null";

	/**
	 * is null that including empty string
	 */
	public final static String ISNULL_INCLUDE_SPACE = "ISNULL";

	public final static String ISNOTNULL_INCLUDE_SPACE = "ISNOTNULL";

	public final static String EQ = "=";
	public final static String EQ2 = "==";//�������ѡ�ȺŲ�����
	
	public final static String EQIC = "= ic";
	public final static String EQ2IC = "== ic";//���Դ�Сд
	

	public final static String IN = "in";	
	public final static String INIC = "in ic";//���Դ�Сд

	public static final String NEQ = "<>";
	public static final String NEQIC = "<> ic";
	
	public static final String NEQ2 = "!=";
	public static final String NEQ2IC = "!= ic";

	public static final String NIN = "not in";
	public static final String NINIC = "not in ic";

	public static final String GREATE = ">";

	public static final String GE = ">=";

	public static final String LE = "<=";

	public static final String LIKE = "like";//������ʾ�İ��������ݿ��like %value%
	
	public static final String LLIKE = "left like";//����� like value%
	
	public static final String RLIKE = "right like";//�Ұ��� like %value
	
	public static final String LIKEIC = "like ic";//���Դ�Сд��like
	
	public static final String LLIKEIC = "llike ic";
	
	public static final String RLIKEIC = "rlike ic";
	
	public static final String NLIKE = "not like";

	public static final String LESS = "<";

	public static final String BETWEEN = "between";
	
	public static final String BETWEENIC = "between ic";//���Դ�Сд��between

}
