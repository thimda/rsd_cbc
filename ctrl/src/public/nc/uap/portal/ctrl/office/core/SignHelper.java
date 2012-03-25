package nc.uap.portal.ctrl.office.core;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 印章帮助；类
 * @author lisw
 *
 */
public class SignHelper {
	/**
	 * 获取当前用户所有印章
	 * @return
	 */
	public static List<OfficeSignVO> GetSign(){
		List<OfficeSignVO> list = new ArrayList<OfficeSignVO>();
		String portalpath = nc.uap.lfw.core.LfwRuntimeEnvironment.getRealPath();		
		String esppath = portalpath + "/office/esp";		
		File file = new File(esppath);
		if(file.exists())
		{
			File[] files = file.listFiles();
			if(null != files)
				for(File subfile : files)
				{
					if(subfile.isDirectory())
						continue;
					String fileName = subfile.getName();
					if(fileName.endsWith(".esp") )
					{
						OfficeSignVO tv = new OfficeSignVO();
						tv.setUsername("test");
						tv.setUsercode("test");						
						tv.setUrl("/portal/office/esp/" + fileName);
						tv.setSignname(fileName);
						list.add(tv);
					}
				}
		}
		return list;
	}
}
