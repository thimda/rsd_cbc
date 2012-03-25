package nc.uap.portal.ctrl.office.core;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OfficeTempeteHelper {
	private static Map<String,String> typeDocMap;
		
	static{
		typeDocMap = new HashMap<String,String>();
		typeDocMap.put("doc","1");
		typeDocMap.put("docx","1");
		
		typeDocMap.put("xls","2");
		typeDocMap.put("xlsx","2");
		
		typeDocMap.put("wps","6");
		typeDocMap.put("et","7");
	}
	public static List<OfficeTempleteFileVo> GetTempleteFile(String filetype,String doctype){
		
		List<OfficeTempleteFileVo> filelist = new ArrayList<OfficeTempleteFileVo>();
		/*
		String portalpath = nc.uap.lfw.core.LfwRuntimeEnvironment.getRealPath();
		 
		String Demopath = portalpath + "/office/demo";		
		File file = new File(Demopath);
		
		if(file.exists()){
			List<String> typelist = doctypeMap.get(doctype);
			if(typelist != null && typelist.size() > 0){
				File[] files = file.listFiles();
				if(null != files)
					for(File subfile : files){
						if(subfile.isDirectory())
							continue;
						String fileName = subfile.getName();					
						for(String type : typelist){
							if(fileName.toLowerCase().endsWith(type)){
								OfficeTempleteFileVo tv = new OfficeTempleteFileVo();
								tv.setFileName(fileName);
								tv.setDisplayName(fileName);
								tv.setFileType("word");
								tv.setFileUrl("/portal/office/demo/" + fileName);
								tv.setTempleteType("redhead");
								filelist.add(tv);
							}
						}
					}
			}
		}*/
		return filelist;
	}	
	
	public static String GetDocTypeByext(String ext){
		String ret = "";
		String fileext = "";
		if(ext != null)
			fileext = ext.trim().toLowerCase();
		if(!fileext.equals("")){
			if(typeDocMap.containsKey(fileext)){
				ret = typeDocMap.get(fileext);
			}
		}
		return ret;
	}
}
