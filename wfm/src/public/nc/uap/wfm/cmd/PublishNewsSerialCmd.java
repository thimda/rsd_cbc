package nc.uap.wfm.cmd;

import nc.uap.wfm.cmd.ICommand;
import nc.uap.wfm.exception.WfmServiceException;

/**
 * 新闻流水号产生器
 * @author zhangxya
 *
 */
public class PublishNewsSerialCmd implements ICommand<String> {

	@Override
	public String execute() throws WfmServiceException {
		return null;
		// TODO Auto-generated method stub
//		NewsVO newsVO = (NewsVO) PwfmContext.getCurrentBpmnSession().getFormVO();
//		//String frmDefPk = LfwRuntimeEnvironment.getWebContext().getParameter(PwfmConstants.FrmDefPk);
//		try {
//			//String runnum =RunNumUtil.getRunNumUtil("pt_news", "新闻");
////			newsVO.setBillno(runnum);
////			return runnum;
//			return null;
//		} catch (BusinessException e) {
//			LfwLogger.error(e.getMessage(), e);
//			throw new LfwRuntimeException(e.getMessage());
//		}
	}

}
