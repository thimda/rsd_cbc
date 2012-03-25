package nc.uap.wfm.action;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nc.bs.framework.common.NCLocator;
import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.util.CpbServiceFacility;
import nc.uap.cpb.org.vos.CpUserVO;
import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.wfm.constant.WfmConstants;
import nc.uap.wfm.itf.IWfmTaskTokenQry;
import nc.uap.wfm.vo.WfmTaskTokenVO;
import nc.vo.org.GroupVO;
import nc.vo.pub.BusinessException;
@SuppressWarnings("restriction") public class MailAuditAction extends HttpServlet {
	private static final long serialVersionUID = 9013128810952776186L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String tokenId = request.getParameter("tokenId");
			if (tokenId == null || tokenId.length() == 0) {
				request.getRequestDispatcher("/").include(LfwRuntimeEnvironment.getWebContext().getRequest(), LfwRuntimeEnvironment.getWebContext().getResponse());
				return;
			}
			WfmTaskTokenVO token = NCLocator.getInstance().lookup(IWfmTaskTokenQry.class).getTaskTokenVoByTokenId(tokenId);
			if (token == null) {
				response.getWriter().write("<script>alert('任务已经被执行');window.close();</script>");
				return;
			}
			CpUserVO user = this.getUserByToken(token);
			this.doLogin(user, request, response);
			String taskPk = request.getParameter("taskpk");
			if (taskPk == null || taskPk.length() == 0) {
				return;
			}
			String url = LfwRuntimeEnvironment.getCorePath() + "/uimeta.um?pageId=flwdatadisps&";
			url = url + "model=" + WfmConstants.FlwDataDispModel + "&";
			url = url + "taskpk=" + taskPk;
			url = url + "&tokenId=" + tokenId;
			response.sendRedirect(url);
		} catch (Exception e) {
			LfwLogger.error(e.getMessage(), e);
		}
	}
	private CpUserVO getUserByToken(WfmTaskTokenVO token) throws CpbBusinessException {
		CpUserVO user = CpbServiceFacility.getCpUserQry().getUserByPk(token.getPk_user());
		return user;
	}
	private void doLogin(CpUserVO user, HttpServletRequest request, HttpServletResponse response) throws BusinessException {
	/*
	 * LfwSessionBean sbean = null;
	 * sbean.setDatasource(LfwRuntimeEnvironment.getDatasource());
	 * sbean.setGroupNo(getGroupNo(user));
	 * sbean.setUserType(user.getUser_type()); sbean.setUser(user); HttpSession
	 * session = request.getSession();
	 * session.setAttribute(SessionConstant.LOGIN_SESSION_BEAN, sbean);
	 * LfwRuntimeEnvironment.setLfwSessionBean(sbean);
	 */
	}
	protected String getGroupNo(CpUserVO user) throws BusinessException {
		String groupNo = "";
		if (user.getUser_type().equals(CpUserVO.USER_TYPE_USER)) {
			GroupVO groupVO = null;
			try {
				groupVO = CpbServiceFacility.getCpGroupQry().queryGroupVOByGroupID(user.getPk_group());
			} catch (BusinessException e) {
				LfwLogger.error(e.getMessage(), e);
			}
			if (groupVO == null) {
				groupNo = "0000";
			} else {
				groupNo = groupVO.getGroupno();
			}
		} else {
			groupNo = "0000";
		}
		return groupNo;
	}
	@Override protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}
}
