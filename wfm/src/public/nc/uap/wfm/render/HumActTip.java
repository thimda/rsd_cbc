package nc.uap.wfm.render;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class HumActTip {
	public static String getHumActInsTip(Map<String, String> humActInsTipMap) {
		Set<String> keySet = humActInsTipMap.keySet();
		Iterator<String> iter = keySet.iterator();
		String humActInsPk = null;
		String tipStr = null;
		while (iter.hasNext()) {
			humActInsPk = iter.next();
			if (tipStr == null) {
				tipStr = "";
			}
			if (tipStr.length() == 0) {
				tipStr = humActInsTipMap.get(humActInsPk);
			} else {
				tipStr = tipStr + "<br>" + humActInsTipMap.get(humActInsPk);
			}
		}
		return tipStr;
	}

}
