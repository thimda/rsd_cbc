package nc.uap.portal.ctrl.office.core;

import java.util.ArrayList;
import java.util.List;

import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.vo.pub.lang.UFDate;

public class DefaultBookMarkLoader implements IBookMarkLoader {

	@Override
	public List<OfficeBookMarkVO> LoadBookMarks() {
		List<OfficeBookMarkVO> list = new ArrayList<OfficeBookMarkVO>();
		
		OfficeBookMarkVO vo = new OfficeBookMarkVO();
		vo.setName("username");
		vo.setValue(LfwRuntimeEnvironment.getLfwSessionBean().getUser_name());
		list.add(vo);
		
		vo = new OfficeBookMarkVO();
		vo.setName("usercode");
		vo.setValue(LfwRuntimeEnvironment.getLfwSessionBean().getUser_code());
		list.add(vo);
		
		vo = new OfficeBookMarkVO();
		vo.setName("serverdate");
		vo.setValue((new UFDate()).toStdString());		
		list.add(vo);
		
		
		return list;
	}

}
