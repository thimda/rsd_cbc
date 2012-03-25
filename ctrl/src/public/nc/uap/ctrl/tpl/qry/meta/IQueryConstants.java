package nc.uap.ctrl.tpl.qry.meta;

/**
 * �˴���������˵��.
 * ��������:(2003-09-11 15:58:30)
 * @author:����
 */
public interface IQueryConstants {
//	public final static String TITLE_NORMAL = nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("_Template","UPP_Template-000513")/*@res "��������"*/;
//	public final static String TITLE_DEFINE = nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("_Template","UPP_Template-000514")/*@res "�Զ�������"*/;


	//��������
	public final static int STRING = 0; //�ַ�
	public final static int INTEGER = 1; //����
	public final static int DECIMAL = 2; //С��
	public final static int DATE = 3; //����
	public final static int BOOLEAN = 4; //�߼�
	public final static int UFREF = 5; //����
	//public final static intCOMBO = 6;//����
	public final static int USERCOMBO = 6; //����
	public final static int USERDEF = 7; //����
	public final static int TIME = 8; //ʱ��
	public final static int MULTILANG = 9; //����
	public final static int LITERALDATE = 10; //UFLiteralDate

	public final static int DISPCODE = 0; //��ʾ����
	public final static int DISPNAME = 1; //��ʾ����

	public final static int RETURNCODE = 0; //���ر���
	public final static int RETURNNAME = 1; //��������
	public final static int RETURNPK = 2; //����PK

//	public final static String DISP_AND =
//		nc.ui.pub.ClientEnvironment.getInstance().getModuleLang().getString(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("_Template","UPP_Template-000515")/*@res "����"*/);
//	public final static String DISP_OR =
//		nc.ui.pub.ClientEnvironment.getInstance().getModuleLang().getString(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("_Template","UPP_Template-000516")/*@res "����"*/);

	public final static String NO_BRACKET = " ";
	public final static String LEFT_BRACKET = "(";
	public final static String RIGHT_BRACKET = ")";

	public final static String COMBO_INDEX = "I"; //�������(��ֵ��)
	public final static String COMBO_INDEX_CHAR = "C"; //�������(�ַ���)
	public final static String COMBO_STRING = "S"; //��������(�ַ���)
	public final static String COMBO_PAIR = "P"; //�ɶԵ�����,hzg+,since v5.02
	public final static String COMBO_UNCHECK = "U"; //δ���
	public final static String COMBO_NULL = "N"; //������Ч����
	
	
	//for translation  resid=value
	public final static String COMBO_INDEX_X = "IX"; //�������(��ֵ��)
	public final static String COMBO_INDEX_CHAR_X = "CX"; //�������(�ַ���)
	public final static String COMBO_STRING_X = "SX"; //��������(�ַ���)
	public final static String COMBO_INDICATE_X = "X"; //��ʾ��Ҫ����
	
	//֧��ͨ��Ԫ���ݶ����ö������
	public final static String COMBO_INTEGER_META = "IM"; //ö�ٵ�Value�����ε� 
	public final static String COMBO_STRING_META = "SM";  //ö�ٵ�Value���ַ���
	
	
	//for translation  resid=value
	public final static String COMBO_INDEX_DBFIELD = "IF"; //�������(��ֵ��)
	public final static String COMBO_INDEX_CHAR_DBFIELD = "CF"; //�������(�ַ���)
	public final static String COMBO_STRING_DBFIELD = "SF"; //��������(�ַ���)
	public final static String COMBO_STRING_DBFIELD_XH="XH";//���ر��е�xh(���)��ֵ
	public final static String COMBO_STRING_DBFIELD_ZFQZ="ZFQZ";//���ر��е�zfqz(�ַ�ȡֵ)��ֵ
}