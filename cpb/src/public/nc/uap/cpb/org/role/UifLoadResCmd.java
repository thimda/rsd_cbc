package nc.uap.cpb.org.role;

import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.cmd.UifDatasetLoadCmd;
import nc.uap.lfw.core.data.Dataset;
import nc.vo.pub.SuperVO;

public class UifLoadResCmd extends UifDatasetLoadCmd{

	@Override
	protected String postProcessQueryVO(SuperVO vo, Dataset ds) {
		// TODO Auto-generated method stub
		String type = LfwRuntimeEnvironment.getWebContext().getWebSession().getOriginalParameter("type");
		if(type != null && type.equals("0"))
			return " type ='1'";
		else if(type != null && type.equals("1"))
			return " type ='0'";
		else
			return super.postProcessQueryVO(vo, ds);
	}

	public UifLoadResCmd(String dsId) {
		super(dsId);
		// TODO Auto-generated constructor stub
	}

}
