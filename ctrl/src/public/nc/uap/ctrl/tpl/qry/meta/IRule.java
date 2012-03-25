package nc.uap.ctrl.tpl.qry.meta;

import java.io.Serializable;

/**
 * ����ӿڱ�־
 * 
 * @author huangzg 2007-3-6
 * @since v5.3
 */
public interface IRule extends Serializable {

	/**
	 * Logical Type define as follow.
	 */
	public final static int TYPE_AND = 0;

	public final static int TYPE_OR = 1;

	public final static int TYPE_NOT = 2;

	public final static int TYPE_XOR = 3;// ��� .,����

	public final static int TYPE_DEDUCTIVE = 5;// �ƶ� if-then

	public final static int TYPE_DEDUCTIVE_COLLECTION = 6;// �ƶ� if-then.��Ա��岿��

	public final static int TYPE_DEDUCTIVE_PART = 7;// �ƶ� if-then�Ĳ���.��if��Then.

	public final static int TYPE_LOGICAL = 8;

	public final static int TYPE_SQL = 9;

	/**
	 * Compare Type define as follow.
	 */
	public final static int TYPE_EQ = 10;// EQ=equal

	// range=between.Only fit for numerical
	public final static int TYPE_RANGE = 11;

	public final static int TYPE_IN = 12;// in=contains

	/**
	 * Regular Expression. Only fit for String,and support some cases as follow:<br>
	 * <li>(1)Start with some words.For Example: hzg\w* respent start with hzg
	 * <li>(2)End with some words.For Example: \w*hzg respent end with hzg
	 * <li>(3)Fix Length.For Example: \w*{4} respent only suit for 4 words.
	 * <li>(4)and so on.
	 */
	public final static int TYPE_PATTERN = 13;

	/**
	 * Collection Type define as follow
	 */
	public final static int TYPE_ALL = 15;// ĳ����������, ֻ�����ڱ���

	public final static int TYPE_EXIST = 16;// ĳ�д�������, ֻ�����ڱ���

}
