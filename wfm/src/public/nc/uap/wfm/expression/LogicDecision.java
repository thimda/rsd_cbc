package nc.uap.wfm.expression;
/**
 *
 * @author tianchuanwu
 */
import java.util.LinkedList;
import java.util.List;
import nc.vo.pub.lang.UFDouble;
public class LogicDecision {
	private static List<String> keyWords = null;
	static {
		keyWords = new LinkedList<String>();
		keyWords.add("!=");
		keyWords.add(">=");
		keyWords.add("<=");
		keyWords.add("==");
		keyWords.add(">");
		keyWords.add("<");
		keyWords.add("&&");
		keyWords.add("||");
	}
	class Condition {
		private String left = "";
		private String operate = "";
		private String right = "";
		private String str = "";
		List<String> conditions = new LinkedList<String>();
		List<String> operates = new LinkedList<String>();
		public Condition(String left, String operate, String right) {
			this.left = left;
			this.operate = operate;
			this.right = right;
		}
		public Condition(String str) {
			this.str = str;
			for (int i = 0; i < keyWords.size(); i++) {
				int index = str.indexOf((String) keyWords.get(i));
				if (index > -1) {
					this.operate = (String) keyWords.get(i);
					this.left = str.substring(0, index);
					this.right = str.substring(index + this.operate.length(), str.length());
					break;
				}
			}
		}
		public boolean result() throws Exception {
			for (int i = 0; i < keyWords.size(); i++) {
				int index = right.indexOf((String) keyWords.get(i));
				if (index > -1) {
					splilt(str, operates, conditions);
					return judgeExcepBrackets(operates, conditions);
				}
			}
			if (operate == "==") {
				if (left.equalsIgnoreCase(right)) {
					return true;
				}
			} else if (operate == "!=") {
				if (!left.equalsIgnoreCase(right)) {
					return true;
				}
			} else if (operate == ">" || operate == ">=" || operate == "<" || operate == "<=") {
				boolean flag = false;
				UFDouble leftLong = null;
				try {
					leftLong = new UFDouble(left);
				} catch (Exception e) {
					flag = true;
				}
				UFDouble rightLong = null;
				try {
					rightLong = new UFDouble(right);
				} catch (Exception e) {
					flag = true;
				}
				if (flag) {
					throw new Exception("表达式的需要的大小比较中含有不是数字的字符串");
				}
				if (operate.equalsIgnoreCase(">")) {
					if (leftLong.doubleValue() > rightLong.doubleValue()) {
						return true;
					}
				} else if (operate.equalsIgnoreCase(">=")) {
					if (leftLong.doubleValue() >= rightLong.doubleValue()) {
						return true;
					}
				} else if (operate.equalsIgnoreCase("<")) {
					if (leftLong.doubleValue() < rightLong.doubleValue()) {
						return true;
					}
				} else {
					if (leftLong.doubleValue() <= rightLong.doubleValue()) {
						return true;
					}
				}
			} else if (operate.equalsIgnoreCase("&&")) {
				if (Boolean.valueOf(left) && Boolean.valueOf(right)) {
					return true;
				}
			} else if (operate.equalsIgnoreCase("||")) {
				if (Boolean.valueOf(left) || Boolean.valueOf(right)) {
					return true;
				}
			}
			return false;
		}
	}
	public String replace(String str, int startIndex, int endIndex, String target) {
		StringBuffer sb = new StringBuffer();
		sb.append(str.substring(0, startIndex));
		sb.append(target);
		for (int i = 0; i < endIndex - startIndex - target.length(); i++) {
			sb.append(" ");
		}
		sb.append(str.substring(endIndex, str.length()));
		return sb.toString();
	}
	public String trim(String str) {
		StringBuffer sb = new StringBuffer();
		char ch[] = str.toCharArray();
		for (int i = 0; i < ch.length; i++) {
			if (ch[i] != ' ') {
				sb.append(ch[i]);
			}
		}
		return sb.toString();
	}
	public boolean judge(String str) throws Exception {
		char ch[] = str.toCharArray();
		StringBuffer sb = new StringBuffer();
		List<String> conditions = new LinkedList<String>();
		List<String> operates = new LinkedList<String>();
		/**
		 * 如果含有括号，说明是复杂的条件判断
		 */
		if (str.indexOf("(") > -1) {
			for (int i = 0; i < ch.length; i++) {
				boolean flag = false;
				int startIndex = -1;
				int endIndex = -1;
				if (ch[i] == '(') {
					startIndex = i;
					for (int j = i + 1; j < ch.length; j++) {
						if (flag == true) {
							break;
						}
						if (ch[j] == '(') {
							startIndex = j;
						}
						if (ch[j] == ')') {
							flag = true;
							endIndex = j;
						}
					}
				}
				if (endIndex > -1) {
					if (sb.length() != 0) {
						sb.delete(0, sb.length());
					}
					sb.append(str.substring(startIndex + 1, endIndex));
					str = this.replace(str, startIndex, endIndex + 1, String.valueOf(new Condition(sb.toString()).result()));
					System.out.println(str);
					i = endIndex + 1;
				}
			}
			str = this.trim(str);
			return judge(str);
		} else {
			if (str.indexOf("&&") == -1 && str.indexOf("||") == -1) {
				return new Condition(str).result();
			} else {
				splilt(str, operates, conditions);
				return this.judgeExcepBrackets(operates, conditions);
			}
		}
	}
	public boolean judgeExcepBrackets(List<String> operates, List<String> conditions) throws Exception {
		if (operates.size() >= 1 && conditions.size() >= 2) {
			boolean result = new Condition((String) conditions.get(0), (String) operates.get(0), (String) conditions.get(1)).result();
			operates.remove(0);
			conditions.remove(0);
			conditions.remove(0);
			conditions.add(0, String.valueOf(result));
		}
		if (operates.size() >= 1 && conditions.size() >= 2) {
			judgeExcepBrackets(operates, conditions);
		}
		if (conditions.size() == 1) {
			return Boolean.valueOf((String) conditions.get(0));
		}
		return false;
	}
	public void splilt(String str, List<String> operates, List<String> conditions) {
		String temp = str;
		for (int i = 0; i < str.length(); i++) {
			if (temp.charAt(0) == 't') {
				conditions.add(temp.substring(0, 4));
				temp = temp.substring(4);
				i = i + 3;
			} else if (temp.charAt(0) == 'f') {
				conditions.add(temp.substring(0, 5));
				temp = temp.substring(5);
				i = i + 4;
			} else if (temp.charAt(0) == '&') {
				operates.add(temp.substring(0, 2));
				temp = temp.substring(2);
				i = i + 1;
			} else if (temp.charAt(0) == '|') {
				operates.add(temp.substring(0, 2));
				temp = temp.substring(2);
				i = i + 1;
			}
		}
	}
	public static void main(String args[]) {
		String str = "((attribute01!=blank)||(attribute02==同意))&&((attribute03>10)&&(attribute04<10))";
		// String str = "(attribute01==attribute01)||(attribute02==同意)||(attribute01==attribute01)";
		try {
			System.out.println(new LogicDecision().judge(str));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
	
	
	 
