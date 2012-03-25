package nc.uap.wfm.utils;
import nc.jdbc.framework.generator.SequenceGenerator;
import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.login.vo.LfwSessionBean;
public class WfmFormInsUtil {
	public static String genFrmInsDefalutPk() {
		String dataSource = LfwRuntimeEnvironment.getDatasource();
		String groupNo = "";
		LfwSessionBean ses = LfwRuntimeEnvironment.getLfwSessionBean();
		if (ses == null)
			groupNo = "0001";
		else {
			groupNo = ses.getPk_unit();
		}
		return new SequenceGenerator(dataSource).generate(groupNo);
	}
}
