package nc.uap.wfm.utils;

import java.io.File;
import java.io.FileOutputStream;

import nc.bs.framework.common.NCLocator;
import nc.uap.dbl.itf.IFrmTmpQry;
import nc.uap.dbl.vo.DblFormTemplateVO;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.wfm.itf.IWfmProDefQry;
import nc.uap.wfm.vo.WfmProdefVO;

/**
 * 现有库数据导出,方便构造初始库
 * @author dengjt
 *
 */
public final class ExportDbUtil {
	public static void exportFmTmp() {
		IFrmTmpQry qry = NCLocator.getInstance().lookup(IFrmTmpQry.class);
		try {
			DblFormTemplateVO[] vos = qry.getFrmTmps();
			if(vos != null){
				File dir = new File("D:\\fmtmp");
				if(!dir.exists())
					dir.mkdirs();
				for (int i = 0; i < vos.length; i++) {
					String tmpStr = vos[i].getTemplateStr();
					FileOutputStream out = null;
					try {
						File f = new File("D:\\fmtmp\\" + vos[i].getPk_formtemplate() + ".xml");
						f.createNewFile();
						out = new FileOutputStream(f);
						out.write(tmpStr.getBytes());
					} 
					catch (Exception e) {
						LfwLogger.error(e);
					}
					finally{
						if(out != null)
							out.close();
					}
				}
			}
		} 
		catch (Exception e) {
			LfwLogger.error(e);
		}
	}
	
	
	public static void exportWf() {
		IWfmProDefQry qry = NCLocator.getInstance().lookup(IWfmProDefQry.class);
		try {
			WfmProdefVO[] vos = qry.getAllProDef();
			if(vos != null){
				File dir = new File("D:\\prodef");
				if(!dir.exists())
					dir.mkdirs();
				for (int i = 0; i < vos.length; i++) {
					String tmpStr = vos[i].getProcessstr();
					FileOutputStream out = null;
					try {
						File f = new File("D:\\prodef\\" + vos[i].getPk_prodef() + ".xml");
						f.createNewFile();
						out = new FileOutputStream(f);
						out.write(tmpStr.getBytes());
					} 
					catch (Exception e) {
						LfwLogger.error(e);
					}
					finally{
						if(out != null)
							out.close();
					}
				}
			}
		} 
		catch (Exception e) {
			LfwLogger.error(e);
		}
	}
}
